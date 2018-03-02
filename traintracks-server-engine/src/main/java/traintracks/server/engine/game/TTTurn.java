package traintracks.server.engine.game;

import traintracks.api.Player;
import traintracks.api.Turn;
import traintracks.api.TurnType;

import java.util.UUID;

public class TTTurn implements Turn {
    UUID id;
    Player player;
    TurnType type;

    public TTTurn(Player player, TurnType type) {
        this.id = UUID.randomUUID();
        this.player = player;
        this.type = type;
    }

    public UUID getId() { return this.id; }
    public Player getPlayer() { return this.player; }
    public TurnType getType() { return this.type; }
    public String toString() { return "Turn " + this.type + " by " + this.player; }
}
