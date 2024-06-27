package Model;

public class Game {
    String Date;
    User winner;
    int XPReward, goldReward;

    public Game(String date, User winner, int XPReward, int goldReward) {
        Date = date;
        this.winner = winner;
        this.XPReward = XPReward;
        this.goldReward = goldReward;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public User getWinner() {
        return winner;
    }

    public void setWinner(User winner) {
        this.winner = winner;
    }

    public int getXPReward() {
        return XPReward;
    }

    public void setXPReward(int XPReward) {
        this.XPReward = XPReward;
    }

    public int getGoldReward() {
        return goldReward;
    }

    public void setGoldReward(int goldReward) {
        this.goldReward = goldReward;
    }
}
