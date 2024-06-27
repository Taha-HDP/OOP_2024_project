package View;

import Controller.UserController;
import Model.Card;
import Model.User;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GameMenu {
    private static User user2;
    private static final User user1 = User.getLoggedInUser();
    private static int map1[]= new int[21] ;
    private static int map2[] = new int[21] ;
    private static int charNumber1=0 , charNumber2=0 ;
    private static int loginAttempt = 0 , betPrice=0;
    private static long startTime = 0;
    private static ArrayList<Card> randomCard1 = new ArrayList<>() ;
    private static ArrayList<Card> randomCard2 = new ArrayList<>() ;
    public static void run(Scanner scanner) {
        System.out.println("entered game menu");
        while (true) {
            System.out.println("select mode :\n1- 1V1\n2- bet");
            String input = scanner.nextLine();
            //1V1
            if (input.equals("1"))
                startGame(scanner,false);
            //bet
            else if (input.equals("2"))
                startGame(scanner,true);
            //back
            else if (input.equals("back"))
                break;
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

    public static boolean login2thPlayer(Scanner scanner) {
        System.out.println("login another player :");
        while (true) {
            String input = scanner.nextLine();
            if (input.matches(Regexes.userLogin.pattern)) {
                Matcher matcher = getCommandMatcher(input, Regexes.userLogin.pattern);
                if (userLogin(matcher)) {
                    return true;
                }
            } else if (input.equals("back")) {
                return false;
            }
        }
    }

    private static boolean userLogin(Matcher matcher) {
        User myUser = User.getLoggedInUser();
        matcher.find();
        long elapsedTime = 0;
        if (startTime != 0) {
            elapsedTime = System.currentTimeMillis() / 1000 - startTime;
            if (elapsedTime >= (5L * loginAttempt)) {
                startTime = 0;
                elapsedTime = 0;
            } else {
                System.out.println("try again in (" + ((5L * loginAttempt) - elapsedTime) + ") seconds");
                return false;
            }
        }
        String username = matcher.group("username");
        String password = matcher.group("password");
        UserController UC = new UserController();
        User loggedInUser = UC.login(username, password);
        User.setLoggedInUser(myUser);
        if (loggedInUser != null) {
            System.out.println("logged in successfully !");
            loginAttempt = 0;
            startTime = 0;
            user2 = loggedInUser;
            return true;
        } else {
            loginAttempt++;
            startTime = System.currentTimeMillis() / 1000;
            elapsedTime = System.currentTimeMillis() / 1000 - startTime;
            System.out.println("try again in (" + ((5L * loginAttempt) - elapsedTime) + ") seconds");
            return false;
        }
    }

    public static void startGame(Scanner scanner , boolean bet) {
        if (login2thPlayer(scanner)) {
            if(bet){
                while (true){
                    System.out.println("select your bet amount :");
                    String betAmount = scanner.nextLine();
                    if(betAmount.equals("back"))
                        return;
                    else if(!betAmount.matches("\\d+"))
                        System.out.println("invalid input");
                    else{
                        int amount = Integer.parseInt(betAmount) ;
                        if(user2.getGold()<amount )
                            System.out.println("user 2 dont have enough gold");
                        else if(user1.getGold()<amount )
                            System.out.println("user 1 dont have enough gold");
                        else{
                            System.out.println("bet amount accepted");
                            user1.setGold(user1.getGold()-amount);
                            user2.setGold(user2.getGold()-amount);
                            betPrice = amount ;
                            break;
                        }
                    }
                }
            }
            while (true) {
                System.out.println("select your character");
                System.out.println("1- character 1\n2- character 2\n3- character 3\n4- character 4\n5- done");
                String input = scanner.nextLine();
                if (input.matches(Regexes.selectCharacter.pattern)) {
                    Matcher matcher = getCommandMatcher(input, Regexes.selectCharacter.pattern);
                    matcher.find();
                    if(Integer.parseInt(matcher.group("characterNumber"))>5 || Integer.parseInt(matcher.group("characterNumber"))<1){
                        System.out.println("invalid number for character");
                    }else if(matcher.group("characterNumber").equals("5")){
                        if(charNumber1==0 || charNumber2==0){
                            System.out.println("both need to select character");
                        }else{
                            printData(scanner) ;
                        }
                    }else if(matcher.group("user").equals("user1")){
                        charNumber1 = Integer.parseInt(matcher.group("characterNumber")) ;
                    }else if(matcher.group("user").equals("user2")){
                        charNumber2 = Integer.parseInt(matcher.group("characterNumber")) ;
                    }else{
                        System.out.println("invalid user");
                    }
                }else if (input.equals("back")) {
                    user2 = null;
                    break;
                } else {
                    System.out.println("invalid command");
                }
            }
        }
    }
    public static void printData(Scanner scanner){
        user1.setHP(100);
        user2.setHP(100);
        int round1=4 , round2=4 ;
        //------------------------------ show map
        printMap(map1) ;
        printMap(map2) ;
        //------------------------------ show HP
        System.out.println("User 1 rounds : " + user1.getHP());
        System.out.println("User 2 rounds : " + user2.getHP());
        //------------------------------ show Rounds
        System.out.println("User 1 HP : " + round1);
        System.out.println("User 2 HP : " + round2);
        //------------------------------ show character
        System.out.println("User 1 character : " + charNumber1);
        System.out.println("User 2 character : " + charNumber2);
        //------------------------------ show cards
        showRandomCard(user1) ;
        showRandomCard(user2) ;
    }
    public static void printMap(int[] numbers){
        Random random = new Random();
        for (int i = 0; i < 21; i++) {
            numbers[i] = i + 1;
        }
        numbers[random.nextInt(21)] = 0;
        for (int number : numbers) {
            if(number==0)
                System.out.println("X ");
            else
                System.out.print(number + " ");
        }
    }
    public static void showRandomCard(User user){
        Random random = new Random();
        for(int i=0 ; i<5 ; i++){
            int x = random.nextInt(user.getCards().size()) ;
            if(user.getFromRandomCard(user.getCardByNumber(x))!=null){
                i-- ;
            }else {
                Card card = user.getCardByNumber(x) ;
                user.randomCards.add(card);
                System.out.println(i + "- type: " + card.getType() + " | name: " + card.getName() + " | cost: " + card.getUpgradeCost() + " | HP: " + card.getHP() + " | damage: " + card.getDamage() + " | duration: " + card.getDuration());
            }
        }
    }
    private void generateRandomCard(User user){
        Random random = new Random();
        int x = random.nextInt(user.getCards().size()) ;
        user.randomCards.add(user.getCardByNumber(x)) ;
    }

}
