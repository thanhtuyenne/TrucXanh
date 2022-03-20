/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gamelathinh;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.Timer;
import javax.swing.border.Border;

/**
 *
 * @author Ca Vinh Thuan
 */
public class Game extends javax.swing.JFrame implements ActionListener {
    
    int tempIndexPlayer;
    int numberI1,//vị trí i của nút bắm thứ nhất
            numberI2, //vị trí i của nút bắm thứ hai
            numberJ1, ////vị trí j của nút bắm thứ nhất
            numberJ2,//vị trí j của nút bắm thứ hai

            value1,//giá trị nút bắm i1 x j1
            value2,//giá trị nút bắm i2 x j2
            finish,//giá trị khi finish
            level;//level đầu vào
    final JButtonInt button[][] = new JButtonInt[9][10];
    JLabel[] listLabel;
    ArrayList<Integer> value = new ArrayList<>();
//    int[] sizeY = {3, 4, 5, 6, 7, 8, 9, 10};
//    int[] sizeX = {2, 3, 4, 5, 6, 7, 8, 9};
    int[] sizeY = {3,4};
    int[] sizeX = {2,3};
    final Container glassPane;
    final int startLV = 0;
    boolean checking, addList = true;
    boolean check;
    int maxTime;
    int score;
    int time;
    int hard;
    NewJFrame menu;
    int lengthList = 11;
    final int MAXTIME = 160;//s
    boolean isBonus;
    boolean turnMusic;
    Player[] listplayer = new Player[lengthList];
    Player[] listplayer1 = new Player[lengthList];
    int bonusScore;
    int bonus;
    final int TIP_SCORE = 100;
    int minIndex = 0;
    String nameStatic = FillName.nameStatic;
    Border blackline = BorderFactory.createLineBorder(Color.black);
    Border redline = BorderFactory.createLineBorder(Color.red);

    /**
     * hàm khơi tạo giá trị ban đầu cho game
     */
    void init() {
        System.out.println("____________________________________________1");
        isBonus = false;
        this.level = -1;
        this.score = 0;
        time = 0;
        bonus = 20 + 10 * hard;//điểm ponus được tính theo công thức mức độ khó 0-3 nhân cho 10 điểm ,cộng cho điểm ponus thường
        bonusScore = 0;
        checking = true;
        check = false;
        maxTime = (-5 / 2) * hard * hard - (15 / 2) * hard + 160;   //thời gian chơi tùy theo độ khó từ 160 cho dễ nhất 135 cho trung bình và 120 cho khó
    }

    /**
     * khởi tạo lại bản game
     */
    void nextLevel() {
        System.out.println("____________________________________________2");
        
        this.level++;   //level tăng lên (số hàng số cột của board)
        updateGame();//vẽ lại bảng game
    }

