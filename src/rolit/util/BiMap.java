package rolit.util;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class BiMap<K, V> {
    private HashMap<K, V> forward = new LinkedHashMap<K, V>();
    private HashMap<V, K> backward = new LinkedHashMap<V, K>();

    public void put(K key, V value) {
        forward.put(key, value);
        backward.put(value, key);
    }

    public V get(Object key) {
        return forward.get(key);
    }

    public K getBackward(Object value) {
        return backward.get(value);
    }
}
