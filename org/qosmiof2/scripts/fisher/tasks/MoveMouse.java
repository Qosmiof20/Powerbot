package org.qosmiof2.scripts.fisher.tasks;

import org.powerbot.script.Condition;
import org.powerbot.script.Random;
import org.powerbot.script.rt6.ClientContext;
import org.powerbot.script.rt6.GameObject;
import org.powerbot.script.rt6.Npc;
import org.powerbot.script.rt6.Player;
import org.qosmiof2.scripts.framework.Node;

import java.awt.event.KeyEvent;

public class MoveMouse extends Node {

    public MoveMouse(ClientContext ctx) {
        super(ctx);
    }

    @Override
    public boolean activate() {
        return ctx.players.local().interacting().valid();
    }

    @Override
    public void execute() {
        Player player = ctx.players.select().shuffle().poll();
        GameObject object = ctx.objects.select().shuffle().poll();
        Npc npc = ctx.npcs.select().shuffle().poll();

        switch (Random.nextInt(1, 40)) {

            case 5:
                player.click(false);
                Condition.sleep(Random.nextInt(2000, 10000));
                break;

            case 9:
                object.hover();
                Condition.sleep(Random.nextInt(5000, 10000));
                break;

            case 13:
                object.click(false);
                Condition.sleep(Random.nextInt(2000, 5000));
                break;

            case 15:
                ctx.input.press(KeyEvent.VK_ENTER);
                Condition.sleep(Random.nextInt(1000, 5000));
                ctx.input.press(KeyEvent.VK_ENTER);
                break;

            case 20:
                player.hover();
                Condition.sleep(Random.nextInt(3000, 8000));
                break;

            case 23:
                npc.click(false);
                Condition.sleep(Random.nextInt(2000, 8000));
                break;

            case 25:
                npc.hover();
                Condition.sleep(Random.nextInt(1000, 5000));
                break;

            case 30:
                ctx.input.move(Random.nextInt(0, ctx.game.dimensions().width), Random.nextInt(0, ctx.game.dimensions().height));
                Condition.sleep(Random.nextInt(1000, 10000));
                break;

            default:
                Condition.sleep(Random.nextInt(1000, 2000));
                break;
        }

    }

}
