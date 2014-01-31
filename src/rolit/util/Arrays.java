package rolit.util;

import java.lang.reflect.Array;

public class Arrays {
    public static <T> boolean contains(T[] array, T element) {
        for(T t : array) {
            if((element == null && t == null) || (element != null && element.equals(t))) {
                return true;
            }
        }

        return false;
    }

    public static <T> T[] cast(Class<T> c, Object[] array) {
        T[] result = (T[]) Array.newInstance(c, array.length);

        for(int i = 0; i < array.length; i++) {
            result[i] = (T) array[i];
        }

        return result;
    }
}
