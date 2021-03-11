package net.fruchtlabor.goodjobs;

import net.fruchtlabor.goodjobs.commands.JobsJoin;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.OfflinePlayer;
import org.bukkit.craftbukkit.libs.it.unimi.dsi.fastutil.Hash;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GoodJobs extends JavaPlugin {

    public static int max_jobs = 2;
    public static String msg_jobjoin = "Du bist erfolgreich jobname gejoint!";
    public static String msg_jobjoin_fail = "Du bist dem Job jobname nicht gejoint!";

    public static List<Job> jobs = new ArrayList<>();

    @Override
    public void onEnable() {
        loadjobs(this);
        JobsJoin jobsJoin = new JobsJoin();
        jobsJoin.setPlugin(this);
        this.getCommand("job").setExecutor(jobsJoin);
    }

    public static void loadjobs(Plugin plugin) {
        Startup startup = new Startup(plugin);
        startup.initialize_jobs();
        jobs = startup.jobs;
    }
}
