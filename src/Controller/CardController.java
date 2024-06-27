package Controller;

import Model.Card;

import java.util.ArrayList;

public class CardController {
    private static ArrayList<Card> cards = new ArrayList<>();

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
    }

    public ArrayList<Card> getCards() {
        return this.cards;
    }

    public void removeCard(Card target) {
        for (Card card : cards) {
            if (card.equals(target)) {
                cards.remove(card);
                return;
            }
        }
    }
}
