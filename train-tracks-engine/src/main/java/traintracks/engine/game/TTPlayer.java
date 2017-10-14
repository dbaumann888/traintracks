package traintracks.engine.game;

import traintracks.api.Player;
import traintracks.api.PlayerState;
import traintracks.api.PlayerType;

import java.awt.*;
import java.util.UUID;

public class TTPlayer implements Player {
    private UUID id;
    private String name;
    private Color color;
    private PlayerType type;
    private PlayerState state;

    public TTPlayer(String name, Color color, PlayerType type) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.color = color;
        this.type = type;
        this.state = new TTPlayerState();
    }

    public UUID getId() { return this.id; }
    public String getName() { return this.name; }
    public Color getColor() { return this.color; }
    public PlayerType getType() { return this.type; }
    public PlayerState getState() { return this.state; }
    public String toString() { return this.getName(); }
}
