package rolit.util;

import java.util.LinkedList;
import java.util.List;

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

    public static String[] remove(String[] original, String toBeRemoved) {
        List<String> result = new LinkedList<String>();

        for(String s : original) {
            if(!s.equals(toBeRemoved)) {
                result.add(s);
            }
        }

        String[] stringArray = new String[result.size()];
        result.toArray(stringArray);
        return stringArray;
    }

    public static String[] push(String s, String[] array) {
        String[] result = new String[array.length + 1];

        int i = 0;

        for(String arrayS : array) {
            result[i++] = arrayS;
        }

        result[result.length - 1] = s;

        return result;
    }
}
