package View;

import Controller.CardController;
import Model.Card;
import Model.Game;
import Model.SQL;
import Model.User;

import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class MainMenu {
    public static void run(Scanner scanner) {
        System.out.println("Main menu");
        if (User.getLoggedInUser().isFirstLogin()) {
            starterPack(User.getLoggedInUser());
            User.getLoggedInUser().setFirstLogin(false);
        }
        while (true) {
            System.out.println("1- start game");
            System.out.println("2- cards");
            System.out.println("3- game history");
            System.out.println("4- shop");
            System.out.println("5- profile");
            System.out.println("6- logout");
            String input = scanner.nextLine();
            //user register
            if (input.equals("1")) {
                GameMenu.run(scanner);
            } else if (input.equals("2")) {
                User myUser = User.getLoggedInUser();
                for (Card card : myUser.getCards()) {
                    System.out.print("type: " + card.getType() + " | name: " + card.getName() + " | HP: " + card.getPower() + " | damage: " + card.getDamage() + " | duration: " + card.getDuration());
                }
            } else if (input.equals("3")) {
                gameHistory(User.getLoggedInUser());
            } else if (input.equals("4")) {
                ShopMenu.run(scanner);
            } else if (input.equals("5")) {
                ProfileMenu.run(scanner);
            } else if (input.equals("6")) {
                User.setLoggedInUser(null);
                System.out.println("logged out successfully");
                LoginMenu LM = new LoginMenu();
                LM.run(scanner);
            }
            //show menu name
            else if (input.matches(Regexes.showMenuName.pattern))
                System.out.println("main menu");
                //exit
            else if (input.equals("exit"))
                System.exit(0);
                //invalid command
            else
                System.out.println("invalid command");
        }
    }

    private static void starterPack(User user) {
        Random random = new Random();
        CardController CC = new CardController();
        for (int i = 0; i < 20; i++) {
            int x = random.nextInt(CC.getCards().size());
            Card card = CC.getCardByNumber(x) ;
            if (user.getCard(card) != null) {
                i--;
            } else {
                user.addCards(card);
                try {
                    Class.forName("org.sqlite.JDBC");
                    Connection c = SQL.c;
                    Statement stmt = SQL.stmt;
                    c.setAutoCommit(false);
                    String addCard = "INSERT INTO " + user.getUsername() + " (CardName,CardType,Power,Damage,Duration,UpgradeLevel,UpgradeCost,Level,TypeNumber) " + "VALUES ('" + card.getName() + "', 'normal', " + card.getPower() + " , " + card.getDamage() + " , " + card.getDuration() + " , " + card.getUpgradeLevel() + " , " + card.getUpgradeCost() + " , " + card.getLevel() + " , " + card.getTypeNumber() + " );";
                    String editFL = "UPDATE User SET FL = false WHERE Username = '" + user.getUsername() + "'";
                    stmt.executeUpdate(addCard);
                    stmt.executeUpdate(editFL);
                    c.commit();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static void gameHistory(User user) {
        for (Game game : Game.games) {
            if (game.Player1.equals(user.getUsername()) || game.Player2.equals(user.getUsername())) {
                System.out.println("player 1 : " + game.Player1);
                System.out.println("player 2 : " + game.Player2);
                System.out.println("winner : " + game.winner.getUsername());
                System.out.println("date : " + game.Date);
                System.out.println("XP reward : " + game.XPReward);
                System.out.println("gold reward : " + game.goldReward);
            }
        }
    }
}
