package model;

import view.ChessboardPoint;
import controller.ClickController;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * 这个类表示国际象棋里面的车
 */
public class RookChessComponent extends ChessComponent {
    /**
     * 黑车和白车的图片，static使得其可以被所有车对象共享
     * <br>
     * FIXME: 需要特别注意此处加载的图片是没有背景底色的！！！
     */
    private static Image ROOK_WHITE;
    private static Image ROOK_BLACK;

    /**
     * 车棋子对象自身的图片，是上面两种中的一种
     */
    private Image rookImage;

    /**
     * 读取加载车棋子的图片
     *
     * @throws IOException
     */
    public void loadResource() throws IOException {
        if (ROOK_WHITE == null) {
            ROOK_WHITE = ImageIO.read(new File("./images/rook-white.png"));
        }

        if (ROOK_BLACK == null) {
            ROOK_BLACK = ImageIO.read(new File("./images/rook-black.png"));
        }
    }


    /**
     * 在构造棋子对象的时候，调用此方法以根据颜色确定rookImage的图片是哪一种
     *
     * @param color 棋子颜色
     */

    private void initiateRookImage(ChessColor color) {
        try {
            loadResource();
            if (color == ChessColor.WHITE) {
                rookImage = ROOK_WHITE;
            } else if (color == ChessColor.BLACK) {
                rookImage = ROOK_BLACK;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public RookChessComponent(ChessboardPoint chessboardPoint, Point location, ChessColor color, ClickController listener, int size) {
        super(chessboardPoint, location, color, listener, size);
        initiateRookImage(color);
        super.setIntiPoint(chessboardPoint);
        if(this.getChessColor()==ChessColor.WHITE) super.setChessName("r");
        else super.setChessName("R");
    }

    /**
     * 车棋子的移动规则
     *
     * @param chessComponents 棋盘
     * @param destination     目标位置，如(0, 0), (0, 7)等等
     * @return 车棋子移动的合法性
     */

    @Override
    public boolean canMoveTo(ChessComponent[][] chessComponents, ChessboardPoint destination,int step) {
        ArrayList<ChessboardPoint> RookCanMove=ChessCanMove(chessComponents,step);
        int row=destination.getX(),col=destination.getY();
        for (ChessboardPoint TestChessboardPoint:RookCanMove) {
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
        } else { // Not on the same row or the same column.
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
        g.drawImage(rookImage, 0, 0, getWidth() , getHeight(), this);
        g.setColor(Color.BLACK);
        if (isSelected()) { // Highlights the model if selected.
            g.setColor(Color.RED);
            g.drawOval(0, 0, getWidth() , getHeight());
        }
    }

    public ArrayList<ChessboardPoint> ChessCanMove(ChessComponent[][] chessComponents,int step){
        ArrayList<ChessboardPoint> RookCanMove = new ArrayList<>();
        int col = this.getChessboardPoint().getY();
        int row = this.getChessboardPoint().getX();
        for (int i = row+1; i < 8; i++) {
            if (chessComponents[i][col].getChessColor() == ChessColor.NONE) {
                RookCanMove.add(new ChessboardPoint(i, col));
            } else if (chessComponents[i][col].getChessColor() == ChessColor.WHITE && this.getChessColor() == ChessColor.BLACK) {
                RookCanMove.add(new ChessboardPoint(i, col));
                break;
            } else if (chessComponents[i][col].getChessColor() == ChessColor.BLACK && this.getChessColor() == ChessColor.WHITE) {
                RookCanMove.add(new ChessboardPoint(i, col));
                break;
            } else if (chessComponents[i][col].getChessColor() == this.getChessColor()) {
                break;
            }
        }
        for (int i = row-1; i >= 0; i--) {
            if (chessComponents[i][col].getChessColor() == ChessColor.NONE) {
                RookCanMove.add(new ChessboardPoint(i, col));
            } else if (chessComponents[i][col].getChessColor() == ChessColor.WHITE && this.getChessColor() == ChessColor.BLACK) {
                RookCanMove.add(new ChessboardPoint(i, col));
                break;
            } else if (chessComponents[i][col].getChessColor() == ChessColor.BLACK && this.getChessColor() == ChessColor.WHITE) {
                RookCanMove.add(new ChessboardPoint(i, col));
                break;
            } else if (chessComponents[i][col].getChessColor() == this.getChessColor()) {
                break;
            }
        }
        for (int j = col-1; j >= 0; j--) {
            if (chessComponents[row][j].getChessColor() == ChessColor.NONE) {
                RookCanMove.add(new ChessboardPoint(row, j));
            } else if (chessComponents[row][j].getChessColor() == ChessColor.WHITE && this.getChessColor() == ChessColor.BLACK) {
                RookCanMove.add(new ChessboardPoint(row, j));
                break;
            } else if (chessComponents[row][j].getChessColor() == ChessColor.BLACK && this.getChessColor() == ChessColor.WHITE) {
                RookCanMove.add(new ChessboardPoint(row, j));
                break;
            } else if (chessComponents[row][j].getChessColor() == this.getChessColor()) {
                break;
            }
        }
        for (int j = col+1; j < 8; j++) {
            if (chessComponents[row][j].getChessColor() == ChessColor.NONE) {
                RookCanMove.add(new ChessboardPoint(row, j));
            } else if (chessComponents[row][j].getChessColor() == ChessColor.WHITE && this.getChessColor() == ChessColor.BLACK) {
                RookCanMove.add(new ChessboardPoint(row, j));
                break;
            } else if (chessComponents[row][j].getChessColor() == ChessColor.BLACK && this.getChessColor() == ChessColor.WHITE) {
                RookCanMove.add(new ChessboardPoint(row, j));
                break;
            } else if (chessComponents[row][j].getChessColor() == this.getChessColor()) {
                break;
            }
        }
        return RookCanMove;
    }
}
