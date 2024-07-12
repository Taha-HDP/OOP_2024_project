package View;

import Controller.GameController;
import Controller.UserController;
import Model.Card;
import Model.Game;
import Model.SQL;
import Model.User;

import java.sql.Connection;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GameMenu {
    private static User user2;
    private static final User user1 = User.getLoggedInUser();
    private static int charNumber1 = 0, charNumber2 = 0, loginAttempt = 0, betPrice = 0, XPCounter1 = 0, XPCounter2 = 0, round1 = 4, round2 = 4, turn = 0;
    private static long startTime = 0;

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
        long elapsedTime;
        if (startTime != 0) {
            elapsedTime = System.currentTimeMillis() / 1000 - startTime;
            if (elapsedTime >= (5L * loginAttempt))
                startTime = 0;
            else {
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

    private static void bet(Scanner scanner) {
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

    public static void startGame(Scanner scanner, boolean bet) {
        if (login2thPlayer(scanner)) {
            if (bet)
                bet(scanner);
            while (true) {
                System.out.println("select your character");
                System.out.println("1- character 1\n2- character 2\n3- character 3\n4- character 4\n5- done");
                String input = scanner.nextLine();
                if (input.matches(Regexes.selectCharacter.pattern)) {
                    Matcher matcher = getCommandMatcher(input, Regexes.selectCharacter.pattern);
                    matcher.find();
                    if (Integer.parseInt(matcher.group("characterNumber")) > 5 || Integer.parseInt(matcher.group("characterNumber")) < 1) {
                        System.out.println("invalid number for character");
                    } else if (matcher.group("user").equals("1")) {
                        charNumber1 = Integer.parseInt(matcher.group("characterNumber"));
                        System.out.println("selected successfully for user 1");
                    } else if (matcher.group("user").equals("2")) {
                        charNumber2 = Integer.parseInt(matcher.group("characterNumber"));
                        System.out.println("selected successfully for user 2");
                    } else {
                        System.out.println("invalid user");
                    }
                } else if (input.equals("5")) {
                    if (charNumber1 == 0 || charNumber2 == 0) {
                        System.out.println("both need to select character");
                    } else {
                        printData(scanner, bet);
                        return;
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

    private static void printData(Scanner scanner, boolean bet) {
        user1.setHP(user1.getLevel() * 100);
        user2.setHP(user1.getLevel() * 100);
        XPCounter1 += user1.getXP();
        XPCounter2 += user2.getXP();
        //------------------------------ show map
        printMap(GameController.map1, 1);
        printMap(GameController.map2, 2);
        //------------------------------ show HP
        System.out.println("User 1 HP : " + user1.getHP());
        System.out.println("User 2 HP : " + user2.getHP());
        //------------------------------ show character
        System.out.println("User 1 character : " + charNumber1);
        System.out.println("User 2 character : " + charNumber2);
        //------------------------------ create random deck cards
        GameController GC = new GameController();
        GC.generateRandomCards(user1);
        GC.generateRandomCards(user2);
        //------------------------------
        while (true) {
            if (turn == -1)
                break;
            //------------------------------ show Rounds
            System.out.println("User 1 rounds : " + round1);
            System.out.println("User 2 rounds : " + round2);
            //------------------------------ show cards
            System.out.println("user1 deck :");
            printDeck(user1);
            System.out.println("user2 deck :");
            printDeck(user2);
            System.out.println("user " + ((turn % 2) + 1) + " turn");
            String input = scanner.nextLine();
            if (input.matches(Regexes.selectCard.pattern)) {
                Matcher matcher = getCommandMatcher(input, Regexes.selectCard.pattern);
                printCardDetail(matcher);
            } else if (input.matches(Regexes.placeCard.pattern)) {
                Matcher matcher = getCommandMatcher(input, Regexes.placeCard.pattern);
                game(matcher, bet);
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

    public static void printDeck(User user) {
        int i = 1;
        if (!user.isShowDeck()) {
            System.out.println("deck hided by enemie this round!");
            user.setShowDeck(true);
        } else {
            for (Card card : user.getRandomCards()) {
                if (card.getType().equals("Spell"))
                    System.out.println(i + "-name: " + card.getName());
                else
                    System.out.println(i + "-name: " + card.getName() + " | Power: " + card.getPower() + " | damage: " + card.getDamage() + " | duration: " + card.getDuration());
                i++;
            }
        }
    }

    public static void printMap(int[] numbers, int n) {
        Random random = new Random();
        for (int i = 0; i < 21; i++) {
            numbers[i] = i + 1;
        }
        int randomSell = random.nextInt(21);
        numbers[randomSell] = 0;
        if (n == 1) {
            GameController.map1Detail[randomSell][0] = -1;
            GameController.map1Detail[randomSell][1] = -1;
        } else {
            GameController.map2Detail[randomSell][0] = -1;
            GameController.map2Detail[randomSell][1] = -1;
        }
        for (int number : numbers) {
            if (number == 0)
                System.out.print("X ");
            else
                System.out.print(number + " ");
        }
        System.out.println();
    }

    private static void printCardDetail(Matcher matcher) {
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

    private static void game(Matcher matcher, boolean bet) {
        matcher.find();
        GameController GC = new GameController();
        int blockNumber = Integer.parseInt(matcher.group("block"));
        int cardNumber = Integer.parseInt(matcher.group("card"));
        if (cardNumber > 5 || cardNumber < 1 || blockNumber > 21 || blockNumber < 1) {
            System.out.println("invalid input");
        } else {
            if (turn % 2 == 0) {
                Card card = user1.getRandomCardByNumber(cardNumber - 1);
                if (round1 <= 0) {
                    System.out.println("your rounds finish");
                    turn++;
                } else if (card.getType().equals("Spell")) {
                    if (placeSpell(turn % 2, user1, user2, GameController.map1Detail, GameController.map1, blockNumber - 1, card)) {
                        user1.removeFromRandomCard(card);
                        GC.generateRandomCard(user1);
                        printBlock();
                    }
                } else if (GC.placeable(blockNumber, card, GameController.map1)) {
                    System.out.println("card placed");
                    GameController.map1 = GC.placeCard(GameController.map1, card, blockNumber, charNumber1, 1);
                    user1.removeFromRandomCard(card);
                    GC.generateRandomCard(user1);
                    printBlock();
                    round1--;
                    turn++;
                } else
                    System.out.println("can't put this card here");
            } else {
                Card card = user2.getRandomCardByNumber(cardNumber - 1);
                if (round2 <= 0) {
                    System.out.println("your rounds finish");
                    turn++;
                } else if (card.getType().equals("Spell")) {
                    if (placeSpell(turn % 2, user2, user1, GameController.map2Detail, GameController.map2, blockNumber - 1, card)) {
                        user2.removeFromRandomCard(card);
                        GC.generateRandomCard(user2);
                        printBlock();
                    }
                } else if (GC.placeable(blockNumber, card, GameController.map2)) {
                    System.out.println("card placed");
                    GameController.map2 = GC.placeCard(GameController.map2, card, blockNumber, charNumber2, 2);
                    user2.removeFromRandomCard(card);
                    GC.generateRandomCard(user2);
                    printBlock();
                    round2--;
                    turn++;
                } else
                    System.out.println("can't put this card here");
            }
            if (round1 == 0 && round2 == 0) {
                endRounds(bet);
                turn = 0;
                round1 = 4;
                round2 = 4;
            }
        }
    }

    private static boolean placeSpell(int turn, User firstUser, User secoundUser, int[][] mapDetail, int map[], int blockNumber, Card card) {
        Random random = new Random();
        int randomSell, randomNumber;
        switch (card.getName()) {
            case "Round-1":
                if (turn == 0)
                    round2--;
                else
                    round1--;
                System.out.println("decrease round of user2");
                return true;
            case "Fixer":
                if (map[blockNumber] == 0) {
                    map[blockNumber] = blockNumber;
                    mapDetail[blockNumber][0] = 0;
                    mapDetail[blockNumber][1] = 0;
                    System.out.println("hole fixed");
                    return true;
                } else {
                    System.out.println("cant put this spell here");
                }
                break;
            case "Shield":
                if (mapDetail[blockNumber][0] == 0) {
                    mapDetail[blockNumber][0] = 500;
                    mapDetail[blockNumber][1] = 0;
                    System.out.println("shield placed");
                    return true;
                } else {
                    System.out.println("cant put this spell here");
                }
                break;
            case "Heal":
                if (mapDetail[blockNumber][0] == 0) {
                    mapDetail[blockNumber][0] = 400;
                    mapDetail[blockNumber][1] = -40;
                    System.out.println("Heal placed");
                    return true;
                } else {
                    System.out.println("cant put this spell here");
                }
                break;
            case "IncreasePow":
                randomSell = random.nextInt(21);
                while (mapDetail[randomSell][0] == 0 || mapDetail[randomSell][0] == 500 || mapDetail[randomSell][0] == 400 ||mapDetail[randomSell][0] == -1) {
                    randomSell = random.nextInt(21);
                }
                mapDetail[randomSell][0] += 15;
                System.out.println("power of sell : " + (randomSell + 1) + " increased 15");
                return true;
            case "ChangeHole":
                randomSell = random.nextInt(21);
                while (mapDetail[randomSell][0] != 0) {
                    randomSell = random.nextInt(21);
                }
                for (int i = 0; i < 21; i++) {
                    if (map[i] == 0) {
                        map[i] = i + 1;
                        mapDetail[i][0] = 0;
                        mapDetail[i][1] = 0;
                        map[randomSell] = 0;
                        mapDetail[randomSell][0] = -1;
                        mapDetail[randomSell][1] = -1;
                        System.out.println("hole changed !");
                        return true;
                    }
                }
                System.out.println("cant find any empty sell");
                return false;
            case "Steal":
                randomNumber = random.nextInt(5);
                Card userCard = firstUser.getRandomCardByNumber(randomNumber);
                firstUser.removeFromRandomCard(userCard);
                secoundUser.randomCards.add(userCard);
                System.out.println("user " + (turn + 1) + "steal card from user " + (turn + 2));
                return true;
            case "Debilitation":
                randomNumber = random.nextInt(5);
                Card target = secoundUser.getRandomCardByNumber(randomNumber);
                target.setDamage(target.getDamage() - 5);
                System.out.println("damage of card : " + target.getName() + "decreased 5");
                randomNumber = random.nextInt(5);
                target = secoundUser.getRandomCardByNumber(randomNumber);
                System.out.println("power of card : " + target.getName() + "decreased 10");
                target.setPower(target.getPower() - 10);
                return true;
            case "Copy":
                Scanner scanner = new Scanner(System.in);
                while (true) {
                    System.out.println("enter number of card that you want to copy (back): ");
                    String input = scanner.nextLine();
                    if (input.equals("back")) {
                        return false;
                    } else if (input.matches("\\d+")) {
                        int num = Integer.parseInt(input);
                        if (num < 1 || num > 5) {
                            System.out.println("invalid number (must between 1-5");
                        } else {
                            firstUser.randomCards.add(firstUser.getRandomCardByNumber(num));
                            System.out.println("copied successfully");
                            return true;
                        }
                    } else {
                        System.out.println("invalid input");
                    }
                }
            case "Hide":
                secoundUser.setShowDeck(false);
                secoundUser.randomCards.sort(Comparator.comparing(Card::getName));
                System.out.println("applied !");
                return true;
        }
        return false;
    }

    private static void printBlock() {
        GameController GC = new GameController();
        System.out.print("player damages 1:");
        for (int i = 0; i < 21; i++) {
            if (GC.getMapDetail(1, i, 1) == -1) {
                System.out.print("| X ");
            } else if (GC.getMapDetail(1, i, 1) == -40) {
                System.out.print("|+40HP");
            } else {
                System.out.print("| " + GC.getMapDetail(1, i, 1) + " ");
            }
        }
        System.out.println();
        System.out.print("power 1:         ");
        for (int i = 0; i < 21; i++) {
            if (GC.getMapDetail(1, i, 0) == -1) {
                System.out.print("| X ");
            } else if (GC.getMapDetail(1, i, 0) == 500) {
                System.out.print("|shield");
            } else if (GC.getMapDetail(1, i, 0) == 400) {
                System.out.print("|Heal");
            } else {
                System.out.print("| " + GC.getMapDetail(1, i, 0) + " ");
            }
        }
        System.out.println();
        System.out.println("-----------------------------------------------------------------------------------");
        System.out.print("power 2:         ");
        for (int i = 0; i < 21; i++) {
            if (GC.getMapDetail(2, i, 0) == -1) {
                System.out.print("| X ");
            } else if (GC.getMapDetail(2, i, 0) == 500) {
                System.out.print("|shield");
            } else if (GC.getMapDetail(2, i, 0) == 400) {
                System.out.print("|Heal");
            } else {
                System.out.print("| " + GC.getMapDetail(2, i, 0) + " ");
            }
        }
        System.out.println();
        System.out.print("player damages 2:");
        for (int i = 0; i < 21; i++) {
            if (GC.getMapDetail(2, i, 1) == -1) {
                System.out.print("| X ");
            } else if (GC.getMapDetail(2, i, 1) == -40) {
                System.out.print("|+40HP");
            } else {
                System.out.print("| " + GC.getMapDetail(2, i, 1) + " ");
            }
        }
        System.out.println();
    }

    private static void endRounds(boolean bet) {
        if (startTimeLine(bet))
            return;
        System.out.println("Round 2");
        //------------------------------ show HP
        System.out.println("User 1 HP : " + user1.getHP());
        System.out.println("User 2 HP : " + user2.getHP());
        //------------------------------ show character
        System.out.println("User 1 character : " + charNumber1);
        System.out.println("User 2 character : " + charNumber2);
        //------------------------------ create random deck cards
        for(int i=0 ; i<5 ; i++){
            user1.randomCards.remove(user1.randomCards.get(i)) ;
            user2.randomCards.remove(user2.randomCards.get(i)) ;
        }
        GameController GC = new GameController();
        GC.generateRandomCards(user1);
        GC.generateRandomCards(user2);
    }

    private static boolean startTimeLine(Boolean bet) {
        GameController GC = new GameController();
        System.out.println("time line start moving!");
        for (int i = 0; i < 21; i++) {
            System.out.print("block : " + (i + 1) + " ");
            if (GC.getMapDetail(1, i, 1) > 0) {
                user2.setHP(user2.getHP() - GC.getMapDetail(1, i, 1));
                XPCounter2 += (GC.getMapDetail(1, i, 1) * 100);
                System.out.println("-" + GC.getMapDetail(1, i, 1) + "HP from user 2 !");
                if (user2.getHP() < 0) {
                    System.out.println("user 2 died !");
                    winner(1, bet);
                    turn = -1;
                    return true;
                } else {
                    System.out.println("user 2 HP : " + user2.getHP());
                }
            } else if (GC.getMapDetail(1, i, 1) < -1) {
                user1.setHP(user1.getHP() - GC.getMapDetail(1, i, 1));
                System.out.println("+" + GC.getMapDetail(1, i, 1) * -1 + "HP for user 1 !");
                System.out.println("user 1 HP : " + user1.getHP());
            }
            if (GC.getMapDetail(2, i, 1) > 0) {
                user1.setHP(user1.getHP() - GC.getMapDetail(2, i, 1));
                XPCounter1 += (GC.getMapDetail(1, i, 1) * 100);
                System.out.println("-" + GC.getMapDetail(2, i, 1) + "HP from user 1 !");
                if (user1.getHP() < 0) {
                    System.out.println("user 1 died !");
                    winner(2, bet);
                    turn = -1;
                    return true;
                } else {
                    System.out.println("user 1 HP : " + user1.getHP());
                }
            } else if (GC.getMapDetail(2, i, 1) < 0) {
                user2.setHP(user2.getHP() - GC.getMapDetail(2, i, 1));
                System.out.println("+" + GC.getMapDetail(2, i, 1) * -1 + "HP for user 2 !");
                System.out.println("user 2 HP : " + user2.getHP());
            }
        }
        return false;
    }

    private static void winner(int userNumber, boolean bet) {
        UserController UC = new UserController();
        User winner;
        int XPReward, goldReward;
        if (userNumber == 1) {
            winner = user1;
            System.out.println("Game Winner : " + user1.getUsername());
            System.out.println("XP : +" + XPCounter2 * 15);
            XPReward = XPCounter2 * 150;
            System.out.println("Gold : +" + (300 + (user1.getLevel() * 20)));
            UC.changeXP(user1, XPCounter2 * 150);
            user1.setGold(user1.getGold() + (user1.getLevel() * 20) + 300);
            goldReward = (user1.getLevel() * 20) + 300;
            if (bet) {
                System.out.println("bet reward : " + betPrice);
                user1.setGold(user1.getGold() + betPrice);
                goldReward += betPrice;
            }
        } else {
            winner = user2;
            System.out.println("Game Winner : " + user2.getUsername());
            System.out.println("XP : +" + XPCounter1 * 15);
            XPReward = XPCounter2 * 150;
            System.out.println("Gold : +" + ((user2.getLevel() * 20)+300));
            UC.changeXP(user2, XPCounter1 * 150);
            UC.changeGold(user2, (user2.getLevel() * 20) + 300);
            goldReward = (user1.getLevel() * 20) + 300;
            if (bet) {
                System.out.println("bet reward : " + betPrice);
                user2.setGold(user2.getGold() + betPrice);
                goldReward += betPrice;
            }
        }
        System.out.println("-------------------------------------------------");
        System.out.println("reward for looser :");
        System.out.println("XP : +" + 500);
        System.out.println("Gold : +" + user2.getLevel() * 50);
        UC.changeXP(user2, 500);
        UC.changeGold(user2, user2.getLevel() * 50);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        Game.games.add(new Game(user1.getUsername(), user2.getUsername(), dtf.format(now), winner, XPReward, goldReward));
        try {
            Class.forName("org.sqlite.JDBC");
            Connection c = SQL.c;
            Statement stmt = SQL.stmt;
            c.setAutoCommit(false);
            String addGame = "INSERT INTO GAME (Player1,Player2,Date,XP,Gold,Winner) " + "VALUES ('" + user1.getUsername() + "', '" + user2.getUsername() + "', '" + dtf.format(now) + "' , '" + XPReward + "' , " + goldReward + " , '" + winner.getUsername() + "' );";
            stmt.executeUpdate(addGame);
            c.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}