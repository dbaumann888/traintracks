package traintracks.server.servlet;

import traintracks.api.Game;
import traintracks.server.GameFactory;
import traintracks.server.ServletConnection;
import traintracks.server.TTConnection;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class TrainTracksServlet extends HttpServlet {

    public TrainTracksServlet() { }

    @Override
    protected void doGet(final HttpServletRequest req, final HttpServletResponse resp) throws IOException {

        ServletConnection connection = new TTConnection();

        resp.setContentType("text/html");
        resp.setStatus(HttpServletResponse.SC_OK);

        String queryString = req.getPathInfo();

        String jsonPayload = connection.query(queryString);

        PrintWriter out = resp.getWriter();
        out.println(jsonPayload);
        out.flush();
    }

}
