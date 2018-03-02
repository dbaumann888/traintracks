package traintracks.api;

import java.util.List;

public interface OpenCards<T> {
    T getCard(int i);
    List<T> getCards();
    T swapCard(int i, T card);
    int getNumOpenings();
}
