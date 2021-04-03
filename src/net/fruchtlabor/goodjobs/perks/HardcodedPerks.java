package net.fruchtlabor.goodjobs.perks;

import net.fruchtlabor.goodjobs.GoodJobs;
import net.fruchtlabor.goodjobs.fill.Job;
import org.bukkit.Material;

import javax.print.attribute.standard.JobName;
import java.util.ArrayList;

public class HardcodedPerks {

	private final ArrayList<Perk> perks;

	public HardcodedPerks() {
		perks = new ArrayList<>();
		fillPerks();
	}

	private void fillPerks(){
		//Start of Minenarbeiter
		Job miner = GoodJobs.getJobByName("Minenarbeiter");
		perks.add(new Perk("Stein gibt mehr Experience", "Steinsammler", 3, Material.COBBLESTONE, miner, null));
		perks.add(new Perk("Schmilzt Erze automatisch", "Erzschmelze", 5, Material.FURNACE, miner, null));
		perks.add(new Perk("Erze können doppeldroppen", "Verdopplung", 10, Material.OAK_SIGN, miner, null));
		perks.add(new Perk("Öfters bekommt man BonusExp", "ExpBoost", 5, Material.EXPERIENCE_BOTTLE, miner, null));
		perks.add(new Perk("Sortiert Erze im Inventar", "Sortierer", 10, Material.CHEST, miner, null));
		perks.add(new Perk("Beim Abbauen von Stein -> manchmal Haste I", "Schnell, schneller, am schnellsten", 6, Material.NETHERITE_PICKAXE, miner, null));
		perks.add(new Perk("Erze können andere Erze beinhalten", "Ist das wirklich Kohle?", 20, Material.COAL, miner, null));
		perks.add(new Perk("Manchmal spawnt eine Kiste mit Loot", "Ein Schatz?", 25, Material.CHEST_MINECART, miner, null));
		perks.add(new Perk("Bessere Schätze", "Ein Schatz? nur in besser?", 40, Material.EXPERIENCE_BOTTLE, miner, null));
		perks.add(new Perk("Backpack mit 3*9 Plätzen", "Backpack", 15, Material.EXPERIENCE_BOTTLE, miner, null));
		//End of Minenarbeiter
		//Start of Fischer
		Job fish = GoodJobs.getJobByName("Fischer");
		perks.add(new Perk("Ab und zu mehr Exp", "Exp wichtig!", 3, Material.FISHING_ROD, fish, null));
		perks.add(new Perk("Holz & nützliche Item Drops", "Loot I", 8, Material.DIAMOND, fish, null));
		perks.add(new Perk("Ich hab eine Truhe gefischt?", "Schatzfischer", 15, Material.CHEST, fish, null));
		perks.add(new Perk("Emerald & Dia Drops", "Loot II", 15, Material.EMERALD, fish, null));
		perks.add(new Perk("Was zum ... ein Kopf?!", "Headfishing", 15, Material.PLAYER_HEAD, fish, null));
		perks.add(new Perk("Bücher lvl 1-2 Drops", "Bücherwurm I", 20, Material.ENCHANTED_BOOK, fish, null));
		perks.add(new Perk("Bücher lvl 3 Drops", "Bücherwurm II", 30, Material.ENCHANTED_BOOK, fish, null));
		perks.add(new Perk("Mendingbücher", "Mendingfishing", 45, Material.ENCHANTED_BOOK, fish, null));
		perks.add(new Perk("Filtert nutzlose Items (Sticks etc.)", "Weniger Schrott", 10, Material.DIRT, fish, null));
		perks.add(new Perk("Eisen & Gold drops", "Loot III", 15, Material.IRON_INGOT, fish, null));
		//End of Fischer
		//Start of Abenteurer
		Job aben = GoodJobs.getJobByName("Abenteurer");
		perks.add(new Perk("Findet ab und zu ein Item im Sand", "Schatzsucher", 5, Material.WOODEN_SHOVEL, aben, null));
		perks.add(new Perk("Versch. Drops in Sand & Erde", "Maulwurf I", 10, Material.BUCKET, aben, null));
		perks.add(new Perk("Im Jungle & Wüste kann man Schätze finden!", "", 10, Material.IRON_BOOTS, aben, null));
		perks.add(new Perk("Laufen/Erkunden bringt Experience", "", 10, Material.EXPERIENCE_BOTTLE, aben, null));
		perks.add(new Perk("Experience dropps aus Sand & Erde", "", 10, Material.STONE_SHOVEL, aben, null));
		//perks.add(new Perk("", ""));
		//perks.add(new Perk("", ""));
		//perks.add(new Perk("", ""));
		//End of Abenteurer
		//Start of Holzfäller
		Job holz = GoodJobs.getJobByName("Holzfaeller");
		perks.add(new Perk("Droppt Baumstämme manchmal doppelt", "Doppeldropper I", 5, Material.STONE_AXE, holz, null));
		perks.add(new Perk("Baumstämme können Exp droppen", "Exp drops", 10, Material.IRON_AXE, holz, null));
		perks.add(new Perk("Baumfäller zu 5%", "Timber I", 25, Material.GOLDEN_AXE, holz, null));
		perks.add(new Perk("Droppt Baumstämme öfters doppelt", "Doppeldropper II", 35, Material.DIAMOND_AXE, holz, null));
		perks.add(new Perk("Baumfäller zu 10%", "Timber II", 45, Material.NETHERITE_AXE, holz, null));
		perks.add(new Perk("Blätter werden instant zerstört", "Raupen/frau/mann", 55, Material.OAK_LEAVES, holz, null));
		perks.add(new Perk("Baumfäller zu 15%", "Timber III", 65, Material.NETHERITE_AXE, holz, null));
		perks.add(new Perk("Findet Kisten in den Bäumen", "Meisterhacker", 75, Material.CHEST, holz, null));
		//End of Holzfäller
		//Start of Farmer
		Job farmer = GoodJobs.getJobByName("Farmer");
		perks.add(new Perk("Rechtsklick mit Sense, farmt Crops", "Sensen/frau/mann", 5, Material.IRON_HOE, farmer, null));
		perks.add(new Perk("Mit der Sense erntest du mehr", "Sensen/frau/mann II", 10, Material.DIAMOND_HOE, farmer, null));
		perks.add(new Perk("Das Töten von Mobs kann Items droppen", "Monsterjäger", 25, Material.CREEPER_HEAD, farmer, null));
		perks.add(new Perk("Weniger Hunger", "Ein eiserner Magen", 35, Material.BREAD, farmer, null));
		perks.add(new Perk("Durch Sensen/frau/mann, bekommst du loot", "Cropdrops I", 45, Material.DIAMOND, farmer, null));
		perks.add(new Perk("Crops können Erfahrung droppen", "Erfahrenes Ernten", 55, Material.EXPERIENCE_BOTTLE, farmer, null));
		perks.add(new Perk("Die Sense hat keine Abnutzung", "Gekonntes sensen", 65, Material.NETHERITE_HOE, farmer, null));
		perks.add(new Perk("Wolle kann doppelt droppen", "Schäfer", 75, Material.SHEARS, farmer, null));
		//End of Farmer
	}

	public ArrayList<Perk> getPerkByJob(String job){
		ArrayList<Perk> perklist = new ArrayList<>();
		for (Perk perk : perks){
			if(perk.getJob().getName().equalsIgnoreCase(job)){
				perklist.add(perk);
			}
		}
		return perklist;
	}
	public Perk getPerkByName(String perkname){
		for (Perk perk : perks){
			if(perk.getPerkname().equalsIgnoreCase(perkname)){
				return perk;
			}
		}
		return null;
	}



}
