package traintracks.client.cli;

import traintracks.api.Car;
import traintracks.api.Flavor;
import traintracks.api.Game;
import traintracks.api.OpenCards;
import traintracks.api.Player;
import traintracks.api.PlayerState;
import traintracks.api.Route;
import traintracks.api.Ticket;
import traintracks.api.Turn;
/*
import traintracks.api.TurnType;
import traintracks.engine.game.TTBuildRouteTurn;
import traintracks.engine.game.TTDrawCarTurn;
import traintracks.engine.game.TTTurn;
*/

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

public class ClientCommandLineInteraction {

    private Game game;
    private Scanner keyboard;

    public ClientCommandLineInteraction(Game game, Scanner keyboard) {
        this.game = game;
        this.keyboard = keyboard;
    }

    public Turn readTurn() {
        while (true) {
            System.out.println("- - - - - - - - - - - - - - - - - -");
            System.out.println("Active player: " + this.game.getBoardState().getActivePlayer());
            Player player = this.game.getBoardState().getActivePlayer();
            PlayerState playerState = player.getState();
            displayCars(playerState.getCars());
            if (playerState.hasPendingTickets()) {
                if ((playerState.getPendingTickets().size()) == playerState.getPendingTicketsMustKeepCount()) {
                    playerState.keepPendingTickets(player, this.game.getBoardState().getCompletedRoutes());
                } else {
                    Ticket ticket = readDiscardTicket();
                    if (ticket != null) {
                        playerState.discardPendingTicket(ticket);
                        this.game.getBoardState().getTicketDrawDeck().addCardToBottom(ticket);
                    }
                    if ((ticket == null) || (playerState.getPendingTickets().size()) == 1) {
                        playerState.keepPendingTickets(player, this.game.getBoardState().getCompletedRoutes());
                    }
                }
            } else {
                int numRemainingTickets = this.game.getBoard().getTicketDeck().getCards().size();
                char actionChar;
                if (playerState.mustDrawSecondCar()) {
                    actionChar = 'd';
                } else {
                    String takeTicketsOption = numRemainingTickets >= 3 ? ", (t)ake tickets" : numRemainingTickets == 0 ? "" : ", (t)ake tickets but only " + numRemainingTickets + " left!";
                    System.out.println("Choose a turn: (b)uild a route, (d)raw a car" + takeTicketsOption + ": ");
                    String turnTypeChar = this.keyboard.next();
                    actionChar = turnTypeChar.charAt(0);
                }

                switch (actionChar) {
                    case 'b':
                        Route route = readRoute();
                        // TODO shortcut if player doesn't have any rainbow cars
                        List<Car> cars = readCars(route);
                        return null; // TODO FIX -- new TTBuildRouteTurn(this.game.getBoardState().getActivePlayer(), route, cars);
                    case 'd':
                        int index = readCarDrawIndex();
                        return null; // TODO FIX --  new TTDrawCarTurn(this.game.getBoardState().getActivePlayer(), index);
                    case 't':
                        if (numRemainingTickets > 0) {
                            return null;  // TODO FIX -- new TTTurn(this.game.getBoardState().getActivePlayer(), TurnType.DRAW_TICKETS);
                        }
                }
            }
        }
    }

    private Ticket readDiscardTicket() {
        List<Ticket> pendingTickets = this.game.getBoardState().getActivePlayer().getState().getPendingTickets();
        while (true) {
            StringBuffer sb = new StringBuffer("Choose a ticket to discard: (0) none");
            for (int i = 0; i < pendingTickets.size(); ++i) {
                sb.append(", (").append(i + 1).append(") ").append(pendingTickets.get(i).toString());
            }
            System.out.println(sb.toString());
            String ticketIndex = this.keyboard.next();
            int index = Integer.parseInt(ticketIndex) - 1;
            if (index == -1) {
                return null;
            } else if ((index >= 0) && (index < pendingTickets.size())) {
                return pendingTickets.get(index);
            } else {
                System.out.println("try again");
            }
        }
    }

    private Route readRoute() {
        final AtomicInteger i = new AtomicInteger(0);
        StringBuffer sb = new StringBuffer();
        sb.append("Choose a route:");
        List<Route> completableRoutes = this.game.getBoardState().getCompletableRoutes(this.game.getBoard());
        completableRoutes.forEach((route) -> { sb.append(" (").append(i.get()).append(")").append(route.toString());
            i.addAndGet(1); });
        System.out.println(sb.toString());
        String routeIndex = this.keyboard.next();
        int index = Integer.parseInt(routeIndex);
        return completableRoutes.get(index);
    }

