package View;

import Controller.CardController;
import Model.Card;
import Model.SQL;
import Model.User;

import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

public class ShopMenu {
    public static void run(Scanner scanner) {
        System.out.println("entered profile menu");
        label:
        while (true) {
            System.out.println("1- all cards");
            System.out.println("2- upgradable cards");
            String input = scanner.nextLine();
            switch (input) {
                case "1":
                    buyCard(scanner);
                    break;
                case "2":
                    upgradeCard(scanner);
                    break;
                case "back":
                    break label;
                default:
                    System.out.println("invalid command");
                    break;
            }
        }
    }

    private static void buyCard(Scanner scanner) {
        CardController CC = new CardController();
        User myUser = User.getLoggedInUser();
        ArrayList<Card> visibleCard = new ArrayList<>();
        boolean check;
        int i = 1;
        for (Card card : CC.getCards()) {
            check = true;
            for (Card userCard : myUser.getCards()) {
                if (card.equals(userCard)) {
                    check = false;
                    break;
                }
            }
            if (check) {
                System.out.println(i + "- type: " + card.getType() + " | name: " + card.getName() + " | cost: " + card.getUpgradeCost() + " | HP: " + card.getPower() + " | damage: " + card.getDamage() + " | duration: " + card.getDuration());
                i++;
                visibleCard.add(card);
            }
        }
        while (true) {
            String input = scanner.nextLine();
            if (input.equals("back")) {
                break;
            }
            if (!input.matches("\\d+")) {
                System.out.println("invalid input");
            } else if (Integer.parseInt(input) < 1 || Integer.parseInt(input) > visibleCard.size()) {
                System.out.println("invalid input");
            } else {
                int cardNumber = Integer.parseInt(input) - 1;
                Card card = visibleCard.get(cardNumber);
                if (myUser.getCard(card) != null) {
                    System.out.println("you already have this card !");
                } else if (myUser.getGold() >= card.getUpgradeCost()) {
                    int gold = User.getLoggedInUser().getGold() - card.getUpgradeCost();
                    try {
                        Class.forName("org.sqlite.JDBC");
                        Connection c = SQL.c;
                        Statement stmt = SQL.stmt;
                        c.setAutoCommit(false);
                        String editGold = "UPDATE User SET Gold = '" + gold + "' WHERE username = '" + myUser.getUsername() + "'";
                        String addCard = "INSERT INTO " + myUser.getUsername() + " (CardName,CardType,Power,Damage,Duration,UpgradeLevel,UpgradeCost,Level,TypeNumber) " + "VALUES ('" + card.getName() + "', 'normal', " + card.getPower() + " , " + card.getDamage() + " , " + card.getDuration() + " , " + card.getUpgradeLevel() + " , " + card.getUpgradeCost() + " , " + card.getLevel() + " , " + card.getTypeNumber() + " );";
                        stmt.executeUpdate(editGold);
                        stmt.executeUpdate(addCard);
                        c.commit();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    myUser.setGold(gold);
                    myUser.addCards(card);
                    System.out.println("successfully added");
                } else {
                    System.out.println("you dont have enough gold");
                }
                break;
            }
        }
    }

    private static void upgradeCard(Scanner scanner) {
        User myUser = User.getLoggedInUser();
        int i = 1;
        for (Card card : myUser.getCards()) {
            System.out.println(i + "- type: " + card.getType() + " | name: " + card.getName() + " | cost: " + card.getUpgradeCost() * 1.25 * (card.getLevel() + 1) + " | HP: " + card.getPower() + " | damage: " + card.getDamage() + " | duration: " + card.getDuration() + " | level for upgrade: " + card.getUpgradeLevel());
            i++;
        }
        while (true) {
            String input = scanner.nextLine();
            if (input.equals("back")) {
                break;
            }
            if (!input.matches("\\d+")) {
                System.out.println("invalid input");
            } else if (Integer.parseInt(input) < 1 || Integer.parseInt(input) > myUser.getCards().size()) {
                System.out.println("invalid input");
            } else {
                int cardNumber = Integer.parseInt(input) - 1;
                Card mainCard = myUser.getCards().get(cardNumber);
                if (myUser.getLevel() < mainCard.getUpgradeLevel()) {
                    System.out.println("you need to level up to level: " + mainCard.getUpgradeLevel() + "then you can upgrade your card");
                } else if (myUser.getGold() >= mainCard.getUpgradeCost() * 1.25 * (mainCard.getLevel() + 1)) {
                    int newLevel = myUser.getCard(mainCard).getLevel() + 1;
                    int newGold = (int) (User.getLoggedInUser().getGold() - (mainCard.getUpgradeCost() * 1.25 * (mainCard.getLevel() + 1))) ;
                    try {
                        Class.forName("org.sqlite.JDBC");
                        Connection c = SQL.c;
                        Statement stmt = SQL.stmt;
                        c.setAutoCommit(false);
                        String editLevel = "UPDATE " + myUser.getUsername() + " SET Level = '" + newLevel + "' WHERE CardName = '" + mainCard.getName() + "'";
                        String editGold = "UPDATE User SET Gold = '" + newGold + "' WHERE username = '" + myUser.getUsername() + "'";
                        stmt.executeUpdate(editLevel);
                        stmt.executeUpdate(editGold);
                        c.commit();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    myUser.setGold(newGold);
                    myUser.getCard(mainCard).setLevel(newLevel);
                    System.out.println("successfully upgraded");
                } else {
                    System.out.println("you dont have enough gold");
                }
                break;
            }
        }
    }
}
