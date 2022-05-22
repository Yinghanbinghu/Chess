package view;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;

public class MainFrame extends JFrame {
    private final int WIDTH;
    private final int HEIGTH;
    private Map<String,Integer> Leaderboard=new HashMap<>();
    private String style="碧蓝";
    private ImageIcon backgroundIcon=new ImageIcon("./images/bilanmain.png");
    private JLabel backgroundLabel=new JLabel(backgroundIcon);

    public MainFrame(int WIDTH, int HEIGTH){
        this.WIDTH = WIDTH;
        this.HEIGTH = HEIGTH;
        this.setTitle("国际象棋");
        /*try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("./resource/Leaderboard.txt"));
             ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("./resource/Leaderboard.txt"))) {

            System.out.println(Leaderboard);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "文件有改动", "提示", JOptionPane.WARNING_MESSAGE);
        }*/
        /*Leaderboard.put("dyh",1);*/
        try {
            /*ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("./resource/Leaderboard.txt"));
            oos.writeObject(Leaderboard);*/
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream("./resource/Leaderboard.txt"));
            Leaderboard = (Map<String, Integer>) ois.readObject();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "文件有改动", "提示", JOptionPane.WARNING_MESSAGE);
        }
        System.out.println(Leaderboard);
        setSize(WIDTH, HEIGTH);
        setLocationRelativeTo(null); // Center the window.
        JPanel j=(JPanel)this.getContentPane();
        j.setOpaque(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //设置程序关闭按键，如果点击右上方的叉就游戏全部关闭了
        setLayout(null);
        addNewWebGameButton();
        /*addPlayerButton();*/
        addNewGameButton();
        addLeaderboardButton();
        addChangButton();
        addBackGround();
    }
    private void addBackGround(){
        switch (style){
            case "四月":backgroundIcon=new ImageIcon("./images/siyuemain.png");
            break;
            case "碧蓝":backgroundIcon=new ImageIcon("./images/bilanmain.png");
            break;
        }
        //Image im=new Image(icon);
        //将图片放入label中
        backgroundLabel.setBounds(0,0,backgroundIcon.getIconWidth(),backgroundIcon.getIconHeight());
        this.setSize(backgroundIcon.getIconWidth(), backgroundIcon.getIconHeight());
//        getLayeredPane().add(label,-100);
        add(backgroundLabel);
    }
    public void changBackground(){
        switch (style){
            case "四月":backgroundIcon=new ImageIcon("./images/siyuemain.png");
            break;
            case "碧蓝":backgroundIcon=new ImageIcon("./images/bilanmain.png");
            break;
        }
        backgroundLabel.setIcon(backgroundIcon);
        backgroundLabel.setBounds(0,0,backgroundIcon.getIconWidth(),backgroundIcon.getIconHeight());
        this.setSize(backgroundLabel.getWidth(),backgroundLabel.getHeight());
        add(backgroundLabel);
        repaint();
//        getLayeredPane().add(label,-100);
    }
    private void addNewWebGameButton(){
        JButton button = new JButton("网络游戏");
        button.setBackground(new Color(0, 51, 204));
        button.addActionListener((e) -> SwingUtilities.invokeLater(() -> {
            button.setBackground(new Color(102, 0, 255));
            SetNameFrame begin=new SetNameFrame(true,this,this.style);
            begin.setVisible(true);
            /*WebChessGameFrame mainFrame = new WebChessGameFrame(1000, 760);
            this.setVisible(false);
            mainFrame.setVisible(true);*/
        }));
        button.setLocation(80, 80);
        button.setSize(200, 60);
        button.setFont(new Font("方正舒体", Font.BOLD, 20));
        add(button);
    }
    private void addNewGameButton(){
        JButton button = new JButton("线下游戏");
        button.setBackground(new Color(0, 51, 204));
        button.addActionListener((e) -> SwingUtilities.invokeLater(() -> {
            button.setBackground(new Color(102, 0, 255));
            SetNameFrame begin=new SetNameFrame(false,this,this.style);
            begin.setVisible(true);
            /*ChessGameFrame chessGameFrameFrame = new ChessGameFrame(1000, 760);
            this.setVisible(false);
            chessGameFrameFrame.setVisible(true);*/
        }));
        button.setLocation(80, 180);
        button.setSize(200, 60);
        button.setFont(new Font("方正舒体", Font.BOLD, 20));
        add(button);
    }
    private void addChangButton(){
        JButton button = new JButton("切换风格");
        button.setBackground(new Color(0, 51, 204));
        button.addActionListener((e) -> SwingUtilities.invokeLater(() -> {
            Object[] obj2 = {"四月是你的谎言", "碧蓝航线",};
            String s = (String) JOptionPane.showInputDialog(null, "请选择类型:\n", "风格", JOptionPane.WARNING_MESSAGE, new ImageIcon("icon.png"), obj2, "四月是你的谎言");
            if(s.equals("四月是你的谎言")){
                this.style="四月";
            }else if(s.equals("碧蓝航线")){
                this.style="碧蓝";
            }
            this.changBackground();
        }));
        button.setLocation(380, 80);
        button.setSize(200, 60);
        button.setFont(new Font("方正舒体", Font.BOLD, 20));
        add(button);
    }
    private void addNewAIGameButton(){
        JButton button = new JButton("人机对战");
        button.addActionListener((e) -> SwingUtilities.invokeLater(() -> {
            ChessGameFrame chessGameFrameFrame = new ChessGameFrame(1000, 760,this.style,this);
            this.setVisible(false);
            chessGameFrameFrame.setVisible(true);
        }));
        button.setLocation(80, 200);
        button.setSize(200, 60);
        button.setFont(new Font("方正舒体", Font.BOLD, 20));
        add(button);
    }
    public void setPlayer(String playerName){
        if(!Leaderboard.containsKey(playerName)){
            Leaderboard.put(playerName,0);
        }
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("./resource/Leaderboard.txt"));
        ) {
            oos.writeObject(Leaderboard);
            System.out.println(Leaderboard);
        } catch (Exception ignored) {
        }
    }
    private void addLeaderboardButton(){
        JButton button = new JButton("查看排行榜");
        button.setBackground(new Color(0, 51, 204));
        button.addActionListener((e) -> SwingUtilities.invokeLater(() -> {
            Map<String, Integer> maps = new TreeMap<String, Integer>();
            maps.putAll(Leaderboard);
            //自定义比较器
            Comparator<Map.Entry<String, Integer>> valCmp = new Comparator<Map.Entry<String,Integer>>() {
                @Override
                public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                    return o2.getValue()-o1.getValue();  // 降序排序，如果想升序就反过来
                }
            };
            //将map转成List，map的一组key，value对应list一个存储空间
            List<Map.Entry<String, Integer>> list = new ArrayList<Map.Entry<String,Integer>>(maps.entrySet()); //传入maps实体
            Collections.sort(list,valCmp); // 注意此处Collections 是java.util包下面的,传入List和自定义的valCmp比较器
            //输出map
            LeaderboardFrame leaderboardFrame=new LeaderboardFrame(list,style);
            leaderboardFrame.setVisible(true);
        }));
        button.setLocation(80, 280);
        button.setSize(200, 60);
        button.setFont(new Font("方正舒体", Font.BOLD, 20));
        add(button);
    }

}
