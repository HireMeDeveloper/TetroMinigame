package me.hiremedev.tetrisplugin.players;

import me.hiremedev.tetrisplugin.display.types.TetrominoeSystem;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.PrimitiveIterator;
import java.util.UUID;

public class PlayerManager {
    private HashMap<Player, TetrominoeSystem> players;
    private HashMap<UUID, Integer> scores;

    public PlayerManager() {
        players = new HashMap<>();
    }

    public void registerPlayer(Player player, TetrominoeSystem system){
        players.put(player, system);
    }

    public void removePlayer(Player player){
        players.remove(player);
    }

    public HashMap<Player, TetrominoeSystem> getPlayers() {
        return players;
    }
}
