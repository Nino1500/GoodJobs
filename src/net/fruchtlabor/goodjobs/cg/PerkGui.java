package net.fruchtlabor.goodjobs.cg;

import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.SmartInventory;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import net.fruchtlabor.goodjobs.GoodJobs;
import net.fruchtlabor.goodjobs.db.DBManager;
import net.fruchtlabor.goodjobs.db.JobPlayer;
import net.fruchtlabor.goodjobs.fill.Job;
import net.fruchtlabor.goodjobs.perks.HardcodedPerks;
import net.fruchtlabor.goodjobs.perks.Perk;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class PerkGui implements InventoryProvider {

	public static Job jobx;

	public static final SmartInventory PerkInv = SmartInventory.builder()
			.id("perkinv")
			.provider(new PerkGui())
			.size(5,9)
			.title("Perks")
			.build();

	@Override public void init(Player player, InventoryContents inventoryContents) {
		inventoryContents.fillBorders(ClickableItem.empty(new ItemStack(Material.GRAY_STAINED_GLASS_PANE)));
		HardcodedPerks hardcodedPerks = new HardcodedPerks();
		ArrayList<Perk> perks = hardcodedPerks.getPerkByJob(jobx.getName());
		ItemStack itemStack = new ItemStack(Material.SUNFLOWER);
		ItemMeta itemMeta = itemStack.getItemMeta();
		itemMeta.setDisplayName(ChatColor.GOLD+"Perk-Punkte");
		List<String> lore = new ArrayList<>();
		JobPlayer jobPlayer = GoodJobs.dbManager.getByUUID(player.getUniqueId(), jobx.getName());
		int plvl = 0;
		if(jobPlayer != null){
			plvl = jobPlayer.getPlvl();
		}
		lore.add(ChatColor.WHITE+""+plvl);
		itemMeta.setLore(lore);
		itemStack.setItemMeta(itemMeta);
		inventoryContents.set(0,0,ClickableItem.empty(itemStack));

		for (Perk perk : perks) {
			ItemStack item = new ItemStack(perk.getGui());
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName(perk.getPerkname());
			List<String> itemlore = new ArrayList<>();
			itemlore.add(perk.getDescription());
			itemlore.add(ChatColor.GOLD + "Kostet: " + ChatColor.WHITE + perk.getCost());
			meta.setLore(itemlore);
			item.setItemMeta(meta);
			inventoryContents.add(ClickableItem.of(item, new Consumer<InventoryClickEvent>() {
				@Override public void accept(InventoryClickEvent inventoryClickEvent) {
					PerkInteract.setjob(jobx);
					PerkInteract.setPerkx(perk);
					PerkInteract.setJobPlayerx(jobPlayer);
					PerkInteract.PerkInteractInv.open(player);
				}
			}));
		}

		ItemStack back = new ItemStack(Material.BARRIER);
		ItemMeta backmeta = back.getItemMeta();
		backmeta.setDisplayName(ChatColor.RED+"Zurück");
		back.setItemMeta(backmeta);
		inventoryContents.set(4, 0, ClickableItem.of(back, new Consumer<InventoryClickEvent>() {
			@Override
			public void accept(InventoryClickEvent inventoryClickEvent) {
				JobSpecificInv.setjob(jobx);
				JobSpecificInv.SpecificInv.open(player);
			}
		}));
	}

	@Override public void update(Player player, InventoryContents inventoryContents) {

	}
	public static void setjob(Job job){
		jobx = job;
	}
}
