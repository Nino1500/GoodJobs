package net.fruchtlabor.goodjobs;

import net.fruchtlabor.goodjobs.cg.JobCmd;
import net.fruchtlabor.goodjobs.db.DBController;
import net.fruchtlabor.goodjobs.db.DBManager;
import net.fruchtlabor.goodjobs.db.LogController;
import net.fruchtlabor.goodjobs.fill.Job;
import net.fruchtlabor.goodjobs.listeners.*;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.*;

public class GoodJobs extends JavaPlugin {

    public static double mult = 5.0;
    public static Plugin plugin;
    public static Economy econ;

    public static DBManager dbManager;
    public static LogController logController;

    public static List<Job> jobs = new ArrayList<>();

    @Override
    public void onDisable() {
        dbManager.safeAllPlayers();
    }

    @Override
    public void onEnable() {

        plugin = this;

        FishListener fishListener = new FishListener(this);
        this.getServer().getPluginManager().registerEvents(fishListener, this);
        BreakListener breakListener = new BreakListener(this);
        this.getServer().getPluginManager().registerEvents(breakListener, this);
        MobKillListener mobKillListener = new MobKillListener(this);
        this.getServer().getPluginManager().registerEvents(mobKillListener, this);
        this.getServer().getPluginManager().registerEvents(new BlockBreakPlaceProtection(), this);
        this.getServer().getPluginManager().registerEvents(new LogBreakListener(), this);
        jobs = fillJobs();

        DBController dbController = new DBController(plugin);

        for (Job job : jobs) {
            dbController.createTable(job.getName());
        }

        dbController.logBreakTable();
        dbController.logPlaceTable();

        dbManager = new DBManager(this);
        logController = new LogController(this);

        //Job command
        JobCmd jobCmd = new JobCmd();
        jobCmd.setPlugin(this);
        this.getCommand("job").setExecutor(jobCmd);

        if (!setupEconomy()) {
            this.getLogger().severe("Disabled due to no Vault dependency found!");
            Bukkit.getPluginManager().disablePlugin(this);
        }

    }


    public List<Job> fillJobs() {
        List<Job> jobs = new ArrayList<>();
        File job_data = new File(this.getDataFolder() + File.separator + "jobs.yml");
        FileConfiguration job_data_config = YamlConfiguration.loadConfiguration(job_data);
        if (!job_data.exists()) {
            this.saveResource("jobs.yml", false);
        } else {
            try {
                job_data_config.save(job_data);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        YamlConfiguration yaml_job_data = YamlConfiguration.loadConfiguration(job_data);
        ConfigurationSection section = yaml_job_data.getConfigurationSection("Jobs");
        if (section != null) {
            for (final String key : section.getKeys(false)) {
                jobs.add(new Job(section.getConfigurationSection(key)));
            }
        }
        return jobs;
    }

    public static Job getJobByName(String name) {
        for (Job job : jobs) {
            if (job.getName().equalsIgnoreCase(name)) {
                return job;
            }
        }
        return null;
    }

    private boolean setupEconomy() {
        if (Bukkit.getPluginManager().getPlugin("Vault") == null) {
            return false;
        }

        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }
}
