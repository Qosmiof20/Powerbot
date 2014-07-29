package org.qosmiof2.scripts.fisher.tasks;

import org.powerbot.script.Area;
import org.powerbot.script.Condition;
import org.powerbot.script.Random;
import org.powerbot.script.Tile;
import org.powerbot.script.rt6.ClientContext;
import org.qosmiof2.scripts.fisher.data.IDs;
import org.qosmiof2.scripts.framework.Node;

import java.util.ArrayList;
import java.util.List;



/**
 * Created by Uporanik on 21.7.2014.
 */
public class WalkToSpot extends Node {
    public WalkToSpot(ClientContext ctx) {
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

    private final Area areaFishing = new Area(new Tile(2927, 3180, 0), new Tile(2918, 3172, 0));
    private final Area areaExchange = new Area(new Tile(2847, 3141, 0), new Tile(2856, 3146, 0));
    private final Tile[] path = {new Tile(2852, 3143, 0), new Tile(2911, 3172, 0), new Tile(2897, 3163, 0), new Tile(2880, 3157, 0), new Tile(2864, 3147, 0)};


    @Override
    public boolean activate() {
        for (IDs enumId : IDs.values()) {
            if (!fish.contains(enumId.getId())) {
                fish.add(enumId.getId());
            }
        }

        if (ctx.widgets.component(1184, 9).text().startsWith("There ye goes")) {
            ctx.chat.clickContinue();
        }
        return !areaFishing.contains(ctx.players.local()) && ctx.backpack.count() != 28 && !ctx.players.local().interacting().valid();
    }

    @Override
    public void execute() {


        switch (Random.nextInt(1, 10)) {
            case 4:
                ctx.camera.pitch(Random.nextInt(20, 60));
                ctx.camera.turnTo(areaFishing.getRandomTile());
                ctx.movement.step(areaFishing.getRandomTile());
                break;

            case 7:
                ctx.camera.turnTo(areaFishing.getRandomTile());
                break;
            default:

                ctx.movement.step(areaFishing.getRandomTile());
                break;
        }



        while (ctx.players.local().inMotion()) {
            Condition.sleep();
        }


    }
}
