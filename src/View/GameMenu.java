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
    private static int map1[] = new int[21];
    private static int map2[] = new int[21];
    private static int map1Detail[][] = new int[21][2];
    private static int map2Detail[][] = new int[21][2];
    private static int charNumber1 = 0, charNumber2 = 0, loginAttempt = 0, betPrice = 0, turn = 0, XPCounter1 = 0, XPCounter2 = 0;
    private static long startTime = 0;
    private static ArrayList<Card> randomCard1 = new ArrayList<>();
    private static ArrayList<Card> randomCard2 = new ArrayList<>();

    public static void run(Scanner scanner) {
        System.out.println("entered game menu");
        while (true) {
            System.out.println("select mode :\n1- 1V1\n2- bet");
            String input = scanner.nextLine();
            //1V1
            if (input.equals("1"))
                startGame(scanner, false);
                //bet
            else if (input.equals("2"))
                startGame(scanner, true);
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

    public static void startGame(Scanner scanner, boolean bet) {
        if (login2thPlayer(scanner)) {
            if (bet) {
                while (true) {
                    System.out.println("select your bet amount :");
                    String betAmount = scanner.nextLine();
                    if (betAmount.equals("back"))
                        return;
                    else if (!betAmount.matches("\\d+"))
                        System.out.println("invalid input");
                    else {
                        int amount = Integer.parseInt(betAmount);
                        if (user2.getGold() < amount)
                            System.out.println("user 2 dont have enough gold");
                        else if (user1.getGold() < amount)
                            System.out.println("user 1 dont have enough gold");
                        else {
                            System.out.println("bet amount accepted");
                            user1.setGold(user1.getGold() - amount);
                            user2.setGold(user2.getGold() - amount);
                            betPrice = amount;
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
                    if (Integer.parseInt(matcher.group("characterNumber")) > 5 || Integer.parseInt(matcher.group("characterNumber")) < 1) {
                        System.out.println("invalid number for character");
                    } else if (matcher.group("characterNumber").equals("5")) {
                        if (charNumber1 == 0 || charNumber2 == 0) {
                            System.out.println("both need to select character");
                        } else {
                            printData(scanner, bet);
                            return;
                        }
                    } else if (matcher.group("user").equals("user1")) {
                        charNumber1 = Integer.parseInt(matcher.group("characterNumber"));
                    } else if (matcher.group("user").equals("user2")) {
                        charNumber2 = Integer.parseInt(matcher.group("characterNumber"));
                    } else {
                        System.out.println("invalid user");
                    }
                } else if (input.equals("back")) {
                    user2 = null;
                    break;
                } else {
                    System.out.println("invalid command");
                }
            }
        }
    }

    public static void printData(Scanner scanner, boolean bet) {
        user1.setHP(100 + user1.getLevel() * 10);
        user2.setHP(100 + user1.getLevel() * 10);
        XPCounter1 += user1.getXP();
        XPCounter2 += user2.getXP();
        int round1 = 4, round2 = 4;
        //------------------------------ show map
        printMap(map1);
        printMap(map2);
        //------------------------------ show HP
        System.out.println("User 1 HP : " + user1.getHP());
        System.out.println("User 2 HP : " + user2.getHP());
        //------------------------------ show character
        System.out.println("User 1 character : " + charNumber1);
        System.out.println("User 2 character : " + charNumber2);
        //------------------------------ create random deck cards
        showRandomCard(user1);
        showRandomCard(user2);
        //------------------------------
        while (true) {
            //------------------------------ show Rounds
            System.out.println("User 1 rounds : " + round1);
            System.out.println("User 2 rounds : " + round2);
            //------------------------------ show cards
            System.out.println("user1 deck :");
            printDeck(user1);
            System.out.println("user2 deck :");
            printDeck(user2);
            String input = scanner.nextLine();
            if (input.matches(Regexes.selectCard.pattern)) {
                Matcher matcher = getCommandMatcher(input, Regexes.selectCard.pattern);
                matcher.find();
                int userNumber = Integer.parseInt(matcher.group("user"));
                int cardNumber = Integer.parseInt(matcher.group("card"));
                if (userNumber > 2 || userNumber < 1 || cardNumber > 5 || cardNumber < 1)
                    System.out.println("invalid input");
                else {
                    Card card;
                    if (userNumber == 1)
                        card = user1.getRandomCardByNumber(cardNumber - 1);
                    else
                        card = user2.getRandomCardByNumber(cardNumber - 1);
                    System.out.println("type: " + card.getType() + " | name: " + card.getName() + " | cost: " + card.getUpgradeCost() + " | HP: " + card.getPower() + " | damage: " + card.getDamage() + " | duration: " + card.getDuration());
                }
            }
            else if (input.matches(Regexes.placeCard.pattern)) {
                Matcher matcher = getCommandMatcher(input, Regexes.placeCard.pattern);
                matcher.find();
                int blockNumber = Integer.parseInt(matcher.group("block"));
                int cardNumber = Integer.parseInt(matcher.group("card"));
                if (cardNumber > 5 || cardNumber < 1 || blockNumber > 21 || blockNumber < 1) {
                    System.out.println("invalid input");
                } else {
                    if (turn % 2 == 0) {
                        Card card = user1.getRandomCardByNumber(cardNumber - 1);
                        if (placeable(blockNumber, card, map1)) {
                            System.out.println("card placed");
                            map1 = placeCard(map1, card, blockNumber, charNumber1);
                            user1.removeFromRandomCard(card);
                            generateRandomCard(user1);
                            printBlock();
                            round1--;
                            turn++;
                        } else
                            System.out.println("can't put this card here");
                    } else {
                        Card card = user2.getRandomCardByNumber(cardNumber - 1);
                        if (placeable(blockNumber, card, map2)) {
                            System.out.println("card placed");
                            map2 = placeCard(map2, card, blockNumber, charNumber2);
                            user2.removeFromRandomCard(card);
                            generateRandomCard(user2);
                            printBlock();
                            round2--;
                            turn++;
                        } else
                            System.out.println("can't put this card here");
                    }
                    if (round1 == 0 && round2 == 0) {
                        if (startTimeLine(bet))
                            return;
                        System.out.println("Round 2");
                        round1 = 4;
                        round2 = 4;
                        //------------------------------ show HP
                        System.out.println("User 1 HP : " + user1.getHP());
                        System.out.println("User 2 HP : " + user2.getHP());
                        //------------------------------ show character
                        System.out.println("User 1 character : " + charNumber1);
                        System.out.println("User 2 character : " + charNumber2);
                        //------------------------------ create random deck cards
                        user1.setRandomCards(null);
                        user2.setRandomCards(null);
                        showRandomCard(user1);
                        showRandomCard(user2);
                    }

                }

            } else if (input.equals("back")) {
                System.out.println("are you sure you want to leave game ?(y/n)");
                input = scanner.nextLine();
                if (input.equals("y")) {
                    break;
                }
            } else if (input.equals("exit"))
                System.exit(0);
                //invalid command
            else
                System.out.println("invalid command");
        }
    }
    private static boolean startTimeLine(Boolean bet) {
        System.out.println("time line start moving!");
        for (int i = 0; i < 21; i++) {
            System.out.print("block :" + i + 1);
            if (map1Detail[i][0] > 0) {
                user2.setHP(user2.getHP() - map1Detail[i][0]);
                System.out.println("-" + map1Detail[i][0] + "HP from user 2 !");
                if (user2.getHP() < 0) {
                    System.out.println("user 2 died !");
                    winner(1, bet);
                    return true;
                } else {
                    System.out.println("user 2 HP : " + user2.getHP());
                }
            }
            if (map2Detail[i][0] > 0) {
                user1.setHP(user1.getHP() - map2Detail[i][0]);
                System.out.println("-" + map2Detail[i][0] + "HP from user 1 !");
                if (user1.getHP() < 0) {
                    System.out.println("user 1 died !");
                    winner(2, bet);
                    return true;
                } else {
                    System.out.println("user 1 HP : " + user1.getHP());
                }
            }
        }
        return false;
    }

    private static void printBlock() {
        System.out.print("player damages 1:");
        for (int i = 0; i < 21; i++) {
            if (map1Detail[i][0] == -1) {
                System.out.print("| X |");
            } else {
                System.out.print("| " + map1Detail[i][0] + " |");
            }
        }
        System.out.println();
        System.out.print("power 1:");
        for (int i = 0; i < 21; i++) {
            if (map1Detail[i][1] == -1) {
                System.out.print("| X |");
            } else {
                System.out.print("| " + map1Detail[i][1] + " |");
            }
        }
        System.out.println();
        System.out.println("-----------------------------------------------------------------------------------");
        System.out.print("power 2:");
        for (int i = 0; i < 21; i++) {
            if (map2Detail[i][1] == -1) {
                System.out.print("| X |");
            } else {
                System.out.print("| " + map2Detail[i][1] + " |");
            }
        }
        System.out.println();
        System.out.print("player damages 2:");
        for (int i = 0; i < 21; i++) {
            if (map2Detail[i][0] == -1) {
                System.out.print("| X |");
            } else {
                System.out.print("| " + map2Detail[i][0] + " |");
            }
        }
    }

    private static int[] placeCard(int[] map, Card card, int blockNumber, int characterNumber) {
        for (int i = blockNumber - 1; i < blockNumber + card.getDuration(); i++) {
            map[i] = 0;
            map1Detail[i][0] = card.getPower();
            map1Detail[i][1] = card.getDamage() / card.getDuration();
            //afzayesh damage baray character
            if (card.getTypeNumber() == characterNumber) {
                map1Detail[i][1] += 10;
            }
            if (map1Detail[i][1] > 0 && map2Detail[i][1] > map1Detail[i][1]) {
                map1Detail[i][1] = -1;
                map1Detail[i][0] = -1;
            } else if (map2Detail[i][1] == map1Detail[i][1] && map1Detail[i][1] > 0) {
                map1Detail[i][1] = -1;
                map1Detail[i][0] = -1;
                map2Detail[i][1] = -1;
                map2Detail[i][0] = -1;
            } else if (map2Detail[i][1] > 0 && map2Detail[i][1] < map1Detail[i][1]) {
                map2Detail[i][1] = -1;
                map2Detail[i][0] = -1;
            }
        }
        return map;
    }

    private static boolean placeable(int blockNumber, Card card, int[] map) {
        for (int i = blockNumber - 1; i < blockNumber + card.getDuration(); i++) {
            if (map[i] == 0) {
                return false;
            }
        }
        return true;
    }

    public static void printMap(int[] numbers) {
        Random random = new Random();
        for (int i = 0; i < 21; i++) {
            numbers[i] = i + 1;
        }
        numbers[random.nextInt(21)] = 0;
        for (int number : numbers) {
            if (number == 0)
                System.out.println("X ");
            else
                System.out.print(number + " ");
        }
    }

    public static void showRandomCard(User user) {
        Random random = new Random();
        for (int i = 0; i < 5; i++) {
            int x = random.nextInt(user.getCards().size());
            if (user.getFromRandomCard(user.getCardByNumber(x)) != null) {
                i--;
            } else {
                Card card = user.getCardByNumber(x);
                user.randomCards.add(card);
            }
        }
    }

    public static void printDeck(User user) {
        int i = 1;
        for (Card card : user.getRandomCards()) {
            System.out.println(i + "- type: " + card.getType() + " | name: " + card.getName() + " | cost: " + card.getUpgradeCost() + " | HP: " + card.getPower() + " | damage: " + card.getDamage() + " | duration: " + card.getDuration());
            i++;
        }

    }

    private static void generateRandomCard(User user) {
        Random random = new Random();
        int x = random.nextInt(user.getCards().size());
        user.randomCards.add(user.getCardByNumber(x));
    }

    private static void winner(int userNumber, boolean bet) {
        UserController UC = new UserController();
        if (userNumber == 1) {
            System.out.println("Game Winner : " + user1.getUsername());
            System.out.println("XP : +" + XPCounter2*150);
            System.out.println("Gold : +" + user1.getLevel()*20);
            UC.changeXP(user1, XPCounter2 * 150);
            user1.setGold(user1.getGold() + (user1.getLevel()*20));
            if (bet) {
                System.out.println("bet reward : " + betPrice);
                user1.setGold(user1.getGold() + betPrice);
            }
        } else {
            System.out.println("Game Winner : " + user2.getUsername());
            System.out.println("XP : +" + XPCounter1 * 150);
            System.out.println("Gold : +" + user2.getLevel()*20);
            UC.changeXP(user2, XPCounter1 * 150);
            UC.changeGold(user2,user2.getLevel()*20) ;
            if (bet) {
                System.out.println("bet reward : " + betPrice);
                user1.setGold(user1.getGold() + betPrice);
            }
            System.out.println("-------------------------------------------------");
            System.out.println("reward for looser :");
            System.out.println("XP : +" + 50);
            System.out.println("Gold : +" + user2.getLevel()*5);
            UC.changeXP(user2, 50);
            UC.changeGold(user2,user2.getLevel()*5) ;
        }
    }
}
