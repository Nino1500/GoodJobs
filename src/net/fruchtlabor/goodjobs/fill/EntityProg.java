package net.fruchtlabor.goodjobs.fill;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

public class EntityProg {

    private int at_lvl;
    private EntityType entity;
    private double exp;

    public EntityProg(int at_lvl, EntityType entity, double exp) {
        this.at_lvl = at_lvl;
        this.entity = entity;
        this.exp = exp;
    }

    public int getAt_lvl() {
        return at_lvl;
    }

    public EntityType getEntity() {
        return entity;
    }

    public double getExp() {
        return exp;
    }
}
