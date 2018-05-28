package traintracks.server.engine.game;

import traintracks.api.CompletedRoute;
import traintracks.api.Player;
import traintracks.api.PlayerScore;
import traintracks.api.Route;
import traintracks.api.RouteScoring;
import traintracks.api.Ticket;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * @author dbaumann
 * @since 1/26/18
 */
public class TTPlayerScore implements PlayerScore {

    private final RouteScoring routeScoring;
    private int routeScore;
    private int ticketScore;
    private boolean hasLongestTrain;
    private int stationScore;

    public TTPlayerScore(RouteScoring routeScoring) {
        this.routeScoring = routeScoring;
        this.routeScore = 0;
        this.ticketScore = 0;
        this.hasLongestTrain = false;
        this.stationScore = 0;
    }

    @Override
    public int getRouteScore() {
        return this.routeScore;
    }

    @Override
    public int getTicketScore() {
        return this.ticketScore;
    }

    @Override
    public boolean hasLongestTrain() {
        return this.hasLongestTrain;
    }

    @Override
    public int getLongestTrainScore() {
        return this.hasLongestTrain ? routeScoring.getLongestTrainScore() : 0;
    }

    @Override
    public int getStationScore() {
        return this.stationScore;
    }

    @Override
    public int getTotalScore() {
        return getRouteScore() + getTicketScore() + getLongestTrainScore() + getStationScore();
    }

    @Override
    public int getPublicScore() {
        return getRouteScore() + getLongestTrainScore() + getStationScore();
    }

    // TODO add PlayerStation as a param to compute stationScore
    @Override
    public void updateScore(Player player, List<CompletedRoute> allCompletedRoutes, List<Ticket> tickets) {
        List<Route> completedRoutesByPlayer =
                allCompletedRoutes.stream().
                filter(cRoute -> cRoute.getPlayer() == player).
                map(cRoute -> cRoute.getRoute()).
                collect(Collectors.toList());
        this.routeScore = completedRoutesByPlayer.stream().mapToInt(route -> this.routeScoring.getScore(route.getLength())).sum();
        this.ticketScore = tickets.stream().mapToInt(ticket -> ticket.isCompleted(completedRoutesByPlayer) ? ticket.getScore() : -1 * ticket.getScore()).sum();
        this.hasLongestTrain = getPlayersWithLongestTrain(allCompletedRoutes).contains(player);
        this.stationScore = 0;
    }

    private Set<Player> getPlayersWithLongestTrain(List<CompletedRoute> allCompletedRoutes) {
        Map<Player, List<Route>> playerCompletedRoutesMap = new HashMap<>();
        for (CompletedRoute completedRoute : allCompletedRoutes) {
            Player player = completedRoute.getPlayer();
            List<Route> completedRoutesByPlayer = playerCompletedRoutesMap.get(player);
            if (completedRoutesByPlayer == null) {
                completedRoutesByPlayer = new ArrayList<>();
                playerCompletedRoutesMap.put(player, completedRoutesByPlayer);
            }
            completedRoutesByPlayer.add(completedRoute.getRoute());
        }

        Set<Player> playersWithLongestTrain = new HashSet<>();
        AtomicInteger maxLength = new AtomicInteger(0);
        for (Player player : playerCompletedRoutesMap.keySet()) {
            List<Route> playerCompletedRoutes = playerCompletedRoutesMap.get(player);
            Stack<Route> visitedRoutes = new Stack<>();
            AtomicInteger playerMaxLength = new AtomicInteger(0);
            visitNextRoute(playerCompletedRoutes, visitedRoutes, 0, playerMaxLength);
            if (playerMaxLength == maxLength) {
                playersWithLongestTrain.add(player);
            } else if (playerMaxLength.get() > maxLength.get()) {
                playersWithLongestTrain = new HashSet<>();
                playersWithLongestTrain.add(player);
                maxLength = playerMaxLength;
            }
        }

        return playersWithLongestTrain;
    }

    private void visitNextRoute(List<Route> playerCompletedRoutes, Stack<Route> visitedRoutes, int length, AtomicInteger maxLength) {
        for (Route nextRoute : playerCompletedRoutes) {
            if (!visitedRoutes.contains(nextRoute)) {
                boolean touchesPrevRoute;
                if (visitedRoutes.size() == 0) {
                    touchesPrevRoute = true;
                } else {
                    Route prevRoute = visitedRoutes.peek();
                    touchesPrevRoute = prevRoute.connectsTo(nextRoute);
                }
                if (touchesPrevRoute) {
                    length += nextRoute.getLength();
                    if (length > maxLength.get()) {
                        maxLength.set(length);
                    }
                    visitedRoutes.push(nextRoute);
                    visitNextRoute(playerCompletedRoutes, visitedRoutes, length, maxLength);
                    if (visitedRoutes.size() == playerCompletedRoutes.size()) {
                        break;
                    }
                    visitedRoutes.pop();
                    length -= nextRoute.getLength();
                }
            }
        }
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("score(");
        sb.append("route=").append(getRouteScore());
        sb.append(" ticket=").append(getTicketScore());
        sb.append(" longest=").append(getLongestTrainScore());
        sb.append(" public=").append(getPublicScore());
        sb.append(" total=").append(getTotalScore());
        sb.append(")");
        return sb.toString();
    }
}
