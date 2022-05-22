package view;

import controller.WebGameController;
import controller.WebTimeController;

import javax.swing.*;
import java.awt.*;
import java.io.File;

/**
 * 这个类表示游戏过程中的整个游戏界面，是一切的载体
 */
public class WebChessGameFrame extends JFrame {
    //    public final Dimension FRAME_SIZE ;
    private final int WIDTH;
    private final int HEIGTH;
    public final int CHESSBOARD_SIZE;
    private WebGameController gameController;
    JButton connectButton;
    JLabel idLabel;
    JButton regretButton;
    JButton watchButton;
    JButton newGameButton;
    JButton quitButton;
    private WebChessboard webChessboard;
    private MainFrame mainFrame;
    private String style;
    private WebTimeController time2 =new WebTimeController(this);

    public WebChessboard getWebChessboard(){
        return webChessboard;
    }

    public WebChessGameFrame(int width, int height,String style,MainFrame mainFrame) {
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

        addConnectButton();
        addLabel();
        addRegretButton();
        addWatchButton();
        addNewGameButton();
        addQuitButton();
        addTimer();
        addChessboard();


        addHelpButton();
        addBackGround();
    }


    /**
     * 在游戏面板中添加棋盘
     */
    private void addChessboard() {
        webChessboard = new WebChessboard(CHESSBOARD_SIZE, CHESSBOARD_SIZE, connectButton, idLabel, regretButton, watchButton, quitButton,newGameButton,time2);
        gameController = new WebGameController(webChessboard);
        webChessboard.setLocation(HEIGTH / 10, HEIGTH / 10);
        add(webChessboard);
    }

    /**
     * 在游戏面板中添加标签
     */
    private void addLabel() {
        idLabel = new JLabel("Sample label");
        idLabel.setLocation(HEIGTH, HEIGTH / 10-60);
        idLabel.setSize(200, 60);
        idLabel.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(idLabel);
    }

    private void addTimer(){
        time2.setShow(true);
        JLabel label= time2.getShowTime();
        label.setLocation(HEIGTH, HEIGTH / 10);
        label.setSize(200, 60);
        add(label);
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
     * 在游戏面板中增加一个按钮，如果按下的话就会显示Hello, world!
     */

    private void addHelloButton() {
        JButton button = new JButton("Show Hello Here");
        button.setLocation(HEIGTH, HEIGTH / 10 + 120);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);
    }

    private void addRegretButton() {
        regretButton = new JButton("悔棋");
        regretButton.setLocation(HEIGTH, HEIGTH / 10 + 300);
        regretButton.setSize(200, 60);
        regretButton.setFont(new Font("方正舒体", Font.BOLD, 20));
        add(regretButton);

        regretButton.addActionListener(e -> {
            System.out.println("Click regret");
            //gameController.regret();
        });
    }

    private void addConnectButton() {
        connectButton = new JButton("连接");
        connectButton.setLocation(HEIGTH, HEIGTH / 10 + 120);
        connectButton.setSize(200, 60);
        connectButton.setFont(new Font("方正舒体", Font.BOLD, 20));
        add(connectButton);
    }

    private void addWatchButton() {
        watchButton = new JButton("观战");
        watchButton.setLocation(HEIGTH, HEIGTH / 10 + 360);
        watchButton.setSize(200, 60);
        watchButton.setFont(new Font("方正舒体", Font.BOLD, 20));
        add(watchButton);
    }

    private void addNewGameButton() {
        newGameButton = new JButton("和棋");
        newGameButton.setLocation(HEIGTH, HEIGTH / 10 + 180);
        newGameButton.setSize(200, 60);
        newGameButton.setFont(new Font("方正舒体", Font.BOLD, 20));
        add(newGameButton);
    }

    private void addLoadButton() {
        JButton button = new JButton("Load");
        button.setLocation(HEIGTH, HEIGTH / 10 + 180);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
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
        JButton button = new JButton("Save");
        button.setLocation(HEIGTH, HEIGTH / 10 + 360);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);

        button.addActionListener(e -> {
            System.out.println("Click save");
            gameController.saveGame();
        });
    }

    private void addQuitButton() {
        quitButton = new JButton("退出游戏");
        quitButton.setLocation(HEIGTH, HEIGTH / 10 + 420);
        quitButton.setSize(200, 60);
        quitButton.setFont(new Font("方正舒体", Font.BOLD, 20));
        add(quitButton);

        quitButton.addActionListener(e -> {
            System.out.println("Click quit");
            this.setVisible(false);
            mainFrame.setVisible(true);
            time2.setShow(false);
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


