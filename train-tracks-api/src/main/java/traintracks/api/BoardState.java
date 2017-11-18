package traintracks.api;

import java.util.List;

public interface BoardState {
    Player getActivePlayer();
    void setActivePlayer(Player nextPlayer);
    List<CompletedRoute> getCompletedRoutes();
    boolean completedRoutesContainsRoute(Route route);
    Deck<Car> getCarDrawDeck();
    OpenCards<Car> getOpenCards();
    Car drawCar(int index);
    void discardCar(Car car);
    Deck<Ticket> getTicketDrawDeck();
}
