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
    public T swapCard(int i, T replacementCard) {
        T cardToReturn = this.cards[i];
        this.cards[i] = replacementCard;
        return cardToReturn;
    }
    public int getNumOpenings() {
        return this.cards.length;
    }
    public String toString() { return "" + this.cards; }
}
