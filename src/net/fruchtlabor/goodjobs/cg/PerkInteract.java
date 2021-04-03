package net.fruchtlabor.goodjobs.cg;

import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.SmartInventory;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import net.fruchtlabor.goodjobs.GoodJobs;
import net.fruchtlabor.goodjobs.db.JobPlayer;
import net.fruchtlabor.goodjobs.fill.Job;
import net.fruchtlabor.goodjobs.perks.Perk;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class PerkInteract implements InventoryProvider {

	private static Job jobx;
	private static Perk perkx;
	private static JobPlayer jobPlayerx;

	public static final SmartInventory PerkInteractInv = SmartInventory.builder()
			.id("perkinteractinv")
			.provider(new PerkInteract())
			.size(3,9)
			.title("Perk-Kaufen-Verkaufen")
			.build();

	@Override public void init(Player player, InventoryContents inventoryContents) {
		inventoryContents.fillBorders(ClickableItem.empty(new ItemStack(Material.GRAY_STAINED_GLASS_PANE)));
		ItemStack buy = new ItemStack(Material.GREEN_BANNER);
		ItemMeta buymeta = buy.getItemMeta();
		List<String> buylore = new ArrayList<>();
		buymeta.setDisplayName(ChatColor.GREEN+"Perk kaufen?");
		buylore.add(ChatColor.GOLD+"Kostet: "+ChatColor.WHITE+perkx.getCost());
		buymeta.setLore(buylore);
		buy.setItemMeta(buymeta);
		ItemStack sell = new ItemStack(Material.RED_BANNER);
		ItemMeta sellmeta = sell.getItemMeta();
		sellmeta.setDisplayName(ChatColor.RED+"Perk verkaufen?");
		List<String> selllore = new ArrayList<>();
		selllore.add(ChatColor.GOLD+"Du bekommst: "+ChatColor.WHITE+perkx.getCost()+ChatColor.GOLD+" Punkte zurück!");
		selllore.add(ChatColor.RED+"Du bezahlst aber: "+ChatColor.WHITE+perkx.getCost()*10+ChatColor.RED+" Beeren dafür!");
		sellmeta.setLore(selllore);
		sell.setItemMeta(sellmeta);

		inventoryContents.set(1,3,ClickableItem.of(buy, new Consumer<InventoryClickEvent>() {
			@Override public void accept(InventoryClickEvent inventoryClickEvent) {
				if(player.hasPermission("Job.perks."+perkx.getPerkname())){
					player.sendMessage(ChatColor.DARK_GREEN+"Du hast den Perk schon!");
				}else{
					if(jobPlayerx == null){
						player.sendMessage(ChatColor.DARK_GREEN+"Du hast den Job nicht!");
					}else{
						if(jobPlayerx.removePlvl(perkx.getCost())){
							Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user "+player.getName()+" permission set Job.perks."+perkx.getPerkname());
						}else{
							player.sendMessage(ChatColor.DARK_GREEN+"Du hast zuwenig Punkte!");
						}
					}
				}
			}
		}));

		inventoryContents.set(1, 5, ClickableItem.of(sell, new Consumer<InventoryClickEvent>() {
			@Override public void accept(InventoryClickEvent inventoryClickEvent) {
				if(player.hasPermission("Job.perks."+perkx.getPerkname())){
					if(jobPlayerx == null){
						player.sendMessage(ChatColor.DARK_GREEN+"Du hast den Job nicht!");
					}else{
						double money = GoodJobs.econ.getBalance(player);
						if(money - perkx.getCost()*10 >= 0){
							GoodJobs.econ.withdrawPlayer(player, perkx.getCost()*10);
							jobPlayerx.addPlvl(perkx.getCost());
							Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user "+player.getName()+" permission unset Job.perks."+perkx.getPerkname());
						}else{
							player.sendMessage(ChatColor.DARK_GREEN+"Du hast zuwenig Geld dafür!");
						}
					}
				}else{
					player.sendMessage(ChatColor.DARK_GREEN+"Du hast den Perk nicht!");
				}
			}
		}));

		ItemStack back = new ItemStack(Material.BARRIER);
		ItemMeta backmeta = back.getItemMeta();
		backmeta.setDisplayName(ChatColor.RED+"Zurück");
		back.setItemMeta(backmeta);
		inventoryContents.set(2, 0, ClickableItem.of(back, new Consumer<InventoryClickEvent>() {
			@Override
			public void accept(InventoryClickEvent inventoryClickEvent) {
				PerkGui.setjob(jobx);
				PerkGui.PerkInv.open(player);
			}
		}));
	}

	@Override public void update(Player player, InventoryContents inventoryContents) {

	}

	public static void setjob(Job job){
		jobx = job;
	}
	public static void setPerkx(Perk perk) { perkx = perk; }
	public static void setJobPlayerx(JobPlayer jobPlayer) { jobPlayerx = jobPlayer; }

}
