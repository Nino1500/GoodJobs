package net.fruchtlabor.goodjobs.cg;

import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.SmartInventory;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import net.fruchtlabor.goodjobs.GoodJobs;
import net.fruchtlabor.goodjobs.db.DBController;
import net.fruchtlabor.goodjobs.fill.Job;
import net.fruchtlabor.goodjobs.db.JobPlayer;
import net.fruchtlabor.goodjobs.fill.Notify;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;

public class JobSpecificInv implements InventoryProvider {

	private static Job jobx;

	public static final SmartInventory SpecificInv = SmartInventory.builder()
			.id("specificinv")
			.provider(new JobSpecificInv())
			.size(3,9)
			.title("Job")
			.build();

	@Override
	public void init(Player player, InventoryContents inventoryContents) {
		ItemStack perks = new ItemStack(Material.OAK_SIGN);
		ItemMeta meta = perks.getItemMeta();
		meta.setDisplayName(ChatColor.GOLD + "Perks");
		perks.setItemMeta(meta);

		ItemStack info = new ItemStack(Material.COMPASS);
		ItemMeta infometa = info.getItemMeta();
		infometa.setDisplayName(ChatColor.GREEN + "Info");
		info.setItemMeta(infometa);

		ItemStack top = new ItemStack(Material.CLOCK);
		ItemMeta topmeta = top.getItemMeta();
		topmeta.setDisplayName(ChatColor.GOLD + "Job-Platzierungen");
		top.setItemMeta(topmeta);

		inventoryContents.set(1, 2, ClickableItem.of(perks, new Consumer<InventoryClickEvent>() {
			@Override
			public void accept(InventoryClickEvent inventoryClickEvent) {
				PerkGui.setjob(jobx);
				PerkGui.PerkInv.open(player);
			}
		}));
		inventoryContents.set(1, 4, ClickableItem.of(info, new Consumer<InventoryClickEvent>() {
			@Override
			public void accept(InventoryClickEvent inventoryClickEvent) {
				JobPlayer jobPlayer = GoodJobs.dbManager.getByUUID(player.getUniqueId(), jobx.getName());
				if(jobPlayer != null){
					player.sendMessage(jobx.getColor()+jobx.getName()+ChatColor.GOLD+" Stats \n");
					int job_lvl = jobPlayer.getLvl();
					double neededAmount = 150*job_lvl+(job_lvl*job_lvl*4);
					player.sendMessage(ChatColor.GREEN+"Erfahrung: "+ChatColor.GRAY+Math.round(jobPlayer.getExp())+ChatColor.WHITE+" / "+ChatColor.RED+neededAmount+ChatColor.WHITE+" Level: "+jobPlayer.getLvl());
				}
				player.closeInventory();
			}
		}));
		inventoryContents.set(1, 6, ClickableItem.of(top, new Consumer<InventoryClickEvent>() {
			@Override
			public void accept(InventoryClickEvent inventoryClickEvent) {
				TopGui.setjob(jobx);
				TopGui.setList(GoodJobs.dbManager.getTop(jobx.getName()));
				TopGui.TopInv.open(player);
			}
		}));
		inventoryContents.fillBorders(ClickableItem.empty(new ItemStack(Material.GRAY_STAINED_GLASS_PANE)));

		ItemStack back = new ItemStack(Material.BARRIER);
		ItemMeta backmeta = back.getItemMeta();
		backmeta.setDisplayName(ChatColor.RED+"Zurück");
		back.setItemMeta(backmeta);
		inventoryContents.set(2, 0, ClickableItem.of(back, new Consumer<InventoryClickEvent>() {
			@Override
			public void accept(InventoryClickEvent inventoryClickEvent) {
				JobInv.INVENTORY.open(player);
			}
		}));
	}
	@Override
	public void update(Player player, InventoryContents inventoryContents) {

	}

	public static void setjob(Job job){
		jobx = job;
	}

}
