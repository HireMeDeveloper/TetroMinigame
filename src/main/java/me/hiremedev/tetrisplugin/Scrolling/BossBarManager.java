package me.hiremedev.tetrisplugin.Scrolling;

import net.kyori.adventure.bossbar.BossBar;

import java.util.ArrayList;
import java.util.List;

public class BossBarManager {
    private List<BossBar> bossBars;

    public BossBarManager(){
        bossBars = new ArrayList<>();
    }
    public void registerBossBar(BossBar bossbar){
        bossBars.add(bossbar);
    }
    public void removeBossBar(BossBar bossbar){
        bossBars.remove(bossbar);
    }
    public List<BossBar> bossBars(){
        return bossBars;
    }
}
