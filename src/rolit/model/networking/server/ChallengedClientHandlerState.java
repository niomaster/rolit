package rolit.model.networking.server;

import rolit.model.networking.common.ProtocolException;

public class ChallengedClientHandlerState extends ClientHandlerState {
    private final String challenger;
    private String[] others;

    public ChallengedClientHandlerState(ClientHandler handler, String challenger, String[] others) {
        super(handler);
        this.challenger = challenger;
        this.others = others;
    }

    @Override
    public ClientHandlerState challengeResponse(rolit.model.networking.client.ChallengeResponsePacket packet) throws ProtocolException {
        getHandler().notifyChallengeResponse(packet.getResponse(), getChallenger(), getOthers());

        if(packet.getResponse()) {
            return new WaitForGameClientHandlerState(getHandler(), getChallenger());
        } else {
            return new GameLobbyClientHandlerState(getHandler());
        }
    }

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
