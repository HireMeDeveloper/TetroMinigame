package me.hiremedev.tetrisplugin.controls.keys;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.stream.Collectors;

public class KeyManager {
    private JavaPlugin plugin;

    public KeyManager(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public ItemStack addStringKey(ItemStack item, String key, String value) {
        var meta = item.getItemMeta();
        var data = meta.getPersistentDataContainer();
        var namespacedKey = new NamespacedKey(plugin, key);
        data.set(namespacedKey, PersistentDataType.STRING, value);
        item.setItemMeta(meta);
        return item;
    }

    public boolean hasStringKey(ItemStack item, String key) {
        var meta = item.getItemMeta();
        var data = meta.getPersistentDataContainer();
        var namespacedKey = new NamespacedKey(plugin, key);
        return data.has(namespacedKey, PersistentDataType.STRING);
    }

    public String getStringValue(ItemStack item, String key) {
        var meta = item.getItemMeta();
        var data = meta.getPersistentDataContainer();
        var namespacedKey = new NamespacedKey(plugin, key);
        return data.get(namespacedKey, PersistentDataType.STRING);
    }

    public List<String> getStringKeys(ItemStack item) {
        var meta = item.getItemMeta();
        var data = meta.getPersistentDataContainer();
        var keys = data.getKeys();
        return keys.stream()
                .map(NamespacedKey::getKey)
                .collect(Collectors.toList());
    }
}
