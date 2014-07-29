package org.qosmiof2.scripts.fisher.tasks;

import org.powerbot.script.Condition;
import org.powerbot.script.Random;
import org.powerbot.script.rt6.ClientContext;
import org.qosmiof2.scripts.fisher.gui.Gui;
import org.qosmiof2.scripts.framework.Node;

/**
 * Created by Uporanik on 22.7.2014.
 */
public class WalkToBank extends Node {

    private Gui gui;

    public WalkToBank(ClientContext ctx, Gui gui) {
        super(ctx);
        this.gui = gui;
    }

    @Override
    public boolean activate() {
        return ctx.backpack.select().count() == 28 && !gui.bank.getArea().contains(ctx.players.local().tile()) && !ctx.players.local().interacting().valid();
    }

    @Override
    public void execute() {

        switch (Random.nextInt(1, 10)) {
            case 5:
                ctx.camera.turnTo(gui.bank.getArea().getRandomTile().derive(1, 5));
                ctx.camera.pitch(Random.nextInt(20, 70));
                ctx.movement.step(gui.bank.getArea().getRandomTile());
                break;


            default:
                ctx.movement.step(gui.bank.getArea().getRandomTile());
                break;
        }

        while (ctx.players.local().inMotion()) {
            Condition.sleep();
        }

    }
}
