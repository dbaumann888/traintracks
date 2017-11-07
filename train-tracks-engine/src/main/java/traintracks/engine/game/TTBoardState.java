package traintracks.engine.game;

import traintracks.api.BoardState;
import traintracks.api.Car;
import traintracks.api.CompletedRoute;
import traintracks.api.Deck;
import traintracks.api.Flavor;
import traintracks.api.OpenCards;
import traintracks.api.Player;
import traintracks.api.Ticket;

import java.util.ArrayList;
import java.util.List;

public class TTBoardState implements BoardState {
    private Player activePlayer;
    private List<CompletedRoute> completedRoutes;
    private Deck<Car> carDrawDeck;
    private OpenCards<Car> openCars;
    private Deck<Ticket> ticketDrawDeck;

    public TTBoardState(Player startingPlayer, Deck<Car> fullCarDeck, Deck<Ticket> fullTicketDeck) {
        this.activePlayer = startingPlayer;
        this.completedRoutes = new ArrayList<>();
        this.carDrawDeck = fullCarDeck;
        this.carDrawDeck.shuffle();
        this.openCars = new TTOpenCards<Car>(Car.class, 5);
        fillOpenCars();
        this.ticketDrawDeck = fullTicketDeck;
        this.ticketDrawDeck.shuffle();
    }

    public Player getActivePlayer() {
        return this.activePlayer;
    }

    public void setActivePlayer(Player nextPlayer) {
        this.activePlayer = nextPlayer;
    }

    public List<CompletedRoute> getCompletedRoutes() {
        return this.completedRoutes;
    }

    public Deck<Car> getCarDrawDeck() {
        return this.carDrawDeck;
    }

    public OpenCards<Car> getOpenCards() {
        return this.openCars;
    }

    public Car drawCar(int index) {
        if (index == -1) {
            // TODO only allow this if there are cars in the deck
            this.activePlayer.getState().setMustDrawSecondCar(!this.activePlayer.getState().mustDrawSecondCar());
            return this.carDrawDeck.drawCard();
        } else {
            Car drawnCar = this.openCars.getCard(index);
            boolean firstDraw = !this.activePlayer.getState().mustDrawSecondCar();
            if (firstDraw) {
                if (drawnCar.getFlavor() != Flavor.RAINBOW) {
                    this.activePlayer.getState().setMustDrawSecondCar(true);
                }
            } else { // second draw
                if (drawnCar.getFlavor() == Flavor.RAINBOW) {
                    throw new RuntimeException("Sorry. Can't select Rainbow for second draw");
                }
                // TODO deal with error condition where drawing rainbow on second card
                this.activePlayer.getState().setMustDrawSecondCar(false);
            }
            drawnCar = this.openCars.retrieveCard(index, this.carDrawDeck.drawCard());
            shuffleOpenCarsIf3Rainbows();
            return drawnCar;
        }
    }

    public Deck<Ticket> getTicketDrawDeck() {
        return this.ticketDrawDeck;
    }

    public String toString() {
        return "board state";
    }

    private void fillOpenCars() {
        List<Car> initialCards = new ArrayList<>();
        for (int i = 0; i < 5; ++i) {
            Car drawnCar = this.carDrawDeck.drawCard();
            if (drawnCar != null) {
                initialCards.add(drawnCar);
            }
        }
        this.openCars.setCards(initialCards);
        shuffleOpenCarsIf3Rainbows();
    }

    private void shuffleOpenCarsIf3Rainbows() {
        long numOpenRainbows = this.openCars.getCards().stream().filter(car -> car.getFlavor() == Flavor.RAINBOW).count();
        if (numOpenRainbows >= 3) {
            for (Car car : this.openCars.getCards()) {
                this.carDrawDeck.addCardToDiscards(car);
            }
            this.openCars.clear();
        }
        long numNonRainbows = getNumNonRainbowCarsInCarDeck();
        if (numNonRainbows >= 3) {
            fillOpenCars();
        } else {
            // TODO flag that we are horked?
        }
    }

    private long getNumNonRainbowCarsInCarDeck() {
        return this.getCarDrawDeck().getCards().stream().filter(car -> car.getFlavor() != Flavor.RAINBOW).count() +
                this.getCarDrawDeck().getDiscards().stream().filter(car -> car.getFlavor() != Flavor.RAINBOW).count();
    }
}