package Model;

import java.sql.*;

public class SQL {
    public static Connection c;
    public static Statement stmt;
    public static void setup() {

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:test.db");
            System.out.println("Opened database successfully");
            stmt = c.createStatement();
            String cardTable = "CREATE TABLE CARD" + "(CardName char(15) primary key," + "CardType char(50)," + "Power int," + "Damage int," + "Duration int," + "UpgradeLevel int," + "UpgradeCost int," + "Level int," + "TypeNumber int)";
            String userTable = "CREATE TABLE USER" + "(Username char(50) primary key," + "Password char(50)," + "Nickname char(50)," + "Email char(50)," + "FatherName char(50)," + "Color char(50)," + "Pet char(50)," + "XP int," + "Level int," + "Gold int,"+ "FL boolean)";
            String gameTable = "CREATE TABLE Game" + "(Player1 char(50)," + "Player2 char(50),"+ "Date char(50),"+ "XP char(50)," + "Gold char(50),"  + "Winner char(50))";
            stmt.executeUpdate(cardTable);
            stmt.executeUpdate(userTable);
            stmt.executeUpdate(gameTable);
        } catch (Exception e) {
            //e.printStackTrace();
        }
    }
}