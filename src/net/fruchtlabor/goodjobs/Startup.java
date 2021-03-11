package net.fruchtlabor.goodjobs;

import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Startup {
    Plugin plugin;

    ArrayList<Job> jobs = new ArrayList<>();

    public Startup(Plugin plugin) {
        this.plugin = plugin;
    }

    public void initialize_jobs(){
        File job_data = new File(plugin.getDataFolder()+File.separator+"jobs.yml");
        FileConfiguration job_data_config = YamlConfiguration.loadConfiguration(job_data);

        if(!job_data.exists()){
            plugin.saveResource("jobs.yml", false);
        }else{
            try {
                job_data_config.save(job_data);
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        YamlConfiguration yaml_job_data = YamlConfiguration.loadConfiguration(job_data);
        ConfigurationSection section = yaml_job_data.getConfigurationSection("Jobs");
        List<Job> jobList = new ArrayList<>();
        if(section != null){
            for (final String key : section.getKeys(false)){
                jobList.add(new Job(section.getConfigurationSection(key)));
            }
        }
    }

    public ArrayList<Job> getJobs() {
        return jobs;
    }
}
