package net.fruchtlabor.goodjobs.db;

import net.fruchtlabor.goodjobs.GoodJobs;
import net.fruchtlabor.goodjobs.db.DBController;
import net.fruchtlabor.goodjobs.fill.Notify;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.util.UUID;

public class JobPlayer {
    String job;
    int lvl;
    double exp;
    UUID uuid;
    int plvl;

    public JobPlayer(String job, int lvl, double exp, UUID uuid, int plvl) {
        this.job = job;
        this.lvl = lvl;
        this.exp = exp;
        this.uuid = uuid;
        this.plvl = plvl;
    }

    public String getJob() {
        return job;
    }

    public int getLvl() {
        return lvl;
    }

    public double getExp() {
        return exp;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void addExp(double experience) {
        if(Bukkit.getPlayer(uuid).hasPermission("Job.multi")){
            experience = experience*GoodJobs.mult;
        }
        if(Bukkit.getPlayer(uuid).hasPermission("Job.notify.exp")){
            Notify notify = new Notify(GoodJobs.plugin);
            notify.notifyExpGain(experience, uuid, job);
        }
        this.exp += experience;
        int needed = 150*lvl+(lvl*lvl*4);
        if(this.exp >= needed){
            addLvl(1);
            this.exp = this.exp - needed;
        }
    }

    public void addLvl(int lvl) {
        Notify notify = new Notify(GoodJobs.plugin);
        this.lvl += lvl;
        addPlvl(lvl);
        notify.notifyLevelUp(this.lvl, uuid, job);
    }

    public void addPlvl(int perklvl){
        this.plvl += perklvl;
    }

    public int getPlvl() {
        return plvl;
    }

    public boolean removePlvl(int points){
        if(this.plvl - points >= 0){
            this.plvl = this.plvl - points;
            return true;
        }else{
            return false;
        }
    }
}
