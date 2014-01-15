package rolit.model.networking.common;

public class ProtocolException extends Exception {
    private final int code;

    public ProtocolException(String message, int code) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
