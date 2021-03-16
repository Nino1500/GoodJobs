package net.fruchtlabor.goodjobs.fill;

import org.bukkit.Material;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.EntityType;

public class BreakProg_items {

    private int at_lvl;
    private Material material;
    private double exp;

    public BreakProg_items(int at_lvl, Material material, double exp) {
        this.at_lvl = at_lvl;
        this.material = material;
        this.exp = exp;
    }

    public int getAt_lvl() {
        return at_lvl;
    }

    public Material getMaterial() {
        return material;
    }

    public double getExp() {
        return exp;
    }
}
