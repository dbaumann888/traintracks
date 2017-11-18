package traintracks.api;

import java.util.List;

public interface Deck<T> {
    List<T> getCards();
    List<T> getDiscards();
    T drawCard();
    void addCardToDiscards(T Card);
    void addCardToBottom(T card);
    boolean isEmpty();
    void shuffle();
}
