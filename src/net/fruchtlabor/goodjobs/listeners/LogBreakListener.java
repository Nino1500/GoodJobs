package net.fruchtlabor.goodjobs.listeners;

import net.fruchtlabor.goodjobs.GoodJobs;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.Locale;
import java.util.UUID;

public class LogBreakListener implements Listener {
	@EventHandler
	public void onBreak(BlockBreakEvent event){

		Material material = event.getBlock().getType();
		UUID uuid = event.getPlayer().getUniqueId();

		if(material.equals(Material.STONE)){
			GoodJobs.logController.addInto(uuid, 0, 0,0,0,1);
		}else if(material.equals(Material.DIAMOND_ORE)){
			GoodJobs.logController.addInto(uuid, 1, 0,0,0,0);
		}else if(material.equals(Material.EMERALD_ORE)){
			GoodJobs.logController.addInto(uuid, 0, 1,0,0,0);
		}else if(material.equals(Material.ANCIENT_DEBRIS)){
			GoodJobs.logController.addInto(uuid, 0, 0,1,0,0);
		}else if(material.equals(Material.GOLD_ORE) || material.equals(Material.NETHER_GOLD_ORE)){
			GoodJobs.logController.addInto(uuid, 0, 0,0,1,0);
		}
	}
}
