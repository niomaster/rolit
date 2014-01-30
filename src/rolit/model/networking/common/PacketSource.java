package rolit.model.networking.common;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

public class PacketSource extends Thread {
    private final BufferedReader input;
    private List<PacketInputStream> inputStreams = new LinkedList<PacketInputStream>();

    public PacketSource(InputStream input) throws UnsupportedEncodingException {
        this.input = new BufferedReader(new InputStreamReader(input, "UTF-8"));
    }

    public PacketInputStream createInputStream() {
        PacketInputStream stream = new PacketInputStream();
        inputStreams.add(stream);
        return stream;
    }

    @Override
    public void run() {
        try {
            while(true) {
                Packet packet = Packet.readServerPacketFrom(input);
                notifyOfPacket(packet);
            }
        } catch (ProtocolException e) {
            notifyOfProtocolException(e);
        } catch (IOException e) {
            notifyOfIOException(e);
        }
    }

    private void notifyOfPacket(Packet packet) {
        for(PacketInputStream stream : inputStreams) {
            stream.notifyOfPacket(packet);
        }
    }

    private void notifyOfProtocolException(ProtocolException e) {
        for(PacketInputStream stream : inputStreams) {
            stream.notifyOfProtocolException(e);
        }
    }

    private void notifyOfIOException(IOException e) {
        for(PacketInputStream stream : inputStreams) {
            stream.notifyOfIOException(e);
        }
    }
}
