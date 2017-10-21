package traintracks.engine.game;

import traintracks.api.DrawCarTurn;
import traintracks.api.Player;
import traintracks.api.TurnType;

public class TTDrawCarTurn extends TTTurn implements DrawCarTurn {
    private int index;

    public TTDrawCarTurn(Player player, int index) {
        super(player, TurnType.DRAW_TRAIN_CAR);
        this.index = index;
    }

    public int getIndex() { return this.index; }
}
