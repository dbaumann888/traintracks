package traintracks.engine.game;

import traintracks.api.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    public List<Route> getUncompletedRoutes(Board board) {
        return board.getRouteMap().getRoutes()
                .stream()
                .filter(route -> !completedRoutesContainsRoute(route)).collect(Collectors.toList());
    }

    public List<Route> getCompletableRoutes(Board board) {
        List<Route> uncompletedRoutes = getUncompletedRoutes(board);
        return uncompletedRoutes.stream().filter(route -> activePlayerCanCompleteRoute(route)).collect(Collectors.toList());
    }

    public boolean completedRoutesContainsRoute(Route route) {
        return this.completedRoutes.stream().filter(completedRoute -> completedRoute.getRoute() == route).count() > 0;
    }

    private boolean activePlayerCanCompleteRoute(Route route) {
        Flavor flavorNeeded = route.getFlavor();
        int numCarsNeeded = route.getLength();
        List<Car> heldCars = this.activePlayer.getState().getCars();
        if (flavorNeeded != Flavor.RAINBOW) {
            long numCarsHeld = heldCars.stream().filter(car -> (car.getFlavor() == flavorNeeded) || (car.getFlavor() == Flavor.RAINBOW)).count();
            return numCarsHeld >= numCarsNeeded;
        } else {
            for (Flavor flavor : Flavor.values()) {
                if (flavor != Flavor.RAINBOW) {
                    long numCarsHeld = heldCars.stream().filter(car -> (car.getFlavor() == flavor) || (car.getFlavor() == Flavor.RAINBOW)).count();
                    if (numCarsHeld >= numCarsNeeded) {
                        return true;
                    }
                }
            }
            return false;
        }
    }

    public Deck<Car> getCarDrawDeck() {
        return this.carDrawDeck;
    }

    public OpenCards<Car> getOpenCards() {
        return this.openCars;
    }

    public Car drawCar(int index) {
        Car drawnCar;
        boolean fromDeck = true;
        if (index == -1) {
            drawnCar = this.carDrawDeck.drawCard();
        } else {
            fromDeck = false;
            boolean secondDraw = this.activePlayer.getState().mustDrawSecondCar();
            if (secondDraw) {
                drawnCar = this.openCars.getCard(index);
                if ((drawnCar !=null) && (drawnCar.getFlavor() == Flavor.RAINBOW)) {
                    throw new RuntimeException("Sorry. Can't select Rainbow for second draw");
                }
            }
            drawnCar = this.openCars.swapCard(index, this.carDrawDeck.drawCard());
            shuffleOpenCarsIf3Rainbows();
        }
        setMustDrawSecondCar(this.activePlayer.getState(), fromDeck, drawnCar);
        return drawnCar;
    }

    private void setMustDrawSecondCar(PlayerState playerState, boolean fromDeck, Car drawnCar) {
        boolean firstDraw = !playerState.mustDrawSecondCar();
        boolean getSecondDraw = firstDraw && (drawnCar != null) &&
                (fromDeck || (drawnCar.getFlavor() != Flavor.RAINBOW));
        playerState.setMustDrawSecondCar(getSecondDraw);
    }

    public void discardCar(Car car) {
        this.carDrawDeck.addCardToDiscards(car);
    }

    public Deck<Ticket> getTicketDrawDeck() {
        return this.ticketDrawDeck;
    }

    public String toString() {
        return "board state";
    }

    private void fillOpenCars() {
        for (int i = 0; i < 5; ++i) {
            Car drawnCar = this.carDrawDeck.drawCard();
            if (drawnCar != null) {
                this.openCars.swapCard(i, drawnCar);
            }
        }
        shuffleOpenCarsIf3Rainbows();
    }

    private void shuffleOpenCarsIf3Rainbows() {
        long numOpenRainbows = this.openCars.getCards().stream().filter(car -> (car != null) && (car.getFlavor() == Flavor.RAINBOW)).count();
        if ((numOpenRainbows >= 3) && (getNumNonRainbowCarsInPlay() > 2)) {
            for (int i = 0; i < this.openCars.getNumOpenings(); ++i) {
                Car car = this.openCars.swapCard(i, null);
                this.carDrawDeck.addCardToDiscards(car);
            }
            fillOpenCars();
        }
    }

    private long getNumNonRainbowCarsInPlay() {
        return this.getCarDrawDeck().getCards().stream().filter(car -> (car != null) && (car.getFlavor() != Flavor.RAINBOW)).count() +
                this.getCarDrawDeck().getDiscards().stream().filter(car -> (car != null) && (car.getFlavor() != Flavor.RAINBOW)).count() +
                this.openCars.getCards().stream().filter(car -> (car != null) && (car.getFlavor() != Flavor.RAINBOW)).count();
    }
}