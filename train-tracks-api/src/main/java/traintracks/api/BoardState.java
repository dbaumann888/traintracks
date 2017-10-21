package traintracks.api;

import java.util.List;

public interface BoardState {
    Player getActivePlayer();
    boolean mustDrawSecondCar();
    void setActivePlayer(Player nextPlayer);
    List<CompletedRoute> getCompletedRoutes();
    Deck<Car> getCarDrawDeck();
    OpenCards<Car> getOpenCards();
    Car drawCar(int index);
    Deck<Ticket> getTicketDrawDeck();
}
