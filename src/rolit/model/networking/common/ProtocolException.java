package rolit.model.networking.common;

import rolit.model.networking.server.ServerProtocol;

/**
 * De verschillende exceptions die kunnen onstaan in het protocol.
 * @author Pieter Bos
 */
public class ProtocolException extends Exception {
    private final int code;

    public ProtocolException(String message, int code) {
        super(message);
        this.code = code;
    }

    public ProtocolException(String message) {
        this(message, ServerProtocol.ERROR_GENERIC);
    }

    public int getCode() {
        return code;
    }
}
