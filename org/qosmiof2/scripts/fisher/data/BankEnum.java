package org.qosmiof2.scripts.fisher.data;


import org.powerbot.script.Area;
import org.powerbot.script.Tile;

/**
 * Created by Uporanik on 20.7.2014.
 */
public enum BankEnum {

    VARROCK_BANK(new Area(new Tile(3188, 3431, 0), new Tile(3182, 3446, 0))), ALKHARID_BANK(new Area(new Tile(3273, 3164, 0), new Tile(3268, 3175, 0)));

    private Area area;

    private BankEnum(Area area) {
        this.area = area;
    }

    private String capitalize(final String string) {
        return String.valueOf(string.charAt(0)).toUpperCase() + string.substring(1).toLowerCase();
    }

    @Override
    public String toString() {
        return capitalize(this.name()).replace("_", " ");
    }

    public Area getArea() {
        return area;
    }
}
