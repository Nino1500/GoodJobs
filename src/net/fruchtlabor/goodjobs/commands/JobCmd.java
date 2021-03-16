package net.fruchtlabor.goodjobs.commands;

import net.fruchtlabor.goodjobs.GoodJobs;
import net.fruchtlabor.goodjobs.db.DBController;
import net.fruchtlabor.goodjobs.fill.Job;
import net.fruchtlabor.goodjobs.listeners.Notify;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class JobCmd implements CommandExecutor {
    Plugin plugin;
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(command.getName().equalsIgnoreCase("job") && commandSender instanceof Player){
            Player player = ((Player) commandSender).getPlayer();
            Notify notify = new Notify(plugin);
            if(player.hasPermission("Job.use")){
                if(strings.length == 2){
                    if(strings[0].equalsIgnoreCase("join")){
                        Job job = GoodJobs.getJobByName(strings[1]);
                        if(job != null){
                            DBController dbController = GoodJobs.dbController;
                            dbController.insertPlayer(player.getUniqueId(), job.getName());
                            notify.notifyJoin(player.getUniqueId(), job.getName(), false);
                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user "+player.getName()+" permission set Jobs."+job.getName());
                            return true;
                        }else{
                            notify.notifyJoin(player.getUniqueId(), strings[1], true);
                            return false;
                        }
                    }else if(strings[0].equalsIgnoreCase("leave")){
                        Job job = GoodJobs.getJobByName(strings[1]);
                        if(job != null){
                            notify.notifyLeave(player.getUniqueId(), job.getName(), false);
                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user "+player.getName()+" permission unset Jobs."+job.getName());
                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "cmi money take "+player.getName()+" 50");
                            return true;
                        }else{
                            notify.notifyLeave(player.getUniqueId(), strings[1], true);
                            return false;
                        }
                    }
                }
                if(strings.length == 0){
                    //TODO open gui for all the stuff
                }
            }
        }
        return false;
    }

    public void setPlugin(Plugin plugin) {
        this.plugin = plugin;
    }
}
