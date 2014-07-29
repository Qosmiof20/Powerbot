package org.qosmiof2.scripts.fisher.tasks;

import org.powerbot.script.Area;
import org.powerbot.script.Condition;

import org.powerbot.script.Random;
import org.powerbot.script.Tile;
import org.powerbot.script.rt6.ClientContext;
import org.powerbot.script.rt6.Npc;
import org.powerbot.script.rt6.TilePath;
import org.qosmiof2.scripts.fisher.data.IDs;
import org.qosmiof2.scripts.framework.Node;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by Uporanik on 21.7.2014.
 */
public class Exchange extends Node {
    public Exchange(ClientContext ctx) {
        super(ctx);
    }

    private final int id = 11267;
    private final Tile tile = new Tile(2851, 3142, 0);
    private final String action = "Exchange";
    private final Area areaExchange = new Area(new Tile(2847, 3141, 0), new Tile(2856, 3146, 0));
    private final Area areaFishing = new Area(new Tile(2927, 3180, 0), new Tile(2918, 3172, 0));

    private final Tile[] path = {new Tile(2911, 3172, 0), new Tile(2897, 3163, 0), new Tile(2880, 3157, 0), new Tile(2864, 3147, 0), new Tile(2852, 3143, 0)};


    private ArrayList<Integer> fish = new ArrayList<Integer>();


    //1184, 9


    @Override
    public boolean activate() {
        return ctx.backpack.select().count() == 28 && !ctx.players.local().interacting().valid();
    }

    @Override
    public void execute() {
        for (IDs enumId : IDs.values()) {
            if (!fish.contains(enumId.getId())) {
                fish.add(enumId.getId());
            }
        }


        Npc npc = ctx.npcs.select().id(id).nearest().first().poll();
        if (npc.inViewport() && areaExchange.contains(ctx.players.local().tile())) {
            if (npc.interact(action)) {
                while (ctx.players.local().inMotion()) {
                    Condition.sleep();
                }

                Condition.wait(new Callable<Boolean>() {
                    @Override
                    public Boolean call() throws Exception {
                        return ctx.widgets.component(1184, 9).visible();
                    }
                }, 500, 5);


            }
        } else {
            switch (Random.nextInt(1, 10)) {
                case 4:
                    ctx.camera.pitch(Random.nextInt(20, 60));
                    ctx.camera.turnTo(areaExchange.getRandomTile());
                    break;

                default:
                    ctx.camera.turnTo(areaExchange.getRandomTile());
                    break;
            }

            ctx.movement.step(areaExchange.getRandomTile());
            while (ctx.players.local().inMotion()) {
                Condition.sleep();
            }

        }

    }
}
