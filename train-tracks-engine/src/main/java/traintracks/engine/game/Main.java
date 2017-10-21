package traintracks.engine.game;

import com.google.common.collect.ImmutableList;
import traintracks.api.Board;
import traintracks.api.Car;
import traintracks.api.Flavor;
import traintracks.api.Game;
import traintracks.api.Player;
import traintracks.api.PlayerType;
import traintracks.api.Route;
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
            System.out.println("Choose a turn: (b)uild a route, (d)raw a car, (t)ake tickets: ");
            String turnTypeChar = keyboard.next();

            switch (turnTypeChar.charAt(0)) {
                case 'b':
                    Route route = readRoute(keyboard, board);
                    Flavor flavor = route.getFlavor();
                    if (flavor == Flavor.RAINBOW) {
                        flavor = readFlavor(keyboard);
                    }
                    return new TTBuildRouteTurn(board.getBoardState().getActivePlayer(), route, flavor);
                case 'd':
                    int index = readIndex(keyboard, board);
                    return new TTDrawCarTurn(board.getBoardState().getActivePlayer(), index);
                case 't':
                    return new TTTurn(board.getBoardState().getActivePlayer(), TurnType.DRAW_TICKETS);
            }

            return new TTTurn(board.getBoardState().getActivePlayer(), TurnType.BUILD_LINE);
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

    private static int readIndex(Scanner keyboard, Board board) {
        while (true) {
            StringBuffer sb = new StringBuffer();
            sb.append("Choose a card: ");
            List<Car> cars = board.getBoardState().getOpenCards().getCards();
            for (int i = 0; i < cars.size(); ++i) {
                sb.append("(").append(i).append(")" ).append(cars.get(i)).append(" ");
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
