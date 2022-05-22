package controller;


import model.ChessColor;
import model.WebChessComponent;
import model.WebKingChessComponent;
import view.WebChessboard;

import javax.swing.*;
import java.util.Random;

public class WebClickController {
    private final WebChessboard chessboard;
    private WebChessComponent first;
    private boolean UpGratePawn;
    private String information;

    public WebChessComponent getFirst() {
        return first;
    }

    public void setUpGratePawn(int upGratePawn) {
        UpGratePawn = true;
    }

    public WebClickController(WebChessboard chessboard) {
        this.chessboard = chessboard;
    }

    public void intiFirst() {
        this.first = null;
    }

    public void onClick(WebChessComponent chessComponent){
        if (first == null) {
            if (chessboard.getCurrentColor()==chessboard.myColor) {
                if (chessComponent.getChessColor() == chessboard.myColor) {
                    int x = chessComponent.getChessboardPoint().getX();
                    int y = chessComponent.getChessboardPoint().getY();
                    chessboard.getProxy().send(chessboard.opponentID + "#" + 'f' + String.format("%d%d", x, y));
                    try {
                        Thread.sleep(20);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    for (int i=0;i<chessboard.getWatchers().size();i++){
                        chessboard.getProxy().send(chessboard.getWatchers().get(i) + "#" + 'f' + String.format("%d%d", x, y));
                        try {
                            Thread.sleep(20);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
                if (handleFirst(chessComponent)) {
                    chessComponent.setSelected(true);
                    first = chessComponent;
                    if (chessboard.isIfHelp()) {
                        chessboard.helpCanMoveTo(first);
                    }
                    first.repaint();
                }
            }
        } else {
            if (first == chessComponent) { // 再次点击取消选取
            if (chessComponent.getChessColor()==chessboard.myColor) {
                int x = chessComponent.getChessboardPoint().getX();
                int y = chessComponent.getChessboardPoint().getY();
                chessboard.getProxy().send(chessboard.opponentID + "#" + 'f' + String.format("%d%d", x, y));
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                for (int i=0;i<chessboard.getWatchers().size();i++){
                    chessboard.getProxy().send(chessboard.getWatchers().get(i) + "#" + 'f' + String.format("%d%d", x, y));
                    try {
                        Thread.sleep(20);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
                chessComponent.setSelected(false);
                WebChessComponent recordFirst = first;
                first = null;
                chessboard.helpCanMoveTo(null);
                recordFirst.repaint();
            } else if (handleSecond(chessComponent)) {
                int x=chessComponent.getChessboardPoint().getX();
                int y=chessComponent.getChessboardPoint().getY();
                chessboard.getProxy().send(chessboard.opponentID+"#"+'f'+String.format("%d%d",x,y));
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                for (int i=0;i<chessboard.getWatchers().size();i++){
                    chessboard.getProxy().send(chessboard.getWatchers().get(i) + "#" + 'f' + String.format("%d%d", x, y));
                    try {
                        Thread.sleep(20);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if(chessComponent instanceof WebKingChessComponent){
                    chessboard.setOpponentReady(false);
                    chessboard.getTime().setShow(false);
                    int n;
                    if(chessboard.getCurrentColor()==ChessColor.WHITE){
                        for (int i=0;i<chessboard.getWatchers().size();i++){
                            chessboard.getProxy().send(chessboard.getWatchers().get(i) + "#" +"t0");
                            try {
                                Thread.sleep(20);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        n = JOptionPane.showConfirmDialog(null, "是否开始新游戏?", "白方获胜", JOptionPane.YES_NO_OPTION);
                    }else {
                        for (int i=0;i<chessboard.getWatchers().size();i++){
                            chessboard.getProxy().send(chessboard.getWatchers().get(i) + "#" +"t1");
                            try {
                                Thread.sleep(20);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        n = JOptionPane.showConfirmDialog(null, "是否开始新游戏?", "黑方获胜", JOptionPane.YES_NO_OPTION);
                    }
                    if (n==1){
                        chessboard.getProxy().send(chessboard.opponentID+"#"+"e");
                        for (int i=0;i<chessboard.getWatchers().size();i++){
                            chessboard.getProxy().send(chessboard.getWatchers().get(i) + "#" +"v");
                            try {
                                Thread.sleep(20);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        return;
                    }
                    if (n!=1) {
                        chessboard.initChessboard();
                        if (chessboard.isOpponentReady()){
                            Random random=new Random();
                            int color1=random.nextInt(2);
                            chessboard.getProxy().send(chessboard.opponentID +"#" + "b" + chessboard.myID + String.format("%d",color1));
                            if (color1==0){
                                chessboard.myColor=ChessColor.WHITE;chessboard.opponentColor=ChessColor.BLACK;
                                JOptionPane.showMessageDialog(null, "继续游戏，你随机到的颜色是白");
                            } else {
                                chessboard.myColor=ChessColor.BLACK;chessboard.opponentColor=ChessColor.WHITE;
                                JOptionPane.showMessageDialog(null, "继续游戏，你随机到的颜色是黑");
                            }
                            chessboard.getTime().setShow(true);
                            return;
                        }
                        chessboard.getProxy().send(chessboard.opponentID+"#"+"u");
                    }
                }
                //repaint in swap chess method.
                chessboard.swapChessComponents(first, chessComponent);
                chessboard.swapColor();
                chessboard.stepCount(true);
                chessboard.helpCanMoveTo(null);
                if(UpGratePawn){
                    chessboard.upGratePawn(first,'x');
                    UpGratePawn=false;
                }

                if(!chessboard.canMove()){ //无子可动和棋
                    chessboard.setOpponentReady(false);
                    chessboard.getTime().setShow(false);
                    int n;
                        for (int i=0;i<chessboard.getWatchers().size();i++){
                            chessboard.getProxy().send(chessboard.getWatchers().get(i) + "#" +"y");
                            try {
                                Thread.sleep(20);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        n = JOptionPane.showConfirmDialog(null, "是否开始新游戏?", "无子可动和棋", JOptionPane.YES_NO_OPTION);
                    if (n==1){
                        chessboard.getProxy().send(chessboard.opponentID+"#"+"e");
                        for (int i=0;i<chessboard.getWatchers().size();i++){
                            chessboard.getProxy().send(chessboard.getWatchers().get(i) + "#" +"v");
                            try {
                                Thread.sleep(20);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        return;
                    }
                    if (n!=1) {
                        chessboard.initChessboard();
                        if (chessboard.isOpponentReady()){
                            Random random=new Random();
                            int color1=random.nextInt(2);
                            chessboard.getProxy().send(chessboard.opponentID +"#" + "b" + chessboard.myID + String.format("%d",color1));
                            if (color1==0){
                                chessboard.myColor=ChessColor.WHITE;chessboard.opponentColor=ChessColor.BLACK;
                                JOptionPane.showMessageDialog(null, "继续游戏，你随机到的颜色是白");
                            } else {
                                chessboard.myColor=ChessColor.BLACK;chessboard.opponentColor=ChessColor.WHITE;
                                JOptionPane.showMessageDialog(null, "继续游戏，你随机到的颜色是黑");
                            }
                            chessboard.getTime().setShow(true);
                            return;
                        }
                        chessboard.getProxy().send(chessboard.opponentID+"#"+"u");
                    }
                }
                chessboard.saveStep();
                first.setSelected(false);
                first = null;
                if(chessboard.isIfHelp()&&chessboard.ifKingCanBeEat()){
                    if(chessboard.getCurrentColor()== ChessColor.WHITE)
                    JOptionPane.showMessageDialog(null, "黑方将军", "提示",JOptionPane.WARNING_MESSAGE);
                    if(chessboard.getCurrentColor()== ChessColor.BLACK)
                        JOptionPane.showMessageDialog(null, "白方将军", "提示",JOptionPane.WARNING_MESSAGE);
                }
            }
        }
    }

    public void webOnClick(WebChessComponent chessComponent) {
        if (first == null) {
            if (handleFirst(chessComponent)) {
                chessComponent.setSelected(true);
                first = chessComponent;
                if (chessboard.isIfHelp()){
                    chessboard.helpCanMoveTo(first);
                }
                first.repaint();
            }
        } else {
            if (first == chessComponent) { // 再次点击取消选取
                chessComponent.setSelected(false);
                WebChessComponent recordFirst = first;
                first = null;
                chessboard.helpCanMoveTo(null);
                recordFirst.repaint();
            } else if (handleSecond(chessComponent)) {
                if(chessComponent instanceof WebKingChessComponent){
                    int n;
                    if(chessboard.getCurrentColor()==ChessColor.WHITE){
                        n = JOptionPane.showConfirmDialog(null, "是否开始新游戏?", "白方获胜", JOptionPane.YES_NO_OPTION);
                    }else {
                        n = JOptionPane.showConfirmDialog(null, "是否开始新游戏?", "黑方获胜", JOptionPane.YES_NO_OPTION);
                    }
                    if(n==1) {
                        chessboard.initChessboard();
                    }
                }
                //repaint in swap chess method.
                chessboard.swapChessComponents(first, chessComponent);
                chessboard.swapColor();
                chessboard.stepCount(true);
                chessboard.helpCanMoveTo(null);
                if(UpGratePawn){
                    UpGratePawn=false;
                }
                if(!chessboard.canMove()){
                    int n = JOptionPane.showConfirmDialog(null, "是否开始新游戏?", "无子可动和棋",JOptionPane.YES_NO_OPTION);
                    if(n==1){
                        chessboard.initChessboard();
                    }
                }
                chessboard.saveStep();
                first.setSelected(false);
                first = null;
                if(chessboard.isIfHelp()&&chessboard.ifKingCanBeEat()){
                    if(chessboard.getCurrentColor()== ChessColor.WHITE)
                        JOptionPane.showMessageDialog(null, "黑方将军", "提示",JOptionPane.WARNING_MESSAGE);
                    if(chessboard.getCurrentColor()== ChessColor.BLACK)
                        JOptionPane.showMessageDialog(null, "白方将军", "提示",JOptionPane.WARNING_MESSAGE);
                }
            }
        }
    }

    public void removePassPawn(WebChessComponent passPawn){
        chessboard.removePassPawn(passPawn);
    }



    /**
     * @param chessComponent 目标选取的棋子
     * @return 目标选取的棋子是否与棋盘记录的当前行棋方颜色相同
     */

    private boolean handleFirst(WebChessComponent chessComponent) {
        return chessComponent.getChessColor() == chessboard.getCurrentColor();
    }


    /**
     * @param chessComponent first棋子目标移动到的棋子second
     * @return first棋子是否能够移动到second棋子位置
     */

    private boolean handleSecond(WebChessComponent chessComponent) {
        return chessComponent.getChessColor() != chessboard.getCurrentColor() &&
                first.canMoveTo(chessboard.getChessComponents(), chessComponent.getChessboardPoint(),chessboard.getStepNum());
    }
}
