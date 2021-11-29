package me.hiremedev.tetrisplugin.controls.keys;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public enum Key {
    QUIT("quit", 8),
    RESET("reset", 7),
    PLAY("play", 0),
    ROTATE("rot", 1),
    MOVE("move", 0),
    FAST_DROP("fast",2);

    private String key;
    private int slot;
    private static final Map<String, Key> lookup = new HashMap<>();

    static {
        Arrays.stream(Key.values()).forEach(
                (key) -> lookup.put(key.getKey(), key)
        );
    }

    Key(String key, int slot) {
        this.key = key;
        this.slot = slot;
    }

    public String getKey() {
        return key;
    }
    public static Key getEnum(String key){
        return lookup.get(key);
    }

    public int getSlot() {
        return slot;
    }
}
