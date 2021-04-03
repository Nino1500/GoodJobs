package net.fruchtlabor.goodjobs.db;

import net.fruchtlabor.goodjobs.fill.BreakLogContainer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class LogController {

	Plugin plugin;
	private HashMap<String,String> placeLog = new HashMap<>();
	private HashMap<String, BreakLogContainer> breakLog = new HashMap<>();

	public LogController(Plugin plugin) {
		this.plugin = plugin;
		getPlaceLog();
		getBreakLog();
		safePeriodic();
	}

	private void safePeriodic(){
		Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
			@Override public void run() {
				safePlaceLog();
				safeBreakLog();
			}
		}, 0L, 12000L);
	}

	private void getPlaceLog(){
		DBController dbController = new DBController(plugin);
		placeLog = dbController.getPlaceLog();
	}

	private void getBreakLog(){
		DBController dbController = new DBController(plugin);
		breakLog = dbController.getBreakLog();
	}

	public BreakLogContainer getBreakLog(UUID uuid){
		for (Map.Entry<String, BreakLogContainer> entry : breakLog.entrySet()){
			if(entry.getKey().equalsIgnoreCase(uuid.toString())){
				return entry.getValue();
			}
		}
		return null;
	}

	public void insertBreakLog(UUID uuid, BreakLogContainer breakLogContainer){
		breakLog.put(uuid.toString(), breakLogContainer);
	}

	public boolean checkBreakLog(UUID uuid){
		for (Map.Entry<String, BreakLogContainer> entry : breakLog.entrySet()){
			if(entry.getKey().equalsIgnoreCase(uuid.toString())){
				return true;
			}
		}
		return false;
	}

	public void addInto(UUID uuid, int dia, int emerald, int ancient, int gold, int stone){
		if(checkBreakLog(uuid)){
			for (Map.Entry<String, BreakLogContainer> entry : breakLog.entrySet()){
				if(entry.getKey().equalsIgnoreCase(uuid.toString())){
					entry.getValue().addDiamond(dia);
					entry.getValue().addEmerald(emerald);
					entry.getValue().addGold(gold);
					entry.getValue().addAncient(ancient);
					entry.getValue().addStone(stone);
					break;
				}
			}
		}else{
			insertBreakLog(uuid, new BreakLogContainer(dia, emerald, ancient, gold, stone));
		}
	}

	public void safeBreakLog(){
		DBController dbController = new DBController(plugin);
		dbController.insertBreakLog(breakLog);
	}

	public void safePlaceLog(){
		DBController dbController = new DBController(plugin);
		dbController.insertPlaceLog(placeLog);
	}

	public boolean checkPlaceLog(Location location){
		return placeLog.containsKey(location.toString());
	}

	public boolean insertPlaceLog(Location location){
		if(!checkPlaceLog(location)){
			placeLog.put(location.toString(), location.getWorld().getName());
			return true;
		}
		return false;
	}

}
