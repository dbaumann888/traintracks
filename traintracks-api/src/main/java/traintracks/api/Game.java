package traintracks.api;

import java.util.List;
import java.util.UUID;

public interface Game {
    UUID getId();
    String getName();
    List<Player> getPlayers();
    Board getBoard();
    GameState getGameState();
    String toDisplayString();
}
