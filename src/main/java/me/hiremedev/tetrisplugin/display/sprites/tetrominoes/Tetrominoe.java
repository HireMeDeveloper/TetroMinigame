package me.hiremedev.tetrisplugin.display.sprites.tetrominoes;

import java.util.Arrays;
import java.util.stream.Collectors;

public enum Tetrominoe {
    T_PIECE_O(
            Block.T, Rotation.ROT_0,
            new Boolean[][]{
                    {false, false, false},
                    {true, true, true},
                    {false, true, false}}
    ),
    T_PIECE_90(
            Block.T, Rotation.ROT_90,
            new Boolean[][]{
                    {false, true, false},
                    {true, true, false},
                    {false, true, false}}
    ),
    T_PIECE_180(
            Block.T, Rotation.ROT_180,
            new Boolean[][]{
                    {false, false, false},
                    {false, true, false},
                    {true, true, true}}
    ),
    T_PIECE_270(
            Block.T, Rotation.ROT_270,
            new Boolean[][]{
                    {false, true, false},
                    {false, true, true},
                    {false, true, false}}
    ),
    O_PIECE_O(
            Block.O, Rotation.ROT_0,
            new Boolean[][]{
                    {false, false, false},
                    {false, true, true},
                    {false, true, true}}
    ),
    O_PIECE_90(
            Block.O, Rotation.ROT_90,
            new Boolean[][]{
                    {false, false, false},
                    {false, true, true},
                    {false, true, true}}
    ),
    O_PIECE_180(
            Block.O, Rotation.ROT_180,
            new Boolean[][]{
                    {false, false, false},
                    {false, true, true},
                    {false, true, true}}
    ),
    O_PIECE_270(
            Block.O, Rotation.ROT_270,
            new Boolean[][]{
                    {false, false, false},
                    {false, true, true},
                    {false, true, true}}
    ),
    L_PIECE_O(
            Block.L, Rotation.ROT_0,
            new Boolean[][]{
                    {false, false, false},
                    {true, true, true},
                    {true, false, false}}
    ),
    L_PIECE_90(
            Block.L, Rotation.ROT_90,
            new Boolean[][]{
                    {true, true, false},
                    {false, true, false},
                    {false, true, false}}
    ),
    L_PIECE_180(
            Block.L, Rotation.ROT_180,
            new Boolean[][]{
                    {false, false, false},
                    {false, false, true},
                    {true, true, true}}
    ),
    L_PIECE_270(
            Block.L, Rotation.ROT_270,
            new Boolean[][]{
                    {false, true, false},
                    {false, true, false},
                    {false, true, true}}
    ),
    J_PIECE_O(
            Block.J, Rotation.ROT_0,
            new Boolean[][]{
                    {false, false, false},
                    {true, true, true},
                    {false, false, true}}
    ),
    J_PIECE_90(
            Block.J, Rotation.ROT_90,
            new Boolean[][]{
                    {false, true, true},
                    {false, true, false},
                    {false, true, false}}
    ),
    J_PIECE_180(
            Block.J, Rotation.ROT_180,
            new Boolean[][]{
                    {false, false, false},
                    {true, false, false},
                    {true, true, true}}
    ),
    J_PIECE_270(
            Block.J, Rotation.ROT_270,
            new Boolean[][]{
                    {false, true, false},
                    {false, true, false},
                    {true, true, false}}
    ),
    S_PIECE_O(
            Block.S, Rotation.ROT_0,
            new Boolean[][]{
                    {false, false, false},
                    {false, true, true},
                    {true, true, false}}
    ),
    S_PIECE_90(
            Block.S, Rotation.ROT_90,
            new Boolean[][]{
                    {true, false, false},
                    {true, true, false},
                    {false, true, false}}
    ),
    S_PIECE_180(
            Block.S, Rotation.ROT_180,
            new Boolean[][]{
                    {false, false, false},
                    {false, true, true},
                    {true, true, false}}
    ),
    S_PIECE_270(
            Block.S, Rotation.ROT_270,
            new Boolean[][]{
                    {true, false, false},
                    {true, true, false},
                    {false, true, false}}
    ),
    Z_PIECE_O(
            Block.Z, Rotation.ROT_0,
            new Boolean[][]{
                    {false, false, false},
                    {true, true, false},
                    {false, true, true}}
    ),
    Z_PIECE_90(
            Block.Z, Rotation.ROT_90,
            new Boolean[][]{
                    {false, false, true},
                    {false, true, true},
                    {false, true, false}}
    ),
    Z_PIECE_180(
            Block.Z, Rotation.ROT_180,
            new Boolean[][]{
                    {false, false, false},
                    {true, true, false},
                    {false, true, true}}
    ),
    Z_PIECE_270(
            Block.Z, Rotation.ROT_270,
            new Boolean[][]{
                    {false, false, true},
                    {false, true, true},
                    {false, true, false}}
    ),
    I_PIECE_0(
            Block.I, Rotation.ROT_0,
            new Boolean[][]{
                    {false, false, false, false},
                    {true, true, true, true},
                    {false, false, false, false},
                    {false, false, false, false}}
    ),
    I_PIECE_90(
            Block.I, Rotation.ROT_90,
            new Boolean[][]{
                    {false, false, true, false},
                    {false, false, true, false},
                    {false, false, true, false},
                    {false, false, true, false}}
    ),
    I_PIECE_180(
            Block.I, Rotation.ROT_180,
            new Boolean[][]{
                    {false, false, false, false},
                    {true, true, true, true},
                    {false, false, false, false},
                    {false, false, false, false}}
    ),
    I_PIECE_270(
            Block.I, Rotation.ROT_270,
            new Boolean[][]{
                    {false, false, true, false},
                    {false, false, true, false},
                    {false, false, true, false},
                    {false, false, true, false}}
    );
    
    


    private Block block;
    private Rotation rotation;
    private Boolean[][] values;

    Tetrominoe(Block block, Rotation rotation, Boolean[][] values) {
        this.block = block;
        this.rotation = rotation;
        this.values = values;
    }
    public static Tetrominoe getTetrominoe(Block block, Rotation rotation){
        return Arrays.stream(Tetrominoe.values())
                .filter((tetrominoe)-> tetrominoe.block.equals(block))
                .filter((tetrominoe)-> tetrominoe.rotation.equals(rotation))
                .collect(Collectors.toList())
                .get(0);
    }

    public Block getBlock() {
        return block;
    }

    public Rotation getRotation() {
        return rotation;
    }

    public Boolean[][] getValues() {
        return values;
    }
}
