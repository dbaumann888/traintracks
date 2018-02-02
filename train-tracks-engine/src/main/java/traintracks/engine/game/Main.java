package traintracks.engine.game;

import com.google.common.collect.ImmutableList;
import traintracks.api.Board;
import traintracks.api.Car;
import traintracks.api.Flavor;
import traintracks.api.Game;
import traintracks.api.OpenCards;
import traintracks.api.Player;
import traintracks.api.PlayerState;
import traintracks.api.PlayerType;
import traintracks.api.Route;
import traintracks.api.Ticket;
import traintracks.api.Turn;
import traintracks.api.TurnType;

import java.awt.Color;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    public static void main(String[] argv) {
        Board board = TTBoard.getTTBoard(TTSetup.NORTH_AMERICA);
        Player player1 = new TTPlayer("muish", Color.BLUE, PlayerType.HUMAN, board);
        Player player2 = new TTPlayer("dantrayal", Color.BLACK, PlayerType.HUMAN, board);
        Player player3 = new TTPlayer("magz", Color.GREEN, PlayerType.HUMAN, board);
        List<Player> players = ImmutableList.of(player1, player2, player3);
        Game game = new TTGame(players, board);
        Scanner keyboard = new Scanner(System.in);
        while (!game.over()) {
            try {
                Turn turn = readTurn(keyboard, game);
                game.applyTurn(turn);
            } catch (Exception e) {
                System.out.println("Uh oh!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                System.out.println("Exception: " + e.getMessage());
                StringWriter sw = new StringWriter();
                PrintWriter pw = new PrintWriter(sw);
                e.printStackTrace(pw);
                String sStackTrace = sw.toString(); // stack trace as a string
                System.out.println(sStackTrace);
            }
            System.out.println(game.toDisplayString());
        }
    }

    private static Turn readTurn(Scanner keyboard, Game game) {
        while (true) {
            Player activePlayer = game.getBoardState().getActivePlayer();
            System.out.println("- - - - - - - - - - - - - - - - - -");
            System.out.println("Active player: " + activePlayer);
            System.out.println(activePlayer.getState().getScore().toString());
            Player player = game.getBoardState().getActivePlayer();
            PlayerState playerState = player.getState();
            displayCars(playerState.getCars());
            if (playerState.hasPendingTickets()) {
                if ((playerState.getPendingTickets().size()) == playerState.getPendingTicketsMustKeepCount()) {
                    playerState.keepPendingTickets(game.getBoardState().getCompletedRoutes());
                } else {
                    Ticket ticket = readDiscardTicket(keyboard, game);
                    if (ticket != null) {
                        playerState.discardPendingTicket(ticket);
                        game.getBoardState().getTicketDrawDeck().addCardToBottom(ticket);
                    }
                    if ((ticket == null) || (playerState.getPendingTickets().size()) == 1) {
                        playerState.keepPendingTickets(game.getBoardState().getCompletedRoutes());
                    }
                }
            } else {
                int numRemainingTickets = game.getBoard().getTicketDeck().getCards().size();
                char actionChar;
                if (playerState.mustDrawSecondCar()) {
                    actionChar = 'd';
                } else {
                    String takeTicketsOption = numRemainingTickets >= 3 ? ", (t)ake tickets" : numRemainingTickets == 0 ? "" : ", (t)ake tickets but only " + numRemainingTickets + " left!";
                    System.out.println("Choose a turn: (b)uild a route, (d)raw a car" + takeTicketsOption + ": ");
                    String turnTypeChar = keyboard.next();
                    actionChar = turnTypeChar.charAt(0);
                }

                switch (actionChar) {
                    case 'b':
                        Route route = readRoute(keyboard, game);
                        // TODO shortcut if player doesn't have any rainbow cars
                        List<Car> cars = readCars(keyboard, route, game);
                        return new TTBuildRouteTurn(game.getBoardState().getActivePlayer(), route, cars);
                    case 'd':
                        int index = readCarDrawIndex(keyboard, game);
                        return new TTDrawCarTurn(game.getBoardState().getActivePlayer(), index);
                    case 't':
                        if (numRemainingTickets > 0) {
                            return new TTTurn(game.getBoardState().getActivePlayer(), TurnType.DRAW_TICKETS);
                        }
                }
            }
        }
    }

    private static Ticket readDiscardTicket(Scanner keyboard, Game game) {
        List<Ticket> pendingTickets = game.getBoardState().getActivePlayer().getState().getPendingTickets();
        while (true) {
            StringBuffer sb = new StringBuffer("Choose a ticket to discard: (0) none");
            for (int i = 0; i < pendingTickets.size(); ++i) {
                sb.append(", (").append(i + 1).append(") ").append(pendingTickets.get(i).toString());
            }
            System.out.println(sb.toString());
            String ticketIndex = keyboard.next();
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

    private static Route readRoute(Scanner keyboard, Game game) {
        final AtomicInteger i = new AtomicInteger(0);
        StringBuffer sb = new StringBuffer();
        sb.append("Choose a route:");
        List<Route> completableRoutes = game.getBoardState().getCompletableRoutes(game.getBoard());
        completableRoutes.forEach((route) -> { sb.append(" (").append(i.get()).append(")").append(route.toString());
                    i.addAndGet(1); });
        System.out.println(sb.toString());
        String routeIndex = keyboard.next();
        int index = Integer.parseInt(routeIndex);
        return completableRoutes.get(index);
    }

    private static List<Car> readCars(Scanner keyboard, Route route, Game game) {
        Flavor chosenFlavor = route.getFlavor();
        if (chosenFlavor == Flavor.RAINBOW) {
            chosenFlavor = readFlavor(keyboard);
        }
        final Flavor finalFlavor = chosenFlavor;
        List<Car> cars = game.getBoardState().getActivePlayer().getState().getCars();
        long numFlavorCars = cars.stream().filter(car -> car.getFlavor() == finalFlavor).count();
        long numRainbows = cars.stream().filter(car -> car.getFlavor() == Flavor.RAINBOW).count();
        long numRainbowsToUse = readNumRainbowsToUse(keyboard, chosenFlavor, route, numFlavorCars, numRainbows);

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

    private static long readNumRainbowsToUse(Scanner keyboard, Flavor flavor, Route route,
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
            String numRainbowsChar = keyboard.next();
            long numRainbowsChosen = Long.parseLong(numRainbowsChar);
            if ((numRainbowsChosen >= minRainbows) && (numRainbowsChosen <= maxRainbows)) {
                return numRainbowsChosen;
            } else {
                System.out.println("invalid choice");
            }
        }
    }

    private static Flavor readFlavor(Scanner keyboard) {
        while (true) {
            StringBuffer sb = new StringBuffer();
            sb.append("Choose a flavor of route:");
            for (Flavor flavor : Flavor.values()) {
                sb.append(" (").append(flavor.getSymbol()).append(")").append(flavor.toString());
            }
            System.out.println(sb.toString());
            String flavorChar = keyboard.next();
            for (Flavor flavor : Flavor.values()) {
                if (flavor.getSymbol() == flavorChar.charAt(0)) {
                    return flavor;
                }
            }
        }
    }

    private static int readCarDrawIndex(Scanner keyboard, Game game) {
        while (true) {
            StringBuffer sb = new StringBuffer();
            sb.append("Choose a card: ");
            OpenCards openCards = game.getBoardState().getOpenCards();
            List<Car> cars = openCards.getCards();
            for (int i = 0; i < cars.size(); ++i) {
                char displayIndex = (char)('0' + i);
                Car car = cars.get(i);
                if ((car != null) && (car.getFlavor() == Flavor.RAINBOW) && (game.getBoardState().getActivePlayer().getState().mustDrawSecondCar())) {
                    displayIndex = 'X';
                }
                sb.append("(").append(displayIndex).append(")" ).append(car).append(" ");
            }
            String deckString = game.getBoard().getCarDeck().isEmpty() ? "deck(empty)" : "deck";
            sb.append("(").append(cars.size()).append(")").append(deckString).append(": ");
            System.out.println(sb.toString());
            String indexChar = keyboard.next();

            int index = Integer.parseInt(indexChar);

            if ((index >= 0) && (index <= cars.size())) {
                if (index == cars.size()) {
                    return -1;
                }
                return index;
            }
        }
    }

    private static void displayCars(List<Car> cars) {
        System.out.println(cars.toString());
    }

}
