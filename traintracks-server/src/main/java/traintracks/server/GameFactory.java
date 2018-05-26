package traintracks.server;

import com.google.common.collect.ImmutableList;
import traintracks.api.Board;
import traintracks.api.Game;
import traintracks.api.Player;
import traintracks.api.PlayerType;
import traintracks.server.engine.game.TTBoard;
import traintracks.server.engine.game.TTGame;
import traintracks.server.engine.game.TTPlayer;
import traintracks.server.engine.game.TTSetup;

import java.awt.*;
import java.util.List;

public class GameFactory {
    private static GameFactory INSTANCE = null;
    private Game game = null;

    private GameFactory() {
        Board board = TTBoard.getTTBoard(TTSetup.NORTH_AMERICA);
        Player player1 = new TTPlayer("muish", Color.BLUE, PlayerType.HUMAN, board);
        Player player2 = new TTPlayer("dantrayal", Color.BLACK, PlayerType.HUMAN, board);
        Player player3 = new TTPlayer("magz", Color.GREEN, PlayerType.HUMAN, board);
        List<Player> players = ImmutableList.of(player1, player2, player3);
        this.game = new TTGame(players, board);
    }

    public static Game getGame() {
        if (INSTANCE == null) {
            synchronized (GameFactory.class) {
                if (INSTANCE == null) {
                    INSTANCE = new GameFactory();
                }
            }
        }
        return INSTANCE.game;
    }
}
