package org.qosmiof2.scripts.fisher.data;

/**
 * Created by Uporanik on 20.7.2014.
 */
public enum Action {

    ACTION("");

    private String action;

    private Action(String action) {
        this.action = action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getAction() {
        return action;
    }
}
