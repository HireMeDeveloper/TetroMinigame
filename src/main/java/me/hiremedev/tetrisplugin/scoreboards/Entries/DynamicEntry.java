package me.hiremedev.tetrisplugin.scoreboards.Entries;

import net.kyori.adventure.text.Component;

import java.util.function.Supplier;

public class DynamicEntry implements Entry{
    private Supplier<Component> provider;

    public DynamicEntry(Supplier<Component> provider) {
        this.provider = provider;
    }

    public Component getComponent() {
        return provider.get();
    }
}
