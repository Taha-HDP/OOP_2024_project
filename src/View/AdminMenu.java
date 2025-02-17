package View;

import Controller.CardController;
import Controller.UserController;
import Model.Card;
import Model.SQL;
import Model.User;

import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AdminMenu {
    public static void run(Scanner scanner) {
        System.out.println("entered Admin menu");
        while (true) {
            System.out.println("1- add card -n (cardName) -p (power 10-100) -d (duration 1-5) -pd (damage 10-50) -ul (upgradeLevel) -uc (upgradeCost) -t (type 1-4)");
            System.out.println("2- edit card");
            System.out.println("3- remove card");
            System.out.println("4- all users");
            System.out.println("5- logout");
            String input = scanner.nextLine();
            if (input.matches(Regexes.addCard.pattern)) {
                Matcher matcher = getCommandMatcher(input, Regexes.addCard.pattern);
                addCard(matcher);
            } else if (input.equals("remove card")) {
                removeCard(scanner);
            } else if (input.equals("edit card")) {
                editCard(scanner);
            } else if (input.equals("all users")) {
                for (User user : UserController.users) {
                    System.out.println(user.getUsername() + " | " + user.getLevel() + " | " + user.getGold());
                }
            } else if (input.matches(Regexes.showMenuName.pattern))
                System.out.println("login menu");
            else if (input.equals("exit"))
                System.exit(0);
            else if (input.equals("back"))
                break;
            else {
                System.out.println("invalid command");
            }
        }
    }

    private static Matcher getCommandMatcher(String input, String regex) {
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(input);
    }

    static void addCard(Matcher matcher) {
        matcher.find();
        String name = matcher.group("name");
        int power = Integer.parseInt(matcher.group("power"));
        int duration = Integer.parseInt(matcher.group("duration"));
        int playerDamage = Integer.parseInt(matcher.group("playerDamage"));
        int upgradeLevel = Integer.parseInt(matcher.group("upgradeLevel"));
        int upgradeLeCost = Integer.parseInt(matcher.group("UpgradeCost"));
        int typeNumber = Integer.parseInt(matcher.group("typeNumber"));
        CardController CC = new CardController();
        if (CC.getCardByName(name) != null) {
            System.out.println("this name is used for another card !");
            return;
        }
        if (power < 10 || power > 100) {
            System.out.println("invalid value for power");
            return;
        }
        if (duration < 1 || duration > 5) {
            System.out.println("invalid value for duration");
            return;
        }
        if (playerDamage < 10 || playerDamage > 50) {
            System.out.println("invalid value for player damage");
            return;
        }
        if (typeNumber < 1 || typeNumber > 4) {
            System.out.println("type Number must be between 1 and 5");
        }
        CC.addCard(new Card(name, "normal", power, playerDamage, duration, upgradeLevel, upgradeLeCost, typeNumber));
        System.out.println("successfully added");
    }

    static void editCard(Scanner scanner) {
        CardController CC = new CardController();
        ArrayList<Card> cards = CC.getCards();
        for (int i = 0; i < cards.size(); i++) {
            System.out.println((i + 1) + "- " + cards.get(i).getName());
        }
        int cardNumber;
        while (true) {
            String input = scanner.nextLine();
            if (input.equals("back")) {
                run(scanner);
                return;
            }
            if (!input.matches("\\d+")) {
                System.out.println("invalid input");
                continue;
            }
            cardNumber = Integer.parseInt(input);
            if (cardNumber > cards.size() || cardNumber < 1) {
                System.out.println("invalid number , try again");
            } else {
                break;
            }
        }
        Card mainCard = cards.get(cardNumber - 1);
        String name = mainCard.getName();
        int power = mainCard.getPower();
        int duration = mainCard.getDamage();
        int playerDamage = mainCard.getDuration();
        int upgradeLevel = mainCard.getUpgradeLevel();
        int upgradeLeCost = mainCard.getUpgradeCost();
        System.out.println("1- name : " + name);
        System.out.println("2- power : " + power);
        System.out.println("3- Damage : " + duration);
        System.out.println("4- Duration : " + playerDamage);
        System.out.println("5- Upgrade Level : " + upgradeLevel);
        System.out.println("6- Upgrade Cost : " + upgradeLeCost);
        System.out.println("7- done");
        int partNumber;
        while (true) {
            String input = scanner.nextLine();
            if (input.equals("back")) {
                run(scanner);
                return;
            }
            if (!input.matches("\\d+")) {
                System.out.println("invalid input");
                continue;
            }
            partNumber = Integer.parseInt(input);
            if (partNumber > 7 || partNumber < 1) {
                System.out.println("invalid number , try again");
            } else {
                switch (partNumber) {
                    case 1:
                        while (true) {
                            input = scanner.nextLine();
                            if (input.equals("back")) {
                                run(scanner);
                                return;
                            }
                            if (CC.getCardByName(input) == null) {
                                name = input;
                                System.out.println("accepted");
                                break;
                            } else {
                                if (!CC.getCardByName(input).equals(mainCard))
                                    System.out.println("this name is already used !");
                            }
                        }
                        break;
                    case 2:
                        while (true) {
                            input = scanner.nextLine();
                            if (input.equals("back")) {
                                run(scanner);
                                return;
                            }
                            if (!input.matches("\\d+")) {
                                System.out.println("invalid input");
                                continue;
                            }
                            if (Integer.parseInt(input) < 10 || Integer.parseInt(input) > 100) {
                                System.out.println("invalid value for HP");
                            } else {
                                power = (Integer.parseInt(input));
                                System.out.println("accepted");
                                break;
                            }
                        }
                        break;
                    case 3:
                        while (true) {
                            input = scanner.nextLine();
                            if (input.equals("back")) {
                                run(scanner);
                                return;
                            }
                            if (!input.matches("\\d+")) {
                                System.out.println("invalid input");
                                continue;
                            }
                            if (Integer.parseInt(input) < 10 || Integer.parseInt(input) > 50) {
                                System.out.println("invalid value for player damage");
                            } else {
                                playerDamage = Integer.parseInt(input);
                                System.out.println("accepted");
                                break;
                            }
                        }
                        break;
                    case 4:
                        while (true) {
                            input = scanner.nextLine();
                            if (input.equals("back")) {
                                run(scanner);
                                return;
                            }
                            if (!input.matches("\\d+")) {
                                System.out.println("invalid input");
                                continue;
                            }
                            if (Integer.parseInt(input) < 1 || Integer.parseInt(input) > 5) {
                                System.out.println("invalid value for duration");
                            } else {
                                duration = Integer.parseInt(input);
                                System.out.println("accepted");
                                break;
                            }
                        }
                        break;
                    case 5: {
                        while (true) {
                            input = scanner.nextLine();
                            if (input.equals("back")) {
                                run(scanner);
                                return;
                            }
                            if (!input.matches("\\d+")) {
                                System.out.println("invalid input");
                                continue;
                            }
                            upgradeLevel = Integer.parseInt(input);
                            System.out.println("accepted");
                            break;
                        }
                        break;
                    }
                    case 6:
                        while (true) {
                            input = scanner.nextLine();
                            if (input.equals("back")) {
                                run(scanner);
                                return;
                            }
                            if (!input.matches("\\d+")) {
                                System.out.println("invalid input");
                                continue;
                            }
                            upgradeLeCost = Integer.parseInt(input);
                            System.out.println("accepted");
                            break;
                        }
                        break;
                    case 7:
                        System.out.println("are you sure you want to edit this card?(y/n)");
                        String answer = scanner.nextLine();
                        if (answer.equals("y")) {
                            try {
                                Class.forName("org.sqlite.JDBC");
                                Connection c = SQL.c;
                                Statement stmt = SQL.stmt;
                                c.setAutoCommit(false);
                                String editName = "UPDATE CARD SET CardName = '" + name + "' WHERE CardName = '" + mainCard.getName() + "'";
                                String editPower = "UPDATE CARD SET Power = '" + power + "' WHERE CardName = '" + mainCard.getName() + "'";
                                String editDamage = "UPDATE CARD SET Damage = '" + playerDamage + "' WHERE CardName = '" + mainCard.getName() + "'";
                                String editDuration = "UPDATE CARD SET Duration = '" + duration + "' WHERE CardName = '" + mainCard.getName() + "'";
                                String editUpgradeLevel = "UPDATE CARD SET UpgradeLevel = '" + upgradeLevel + "' WHERE CardName = '" + mainCard.getName() + "'";
                                String editUpgradeCost = "UPDATE CARD SET UpgradeCost = '" + upgradeLeCost + "' WHERE CardName = '" + mainCard.getName() + "'";
                                stmt.executeUpdate(editName);
                                stmt.executeUpdate(editPower);
                                stmt.executeUpdate(editDamage);
                                stmt.executeUpdate(editDuration);
                                stmt.executeUpdate(editUpgradeLevel);
                                stmt.executeUpdate(editUpgradeCost);
                                c.commit();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            mainCard.setName(name);
                            mainCard.setPower(power);
                            mainCard.setDamage(playerDamage);
                            mainCard.setDuration(duration);
                            mainCard.setUpgradeLevel(upgradeLevel);
                            mainCard.setUpgradeCost(upgradeLeCost);
                            System.out.println("successfully edited !");
                        }
                        run(scanner);
                        return;
                }
            }
        }

    }

    static void removeCard(Scanner scanner) {
        CardController CC = new CardController();
        ArrayList<Card> cards = CC.getCards();
        for (int i = 0; i < cards.size(); i++) {
            System.out.println((i + 1) + "- " + cards.get(i).getName());
        }
        int cardNumber;
        while (true) {
            String input = scanner.nextLine();
            if (input.equals("back")) {
                run(scanner);
                return;
            }
            if (!input.matches("\\d")) {
                System.out.println("invalid input");
                continue;
            }
            cardNumber = Integer.parseInt(input);
            if (cardNumber > cards.size() || cardNumber < 1) {
                System.out.println("invalid number , try again");
            } else {
                break;
            }
        }
        System.out.println("are you sure  you want to delete this card ?(y/n)");
        String answer = scanner.nextLine();
        if (answer.equals("y")) {
            Card mainCard = cards.get(cardNumber - 1);
            CC.removeCard(mainCard);
            System.out.println("successfully removed");
        }
        run(scanner);
    }
}
