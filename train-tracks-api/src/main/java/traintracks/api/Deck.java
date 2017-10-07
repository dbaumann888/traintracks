package traintracks.api;

import java.util.List;

public interface Deck<T> {
    List<T> getCards();
    T drawCard();
    void addCard(T card);
    void shuffle();
}
