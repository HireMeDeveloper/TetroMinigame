package me.hiremedev.tetrisplugin.display.util;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;

public enum JoinIcon {
    L(Component.text("L").color(NamedTextColor.GOLD).decorate(TextDecoration.BOLD)),
    J(Component.text("J").color(NamedTextColor.BLUE).decorate(TextDecoration.BOLD)),
    I(Component.text("I").color(NamedTextColor.AQUA).decorate(TextDecoration.BOLD)),
    O(Component.text("O").color(NamedTextColor.YELLOW).decorate(TextDecoration.BOLD)),
    S(Component.text("S").color(NamedTextColor.RED).decorate(TextDecoration.BOLD)),
    Z(Component.text("Z").color(NamedTextColor.GREEN).decorate(TextDecoration.BOLD))
    ;

    private Component icon;

    JoinIcon(Component icon) {
        this.icon = icon;
    }

    public Component getIcon() {
        return icon;
    }
}
