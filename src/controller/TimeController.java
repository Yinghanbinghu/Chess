package controller;

import view.ChessGameFrame;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class TimeController extends JFrame implements ActionListener {

    //　一个显示时间的JLabel

    private JLabel showTime = new JLabel();
    private Timer timer;
    private int minute=1;
    private int second=0;
    private ChessGameFrame frame;
    private boolean show=false;

    public void setShow(boolean show){
        this.show=show;
    }

    public JLabel getShowTime(){
        return showTime;
    }

    public TimeController(ChessGameFrame chessGameFrame){
        this.frame =chessGameFrame;
        timer = new Timer(1000,this);
        timer.start();
    }

    public void restart(){
        minute=1;
        second=0;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (this.show) {
            showTime.setFont(new Font("方正舒体", Font.BOLD, 25));
            if (second != 0) {
                second = second - 1;
            } else {
                minute = minute - 1;
                second = 59;
            }
            if (second < 10) {
                showTime.setText(String.format("剩余：0%d:0%d", minute, second));
                System.out.printf("剩余：0%d:0%d%n", minute, second);
            } else {
                showTime.setText(String.format("剩余：0%d:%d", minute, second));
                System.out.printf("剩余：0%d:%d%n", minute, second);
            }
            if (minute < 1) {
                showTime.setForeground(Color.RED);
            }
            if (minute == 0 && second == 0) {
                minute = 1;
                frame.getChessboard().swapColor();
                JOptionPane.showMessageDialog(null, "超时！");
            }
        }
    }
}
/*import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Timer;
import java.util.TimerTask;

public class TimeController {

    private int minute;
    private int second;
    private static boolean isOpen = false;
    private static TimeController timer;

    static {
        timer = new TimeController(0, 5);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Countdown Timer");
        frame.setLayout(new GridLayout(2, 1));
        frame.setSize(500, 500);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1, 2));

        JLabel label = new JLabel(timer.toString());
        label.setFont(new Font("宋体", Font.BOLD, 100));

        JButton button = new JButton("开始/暂停");
        button.setBounds(0, 0, 500, 500);
        button.setFont(new Font("楷体", Font.BOLD, 50));
        button.addActionListener((ActionEvent e) -> {
            if (label.getText().equals("00:00")) {
                timer = new TimeController(0, 5);
                label.setText(timer.toString());
            }
            if (isOpen) {
                isOpen = false;
            } else {
                isOpen = true;
                countdown(label);
            }
        });

        JButton button1 = new JButton("重置");
        button1.setFont(new Font("楷体", Font.BOLD, 50));
        button1.addActionListener((ActionEvent e) -> {
            timer = new TimeController(0, 5);
            label.setText(timer.toString());
            isOpen = false;
        });

        panel.add(button);
        panel.add(button1);

        frame.add(label);
        frame.add(panel);
        frame.setVisible(true);
    }

    private static void countdown(JLabel label) {
        Timer timer1 = new Timer();
        timer1.schedule(new TimerTask() {
            @Override
            public void run() {
                if ((timer.minute == 0 && timer.second == 0) || !isOpen) {
//                    timer = new CountdownTimer(0, 5);
                    label.setText(timer.toString());
                    isOpen = false;
                    timer1.cancel();
                    return;
                }
                System.out.println(label.getText());
                timer.second--;
                if (timer.second == -1) {
                    timer.minute--;
                    timer.second = 59;
                }
                label.setText(timer.toString());
            }
        }, 1000, 1000);
    }

    public TimeController(int minute, int second) {
        this.minute = minute;
        this.second = second;
    }

    @Override
    public String toString() {
        return String.format("%02d:%02d", this.minute, this.second);
    }

}*/
