package org.qosmiof2.scripts.fisher.tasks;

import org.powerbot.script.Condition;
import org.powerbot.script.Random;
import org.powerbot.script.rt6.ClientContext;
import org.qosmiof2.scripts.fisher.data.FishingLocationEnum;
import org.qosmiof2.scripts.fisher.gui.Gui;
import org.qosmiof2.scripts.framework.Node;

/**
 * Created by Uporanik on 22.7.2014.
 */
public class WalkToSpotFromBank extends Node {

    private Gui gui;

    public WalkToSpotFromBank(ClientContext ctx, Gui gui) {
        super(ctx);
        this.gui = gui;
    }

    @Override
    public boolean activate() {
        return ctx.npcs.select().name("Fishing spot").isEmpty() && ctx.backpack.count() != 28 && !ctx.bank.opened() && !ctx.players.local().interacting().valid();
    }

    @Override
    public void execute() {

        switch (Random.nextInt(1, 10)) {
            case 5:
                ctx.camera.pitch(Random.nextInt(20, 70));
                ctx.movement.step(FishingLocationEnum.NPC_LOCATION.getTile().derive(1, 10));
                break;


            default:
                ctx.movement.step(FishingLocationEnum.NPC_LOCATION.getTile().derive(1, 10));
                break;
        }

        while (ctx.players.local().inMotion()) {
            Condition.sleep();
        }

    }
}
