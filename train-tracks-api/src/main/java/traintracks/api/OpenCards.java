package traintracks.api;

import java.util.List;

public interface OpenCards<T> {
    T getCard(int i);
    List<T> getCards();
    void setCards(List<T> cards);
    T retrieveCard(int i, T card);
    void clear();
}
