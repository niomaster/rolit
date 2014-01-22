package rolit.model.networking.extensions;
/**
 * @author Pieter Bos
 */
import rolit.model.networking.client.ClientProtocol;

public abstract class WebSocketClientProtocol extends ClientProtocol {
    public static final String GET = "GET";

    /**
     * Bij wijze van spreke een methode van de client.
     * @param path Moet altijd / zijn
     * @param version Moet altijd HTTP/1.1 zijn;
     */
    public abstract void get(String path, String version);
}
