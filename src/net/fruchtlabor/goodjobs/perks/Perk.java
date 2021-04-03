package net.fruchtlabor.goodjobs.perks;

import net.fruchtlabor.goodjobs.fill.Job;
import org.bukkit.Material;

public class Perk {
	private String description;
	private String perkname;
	private int cost;
	private Material gui;
	private Job job;
	private Perk needed_Perk;

	public Perk(String description, String perkname, int cost, Material gui, Job job, Perk needed_Perk) {
		this.description = description;
		this.perkname = perkname;
		this.cost = cost;
		this.gui = gui;
		this.job = job;
		this.needed_Perk = needed_Perk;
	}

	public String getDescription() {
		return description;
	}

	public String getPerkname() {
		return perkname;
	}

	public int getCost() {
		return cost;
	}

	public Material getGui() {
		return gui;
	}

	public Job getJob() {
		return job;
	}

	public Perk getNeeded_Perk() {
		return needed_Perk;
	}
}
