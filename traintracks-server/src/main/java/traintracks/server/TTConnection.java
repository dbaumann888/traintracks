package traintracks.server;

import com.google.common.base.Strings;
import com.google.common.collect.ImmutableList;
import com.google.gson.Gson;
import traintracks.api.Board;
import traintracks.api.Game;
import traintracks.api.Player;
import traintracks.api.PlayerState;
import traintracks.server.engine.game.TTBoard;

import java.util.List;
import java.util.stream.Collectors;

import static traintracks.server.engine.game.TTSetup.EUROPE;
import static traintracks.server.engine.game.TTSetup.NORTH_AMERICA;

public class TTConnection implements ServletConnection {

    private Game game;
    private List<Board> boards;

    public TTConnection() {
        this.game = GameFactory.getGame();
        this.boards = ImmutableList.of(TTBoard.getTTBoard(NORTH_AMERICA), TTBoard.getTTBoard(EUROPE));
    }

    @Override
    public String query(String queryString) {
        String resource = queryString;
        String id = null;
        if (Strings.isNullOrEmpty(queryString)) {
            throw new IllegalArgumentException("empty URI");
        }
        if (queryString.contains("/")) {
            if (!queryString.startsWith("/")) {
                throw new IllegalArgumentException("URI doesn't begin with /");
            }
            String[] splits = queryString.split("/");
            resource = splits[1];
            if (splits.length > 2) {
                id = splits[2];
            }
        } else {
            throw new IllegalArgumentException("invalid URI -- no / found");
        }

        String jsonPayload = null;
        Gson gson = new Gson();
        List<Player> players;

        switch (resource) {
            case "games":
                if (id != null) {
                    jsonPayload = gson.toJson(game);
                } else {
                    List<Game> games = ImmutableList.of(game);
                    jsonPayload = gson.toJson(games);
                }
                break;
            case "boards":
                if (id != null) {
                    jsonPayload = gson.toJson(game.getBoard());
                } else {
                    jsonPayload = gson.toJson(boards);
                }
                break;
            case "board-states":
                if (id != null) {
                    jsonPayload = gson.toJson(game.getBoardState());
                } else {
                    throw new IllegalArgumentException("can't request list of BoardStates");
                }
                break;
            case "players":
                players = game.getPlayers();
                final String fid = id;
                if (id != null) {
                    Player player = players.stream().filter(p -> p.getId().toString().equals(fid)).findFirst().get();
                    jsonPayload = gson.toJson(player);
                } else {
                    jsonPayload = gson.toJson(players);
                }
                break;
            case "player-states":
                players = game.getPlayers();
                List<PlayerState> playerStates = players.stream().map(p -> p.getState()).collect(Collectors.toList());
                final String psId = id;
                if (id != null) {
                    PlayerState playerState = playerStates.stream().filter(ps -> ps.getId().toString().equals(psId)).findFirst().get();
                    jsonPayload = gson.toJson(playerState);
                } else {
                    jsonPayload = gson.toJson(playerStates);
                }
                break;
        }
        return jsonPayload;
    }
}
