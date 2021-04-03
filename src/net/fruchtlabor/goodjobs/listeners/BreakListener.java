package net.fruchtlabor.goodjobs.listeners;

import net.fruchtlabor.goodjobs.GoodJobs;
import net.fruchtlabor.goodjobs.db.JobPlayer;
import net.fruchtlabor.goodjobs.fill.BreakProg_items;
import net.fruchtlabor.goodjobs.fill.Job;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;

public class BreakListener implements Listener {
    Plugin plugin;

    public BreakListener(Plugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        ArrayList<Job> jobs = GoodJobs.dbManager.getPlayerJobs(player.getUniqueId());
        if(!GoodJobs.logController.checkPlaceLog(event.getBlock().getLocation())){
            for (Job job : jobs) {
                for (BreakProg_items item : job.getBreaklist()) {
                    if (item.getMaterial().equals(event.getBlock().getType())) {
                        JobPlayer jobPlayer = GoodJobs.dbManager.getByUUID(player.getUniqueId(), job.getName());
                        if(item.getAt_lvl() <= jobPlayer.getLvl() && isItemUsed(player.getInventory().getItemInMainHand().getType())){
                            jobPlayer.addExp(item.getExp());
                        }
                    }
                }
            }
        }
    }
    public boolean isItemUsed(Material material){
        List<Material> list = new ArrayList<>();
        list.add(Material.DIAMOND_PICKAXE);
        list.add(Material.IRON_PICKAXE);
        list.add(Material.STONE_PICKAXE);
        list.add(Material.DIAMOND_AXE);
        list.add(Material.IRON_AXE);
        list.add(Material.STONE_AXE);
        list.add(Material.DIAMOND_SHOVEL);
        list.add(Material.IRON_SHOVEL);
        list.add(Material.STONE_SHOVEL);
        list.add(Material.DIAMOND_HOE);
        list.add(Material.IRON_HOE);
        list.add(Material.STONE_HOE);
        list.add(Material.NETHERITE_PICKAXE);
        list.add(Material.NETHERITE_SHOVEL);
        list.add(Material.NETHERITE_AXE);
        list.add(Material.NETHERITE_HOE);
        return list.contains(material);
    }

}
