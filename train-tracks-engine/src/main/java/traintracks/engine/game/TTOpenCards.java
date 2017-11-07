package traintracks.engine.game;

import traintracks.api.OpenCards;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

public class TTOpenCards<T> implements OpenCards<T> {
    private T[] cards;

    public TTOpenCards(Class<T> clazz, int size) {
        this.cards = (T[]) Array.newInstance(clazz, size);
    }

    public T getCard(int i) { return this.cards[i]; }
    public List<T> getCards() { return Arrays.asList(this.cards); }
    public void setCards(List<T> cards) {
        if (cards.size() > this.cards.length) {
            throw new IllegalArgumentException("List is too long to fit in array");
        }
        for (int i = 0; i < cards.size(); ++i) {
            this.cards[i] = cards.get(i);
        }
    }
    public T retrieveCard(int i, T replacementCard) {
        T cardToReturn = this.cards[i];
        this.cards[i] = replacementCard;
        return cardToReturn;
    }
    public void clear() {
        for (int i = 0; i < this.cards.length; ++i) {
            this.cards[i] = null;
        }
    }
    public String toString() { return "" + this.cards; }
}
