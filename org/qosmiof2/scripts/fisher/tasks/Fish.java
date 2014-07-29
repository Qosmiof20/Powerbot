package org.qosmiof2.scripts.fisher.tasks;

import org.powerbot.script.Area;
import org.powerbot.script.Condition;
import org.powerbot.script.Random;
import org.powerbot.script.Tile;
import org.powerbot.script.rt6.ClientContext;
import org.powerbot.script.rt6.Npc;
import org.qosmiof2.scripts.fisher.data.Action;
import org.qosmiof2.scripts.fisher.data.KaramjaEnum;
import org.qosmiof2.scripts.fisher.gui.Gui;
import org.qosmiof2.scripts.framework.Node;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Fish extends Node {

    private Gui gui;

    public Fish(ClientContext ctx, Gui gui) {
        super(ctx);
        this.gui = gui;
    }

    private ArrayList<String> fish = new ArrayList<String>();
    private final Area areaFishing = new Area(new Tile(2927, 3180, 0), new Tile(2918, 3172, 0));

    @Override
    public boolean activate() {
        return !ctx.players.local().interacting().valid()
                && ctx.backpack.select().count() < 28
                && !ctx.npcs.select().name("Fishing spot").isEmpty() && !ctx.players.local().inMotion();
    }

    public boolean containsAction(String action, Npc npc) {
        return Arrays.asList(npc.actions()).contains(action);
    }

    @Override
    public void execute() {
        Npc npc = ctx.npcs.nearest().shuffle().poll();
        System.out.println("-1");
        if (npc.inViewport() && ctx.movement.distance(npc, ctx.players.local().tile()) < 10) {
            System.out.println("0");
            if (containsAction(Action.ACTION.getAction(), npc)) {
                System.out.println("1");
                if (npc.interact(Action.ACTION.getAction())) {
                    System.out.println("2");
                    while (ctx.players.local().inMotion()) {
                        Condition.sleep();
                    }
                }
            } else {
                return;
            }


        } else {
            switch (Random.nextInt(1, 10)) {
                case 5:
                    ctx.camera.turnTo(npc);
                    ctx.camera.pitch(Random.nextInt(20, 84));
                    break;

                default:
                    ctx.camera.turnTo(npc);
                    break;
            }
            if (!npc.inViewport()) {
                ctx.movement.step(npc.tile().derive(1, 5));
                if (KaramjaEnum.KARAMJA.getBoolean()) {
                    ctx.movement.step(areaFishing.getRandomTile().derive(1, 5));
                }

            }


        }

    }
}
