package traintracks.engine.game;

import traintracks.api.OpenCards;

import java.util.Arrays;
import java.util.List;

public class TTOpenCards<T> implements OpenCards<T> {
    private T[] cards;

    public TTOpenCards(List<T> cards) {
        this.cards = (T[])(cards.toArray());
    }

    public T getCard(int i) { return this.cards[i]; }
    public List<T> getCards() { return Arrays.asList(this.cards); }
    public T retrieveCard(int i, T replacementCard) {
        T cardToReturn = this.cards[i];
        this.cards[i] = replacementCard;
        return cardToReturn;
    }
    public String toString() { return "" + this.cards; }
}
