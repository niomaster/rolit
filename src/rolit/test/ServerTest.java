package rolit.test;

import org.junit.Before;
import org.junit.Test;
import rolit.model.event.ServerListener;
import rolit.model.networking.server.ClientHandler;
import rolit.model.networking.server.Server;
import rolit.model.networking.server.ServerGame;
import rolit.model.networking.server.User;

import java.io.IOException;
import java.net.Socket;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by Martijn on 31-1-14.
 */
public class ServerTest {

    Server server;
    ServerListener listener;

    @Before
    public void setUp() throws IOException {
        server = new Server("localhost", 0);
        listener = new ServerListener() {
            @Override
            public void serverError(String reason) {

            }

            @Override
            public void newClient(ClientHandler handler) {

            }

            @Override
            public void clientError(String reason) {

            }
        };
    }

    @Test
    public void testAddListener() throws Exception {
        server.addListener(listener);
        assertTrue(server.isInListeners(listener));
    }

    @Test
    public void testRemoveListener() throws Exception {
        server.removeListener(listener);
        assertFalse(server.isInListeners(listener));
    }
}
