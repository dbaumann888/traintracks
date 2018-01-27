package traintracks.api;

import java.util.List;

public interface Game {
    void applyTurn(Turn turn);
    List<Player> getPlayers();
    Board getBoard();
    BoardState getBoardState();
    List<Turn> getTurnHistory();
    boolean over();
    String toDisplayString();
}
