package be.craftcode.scrabble.domain;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

public class Player {
    private List<Letter> tiles;
    private int id;

    public Player(int id) {
        this.id = id;
        this.tiles = new ArrayList<>();
    }

    public void setTiles(List<Letter> tiles) {
        this.tiles = tiles;
    }

    public List<Letter> getTiles() {
        return tiles;
    }
    public void addTile(Letter tile){
        this.tiles.add(tile);
    }
}
