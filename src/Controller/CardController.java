package Controller;

import java.sql.*;

import Model.Card;
import Model.SQL;

import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;

public class CardController {
    public static ArrayList<Card> cards = new ArrayList<>();

    public Card getCardByName(String Name) {
        for (Card card : cards) {
            if (card.getName().equals(Name)) {
                return card;
            }
        }
        return null;
    }

    public void addCard(Card card) {
        cards.add(card);
        try {
            Class.forName("org.sqlite.JDBC");
            Connection c = SQL.c;
            Statement stmt = SQL.stmt;
            c.setAutoCommit(false);
            String addCard = "INSERT INTO CARD (CardName,CardType,Power,Damage,Duration,UpgradeLevel,UpgradeCost,Level,TypeNumber) " + "VALUES ('" + card.getName() + "', 'normal', " + card.getPower() + " , " + card.getDamage() + " , " + card.getDuration() + " , " + card.getUpgradeLevel() + " , " + card.getUpgradeCost() + " , " + card.getLevel() + " , " + card.getTypeNumber() + " );";
            stmt.executeUpdate(addCard);
            c.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public Card getCardByNumber(int x){
        return cards.get(x) ;
    }
    public ArrayList<Card> getCards() {
        return this.cards;
    }

    public void removeCard(Card target) {
        for (Card card : cards) {
            if (card.equals(target)) {
                cards.remove(card);
                try {
                    Class.forName("org.sqlite.JDBC");
                    Connection c = SQL.c;
                    Statement stmt = SQL.stmt;
                    c.setAutoCommit(false);
                    String deleteCard = "DELETE FROM CARD WHERE CardName = '" + card.getName() + "'";
                    stmt.executeUpdate(deleteCard);
                    c.commit();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return;
            }
        }
    }

    public void setCards(ArrayList<Card> all) {
        cards = all;
    }
}
