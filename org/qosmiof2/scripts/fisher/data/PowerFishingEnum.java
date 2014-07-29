package org.qosmiof2.scripts.fisher.data;

/**
 * Created by Uporanik on 21.7.2014.
 */
public enum PowerFishingEnum {

    PowerFishingBoolean(false);

    private boolean powerFishing;

    private PowerFishingEnum(boolean powerFishing) {
        this.powerFishing = powerFishing;
    }

    public boolean getPowerFishing() {
        return powerFishing;
    }

    public boolean setPowerFishing(boolean powerFishing) {
        return this.powerFishing = powerFishing;
    }
}
