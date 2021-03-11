package net.fruchtlabor.goodjobs;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.ConfigurationSection;

import java.util.List;
import java.util.UUID;

public class JobsPlayer {
    List<Job> jobs;
    UUID uuid;

    public JobsPlayer(ConfigurationSection configurationSection) {
        this.uuid = UUID.fromString(configurationSection.getString("players."));
        List<Integer> list = configurationSection.getIntegerList("players."+uuid);
        for (int i = 0; i < GoodJobs.jobs.size(); i++) {
            for (int j = 0; j < list.size(); j++) {
                if(GoodJobs.jobs.get(i).getId() == list.get(j)){
                    jobs.add(GoodJobs.jobs.get(i));
                }
            }
        }
    }

    public UUID getUuid() {
        return uuid;
    }

    public OfflinePlayer getPlayer() {
        return Bukkit.getPlayer(uuid);
    }

    public List<Job> getJobs() {
        return jobs;
    }
}
