package net.fruchtlabor.goodjobs.commands;

import net.fruchtlabor.goodjobs.GoodJobs;
import net.fruchtlabor.goodjobs.Job;
import net.fruchtlabor.goodjobs.JobsPlayerManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.awt.*;

public class JobsJoin implements CommandExecutor {
    Plugin plugin;
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(commandSender instanceof Player && command.getName().equalsIgnoreCase("job")){
            Player player = ((Player) commandSender).getPlayer();
            if(player.hasPermission("jobs.join")){
                if(strings.length == 2){
                    if(strings[0].equalsIgnoreCase("join")){
                        String job = strings[1];
                        JobsPlayerManager playerManager = new JobsPlayerManager(plugin);
                        Job job_ = playerManager.getJobByName(job);
                        if(job_ != null){
                            if(playerManager.addJobsPlayer(player.getUniqueId(), job_)){
                                player.sendMessage(GoodJobs.msg_jobjoin.replace("jobname", job_.getColor()+job_.getName()));
                            }else{
                                player.sendMessage(GoodJobs.msg_jobjoin_fail.replace("jobname", job_.getColor()+job_.getName()));
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    public void setPlugin(Plugin plugin){
        this.plugin = plugin;
    }
}
