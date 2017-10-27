package traintracks.engine.game;

import traintracks.api.Board;
import traintracks.api.Game;
import traintracks.api.Player;
import traintracks.api.Turn;

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
        initializePlayers();
    }

    public List<Player> getPlayers() { return this.players; }
    public Board getBoard() { return this.board; }
    public List<Turn> getTurnHistory() { return this.history; }
    public boolean over() { return false; } // TODO fixme
    public String toString() { return this.getBoard().getName(); }
    public String toDisplayString() { return this.getBoard().getName(); } // TODO fixme

    private void initializePlayers() {
        for (Player player : this.players) {
            player.getState().setCarriageCount(this.board.getPlayerCarriageCount());
            for (int i = 0; i < 4; ++i) {
                player.getState().getCars().add(this.board.getCarDeck().drawCard());
            }
            for (int i = 0; i < 1; ++i) {
                player.getState().getTickets().add(this.board.getTicketDeck().drawCard());
            }
        }
    }

    private void nextPlayer() {
        int index = this.players.indexOf(this.board.getBoardState().getActivePlayer());
        int nextIndex = (index + 1) % this.players.size();
        this.board.getBoardState().setActivePlayer(this.players.get(nextIndex));
    }
    public void applyTurn(Turn turn) {
        switch (turn.getType()) {
            case DISCARD_PENDING_TICKETS:
                break;
            case BUILD_LINE:
                TTBuildRouteTurn routeTurn = (TTBuildRouteTurn) turn;
                nextPlayer();
                break;
            case DRAW_TRAIN_CAR:
                TTDrawCarTurn carTurn = (TTDrawCarTurn) turn;
                carTurn.getPlayer().getState().getCars().add(this.board.getBoardState().drawCar(carTurn.getIndex()));
                if (!carTurn.getPlayer().getState().mustDrawSecondCar()) {
                    nextPlayer();
                }
                break;
            case DRAW_TICKETS:
                for (int i = 0; i < 3; ++i) {
                    turn.getPlayer().getState().addPendingTicket(board.getTicketDeck().drawCard());
                }
                nextPlayer();
                break;
        }

        System.out.println(turn);
    }

}
