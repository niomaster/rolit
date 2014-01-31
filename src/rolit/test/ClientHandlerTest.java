package rolit.test;

import org.junit.Before;
import org.junit.Test;
import rolit.model.networking.common.CommonProtocol;
import rolit.model.networking.server.ClientHandler;
import rolit.model.networking.server.Server;
import rolit.model.networking.server.ServerGame;
import rolit.model.networking.server.User;

import java.net.Socket;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * @author Martijn de Bijl
 */
public class ClientHandlerTest {

    private ClientHandler handler;
    private Server server;
    private User user;

    @Before
    public void setUp() throws Exception {
        server = new Server("localhost", 0);
        handler = new ClientHandler(server, new Socket());
        user = new User("Martijn", handler);
        handler.setClientSupports(CommonProtocol.SUPPORTS_BAREBONE);
        handler.setClientName("Martijn");
    }

    /**
     * Test of de handler de goede supports terug geeft.
     * @throws Exception
     */
    @Test
    public void testGetClientSupports() throws Exception {
        assertEquals(CommonProtocol.SUPPORTS_BAREBONE, handler.getClientSupports());
    }

    /**
     * Test of de supports van de client verandert kunnen worden.
     * @throws Exception
     */
    @Test
    public void testSetClientSupports() throws Exception {
        handler.setClientSupports(CommonProtocol.SUPPORTS_CHAT_CHALLENGE);
        assertEquals(CommonProtocol.SUPPORTS_CHAT_CHALLENGE, handler.getClientSupports());
    }

    /**
     * Test of de server de goede naam terug geeft van zijn client.
     * @throws Exception
     */
    @Test
    public void testGetClientName() throws Exception {
        assertEquals("Martijn", handler.getClientName());
    }

    /**
     * Test of mde handler een andere naam kan geven voor zijn client.
     * @throws Exception
     */
    @Test
    public void testSetClientName() throws Exception {
        handler.setClientName("Pieter");
        assertEquals("Pieter", handler.getClientName());
    }

    /**
     * Test of de handler kan zien of de client uitdagen ondersteunt.
     * @throws Exception
     */
    @Test
    public void testCanBeChallenged() throws Exception {
        assertFalse(handler.canBeChallenged());
    }

    /**
     * Test of de handler kan zien of de client chatten ondersteunt.
     * @throws Exception
     */
    @Test
    public void testCanChat() throws Exception {
        assertFalse(handler.canChat());
    }

    /**
     * Test of de handler de goede user terug geeft.
     * @throws Exception
     */
    @Test
    public void testGetUser() throws Exception {
        assertEquals(user.getUsername(), handler.getUser().getUsername());
    }
}
