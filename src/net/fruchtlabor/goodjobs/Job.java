package net.fruchtlabor.goodjobs;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;

import java.util.ArrayList;
import java.util.List;

public class Job {
    private int max_level;
    private String name;
    private ChatColor color;
    private int id;
    private Material gui;
    private String description;
    private List<BreakProg_items> breaklist;

    public Job(final ConfigurationSection section){
        this.max_level = section.getInt("max_level");
        this.name = section.getString("name");
        this.color = ChatColor.valueOf(section.getString("color"));
        this.id = section.getInt("job_id");
        this.gui = Material.matchMaterial(section.getString("gui"));
        this.description = section.getString("description");
        this.breaklist = constBreakList(section.getStringList("break"));

    }

    public List<BreakProg_items> constBreakList(List<String> break_list){
        List<BreakProg_items> list = new ArrayList<>();
        for (String s : break_list){
            String spl[] = s.split("-");
            Material material = Material.matchMaterial(spl[0]);
            double exp = Double.parseDouble(spl[1]);
            int limit = Integer.parseInt(spl[2]);
            list.add(new BreakProg_items(limit, material, exp));
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
}
