package me.hiremedev.tetrisplugin.display.types;

import me.hiremedev.tetrisplugin.controls.ControlItemFactory;
import me.hiremedev.tetrisplugin.controls.keys.KeyManager;
import me.hiremedev.tetrisplugin.database.DatabaseManager;
import me.hiremedev.tetrisplugin.database.SystemDocument;
import me.hiremedev.tetrisplugin.display.screens.ScreenStyle;
import me.hiremedev.tetrisplugin.display.screens.SystemManager;
import me.hiremedev.tetrisplugin.display.screens.SpriteScreen;
import me.hiremedev.tetrisplugin.display.sprites.Sprite;
import me.hiremedev.tetrisplugin.display.sprites.tetrominoes.Block;
import me.hiremedev.tetrisplugin.display.sprites.tetrominoes.Rotation;
import me.hiremedev.tetrisplugin.display.sprites.tetrominoes.Tetrominoe;
import me.hiremedev.tetrisplugin.display.util.Axis;
import me.hiremedev.tetrisplugin.display.util.GameState;
import me.hiremedev.tetrisplugin.display.util.Level;
import me.hiremedev.tetrisplugin.display.util.PointMath;
import me.hiremedev.tetrisplugin.engine.MasterManager;
import me.hiremedev.tetrisplugin.scoreboards.ScoreboardHandler;
import me.hiremedev.tetrisplugin.tasks.FallingSprites;
import me.hiremedev.tetrisplugin.tasks.UpdateScoreboardTask;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;

import java.awt.geom.Point2D;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;
import java.util.UUID;

public class TetrominoeSystem {
    private JavaPlugin plugin;
    private DatabaseManager databaseManager;
    private ScoreboardHandler scoreboardHandler;
    private String name;
    private SpriteScreen mainDisplay;
    private SpriteScreen nextBox;
    private final Point2D offset = new Point2D.Double(11, 4);

    private HashMap<Player, UUID> players;
    private ControlItemFactory itemFactory;

    private GameState state = GameState.OFF;
    private Axis axis;
    private Location location;
    private ScreenStyle style;

    private Block nextBlock;
    private Level currentLevel = Level._1;
    private Level highestLevel;
    private int clears = 0;
    private int highScore;
    private String highScoreName;
    private int score = 0;

    private FallingSprites clock;
    private int taskId;

    public TetrominoeSystem(String name,
                            Location location,
                            Axis axis,
                            ScreenStyle style,
                            JavaPlugin plugin ,
                            ScoreboardHandler scoreboardHandler,
                            DatabaseManager databaseManager,
                            SystemManager systemManager,
                            KeyManager keyManager,
                            Boolean createDoc) {
        this.scoreboardHandler = scoreboardHandler;
        this.plugin = plugin;
        this.databaseManager = databaseManager;
        this.name = name;
        this.axis = axis;
        this.location = location;
        this.style = style;
        mainDisplay = new SpriteScreen(
                name,
                location,
                axis,
                22,
                10,
                style,
                this
        );
        nextBox = new SpriteScreen(
                (name + " next box"),
                PointMath.locationFromPoint(location, axis, offset, 0),
                axis,
                6,
                6,
                style,
                this
        );

        systemManager.registerSystem(this);
        var rand = new Random();
        nextBlock = Block.values()[rand.nextInt(Block.values().length)];

        clock = new FallingSprites(mainDisplay.getSpriteManager());
        taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, clock, 0, currentLevel.getPeriod());

        players = new HashMap<>();
        itemFactory = new ControlItemFactory(keyManager, name);

