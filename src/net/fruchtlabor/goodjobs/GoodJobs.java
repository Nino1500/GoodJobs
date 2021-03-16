package net.fruchtlabor.goodjobs;

import net.fruchtlabor.goodjobs.commands.JobCmd;
import net.fruchtlabor.goodjobs.db.DBController;
import net.fruchtlabor.goodjobs.fill.Job;
import net.fruchtlabor.goodjobs.listeners.JobListener;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class GoodJobs extends JavaPlugin {

    public static int max_jobs = 2;
    public static String msg_jobjoin = "Du bist erfolgreich jobname gejoint!";
    public static String msg_jobjoin_fail = "Du bist dem Job jobname nicht gejoint!";
    public static String dbName = "goodjobs";
    public static boolean switch_money = false;
    public static DBController dbController;

    public static List<Job> jobs = new ArrayList<>();

    @Override
    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage("DB notwendig ... fahre Plugin herunter ... bum bum bum");
        super.onDisable();
    }

    @Override
    public void onEnable() {

        JobListener jobListener = new JobListener(this);
        this.getServer().getPluginManager().registerEvents(jobListener, this);

        jobs = fillJobs();
        //the db controller for access to the db
        dbController = new DBController(this, dbName);
        //check if db exists if not create it
        //create a table for each job
        for (Job job : jobs){
            dbController.createTable(job.getName());
        }
        //Job command
        JobCmd jobCmd = new JobCmd();
        jobCmd.setPlugin(this);
        this.getCommand("job").setExecutor(jobCmd);
    }

    public List<Job> fillJobs(){
        List<Job> jobs = new ArrayList<>();
        File job_data = new File(this.getDataFolder()+File.separator+"jobs.yml");
        FileConfiguration job_data_config = YamlConfiguration.loadConfiguration(job_data);
        if(!job_data.exists()){
            this.saveResource("jobs.yml", false);
        }else{
            try {
                job_data_config.save(job_data);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        YamlConfiguration yaml_job_data = YamlConfiguration.loadConfiguration(job_data);
        ConfigurationSection section = yaml_job_data.getConfigurationSection("Jobs");
        if(section != null){
            for (final String key : section.getKeys(false)){
                jobs.add(new Job(section.getConfigurationSection(key)));
            }
        }
        return jobs;
    }

    public static Job getJobByName(String name){
        for (Job job : jobs){
            if(job.getName().equalsIgnoreCase(name)){
                return job;
            }
        }
        return null;
    }

}
