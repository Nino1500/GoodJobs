package net.fruchtlabor.goodjobs.cg;

import net.fruchtlabor.goodjobs.GoodJobs;
import net.fruchtlabor.goodjobs.db.DBController;
import net.fruchtlabor.goodjobs.db.DBManager;
import net.fruchtlabor.goodjobs.fill.BreakLogContainer;
import net.fruchtlabor.goodjobs.fill.Job;
import net.fruchtlabor.goodjobs.fill.Notify;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.UUID;

public class JobCmd implements CommandExecutor {

    Plugin plugin;

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(command.getName().equalsIgnoreCase("job") && commandSender instanceof Player){
            Player player = ((Player) commandSender).getPlayer();
            if(strings.length == 0){
                JobInv.INVENTORY.open(player);
            }
            if(strings.length>=4 && player.hasPermission("job.admin")){
                if(strings[0].equalsIgnoreCase("give")){
                    Player player1 = Bukkit.getPlayer(strings[1]);
                    int lvl = Integer.parseInt(strings[2]);
                    String jobname = strings[3];
                    GoodJobs.dbManager.getByUUID(player1.getUniqueId(), jobname).addLvl(lvl);
                }
            }
            if(strings.length>=2){
                if(strings[0].equalsIgnoreCase("notify")){
                    if(strings[1].equalsIgnoreCase("aus")){
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user "+player.getName()+" permission unset Job.notify.exp");
                    }else if(strings[1].equalsIgnoreCase("an")){
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user "+player.getName()+" permission set Job.notify.exp");
                    }
                }
                if(strings[0].equalsIgnoreCase("check") && player.hasPermission("job.mod")){
                    Player pl = Bukkit.getPlayer(strings[1]);
                    if(pl != null){
                        BreakLogContainer container = GoodJobs.logController.getBreakLog(pl.getUniqueId());
                        player.sendMessage("Spieler hat:\n");
                        player.sendMessage("Dia: "+container.getDiamond() + "\n" +
                                "Ancient Debris: "+container.getAncient() +"\n" +
                                "Gold: "+container.getGold() +"\n" +
                                "Emerald: "+container.getEmerald() +"\n" +
                                "Stein: "+container.getStone()+" abgebaut!");
                    }
                }
                if(strings[0].equalsIgnoreCase("join") && player.hasPermission("job.join")){
                    Notify notify = new Notify(GoodJobs.plugin);
                    String job = strings[1];
                    Job jobx = GoodJobs.getJobByName(job);
                    if(jobx != null){
                        if(GoodJobs.dbManager.addPlayer(player.getUniqueId(), jobx.getName(), 1, 0.0, 1)){
                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user "+player.getName()+" permission set Job."+jobx.getName());
                            notify.notifyJoin(player.getUniqueId(), jobx.getName(), false);
                        }else{
                            notify.notifyJoin(player.getUniqueId(), jobx.getName(), true);
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }

    public void setPlugin(Plugin plugin) {
        this.plugin = plugin;
    }
}
