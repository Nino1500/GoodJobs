package net.fruchtlabor.goodjobs.fill;

public class BreakLogContainer {
	private int diamond;
	private int emerald;
	private int ancient;
	private int gold;
	private int stone;

	public BreakLogContainer(int diamond, int emerald, int ancient, int gold, int stone) {
		this.diamond = diamond;
		this.emerald = emerald;
		this.ancient = ancient;
		this.gold = gold;
		this.stone = stone;
	}

	public int getDiamond() {
		return diamond;
	}

	public int getEmerald() {
		return emerald;
	}

	public int getAncient() {
		return ancient;
	}

	public int getGold() {
		return gold;
	}

	public int getStone() {
		return stone;
	}

	public void addDiamond(int diamond) {
		this.diamond += diamond;
	}

	public void addEmerald(int emerald) {
		this.emerald += emerald;
	}

	public void addAncient(int ancient) {
		this.ancient += ancient;
	}

	public void addGold(int gold) {
		this.gold += gold;
	}

	public void addStone(int stone) {
		this.stone += stone;
	}
}
