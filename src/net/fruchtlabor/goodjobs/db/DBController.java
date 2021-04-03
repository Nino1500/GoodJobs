package net.fruchtlabor.goodjobs.db;

import net.fruchtlabor.goodjobs.fill.BreakLogContainer;
import org.bukkit.craftbukkit.libs.it.unimi.dsi.fastutil.Hash;
import org.bukkit.plugin.Plugin;

import java.sql.*;
import java.util.*;

public class DBController {
    Plugin plugin;

    public DBController(Plugin plugin) {
        this.plugin = plugin;
    }

    public Connection connect() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/test","test", "test");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public void createTable(String jobname){
        String sql = "CREATE TABLE IF NOT EXISTS " + jobname +
                "(uuid VARCHAR(255) not NULL," +
                "lvl int," +
                "exp double," +
                "plvl int," +
                "PRIMARY KEY (uuid))";

        try {
            Connection conn = connect();
            Statement stmt = conn.createStatement();
            // create a new table
            stmt.execute(sql);
            conn.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void logBreakTable(){
        String sql = "CREATE TABLE IF NOT EXISTS breaklog " +
                "(uuid VARCHAR(255) not NULL," +
                "diamond INTEGER, " +
                "emerald INTEGER, " +
                "ancient INTEGER, " +
                "gold INTEGER, " +
                "stone INTEGER, " +
                "PRIMARY KEY (uuid))";
        try {
            Connection conn = connect();
            Statement stmt = conn.createStatement();
            // create a new table
            stmt.execute(sql);
            conn.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void logPlaceTable(){
        String sql = "CREATE TABLE IF NOT EXISTS placelog " +
                "(location VARCHAR(512) not NULL," +
                "world VARCHAR(32)," +
                "PRIMARY KEY (location))";
        try {
            Connection conn = connect();
            Statement stmt = conn.createStatement();
            // create a new table
            stmt.execute(sql);
            conn.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void insertBreakLog(HashMap<String, BreakLogContainer> map){

        String sql = "INSERT INTO breaklog VALUES (?,?,?,?,?,?) ON DUPLICATE KEY UPDATE " +
                "diamond= ?, emerald= ?, ancient= ?, gold= ?, stone= ?";

        try {
            Connection connection = connect();
            PreparedStatement statement = connection.prepareStatement(sql);

            for (Map.Entry<String, BreakLogContainer> entry : map.entrySet()){
                statement.setString(1, entry.getKey());
                statement.setInt(2, entry.getValue().getDiamond());
                statement.setInt(3, entry.getValue().getEmerald());
                statement.setInt(4, entry.getValue().getAncient());
                statement.setInt(5, entry.getValue().getGold());
                statement.setInt(6, entry.getValue().getStone());
                statement.setInt(7, entry.getValue().getDiamond());
                statement.setInt(8, entry.getValue().getEmerald());
                statement.setInt(9, entry.getValue().getAncient());
                statement.setInt(10, entry.getValue().getGold());
                statement.setInt(11, entry.getValue().getStone());
                statement.addBatch();
            }

            statement.executeBatch();
            connection.close();
            statement.close();

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void insertPlaceLog(HashMap<String, String> map){
        String sql = "INSERT INTO placelog VALUES (?,?) ON DUPLICATE KEY UPDATE " +
                "world= ?";

        try {
            Connection connection = connect();
            PreparedStatement statement = connection.prepareStatement(sql);
            for (Map.Entry<String,String> entry : map.entrySet()){
                statement.setString(1, entry.getKey());
                statement.setString(2, entry.getValue());
                statement.setString(3, entry.getValue());
                statement.addBatch();
            }
            statement.executeBatch();
            connection.close();
            statement.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public HashMap<String, BreakLogContainer> getBreakLog(){
        HashMap<String, BreakLogContainer> map = new HashMap<>();
        String sql = "SELECT * FROM breaklog";
        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;
        try {
            connection = connect();
            statement = connection.createStatement();
            rs = statement.executeQuery(sql);
            while (rs.next()){
                map.put(rs.getString(1), new BreakLogContainer(rs.getInt(1),
                        rs.getInt(2), rs.getInt(3), rs.getInt(4),
                        rs.getInt(5)));
            }
            return map;
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public HashMap<String, String> getPlaceLog(){
        String sql = "SELECT * FROM placelog";
        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;
        HashMap<String, String> map = new HashMap<>();
        try {
            connection = connect();
            statement = connection.createStatement();
            rs = statement.executeQuery(sql);
            while (rs.next()){
                map.put(rs.getString(1), rs.getString(2));
            }
            return map;
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public ArrayList<JobPlayer> getPlayersByJob(String jobname){
        String sql = "SELECT * FROM "+jobname;
        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;
        ArrayList<JobPlayer> list = new ArrayList<>();
        try {
            connection = connect();
            statement = connection.createStatement();
            rs = statement.executeQuery(sql);
            while (rs.next()){
                list.add(new JobPlayer(jobname, rs.getInt("lvl"), rs.getDouble("exp"), UUID.fromString(rs.getString("uuid")), rs.getInt("plvl")));
            }
            return list;
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            try {
                if(rs != null){
                    rs.close();
                }
                if(statement != null){
                    statement.close();
                }
                if(connection != null){
                    connection.close();
                }
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * Method inserts all players of each job into the db
     * @param list list of each job with its players
     * @param jobname jobname for the right table
     */
    public void insertAllPlayers(ArrayList<JobPlayer> list, String jobname){
        try {
            String sql = "INSERT INTO "+jobname+" VALUES (?,?,?,?) ON DUPLICATE KEY UPDATE lvl= ?, exp= ?, plvl= ?";
            Connection connection = connect();
            PreparedStatement statement = connection.prepareStatement(sql);
            for (JobPlayer jobPlayer : list) {
                statement.setString(1, jobPlayer.getUuid().toString());
                statement.setInt(2, jobPlayer.getLvl());
                statement.setDouble(3, jobPlayer.getExp());
                statement.setInt(4, jobPlayer.getPlvl());
                statement.setInt(5, jobPlayer.getLvl());
                statement.setDouble(6, jobPlayer.getExp());
                statement.setInt(7, jobPlayer.getPlvl());
                statement.addBatch();
            }
            int[] numUpdates = statement.executeBatch();
            System.out.println("Saved: "+numUpdates.length+" Players! Job: "+jobname);
            connection.close();
            statement.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
