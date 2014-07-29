package org.qosmiof2.scripts.fisher.gui;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import org.powerbot.script.Tile;
import org.powerbot.script.rt6.ClientAccessor;
import org.powerbot.script.rt6.ClientContext;
import org.powerbot.script.rt6.Npc;
import org.qosmiof2.scripts.fisher.Fisher;
import org.qosmiof2.scripts.fisher.data.*;
import org.qosmiof2.scripts.fisher.data.Action;
import org.qosmiof2.scripts.fisher.tasks.*;

public class Gui extends ClientAccessor {

    public Gui(ClientContext ctx) {
        super(ctx);
        init();
    }

    private JFrame frame = new JFrame("QAIO Fisher");
    private JPanel panel = new JPanel();
    private JPanel panelOptional = new JPanel();
    private DefaultListModel<String> model = new DefaultListModel<String>();
    private JList<String> list = new JList<String>(model);
    private JScrollPane sPane = new JScrollPane(list);
    private JLabel label = new JLabel(
            "<html>Please select action you<br>want to interact with: </html>");
    private JButton button = new JButton("Refresh");
    private JButton buttonStart = new JButton("Start");
    private JTabbedPane tP = new JTabbedPane();
    private JCheckBox checkBox = new JCheckBox("Use action bar");
    private JCheckBox checkBox1 = new JCheckBox("Powerfishing");
    private JCheckBox checkBox2 = new JCheckBox("Bank");
    private JCheckBox checkBox3 = new JCheckBox("Normal");
    private JCheckBox checkBox4 = new JCheckBox("Karamja style");
    private ButtonGroup group = new ButtonGroup();

    public BankEnum bank;
    private JLabel bankLabel = new JLabel("Select nearest bank: ");

    private JComboBox<BankEnum> comboBox = new JComboBox<BankEnum>(BankEnum.values());


    private void init() {


        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setSize(new Dimension(135, 255));
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
        frame.add(tP);

        panel();
        optionalPanel();

        tP.add("Action", panel);
        tP.add("Optional", panelOptional);
        tP.setFocusable(false);

        group.add(checkBox1);
        group.add(checkBox2);
        group.add(checkBox3);
        group.add(checkBox4);

        buttonStart.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                Action.ACTION.setAction(list.getSelectedValue());
                System.out.println(Action.ACTION.getAction());


                if (checkBox.isSelected()) {
                    ActionBar.ACTION_BAR.setUseActionBar(true);
                }
                if (checkBox1.isSelected() || checkBox3.isSelected()) {
                    Fisher.nodeList.add(new Drop(ctx));
                    if (checkBox1.isSelected()) {
                        PowerFishingEnum.PowerFishingBoolean.setPowerFishing(true);
                    }
                }

                if (checkBox2.isSelected()) {
                    //Fisher.nodeList.add(new WalkToBank(ctx));
                    bank = (BankEnum) comboBox.getSelectedItem();
                    Fisher.nodeList.add(new WalkToBank(ctx, Gui.this));
                    Fisher.nodeList.add(new WalkToSpotFromBank(ctx, Gui.this));
                    Fisher.nodeList.add(new Bank(ctx, Gui.this));
                    Npc npc = ctx.npcs.select().name("Fishing spot").nearest().poll();

                    FishingLocationEnum.NPC_LOCATION.setTile(npc.tile());
                    System.out.println(bank);

                }
                if (checkBox4.isSelected()) {
                    Fisher.nodeList.add(new Exchange(ctx));
                    Fisher.nodeList.add(new WalkToSpot(ctx));
                }


                Fisher.nodeList.add(new Fish(ctx, Gui.this));
                Fisher.nodeList.add(new MoveMouse(ctx));

                frame.dispose();

            }

        });

    }

    private void panel() {
        panel.setLayout(new FlowLayout(FlowLayout.LEFT));
        panel.add(label);
        panel.add(sPane);
        panel.add(button);
        panel.add(buttonStart);

        label.setPreferredSize(new Dimension(150, 50));

        sPane.setPreferredSize(new Dimension(115, 50));
        sPane.setFocusable(false);
        list.setFocusable(false);
        sPane.setBorder(BorderFactory.createEtchedBorder());

        button.setPreferredSize(new Dimension(115, 30));
        button.setFocusable(false);

        buttonStart.setPreferredSize(new Dimension(115, 30));
        buttonStart.setFocusable(false);

        button.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                for (Npc npc : ctx.npcs.select().name("Fishing spot")) {
                    for (String string : npc.actions()) {
                        if (!model.contains(string)) {
                            model.addElement(string);
                        }
                    }
                }

            }

        });


    }


    private void optionalPanel() {
        panelOptional.setLayout(new FlowLayout(FlowLayout.LEFT));
        panelOptional.add(checkBox);
        panelOptional.add(Box.createRigidArea(new Dimension(20, 0)));
        panelOptional.add(checkBox1);
        panelOptional.add(checkBox2);
        panelOptional.add(checkBox3);
        panelOptional.add(checkBox4);
        panelOptional.add(Box.createRigidArea(new Dimension(50, 0)));
        panelOptional.add(bankLabel);
        panelOptional.add(comboBox);

        checkBox3.setSelected(true);


        checkBox.setPreferredSize(new Dimension(100, 20));
        checkBox1.setPreferredSize(new Dimension(100, 20));
        checkBox2.setPreferredSize(new Dimension(100, 20));
        checkBox3.setPreferredSize(new Dimension(100, 20));
        checkBox4.setPreferredSize(new Dimension(100, 20));
        bankLabel.setPreferredSize(new Dimension(120, 20));

        comboBox.setPreferredSize(new Dimension(115, 30));
        comboBox.setFocusable(false);
        comboBox.setEnabled(false);
        bankLabel.setEnabled(false);

        checkBox.setFocusable(false);
        checkBox1.setFocusable(false);
        checkBox2.setFocusable(false);
        checkBox3.setFocusable(false);
        checkBox4.setFocusable(false);

        checkBox2.setEnabled(false);

        checkBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (checkBox2.isSelected()) {
                    checkBox2.setSelected(false);
                    checkBox3.setSelected(true);
                    comboBox.setEnabled(false);
                    bankLabel.setEnabled(false);

                }

                if (checkBox4.isSelected()) {
                    checkBox4.setSelected(false);
                    checkBox3.setSelected(true);
                }

            }
        });

        checkBox1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (comboBox.isEnabled()) {
                    comboBox.setEnabled(false);
                    bankLabel.setEnabled(false);
                }
            }
        });

        checkBox3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (comboBox.isEnabled()) {
                    comboBox.setEnabled(false);
                    bankLabel.setEnabled(false);
                }
            }
        });

        checkBox2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (checkBox.isSelected()) {
                    checkBox.setSelected(false);
                }

                if (checkBox2.isSelected()) {
                    comboBox.setEnabled(true);
                    bankLabel.setEnabled(true);
                } else {
                    comboBox.setEnabled(false);
                    bankLabel.setEnabled(false);
                }
            }
        });

        checkBox4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (checkBox.isSelected()) {
                    checkBox.setSelected(false);
                }
                if (comboBox.isEnabled()) {
                    bankLabel.setEnabled(false);
                    comboBox.setEnabled(false);
                }
            }
        });
    }
}
