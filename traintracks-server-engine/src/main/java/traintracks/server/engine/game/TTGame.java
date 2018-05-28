package traintracks.server.engine.game;

import traintracks.api.Board;
import traintracks.api.BoardState;
import traintracks.api.BuildRouteTurn;
import traintracks.api.Car;
import traintracks.api.CompletedRoute;
import traintracks.api.DrawCarTurn;
import traintracks.api.Game;
import traintracks.api.Player;
import traintracks.api.Route;
import traintracks.api.Ticket;
import traintracks.api.Turn;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TTGame implements Game {
    private final UUID id;
    private final List<Player> players;
    private final Board board;
    private final BoardState boardState;
    private final List<Turn> history;

    public TTGame(List<Player> players, Board board) {
        this.id = UUID.randomUUID();
        this.players = players;
        this.board = board;


        this.boardState = new TTBoardState(players.get(0), board.getCarDeck(), board.getTicketDeck());
        this.history = new ArrayList<Turn>();
        initializePlayers();
    }

    public List<Player> getPlayers() { return this.players; }
    public Board getBoard() { return this.board; }
    public BoardState getBoardState() { return this.boardState; }
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
            List<Ticket> newTickets = new ArrayList<>();
            for (int i = 0; i < 3; ++i) {
                newTickets.add(this.board.getTicketDeck().drawCard());
            }
            player.getState().addPendingTickets(newTickets, 2);
        }
    }

    private void nextPlayer() {
        int index = this.players.indexOf(this.boardState.getActivePlayer());
        int nextIndex = (index + 1) % this.players.size();
        this.boardState.setActivePlayer(this.players.get(nextIndex));
    }
    public void applyTurn(Turn turn) {
        switch (turn.getType()) {
            case DISCARD_PENDING_TICKETS:
                applyTurnDiscardPendingTickets(turn);
                break;
            case BUILD_ROUTE:
                applyTurnBuildRoute((BuildRouteTurn)turn);
                break;
            case DRAW_TRAIN_CAR:
                applyTurnDrawCar((DrawCarTurn)turn);
                break;
            case DRAW_TICKETS:
                applyTurnDrawTickets(turn);
                break;
        }

        System.out.println(turn);
    }

    private void applyTurnDiscardPendingTickets(Turn turn) {
        // TODO implement discard tickets
    }

    private void applyTurnBuildRoute(BuildRouteTurn routeTurn) {
        Player player = routeTurn.getPlayer();
        Route route = routeTurn.getRoute();
        if (this.boardState.completedRoutesContainsRoute(routeTurn.getRoute())) {
            throw new RuntimeException("The route " + route + " has already been completed");
        }

        for (Car car : routeTurn.getCars()) {
            if (!player.getState().getCars().contains(car)) {
                throw new RuntimeException("You don't have this car:" + car);
            }
        }
        for (Car car : routeTurn.getCars()) {
            player.getState().getCars().remove(car);
            this.boardState.discardCar(car);
        }
        CompletedRoute completedRoute = new TTCompletedRoute(routeTurn.getRoute(), player);
        this.boardState.getCompletedRoutes().add(completedRoute);
        player.getState().setCarriageCount(player.getState().getCarriageCount() - routeTurn.getRoute().getLength());
        player.getState().updateScore(player, this.boardState.getCompletedRoutes());
        // TODO update other players as well because they may no longer the longest route holder: game.updateScores()
        nextPlayer();
    }

    private void applyTurnDrawCar(DrawCarTurn carTurn) {
        Car drawnCar = this.boardState.drawCar(carTurn.getIndex());
        if (drawnCar != null) {
            carTurn.getPlayer().getState().getCars().add(drawnCar);
        }
        if (!carTurn.getPlayer().getState().mustDrawSecondCar()) {
            nextPlayer();
        }
    }

    private void applyTurnDrawTickets(Turn turn) {
        int numRemainingTickets = board.getTicketDeck().getCards().size();
        List<Ticket> newTickets = new ArrayList<>();
        for (int i = 0; i < Math.min(3, numRemainingTickets); ++i) {
            newTickets.add(board.getTicketDeck().drawCard());
        }
        turn.getPlayer().getState().addPendingTickets(newTickets, 1);
        nextPlayer();
    }
}
