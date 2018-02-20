package traintracks.service;

import org.eclipse.jetty.http.HttpVersion;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.HttpConfiguration;
import org.eclipse.jetty.server.HttpConnectionFactory;
import org.eclipse.jetty.server.SecureRequestCustomizer;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.SslConnectionFactory;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.util.ssl.SslContextFactory;
import traintracks.service.servlet.TrainTracksServlet;

import java.net.InetSocketAddress;

public class TrainTracksService {

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
        Server server = getServer();

        server.start();
        server.join();
    }

    private static Server getServer() {
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
