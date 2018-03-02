package traintracks.api;

import java.util.Map;

public interface RouteScoring {
    int getLongestTrainScore();
    int getScore(int routeLength);
    Map<Integer, Integer> getScores();
}
