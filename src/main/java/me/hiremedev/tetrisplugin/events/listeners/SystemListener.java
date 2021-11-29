package me.hiremedev.tetrisplugin.events.listeners;

import me.hiremedev.tetrisplugin.display.screens.SystemManager;
import me.hiremedev.tetrisplugin.engine.MasterManager;
import me.hiremedev.tetrisplugin.events.PlayerSystemEvent;
import me.hiremedev.tetrisplugin.events.SystemAction;
import me.hiremedev.tetrisplugin.players.PlayerManager;
import me.hiremedev.tetrisplugin.scoreboards.ScoreboardHandler;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class SystemListener implements Listener {
    private PlayerManager playerManager;
    private ScoreboardHandler scoreboardHandler;
    private SystemManager systemManager;

    public SystemListener(MasterManager masterManager) {
        this.playerManager = playerManager;
        this.scoreboardHandler = scoreboardHandler;
        this.systemManager = systemManager;
    }

    @EventHandler
    public void onPlayerJoinSystem(PlayerSystemEvent event){
        var player = event.getPlayer();
        var system = event.getSystem();
        var action = event.getAction();

        if(action.equals(SystemAction.JOIN)) {
            playerManager.registerPlayer(player, system);
            scoreboardHandler.addPlayerToSystem(player, system);
            return;
        }
            playerManager.removePlayer(player);
            scoreboardHandler.removePlayerFromSystem(player, system);

    }
}
