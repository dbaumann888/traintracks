package traintracks.api;

import java.util.List;

public interface OpenCards<T> {
    T getCard(int i);
    List<T> getCards();
    T retrieveCard(int i, T card);
}
