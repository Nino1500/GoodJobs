package net.fruchtlabor.goodjobs.listeners;

import net.fruchtlabor.goodjobs.GoodJobs;
import net.fruchtlabor.goodjobs.db.DBController;
import net.fruchtlabor.goodjobs.db.JobPlayer;
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

public class FishListener implements Listener {
    Plugin plugin;

    public FishListener(Plugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onFish(PlayerFishEvent event){
        Player player = event.getPlayer();
        ArrayList<Job> jobs = GoodJobs.dbManager.getPlayerJobs(player.getUniqueId());
        if(event.getState() == PlayerFishEvent.State.BITE){
            for (Job job : jobs){
                for (FishProg item : job.getFishlist()){
                    Material mat = ((Item)event.getCaught()).getItemStack().getType();
                    //if the item is "stone" so it gives exp to all the rest of the items ...
                    if(item.getMaterial().equals(mat)){
                        JobPlayer jobPlayer = GoodJobs.dbManager.getByUUID(player.getUniqueId(), job.getName());
                        if(item.getAt_lvl() <= jobPlayer.getLvl()){
                            jobPlayer.addExp(item.getExp());
                        }
                    }
                }
            }
        }
    }

}
