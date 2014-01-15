package rolit.util;

public class Strings {
    public static String join(String delimiter, String[] strings) {
        String result = "";
        boolean first = true;

        for(String string : strings) {
            if(!first) {
                result += delimiter;
            }

            first = false;

            result += string;
        }

        return result;
    }
}
