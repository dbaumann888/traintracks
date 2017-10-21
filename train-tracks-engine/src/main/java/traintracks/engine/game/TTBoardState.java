package traintracks.engine.game;

import com.google.common.collect.ImmutableList;
import traintracks.api.BoardState;
import traintracks.api.Car;
import traintracks.api.CompletedRoute;
import traintracks.api.Deck;
import traintracks.api.OpenCards;
import traintracks.api.Player;
import traintracks.api.Ticket;

import java.util.ArrayList;
import java.util.List;

public class TTBoardState implements BoardState {
    private Player activePlayer;
    private boolean mustDrawSecondCar;
    private List<CompletedRoute> completedRoutes;
    private Deck<Car> carDrawDeck;
    private OpenCards<Car> openCars;
    private Deck<Car> carDiscardDeck;
    private Deck<Ticket> ticketDrawDeck;

    public TTBoardState(Player startingPlayer, Deck<Car> fullCarDeck, Deck<Ticket> fullTicketDeck) {
        this.activePlayer = startingPlayer;
        this.mustDrawSecondCar = false;
        this.completedRoutes = new ArrayList<>();
        this.carDrawDeck = fullCarDeck;
        this.carDrawDeck.shuffle();
        List<Car> initialCards = new ArrayList<>();
        for (int i = 0; i < 5; ++i) {
            initialCards.add(this.carDrawDeck.drawCard());
        }
        this.openCars = new TTOpenCards<Car>(initialCards);
        this.carDiscardDeck = new TTDeck<>(ImmutableList.of());
        this.ticketDrawDeck = fullTicketDeck;
        this.ticketDrawDeck.shuffle();
    }

    public Player getActivePlayer() { return this.activePlayer; }
    public void setActivePlayer(Player nextPlayer) { this.activePlayer = nextPlayer; }
    public boolean mustDrawSecondCar() { return this.mustDrawSecondCar; }
    public void setMustDrawSecondCar(boolean mustDrawSecondCar) { this.mustDrawSecondCar = mustDrawSecondCar; }
    public List<CompletedRoute> getCompletedRoutes() { return this.completedRoutes; }
    public Deck<Car> getCarDrawDeck() { return this.carDrawDeck; }
    public OpenCards<Car> getOpenCards() { return this.openCars; }
    public Car drawCar(int index) {
        if (index == -1) {
            return this.carDrawDeck.drawCard();
        } else {
            return this.openCars.retrieveCard(index, this.carDrawDeck.drawCard());
        }
    }
    public Deck<Ticket> getTicketDrawDeck() { return this.ticketDrawDeck; }
    public String toString() { return "board state"; }
}
