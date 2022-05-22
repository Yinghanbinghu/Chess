package view;

import javax.swing.*;
import java.awt.*;
public class SetNameFrame extends JFrame {
    private int HEIGTH;
    private int WIDTH;
    private boolean Ifweb=false;
    private String WPlayer;
    private String BPlayer;
    private JTextField BlackP=new JTextField(10);
    private JTextField WhiteP=new JTextField(10);
    private MainFrame mainframe=null;
    private JTextField WebPlayer=new JTextField(10);
    private String style;
    public SetNameFrame(boolean b,MainFrame mainframe,String style) {
        this.style=style;
        this.mainframe= mainframe;
        mainframe.setVisible(false);
        Ifweb=b;
        this.HEIGTH = 275;
        this.WIDTH = 390;
        setSize(WIDTH, HEIGTH);
        setLocationRelativeTo(null); // Center the window.
        JPanel j = (JPanel) this.getContentPane();
        j.setOpaque(false);
        setDefaultCloseOperation(0); //设置程序关闭按键，如果点击右上方的叉就游戏全部关闭了
        setLayout(null);

        BlackP.setLocation(150,80);
        BlackP.setSize(150,25);
        BlackP.setFont(new Font("方正舒体", Font.BOLD, 20));
        BlackP.setBackground(new Color(204, 204, 255));
        WhiteP.setLocation(150,20);
        WhiteP.setSize(150,25);
        WhiteP.setFont(new Font("方正舒体", Font.BOLD, 20));
        WhiteP.setBackground(new Color(204, 204, 255));
        WebPlayer.setLocation(150,20);
        WebPlayer.setSize(150,25);
        WebPlayer.setFont(new Font("方正舒体", Font.BOLD, 20));
        WebPlayer.setBackground(new Color(204, 204, 255));
        if(!Ifweb) {
            add(WhiteP);
            add(BlackP);
            addWLable();
            addBLable();
        }else {
            add(WebPlayer);
            addWebLable();
        }
        /*addBlack();*/
        /*addWhite();*/
        addNewGameButton();
        addBackButton();
        addBackGround();
    }


    private void addBackGround() {
        ImageIcon icon = new ImageIcon("./images/α.png");
        //Image im=new Image(icon);
        //将图片放入label中
        JLabel label = new JLabel(icon);
        label.setBounds(0, 0, icon.getIconWidth(), icon.getIconHeight());
//        getLayeredPane().add(label,-100);
        add(label);
    }
    private void addBlack() {
        JTextField BlackP=new JTextField(10);
        BlackP.setLocation(150,80);
        BlackP.setSize(150,25);
        BlackP.setFont(new Font("方正舒体", Font.BOLD, 20));
        BlackP.setBackground(new Color(204, 204, 255));
        add(BlackP);
    }
    private void addWhite() {
        JTextField WhiteP=new JTextField(10);
        WhiteP.setLocation(150,20);
        WhiteP.setSize(150,25);
        WhiteP.setFont(new Font("方正舒体", Font.BOLD, 20));
        WhiteP.setBackground(new Color(204, 204, 255));
        add(WhiteP);
    }
    private void addWLable() {
        JLabel statusLabel = new JLabel("输入白方名称：");
        statusLabel.setLocation(0,0);
        statusLabel.setSize(200, 60);
        statusLabel.setFont(new Font("方正舒体", Font.BOLD, 20));
        add(statusLabel);
    }
    private void addWebLable() {
        JLabel statusLabel = new JLabel("输入玩家名称：");
        statusLabel.setLocation(0,0);
        statusLabel.setSize(200, 60);
        statusLabel.setFont(new Font("方正舒体", Font.BOLD, 20));
        add(statusLabel);
    }
    private void addBLable() {
        JLabel statusLabel = new JLabel("输入黑方名称：");
        statusLabel.setLocation(0,60);
        statusLabel.setSize(200, 60);
        statusLabel.setFont(new Font("方正舒体",Font.BOLD, 20));
        add(statusLabel);
    }
    private void addNewGameButton(){
        JButton button = new JButton("开始游戏");
        button.addActionListener((e) -> SwingUtilities.invokeLater(() -> {
            boolean IfSetName=true;
            if(!Ifweb){
            if (BlackP.getText().length()==0||WhiteP.getText().length()==0) {
                IfSetName=false;
                JOptionPane.showMessageDialog(null, "请输入用户名", "提示", JOptionPane.WARNING_MESSAGE);
            }
            }else
            if (WebPlayer.getText().length()==0) {
                IfSetName=false;
                JOptionPane.showMessageDialog(null, "请输入用户名", "提示", JOptionPane.WARNING_MESSAGE);
            }
            if(IfSetName){
                if(Ifweb){
                    mainframe.setPlayer(WebPlayer.getText());
                }else {
                    mainframe.setPlayer(BlackP.getText());
                    mainframe.setPlayer(WhiteP.getText());
                }
                if (Ifweb) {
                    WebChessGameFrame mainFrame = new WebChessGameFrame(1000, 760,this.style,mainframe);
                    this.setVisible(false);
                    mainFrame.setVisible(true);


                    /*try {
                        Thread.sleep(20);
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                    WebChessGameFrame mainFrame1 = new WebChessGameFrame(1000, 760,this.style,mainframe);
                    this.setVisible(false);
                    mainFrame1.setVisible(true);*/

                    //测试用的生成第二个网络棋盘

                } else {
                    ChessGameFrame chessGameFrameFrame = new ChessGameFrame(1000, 760,this.style,mainframe);
                    this.setVisible(false);
                    chessGameFrameFrame.setVisible(true);
                }
            }
        }));
        button.setLocation(50, 130);
        button.setSize(150, 30);
        button.setFont(new Font("方正舒体", Font.BOLD, 20));
        button.setBackground(new Color(204, 204, 255));
        add(button);
    }
    private void addBackButton(){
        JButton button = new JButton("返回");
        button.addActionListener((e) -> SwingUtilities.invokeLater(() -> {
            this.setVisible(false);
            this.mainframe.setVisible(true);
        }));
        button.setLocation(50, 180);
        button.setSize(150, 30);
        button.setFont(new Font("方正舒体", Font.BOLD, 20));
        button.setBackground(new Color(204, 204, 255));
        add(button);
    }
}
