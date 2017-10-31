package traintracks.engine.game;

import com.google.common.collect.ImmutableList;
import traintracks.api.Board;
import traintracks.api.Car;
import traintracks.api.Flavor;
import traintracks.api.Game;
import traintracks.api.Player;
import traintracks.api.PlayerType;
import traintracks.api.Route;
import traintracks.api.Ticket;
import traintracks.api.Turn;
import traintracks.api.TurnType;

import java.awt.Color;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] argv) {
        Player player1 = new TTPlayer("muish", Color.BLUE, PlayerType.HUMAN);
        Player player2 = new TTPlayer("dantrayal", Color.BLACK, PlayerType.HUMAN);
        Player player3 = new TTPlayer("magz", Color.BLACK, PlayerType.HUMAN);
        List<Player> players = ImmutableList.of(player1, player2, player3);
        Board board = TTBoard.getTTBoard(TTSetup.NORTH_AMERICA, player1);
        Game game = new TTGame(players, board);
        Scanner keyboard = new Scanner(System.in);
        while (!game.over()) {
            Turn turn = readTurn(keyboard, board);
            game.applyTurn(turn);
            System.out.println(game.toDisplayString());
        }
    }

    private static Turn readTurn(Scanner keyboard, Board board) {
        while (true) {
            System.out.println("- - - - - - - - - - - - - - - - - -");
            System.out.println("Active player: " + board.getBoardState().getActivePlayer());
            if (board.getBoardState().getActivePlayer().getState().hasPendingTickets()) {
                Ticket ticket = readDiscardTicket(keyboard, board);
                if (ticket != null) {
                    board.getBoardState().getActivePlayer().getState().discardPendingTicket(ticket);
                    board.getBoardState().getTicketDrawDeck().addCardToBottom(ticket);
                }
                if ((ticket == null) || (board.getBoardState().getActivePlayer().getState().getPendingTickets().size()) == 1) {
                    board.getBoardState().getActivePlayer().getState().keepPendingTickets();
                }
            } else {
                char actionChar;
                if (board.getBoardState().getActivePlayer().getState().mustDrawSecondCar()) {
                    actionChar = 'd';
                } else {
                    System.out.println("Choose a turn: (b)uild a route, (d)raw a car, (t)ake tickets: ");
                    String turnTypeChar = keyboard.next();
                    actionChar = turnTypeChar.charAt(0);
                }

                switch (actionChar) {
                    case 'b':
                        Route route = readRoute(keyboard, board);
                        Flavor flavor = route.getFlavor();
                        if (flavor == Flavor.RAINBOW) {
                            flavor = readFlavor(keyboard);
                        }
                        return new TTBuildRouteTurn(board.getBoardState().getActivePlayer(), route, flavor);
                    case 'd':
                        int index = readCarDrawIndex(keyboard, board);
                        return new TTDrawCarTurn(board.getBoardState().getActivePlayer(), index);
                    case 't':
                        return new TTTurn(board.getBoardState().getActivePlayer(), TurnType.DRAW_TICKETS);
                }

                return new TTTurn(board.getBoardState().getActivePlayer(), TurnType.BUILD_ROUTE);
            }
        }
    }

    private static Ticket readDiscardTicket(Scanner keyboard, Board board) {
        System.out.println("Choose a ticket to discard: (0) none, ");
        String ticketIndex = keyboard.next();
        int index = ticketIndex.charAt(0) - '0' - 1;
        if (index == -1) {
            return null;
        } else {
            // TODO handle out of bounds situation
            return board.getBoardState().getActivePlayer().getState().getPendingTickets().get(index);
        }
    }



    private static Route readRoute(Scanner keyboard, Board board) {
        int[] i = {0};
        StringBuffer sb = new StringBuffer();
        sb.append("Choose a route: ");
        board.getRouteMap().getRoutes().forEach((route) -> {sb.append("(").append(i[0]).append(") ").append(route.toString()); i[0] = i[0] + 1;});
        System.out.println(sb.toString());
        String routeIndex = keyboard.next();

        return board.getRouteMap().getRoutes().get(routeIndex.charAt(0) - '0');
    }

    private static Flavor readFlavor(Scanner keyboard) {
        while (true) {
            StringBuffer sb = new StringBuffer();
            sb.append("Choose a flavor of route: ");
            for (Flavor flavor : Flavor.values()) {
                sb.append("(").append(flavor.getSymbol()).append(") ").append(flavor.toString());
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

    private static int readCarDrawIndex(Scanner keyboard, Board board) {
        while (true) {
            StringBuffer sb = new StringBuffer();
            sb.append("Choose a card: ");
            List<Car> cars = board.getBoardState().getOpenCards().getCards();
            for (int i = 0; i < cars.size(); ++i) {
                char displayIndex = (char)('0' + i);
                Flavor flavor = cars.get(i).getFlavor();
                if ((flavor == Flavor.RAINBOW) && (board.getBoardState().getActivePlayer().getState().mustDrawSecondCar())) {
                    displayIndex = 'X';
                }
                sb.append("(").append(displayIndex).append(")" ).append(cars.get(i)).append(" ");
            }
            sb.append("(").append(cars.size()).append(")deck: ");
            System.out.println(sb.toString());
            String indexChar = keyboard.next();

            int index = indexChar.charAt(0) - '0';

            if ((index >= 0) && (index <= cars.size())) {
                if (index == cars.size()) {
                    return -1;
                }
                return index;
            }
        }
    }

}
