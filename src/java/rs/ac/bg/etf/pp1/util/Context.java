package rs.ac.bg.etf.pp1.util;

import java.util.HashMap;
import java.util.Map;

public final class Context {
    public static final class Key<T> {
    }

    private final Map<Key<?>, Object> table = new HashMap<>();

    public <T> void put(Key<T> key, T value) {
        Object object = table.put(key, value);
        if (object != null) throw new IllegalStateException("Key " + key + " already exists.");
    }

    @SuppressWarnings("unchecked")
    public <T> T get(Key<T> key) {
        return (T) table.get(key);
    }
}
