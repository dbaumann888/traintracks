package traintracks.server.engine.game;

import com.google.common.collect.ImmutableList;
import junit.framework.TestCase;
import org.junit.Test;
import traintracks.api.Board;
import traintracks.api.CompletedRoute;
import traintracks.api.Flavor;
import traintracks.api.Player;
import traintracks.api.PlayerScore;
import traintracks.api.Route;
import traintracks.api.RouteScoring;
import traintracks.api.RouteType;
import traintracks.api.Station;
import traintracks.api.Ticket;

import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class TestTTPlayerScore extends TestCase {

    private Board mockBoard;
    private Player mockPlayer1;
    private Player mockPlayer2;
    private Player mockPlayer3;
    private RouteScoring mockRouteScoring;
    private Station stationA, stationB, stationC, stationD, stationE;
    private Route routeAB1, routeBA1, routeBC3, routeCB1, routeCD2, routeCA1, routeDA1, routeBD1, routeCD1, routeED1;

    public TestTTPlayerScore() {
        this.mockBoard = mock(Board.class);
        this.mockPlayer1 = mock(Player.class);
        this.mockPlayer2 = mock(Player.class);
        this.mockPlayer3 = mock(Player.class);
        this.mockRouteScoring = mock(RouteScoring.class);
        when(this.mockRouteScoring.getLongestTrainScore()).thenReturn(1000);
        when(this.mockRouteScoring.getScore(1)).thenReturn(1);
        when(this.mockRouteScoring.getScore(2)).thenReturn(2);
        when(this.mockRouteScoring.getScore(3)).thenReturn(3);
        this.stationA = new TTStation("A", new Point(1, 1));
        this.stationB = new TTStation("B", new Point(1, 1));
        this.stationC = new TTStation("C", new Point(1, 1));
        this.stationD = new TTStation("D", new Point(1, 1));
        this.stationE = new TTStation("E", new Point(1, 1));

        this.routeAB1 = new TTRoute(this.stationA, this.stationB, RouteType.STANDARD, 1, Flavor.BLUEBERRY, 0);
        this.routeBA1 = new TTRoute(this.stationB, this.stationA, RouteType.STANDARD, 1, Flavor.BLUEBERRY, 0);
        this.routeBC3 = new TTRoute(this.stationB, this.stationC, RouteType.STANDARD, 3, Flavor.BLUEBERRY, 0);
        this.routeCB1 = new TTRoute(this.stationC, this.stationB, RouteType.STANDARD, 1, Flavor.BLUEBERRY, 0);
        this.routeCD2 = new TTRoute(this.stationC, this.stationD, RouteType.STANDARD, 2, Flavor.BLUEBERRY, 0);
        this.routeCA1 = new TTRoute(this.stationC, this.stationA, RouteType.STANDARD, 1, Flavor.BLUEBERRY, 0);
        this.routeDA1 = new TTRoute(this.stationD, this.stationA, RouteType.STANDARD, 1, Flavor.BLUEBERRY, 0);
        this.routeBD1 = new TTRoute(this.stationB, this.stationD, RouteType.STANDARD, 1, Flavor.BLUEBERRY, 0);
        this.routeCD1 = new TTRoute(this.stationC, this.stationD, RouteType.STANDARD, 1, Flavor.BLUEBERRY, 0);
        this.routeED1 = new TTRoute(this.stationE, this.stationD, RouteType.STANDARD, 1, Flavor.BLUEBERRY, 0);
    }

    @Test
    public void testNewRouteScore() {
        PlayerScore score = new TTPlayerScore(this.mockPlayer1, this.mockRouteScoring);
        verifyScore(score, this.mockPlayer1, 0, 0, 0, 0, 0,
                false);
    }


    @Test
    public void testEmptyRouteScore() {
        PlayerScore player1Score = new TTPlayerScore(this.mockPlayer1, this.mockRouteScoring);
        PlayerScore player2Score = new TTPlayerScore(this.mockPlayer2, this.mockRouteScoring);
        PlayerScore player3Score = new TTPlayerScore(this.mockPlayer3, this.mockRouteScoring);

        List<CompletedRoute> completedRoutes = ImmutableList.of();
        player1Score.updateScore(completedRoutes, ImmutableList.of());
        player2Score.updateScore(completedRoutes, ImmutableList.of());
        player3Score.updateScore(completedRoutes, ImmutableList.of());
        verifyScore(player1Score, this.mockPlayer1, 0, 0, 0, 0,0,false);
        verifyScore(player2Score, this.mockPlayer2, 0, 0, 0, 0,0,false);
        verifyScore(player3Score, this.mockPlayer3, 0, 0, 0, 0,0,false);
    }

    @Test
    public void testRouteScore() {
        PlayerScore player1Score = new TTPlayerScore(this.mockPlayer1, this.mockRouteScoring);
        PlayerScore player2Score = new TTPlayerScore(this.mockPlayer2, this.mockRouteScoring);
        PlayerScore player3Score = new TTPlayerScore(this.mockPlayer3, this.mockRouteScoring);
        CompletedRoute crp1a = new TTCompletedRoute(this.routeAB1, mockPlayer1);
        CompletedRoute crp1b = new TTCompletedRoute(this.routeBC3, mockPlayer1);
        CompletedRoute crp2 = new TTCompletedRoute(this.routeCD2, mockPlayer2);

        List<CompletedRoute> completedRoutes = ImmutableList.of(crp1a, crp1b, crp2);
        player1Score.updateScore(completedRoutes, ImmutableList.of());
        player2Score.updateScore(completedRoutes, ImmutableList.of());
        player3Score.updateScore(completedRoutes, ImmutableList.of());
        verifyScore(player1Score, this.mockPlayer1, 4, 0, 1000, 1004,1004,true);
        verifyScore(player2Score, this.mockPlayer2, 2, 0, 0, 2,2,false);
        verifyScore(player3Score, this.mockPlayer3, 0, 0, 0, 0,0,false);
    }

    @Test
    public void testTicketScore() {
        ticketScoreHelper(ImmutableList.of(this.routeAB1), ImmutableList.of(this.routeBA1, this.routeCB1), ImmutableList.of(this.routeBC3),
                ImmutableList.of(new TTTicket(this.stationB, this.stationA, 8)),
                ImmutableList.of(new TTTicket(this.stationC, this.stationA, 9)),
                ImmutableList.of(new TTTicket(this.stationA, this.stationC, 7)),
                8, 9, -7);
    }

    @Test
    public void testTicketScoreComplex() {
        ticketScoreHelper(ImmutableList.of(this.routeAB1, this.routeBC3, this.routeCA1, this.routeDA1, this.routeBD1, this.routeCD1, this.routeED1),
                ImmutableList.of(),
                ImmutableList.of(this.routeBA1, this.routeCB1, this.routeCA1),
                ImmutableList.of(new TTTicket(this.stationA, this.stationE, 50)),
                ImmutableList.of(new TTTicket(this.stationD, this.stationA, 9)),
                ImmutableList.of(new TTTicket(this.stationA, this.stationD, 73)),
                50, -9, -73);
    }

    private void ticketScoreHelper(List<Route> player1Routes, List<Route> player2Routes, List<Route> player3Routes,
                                   List<Ticket> player1Tickets, List<Ticket> player2Tickets, List<Ticket> player3Tickets,
                                   int player1TicketScore, int player2TicketScore, int player3TicketScore)
    {
        PlayerScore player1Score = new TTPlayerScore(this.mockPlayer1, this.mockRouteScoring);
        PlayerScore player2Score = new TTPlayerScore(this.mockPlayer2, this.mockRouteScoring);
        PlayerScore player3Score = new TTPlayerScore(this.mockPlayer3, this.mockRouteScoring);
        List<CompletedRoute> completedRoutes = player1Routes.stream().map(r -> new TTCompletedRoute(r, this.mockPlayer1)).collect(Collectors.toList());
        completedRoutes.addAll(player2Routes.stream().map(r -> new TTCompletedRoute(r, this.mockPlayer2)).collect(Collectors.toList()));
        completedRoutes.addAll(player3Routes.stream().map(r -> new TTCompletedRoute(r, this.mockPlayer3)).collect(Collectors.toList()));
        player1Score.updateScore(completedRoutes, player1Tickets);
        player2Score.updateScore(completedRoutes, player2Tickets);
        player3Score.updateScore(completedRoutes, player3Tickets);
        assertEquals("wrong player1 ticket score", player1TicketScore, player1Score.getTicketScore());
        assertEquals("wrong player2 ticket score", player2TicketScore, player2Score.getTicketScore());
        assertEquals("wrong player3 ticket score", player3TicketScore, player3Score.getTicketScore());
    }

    private void verifyScore(PlayerScore score, Player player, int routeScore, int ticketScore, int longestScore,
                             int publicScore, int totalScore, boolean hasLongestTrain)
    {
        assertEquals("wrong player found", player, score.getPlayer());
        assertEquals("wrong route score", routeScore, score.getRouteScore());
        assertEquals("wrong ticket score", ticketScore, score.getTicketScore());
        assertEquals("wrong longest train score", longestScore, score.getLongestTrainScore());
        assertEquals("wrong public score", publicScore, score.getPublicScore());
        assertEquals("wrong total score", totalScore, score.getTotalScore());
        assertEquals("expected to be longest train", hasLongestTrain, score.hasLongestTrain());
    }
}
