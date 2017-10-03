package traintracks.api;

import java.util.List;

public interface Game {
    void applyTurn(Turn turn);
    List<Player> getPlayers();
    Board getBoard();
    List<Turn> getTurnHistory();
}
