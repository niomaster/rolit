package rolit.model.networking.server;

import rolit.model.networking.common.ProtocolException;

/**
 * De staat van de ClientHandler ten aanzien van het gechallenged kunnen worden.
 * @author Pieter Bos
 */
public class ChallengedClientHandlerState extends ClientHandlerState {
    private final String challenger;
    private String[] others;

    public ChallengedClientHandlerState(ClientHandler handler, String challenger, String[] others) {
        super(handler);
        this.challenger = challenger;
        this.others = others;
    }

    /**
     * Verandert de status van de ClientHandler als er een ChallengeResponse pakketje ontvangen wordt.
     * @param packet het ontvangen pakketje.
     * @return Een nieuwe ClientHandler staat met een nieuwe status.
     * @throws ProtocolException gooit deze exceptie als er iets fout gaat.
     */
    @Override
    public ClientHandlerState challengeResponse(rolit.model.networking.client.ChallengeResponsePacket packet) throws ProtocolException {
        getHandler().notifyChallengeResponse(packet.getResponse(), getChallenger(), getOthers());

        if(packet.getResponse()) {
            return new WaitForGameClientHandlerState(getHandler(), getChallenger());
        } else {
            getHandler().notifyCannotBeChallenged();
            return new GameLobbyClientHandlerState(getHandler());
        }
    }

    /**
     * Verandert de status van de ClientHandler als de speler een challenge heeft geaccepteerd of gedeclined.
     * @param response De reactie van de speler op de challenge.
     * @param challenged de naam van de speler die gechallenged is.
     * @return een nieuwe ClientHandler staat met een nieuwe status.
     */
    @Override
    public ClientHandlerState notifyChallengeResponseBy(boolean response, String challenged) {
        getHandler().write(new ChallengeResponsePacket(challenged, response));
        return this;
    }

    public String getChallenger() {
        return challenger;
    }

    public String[] getOthers() {
        return others;
    }

    @Override
    public ClientHandlerState exit() {
        try {
            getHandler().notifyChallengeResponse(false, getChallenger(), getOthers());
        } catch (ProtocolException e) {  }

        return null;
    }
}
