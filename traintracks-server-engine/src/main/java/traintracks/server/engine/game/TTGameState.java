package traintracks.server.engine.game;

import traintracks.api.BuildRouteTurn;
import traintracks.api.Car;
import traintracks.api.CompletedRoute;
import traintracks.api.DrawCarTurn;
import traintracks.api.Game;
import traintracks.api.GameState;
import traintracks.api.Player;
import traintracks.api.Route;
import traintracks.api.Ticket;
import traintracks.api.Turn;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TTGameState implements GameState {
    private final UUID id;
    private Player activePlayer;
    private final List<Turn> history;

    public TTGameState(Player startingPlayer) {
        this.activePlayer = startingPlayer;
        this.id = UUID.randomUUID();
        this.history = new ArrayList<Turn>();
    }

    public UUID getId() { return this.id; }

    public Player getActivePlayer() {
        return this.activePlayer;
    }

    public void setActivePlayer(Player nextPlayer) {
        this.activePlayer = nextPlayer;
    }

    public boolean over() { return false; } // TODO fixme

    public List<Turn> getTurnHistory() { return this.history; }

    public void applyTurn(Game game, Turn turn) {
        switch (turn.getType()) {
            case DISCARD_PENDING_TICKETS:
                applyTurnDiscardPendingTickets(game, turn);
                break;
            case BUILD_ROUTE:
                applyTurnBuildRoute(game, (BuildRouteTurn)turn);
                break;
            case DRAW_TRAIN_CAR:
                applyTurnDrawCar(game, (DrawCarTurn)turn);
                break;
            case DRAW_TICKETS:
                applyTurnDrawTickets(game, turn);
                break;
        }

        System.out.println(turn);
    }

    private void applyTurnDiscardPendingTickets(Game game, Turn turn) {
        // TODO implement discard tickets
    }

    private void applyTurnBuildRoute(Game game, BuildRouteTurn routeTurn) {
        Player player = routeTurn.getPlayer();
        Route route = routeTurn.getRoute();
        if (game.getBoard().getBoardState().completedRoutesContainsRoute(routeTurn.getRoute())) {
            throw new RuntimeException("The route " + route + " has already been completed");
        }

        for (Car car : routeTurn.getCars()) {
            if (!player.getState().getCars().contains(car)) {
                throw new RuntimeException("You don't have this car:" + car);
            }
        }
        for (Car car : routeTurn.getCars()) {
            player.getState().getCars().remove(car);
            game.getBoard().getBoardState().discardCar(car);
        }
        CompletedRoute completedRoute = new TTCompletedRoute(routeTurn.getRoute(), player);
        game.getBoard().getBoardState().getCompletedRoutes().add(completedRoute);
        player.getState().setCarriageCount(player.getState().getCarriageCount() - routeTurn.getRoute().getLength());
        player.getState().updateScore(player, game.getBoard().getBoardState().getCompletedRoutes());
        // TODO update other players as well because they may no longer the longest route holder: game.updateScores()
        nextPlayer(game);
    }

    private void applyTurnDrawCar(Game game, DrawCarTurn carTurn) {
        Car drawnCar = game.getBoard().getBoardState().drawCar(game.getGameState().getActivePlayer(), carTurn.getIndex());
        if (drawnCar != null) {
            carTurn.getPlayer().getState().getCars().add(drawnCar);
        }
        if (!carTurn.getPlayer().getState().mustDrawSecondCar()) {
            nextPlayer(game);
        }
    }

    private void applyTurnDrawTickets(Game game, Turn turn) {
        int numRemainingTickets = game.getBoard().getBoardState().getTicketDrawDeck().getCards().size();
        List<Ticket> newTickets = new ArrayList<>();
        for (int i = 0; i < Math.min(3, numRemainingTickets); ++i) {
            newTickets.add(game.getBoard().getBoardState().getTicketDrawDeck().drawCard());
        }
        turn.getPlayer().getState().addPendingTickets(newTickets, 1);
        nextPlayer(game);
    }

    private void nextPlayer(Game game) {
        int index = game.getPlayers().indexOf(getActivePlayer());
        int nextIndex = (index + 1) % game.getPlayers().size();
        setActivePlayer(game.getPlayers().get(nextIndex));
    }

}
