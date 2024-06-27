package Model;

public class Card {
    private String Name, Type;
    private int HP, Damage, Duration, UpgradeLevel, UpgradeCost, level = 1, TypeNumber;

    public Card(String name, int HP, int damage, int duration, int upgradeLevel, int upgradeCost, int typeNumber) {
        this.Name = name;
        this.HP = HP;
        this.Damage = damage;
        this.Duration = duration;
        this.UpgradeLevel = upgradeLevel;
        this.UpgradeCost = upgradeCost;
        this.Type = "normal";
        this.TypeNumber = typeNumber;
    }

    public int getTypeNumber() {
        return TypeNumber;
    }

    public void setTypeNumber(int typeNumber) {
        TypeNumber = typeNumber;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getUpgradeLevel() {
        return UpgradeLevel;
    }

    public void setUpgradeLevel(int upgradeLevel) {
        UpgradeLevel = upgradeLevel;
    }

    public int getUpgradeCost() {
        return UpgradeCost;
    }

    public void setUpgradeCost(int upgradeCost) {
        UpgradeCost = upgradeCost;
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
