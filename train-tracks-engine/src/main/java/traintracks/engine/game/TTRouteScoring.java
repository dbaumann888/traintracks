package traintracks.engine.game;

import traintracks.api.RouteScoring;

import java.util.Map;

public class TTRouteScoring implements RouteScoring {
    private Map<Integer, Integer> lengthToScoreMap;

    public TTRouteScoring(Map<Integer, Integer> lengthToScoreMap) {
        this.lengthToScoreMap = lengthToScoreMap;
    }

    public int getScore(int routeLength) {
        return this.lengthToScoreMap.get(routeLength);
    }

    public Map<Integer, Integer> getScores() {
        return this.lengthToScoreMap;
    }
}
