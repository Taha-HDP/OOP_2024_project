package View;
import Controller.UserController;
import Model.Card;
import Model.User;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainMenu {
    public static void run(Scanner scanner) {
        System.out.println("Main menu");
        System.out.println("1- start game");
        System.out.println("2- cards");
        System.out.println("3- game history");
        System.out.println("4- shop");
        System.out.println("5- profile");
        System.out.println("6- logout");
        if(User.getLoggedInUser().isFirstLogin()){
            starterPack(User.getLoggedInUser());
            User.getLoggedInUser().setFirstLogin(false);
        }
        UserController UC = new UserController();
        while (true) {
            String input = scanner.nextLine();
            //user register
            if (input.equals("start game")) {
                GameMenu.run(scanner) ;
            }else if (input.equals("cards")) {
                User myUser = User.getLoggedInUser() ;
                for(Card card : myUser.getCards()){
                    System.out.print(card.getType() + " | " + card.getName() + " | " + card.getDamage() + " | " + card.getHP() + " | " + card.getDuration());
                }
            }else if (input.equals("game history")) {
                //game history
            }else if (input.equals("shop")) {
                ShopMenu.run(scanner) ;
            }else if (input.equals("profile")) {
                ProfileMenu.run(scanner) ;
            }else if (input.equals("logout")) {
                User.setLoggedInUser(null);
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
    private static Matcher getCommandMatcher(String input, String regex) {
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(input);
    }
    private static void starterPack(User user){
        // 20 kart dade shavad ;
    }
}
