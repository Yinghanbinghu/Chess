package view;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private final int WIDTH;
    private final int HEIGTH;
    private String playerName;

    public MainFrame(int WIDTH, int HEIGTH){
        this.WIDTH = WIDTH;
        this.HEIGTH = HEIGTH;
        setSize(WIDTH, HEIGTH);
        setLocationRelativeTo(null); // Center the window.
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //设置程序关闭按键，如果点击右上方的叉就游戏全部关闭了
        setLayout(null);
        addNewGameButton();
        addPlayerButton();
    }
    private void addNewGameButton(){
        JButton button = new JButton("新游戏");
        button.addActionListener((e) -> SwingUtilities.invokeLater(() -> {
            ChessGameFrame mainFrame = new ChessGameFrame(1000, 760);
            this.setVisible(false);
            mainFrame.setVisible(true);
        }));
        button.setLocation(80, 80);
        button.setSize(200, 60);
        button.setFont(new Font("行楷", Font.BOLD, 20));
        add(button);
    }
    private void addPlayerButton(){
        JButton button = new JButton("玩家名");
        button.addActionListener((e) -> SwingUtilities.invokeLater(() -> {
        }));
        button.setLocation(80, 140);
        button.setSize(200, 60);
        button.setFont(new Font("行楷", Font.BOLD, 20));
        add(button);
    }

public static void main(String[] args) {
        MainFrame frame=new MainFrame(600,400);
        frame.setVisible(true);
    }

}
