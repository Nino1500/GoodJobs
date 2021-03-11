package net.fruchtlabor.goodjobs;

import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.util.*;

public class JobsPlayerManager {
    Plugin plugin;

    public JobsPlayerManager(Plugin plugin) {
        this.plugin = plugin;
    }

    public List<JobsPlayer> getPlayers(){
        File data = new File(plugin.getDataFolder()+File.separator+"players.yml");
        FileConfiguration dataconfig = YamlConfiguration.loadConfiguration(data);

        if(!data.exists()){
            plugin.saveResource("players.yml", false);
        }
        else{
            try {
                dataconfig.save(data);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        List<JobsPlayer> list = new ArrayList<>();

        YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(data);
        ConfigurationSection section = yamlConfiguration.getConfigurationSection("players");
        if(section != null){
            for (final String key : section.getKeys(false)){
                list.add(new JobsPlayer(section.getConfigurationSection(key)));
            }
        }

        return list;
    }

    public Job getJobByName(String name){
        List<Job> list = GoodJobs.jobs;
        for (int i = 0; i < list.size(); i++) {
            if(list.get(i).getName().equalsIgnoreCase(name)){
                return list.get(i);
            }
        }
        return null;
    }

    public OfflinePlayer getPlayer(String name){
        return null;
    }
    public OfflinePlayer getPlayer(UUID uuid){
        return null;
    }
    public boolean addJobsPlayer(UUID uuid, Job job){

        File data = new File(plugin.getDataFolder()+File.separator+"players.yml");
        FileConfiguration dataconfig = YamlConfiguration.loadConfiguration(data);

        if(!data.exists()){
            plugin.saveResource("players.yml", false);
        }
        else{
            try {
                dataconfig.save(data);
            }catch (Exception e){
                e.printStackTrace();
                return false;
            }
        }
        List<Integer> list = dataconfig.getIntegerList("players."+uuid.toString());

        if(list.size()>=GoodJobs.max_jobs-1){
            return false;
        }
        else if(list.contains(job.getId())){
            return false;
        }
        else{
            list.add(job.getId());
        }

        dataconfig.set("players."+uuid.toString(), list);

        try {
            dataconfig.save(data);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }

        return true;
    }
    public boolean removeJob(UUID uuid){
        File data = new File(plugin.getDataFolder()+File.separator+"players.yml");
        FileConfiguration dataconfig = YamlConfiguration.loadConfiguration(data);

        if(!data.exists()){
            plugin.saveResource("players.yml", false);
        }
        else{
            try {
                dataconfig.save(data);
            }catch (Exception e){
                e.printStackTrace();
                return false;
            }
        }

        dataconfig.set("players."+uuid.toString(), "");

        try {
            dataconfig.save(data);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }

        return true;
    }
}
