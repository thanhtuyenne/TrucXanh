/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gamelathinh;

import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author Admin
 */
public class Stop extends javax.swing.JPanel {

    JButton p1 = new JButton("Resume");
    JButton p2 = new JButton("New Game");
    JButton p3 = new JButton("Home");

    public Stop(Container opj, Game game, NewJFrame menu) throws IOException {
        setSize(200, 200);
        p3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                game.setVisible(false);
                game.dispose();
                menu.setVisible(!menu.isVisible());
            }
        });
        p1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {   
                game.tempIndexPlayer = 10;
                opj.setVisible(false);
            }
        });
        p2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                int result = JOptionPane.showConfirmDialog(new JFrame(), "Drop all the points and play a new game?", "Message",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE);

                if (result == JOptionPane.YES_OPTION) {
                   
                    opj.setVisible(false);
                    NewGameNotFinish(game);
                     menu.glassPane.setVisible(false);
                    game.init();
                    game.nextLevel();
                }
            }
        });
        setPreferredSize(new java.awt.Dimension(200, 200));
        setLayout(new GridLayout(0, 1));
        add(p1);
        add(p2);
        add(p3);
        setBackground(new Color(0, 0, 0, 100));
        this.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder(null, "Pause", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial Black", 1, 18), new Color(255, 255, 255))));
    }

    public void NewGameNotFinish(Game game) {
        game.getBackup();
        game.addPlayerToList(game.nameStatic);
        game.listLabel[10].setText(game.nameStatic + " Score: 0");
        game.SetLabel();
        game.addList = true;
        
    }
}
