package Model;

public class Card {
    private String Name ;
    private int HP , Damage , Duration;

    public Card(String name, int HP, int damage, int duration) {
        this.Name = name;
        this.HP = HP;
        this.Damage = damage;
        this.Duration = duration;
    }

    public String getName() {
        return this.Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public int getHP() {
        return this.HP;
    }

    public void setHP(int HP) {
        this.HP = HP;
    }

    public int getDamage() {
        return this.Damage;
    }

    public void setDamage(int damage) {
        this.Damage = damage;
    }

    public int getDuration() {
        return this.Duration;
    }

    public void setDuration(int duration) {
        this.Duration = duration;
    }
}
