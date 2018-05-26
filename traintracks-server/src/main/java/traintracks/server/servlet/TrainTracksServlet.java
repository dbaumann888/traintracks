package traintracks.server.servlet;

import traintracks.api.Game;
import traintracks.server.GameFactory;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class TrainTracksServlet extends HttpServlet {

    public TrainTracksServlet() { }

    @Override
    protected void doGet(final HttpServletRequest req, final HttpServletResponse resp) throws IOException {

        resp.setContentType("text/html");
        resp.setStatus(HttpServletResponse.SC_OK);
        String jsonPayload = null;

        String query = req.getPathInfo();

        // Game game = (Game)this.getServletContext().getAttribute("game");
        Game game = GameFactory.getGame();

        if ((query != null) && (query.equals("/Boards"))) {
            jsonPayload = game.getBoard().toString();
        }

        PrintWriter out = resp.getWriter();
        out.println(jsonPayload);
        out.flush();
    }

}
