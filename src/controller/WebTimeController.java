package controller;

import view.WebChessGameFrame;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class WebTimeController extends JFrame implements ActionListener {

    //　一个显示时间的JLabel

    private JLabel showTime = new JLabel();
    private Timer timer;
    private int minute=1;
    private int second=0;
    private WebChessGameFrame frame;
    private boolean show=false;

    public void setShow(boolean show){
        this.show=show;
    }

    public JLabel getShowTime(){
        return showTime;
    }

    public WebTimeController(WebChessGameFrame webChessGameFrame){
        this.frame =webChessGameFrame;
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
                System.out.println(String.format("剩余：0%d:0%d", minute, second));
            } else {
                showTime.setText(String.format("剩余：0%d:%d", minute, second));
                System.out.println(String.format("剩余：0%d:%d", minute, second));
            }
            if (minute < 1) {
                showTime.setForeground(Color.RED);
            }
            if (minute == 0 && second == 0) {
                minute = 1;
                frame.getWebChessboard().swapColor();
                frame.getWebChessboard().getProxy().send(frame.getWebChessboard().opponentID+"#"+"s");
                JOptionPane.showMessageDialog(null, "超时！");
            }
        }
    }
}