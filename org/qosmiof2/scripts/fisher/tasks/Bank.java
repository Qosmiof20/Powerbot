package org.qosmiof2.scripts.fisher.tasks;

import org.powerbot.script.Condition;
import org.powerbot.script.Random;
import org.powerbot.script.rt6.ClientContext;
import org.qosmiof2.scripts.fisher.data.IDs;
import org.qosmiof2.scripts.fisher.gui.Gui;
import org.qosmiof2.scripts.framework.Node;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by Uporanik on 22.7.2014.
 */
public class Bank extends Node {
    private Gui gui;

    public Bank(ClientContext ctx, Gui gui) {
        super(ctx);
        this.gui = gui;
    }

    private ArrayList<Integer> fish = new ArrayList<Integer>();


    public static int[] toArray(List<Integer> list) {
        final int[] array = new int[list.size()];
        int count = 0;
        for (int i : list) {
            array[count] = i;
            count++;
        }
        return array;
    }

    @Override
    public boolean activate() {

        for (IDs enumId : IDs.values()) {
            if (!fish.contains(enumId.getId())) {
                fish.add(enumId.getId());
            }
        }

        return gui.bank.getArea().contains(ctx.players.local().tile()) && ctx.backpack.select().count() == 28;
    }

    @Override
    public void execute() {
        if (ctx.bank.inViewport() && ctx.movement.reachable(ctx.bank.nearest().tile(), ctx.players.local().tile())) {
            if (ctx.bank.open()) {

                Condition.wait(new Callable<Boolean>() {
                    @Override
                    public Boolean call() throws Exception {
                        return ctx.bank.opened();
                    }
                }, 500, 5);

                if (ctx.bank.opened()) {
                    ctx.bank.presetGear1();
                    Condition.wait(new Callable<Boolean>() {
                        @Override
                        public Boolean call() throws Exception {
                            return !ctx.bank.opened();
                        }
                    }, 500, 5);
                }
            }
        } else {
            ctx.camera.turnTo(ctx.bank.nearest().tile().derive(1, 10));
            ctx.camera.pitch(Random.nextInt(20, 80));

            if (!ctx.bank.inViewport()) {
                ctx.movement.step(ctx.bank.nearest().tile().derive(1, 5));
                while (ctx.players.local().inMotion()) {
                    Condition.sleep();

                }
            }

        }
    }
}
