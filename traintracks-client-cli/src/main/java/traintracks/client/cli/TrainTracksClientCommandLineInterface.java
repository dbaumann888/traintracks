package traintracks.client.cli;

/*
import com.google.common.collect.ImmutableList;
import traintracks.api.Board;
import traintracks.api.Game;
import traintracks.api.Player;
import traintracks.api.PlayerType;
import traintracks.api.Turn;
import traintracks.engine.game.TTBoard;
import traintracks.engine.game.TTGame;
import traintracks.engine.game.TTPlayer;
import traintracks.engine.game.TTSetup;

import java.awt.*;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import java.util.Scanner;
*/
public class TrainTracksClientCommandLineInterface {
    public static void main(String[] argv) {
/* TODO refactor out the common CLI code and call server to get this info
        Board board = TTBoard.getTTBoard(TTSetup.NORTH_AMERICA);
        Player player1 = new TTPlayer("muish", Color.BLUE, PlayerType.HUMAN, board);
        Player player2 = new TTPlayer("dantrayal", Color.BLACK, PlayerType.HUMAN, board);
        Player player3 = new TTPlayer("magz", Color.GREEN, PlayerType.HUMAN, board);
        List<Player> players = ImmutableList.of(player1, player2, player3);
        Game game = new TTGame(players, board);
        Scanner keyboard = new Scanner(System.in);

        ClientCommandLineInteraction cli = new ClientCommandLineInteraction(game, keyboard);
        while (!game.over()) {
            try {
                Turn turn = cli.readTurn();
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
*/
    }
}

