/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gamelathinh;

import java.awt.Color;
import java.awt.FontFormatException;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.Timer;

/**
 *
 * @author ASUS
 */
public class Tutorial extends javax.swing.JFrame implements ActionListener {

    NewJFrame menu;
    JButtonInt button[][] = new JButtonInt[2][3];
    int value[] = {1, 1, 2, 2, 3, 3};
    int step = 0;
    boolean check = true;
    int timeLine = 0;
    Timer delay;
    Timer time;

    void delay() {
        delay = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {

                switch (step) {
                    case 1:
                        button[0][0].setVisible(false);
                        button[0][1].setVisible(false);
                        break;
                    case 3:
                        button[0][0].hideIcon();
                        button[0][1].hideIcon();
                        button[0][0].setEnabled(false);
                        button[0][1].setEnabled(false);
                        break;
                }
                delay.stop();
                step++;
                update();
            }
        });
        delay.start();

    }

    void step2() {
        button[0][0].setVisible(false);
        button[0][1].setVisible(false);

        button[0][2].setVisible(true);
        button[1][2].setVisible(true);

        button[0][2].setEnabled(true);
        button[1][2].setEnabled(true);

        button[0][2].value = 2;
        button[1][2].value = 3;

        button[0][2].setIconBt("clickhere1");
        button[1][2].setIconBt("clickhere1");
    }

    void newGame() {
        int n = JOptionPane.showConfirmDialog(new JFrame(), "Do you got the rule? \n Let's play game !");
        if (n == 0) {
            this.setVisible(false);
        } else {
            this.setVisible(false);
        }
    }

    void update() {
        switch (step) {
            case 1:
                jLabel1.setText("<html>You will turn over each pair of pictures in turn, only flipping 2 cells at a time.<br> "
                        + "<br>You have to find and flip pairs of similar pictures"
                        + " Pairs of the same picture that "
                        + "are flipped up will disappear</html>");
                delay();
                break;
            case 2:
                jLabel1.setText("You will continue to choose another pair of pictures");
                step2();
                break;
            case 3:
                jLabel1.setText("<html>If the pairs of pictures are different,the pair of pictures will flip back</html>");
                delay();
                break;
            case 4:
                button[0][2].hideIcon();
                button[1][2].hideIcon();
                time = new Timer(1000, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent ae) {
                        timeLine += 20;
                        jProgressBar1.setValue(100 - timeLine);
                        if (jProgressBar1.getValue() == 0) {
                            time.stop();
                            newGame();
                        }
                    }
                });
                time.start();
                jLabel1.setText("<html>And this is timeline. If time runs out and you haven't finished.<br> You gonna lose</html>");
                break;
        }

    }

    /**
     * Creates new form Tutorial
     */
    public Tutorial(NewJFrame menu) {
        this.menu = menu;
        initComponents();
        this.setLocationRelativeTo(null);
        setTitle("Tutorial");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        Image icon = Toolkit.getDefaultToolkit().getImage("psyduck.png");
        Image image = new ImageIcon(getClass().getResource("/Game/icon/BackGround.png")).getImage();
        this.setIconImage(icon);
        int m = 2, n = 3;
        jPanel1.setLayout(new GridLayout(m, n));

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                button[i][j] = new JButtonInt();//tạo 0ối t[ợng
                button[i][j].value = value[n * i + j];//nhạp giá trị
                if (i == 0 && j < 2) {
                    button[i][j].setIconBt("clickhere1");
                    button[i][j].setIconBt("clickhere1");

                } else {
                    button[i][j].hideIcon();//ẩn icon
                    button[i][j].setEnabled(false);//ẩn icon
                }
                button[i][j].setBackground(Color.white);//0ổi bg
                button[i][j].addActionListener(this);

                button[i][j].setActionCommand(i + " " + j);
                button[i][j].setBorder(null);
                jPanel1.add(button[i][j]);
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jProgressBar1 = new JProgressBar(0,100);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        jPanel2.setBackground(new java.awt.Color(120, 197, 239));

        jPanel1.setBackground(new java.awt.Color(255, 51, 51));
        jPanel1.setDebugGraphicsOptions(javax.swing.DebugGraphics.NONE_OPTION);
        jPanel1.setPreferredSize(new java.awt.Dimension(700, 700));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 700, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 700, Short.MAX_VALUE)
        );

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setText("In each round, the corresponding pairs of pictures will appear");
        jLabel1.setAlignmentX(0.5F);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(95, 95, 95)
                        .addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 484, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 590, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(123, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(209, 209, 209)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(115, 115, 115)
                        .addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JProgressBar jProgressBar1;
    // End of variables declaration//GEN-END:variables

    @Override
    public void actionPerformed(ActionEvent ae) {
        System.out.println(check);
        String[] s = ae.getActionCommand().split(" ");
        int i = Integer.parseInt(s[0]);
        int j = Integer.parseInt(s[1]);
        if (!button[i][j].isOpen) {
            check = !check;
            button[i][j].showIcon();
            if (check) {
                step++;
                update();
            }
        }
    }
}
