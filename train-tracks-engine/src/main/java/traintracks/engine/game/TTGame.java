package traintracks.engine.game;

import com.google.common.collect.ImmutableList;
import traintracks.api.Board;
import traintracks.api.Game;
import traintracks.api.Player;
import traintracks.api.PlayerType;
import traintracks.api.Turn;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TTGame implements Game {
    private final UUID id;
    private final List<Player> players;
    private final Board board;
    private final List<Turn> history;

    TTGame(List<Player> players, Board board) {
        this.id = UUID.randomUUID();
        this.players = players;
        this.board = board;
        this.history = new ArrayList<Turn>();
    }

    public List<Player> getPlayers() { return this.players; }
    public Board getBoard() { return this.board; }
    public List<Turn> getTurnHistory() { return this.history; }

    public void applyTurn(Turn turn) {
        // TODO implement
    }

    public static void main(String[] argv) {
        Player player1 = new TTPlayer("muish", Color.BLUE, PlayerType.HUMAN);
        Player player2 = new TTPlayer("dantrayal", Color.BLACK, PlayerType.HUMAN);
        Board board = TTBoard.getTTBoard(TTSetup.NORTH_AMERICA);
        List<Player> players = ImmutableList.of(player1, player2);
        Game game = new TTGame(players, board);
        System.out.println(game.toString());
    }
}
