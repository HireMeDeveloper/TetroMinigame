package me.hiremedev.tetrisplugin.scoreboards;

import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class ScoreboardUpdater extends BukkitRunnable {
    private List<OpScoreboard> boards;

    public ScoreboardUpdater() {
        boards = new ArrayList<>();
    }

    public void addBoard(OpScoreboard opScoreboard){
        boards.add(opScoreboard);
    }

    public void removeBoard(OpScoreboard opScoreboard){
        boards.remove(opScoreboard);
    }

    @Override
    public void run() {
        boards.forEach(
                (board) -> board.update()
        );
    }
}
