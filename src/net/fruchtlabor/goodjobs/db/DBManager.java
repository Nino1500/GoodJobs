package net.fruchtlabor.goodjobs.db;

import com.sun.deploy.uitoolkit.impl.awt.AWTWindowFactory;
import net.fruchtlabor.goodjobs.GoodJobs;
import net.fruchtlabor.goodjobs.fill.Job;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

public class DBManager {
	Plugin plugin;
	private final ArrayList<JobPlayer> players;

	public DBManager(Plugin plugin) {
		this.plugin = plugin;
		players = new ArrayList<>();
		getAllPlayers();
		safePeriodic();
	}

	private void safePeriodic(){
		Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
			@Override public void run() {
				safeAllPlayers();
			}
		}, 0L, 1200L);
	}

	public boolean addPlayer(UUID uuid, String jobname, int lvl, double exp, int plvl){
		JobPlayer newplayer = new JobPlayer(jobname, lvl, exp, uuid, plvl);
		if(getByUUID(uuid, jobname) == null){
			players.add(newplayer);
			return true;
		}
		return false;
	}

	public ArrayList<Job> getPlayerJobs(UUID uuid){
		ArrayList<Job> list = new ArrayList<>();
		for (JobPlayer jobPlayer : players){
			if(jobPlayer.uuid.equals(uuid)){
				list.add(GoodJobs.getJobByName(jobPlayer.job));
			}
		}
		return list;
	}

	public ArrayList<JobPlayer> getTop(String job){
		ArrayList<JobPlayer> list = new ArrayList<>();
		for (JobPlayer jobPlayer : players){
			if(jobPlayer.getJob().equalsIgnoreCase(job)){
				list.add(jobPlayer);
			}
		}
		list.sort(Comparator.comparing(JobPlayer::getLvl).reversed());
		return list;
	}

	public JobPlayer getByUUID(UUID uuid, String jobname){
		for (JobPlayer jobPlayer : players){
			if(jobPlayer.getUuid().equals(uuid) && jobPlayer.getJob().equalsIgnoreCase(jobname)){
				return jobPlayer;
			}
		}
		return null;
	}

	private void getAllPlayers(){
		DBController dbController = new DBController(plugin);
		for (Job job : GoodJobs.jobs){
			ArrayList<JobPlayer> list = dbController.getPlayersByJob(job.getName());
			if(list != null){
				players.addAll(list);
			}
		}
	}

	public void safeAllPlayers(){
		DBController dbController = new DBController(plugin);
		ArrayList<JobPlayer> insertList = new ArrayList<>();
		for (Job job : GoodJobs.jobs){
			for (JobPlayer jobPlayer : players){
				if(job.getName().equalsIgnoreCase(jobPlayer.getJob())){
					insertList.add(jobPlayer);
				}
			}
			dbController.insertAllPlayers(insertList, job.getName());
			insertList.clear();
		}
	}

}
