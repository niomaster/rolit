package rolit.model.networking.common;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class PacketInputStream {
    private ReentrantLock lock = new ReentrantLock();
    private Condition queueNotEmpty = lock.newCondition();
    private Queue<Object> queue = new LinkedList<Object>();

    public Packet readPacket() throws ProtocolException, IOException {
        lock.lock();

        Object result = null;

        try {
            while(queue.size() == 0) {
                queueNotEmpty.await();
            }

            result = queue.poll();
        } catch (InterruptedException e) {

        } finally {
            lock.unlock();
        }

        if(result instanceof Packet) {
            return (Packet) result;
        } else if(result instanceof ProtocolException) {
            ProtocolException e = (ProtocolException) result;
            throw new ProtocolException(e.getMessage(), e.getCode());
        } else {
            throw (IOException) result;
        }
    }

    private void add(Object object) {
        lock.lock();

        try {
            queue.add(object);
            queueNotEmpty.signal();
        } finally {
            lock.unlock();
        }
    }

    public void notifyOfPacket(Packet packet) {
        add(packet);
    }

    public void notifyOfProtocolException(ProtocolException e) {
        add(e);
    }

    public void notifyOfIOException(IOException e) {
        add(e);
    }
}
