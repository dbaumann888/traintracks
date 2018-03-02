package traintracks.api;

import java.util.List;

/**
 * @author dbaumann
 * @since 1/26/18
 */
public interface PlayerScore {
    Player getPlayer();
    int getRouteScore();
    int getTicketScore();
    boolean hasLongestTrain();
    int getLongestTrainScore();
    int getStationScore();
    int getTotalScore();
    int getPublicScore();
    void updateScore(List<CompletedRoute> completedRoutes, List<Ticket> tickets);
}
