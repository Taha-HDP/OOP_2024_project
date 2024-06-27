package View;

import Model.Card;
import Model.User;

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
                    System.out.print("type: " + card.getType() + " | name: " + card.getName() + " | HP: " + card.getHP() + " | damage: " + card.getDamage() + " | duration: " + card.getDuration());
                }
            } else if (input.equals("3")) {
                gameHistory();
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
        for (int i = 0; i < 20; i++) {
            //user.addCards(random card);
        }
    }

    private static void gameHistory() {

    }
}
