package model;

import view.ChessboardPoint;
import controller.ClickController;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * 这个类表示国际象棋里面的象
 */
public class BishopChessComponent extends ChessComponent {
    private static Image Bishop_White;
    private static Image Bishop_Black;
    private Image bishopImage;
    public void loadResource() throws IOException {
        if (Bishop_White == null) {
            Bishop_White = ImageIO.read(new File("./images/bishop-white.png"));
        }

        if (Bishop_Black == null) {
            Bishop_Black = ImageIO.read(new File("./images/bishop-black.png"));
        }
    }


    private void initiateBishopImage(ChessColor color) {
        try {
            loadResource();
            if (color == ChessColor.WHITE) {
                bishopImage = Bishop_White;
            } else if (color == ChessColor.BLACK) {
                bishopImage = Bishop_Black;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public BishopChessComponent(ChessboardPoint chessboardPoint, Point location, ChessColor color, ClickController listener, int size) {
        super(chessboardPoint, location, color, listener, size);
        initiateBishopImage(color);
        if(this.getChessColor()==ChessColor.WHITE) super.setChessName("b");
        else super.setChessName("B");
    }

    /**
     * 象棋子的移动规则
     *
     * @param chessComponents 棋盘
     * @param destination 目标位置
     * @return 象棋子移动的合法性
     */

    public boolean canMoveTo(ChessComponent[][] chessComponents, ChessboardPoint destination,int step) {
        ArrayList<ChessboardPoint> BishopCanMove=ChessCanMove(chessComponents,step);
        int row=destination.getX(),col=destination.getY();
        for (ChessboardPoint TestChessboardPoint:BishopCanMove) {
            if( TestChessboardPoint.getX() == row && TestChessboardPoint.getY() == col)return true;
        }
        return false;
        /*ChessboardPoint source = getChessboardPoint();
        if (source.getY() -destination.getY()==source.getX()- destination.getX()) {
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

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
//        g.drawImage(rookImage, 0, 0, getWidth() - 13, getHeight() - 20, this);
        g.drawImage(bishopImage, 0, 0, getWidth() , getHeight(), this);
        g.setColor(Color.BLACK);
        if (isSelected()) { // Highlights the model if selected.
            g.setColor(Color.RED);
            g.drawOval(0, 0, getWidth() , getHeight());
        }
    }

    @Override
    public ArrayList<ChessboardPoint> ChessCanMove(ChessComponent[][] chessComponents,int step){
        ArrayList<ChessboardPoint> BishopCanMove=new ArrayList<>();
        int col = this.getChessboardPoint().getY();
        int row = this.getChessboardPoint().getX();
        for (int i = row+1, j = col+1; i <8&&j<8; i++, j++) {
            if(chessComponents[i][j].getChessColor()==ChessColor.NONE){
                BishopCanMove.add(new ChessboardPoint(i, j));}
            else if (chessComponents[i][j].getChessColor()==ChessColor.WHITE&&this.getChessColor()==ChessColor.BLACK){
                BishopCanMove.add(new ChessboardPoint(i, j));
                break;
            }else if (chessComponents[i][j].getChessColor()==ChessColor.BLACK&&this.getChessColor()==ChessColor.WHITE){
                BishopCanMove.add(new ChessboardPoint(i, j));
                break;
            }else if(chessComponents[i][j].getChessColor()==this.getChessColor()){
                break;
            }
        }
        for (int i = row-1, j = col-1; i >=0&&j>=0; i--, j--) {
            if(chessComponents[i][j].getChessColor()==ChessColor.NONE){
                BishopCanMove.add(new ChessboardPoint(i, j));}
            else if (chessComponents[i][j].getChessColor()==ChessColor.WHITE&&this.getChessColor()==ChessColor.BLACK){
                BishopCanMove.add(new ChessboardPoint(i, j));
                break;
            }else if (chessComponents[i][j].getChessColor()==ChessColor.BLACK&&this.getChessColor()==ChessColor.WHITE){
                BishopCanMove.add(new ChessboardPoint(i, j));
                break;
            }else if(chessComponents[i][j].getChessColor()==this.getChessColor()){
                break;
            }
        }
        for (int i = row-1, j = col+1; i >= 0 && j < 8; i--, j++) {
            if (chessComponents[i][j].getChessColor() == ChessColor.NONE) {
                BishopCanMove.add(new ChessboardPoint(i, j));
            } else if (chessComponents[i][j].getChessColor() == ChessColor.WHITE && this.getChessColor() == ChessColor.BLACK) {
                BishopCanMove.add(new ChessboardPoint(i, j));
                break;
            } else if (chessComponents[i][j].getChessColor() == ChessColor.BLACK && this.getChessColor() == ChessColor.WHITE) {
                BishopCanMove.add(new ChessboardPoint(i, j));
                break;
            } else if (chessComponents[i][j].getChessColor() == this.getChessColor()) {
                break;
            }
        }
        for (int i = row+1, j = col-1; j >= 0 && i < 8; j--, i++) {
            if (chessComponents[i][j].getChessColor() == ChessColor.NONE) {
                BishopCanMove.add(new ChessboardPoint(i, j));
            } else if (chessComponents[i][j].getChessColor() == ChessColor.WHITE && this.getChessColor() == ChessColor.BLACK) {
                BishopCanMove.add(new ChessboardPoint(i, j));
                break;
            } else if (chessComponents[i][j].getChessColor() == ChessColor.BLACK && this.getChessColor() == ChessColor.WHITE) {
                BishopCanMove.add(new ChessboardPoint(i, j));
                break;
            } else if (chessComponents[i][j].getChessColor() == this.getChessColor()) {
                break;
            }
        }
        return BishopCanMove;
    }
}
