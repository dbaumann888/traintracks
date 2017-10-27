package traintracks.engine.game;

import traintracks.api.Deck;

import java.util.Collections;
import java.util.List;

public class TTDeck<T> implements Deck<T> {
    private List<T> cards;

    public TTDeck(List<T> cards) {
        this.cards = cards;
    }

    public List<T> getCards() {
        return this.cards;
    }

    public T drawCard() {
        return this.cards.remove(0);
    }

    public void addCardToBottom(T card) {
        this.cards.add(card);
    }

    public void shuffle() {
        Collections.shuffle(this.cards);
    }

    public String toString() { return "" + this.cards.size(); }
}
