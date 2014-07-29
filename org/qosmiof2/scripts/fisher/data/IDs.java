package org.qosmiof2.scripts.fisher.data;

import java.util.IdentityHashMap;

/**
 * Created by Uporanik on 21.7.2014.
 */
public enum IDs {

    RAW_SHRIMPS(317),
    RAW_ANCHOVIES(321),
    RAW_SARDINE(327),
    RAW_SALMON(331),
    RAW_TROUT(335),
    RAW_GIANT_CARP(338),
    RAW_COD(341),
    RAW_HERRING(346),
    RAW_PIKE(349),
    RAW_MACKAREL(353),
    RAW_TUNA(359),
    RAW_BASS(363),
    RAW_SWORDFISH(371),
    RAW_LOBSTER(377),
    RAW_SHARK(383),
    LEAPING_TROUT(11328),
    LEAPING_SALMON(11330),
    LEAPING_STURGEON(11332),
    LEAPING_TROUT_1(11329),
    LEAPING_SALMON_1(11331),
    LEAPINg_STURGEON_1(11333),
    GUT_LEAPING_TROUT(25566),
    GUT_LEAPING_SALMON(25567),
    GUT_LEAPNIG_STURGEON(25568);


    private int id;

    private IDs(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
