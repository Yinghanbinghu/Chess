package view;

import controller.GameController;
import controller.TimeController;

import javax.swing.*;
import java.awt.*;
import java.io.File;

/**
 * 这个类表示游戏过程中的整个游戏界面，是一切的载体
 */
public class ChessGameFrame extends JFrame {
    //    public final Dimension FRAME_SIZE ;
    private final int WIDTH;
    private final int HEIGTH;
    public final int CHESSBOARD_SIZE;
    private GameController gameController;
    private String style;
    private MainFrame mainFrame;
    private Chessboard chessboard;
    private TimeController time1 =new TimeController(this);
    JButton playBackButton;

    public Chessboard getChessboard() {
        return chessboard;
    }

    public ChessGameFrame(int width, int height, String style, MainFrame mainFrame) {
        this.mainFrame=mainFrame;
        this.style=style;
        setTitle("2022 CS102A Project Demo"); //设置标题
        this.WIDTH = width;
        this.HEIGTH = height;
        this.CHESSBOARD_SIZE = HEIGTH * 4 / 5;

        setSize(WIDTH, HEIGTH);
        setLocationRelativeTo(null); // Center the window.
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //设置程序关闭按键，如果点击右上方的叉就游戏全部关闭了
        setLayout(null);


        addPlaybackButton();
        addChessboard();
        addTimer();
        addLabel();
        addHelloButton();
        addLoadButton();
        addNewGameButton();
        addHelpButton();
        addSaveButton();
        addRegretButton();
        addQuitButton();
        addBackGround();
    }


    /**
     * 在游戏面板中添加棋盘
     */
    private void addChessboard() {
        chessboard = new Chessboard(CHESSBOARD_SIZE, CHESSBOARD_SIZE, time1, playBackButton);
        gameController = new GameController(chessboard);
        chessboard.setLocation(HEIGTH / 10, HEIGTH / 10);
        add(chessboard);
    }

    private void addBackGround(){
        Icon backgroundIcon=new ImageIcon();
        switch (style){
            case "四月":backgroundIcon=new ImageIcon("./images/siyuechessboard.png");
                break;
            case "碧蓝":backgroundIcon=new ImageIcon("./images/bilanchessboard.png");
                break;
        }
        //Image im=new Image(icon);
        //将图片放入label中
        JLabel backgroundLabel=new JLabel(backgroundIcon);
        backgroundLabel.setBounds(0,0,backgroundIcon.getIconWidth(),backgroundIcon.getIconHeight());
//        getLayeredPane().add(label,-100);
        add(backgroundLabel);
    }

    /**
     * 在游戏面板中添加标签
     */
    private void addLabel() {
        JLabel statusLabel = new JLabel("2022 CS102A Project");
        statusLabel.setLocation(HEIGTH, HEIGTH / 10-60);
        statusLabel.setSize(200, 60);
        statusLabel.setFont(new Font("Rockwell", Font.BOLD, 15));
        add(statusLabel);
    }

    private void addTimer(){
        time1.setShow(true);
        JLabel label= time1.getShowTime();
        label.setLocation(HEIGTH, HEIGTH / 10);
        label.setSize(200, 60);
        add(label);
    }



    /**
     * 在游戏面板中增加一个按钮，如果按下的话就会显示Hello, world!
     */

    private void addHelloButton() {
        JButton button = new JButton("你好！");
        button.addActionListener((e) -> JOptionPane.showMessageDialog(this, "Hello, world!"));
        button.setLocation(HEIGTH, HEIGTH / 10 + 60);
        button.setSize(200, 60);
        button.setFont(new Font("方正舒体", Font.BOLD, 20));
        add(button);
    }

    private void addNewGameButton() {
        JButton button = new JButton("新游戏");
        button.addActionListener((e) -> gameController.newGame());
        button.setLocation(HEIGTH, HEIGTH / 10 + 120);
        button.setSize(200, 60);
        button.setFont(new Font("方正舒体", Font.BOLD, 20));
        add(button);
    }

    private void addLoadButton() {
        JButton button = new JButton("载入");
        button.setLocation(HEIGTH, HEIGTH / 10 + 180);
        button.setSize(200, 60);
        button.setFont(new Font("方正舒体", Font.BOLD, 20));
        add(button);

        button.addActionListener(e -> {
            System.out.println("Click load");
            JFileChooser jfc=new JFileChooser(new File("./resource"));
            jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
            jfc.showDialog(new JLabel(),"确认");
            File file;
            try {
                file=jfc.getSelectedFile();
                if(file.getName().endsWith(".txt")) {
                    if (file!=null) gameController.loadGameFromFile(file);
                }else {
                    JOptionPane.showMessageDialog(null,"104","文件格式错误",JOptionPane.ERROR_MESSAGE);
                }
            }catch (Exception ignored){}
        });
    }
    private void addHelpButton(){
        JButton button = new JButton("帮助模式：开");

        button.addActionListener((e) ->{
            gameController.setHelpModel();
            if(gameController.getHelpModel()){button.setText("帮助模式：开");
            }else button.setText("帮助模式：关");
        });

        button.setLocation(HEIGTH, HEIGTH / 10 + 240);
        button.setSize(200, 60);
        button.setFont(new Font("方正舒体", Font.BOLD, 20));
        add(button);
    }
    private void addSaveButton() {
        JButton button = new JButton("保存");
        button.setLocation(HEIGTH, HEIGTH / 10 + 360);
        button.setSize(200, 60);
        button.setFont(new Font("方正舒体", Font.BOLD, 20));
        add(button);

        button.addActionListener(e -> {
            System.out.println("Click save");
            gameController.saveGame();
        });
    }
    private void addRegretButton() {
        JButton button = new JButton("悔棋");
        button.setLocation(HEIGTH, HEIGTH / 10 + 300);
        button.setSize(200, 60);
        button.setFont(new Font("方正舒体", Font.BOLD, 20));
        add(button);

        button.addActionListener(e -> {
            System.out.println("Click regret");
            gameController.regret();
        });
    }
    private void addPlaybackButton() {
        playBackButton = new JButton("回放");
        playBackButton.setLocation(HEIGTH, HEIGTH / 10 + 420);
        playBackButton.setSize(200, 60);
        playBackButton.setFont(new Font("方正舒体", Font.BOLD, 20));
        add(playBackButton);

        playBackButton.addActionListener(e -> {
            System.out.println("Click playback");
        });
    }
    private void addQuitButton() {
        JButton button = new JButton("退出");
        button.setLocation(HEIGTH, HEIGTH / 10 + 480);
        button.setSize(200, 60);
        button.setFont(new Font("方正舒体", Font.BOLD, 20));
        add(button);

        button.addActionListener(e -> {
            System.out.println("Click quit");
            this.setVisible(false);
            mainFrame.setVisible(true);
            time1.setShow(false);
        });
    }
}


/*class PawnUpGrate extends JFrame{
    private final int WIDTH;
    private final int HEIGTH;

    PawnUpGrate(int width, int heigth) {
        WIDTH = width;
        HEIGTH = heigth;
        setSize(WIDTH, HEIGTH);
    }*/


