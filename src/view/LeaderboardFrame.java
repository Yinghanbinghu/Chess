package view;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Map;

public class LeaderboardFrame extends JFrame {
    private List<Map.Entry<String, Integer>> list;
    private String style;
    public LeaderboardFrame(List<Map.Entry<String, Integer>> list,String style) {
        this.style=style;
        this.list = list;
        this.setTitle("国际象棋");
        setLocation(500,0);
        JPanel j=(JPanel)this.getContentPane();
        j.setOpaque(false);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setLayout(null);
        setSize(461,799);
        for(int i=0;i<list.size();i++) {
            JLabel label1=new JLabel(list.get(i).getKey());
            label1.setLocation(30,i*20);
            label1.setSize(100,20);
            label1.setFont(new Font("方正舒体",Font.BOLD, 20));
            add(label1);
            JLabel label2=new JLabel(String.valueOf(list.get(i).getValue()));
            label2.setLocation(200,i*20);
            label2.setSize(100,20);
            label2.setFont(new Font("方正舒体",Font.BOLD, 20));
            add(label2);
            System.out.println(list.get(i).getKey() + " = " + list.get(i).getValue());
        }
        addBackGround();
    }

    private void addBackGround(){
        ImageIcon icon=new ImageIcon();
        switch (style){
            case "四月":icon=new ImageIcon("./images/siyueLeader.png");
            break;
            case "碧蓝":icon=new ImageIcon("./images/bilanLeader.png");
            break;
        }
        this.setSize(icon.getIconWidth(), icon.getIconHeight());
        //Image im=new Image(icon);
        //将图片放入label中
        JLabel label=new JLabel(icon);
        label.setBounds(0,0,icon.getIconWidth(),icon.getIconHeight());
//        getLayeredPane().add(label,-100);
        add(label);
    }
}
