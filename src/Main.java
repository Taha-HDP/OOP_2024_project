import Controller.CardController;
import Controller.UserController;
import Model.Card;
import Model.Game;
import Model.SQL;
import Model.User;
import View.LoginMenu;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        LoginMenu LM = new LoginMenu();
        SQL.setup();
        //firstData();
        getData() ;
        LM.run(scanner);
    }
    private static void getData(){
        //get all cards
        try {
            Class.forName("org.sqlite.JDBC");
            SQL.c.setAutoCommit(false);
            ResultSet rs = SQL.stmt.executeQuery("SELECT * FROM CARD;");
            CardController CC = new CardController() ;
            ArrayList<Card> card = new ArrayList<>() ;
            while (rs.next()) {
                String name = rs.getString("CardName");
                String type = rs.getString("CardType");
                int power = rs.getInt("Power");
                int damage = rs.getInt("Damage");
                int duration = rs.getInt("Duration");
                int upgradeLevel = rs.getInt("UpgradeLevel");
                int upgradeCost = rs.getInt("UpgradeCost");
                int typeNumber = rs.getInt("TypeNumber");
                card.add(new Card(name,type,power,damage,duration,upgradeLevel,upgradeCost,typeNumber)) ;
            }
            CC.setCards(card);
        }catch (Exception e){
            e.printStackTrace();
        }
        //get all users
        try {
            Class.forName("org.sqlite.JDBC");
            SQL.c.setAutoCommit(false);
            ResultSet rs = SQL.stmt.executeQuery("SELECT * FROM USER;");
            UserController UC = new UserController() ;
            ArrayList<User> user = new ArrayList<>() ;
            while (rs.next()) {
                String name = rs.getString("Username");
                String password = rs.getString("Password");
                String email = rs.getString("Email");
                String nickname = rs.getString("Nickname");
                String fatherName = rs.getString("FatherName");
                String pet = rs.getString("Pet");
                String color = rs.getString("Color");
                int level = rs.getInt("Level");
                int XP = rs.getInt("XP");
                int gold = rs.getInt("Gold");
                boolean FL = rs.getBoolean("FL");
                User target = new User(name,password,email,nickname,fatherName,color,pet) ;
                target.setGold(gold);
                target.setLevel(level);
                target.setXP(XP);
                target.setFirstLogin(FL);
                user.add(target) ;
            }
            UC.setUsers(user);
        }catch (Exception e){
            e.printStackTrace();
        }
        for(User user : UserController.users){
            try {
                Class.forName("org.sqlite.JDBC");
                SQL.c.setAutoCommit(false);
                ResultSet rs2 = SQL.stmt.executeQuery("SELECT * FROM "+user.getUsername()+";");
                while (rs2.next()){
                    String cardName = rs2.getString("CardName");
                    String type = rs2.getString("CardType");
                    int power = rs2.getInt("Power");
                    int damage = rs2.getInt("Damage");
                    int duration = rs2.getInt("Duration");
                    int upgradeLevel = rs2.getInt("UpgradeLevel");
                    int upgradeCost = rs2.getInt("UpgradeCost");
                    int typeNumber = rs2.getInt("TypeNumber");
                    int cardLevel = rs2.getInt("Level");
                    Card card = new Card(cardName,type,power,damage,duration,upgradeLevel,upgradeCost,typeNumber);
                    card.setLevel(cardLevel);
                    user.addCards(card);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        //get all games
        try {
            Class.forName("org.sqlite.JDBC");
            SQL.c.setAutoCommit(false);
            ResultSet rs = SQL.stmt.executeQuery("SELECT * FROM GAME;");
            UserController UC = new UserController() ;
            while (rs.next()) {
                String Player1 = rs.getString("Player1");
                String Player2 = rs.getString("Player2");
                String Date = rs.getString("Date");
                int XP = rs.getInt("XP");
                int Gold = rs.getInt("Gold");
                String Winner = rs.getString("Winner");
                Game.games.add(new Game(Player1 , Player2 , Date , UC.getByUsername(Winner)  , XP , Gold )) ;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println("ALL DATA RECEIVED ");
    }
    static void firstData(){
        try {
            Class.forName("org.sqlite.JDBC");
            Connection c = SQL.c;
            Statement stmt = SQL.stmt;
            c.setAutoCommit(false);
            String i = "insert into CARD (CardName,CardType,Power,Damage,Duration,UpgradeLevel,UpgradeCost,Level,TypeNumber) "+ "values('Mokhtari'   , 'Normal' , 40 , 24 , 2 , 4 , 400 , 1 , 1);" ;
            stmt.executeUpdate(i);
            i = "insert into CARD (CardName,CardType,Power,Damage,Duration,UpgradeLevel,UpgradeCost,Level,TypeNumber) "+ "values('DolatAbadi' , 'Normal' , 21 , 12 , 3 , 4 , 200 , 1 , 2);" ;
            stmt.executeUpdate(i);
            i = "insert into CARD (CardName,CardType,Power,Damage,Duration,UpgradeLevel,UpgradeCost,Level,TypeNumber) "+ "values('Kazem'      , 'Normal' , 40 , 30 , 2 , 4 , 500 , 1 , 3);" ;
            stmt.executeUpdate(i);
            i = "insert into CARD (CardName,CardType,Power,Damage,Duration,UpgradeLevel,UpgradeCost,Level,TypeNumber) "+ "values('Makian'     , 'Normal' , 40 , 28 , 4 , 4 , 300 , 1 , 4);" ;
            stmt.executeUpdate(i);
            i = "insert into CARD (CardName,CardType,Power,Damage,Duration,UpgradeLevel,UpgradeCost,Level,TypeNumber) "+ "values('Kia'        , 'Normal' , 10 , 10 , 1 , 4 , 200 , 1 , 1);" ;
            stmt.executeUpdate(i);
            i = "insert into CARD (CardName,CardType,Power,Damage,Duration,UpgradeLevel,UpgradeCost,Level,TypeNumber) "+ "values('DrStop'     , 'Normal' , 45 , 50 , 5 , 4 , 200 , 1 , 2);" ;
            stmt.executeUpdate(i);
            i = "insert into CARD (CardName,CardType,Power,Damage,Duration,UpgradeLevel,UpgradeCost,Level,TypeNumber) "+ "values('Hadi'       , 'Normal' , 30 , 24 , 3 , 4 , 300 , 1 , 3);" ;
            stmt.executeUpdate(i);
            i = "insert into CARD (CardName,CardType,Power,Damage,Duration,UpgradeLevel,UpgradeCost,Level,TypeNumber) "+ "values('Sedei'      , 'Normal' , 48 , 40 , 4 , 4 , 400 , 1 , 4);" ;
            stmt.executeUpdate(i);
            i = "insert into CARD (CardName,CardType,Power,Damage,Duration,UpgradeLevel,UpgradeCost,Level,TypeNumber) "+ "values('Mohandes'   , 'Normal' , 39 , 33 , 3 , 4 , 400 , 1 , 1);" ;
            stmt.executeUpdate(i);
            i = "insert into CARD (CardName,CardType,Power,Damage,Duration,UpgradeLevel,UpgradeCost,Level,TypeNumber) "+ "values('Doctor'     , 'Normal' , 32 , 40 , 4 , 4 , 200 , 1 , 2);" ;
            stmt.executeUpdate(i);
            i = "insert into CARD (CardName,CardType,Power,Damage,Duration,UpgradeLevel,UpgradeCost,Level,TypeNumber) "+ "values('Barqi'      , 'Normal' , 36 , 30 , 3 , 4 , 300 , 1 , 3);" ;
            stmt.executeUpdate(i);
            i = "insert into CARD (CardName,CardType,Power,Damage,Duration,UpgradeLevel,UpgradeCost,Level,TypeNumber) "+ "values('Compi'      , 'Normal' , 25 , 15 , 1 , 4 , 400 , 1 , 4);" ;
            stmt.executeUpdate(i);
            i = "insert into CARD (CardName,CardType,Power,Damage,Duration,UpgradeLevel,UpgradeCost,Level,TypeNumber) "+ "values('MecHaqir'   , 'Normal' , 20 , 20 , 2 , 4 , 200 , 1 , 1);" ;
            stmt.executeUpdate(i);
            i = "insert into CARD (CardName,CardType,Power,Damage,Duration,UpgradeLevel,UpgradeCost,Level,TypeNumber) "+ "values('Sharifi'    , 'Normal' , 40 , 40 , 2 , 4 , 500 , 1 , 2);" ;
            stmt.executeUpdate(i);
            i = "insert into CARD (CardName,CardType,Power,Damage,Duration,UpgradeLevel,UpgradeCost,Level,TypeNumber) "+ "values('Mahdi'      , 'Normal' , 30 , 30 , 2 , 4 , 400 , 1 , 3);" ;
            stmt.executeUpdate(i);
            i = "insert into CARD (CardName,CardType,Power,Damage,Duration,UpgradeLevel,UpgradeCost,Level,TypeNumber) "+ "values('Taha'       , 'Normal' , 15 , 10 , 1 , 4 , 200 , 1 , 4);" ;
            stmt.executeUpdate(i);
            i = "insert into CARD (CardName,CardType,Power,Damage,Duration,UpgradeLevel,UpgradeCost,Level,TypeNumber) "+ "values('TorkKhar'   , 'Normal' , 50 , 40 , 5 , 4 , 200 , 1 , 1);" ;
            stmt.executeUpdate(i);
            i = "insert into CARD (CardName,CardType,Power,Damage,Duration,UpgradeLevel,UpgradeCost,Level,TypeNumber) "+ "values('Shovalie'   , 'Normal' , 39 , 36 , 3 , 4 , 300 , 1 , 2);" ;
            stmt.executeUpdate(i);
            i = "insert into CARD (CardName,CardType,Power,Damage,Duration,UpgradeLevel,UpgradeCost,Level,TypeNumber) "+ "values('asqarqasab' , 'Normal' , 40 , 32 , 4 , 4 , 300 , 1 , 3);" ;
            stmt.executeUpdate(i);
            i = "insert into CARD (CardName,CardType,Power,Damage,Duration,UpgradeLevel,UpgradeCost,Level,TypeNumber) "+ "values('Single'     , 'Normal' , 20 , 15 , 1 , 4 , 300 , 1 , 4);" ;
            stmt.executeUpdate(i);

            i = "insert into CARD (CardName,CardType,Power,Damage,Duration,UpgradeLevel,UpgradeCost,Level,TypeNumber) "+ "values('Shield'     , 'Spell' , Null , Null , Null , 4 , 300 , 1 , 0);";
            stmt.executeUpdate(i);
            i = "insert into CARD (CardName,CardType,Power,Damage,Duration,UpgradeLevel,UpgradeCost,Level,TypeNumber) "+ "values('Heal'       , 'Spell' , Null , Null , Null , 4 , 300 , 1 , 0);";
            stmt.executeUpdate(i);
            i = "insert into CARD (CardName,CardType,Power,Damage,Duration,UpgradeLevel,UpgradeCost,Level,TypeNumber) "+ "values('IncreasePow', 'Spell' , Null , Null , Null , 4 , 300 , 1 , 0);";
            stmt.executeUpdate(i);
            i = "insert into CARD (CardName,CardType,Power,Damage,Duration,UpgradeLevel,UpgradeCost,Level,TypeNumber) "+ "values('ChangeHole' , 'Spell' , Null , Null , Null , 4 , 300 , 1 , 0);";
            stmt.executeUpdate(i);
            i = "insert into CARD (CardName,CardType,Power,Damage,Duration,UpgradeLevel,UpgradeCost,Level,TypeNumber) "+ "values('Fixer'      , 'Spell' , Null , Null , Null , 4 , 300 , 1 , 0);";
            stmt.executeUpdate(i);
            i = "insert into CARD (CardName,CardType,Power,Damage,Duration,UpgradeLevel,UpgradeCost,Level,TypeNumber) "+ "values('Roand-1'    , 'Spell' , Null , Null , Null , 4 , 300 , 1 , 0);";
            stmt.executeUpdate(i);
            i = "insert into CARD (CardName,CardType,Power,Damage,Duration,UpgradeLevel,UpgradeCost,Level,TypeNumber) "+ "values('Steal'      , 'Spell' , Null , Null , Null , 4 , 300 , 1 , 0);";
            stmt.executeUpdate(i);
            i = "insert into CARD (CardName,CardType,Power,Damage,Duration,UpgradeLevel,UpgradeCost,Level,TypeNumber) "+ "values('Debilitation', 'Spell' , Null , Null , Null , 4 , 300 , 1 , 0);";
            stmt.executeUpdate(i);
            i = "insert into CARD (CardName,CardType,Power,Damage,Duration,UpgradeLevel,UpgradeCost,Level,TypeNumber) "+ "values('Copy'       , 'Spell' , Null , Null , Null , 4 , 300 , 1 , 0);";
            stmt.executeUpdate(i);
            i = "insert into CARD (CardName,CardType,Power,Damage,Duration,UpgradeLevel,UpgradeCost,Level,TypeNumber) "+ "values('Hide'       , 'Spell' , Null , Null , Null , 4 , 300 , 1 , 0);";
            stmt.executeUpdate(i);
            c.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}