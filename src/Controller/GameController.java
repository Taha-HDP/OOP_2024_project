package Controller;

import Model.Card;
import Model.User;

import java.util.Random;

public class GameController {
    public static int map1[] = new int[21];
    public static int map2[] = new int[21];
    public static int map1Detail[][] = new int[21][2];
    public static int map2Detail[][] = new int[21][2];

    public boolean placeable(int blockNumber, Card card, int[] map) {
        for (int i = blockNumber - 1; i < blockNumber + card.getDuration(); i++) {
            if (map[i] == 0) {
                return false;
            }
        }
        return true;
    }

    public void generateRandomCards(User user) {
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

    public void generateRandomCard(User user) {
        Random random = new Random();
        int x = random.nextInt(user.getCards().size());
        user.randomCards.add(user.getCardByNumber(x));
    }

    public int[] placeCard(int[] map, Card card, int blockNumber, int characterNumber) {
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

    public int getMapDetail(int mapNumber, int i, int j) {
        if (mapNumber == 1) {
            return map1Detail[i][j];
        } else {
            return map2Detail[i][j];
        }
    }
}
