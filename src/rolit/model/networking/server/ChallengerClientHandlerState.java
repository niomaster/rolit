package rolit.model.networking.server;

import rolit.model.networking.client.StartGamePacket;
import rolit.model.networking.common.ProtocolException;

import java.util.LinkedList;

/**
 * De staat van de ClientHandler status van de challenger.
 * @author Pieter Bos
 */
public class ChallengerClientHandlerState extends ClientHandlerState {
    LinkedList<String> currentlyAccepted = new LinkedList<String>();

    public ChallengerClientHandlerState(ClientHandler handler, String[] challenged) {
        super(handler);
    }

    /**
     * Als de challenger de game start verandert de status van de clients.
     * @param packet een StartGame pakket.
     * @return een nieuwe ClientHandler met een nieuwe status.
     */
    @Override
    public ClientHandlerState startGame(StartGamePacket packet) throws ProtocolException {
        getHandler().createChallengeGame(currentlyAccepted);

        return new GameClientHandlerState(getHandler(), getHandler().getClientName());
    }

    /**
     * Stuurt een notification naar de challenger als een speler die gechallenged is reageert.
     * @param response de reactie van de speler op de challenge.
     * @param challenged de andere spelers die gechallenged zijn.
     * @return een nieuwe ClientHandler met een nieuwe status.
     */
    @Override
    public ClientHandlerState notifyChallengeResponseBy(boolean response, String challenged) {
        getHandler().write(new ChallengeResponsePacket(challenged, response));

        if(response) {
            currentlyAccepted.add(challenged);
        } else {
            currentlyAccepted.remove(challenged);
        }

        return this;
    }
}
