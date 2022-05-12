package model;

import view.ChessboardPoint;
import controller.ClickController;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * 这个类表示国际象棋里面的马
 */
public class KnightChessComponent extends ChessComponent {
    /**
     * 黑后和白后的图片，static使得其可以被所有车对象共享
     * <br>
     * FIXME: 需要特别注意此处加载的图片是没有背景底色的！！！
     */
    private static Image Knight_White;
    private static Image Knight_Black;

    /**
     * 车棋子对象自身的图片，是上面两种中的一种
     */
    private Image KnightImage;

    /**
     * 读取加载车棋子的图片
     *
     * @throws IOException
     */
    public void loadResource() throws IOException {
        if (Knight_White == null) {
            Knight_White = ImageIO.read(new File("./images/knight-white.png"));
        }

        if (Knight_Black == null) {
            Knight_Black = ImageIO.read(new File("./images/knight-black.png"));
        }
    }


    /**
     * 在构造棋子对象的时候，调用此方法以根据颜色确定queenImage的图片是哪一种
     *
     * @param color 棋子颜色
     */

    private void initiateKnightImage(ChessColor color) {
        try {
            loadResource();
            if (color == ChessColor.WHITE) {
                KnightImage = Knight_White;
            } else if (color == ChessColor.BLACK) {
                KnightImage = Knight_Black;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public KnightChessComponent(ChessboardPoint chessboardPoint, Point location, ChessColor color, ClickController listener, int size) {
        super(chessboardPoint, location, color, listener, size);
        initiateKnightImage(color);
    }

    /**
     * 后棋子的移动规则
     *
     * @param chessComponents 棋盘
     * @param destination     目标位置
     * @return 后棋子移动的合法性
     */


    @Override
    public boolean canMoveTo(ChessComponent[][] chessComponents, ChessboardPoint destination,int step) {
        ArrayList<ChessboardPoint> KnightCanMove=ChessCanMove(chessComponents,step);
        int row=destination.getX(),col=destination.getY();
        for (ChessboardPoint TestChessboardPoint:KnightCanMove) {
            if( TestChessboardPoint.getX() == row && TestChessboardPoint.getY() == col)return true;
        }
        return false;
        /*ChessboardPoint source = getChessboardPoint();
        if ((source.getX() == destination.getX()+1||source.getX() == destination.getX()-1)&&(source.getY() == destination.getY()+2||source.getY() == destination.getY()-2)) {
            return true;
        }else if((source.getX() == destination.getX()+2||source.getX() == destination.getX()-2)&&(source.getY() == destination.getY()+1||source.getY() == destination.getY()-1)){
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
        g.drawImage(KnightImage, 0, 0, getWidth() , getHeight(), this);
        g.setColor(Color.BLACK);
        if (isSelected()) { // Highlights the model if selected.
            g.setColor(Color.RED);
            g.drawOval(0, 0, getWidth() , getHeight());
        }
    }

    public ArrayList<ChessboardPoint> ChessCanMove(ChessComponent[][] chessComponents,int step){
        ArrayList<ChessboardPoint> KnightCanMove = new ArrayList<>();
        int col = this.getChessboardPoint().getY();
        int row = this.getChessboardPoint().getX();
        int i,j;
        i=row+2;j=col-1;
        if(i>=0&&i<8&&j>=0&&j<8&&chessComponents[i][j].getChessColor()!=this.getChessColor())KnightCanMove.add(new ChessboardPoint(i,j));
        i=row+2;j=col+1;
        if(i>=0&&i<8&&j>=0&&j<8&&chessComponents[i][j].getChessColor()!=this.getChessColor())KnightCanMove.add(new ChessboardPoint(i,j));
        i=row+1;j=col-2;
        if(i>=0&&i<8&&j>=0&&j<8&&chessComponents[i][j].getChessColor()!=this.getChessColor())KnightCanMove.add(new ChessboardPoint(i,j));
        i=row+1;j=col+2;
        if(i>=0&&i<8&&j>=0&&j<8&&chessComponents[i][j].getChessColor()!=this.getChessColor())KnightCanMove.add(new ChessboardPoint(i,j));
        i=row-1;j=col-2;
        if(i>=0&&i<8&&j>=0&&j<8&&chessComponents[i][j].getChessColor()!=this.getChessColor())KnightCanMove.add(new ChessboardPoint(i,j));
        i=row-1;j=col+2;
        if(i>=0&&i<8&&j>=0&&j<8&&chessComponents[i][j].getChessColor()!=this.getChessColor())KnightCanMove.add(new ChessboardPoint(i,j));
        i=row-2;j=col-1;
        if(i>=0&&i<8&&j>=0&&j<8&&chessComponents[i][j].getChessColor()!=this.getChessColor())KnightCanMove.add(new ChessboardPoint(i,j));
        i=row-2;j=col+1;
        if(i>=0&&i<8&&j>=0&&j<8&&chessComponents[i][j].getChessColor()!=this.getChessColor())KnightCanMove.add(new ChessboardPoint(i,j));
        return KnightCanMove;
    }
}
