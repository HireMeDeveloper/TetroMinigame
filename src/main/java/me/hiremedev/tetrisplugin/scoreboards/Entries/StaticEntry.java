package me.hiremedev.tetrisplugin.scoreboards.Entries;

import net.kyori.adventure.text.Component;

public class StaticEntry implements Entry{
    private Component component;

    public StaticEntry(Component component) {
        this.component = component;
    }

    public Component getComponent() {
        return component;
    }
}
