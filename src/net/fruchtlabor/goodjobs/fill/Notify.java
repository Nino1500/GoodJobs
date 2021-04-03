package net.fruchtlabor.goodjobs.fill;

import net.fruchtlabor.goodjobs.GoodJobs;
import net.fruchtlabor.goodjobs.fill.Job;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.UUID;

public class Notify {
    Plugin plugin;

    public Notify(Plugin plugin) {
        this.plugin = plugin;
    }

    public void notifyLevelUp(int level, UUID uuid, String jobname){
        Player player = Bukkit.getPlayer(uuid);
        Job job = GoodJobs.getJobByName(jobname);
        ChatColor color = job.getColor();
        player.sendMessage(color+jobname+ChatColor.GOLD+" [LevelUp] ");
    }
    public void notifyExpGain(double exp, UUID uuid, String jobname){
        Player player = Bukkit.getPlayer(uuid);
        Job job = GoodJobs.getJobByName(jobname);
        ChatColor color = job.getColor();
        player.sendMessage(color+jobname+ChatColor.GOLD+" + "+exp);
    }
    public void notifyJoin(UUID uuid, String jobname, boolean fail){
        Player player = Bukkit.getPlayer(uuid);
        Job job = GoodJobs.getJobByName(jobname);
        ChatColor color = job.getColor();
        if(fail){
            player.sendMessage(color+jobname+ChatColor.GOLD+" schon vorhanden!");
        }else{
            player.sendMessage(color+jobname+ChatColor.GOLD+" beigetreten");
        }
    }
    public void notifyLeave(UUID uuid, String jobname, boolean fail){
        Player player = Bukkit.getPlayer(uuid);
        Job job = GoodJobs.getJobByName(jobname);
        ChatColor color = job.getColor();
        if(fail){
            player.sendMessage(color+jobname+ChatColor.GOLD+" nicht vorhanden!");
        }else{
            player.sendMessage(color+jobname+ChatColor.GOLD+" verlassen");
        }
    }

}
