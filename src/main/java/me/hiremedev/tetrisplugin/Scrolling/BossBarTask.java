package me.hiremedev.tetrisplugin.Scrolling;

import me.hiremedev.tetrisplugin.scoreboards.Entries.Entry;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.io.BukkitObjectInputStream;

import java.util.*;

public class BossBarTask extends BukkitRunnable {
    private BossBarManager bossBarManager;
    private Iterator<ScrollingTips> iterator;
    private long startTipTime;
    private final long lifetime = 12000;

    private BossBar bossBar;

    public BossBarTask(BossBarManager bossBarManager){
        this.bossBarManager = bossBarManager;
        iterator = Arrays.stream(ScrollingTips.values()).iterator();
        startTipTime = System.currentTimeMillis();
        this.bossBar = BossBar.bossBar(getNextTip(), 1, BossBar.Color.GREEN, BossBar.Overlay.PROGRESS);
        bossBarManager.registerBossBar(bossBar);
        var rand = new Random();

        startTipTime = System.currentTimeMillis();
    }

    private Component getNextTip(){
        if(!iterator.hasNext()) {
            iterator = Arrays.stream(ScrollingTips.values()).iterator();
        }
        return iterator.next().getTip();
    }

    @Override
    public void run() {
        var currentTime = System.currentTimeMillis();

        var endTime = startTipTime + lifetime;
        var delta = currentTime - startTipTime;

        var progress =  1 - ( delta / (float) lifetime);
        if(progress <= 0) {
            startTipTime = System.currentTimeMillis();
            bossBar.name(getNextTip());
        }
        bossBar.progress((progress > 0) ? progress : 0);
    }
}
