package traintracks.api;

import java.util.List;

public interface BoardState {
    Player getActivePlayer();
    void setActivePlayer(Player nextPlayer);
    List<CompletedRoute> getCompletedRoutes();
    Deck<Car> getCarDrawDeck();
    OpenCards<Car> getOpenCards();
    Deck<Ticket> getTicketDrawDeck();
}
