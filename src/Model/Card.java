package Model;

public class Card {
    private String Name , Type;
    private int HP , Damage , Duration , UpgradeLevel , UpgradeLeCost;
    public Card(String name, int HP, int damage, int duration , int upgradeLevel , int upgradeLeCost) {
        this.Name = name;
        this.HP = HP;
        this.Damage = damage;
        this.Duration = duration;
        this.UpgradeLevel = upgradeLevel ;
        this.UpgradeLeCost = upgradeLeCost ;
    }

    public int getUpgradeLevel() {
        return UpgradeLevel;
    }

    public void setUpgradeLevel(int upgradeLevel) {
        UpgradeLevel = upgradeLevel;
    }

    public int getUpgradeLeCost() {
        return UpgradeLeCost;
    }

    public void setUpgradeLeCost(int upgradeLeCost) {
        UpgradeLeCost = upgradeLeCost;
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

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }
}
