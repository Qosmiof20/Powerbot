package org.qosmiof2.scripts.fisher.data;

/**
 * Created by Uporanik on 21.7.2014.
 */
public enum KaramjaEnum {

    KARAMJA(false);

    private boolean valid;

    private KaramjaEnum(boolean valid) {
        this.valid = valid;
    }

    public boolean setBoolean(boolean valid) {
        return this.valid = valid;
    }

    public boolean getBoolean() {
        return valid;
    }
}
