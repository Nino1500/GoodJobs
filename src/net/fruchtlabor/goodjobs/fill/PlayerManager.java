package net.fruchtlabor.goodjobs.fill;

import net.fruchtlabor.goodjobs.GoodJobs;
import net.fruchtlabor.goodjobs.db.DBController;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PlayerManager {
    Plugin plugin;

    public PlayerManager(Plugin plugin) {
        this.plugin = plugin;
    }

    public JobPlayer getPlayer(UUID uuid, String jobname){
        for (Job job : GoodJobs.jobs){
            if(job.getName().equalsIgnoreCase(jobname)){
                DBController dbController = new DBController(plugin, GoodJobs.dbName);
                return new JobPlayer(job.getName(), dbController.getLvl(uuid, jobname), dbController.getExp(uuid, jobname), uuid, plugin);
            }
        }
        return null;
    }

    public ArrayList<Job> getJobs(UUID uuid){
        Player player = Bukkit.getPlayer(uuid);
        ArrayList<Job> jobs = new ArrayList<>();
        for (Job job : GoodJobs.jobs){
            if(player.hasPermission("Jobs."+job.getName())){
                jobs.add(job);
            }
        }
        return jobs;
    }

}
class JobPlayer{
    String job;
    int lvl;
    double exp;
    UUID uuid;
    Plugin plugin;

    public JobPlayer(String job, int lvl, double exp, UUID uuid, Plugin plugin) {
        this.job = job;
        this.lvl = lvl;
        this.exp = exp;
        this.uuid = uuid;
        this.plugin = plugin;
    }

    public String getJob() {
        return job;
    }

    public int getLvl() {
        return lvl;
    }

    public double getExp() {
        return exp;
    }

    public UUID getUuid() {
        return uuid;
    }

    public boolean addExp(double exp){
        DBController dbController = new DBController(plugin, GoodJobs.dbName);
        dbController.addExp(uuid, job, exp);
        return true;
    }

    public boolean addLvl(int lvl){
        DBController dbController = new DBController(plugin, GoodJobs.dbName);
        dbController.addLvl(uuid, job, lvl);
        return true;
    }

}
