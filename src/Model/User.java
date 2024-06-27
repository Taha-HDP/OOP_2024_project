package Model;

import java.util.ArrayList;

public class User {
    private String Username, Password, Nickname, Email;
    private final String FathersName, Color, Pet;
    private static User loggedInUser = null;
    private boolean firstLogin = true;
    private int Level = 1, XP = 0, Gold = 1000, HP = 100;
    private final ArrayList<Card> cards = new ArrayList<>();
    public ArrayList<Card> randomCards = new ArrayList<>();

    public User(String username, String password, String nickname, String email, String fathersName, String color, String pet) {
        this.Username = username;
        this.Password = password;
        this.Nickname = nickname;
        this.Email = email;
        this.FathersName = fathersName;
        this.Color = color;
        this.Pet = pet;
    }
    public Card getCardByNumber(int number){
        return cards.get(number) ;
    }
    public Card getFromRandomCard(Card card){
        for(Card target : randomCards){
            if(target.equals(card)){
                return target ;
            }
        }
        return null ;
    }
    public int getHP() {
        return HP;
    }

    public void setHP(int HP) {
        this.HP = HP;
    }

    public Card getCard(Card card) {
        for (Card target : this.cards) {
            if (card.equals(target)) {
                return card;
            }
        }
        return null;
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public void addCards(Card card) {
        this.cards.add(card);
    }

    public void setUsername(String username) {
        this.Username = username;
    }

    public void setPassword(String password) {
        this.Password = password;
    }

    public void setNickname(String nickname) {
        this.Nickname = nickname;
    }

    public void setEmail(String email) {
        this.Email = email;
    }

    public boolean isFirstLogin() {
        return firstLogin;
    }

    public void setFirstLogin(boolean firstLogin) {
        this.firstLogin = firstLogin;
    }

    public void setLevel(int level) {
        Level = level;
    }

    public void setXP(int XP) {
        this.XP = XP;
    }

    public void setGold(int gold) {
        Gold = gold;
    }

    public String getUsername() {
        return this.Username;
    }

    public String getPassword() {
        return this.Password;
    }

    public String getNickname() {
        return this.Nickname;
    }

    public String getEmail() {
        return this.Email;
    }

    public int getLevel() {
        return Level;
    }

    public int getXP() {
        return XP;
    }

    public int getGold() {
        return Gold;
    }

    public String getFathersName() {
        return FathersName;
    }

    public String getColor() {
        return Color;
    }

    public String getPet() {
        return Pet;
    }

    public static void setLoggedInUser(User user) {
        User.loggedInUser = user;
    }

    public static User getLoggedInUser() {
        return loggedInUser;
    }
}
