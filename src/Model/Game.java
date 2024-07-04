package Model;

import java.util.ArrayList;

public class Game {
    public String Date, Player1, Player2;
    public User winner;
    public int XPReward, goldReward;
    public static ArrayList<Game> games = new ArrayList<>();

    public Game(String player1, String player2, String date, User winner, int XPReward, int goldReward) {
        this.Date = date;
        this.winner = winner;
        this.XPReward = XPReward;
        this.goldReward = goldReward;
        this.Player1 = player1;
        this.Player2 = player2;
    }
}
