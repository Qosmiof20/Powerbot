package org.qosmiof2.scripts.fisher.data;


import org.powerbot.script.Tile;

/**
 * Created by Uporanik on 22.7.2014.
 */
public enum FishingLocationEnum {

    NPC_LOCATION(new Tile(0, 0, 0));


    private Tile tile;

    private FishingLocationEnum(Tile tile) {
        this.tile = tile;
    }

    public Tile getTile() {
        return tile;
    }

    public Tile setTile(Tile tile) {
        return this.tile = tile;
    }
}
