package net.fruchtlabor.goodjobs.listeners;

import net.fruchtlabor.goodjobs.GoodJobs;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

import java.util.ArrayList;
import java.util.List;

public class BlockBreakPlaceProtection implements Listener {
	@EventHandler
	public void onPlace(BlockPlaceEvent event){
		GoodJobs.logController.insertPlaceLog(event.getBlock().getLocation());
	}
}
