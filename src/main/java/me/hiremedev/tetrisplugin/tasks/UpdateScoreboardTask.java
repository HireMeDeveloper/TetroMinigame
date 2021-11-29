package me.hiremedev.tetrisplugin.tasks;

import me.hiremedev.tetrisplugin.display.types.TetrominoeSystem;
import me.hiremedev.tetrisplugin.scoreboards.ScoreboardHandler;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;

public class UpdateScoreboardTask extends BukkitRunnable {
    private ScoreboardHandler scoreboardHandler;
    private TetrominoeSystem system;
    private Player player;

    public UpdateScoreboardTask(ScoreboardHandler scoreboardHandler, TetrominoeSystem system) {
        this.scoreboardHandler = scoreboardHandler;
        this.system = system;
        this.player = null;
    }

    public UpdateScoreboardTask(ScoreboardHandler scoreboardHandler, Player player){
        this.scoreboardHandler = scoreboardHandler;
        this.player = player;
        this.system = null;
    }

    @Override
    public void run() {
        if(player == null){
            scoreboardHandler.updateScoreboardForSystem(system);
            return;
        }
        scoreboardHandler.updateScoreboardForPlayer(player);
    }
}
