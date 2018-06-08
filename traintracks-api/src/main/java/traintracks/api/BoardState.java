package traintracks.api;

import java.util.List;

public interface BoardState {
    List<CompletedRoute> getCompletedRoutes();
    List<Route> getUncompletedRoutes(Board board);
    List<Route> getCompletableRoutes(Player player, Board board);
    boolean completedRoutesContainsRoute(Route route);
    Deck<Car> getCarDrawDeck();
    OpenCards<Car> getOpenCards();
    Car drawCar(Player player, int index);
    void discardCar(Car car);
    Deck<Ticket> getTicketDrawDeck();
}
