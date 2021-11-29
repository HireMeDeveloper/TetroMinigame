package me.hiremedev.tetrisplugin.controls.keys;

import org.bukkit.inventory.ItemStack;

public class Slottable {
    private ItemStack item;
    private int slot;

    public Slottable(ItemStack item, int slot) {
        this.item = item;
        this.slot = slot;
    }

    public ItemStack getItem() {
        return item;
    }

    public int getSlot() {
        return slot;
    }
}
