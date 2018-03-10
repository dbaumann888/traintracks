package traintracks.server;

import com.google.common.collect.ImmutableList;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.HttpConfiguration;
import org.eclipse.jetty.server.HttpConnectionFactory;
import org.eclipse.jetty.server.SecureRequestCustomizer;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletHandler;
import traintracks.api.Board;
import traintracks.api.Game;
import traintracks.api.Player;
import traintracks.api.PlayerType;
import traintracks.server.engine.game.TTBoard;
import traintracks.server.engine.game.TTGame;
import traintracks.server.engine.game.TTPlayer;
import traintracks.server.engine.game.TTSetup;
import traintracks.server.servlet.TrainTracksServlet;

import javax.servlet.ServletContext;
import java.awt.Color;
import java.net.InetSocketAddress;
import java.util.List;

public class TrainTracksServer {

    public final static String HOSTNAME = "localhost";
    public final static int PORT = 8200;
    public final static int SEC_PORT = 9200;
    public final static String KEYSTORE = "setup/keystore";
    public final static String KEYSTORE_PWD = "test1234";

    public static void main(String[] args) throws Exception {
        System.setProperty("org.eclipse.jetty.LEVEL", "DEBUG");

//        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(
//                ODataSampleProducerConfig.class
//        );

        Game game = createGame();

        Server server = getServer(game);
        server.start();
        server.join();
    }

    private static Game createGame() {
        Board board = TTBoard.getTTBoard(TTSetup.NORTH_AMERICA);
        Player player1 = new TTPlayer("muish", Color.BLUE, PlayerType.HUMAN, board);
        Player player2 = new TTPlayer("dantrayal", Color.BLACK, PlayerType.HUMAN, board);
        Player player3 = new TTPlayer("magz", Color.GREEN, PlayerType.HUMAN, board);
        List<Player> players = ImmutableList.of(player1, player2, player3);
        Game game = new TTGame(players, board);
        return game;
    }

    private static Server getServer(Game game) {
        String hostname = HOSTNAME;
        String portStr = System.getenv("PORT");
        int port = PORT;
        if (portStr != null) {
            hostname = "0.0.0.0";
            port = Integer.valueOf(portStr);
        }

        InetSocketAddress serverAddress = new InetSocketAddress(hostname, port);
        Server server = new Server(serverAddress);

        ServletHandler servletHandler = new ServletHandler();
        servletHandler.addServletWithMapping(TrainTracksServlet.class, "/tt"); //new ServletHolder(servletProvider.getServlet()),
                // servletProvider.getServletPathMatch()));
        server.setHandler(servletHandler);

        ServletContext servletContext = servletHandler.getServletContext();
        servletContext.setAttribute("game", game);

        HttpConfiguration httpConfig = new HttpConfiguration();
        httpConfig.setSecureScheme("https");
        httpConfig.setSecurePort(SEC_PORT);

        HttpConfiguration httpsConfig = new HttpConfiguration(httpConfig);
        httpsConfig.addCustomizer(new SecureRequestCustomizer());

        ServerConnector httpConnector = new ServerConnector(server, new HttpConnectionFactory(httpConfig));
        httpConnector.setPort(port);

//        SslContextFactory sslContextFactory = new SslContextFactory(KEYSTORE);
//        sslContextFactory.setKeyStorePassword(KEYSTORE_PWD);
        ServerConnector httpsConnector = new ServerConnector(server,
//                new SslConnectionFactory(sslContextFactory, HttpVersion.HTTP_1_1.asString()),
                new HttpConnectionFactory(httpsConfig));
        httpsConnector.setPort(SEC_PORT);

        server.setConnectors(new Connector[]{httpConnector, httpsConnector});
        return server;
    }

}
