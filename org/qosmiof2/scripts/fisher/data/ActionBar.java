package org.qosmiof2.scripts.fisher.data;

/**
 * Created by Uporanik on 21.7.2014.
 */
public enum ActionBar {

    ACTION_BAR(false);

    private boolean useActionBar;

    private ActionBar(boolean useActionBar) {
        this.useActionBar = useActionBar;
    }


    public boolean getUseActionBar() {
        return useActionBar;
    }

    public boolean setUseActionBar(boolean useActionBar) {
        return this.useActionBar = useActionBar;
    }
}
