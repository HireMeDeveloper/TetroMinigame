package me.hiremedev.tetrisplugin.controls;

import me.hiremedev.tetrisplugin.controls.keys.Key;
import me.hiremedev.tetrisplugin.controls.keys.KeyManager;
import me.hiremedev.tetrisplugin.controls.keys.Slottable;
import me.hiremedev.tetrisplugin.display.util.GameState;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class ControlItemFactory {
    private KeyManager keyManager;
    private List<Player> players;
    private String screenName;

    public ControlItemFactory(KeyManager keyManager, String screenName) {
        this.keyManager = keyManager;
        this.players = new ArrayList<>();
        this.screenName = screenName;
    }

    public void addPlayer(Player... players) {
        this.players.addAll(Arrays.asList(players));
    }

    public void clearPlayers(){
        players = new ArrayList<>();
    }

    private List<Slottable> createPlayItems() {
        List<Slottable> items = new ArrayList<>();
        items.add(new Slottable(
                        keyManager.addStringKey(
                                addCustomName(
                                        new ItemStack(Material.SUGAR),
                                        "click to MOVE left or right",
                                        NamedTextColor.BLUE
                                ),
                                Key.MOVE.getKey(),
                                screenName
                        ),
                Key.MOVE.getSlot()
                )
        );
        items.add(new Slottable(
                        keyManager.addStringKey(
                                addCustomName(new ItemStack(Material.BOWL),
                                        "click to ROTATE left or right",
                                        NamedTextColor.AQUA
                                ),
                                Key.ROTATE.getKey(),
                                screenName
                        ),
                Key.ROTATE.getSlot()
                )
        );
        items.add(new Slottable(
                        keyManager.addStringKey(
                                addCustomName(new ItemStack(Material.PRISMARINE_SHARD),
                                        "click or drop to FAST DROP",
                                        NamedTextColor.YELLOW
                                ),
                                Key.FAST_DROP.getKey(),
                                screenName
                        ),
                Key.FAST_DROP.getSlot()
                )
        );
        items.add(new Slottable(
                        keyManager.addStringKey(
                                addCustomName(new ItemStack(Material.REDSTONE),
                                        "click to RESTART",
                                        NamedTextColor.RED
                                ),
                                Key.RESET.getKey(),
                                screenName
                        ),
                Key.RESET.getSlot()
                )
        );
        items.add(new Slottable(
                        keyManager.addStringKey(
                                addCustomName(new ItemStack(Material.TNT),
                                        "click to QUIT",
                                        NamedTextColor.RED
                                ),
                                Key.QUIT.getKey(),
                                screenName
                        ),
                        Key.QUIT.getSlot()
                )

        );
        return items;
    }

    private List<Slottable> createStandbyItems() {
        List<Slottable> items = new ArrayList<>();
        items.add(new Slottable(
                        keyManager.addStringKey(
                                addCustomName(new ItemStack(Material.EMERALD),
                                        "click to PLAY",
                                        NamedTextColor.GREEN
                                ),
                                Key.PLAY.getKey(),
                                screenName
                        ),
                Key.PLAY.getSlot()
                )
        );
        items.add(new Slottable(
                        keyManager.addStringKey(
                                addCustomName(new ItemStack(Material.TNT),
                                        "click to QUIT",
                                        NamedTextColor.RED
                                ),
                                Key.QUIT.getKey(),
                                screenName
                        ),
                Key.QUIT.getSlot()
                )

        );
        return items;
    }

    private ItemStack addCustomName(ItemStack item, String name, NamedTextColor color) {
        var meta = item.getItemMeta();
        var data = meta.getPersistentDataContainer();
        meta.displayName(Component.text(name).color(color));
        item.setItemMeta(meta);
        return item;
    }

    public void giveItems(GameState state) {
        switch (state) {
            case STANDBY_END:
            case STANDBY_START:
                var standbyItems = createStandbyItems();
                players.forEach((player) -> player.getInventory().clear());
                players.forEach(
                        (player) -> {
                            standbyItems.forEach((item) -> player.getInventory().setItem(item.getSlot(),item.getItem()));
                        }
                );
                break;
            case PLAYING:
                var playItems = createPlayItems();
                var it = playItems.iterator();
                players.forEach((player) -> player.getInventory().clear());
                while (it.hasNext()) {
                    players.forEach(
                            (player) -> {
                                if (it.hasNext()) {
                                    var slotable = it.next();
                                    player.getInventory().setItem(slotable.getSlot(), slotable.getItem());
                                    it.remove();
                                }
                            }
                    );
                }
                break;
            case OFF:
                break;
        }


    }
}
