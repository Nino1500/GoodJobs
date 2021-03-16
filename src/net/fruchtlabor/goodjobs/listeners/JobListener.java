package net.fruchtlabor.goodjobs.listeners;

import net.fruchtlabor.goodjobs.GoodJobs;
import net.fruchtlabor.goodjobs.db.DBController;
import net.fruchtlabor.goodjobs.fill.*;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.UUID;

public class JobListener implements Listener {
    Plugin plugin;

    public JobListener(Plugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event){
        Bukkit.getConsoleSender().sendMessage("1");
        Player player = event.getPlayer();
        PlayerManager playerManager = new PlayerManager(plugin);
        ArrayList<Job> jobs = playerManager.getJobs(player.getUniqueId());
        Bukkit.getConsoleSender().sendMessage("jobs.size: "+jobs.size());
        for (Job job : jobs){
            Bukkit.getConsoleSender().sendMessage("2");
            for (BreakProg_items item : job.getBreaklist()){
                Bukkit.getConsoleSender().sendMessage("3");
                if(item.getMaterial().equals(event.getBlock().getType())){
                    Bukkit.getConsoleSender().sendMessage("4");
                    addExp(player.getUniqueId(), item.getExp(), job.getName());
                }
            }
        }
    }

    @EventHandler
    public void onFish(PlayerFishEvent event){
        Player player = event.getPlayer();
        PlayerManager playerManager = new PlayerManager(plugin);
        ArrayList<Job> jobs = playerManager.getJobs(player.getUniqueId());
        if(event.getState() == PlayerFishEvent.State.BITE){
            for (Job job : jobs){
                for (FishProg item : job.getFishlist()){
                    Material mat = ((Item)event.getCaught()).getItemStack().getType();
                    //if the item is "stone" so it gives exp to all the rest of the items ...
                    if(item.getMaterial() == Material.STONE){
                        addExp(player.getUniqueId(), item.getExp(), job.getName());
                        break;
                    }
                    if(item.getMaterial().equals(mat)){
                        addExp(player.getUniqueId(), item.getExp(), job.getName());
                        break;
                    }
                }
            }
        }
    }
    @EventHandler
    public void onKill(EntityDeathEvent event){
        if(event.getEntity().getKiller() != null){
            Player player = event.getEntity().getKiller();
            PlayerManager playerManager = new PlayerManager(plugin);
            ArrayList<Job> jobs = playerManager.getJobs(player.getUniqueId());
            for (Job job : jobs){
                for (EntityProg entityProg : job.getEntityProgList()){
                    if(entityProg.getEntity().name().equalsIgnoreCase(event.getEntity().getName())){
                        addExp(player.getUniqueId(), entityProg.getExp(), job.getName());
                    }
                }
            }
        }
    }

    public void addExp(UUID uuid, double exp, String jobname){
        Bukkit.getConsoleSender().sendMessage("Test");
        Bukkit.getScheduler().runTaskAsynchronously(plugin, new Runnable() {
            @Override
            public void run() {
                DBController dbController = GoodJobs.dbController;
                dbController.addExp(uuid, jobname, exp);
                Bukkit.getPlayer(uuid).sendMessage("DEBUG_EXP: "+dbController.getExp(uuid, jobname));
            }
        });
    }

}
