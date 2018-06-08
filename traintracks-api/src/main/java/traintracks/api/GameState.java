package traintracks.api;

import java.util.List;
import java.util.UUID;

public interface GameState {
    UUID getId();
    Player getActivePlayer();
    void setActivePlayer(Player nextPlayer);
    boolean over();
    List<Turn> getTurnHistory();
    void applyTurn(Game game, Turn turn);
}
