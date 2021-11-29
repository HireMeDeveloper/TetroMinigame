package me.hiremedev.tetrisplugin.Scrolling;

import me.hiremedev.tetrisplugin.scoreboards.Entries.Entry;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

import java.util.List;

public enum ScrollingTips {
    TIP_1(Component.text("Tip: High scores are saved onto each screen!").color(NamedTextColor.AQUA)),
    TIP_2(Component.text("Hope you like puzzles!").color(NamedTextColor.YELLOW)),
    TIP_3(Component.text("Server created by HireMeDeveloper.").color(NamedTextColor.AQUA)),
    TIP_4(Component.text("Tip: Sneaking with the move tool will let you rotate instead.").color(NamedTextColor.GREEN)),
    TIP_5(Component.text("Only the best can reach level 9.").color(NamedTextColor.AQUA)),
    TIP_6(Component.text("Is a level 10 quad clear even possible?").color(NamedTextColor.YELLOW)),
    TIP_7(Component.text("Tip: The level increases with the number of line clears you earn.").color(NamedTextColor.AQUA)),
    TIP_8(Component.text("Tell your friends about the server.").color(NamedTextColor.GREEN)),
    TIP_9(Component.text("Tip: More points are earned the higher your current level.").color(NamedTextColor.AQUA)),
    TIP_10(Component.text("Rumor has it that HireMeDeveloper made this without QuillDev hmmm...").color(NamedTextColor.YELLOW)),
    TIP_11(Component.text("Tip: You get more score for clearing multiple lines with one Tetro block!").color(NamedTextColor.AQUA)),
    TIP_12(Component.text("Tip: The Blocks fall faster every level!!").color(NamedTextColor.GREEN)),
    TIP_13(Component.text("Don't forget to take breaks!").color(NamedTextColor.YELLOW)),
    TIP_14(Component.text("Tip: The box on the right of the screen shows the next piece that will drop!").color(NamedTextColor.AQUA)),
    TIP_15(Component.text("Imagine having the high score on every screen!").color(NamedTextColor.GREEN));

    private Component tip;

    ScrollingTips(Component tip) {
        this.tip = tip;
    }

    public Component getTip() {
        return tip;
    }
}
