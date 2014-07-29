package org.qosmiof2.scripts.fisher;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.SwingUtilities;

import org.powerbot.script.*;
import org.powerbot.script.Script.Manifest;
import org.powerbot.script.rt6.ClientContext;
import org.powerbot.script.rt6.Skills;
import org.qosmiof2.scripts.fisher.gui.Gui;
import org.qosmiof2.scripts.framework.Node;

@Manifest(description = "Trains fishing, anywere, anytime - by Qosmiof2", name = "QFisher", properties = "topic=1200965;client=6")
public class Fisher extends PollingScript<ClientContext> implements
        PaintListener {


    public static ArrayList<Node> nodeList = new ArrayList<Node>();

    @Override
    public void start() {
        expStart = ctx.skills.experience(Skills.FISHING);
        lvlStart = ctx.skills.realLevel(Skills.FISHING);
        exp = expStart;
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                new Gui(ctx);
            }

        });
    }

    @Override
    public void poll() {
        for (Node node : nodeList) {
            if (node.activate()) {
                node.execute();
                Condition.sleep(Random.nextInt(100, 500));
            }
        }

    }

    private int x, y;
    private int exp, expNow, expStart, gainedExp, caught, expPh, fishPh,
            lvlGained, lvlStart, lvlNow;
    private final Color color = new Color(0, 0, 0, 150);

    private final Font font1 = new Font("Arial", 1, 15);

    private String runTime() {
        final int sec = (int) (ctx.controller.script().getTotalRuntime() / 1000), h = sec / 3600, m = sec / 60 % 60, s = sec % 60;
        return (h < 10 ? "0" + h : h) + ":" + (m < 10 ? "0" + m : m) + ":"
                + (s < 10 ? "0" + s : s);
    }

    @Override
    public void repaint(Graphics g1) {
        x = ctx.input.getLocation().x;
        y = ctx.input.getLocation().y;
        expNow = ctx.skills.experience(Skills.FISHING);

        lvlNow = ctx.skills.realLevel(Skills.FISHING);
        gainedExp = expNow - expStart;
        lvlGained = lvlNow - lvlStart;

        fishPh = (int) (caught * (3600000D / ctx.controller.script()
                .getTotalRuntime()));

        expPh = (int) (gainedExp * (3600000D / ctx.controller.script()
                .getTotalRuntime()));

        Graphics g = (Graphics) g1;

        g.setColor(Color.RED);

        // g.drawLine(x - 10, y, x + 10, y);
        // g.drawLine(x, y - 10, x, y + 10);

        g.fillOval(ctx.input.getLocation().x - 2, ctx.input.getLocation().y, 5, 5);
        g.setColor(Color.BLACK);
        g.drawOval(ctx.input.getLocation().x - 3, ctx.input.getLocation().y, 6, 6);

        g.setColor(color);
        g.fillRect(8, 12, 175, 145);
        g.drawRect(8, 12, 175, 145);
        g.setFont(font1);
        g.setColor(Color.WHITE);
        g.drawString(runTime(), 17, 30);
        g.drawString("Caught: " + caught, 17, 50);
        g.drawString("Level: " + lvlNow + "(" + lvlGained + ")", 17, 70);
        g.drawString("Fish p/h: " + fishPh, 17, 90);
        g.drawString("Exp gained: " + gainedExp, 17, 110);
        g.drawString("Exp p/h: " + expPh, 17, 130);


        if (expNow > exp) {
            caught++;
            exp = expNow;
        }
    }
}
