package rolit.view.client;

import rolit.model.networking.common.Packet;
import rolit.model.networking.common.PacketInputStream;
import rolit.model.networking.common.ProtocolException;
import rolit.model.networking.server.*;
import rolit.util.Arrays;

import java.io.IOException;

public class EventThread extends Thread {
    private final PacketInputStream stream;
    private final MainView.MainController controller;
    private final Class[] EVENT_PACKET_TYPES = {
            GamePacket.class,
            MessagePacket.class,
            ChallengePacket.class,
            OnlinePacket.class,
            CanBeChallengedPacket.class
    };

    public EventThread(MainView.MainController controller, PacketInputStream stream) {
        this.stream = stream;
        this.controller = controller;
    }

    @Override
    public void run() {
        try {
            while(true) {
                Packet packet = stream.readPacket();

                if(Arrays.contains(EVENT_PACKET_TYPES, packet.getClass())) {
                    if(packet instanceof GamePacket) {
                        controller.gameUpdate((GamePacket) packet);
                    } else if(packet instanceof MessagePacket) {
                        controller.message((MessagePacket) packet);
                    } else if(packet instanceof ChallengePacket) {
                        controller.challenge((ChallengePacket) packet);
                    } else if(packet instanceof OnlinePacket) {
                        controller.online((OnlinePacket) packet);
                    } else if(packet instanceof CanBeChallengedPacket) {
                        controller.canBeChallenged((CanBeChallengedPacket) packet);
                    }
                }
            }
        } catch (ProtocolException e) {

        } catch (IOException e) {

        }
    }
}
