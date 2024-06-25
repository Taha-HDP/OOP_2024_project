package Model;

public class User {
    private String Username , Password , Nickname , Email , FathersName , Color , Pet ;
    private static User loggedInUser ;
    private int Level=1 , HP = 100 , XP=0 , Gold = 1000 ;
    public User(String username, String password, String nickname, String email, String fathersName , String color , String pet) {
        this.Username = username;
        this.Password = password;
        this.Nickname = nickname;
        this.Email = email;
        this.FathersName = fathersName;
        this.Color = color ;
        this.Pet = pet ;
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

    public void setLevel(int level) {
        Level = level;
    }

    public void setHP(int HP) {
        this.HP = HP;
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

    public int getHP() {
        return HP;
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

    public void setFathersName(String fathersName) {
        FathersName = fathersName;
    }

    public String getColor() {
        return Color;
    }

    public void setColor(String color) {
        Color = color;
    }

    public String getPet() {
        return Pet;
    }

    public void setPet(String pet) {
        Pet = pet;
    }
    public static void setLoggedInUser(User user) {
        User.loggedInUser = user;
    }
}
