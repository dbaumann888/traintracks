package traintracks.server.engine.game;

import traintracks.api.Deck;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TTDeck<T> implements Deck<T> {
    private List<T> cards;
    private List<T> discards;

    public TTDeck(List<T> cards) {
        this.cards = cards;
        this.discards = new ArrayList<>();
    }

    public TTDeck(List<T> cards, List<T> discards) {
        this.cards = cards;
        this.discards = discards;
    }

    public Deck<T> clone() {
        return new TTDeck(new ArrayList<>(this.cards), new ArrayList<>(this.discards));
    }

    public List<T> getCards() {
        return this.cards;
    }

    public List<T> getDiscards() {
        return this.discards;
    }

    public T drawCard() {
        if (this.cards.isEmpty()) {
            if (this.discards.isEmpty()) {
                return null;
            }
            this.cards.addAll(this.discards);
            this.discards.clear();
            shuffle();
        }
        return this.cards.remove(0);
    }

    public void addCardToDiscards(T card) {
        this.discards.add(card);
    }

    public void addCardToBottom(T card) {
        this.cards.add(card);
    }

    public void shuffle() {
        Collections.shuffle(this.cards);
    }

    public boolean isEmpty() {
        return this.cards.isEmpty();
    }
    public String toString() { return "" + this.cards.size(); }
}
