package net.fruchtlabor.goodjobs.listeners;

import net.fruchtlabor.goodjobs.GoodJobs;
import net.fruchtlabor.goodjobs.db.JobPlayer;
import net.fruchtlabor.goodjobs.fill.EntityProg;
import net.fruchtlabor.goodjobs.fill.Job;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;

public class MobKillListener implements Listener {
    Plugin plugin;

    public MobKillListener(Plugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onKill(EntityDeathEvent event){
        if(event.getEntity().getKiller() != null){
            Player player = event.getEntity().getKiller();
            ArrayList<Job> jobs = GoodJobs.dbManager.getPlayerJobs(player.getUniqueId());
            for (Job job : jobs){
                for (EntityProg entityProg : job.getEntityProgList()){
                    if(entityProg.getEntity().name().equalsIgnoreCase(event.getEntity().getName())){
                        JobPlayer jobPlayer = GoodJobs.dbManager.getByUUID(player.getUniqueId(), job.getName());
                        if(entityProg.getAt_lvl() <= jobPlayer.getLvl() && isItemUsed(player.getInventory().getItemInMainHand().getType())){
                            if(event.getEntity().getFallDistance() < 5 && event.getEntity().getFireTicks() < 2){
                                jobPlayer.addExp(entityProg.getExp());
                            }
                        }
                    }
                }
            }
        }
    }
    public boolean isItemUsed(Material material){
        List<Material> list = new ArrayList<>();
        list.add(Material.NETHERITE_AXE);
        list.add(Material.DIAMOND_AXE);
        list.add(Material.IRON_AXE);
        list.add(Material.STONE_AXE);
        list.add(Material.NETHERITE_SWORD);
        list.add(Material.STONE_SWORD);
        list.add(Material.DIAMOND_SWORD);
        list.add(Material.IRON_SWORD);
        list.add(Material.BOW);
        return list.contains(material);
    }
}
