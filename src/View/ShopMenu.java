package View;
import Controller.CardController;
import Model.Card;
import Model.User;
import java.util.ArrayList;
import java.util.Scanner;
public class ShopMenu {
    public static void run(Scanner scanner){
        System.out.println("entered profile menu");
        System.out.println("1- all cards");
        System.out.println("2- upgradable cards");
        while (true) {
            String input = scanner.nextLine();
            if(input.equals("1")){
                buyCard(scanner);
            }else if(input.equals("2")){
                upgradeCard(scanner);
            }else if(input.equals("back")){
                break ;
            }else{
                System.out.println("invalid command");
            }
        }
    }
    private static void buyCard(Scanner scanner){
        CardController CC = new CardController() ;
        User myUser = User.getLoggedInUser() ;
        ArrayList<Card> visibleCard = new ArrayList<>() ;
        boolean check = true ;
        int i=1 ;
        for(Card card : CC.getCards()){
            check = true ;
            for(Card userCard : myUser.getCards()){
                if(card.equals(userCard)){
                    check = false ;
                    break;
                }
            }
            if(check) {
                System.out.println(i +  "- type: " + card.getType() + " | name: " + card.getName() + " | cost: " + card.getUpgradeLeCost() + " | HP: " + card.getHP() + " | damage: " + card.getDamage() + " | duration: " + card.getDuration());
                i++;
                visibleCard.add(card) ;
            }
        }
        while (true){
            String input = scanner.nextLine();
            if(input.equals("back")){
                break ;
            }
            if(!input.matches("\\d+")){
                System.out.println("invalid input");
            }else if(Integer.valueOf(input)<1 || Integer.valueOf(input)>visibleCard.size()){
                System.out.println("invalid input");
            }else{
                int cardNumber = Integer.valueOf(input)-1 ;
                Card mainCard = visibleCard.get(cardNumber) ;
                if(myUser.hasCard(mainCard)){
                    System.out.println("you already have this card !");
                }else if(myUser.getGold()>=mainCard.getUpgradeLeCost()){
                    myUser.setGold(User.getLoggedInUser().getGold()-mainCard.getUpgradeLeCost());
                    myUser.addCards(mainCard);
                    System.out.println("successfully added");
                }else{
                    System.out.println("you dont have enough gold");
                }
                break ;
            }
        }
    }
    private static void upgradeCard(Scanner scanner){
        CardController CC = new CardController() ;
        User myUser = User.getLoggedInUser() ;
        int i=1 ;
        for(Card card : myUser.getCards()){
            System.out.println(i + "- type: " + card.getType() + " | name: " + card.getName() + " | cost: " + card.getUpgradeLeCost()*1.25*(card.getLevel()+1) + " | HP: " + card.getHP() + " | damage: " + card.getDamage() + " | duration: " + card.getDuration() + " | level for upgrade: " + card.getUpgradeLevel());
            i++;
        }
        while (true){
            String input = scanner.nextLine();
            if(input.equals("back")){
                break ;
            }
            if(!input.matches("\\d+")){
                System.out.println("invalid input");
            }else if(Integer.valueOf(input)<1 || Integer.valueOf(input)>myUser.getCards().size()){
                System.out.println("invalid input");
            }else{
                int cardNumber = Integer.valueOf(input)-1 ;
                Card mainCard = myUser.getCards().get(cardNumber) ;
                if(myUser.getLevel()<mainCard.getUpgradeLevel()){
                    System.out.println("you need to level up to level: " + mainCard.getUpgradeLevel() + "then you can upgrade your card");
                }else if(myUser.getGold()>= mainCard.getUpgradeLeCost()*1.25*(mainCard.getLevel()+1)){
                    myUser.setGold( (int) ( User.getLoggedInUser().getGold()- (mainCard.getUpgradeLeCost()*1.25*(mainCard.getLevel()+1)) ) );
                    myUser.addCards(mainCard);
                    System.out.println("successfully added");
                }else{
                    System.out.println("you dont have enough gold");
                }
                break ;
            }
        }
    }
}
