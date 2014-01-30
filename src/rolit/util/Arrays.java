package rolit.util;

public class Arrays {
    public static <T> boolean contains(T[] array, T element) {
        for(T t : array) {
            if((element == null && t == null) || (element != null && element.equals(t))) {
                return true;
            }
        }

        return false;
    }
}
