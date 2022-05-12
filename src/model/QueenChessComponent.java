package model;

import view.ChessboardPoint;
import controller.ClickController;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * 这个类表示国际象棋里面的后
 */
public class QueenChessComponent extends ChessComponent {
    /**
     * 黑后和白后的图片，static使得其可以被所有车对象共享
     * <br>
     * FIXME: 需要特别注意此处加载的图片是没有背景底色的！！！
     */
    private static Image Queen_White;
    private static Image Queen_Black;

    /**
     * 车棋子对象自身的图片，是上面两种中的一种
     */
    private Image queenImage;

    /**
     * 读取加载车棋子的图片
     *
     * @throws IOException
     */
    public void loadResource() throws IOException {
        if (Queen_White == null) {
            Queen_White = ImageIO.read(new File("./images/queen-white.png"));
        }

        if (Queen_Black == null) {
            Queen_Black = ImageIO.read(new File("./images/queen-black.png"));
        }
    }


    /**
     * 在构造棋子对象的时候，调用此方法以根据颜色确定queenImage的图片是哪一种
     *
     * @param color 棋子颜色
     */

    private void initiateQueenImage(ChessColor color) {
        try {
            loadResource();
            if (color == ChessColor.WHITE) {
                queenImage = Queen_White;
            } else if (color == ChessColor.BLACK) {
                queenImage = Queen_Black;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public QueenChessComponent(ChessboardPoint chessboardPoint, Point location, ChessColor color, ClickController listener, int size) {
        super(chessboardPoint, location, color, listener, size);
        initiateQueenImage(color);
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
        ArrayList<ChessboardPoint> QueenCanMove=ChessCanMove(chessComponents,step);
        int row=destination.getX(),col=destination.getY();
        for (ChessboardPoint TestChessboardPoint:QueenCanMove) {
            if( TestChessboardPoint.getX() == row && TestChessboardPoint.getY() == col)return true;
        }
        return false;
        /*ChessboardPoint source = getChessboardPoint();
        if (source.getX() == destination.getX()) {
            int row = source.getX();
            for (int col = Math.min(source.getY(), destination.getY()) + 1;
                 col < Math.max(source.getY(), destination.getY()); col++) {
                if (!(chessComponents[row][col] instanceof EmptySlotComponent)) {
                    return false;
                }
            }
        } else if (source.getY() == destination.getY()) {
            int col = source.getY();
            for (int row = Math.min(source.getX(), destination.getX()) + 1;
                 row < Math.max(source.getX(), destination.getX()); row++) {
                if (!(chessComponents[row][col] instanceof EmptySlotComponent)) {
                    return false;
                }
            }
        }else if (source.getY() -destination.getY()==source.getX()- destination.getX()) {
            for (int row = Math.min(source.getX(), destination.getX()) + 1,col=Math.min(source.getY(), destination.getY()) + 1;
                 row < Math.max(source.getX(), destination.getX()); row++,col++) {
                if (!(chessComponents[row][col] instanceof EmptySlotComponent)) {
                    return false;
                }
            }
        }else if (source.getY() -destination.getY()==-source.getX()+ destination.getX()) {
            for (int row = Math.min(source.getX(), destination.getX()) + 1,col=Math.max(source.getY(), destination.getY()) - 1;
                 row < Math.max(source.getX(), destination.getX()); row++,col--) {
                if (!(chessComponents[row][col] instanceof EmptySlotComponent)) {
                    return false;
                }
            }
        }
        else { // Not on the same line.
            return false;
        }
        return true;*/
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
        g.drawImage(queenImage, 0, 0, getWidth() , getHeight(), this);
        g.setColor(Color.BLACK);
        if (isSelected()) { // Highlights the model if selected.
            g.setColor(Color.RED);
            g.drawOval(0, 0, getWidth() , getHeight());
        }
    }

    public ArrayList<ChessboardPoint> ChessCanMove(ChessComponent[][] chessComponents,int step){
        ArrayList<ChessboardPoint> QueenCanMove = new ArrayList<>();
        int col = this.getChessboardPoint().getY();
        int row = this.getChessboardPoint().getX();
        for (int i = row+1; i < 8; i++) {
            if (chessComponents[i][col].getChessColor() == ChessColor.NONE) {
                QueenCanMove.add(new ChessboardPoint(i, col));
            } else if (chessComponents[i][col].getChessColor() == ChessColor.WHITE && this.getChessColor() == ChessColor.BLACK) {
                QueenCanMove.add(new ChessboardPoint(i, col));
                break;
            } else if (chessComponents[i][col].getChessColor() == ChessColor.BLACK && this.getChessColor() == ChessColor.WHITE) {
                QueenCanMove.add(new ChessboardPoint(i, col));
                break;
            } else if (chessComponents[i][col].getChessColor() == this.getChessColor()) {
                break;
            }
        }
        for (int i = row-1; i >= 0; i--) {
            if (chessComponents[i][col].getChessColor() == ChessColor.NONE) {
                QueenCanMove.add(new ChessboardPoint(i, col));
            } else if (chessComponents[i][col].getChessColor() == ChessColor.WHITE && this.getChessColor() == ChessColor.BLACK) {
                QueenCanMove.add(new ChessboardPoint(i, col));
                break;
            } else if (chessComponents[i][col].getChessColor() == ChessColor.BLACK && this.getChessColor() == ChessColor.WHITE) {
                QueenCanMove.add(new ChessboardPoint(i, col));
                break;
            } else if (chessComponents[i][col].getChessColor() == this.getChessColor()) {
                break;
            }
        }
        for (int j = col-1; j >= 0; j--) {
            if (chessComponents[row][j].getChessColor() == ChessColor.NONE) {
                QueenCanMove.add(new ChessboardPoint(row, j));
            } else if (chessComponents[row][j].getChessColor() == ChessColor.WHITE && this.getChessColor() == ChessColor.BLACK) {
                QueenCanMove.add(new ChessboardPoint(row, j));
                break;
            } else if (chessComponents[row][j].getChessColor() == ChessColor.BLACK && this.getChessColor() == ChessColor.WHITE) {
                QueenCanMove.add(new ChessboardPoint(row, j));
                break;
            } else if (chessComponents[row][j].getChessColor() == this.getChessColor()) {
                break;
            }
        }
        for (int j = col+1; j < 8; j++) {
            if (chessComponents[row][j].getChessColor() == ChessColor.NONE) {
                QueenCanMove.add(new ChessboardPoint(row, j));
            } else if (chessComponents[row][j].getChessColor() == ChessColor.WHITE && this.getChessColor() == ChessColor.BLACK) {
                QueenCanMove.add(new ChessboardPoint(row, j));
                break;
            } else if (chessComponents[row][j].getChessColor() == ChessColor.BLACK && this.getChessColor() == ChessColor.WHITE) {
                QueenCanMove.add(new ChessboardPoint(row, j));
                break;
            } else if (chessComponents[row][j].getChessColor() == this.getChessColor()) {
                break;
            }
        }

        for (int i = row-1, j = col+1; i >= 0 && j < 8; i--, j++) {
            if (chessComponents[i][j].getChessColor() == ChessColor.NONE) {
                QueenCanMove.add(new ChessboardPoint(i, j));
            } else if (chessComponents[i][j].getChessColor() == ChessColor.WHITE && this.getChessColor() == ChessColor.BLACK) {
                QueenCanMove.add(new ChessboardPoint(i, j));
                break;
            } else if (chessComponents[i][j].getChessColor() == ChessColor.BLACK && this.getChessColor() == ChessColor.WHITE) {
                QueenCanMove.add(new ChessboardPoint(i, j));
                break;
            } else if (chessComponents[i][j].getChessColor() == this.getChessColor()) {
                break;
            }
        }
        for (int i = row+1, j = col-1; j >= 0 && i < 8; j--, i++) {
            if (chessComponents[i][j].getChessColor() == ChessColor.NONE) {
                QueenCanMove.add(new ChessboardPoint(i, j));
            } else if (chessComponents[i][j].getChessColor() == ChessColor.WHITE && this.getChessColor() == ChessColor.BLACK) {
                QueenCanMove.add(new ChessboardPoint(i, j));
                break;
            } else if (chessComponents[i][j].getChessColor() == ChessColor.BLACK && this.getChessColor() == ChessColor.WHITE) {
                QueenCanMove.add(new ChessboardPoint(i, j));
                break;
            } else if (chessComponents[i][j].getChessColor() == this.getChessColor()) {
                break;
            }
        }
        for (int i = row+1, j = col+1; i <8&&j<8; i++, j++) {
            if(chessComponents[i][j].getChessColor()==ChessColor.NONE){
                QueenCanMove.add(new ChessboardPoint(i, j));}
            else if (chessComponents[i][j].getChessColor()==ChessColor.WHITE&&this.getChessColor()==ChessColor.BLACK){
                QueenCanMove.add(new ChessboardPoint(i, j));
                break;
            }else if (chessComponents[i][j].getChessColor()==ChessColor.BLACK&&this.getChessColor()==ChessColor.WHITE){
                QueenCanMove.add(new ChessboardPoint(i, j));
                break;
            }else if(chessComponents[i][j].getChessColor()==this.getChessColor()){
                break;
            }
        }
        for (int i = row-1, j = col-1; i >=0&&j>=0; i--, j--) {
            if(chessComponents[i][j].getChessColor()==ChessColor.NONE){
                QueenCanMove.add(new ChessboardPoint(i, j));}
            else if (chessComponents[i][j].getChessColor()==ChessColor.WHITE&&this.getChessColor()==ChessColor.BLACK){
                QueenCanMove.add(new ChessboardPoint(i, j));
                break;
            }else if (chessComponents[i][j].getChessColor()==ChessColor.BLACK&&this.getChessColor()==ChessColor.WHITE){
                QueenCanMove.add(new ChessboardPoint(i, j));
                break;
            }else if(chessComponents[i][j].getChessColor()==this.getChessColor()){
                break;
            }
        }
        return QueenCanMove;
    }
}
