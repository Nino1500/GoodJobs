package net.fruchtlabor.goodjobs.listeners;

import org.bukkit.plugin.Plugin;

import java.util.UUID;

public class Notify {
    Plugin plugin;

    public Notify(Plugin plugin) {
        this.plugin = plugin;
    }

    public void notifyLevelUp(int level, UUID uuid, String jobname){

    }
    public void notifyExpGain(double exp, UUID uuid, String jobname){

    }
    public void notifyJoin(UUID uuid, String jobname, boolean fail){

    }
    public void notifyLeave(UUID uuid, String jobname, boolean fail){

    }

}
