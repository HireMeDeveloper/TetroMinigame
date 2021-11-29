package me.hiremedev.tetrisplugin.display.screens;

import org.bukkit.Material;
import org.bukkit.Sound;

public enum ScreenStyle {
    CLASSIC(
            Material.REDSTONE_LAMP, Material.REDSTONE_BLOCK, Material.EMERALD_BLOCK,
            Sound.BLOCK_NOTE_BLOCK_BIT,
            Sound.BLOCK_NOTE_BLOCK_SNARE,
            Sound.BLOCK_NOTE_BLOCK_PLING
    ),
    ARCTIC(
            Material.WHITE_STAINED_GLASS, Material.POWDER_SNOW,Material.PACKED_ICE,
            Sound.BLOCK_SNOW_PLACE,
            Sound.BLOCK_SNOW_FALL,
            Sound.BLOCK_SNOW_BREAK
    ),
    GOTHIC(
            Material.GRAY_STAINED_GLASS, Material.MAGMA_BLOCK, Material.NETHERITE_BLOCK,
            Sound.BLOCK_LAVA_POP,
            Sound.ITEM_BUCKET_FILL_LAVA,
            Sound.BLOCK_LAVA_EXTINGUISH
    ),
    CUTE(
            Material.PINK_STAINED_GLASS,Material.PURPLE_WOOL, Material.WHITE_CONCRETE,
            Sound.BLOCK_WOOL_STEP,
            Sound.BLOCK_WOOL_PLACE,
            Sound.BLOCK_WOOL_BREAK
    ),
    BRICKY(
            Material.LIGHT_GRAY_STAINED_GLASS, Material.BRICKS, Material.PINK_TERRACOTTA,
            Sound.BLOCK_DEEPSLATE_BRICKS_STEP,
            Sound.BLOCK_DEEPSLATE_BRICKS_PLACE,
            Sound.BLOCK_DEEPSLATE_BRICKS_BREAK
    ),
    DARK(
            Material.TINTED_GLASS, Material.GRANITE, Material.OBSIDIAN,
            Sound.BLOCK_STONE_HIT,
            Sound.BLOCK_STONE_PLACE,
            Sound.BLOCK_STONE_BREAK
    );

    private Material front;
    private Material middle;
    private Material back;

    private Sound moveSound;
    private Sound dropSound;
    private Sound clearSound;

    ScreenStyle(Material front, Material middle, Material back, Sound moveSound, Sound dropSound, Sound clearSound) {
        this.front = front;
        this.middle = middle;
        this.back = back;
        this.moveSound = moveSound;
        this.dropSound = dropSound;
        this.clearSound = clearSound;
    }

    public Material getFront() {
        return front;
    }

    public Material getMiddle() {
        return middle;
    }

    public Material getBack() {
        return back;
    }

    public Sound getMoveSound() {
        return moveSound;
    }

    public Sound getDropSound() {
        return dropSound;
    }

    public Sound getClearSound() {
        return clearSound;
    }
}
