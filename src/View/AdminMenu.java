package View;

import Controller.CardController;
import Controller.UserController;
import Model.Card;
import Model.User;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AdminMenu {
    public static void run(Scanner scanner) {
        System.out.println("entered Admin menu");
        while (true){
            String input = scanner.nextLine();
            if(input.matches(Regexes.addCard.pattern)){
                Matcher matcher = getCommandMatcher(input, Regexes.addCard.pattern);
                addCard(matcher);
            }else if(input.equals("remove card")){
                removeCard(scanner);
            }else if(input.equals("edit card")){
                editCard(scanner);
            }else if(input.equals("all users")){
                for(User user : UserController.users){
                    System.out.println(user.getUsername() + " | " + user.getLevel() + " | " + user.getGold());
                }
            }else if (input.equals("exit"))
                System.exit(0);
            else{
                System.out.println("invalid command");
            }
        }
    }
    private static Matcher getCommandMatcher(String input, String regex) {
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(input);
    }
    static void addCard(Matcher matcher){
        matcher.find();
        String name = matcher.group("name") ;
        int HP = Integer.valueOf(matcher.group("power")) ;
        int duration = Integer.valueOf(matcher.group("duration")) ;
        int playerDamage = Integer.valueOf(matcher.group("playerDamage")) ;
        int upgradeLevel = Integer.valueOf(matcher.group("upgradeLevel")) ;
        int upgradeLeCost = Integer.valueOf(matcher.group("UpgradeCost")) ;
        CardController CC = new CardController();
        if (CC.getCardByName(name) != null) {
            System.out.println("this name is used for another card !");
            return;
        }
        if(HP<10 || HP>100){
            System.out.println("invalid value for HP");
            return;
        }
        if(duration<1 || duration>5){
            System.out.println("invalid value for duration");
            return;
        }
        if(playerDamage<10 || playerDamage>50){
            System.out.println("invalid value for player damage");
            return;
        }
        CC.addCard(new Card(name,HP,playerDamage,duration,upgradeLevel,upgradeLeCost));
        System.out.println("successfully added");
    }
    static void editCard(Scanner scanner){
        CardController CC = new CardController();
        ArrayList<Card> cards = CC.getCards() ;
        for(int i=0 ; i<cards.size() ; i++){
            System.out.println((i+1) +  "- " + cards.get(i).getName());
        }
        int cardNumber=0 ;
        while(true){
            String input = scanner.nextLine();
            if(input.equals("back")){
                run(scanner) ;
                return;
            }
            if(!input.matches("\\d+")){
                System.out.println("invalid input");
                continue;
            }
            cardNumber = Integer.valueOf(input) ;
            if (cardNumber > cards.size() || cardNumber < 1) {
                System.out.println("invalid number , try again");
            }else{
                break ;
            }
        }
        Card mainCard = cards.get(cardNumber-1) ;
        String name = mainCard.getName() ;
        int HP = mainCard.getHP();
        int duration = mainCard.getDamage();
        int playerDamage = mainCard.getDuration();
        int upgradeLevel = mainCard.getUpgradeLevel();
        int upgradeLeCost = mainCard.getUpgradeLeCost();
        System.out.println("1- name : " + name);
        System.out.println("2- HP : " + HP);
        System.out.println("3- Damage : " + duration);
        System.out.println("4- Duration : " + playerDamage);
        System.out.println("5- Upgrade Level : " + upgradeLevel);
        System.out.println("6- Upgrade Cost : " + upgradeLeCost);
        System.out.println("7- done");
        int partNumber=0 ;
        while(true){
            String input = scanner.nextLine();
            if(input.equals("back")){
                run(scanner) ;
                return;
            }
            if(!input.matches("\\d+")){
                System.out.println("invalid input");
                continue;
            }
            partNumber = Integer.valueOf(input) ;
            if (partNumber > 7 || partNumber < 1) {
                System.out.println("invalid number , try again");
            }else{
                switch (partNumber){
                    case 1:
                        while(true){
                            input = scanner.nextLine();
                            if(input.equals("back")){
                                run(scanner);
                                return;
                            }
                            if(CC.getCardByName(input)==null){
                                name = input ;
                                System.out.println("accepted");
                                break;
                            }else{
                                if(!CC.getCardByName(input).equals(mainCard))
                                    System.out.println("this name is already used !");
                            }
                        }
                        break;
                    case 2:
                        while(true){
                            input = scanner.nextLine();
                            if(input.equals("back")){
                                run(scanner);
                                return;
                            }
                            if(!input.matches("\\d+")){
                                System.out.println("invalid input");
                                continue;
                            }
                            if(Integer.valueOf(input)<10 || Integer.valueOf(input)>100){
                                System.out.println("invalid value for HP");
                            }else{
                                HP = (Integer.valueOf(input)) ;
                                System.out.println("accepted");
                                break;
                            }
                        }
                        break;
                    case 3:
                        while(true){
                            input = scanner.nextLine();
                            if(input.equals("back")){
                                run(scanner);
                                return;
                            }
                            if(!input.matches("\\d+")){
                                System.out.println("invalid input");
                                continue;
                            }
                            if(Integer.valueOf(input)<10 || Integer.valueOf(input)>50){
                                System.out.println("invalid value for player damage");
                            }else{
                                playerDamage = Integer.valueOf(input) ;
                                System.out.println("accepted");
                                break;
                            }
                        }
                        break;
                    case 4:
                        while(true){
                            input = scanner.nextLine();
                            if(input.equals("back")){
                                run(scanner);
                                return;
                            }
                            if(!input.matches("\\d+")){
                                System.out.println("invalid input");
                                continue;
                            }
                            if(Integer.valueOf(input)<1 || Integer.valueOf(input)>5){
                                System.out.println("invalid value for duration");
                            }else{
                                duration = Integer.valueOf(input) ;
                                System.out.println("accepted");
                                break;
                            }
                        }
                        break;
                    case 5: {
                        while (true){
                            input = scanner.nextLine();
                            if (input.equals("back")) {
                                run(scanner);
                                return;
                            }
                            if (!input.matches("\\d+")) {
                                System.out.println("invalid input");
                                continue;
                            }
                            upgradeLevel = Integer.valueOf(input) ;
                            System.out.println("accepted");
                            break;
                        }
                        break;
                    }
                    case 6:
                        while (true){
                            input = scanner.nextLine();
                            if (input.equals("back")) {
                                run(scanner);
                                return;
                            }
                            if (!input.matches("\\d+")) {
                                System.out.println("invalid input");
                                continue;
                            }
                            upgradeLeCost = Integer.valueOf(input) ;
                            System.out.println("accepted");
                            break;
                        }
                        break;
                    case 7:
                        System.out.println("are you sure you want to edit this card?(y/n)");
                        String answer = scanner.nextLine() ;
                        if(answer.equals("y")){
                            mainCard.setName(name);
                            mainCard.setHP(HP);
                            mainCard.setDamage(playerDamage);
                            mainCard.setDuration(duration);
                            mainCard.setUpgradeLevel(upgradeLevel);
                            mainCard.setUpgradeLeCost(upgradeLeCost);
                            System.out.println("successfully edited !");
                        }
                        run(scanner);
                        return;
                }
            }
        }

    }
    static void removeCard(Scanner scanner){
        CardController CC = new CardController();
        ArrayList<Card> cards = CC.getCards() ;
        for(int i=0 ; i<cards.size() ; i++){
            System.out.println((i+1) +  "- " + cards.get(i).getName());
        }
        int cardNumber=0 ;
        while(true){
            String input = scanner.nextLine();
            if(input.equals("back")){
                run(scanner) ;
                return;
            }
            if(!input.matches("\\d")){
                System.out.println("invalid input");
                continue;
            }
            cardNumber = Integer.valueOf(input) ;
            if (cardNumber > cards.size() || cardNumber < 1) {
                System.out.println("invalid number , try again");
            }else{
                break ;
            }
        }
        System.out.println("are you sure  you want to delete this card ?(y/n)");
        String answer = scanner.nextLine() ;
        if(answer.equals("y")){
            Card mainCard = cards.get(cardNumber-1) ;
            CC.removeCard(mainCard);
            System.out.println("successfully removed");
        }
        run(scanner) ;
        return;
    }
}
