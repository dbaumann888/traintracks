package traintracks.server;

public interface ServletConnection {
    /**
     * handle query
     * @param queryString URI path after the endpoint. eg. /Boards
     * @return json representation
     */
    String query(String queryString);
}