        if (createDoc) databaseManager.saveSystemDocument(new SystemDocument(name, axis, location, highScore, highScoreName, style));
        scoreboardHandler.createScoreboardForSystem(this);
    }

    public void lose(Boolean quit, GameState state){
        if(score > highScore) {
            highScoreName = ((Player) players.keySet().toArray()[0]).getName();
            highScore = score;
            players.forEach(
                    ((player, uuid) -> player.showTitle(
                            Title.title(
                                    Component.text("NEW HIGH SCORE!").color(NamedTextColor.GOLD).decorate(TextDecoration.BOLD),
                                    Component.text("Score: %s".formatted(highScore))
                            )
                    ))
            );
            databaseManager.updateSystemDoc(
                    new SystemDocument(name, axis, location, highScore, highScoreName, style)
            );
        }else{
            players.forEach(
                    (player, uuid) -> player.showTitle(
                            Title.title(
                                    Component.text((quit) ? "YOU QUIT!" : "YOU LOSE!").color(NamedTextColor.RED),
                                    Component.text("Score: %s".formatted(score))
                            )
                    )
            );
        }
        setState(state);
        Bukkit.getLogger().info("%s: %s".formatted(highScoreName, highScore));
    }

    public Block getNextBlock(){
        var returnedBlock = nextBlock;
        var rand = new Random();
        nextBlock = Block.values()[rand.nextInt(Block.values().length)];
        var tetromino = Tetrominoe.getTetrominoe(nextBlock, Rotation.ROT_0);
        var sprite = new Sprite(tetromino.name(),tetromino.getValues(), new Point2D.Double(2,2));
        nextBox.clearScreen();
        nextBox.spriteSet(sprite, true);
        return returnedBlock;
    }

    public void setState(GameState state) {
        this.state = state;
        Bukkit.getLogger().info("%s state changed to %s".formatted(name, state));
        switch (state) {
            case OFF -> {
                databaseManager.updateSystemDoc(
                        new SystemDocument(name, axis, location, highScore, highScoreName, style)
                );
                clearPlayers();
                mainDisplay.clearScreen();
                nextBox.clearScreen();
            }
            case STANDBY_START, STANDBY_END -> {
                itemFactory.giveItems(state);
                mainDisplay.clearScreen();
                nextBox.clearScreen();
            }
            case PLAYING -> {
                highestLevel = Level._1;
                currentLevel = Level._1;
                clears = 0;
                score = 0;
                clockReset();
                mainDisplay.clearScreen();
                nextBox.clearScreen();
                itemFactory.giveItems(state);
                mainDisplay.getSpriteManager().dropRandomPeice();
            }
        }
    }

    private void clockReset(){
        Bukkit.getScheduler().cancelTask(taskId);
        taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, clock, 0, currentLevel.getPeriod());
    }

    public void addPlayer(Player player){
        players.put(player, player.getUniqueId());
        itemFactory.addPlayer(player);
    }

    private void clearPlayers(){
        itemFactory.clearPlayers();
        players.keySet().forEach(
                (player) -> player.getInventory().clear()
        );
        players = new HashMap<>();
    }

    public String getName() {
        return name;
    }

    public SpriteScreen getMainDisplay() {
        return mainDisplay;
    }

    public SpriteScreen getNextBox() {
        return nextBox;
    }

    public HashMap<Player, UUID> getPlayers() {
        return players;
    }

    public int getClears() {
        return clears;
    }

    public int getScore() {
        return score;
    }

    public void addScore(int amount) {
        score += amount;
    }

    public Level getCurrentLevel() {
        return currentLevel;
    }

    public String getHighScoreName() {
        return highScoreName;
    }

    public void setHighScoreName(String highScoreName) {
        this.highScoreName = highScoreName;
    }

    public int getHighScore() {
        return highScore;
    }

    public void setHighScore(int highScore) {
        this.highScore = highScore;
    }

    public void addClears(int amount) {
        this.clears = clears + amount;
        currentLevel = Level.getLevelFromClears(clears);
        if(currentLevel.getValue() > highestLevel.getValue()){
            players.forEach((player, uuid) -> {
                player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1,1);
                player.showTitle(Title.title(
                        Component.text(""),
                        Component.text("Level up!  Next at %s lines!".formatted(Level.getLevelFromValue(currentLevel.getValue() + 1).getLineRequirement()))
                                .color(NamedTextColor.GREEN)
                        )
                );
            });

            highestLevel = currentLevel;
        }
        clockReset();
    }

    public ScreenStyle getStyle() {
        return style;
    }

    public GameState getState() {
        return state;
    }
}
