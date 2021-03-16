package net.fruchtlabor.goodjobs.db;

import net.fruchtlabor.goodjobs.listeners.Notify;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;
import java.util.stream.Collectors;

public class DBController {
    Plugin plugin;
    String dbName;
    String url;
    Connection connection;

    public DBController(Plugin plugin, String dbName) {
        this.plugin = plugin;
        this.dbName = dbName;
        connection = connect();
    }

    public Connection connect() {
        Connection connection = null;
        File dataFolder = new File(plugin.getDataFolder(), dbName+".db");
        if (!dataFolder.exists()){
            try {
                dataFolder.createNewFile();
            } catch (IOException e) {
                plugin.getLogger().log(Level.SEVERE, "File write error: "+dbName+".db");
            }
        }
        try {
            if(connection!=null && !connection.isClosed()){
                return connection;
            }
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:" + dataFolder);
            return connection;
        } catch (SQLException ex) {
            plugin.getLogger().log(Level.SEVERE,"SQLite exception on initialize", ex);
        } catch (ClassNotFoundException ex) {
            plugin.getLogger().log(Level.SEVERE, "You need the SQLite JBDC library. Google it. Put it in /lib folder.");
        }
        return null;
    }

    public void createTable(String jobname){
        String sql = "CREATE TABLE IF NOT EXISTS " + jobname +
                "(uuid VARCHAR(255) not NULL," +
                "lvl int," +
                "exp double," +
                "PRIMARY KEY (uuid))";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    //Player gets the Perm Job.jobname, on the command it inserts the player to the table (if he doesnt exist already)
    public boolean insertPlayer(UUID uuid, String jobname){
        String sql = "INSERT INTO "+jobname+" (uuid, lvl, exp) VALUES (?,?,?)";
        if(!checkIfExists(uuid, jobname)){
            try {
                PreparedStatement stmt = connection.prepareStatement(sql);
                stmt.setString(1, uuid.toString());
                stmt.setInt(2, 1);
                stmt.setDouble(3, 0.0);
                stmt.executeUpdate();
                return true;
            }catch (SQLException e){
                e.printStackTrace();
                Bukkit.getConsoleSender().sendMessage("Error at insertPlayer DBCONTROLLER!!");
                return false;
            }
        }
        return false;
    }
    public boolean checkIfExists(UUID uuid, String jobname){
        String sql = "SELECT * FROM " + jobname;
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if(rs == null){
                return false;
            }
            while (rs.next()){
                if(rs.getString("uuid").equalsIgnoreCase(uuid.toString())){
                    return true;
                }
            }
            return false;
        }catch (SQLException e){
            e.printStackTrace();
            Bukkit.getConsoleSender().sendMessage("Error at checkIfExists DBCONTROLLER!!");
            return false;
        }
    }
    public void addLvl(UUID uuid, String jobname, int lvl){
        lvl = lvl + getLvl(uuid, jobname);
        String sql = "UPDATE " + jobname + "SET lvl = ? WHERE uuid = ?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, lvl);
            stmt.setString(2, uuid.toString());
            stmt.executeUpdate();
            Notify notify = new Notify(plugin);
            notify.notifyLevelUp(lvl, uuid, jobname);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    public void addExp(UUID uuid, String jobname, double exp){
        exp = exp + getExp(uuid, jobname);
        String sql = "UPDATE " + jobname + " SET exp = ? WHERE uuid = ?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setDouble(1, exp);
            stmt.setString(2, uuid.toString());
            stmt.executeUpdate();
            Notify notify = new Notify(plugin);
            notify.notifyExpGain(exp, uuid, jobname);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    public void removeLvl(UUID uuid, String jobname, int lvl){
        lvl = getLvl(uuid, jobname) - lvl;
        if(getLvl(uuid, jobname) > 1 && lvl >= 1){
            String sql = "UPDATE " + jobname + " SET lvl = ? WHERE uuid = ?";
            try {
                PreparedStatement stmt = connection.prepareStatement(sql);
                stmt.setInt(1, lvl);
                stmt.setString(2, uuid.toString());
                stmt.executeUpdate();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
    }
    public void removeExp(UUID uuid, String jobname, double exp){
        exp = getExp(uuid, jobname) - exp;
        if(exp > 0.0){
            String sql = "UPDATE " + jobname + " SET exp = ? WHERE uuid = ?";
            try {
                PreparedStatement stmt = connection.prepareStatement(sql);
                stmt.setDouble(1, exp);
                stmt.setString(2, uuid.toString());
                stmt.executeUpdate();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
    }
    public void deletePlayer(UUID uuid, String jobname){
        String sql = "DELETE FROM " + jobname + " WHERE uuid = ?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, uuid.toString());
            stmt.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    public List<Map.Entry<UUID, Integer>> getTop(String jobname){
        HashMap<UUID, Integer> playermap = new HashMap<>();
        String sql = "SELECT * FROM " + jobname;
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()){
                playermap.put(UUID.fromString(rs.getString("uuid")), rs.getInt("lvl"));
            }
            return playermap.entrySet().stream()
                    .sorted(Map.Entry.comparingByValue())
                    .collect(Collectors.toList());
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }
    public int getLvl(UUID uuid, String jobname){
        String sql = "SELECT lvl FROM "+jobname+" WHERE uuid = ?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, uuid.toString());
            ResultSet rs = stmt.executeQuery();
            return rs.getInt("lvl");
        }catch (SQLException e){
            e.printStackTrace();
        }
        return 0;
    }
    public double getExp(UUID uuid, String jobname){
        String sql = "SELECT exp FROM "+jobname+" WHERE uuid = ?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, uuid.toString());
            ResultSet rs = stmt.executeQuery();
            return rs.getDouble("exp");
        }catch (SQLException e){
            e.printStackTrace();
        }
        return 0.0;
    }


}
