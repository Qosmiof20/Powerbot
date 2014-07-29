package org.qosmiof2.scripts.fisher.tasks;

import org.powerbot.script.Condition;
import org.powerbot.script.Random;
import org.powerbot.script.rt6.ClientContext;
import org.powerbot.script.rt6.Item;
import org.powerbot.script.rt6.Widget;
import org.qosmiof2.scripts.fisher.data.ActionBar;
import org.qosmiof2.scripts.fisher.data.IDs;
import org.qosmiof2.scripts.fisher.data.PowerFishingEnum;
import org.qosmiof2.scripts.framework.Node;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

public class Drop extends Node {

    public Drop(ClientContext ctx) {
        super(ctx);
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
        return ctx.backpack.select().count() == 28
                && !ctx.players.local().interacting().valid();
    }


    @Override
    public void execute() {

        for (IDs enumId : IDs.values()) {
            if (!fish.contains(enumId.getId())) {
                fish.add(enumId.getId());
            }
        }

        if (ctx.widgets.component(1186, 2).text().startsWith("You can't carry")) {
            ctx.chat.clickContinue();
            ctx.input.move(Random.nextInt(0, ctx.game.dimensions().width), Random.nextInt(0, ctx.game.dimensions().height));
        }

        if (ActionBar.ACTION_BAR.getUseActionBar()) {
            dropUsingBar();
        } else {
            drop();
        }

    }

    private void dropUsingBar() {

        // for (int i = 0; i <= 28 && !ctx.backpack.id(toArray(fish)).isEmpty(); i++) {

        //  if (new int[]{ctx.combatBar.actionAt(0).id()} == toArray(fish)) {


        if (PowerFishingEnum.PowerFishingBoolean.getPowerFishing() == true) {
            if (ctx.combatBar.actionAt(0).id() != -1 && !ctx.backpack.select().id(ctx.combatBar.actionAt(0).id()).isEmpty()) {
                ctx.combatBar.actionAt(0).select();
            } else {
                ctx.combatBar.actionAt(1).select();
            }
            Condition.wait(new Callable<Boolean>() {
                @Override
                public Boolean call() throws Exception {
                    return false;
                }
            }, 100, 2);
        } else {
            for (int i = 0; i < 50 && !ctx.backpack.select().id(toArray(fish)).isEmpty();
                 i++) {
                Item item = ctx.backpack.id(ctx.combatBar.actionAt(0)).poll();
                Item item2 = ctx.backpack.id(ctx.combatBar.actionAt(1)).poll();
                if (ctx.combatBar.actionAt(0).id() != -1 && ctx.combatBar.actionAt(1).id() != -1 && !ctx.backpack.select().id(ctx.combatBar.actionAt(1).id()).isEmpty() && !ctx.backpack.select().id(ctx.combatBar.actionAt(0).id()).isEmpty()) {
                    ctx.combatBar.actionAt(0).select();
                    ctx.combatBar.actionAt(1).select();
                } else if (ctx.combatBar.actionAt(0).id() != -1 && !ctx.backpack.select().id(ctx.combatBar.actionAt(0).id()).isEmpty()) {
                    ctx.combatBar.actionAt(0).select();
                } else if (ctx.combatBar.actionAt(1).id() != -1 && !ctx.backpack.select().id(ctx.combatBar.actionAt(1).id()).isEmpty()) {
                    ctx.combatBar.actionAt(1).select();
                }

                Condition.wait(new Callable<Boolean>() {
                    @Override
                    public Boolean call() throws Exception {
                        return false;
                    }
                }, 100, 2);
            }
        }

    }


    private void drop() {

        if (PowerFishingEnum.PowerFishingBoolean.getPowerFishing() == true) {
            final Item item = ctx.backpack.id(toArray(fish)).first().poll();
            item.interact("Drop");
            Condition.wait(new Callable<Boolean>() {


                @Override
                public Boolean call() throws Exception {
                    return !item.valid();
                }

            }, 500, 5);
        } else {
            for (final Item item : ctx.backpack.id(toArray(fish))) {
                item.interact("Drop");
                Condition.wait(new Callable<Boolean>() {


                    @Override
                    public Boolean call() throws Exception {
                        return !item.valid();
                    }

                }, 500, 5);
            }

        }

    }

}
