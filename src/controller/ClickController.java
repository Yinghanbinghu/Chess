package controller;


import model.ChessColor;
import model.ChessComponent;
import view.Chessboard;

import javax.swing.*;

public class ClickController {
    private final Chessboard chessboard;
    private ChessComponent first;
    private boolean UpGratePawn;

    public void setUpGratePawn(int upGratePawn) {
        UpGratePawn = true;
    }

    public ClickController(Chessboard chessboard) {
        this.chessboard = chessboard;
    }

    public void intiFirst() {
        this.first = null;
    }

    public void onClick(ChessComponent chessComponent) {
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
                ChessComponent recordFirst = first;
                first = null;
                chessboard.helpCanMoveTo(null);
                recordFirst.repaint();
            } else if (handleSecond(chessComponent)) {
                //repaint in swap chess method.
                chessboard.swapChessComponents(first, chessComponent);
                chessboard.swapColor();
                chessboard.stepCount(true);
                chessboard.helpCanMoveTo(null);
                if(UpGratePawn){
                    chessboard.upGratePawn(first);
                    UpGratePawn=false;
                }
                if(!chessboard.canMove()){
                    int n = JOptionPane.showConfirmDialog(null, "是否开始新游戏?", "无子可动和棋",JOptionPane.YES_NO_OPTION);
                    if(n==1){
                        chessboard.initChessboard();
                    }
                }
                if(chessboard.isIfHelp()&&chessboard.ifKingCanBeEat()){
                    if(chessboard.getCurrentColor()== ChessColor.WHITE)
                    JOptionPane.showMessageDialog(null, "黑方将军", "提示",JOptionPane.WARNING_MESSAGE);
                    if(chessboard.getCurrentColor()== ChessColor.BLACK)
                        JOptionPane.showMessageDialog(null, "白方将军", "提示",JOptionPane.WARNING_MESSAGE);
                }
                chessboard.saveStep();
                first.setSelected(false);
                first = null;
            }

        }
    }

    public void removePassPawn(ChessComponent passPawn){
        chessboard.removePassPawn(passPawn);
    }



    /**
     * @param chessComponent 目标选取的棋子
     * @return 目标选取的棋子是否与棋盘记录的当前行棋方颜色相同
     */

    private boolean handleFirst(ChessComponent chessComponent) {
        return chessComponent.getChessColor() == chessboard.getCurrentColor();
    }


    /**
     * @param chessComponent first棋子目标移动到的棋子second
     * @return first棋子是否能够移动到second棋子位置
     */

    private boolean handleSecond(ChessComponent chessComponent) {
        return chessComponent.getChessColor() != chessboard.getCurrentColor() &&
                first.canMoveTo(chessboard.getChessComponents(), chessComponent.getChessboardPoint(),chessboard.getStepNum());
    }
}
