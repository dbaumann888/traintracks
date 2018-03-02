package traintracks.server.engine.game;

import traintracks.api.RouteScoring;

import java.util.Map;

public class TTRouteScoring implements RouteScoring {
    private final int longestTrainScore;
    private final Map<Integer, Integer> lengthToScoreMap;

    public TTRouteScoring(int longestTrainScore, Map<Integer, Integer> lengthToScoreMap) {
        this.longestTrainScore = longestTrainScore;
        this.lengthToScoreMap = lengthToScoreMap;
    }

    @Override
    public int getLongestTrainScore() {
        return this.longestTrainScore;
    }

    public int getScore(int routeLength) {
        return this.lengthToScoreMap.get(routeLength);
    }

    public Map<Integer, Integer> getScores() {
        return this.lengthToScoreMap;
    }
    public String getString() { return getScores().toString(); }
}
