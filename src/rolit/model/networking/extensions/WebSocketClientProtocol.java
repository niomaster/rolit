package rolit.model.networking.extensions;

public abstract class WebSocketClientProtocol {
    public static final String GET = "GET";

    /**
     * Bij wijze van spreke een methode van de client.
     * @param path Moet altijd / zijn
     * @param version Moet altijd HTTP/1.1 zijn;
     */
    public abstract void get(String path, String version);
}
