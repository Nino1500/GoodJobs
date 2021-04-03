package net.fruchtlabor.goodjobs.cg;

import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.SmartInventory;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import net.fruchtlabor.goodjobs.db.JobPlayer;
import net.fruchtlabor.goodjobs.fill.Job;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.UUID;
import java.util.function.Consumer;

public class TopGui implements InventoryProvider {

	public static ArrayList<JobPlayer> list;
	public static Job jobx;

	public static final SmartInventory TopInv = SmartInventory.builder()
			.id("topinv")
			.provider(new TopGui())
			.size(3,9)
			.title("TopListe")
			.build();

	public static void setList(ArrayList<JobPlayer> list) {
		TopGui.list = list;
	}
	public static void setjob(Job job){
		jobx = job;
	}

	@Override public void init(Player player, InventoryContents inventoryContents) {

		inventoryContents.fillBorders(ClickableItem.empty(new ItemStack(Material.GRAY_STAINED_GLASS_PANE)));

		int x = 0;
		for (int i = 0; i < list.size(); i++) {
			if(x>9){
				break;
			}
			x++;
			Player player1 = Bukkit.getPlayer(list.get(i).getUuid());
			if(player1!=null){
				inventoryContents.add(ClickableItem.empty(getHead(player1, list.get(i).getLvl())));
			}
		}

		ItemStack back = new ItemStack(Material.BARRIER);
		ItemMeta backmeta = back.getItemMeta();
		backmeta.setDisplayName(ChatColor.RED+"Zurück");
		back.setItemMeta(backmeta);
		inventoryContents.set(2, 0, ClickableItem.of(back, new Consumer<InventoryClickEvent>() {
			@Override
			public void accept(InventoryClickEvent inventoryClickEvent) {
				JobSpecificInv.setjob(jobx);
				JobSpecificInv.SpecificInv.open(player);
			}
		}));
	}

	public static ItemStack getHead(Player player, int lvl) {
		int lifePlayer = (int) player.getHealth();
		ItemStack item = new ItemStack(Material.PLAYER_HEAD);
		SkullMeta skull = (SkullMeta) item.getItemMeta();
		skull.setDisplayName(ChatColor.GOLD+player.getName());
		ArrayList<String> lore = new ArrayList<String>();
		lore.add(ChatColor.GOLD+"Level: "+ChatColor.WHITE+lvl);
		skull.setLore(lore);
		skull.setOwningPlayer(player);
		item.setItemMeta(skull);
		return item;
	}

	@Override public void update(Player player, InventoryContents inventoryContents) {

	}

}
