package net.fruchtlabor.goodjobs.fill;

import net.fruchtlabor.goodjobs.fill.BreakProg_items;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class Job {
    private int max_level;
    private String name;
    private ChatColor color;
    private int id;
    private Material gui;
    private String description;
    private double shear;
    private List<BreakProg_items> breaklist;
    private List<FishProg> fishlist;
    private List<EntityProg> entityProgList;

    public Job(final ConfigurationSection section) {
        this.max_level = section.getInt("max_level");
        this.name = section.getString("name");
        this.color = ChatColor.valueOf(section.getString("color"));
        this.id = section.getInt("job_id");
        this.gui = Material.matchMaterial(section.getString("gui"));
        this.description = section.getString("description");
        this.shear = section.getDouble("shear");
        this.breaklist = constBreakList(section.getStringList("break"));
        this.entityProgList = constEntityProgs(section.getStringList("mobkill"));
        this.fishlist = constFishList(section.getStringList("fish"));
    }

    public List<EntityProg> constEntityProgs(List<String> entites) {
        List<EntityProg> list = new ArrayList<>();
        for (String s : entites) {
            String[] spl = s.split("-");
            EntityType entity = EntityType.valueOf(spl[0].toUpperCase());
            double exp = Double.parseDouble(spl[1]);
            int limit = Integer.parseInt(spl[2]);
            list.add(new EntityProg(limit, entity, exp));
        }
        return list;
    }

    public List<BreakProg_items> constBreakList(List<String> break_list) {
        List<BreakProg_items> list = new ArrayList<>();
        for (String s : break_list) {
            String[] spl = s.split("-");
            Material material = Material.matchMaterial(spl[0]);
            double exp = Double.parseDouble(spl[1]);
            int limit = Integer.parseInt(spl[2]);
            list.add(new BreakProg_items(limit, material, exp));
        }
        return list;
    }

    public List<FishProg> constFishList(List<String> fishl) {
        List<FishProg> list = new ArrayList<>();
        for (String s : fishl) {
            String[] spl = s.split("-");
            Material material = Material.matchMaterial(spl[0]);
            double exp = Double.parseDouble(spl[1]);
            int limit = Integer.parseInt(spl[2]);
            list.add(new FishProg(limit, material, exp));
        }
        return list;
    }

    public int getMax_level() {
        return max_level;
    }

    public String getName() {
        return name;
    }

    public ChatColor getColor() {
        return color;
    }

    public int getId() {
        return id;
    }

    public Material getGui() {
        return gui;
    }

    public String getDescription() {
        return description;
    }

    public List<BreakProg_items> getBreaklist() {
        return breaklist;
    }

    public List<FishProg> getFishlist() {
        return fishlist;
    }

    public double getShear() {
        return shear;
    }

    public List<EntityProg> getEntityProgList() {
        return entityProgList;
    }
}