    /**
     * repaint lại bản game đặt các giá trị mới
     */
    void updateGame() {
        System.out.println("____________________________________________3");
        
        value.removeAll(value);//xóa hết các giá trị củ
        chess.removeAll();//xóa hết các button củ
        timer.stop(); //thanh thời gian dừng lại
        time = 0;//thời gian bắt đầu là 0
        jProgressBar1.setValue(maxTime);
        //cập nhật lại điểm số cho bản score
        labelScore.setText(score + "");
        labelBonus.setText(bonusScore + "");
        try {
            int m = sizeX[level];//m cột
            int n = sizeY[level];//n dòng
            this.setValue(m, n);
            finish = m * n / 2;
            chess.setLayout(new GridLayout(m, n));
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    button[i][j] = new JButtonInt();
                    button[i][j].value = value.get(n * i + j);
                    button[i][j].hideIcon();
                    button[i][j].setBackground(Color.white);
                    button[i][j].setActionCommand(i + " " + j);
                    button[i][j].addActionListener(this);
                    button[i][j].setBorder(null);
                    chess.add(button[i][j]);
                }
            }
            chess.revalidate();
        } catch (IndexOutOfBoundsException ex) {
            endCase(true);
        }
    }

    /**
     * hiện thị hình ảnh nơi giá trị bằng với tên ảnh
     */
    void open() {
        System.out.println("____________________________________________4");
        
        if (check) {
            button[numberI1][numberJ1].showIcon();
        } else {
            button[numberI2][numberJ2].showIcon();
        }
    }

    /**
     * đống hình lại(chuyển về dạng ẩn)
     */
    void close() {
        System.out.println("____________________________________________5");
        
        button[numberI1][numberJ1].hideIcon();
        button[numberI2][numberJ2].hideIcon();
    }

    //nếu hai hình 'giống nhau' thì hide nó đi
    void match() {
        System.out.println("____________________________________________6");
        addScore(TIP_SCORE);   //điểm tăng bình thường ở đây 
        button[numberI1][numberJ1].setVisible(false);
        button[numberI2][numberJ2].setVisible(false);
        finish--;
        if (finish == 0) {
            System.out.println("----");
            addScore(getBonus());
            addScore((int) (jProgressBar1.getPercentComplete() * TIP_SCORE));    //điểm tăng lên
            System.out.println("end");
            nextLevel();
        }
    }

    //hàm cập nhật điểm
    void addScore(int value) {
        System.out.println("____________________________________________7");
        System.out.println("before add score: " + this.score);
        this.score += value; //điểm số sẽ được cộng với giá trị chuyền vào
        labelScore.setText(score + "");
        System.out.println("after add score: " + this.score);
        getMinIndex();
        
        if (score > listplayer[minIndex].getScore() && addList) {
            addList = false;
            listplayer[minIndex].setName(nameStatic);
            listplayer[minIndex].setScore(score);
            tempIndexPlayer = minIndex;
            listLabel[10].setVisible(false);
        }
        updateScore(score, tempIndexPlayer);
        sortListRanking();
        listLabel[tempIndexPlayer].setBorder(redline);
        SetLabel();
        System.out.println("addscore: " + score);
        
    }

    /**
     * hàm dùng để kiểm tra hai thẻ có thực sự giống nhau hay không
     */
    void check() {
        System.out.println("____________________________________________8");
        
        int index1 = numberI1 * sizeY[level] + numberJ1;   //vị trí của ô đó
        int index2 = numberI2 * sizeY[level] + numberJ2;
        int value1 = this.value1;   //giá trị tại ô đó
        int value2 = this.value2;
        
        if (CheckL(index1, index2, value1, value2)) {
            match();    //trùng
            System.out.println("+ TIP_SCORE: " + TIP_SCORE);
            if (isBonus) {
                bonusScore = Integer.parseInt(labelBonus.getText()) + bonus;//tăng điểm ponus hiện tại
                labelBonus.setText(bonusScore + "");
            }
            isBonus = true; //sau 2 lần đúng liên tiếp thì có ponus
        } else {
            isBonus = false; //không trùng
            addScore(getBonus()); //điểm tăng đột biến ở đây ở đây(điểm ponus đc cộng vào score)
            System.out.println("+ bonus score: " + bonusScore);
            bonusScore = 0;//xã vàng
            labelBonus.setText("0");
            close();
        }
        checking = true;
    }

    /**
     * kiểm tra giá trị xem tại địa điểm và giá trị đó có phải là hai thẻ giống
     * nhau không
     *
     * @param index1
     * @param index2
     * @param value1
     * @param value2
     * @return
     */
    boolean CheckL(int index1, int index2, int value1, int value2) {
        System.out.println("____________________________________________9");
        
        return ((index1 != index2) && (value1 == value2));//giống nhau chỉ khi khác vị trí nhưng giá trị thì giống nhau
    }
    
    @Override
    public void actionPerformed(ActionEvent ae) {
        System.out.println("____________________________________________10");
        
        timer.start();//
        String[] s = ae.getActionCommand().split(" ");//cắt chuổi từ actioncommand
        int i = Integer.parseInt(s[0]);//giá trị m
        int j = Integer.parseInt(s[1]);//giá trị n với table m x n
        if (button[i][j].isOpen == false && checking) {//nếu nút chưa mở và đang không check hai nút thì mởi thẻ
            check = !check;
            if (check) {
                numberI1 = i;   //gán giá trị 
                numberJ1 = j;
                value1 = button[i][j].value;
                open();
            } else {
                numberI2 = i;
                numberJ2 = j;
                value2 = button[i][j].value;
                open();
                timerCheck.start();
                checking = false;
            }
        }
    }

    /**
     * hàm timer dùng để đặt lịch thực hiện một thao tác theo thời gian hàm này
     * dùng để đặt lại giá trị cho thanh progress bar theo thời gian 1s= 1000ms
     */
    Timer timer = new Timer(1000, new ActionListener() {
        
        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("____________________________________________11");
            
            time++;
            jProgressBar1.setValue(maxTime - time);
            if (maxTime == time) {
                System.out.println("+ bonus score (307): " + bonusScore);
                timer.stop();
                addScore(getBonus());//cộng điêm lần cuối
                endCase(false);//khi thời gian kết thúc thì gọi endcase để thông báo
            }
        }
    });
    
    int getBonus() {
        int bonusScore = this.bonusScore;
        this.bonusScore = 0;
        this.labelBonus.setText(this.bonusScore + "");
        return bonusScore;
    }
    //hàm này dùng để câu giờ check cho so sánh thẻ  tạo hiệu ứng (delay)
    Timer timerCheck = new Timer(250, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("____________________________________________12");
            
            check();
            timerCheck.stop();
        }
    });

    /**
     * hàm dùng để gán giá trị cho mảng m x n
     *
     * @param m
     * @param n
     */
    void setValue(int m, int n) {
        System.out.println("____________________________________________13");
                System.out.println("Score: "+score);

        int half = m * n / 2;
        int j = m * n;
        for (int i = 1; i <= j; i++) {
            if (i <= half) {
                value.add(i);
            } else {
                value.add(i - half);
            }
        }
        Collections.shuffle(value);//trộn lên giá trị 
    }

    /**
     * return: true game win return: false game lose
     *
     * @param result
     */
    void endCase(boolean result) {
        System.out.println("____________________________________________14");
        //xóa hết cái thẻ
        chess.removeAll();
        chess.revalidate();
        chess.repaint();
        chess.setLayout(new GridBagLayout());
        JLabel jLabel1 = new javax.swing.JLabel();
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Game/icon/" + (result ? "win" : "loss") + ".png")));
        chess.add(jLabel1);//thêm hình để thông báo
        //  hỏi người chơi có muốn chơi thêm không
        int choose = JOptionPane.showConfirmDialog(new JFrame(), "Game ended play a new game?", "Message",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);
        
        if (choose == JOptionPane.YES_OPTION) {
            System.out.println("choose yes");
            NewGameHaveFinish();                            //man choi moi
        } else {
            this.writeFile(listplayer);
            menu.setVisible(true);//hiện thị menu lại khi mà người dùng không chơi nữa
            this.setVisible(false);
            this.dispose();
        }

        //kiem tra diem co loat vao top hay khong
    }
    
    public void NewGameHaveFinish() {
        System.out.println("____________________________________________15");
        
        writeFile(listplayer);    //luu file
        listplayer = readFile(listplayer);  //read file
        backup();                                      //tao backup
        this.init();//khởi tạo lại các giá trị
        this.nextLevel(); //bắt đầu game
        glassPane.setVisible(false);
        addPlayerToList(nameStatic);              //add player vo list
        addList = true;
    }

    /**
     * Creates new form Game
     *
     * @throws java.io.IOException
     */
    public Game(int hard, NewJFrame menu) throws IOException {
        System.out.println("____________________________________________16");
        
        this.hard = hard;   //độ khó
        this.menu = menu;
        this.init(); //khởi tạo giá trị cho game
        Image image = new ImageIcon(getClass().getResource("/Game/icon/BackGround.png")).getImage();
        this.setContentPane(new JLabel(new ImageIcon(image)));//set background cho hình ảnh
        this.getContentPane().requestFocusInWindow();
        initComponents();
        this.setLocationRelativeTo(null);//căn giữa frame ở màng hình
        this.nextLevel(); //bắt đầu game
//        glasspane
        this.setGlassPane(new JComponent() {
            @Override
            protected void paintComponent(Graphics grphcs) {
                grphcs.setColor(new Color(0, 0, 0, 150));
                grphcs.fillRect(0, 0, getWidth(), getHeight());
            }
        });
        glassPane = (Container) super.getGlassPane();
        glassPane.setLayout(new GridBagLayout());
        glassPane.add(new Stop(glassPane, this, menu));                    //add panel or button can hien vao day, class stop
        glassPane.addMouseListener(new MouseAdapter() {
        });  //ngan chan input
        this.setVisible(true);

        //music
        //list rank
        listLabel = new JLabel[lengthList + 1];
        rank.setLayout(new FlowLayout());
        for (int k = 0; k < lengthList; k++) {
            listLabel[k] = new JLabel();
            listLabel[k].setPreferredSize(new Dimension(560, 50));
            listLabel[k].setFont(new Font("Serif", Font.PLAIN, 30));
            if (k != 10) {
                listLabel[k].setText(k + 1 + ".");
                listLabel[k].setBorder(blackline);
            } else {
                listLabel[k].setText(nameStatic + " Score: " + score);
                listLabel[k].setBorder(redline);
            }
            rank.add(listLabel[k]);
        }
        CreateList();
        preventNull();
        listplayer = readFile(listplayer);
        backup();
        addPlayerToList(nameStatic);
        SetLabel();
    }
    
    public void CreateList() {
        System.out.println("____________________________________________17");
        
        for (int i = 0; i < lengthList - 1; i++) {
            listplayer[i] = new Player();
        }
    }
    
    public void SetLabel() {
        System.out.println("____________________________________________18");
        
        for (int i = 0; i < lengthList - 1; i++) {
            listLabel[i].setText(i + 1 + ". " + listplayer[i].getName() + " Score: " + listplayer[i].getScore());
            if (i == tempIndexPlayer) {
                listLabel[i].setBorder(redline);
            } else {
                listLabel[i].setBorder(blackline);
            }
        }
    }
    
    public void sortListRanking() {
        System.out.println("____________________________________________19");
        
        for (int i = 0; i < lengthList - 1; i++) {
            for (int j = 0; j < lengthList - 2; j++) {
                if (listplayer[j].getScore() < listplayer[j + 1].getScore()) {
                    
                    if (tempIndexPlayer == j) {
                        tempIndexPlayer = j + 1;
                    } else if (tempIndexPlayer == j + 1) {
                        tempIndexPlayer = j;
                    }
                    Player temp = listplayer[j];
                    listplayer[j] = listplayer[j + 1];
                    listplayer[j + 1] = temp;
                    
                }
            }
        }
    }
    
    public void getMinIndex() {
        System.out.println("___________________________________________20");
        
        minIndex = 0;
        for (int i = 0; i < lengthList - 1; i++) {
            for (int j = 0; j < lengthList - 2; j++) {
                if (listplayer[j].getScore() > listplayer[j + 1].getScore()) {
                    minIndex = j + 1;
                }
            }
        }
    }
    
    public void preventNull() {
        System.out.println("____________________________________________21");
                System.out.println("Score: "+score);
                System.out.println(tempIndexPlayer);
        for (int i = 0; i < lengthList - 1; i++) {
            if (listplayer[i].getName() == null) {
                listplayer[i].setName("----");
                listplayer[i].setScore(0);
            }
        }
    }
    
    public int checkNullIndex() {
        System.out.println("____________________________________________22");
        preventNull();
        int index = -1;
        for (int i = 0; i < lengthList - 1; i++) {
            if (listplayer[i].getName().equals("----")) {
                index = i;
                break;
            }
        }
        return index;
    }
    
    public void addPlayerToList(String name) {
        System.out.println("____________________________________________23");
        System.out.println("Score: "+score);
        int index = checkNullIndex();
        System.out.println("index "+index);
        if (index == -1) {
            listLabel[tempIndexPlayer].setBorder(blackline);
            tempIndexPlayer = 10;
            updateScore(score, tempIndexPlayer);
            
            listLabel[10].setVisible(true);
        } else {
            listplayer[index].setName(nameStatic);
            listplayer[index].setScore(0);
            tempIndexPlayer = index;
            listLabel[10].setVisible(false);
            addList = false;
        }
    }
    
    public void updateScore(int score, int indexPlayer) {
        System.out.println("____________________________________________24");
        
        if (indexPlayer == 10) {
            listLabel[indexPlayer].setText(nameStatic + " " + score);
        } else {
            listplayer[indexPlayer].setName(nameStatic);
            listplayer[indexPlayer].setScore(score);
        }

//        listplayer[indexPlayer].setScore(score);
    }
    
    public void backup() {
        System.out.println("____________________________________________25");
        
        for (int i = 0; i < lengthList - 1; i++) {
            listplayer1[i] = new Player();
            listplayer1[i].setName(listplayer[i].getName());
            listplayer1[i].setScore(listplayer[i].getScore());
            
        }
    }
    
    public void getBackup() {
        System.out.println("____________________________________________26");
        
        for (int i = 0; i < lengthList - 1; i++) {
            listplayer[i].setName(listplayer1[i].getName());
            listplayer[i].setScore(listplayer1[i].getScore());
            if (i == tempIndexPlayer) {
                listLabel[i].setBorder(redline);
            } else {
                listLabel[i].setBorder(blackline);
            }
        }
        SetLabel();
    }
    
    public void writeFile(Player[] listRanking) {
        System.out.println("____________________________________________27");
        
        try {
            FileOutputStream f = new FileOutputStream(new File("rank\\\\rank.txt"));
            ObjectOutputStream o = new ObjectOutputStream(f);
            //write intro file

            o.writeObject(listRanking);
            o.close();
            f.close();
        } catch (FileNotFoundException e) {
            
        } catch (IOException ex) {
            
        }
    }
    
    public Player[] readFile(Player[] list) {
        System.out.println("___________________________________________28");
        
        try {
            FileInputStream fi = new FileInputStream(new File("rank\\\\rank.txt"));
            ObjectInputStream oi = new ObjectInputStream(fi);
            list = (Player[]) oi.readObject();
            // Read objects
            oi.close();
            fi.close();
            
        } catch (ClassNotFoundException ex) {
        } catch (IOException e) {
            
        }
        return list;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton2 = new javax.swing.JButton();
        jPopupMenu1 = new javax.swing.JPopupMenu();
        chess = new javax.swing.JPanel();
        jProgressBar1 = new JProgressBar(JProgressBar.VERTICAL,0, maxTime);
        jLabel2 = new javax.swing.JLabel();
        rank = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        labelScore = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        labelBonus = new javax.swing.JLabel();

        jButton2.setText("jButton2");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Game Lat Hinh");
        setBackground(new java.awt.Color(51, 255, 255));
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setLocation(new java.awt.Point(0, 0));
        setLocationByPlatform(true);
        setName("BackGround"); // NOI18N
        setUndecorated(true);
        setSize(new java.awt.Dimension(1920, 1080));

        chess.setBackground(new java.awt.Color(253, 152, 84));
        chess.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        chess.setAlignmentX(100.0F);
        chess.setAlignmentY(100.0F);
        chess.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        chess.setDebugGraphicsOptions(javax.swing.DebugGraphics.NONE_OPTION);
        chess.setPreferredSize(new java.awt.Dimension(900, 900));

        javax.swing.GroupLayout chessLayout = new javax.swing.GroupLayout(chess);
        chess.setLayout(chessLayout);
        chessLayout.setHorizontalGroup(
            chessLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 894, Short.MAX_VALUE)
        );
        chessLayout.setVerticalGroup(
            chessLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 892, Short.MAX_VALUE)
        );

        jProgressBar1.setAlignmentX(1060.0F);
        jProgressBar1.setAlignmentY(100.0F);
        jProgressBar1.setPreferredSize(new java.awt.Dimension(40, 900));
        jProgressBar1.setRequestFocusEnabled(false);
        jProgressBar1.setLocation(1060, 100);

        Image image = new ImageIcon(getClass().getResource("/Game/icon/pause.png")).getImage();
        Icon icon = new ImageIcon(image.getScaledInstance(50, 50, Image.SCALE_SMOOTH));
        jLabel2.setIcon(icon);
        jLabel2.setPreferredSize(new java.awt.Dimension(100, 100));
        jLabel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel2MouseClicked(evt);
            }
        });

        rank.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder(null, "Rank", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial Black", 1, 18)))); // NOI18N
        rank.setAlignmentX(1200.0F);
        rank.setAlignmentY(100.0F);
        rank.setPreferredSize(new java.awt.Dimension(600, 670));
        rank.setLocation(1200, 100);

        javax.swing.GroupLayout rankLayout = new javax.swing.GroupLayout(rank);
        rank.setLayout(rankLayout);
        rankLayout.setHorizontalGroup(
            rankLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 588, Short.MAX_VALUE)
        );
        rankLayout.setVerticalGroup(
            rankLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 635, Short.MAX_VALUE)
        );

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Score", javax.swing.border.TitledBorder.LEADING, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial Black", 1, 18))); // NOI18N
        jPanel1.setAlignmentX(1200.0F);
        jPanel1.setAlignmentY(800.0F);
        jPanel1.setPreferredSize(new java.awt.Dimension(600, 200));
        jPanel1.setRequestFocusEnabled(false);
        jPanel1.setLocation(1200, 800);

        labelScore.setFont(new java.awt.Font("Arial Black", 1, 48)); // NOI18N
        labelScore.setText("0");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(labelScore, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(labelScore)
                .addContainerGap(57, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Bonus", javax.swing.border.TitledBorder.LEADING, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial Black", 1, 18))); // NOI18N
        jPanel2.setAlignmentX(1200.0F);
        jPanel2.setAlignmentY(800.0F);
        jPanel2.setPreferredSize(new java.awt.Dimension(600, 200));
        jPanel2.setRequestFocusEnabled(false);
        jPanel1.setLocation(1200, 800);

        labelBonus.setFont(new java.awt.Font("Arial Black", 1, 48)); // NOI18N
        labelBonus.setText("0");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelBonus, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(labelBonus)
                .addContainerGap(57, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(100, 100, 100)
                .addComponent(chess, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(60, 60, 60)
                .addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(100, 100, 100)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(rank, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 294, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(19, 19, 19)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                .addContainerGap(120, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rank, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 23, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(80, 80, 80))
            .addGroup(layout.createSequentialGroup()
                .addGap(100, 100, 100)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 900, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(chess, 898, 898, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        setSize(new java.awt.Dimension(1920, 1080));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseClicked
        // TODO add your handling code here:
        System.out.println("____________________________________________29");
        
        timer.stop();
        glassPane.setVisible(true);
    }//GEN-LAST:event_jLabel2MouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel chess;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JLabel labelBonus;
    private javax.swing.JLabel labelScore;
    private javax.swing.JPanel rank;
    // End of variables declaration//GEN-END:variables

}

class JButtonInt extends JButton {
    
    int value;
    boolean isOpen = false;
    
    public JButtonInt() {
        super();
    }

    //hiển thị hình tại tại giá trị value
    public void showIcon() {
        isOpen = true;
        int width = 120, height = 127;
        Image image = new ImageIcon(getClass().getResource("/Game/icon/icon(" + this.value + ").png")).getImage();
        Icon icon = new ImageIcon(image.getScaledInstance(width, height, Image.SCALE_SMOOTH));
        this.setIcon(icon);
    }

    //ẩn hình ảnh bằng hình khác
    public void hideIcon() {
        isOpen = false;
        int width = 120, height = 127;
        Image image = new ImageIcon(getClass().getResource("/Game/icon/icon(0).png")).getImage();
        Icon icon = new ImageIcon(image.getScaledInstance(width, height, Image.SCALE_SMOOTH));
        this.setIcon(icon);
    }
    
    void setIconBt(String iconName) {
        int width = 120, height = 127;
        Image image = new ImageIcon(getClass().getResource("/Game/icon/" + iconName + ".png")).getImage();
        Icon icon = new ImageIcon(image.getScaledInstance(width, height, Image.SCALE_SMOOTH));
        this.setIcon(icon);
    }
}

class BackingPane extends JPanel {
    
    public BackingPane() {
        setLayout(new GridBagLayout());
        setOpaque(false);
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(new Color(128, 128, 128, 192));
        g.fillRect(0, 0, getWidth(), getHeight());
    }
    
}
