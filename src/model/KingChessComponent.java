package model;

import view.ChessboardPoint;
import controller.ClickController;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * 这个类表示国际象棋里面的王
 */
public class KingChessComponent extends ChessComponent {
    private static Image King_White;
    private static Image King_Black;
    private Image kingImage;

    /**
     * 读取加载王棋子的图片
     *
     * @throws IOException
     */
    public void loadResource() throws IOException {
        if (King_White == null) {
            King_White = ImageIO.read(new File("./images/king-white.png"));
        }

        if (King_Black == null) {
            King_Black = ImageIO.read(new File("./images/king-black.png"));
        }
    }


    /**
     * 在构造棋子对象的时候，调用此方法以根据颜色确定kingImage的图片是哪一种
     *
     * @param color 棋子颜色
     */

    private void initiateQueenImage(ChessColor color) {
        try {
            loadResource();
            if (color == ChessColor.WHITE) {
                kingImage = King_White;
            } else if (color == ChessColor.BLACK) {
                kingImage = King_Black;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public KingChessComponent(ChessboardPoint chessboardPoint, Point location, ChessColor color, ClickController listener, int size) {
        super(chessboardPoint, location, color, listener, size);
        initiateQueenImage(color);
    }

    /**
     * 王棋子的移动规则
     *
     * @param chessComponents 棋盘
     * @param destination     目标位置
     * @return 王棋子移动的合法性
     */
    /*public boolean king_king(ChessComponent[][] chessComponents, ChessboardPoint destination){

    }*/
    @Override
    public boolean canMoveTo(ChessComponent[][] chessComponents, ChessboardPoint destination,int step) {
        ArrayList<ChessboardPoint> KingCanMove=ChessCanMove(chessComponents,step);
        int row=destination.getX(),col=destination.getY();
        for (ChessboardPoint TestChessboardPoint:KingCanMove) {
            if( TestChessboardPoint.getX() == row && TestChessboardPoint.getY() == col)return true;
        }
        return false;
        /*ChessboardPoint source = getChessboardPoint();
        if (Math.abs(destination.getX()-this.getChessboardPoint().getX())<=1&&Math.abs(destination.getY()-this.getChessboardPoint().getY())<=1) {*//*(source.getX() == destination.getX() && (source.getY() == destination.getY() + 1 || source.getY() == destination.getY() - 1)) || (destination.getY() == source.getY() && (source.getX() == destination.getX() + 1 || source.getX() == destination.getX() - 1))*//*
            int row = destination.getX(), col = destination.getY();
            int i,j;
            i=row-1;j=col;
            if(i<8&&i>=0&&j<8&&j>=0&&chessComponents[i][j] instanceof KingChessComponent&&chessComponents[i][j].getChessColor()!=this.getChessColor()) return false;
            i=row-1;j=col+1;
            if(i<8&&i>=0&&j<8&&j>=0&&chessComponents[i][j] instanceof KingChessComponent&&chessComponents[i][j].getChessColor()!=this.getChessColor()) return false;
            i=row-1;j=col-1;
            if(i<8&&i>=0&&j<8&&j>=0&&chessComponents[i][j] instanceof KingChessComponent&&chessComponents[i][j].getChessColor()!=this.getChessColor()) return false;
            i=row;j=col+1;
            if(i<8&&i>=0&&j<8&&j>=0&&chessComponents[i][j] instanceof KingChessComponent&&chessComponents[i][j].getChessColor()!=this.getChessColor()) return false;
            i=row;j=col-1;
            if(i<8&&i>=0&&j<8&&j>=0&&chessComponents[i][j] instanceof KingChessComponent&&chessComponents[i][j].getChessColor()!=this.getChessColor()) return false;
            i=row+1;j=col;
            if(i<8&&i>=0&&j<8&&j>=0&&chessComponents[i][j] instanceof KingChessComponent&&chessComponents[i][j].getChessColor()!=this.getChessColor()) return false;
            i=row+1;j=col-1;
            if(i<8&&i>=0&&j<8&&j>=0&&chessComponents[i][j] instanceof KingChessComponent&&chessComponents[i][j].getChessColor()!=this.getChessColor()) return false;
            i=row+1;j=col+1;
            if(i<8&&i>=0&&j<8&&j>=0&&chessComponents[i][j] instanceof KingChessComponent&&chessComponents[i][j].getChessColor()!=this.getChessColor()) return false;
            return true;
        }else return false;*/
    }

    /**
     * 注意这个方法，每当窗体受到了形状的变化，或者是通知要进行绘图的时候，就会调用这个方法进行画图。
     *
     * @param g 可以类比于画笔
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
//        g.drawImage(rookImage, 0, 0, getWidth() - 13, getHeight() - 20, this);
        g.drawImage(kingImage, 0, 0, getWidth(), getHeight(), this);
        g.setColor(Color.BLACK);
        if (isSelected()) { // Highlights the model if selected.
            g.setColor(Color.RED);
            g.drawOval(0, 0, getWidth(), getHeight());
        }
    }

    public ArrayList<ChessboardPoint> ChessCanMove(ChessComponent[][] chessComponents,int step){
        ArrayList<ChessboardPoint> KingCanMove=new ArrayList<>();
        int row=this.getChessboardPoint().getX(),col=this.getChessboardPoint().getY();
        int i,j;
        i=row-1;j=col;
        if (i>=0&&i<8&&j>=0&j<8&&chessComponents[i][j].getChessColor()!=this.getChessColor()&&KingMeet(chessComponents,i,j)) KingCanMove.add(new ChessboardPoint(i,j));
        i=row-1;j=col-1;
        if (i>=0&&i<8&&j>=0&j<8&&chessComponents[i][j].getChessColor()!=this.getChessColor()&&KingMeet(chessComponents,i,j)) KingCanMove.add(new ChessboardPoint(i,j));
        i=row-1;j=col+1;
        if (i>=0&&i<8&&j>=0&j<8&&chessComponents[i][j].getChessColor()!=this.getChessColor()&&KingMeet(chessComponents,i,j)) KingCanMove.add(new ChessboardPoint(i,j));
        i=row;j=col+1;
        if (i>=0&&i<8&&j>=0&j<8&&chessComponents[i][j].getChessColor()!=this.getChessColor()&&KingMeet(chessComponents,i,j)) KingCanMove.add(new ChessboardPoint(i,j));
        i=row;j=col-1;
        if (i>=0&&i<8&&j>=0&j<8&&chessComponents[i][j].getChessColor()!=this.getChessColor()&&KingMeet(chessComponents,i,j)) KingCanMove.add(new ChessboardPoint(i,j));
        i=row+1;j=col+1;
        if (i>=0&&i<8&&j>=0&j<8&&chessComponents[i][j].getChessColor()!=this.getChessColor()&&KingMeet(chessComponents,i,j)) KingCanMove.add(new ChessboardPoint(i,j));
        i=row+1;j=col-1;
        if (i>=0&&i<8&&j>=0&j<8&&chessComponents[i][j].getChessColor()!=this.getChessColor()&&KingMeet(chessComponents,i,j)) KingCanMove.add(new ChessboardPoint(i,j));
        i=row+1;j=col;
        if (i>=0&&i<8&&j>=0&j<8&&chessComponents[i][j].getChessColor()!=this.getChessColor()&&KingMeet(chessComponents,i,j)) KingCanMove.add(new ChessboardPoint(i,j));
        return KingCanMove;
    }
    private boolean KingMeet(ChessComponent[][] chessComponents,int row,int col){
        int i,j;
        i=row-1;j=col;
        if(i<8&&i>=0&&j<8&&j>=0&&chessComponents[i][j] instanceof KingChessComponent&&chessComponents[i][j].getChessColor()!=this.getChessColor()) return false;
        i=row-1;j=col+1;
        if(i<8&&i>=0&&j<8&&j>=0&&chessComponents[i][j] instanceof KingChessComponent&&chessComponents[i][j].getChessColor()!=this.getChessColor()) return false;
        i=row-1;j=col-1;
        if(i<8&&i>=0&&j<8&&j>=0&&chessComponents[i][j] instanceof KingChessComponent&&chessComponents[i][j].getChessColor()!=this.getChessColor()) return false;
        i=row;j=col+1;
        if(i<8&&i>=0&&j<8&&j>=0&&chessComponents[i][j] instanceof KingChessComponent&&chessComponents[i][j].getChessColor()!=this.getChessColor()) return false;
        i=row;j=col-1;
        if(i<8&&i>=0&&j<8&&j>=0&&chessComponents[i][j] instanceof KingChessComponent&&chessComponents[i][j].getChessColor()!=this.getChessColor()) return false;
        i=row+1;j=col;
        if(i<8&&i>=0&&j<8&&j>=0&&chessComponents[i][j] instanceof KingChessComponent&&chessComponents[i][j].getChessColor()!=this.getChessColor()) return false;
        i=row+1;j=col-1;
        if(i<8&&i>=0&&j<8&&j>=0&&chessComponents[i][j] instanceof KingChessComponent&&chessComponents[i][j].getChessColor()!=this.getChessColor()) return false;
        i=row+1;j=col+1;
        if(i<8&&i>=0&&j<8&&j>=0&&chessComponents[i][j] instanceof KingChessComponent&&chessComponents[i][j].getChessColor()!=this.getChessColor()) return false;
        return true;
    }
}
