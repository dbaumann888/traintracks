package traintracks.server.engine.game;

import traintracks.api.Board;
import traintracks.api.Game;
import traintracks.api.GameState;
import traintracks.api.Player;
import traintracks.api.Ticket;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TTGame implements Game {
    private final UUID id;
    private final String name;
    private final List<Player> players;
    private final Board board;
    private final GameState gameState;

    public TTGame(String name, List<Player> players, Board board) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.players = players;
        this.board = board;
        this.gameState = new TTGameState(players.get(0));
        initializePlayers();
    }

    public UUID getId() { return this.id; }
    public String getName() { return this.name; }
    public List<Player> getPlayers() { return this.players; }
    public Board getBoard() { return this.board; }
    public GameState getGameState() { return this.gameState; }

    public String toString() { return this.getBoard().getName(); }
    public String toDisplayString() { return this.getBoard().getName(); } // TODO fixme

    private void initializePlayers() {
        for (Player player : this.players) {
            player.getState().setCarriageCount(this.board.getPlayerCarriageCount());
            for (int i = 0; i < 4; ++i) {
                player.getState().getCars().add(this.board.getBoardState().getCarDrawDeck().drawCard());
            }
            List<Ticket> newTickets = new ArrayList<>();
            for (int i = 0; i < 3; ++i) {
                newTickets.add(this.board.getBoardState().getTicketDrawDeck().drawCard());
            }
            player.getState().addPendingTickets(newTickets, 2);
        }
    }

}
