package rolit.model.networking.common;

import rolit.model.networking.server.ServerProtocol;
import rolit.util.Strings;

import java.util.Arrays;

/**
 * De vrschillende argumenten die pakketjes kunnen hebben.
 * @author Pieter Bos
 */
public class PacketArgs {
    public enum ArgumentType {
        Integer,
        Boolean,
        String,
        MultiString
    }

    private Object[] data;

    public PacketArgs(Object[] data) {
        this.data = data;
    }

    private static Object parse(String data, ArgumentType arg) throws ProtocolException {
        switch(arg) {
            case Integer:
                return Integer.parseInt(data);
            case Boolean:
                if(!data.equals(CommonProtocol.T_BOOLEAN_TRUE) && !data.equals(CommonProtocol.T_BOOLEAN_FALSE)) {
                    throw new ProtocolException("Boolean must be true or false.", ServerProtocol.ERROR_GENERIC);
                }

                return data.equals(CommonProtocol.T_BOOLEAN_TRUE);
            case String:
                return data;
            case MultiString:
                throw new ProtocolException("Cannot parse a MultiString as a singular argument.", ServerProtocol.ERROR_GENERIC);
        }

        // Impossible
        return null;
    }

    public int getInt(int i) {
        return (Integer) data[i];
    }

    public boolean getBool(int i) {
        return (Boolean) data[i];
    }

    public String getString(int i) {
        return (String) data[i];
    }

    public String[] getMultiString(int i) {
        return (String[]) data[i];
    }

    public String getSpacedString(int i) {
        return Strings.join(CommonProtocol.COMMAND_DELIMITER, getMultiString(i));
    }

    public static PacketArgs fromParts(String[] parts, ArgumentType[] args) throws ProtocolException {
        boolean multiString = false;
        int multiStringLocation = -1;

        for(int i = 0; i < args.length; i++) {
            if(args[i] == ArgumentType.MultiString) {
                multiString = true;
                multiStringLocation = i;
                break;
            }
        }

        Object[] result = new Object[args.length];

        if(multiString) {
            for(int i = 0; i < multiStringLocation; i++) {
                result[i] = parse(parts[i], args[i]);
            }

            int left = args.length - multiStringLocation - 1;

            for(int i = parts.length - left, resultI = multiStringLocation + 1; i < parts.length; i++, resultI++) {
                result[resultI] = parse(parts[i], args[resultI]);
            }

            result[multiStringLocation] = Arrays.copyOfRange(parts, multiStringLocation, multiStringLocation + parts.length - args.length + 1);
        } else {
            if(parts.length > args.length) {
                throw new ProtocolException("Length of parts exceeds argument count.", ServerProtocol.ERROR_GENERIC);
            }

            for(int i = 0; i < parts.length; i++) {
                result[i] = parse(parts[i], args[i]);
            }
        }

        return new PacketArgs(result);
    }

    @Override
    public String toString() {
        String result = "";
        boolean first = true;

        for(Object unit : data) {
            if(!first) {
                result += CommonProtocol.COMMAND_DELIMITER;
            }

            first = false;

            if(unit instanceof Integer) {
                result += Integer.toString((Integer) unit);
            } else if(unit instanceof Boolean) {
                result += Boolean.toString((Boolean) unit);
            } else if(unit instanceof String) {
                result += (String) unit;
            } else {
                result += Strings.join(CommonProtocol.COMMAND_DELIMITER, (String[]) unit);
            }
        }

        return result;
    }

    public static String[] spacedToMulti(String s) {
        return s.split(CommonProtocol.COMMAND_DELIMITER);
    }
}