    private List<Car> readCars(Route route) {
        Flavor chosenFlavor = route.getFlavor();
        if (chosenFlavor == Flavor.RAINBOW) {
            chosenFlavor = readFlavor();
        }
        final Flavor finalFlavor = chosenFlavor;
        List<Car> cars = this.game.getBoardState().getActivePlayer().getState().getCars();
        long numFlavorCars = cars.stream().filter(car -> car.getFlavor() == finalFlavor).count();
        long numRainbows = cars.stream().filter(car -> car.getFlavor() == Flavor.RAINBOW).count();
        long numRainbowsToUse = readNumRainbowsToUse(chosenFlavor, route, numFlavorCars, numRainbows);

        long numFlavorCarsLeft = route.getLength() - numRainbowsToUse;
        long numRainbowCarsLeft = numRainbowsToUse;
        List<Car> chosenCars = new ArrayList<>();
        for (Car car : cars) {
            if ((numFlavorCarsLeft > 0) && (car.getFlavor() == chosenFlavor)) {
                chosenCars.add(car);
                numFlavorCarsLeft -= 1;
            } else if ((numRainbowCarsLeft > 0) && (car.getFlavor() == Flavor.RAINBOW)) {
                chosenCars.add(car);
                numRainbowCarsLeft -= 1;
            }
        }
        return chosenCars;
    }

    private long readNumRainbowsToUse(Flavor flavor, Route route,
                                     long numFlavorCars, long numRainbows)
    {
        long minRainbows = Math.max(route.getNumEngines(), route.getLength() - numFlavorCars);
        long maxRainbows = Math.min(route.getLength(), numRainbows);
        if (minRainbows > maxRainbows) {
            // TODO throw a different exception
            throw new RuntimeException("You can't build that route!");
        }
        if ((numRainbows == 0) || (flavor == Flavor.RAINBOW)) {
            return 0;
        }
        while (true) {
            StringBuffer sb = new StringBuffer();
            sb.append("You have " + numFlavorCars + " " + flavor.toString() + " cars and");
            sb.append(" " + numRainbows + " " + Flavor.RAINBOW.toString() + " cars.\n");
            sb.append("Choose the number of rainbows cars to use [" + minRainbows + " - " + maxRainbows + "]:");
            System.out.println(sb.toString());
            String numRainbowsChar = this.keyboard.next();
            long numRainbowsChosen = Long.parseLong(numRainbowsChar);
            if ((numRainbowsChosen >= minRainbows) && (numRainbowsChosen <= maxRainbows)) {
                return numRainbowsChosen;
            } else {
                System.out.println("invalid choice");
            }
        }
    }

    private Flavor readFlavor() {
        while (true) {
            StringBuffer sb = new StringBuffer();
            sb.append("Choose a flavor of route:");
            for (Flavor flavor : Flavor.values()) {
                sb.append(" (").append(flavor.getSymbol()).append(")").append(flavor.toString());
            }
            System.out.println(sb.toString());
            String flavorChar = this.keyboard.next();
            for (Flavor flavor : Flavor.values()) {
                if (flavor.getSymbol() == flavorChar.charAt(0)) {
                    return flavor;
                }
            }
        }
    }

    private int readCarDrawIndex() {
        while (true) {
            StringBuffer sb = new StringBuffer();
            sb.append("Choose a card: ");
            OpenCards openCards = this.game.getBoardState().getOpenCards();
            List<Car> cars = openCards.getCards();
            for (int i = 0; i < cars.size(); ++i) {
                char displayIndex = (char)('0' + i);
                Car car = cars.get(i);
                if ((car != null) && (car.getFlavor() == Flavor.RAINBOW) && (this.game.getBoardState().getActivePlayer().getState().mustDrawSecondCar())) {
                    displayIndex = 'X';
                }
                sb.append("(").append(displayIndex).append(")" ).append(car).append(" ");
            }
            String deckString = this.game.getBoard().getCarDeck().isEmpty() ? "deck(empty)" : "deck";
            sb.append("(").append(cars.size()).append(")").append(deckString).append(": ");
            System.out.println(sb.toString());
            String indexChar = this.keyboard.next();

            int index = Integer.parseInt(indexChar);

            if ((index >= 0) && (index <= cars.size())) {
                if (index == cars.size()) {
                    return -1;
                }
                return index;
            }
        }
    }

    private void displayCars(List<Car> cars) {
        System.out.println(cars.toString());
    }

}