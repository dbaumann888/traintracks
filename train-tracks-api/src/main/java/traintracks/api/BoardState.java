package traintracks.api;

import java.util.List;

public interface BoardState {
    Player getActivePlayer();
    void setActivePlayer(Player nextPlayer);
    List<CompletedRoute> getCompletedRoutes();
    List<Route> getUncompletedRoutes(Board board);
    List<Route> getCompletableRoutes(Board board);
    boolean completedRoutesContainsRoute(Route route);
    Deck<Car> getCarDrawDeck();
    OpenCards<Car> getOpenCards();
    Car drawCar(int index);
    void discardCar(Car car);
    Deck<Ticket> getTicketDrawDeck();
}
