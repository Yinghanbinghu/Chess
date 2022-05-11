package model;

import view.ChessboardPoint;
import controller.ClickController;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 * 这个类表示国际象棋里面的兵
 */
public class PawnChessComponent extends ChessComponent {
    /**
     * 黑后和白后的图片，static使得其可以被所有车对象共享
     * <br>
     * FIXME: 需要特别注意此处加载的图片是没有背景底色的！！！
     */
    private static Image Pawn_White;
    private static Image Pawn_Black;

    /**
     * 车棋子对象自身的图片，是上面两种中的一种
     */
    private Image pawnImage;

    /**
     * 读取加载车棋子的图片
     *
     * @throws IOException
     */
    public void loadResource() throws IOException {
        if (Pawn_White == null) {
            Pawn_White = ImageIO.read(new File("./images/pawn-white.png"));
        }

        if (Pawn_Black == null) {
            Pawn_Black = ImageIO.read(new File("./images/pawn-black.png"));
        }
    }


    /**
     * 在构造棋子对象的时候，调用此方法以根据颜色确定queenImage的图片是哪一种
     *
     * @param color 棋子颜色
     */

    private void initiatePawnImage(ChessColor color) {
        try {
            loadResource();
            if (color == ChessColor.WHITE) {
                pawnImage = Pawn_White;
            } else if (color == ChessColor.BLACK) {
                pawnImage = Pawn_Black;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public PawnChessComponent(ChessboardPoint chessboardPoint, Point location, ChessColor color, ClickController listener, int size) {
        super(chessboardPoint, location, color, listener, size);
        initiatePawnImage(color);
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
        ChessboardPoint source = getChessboardPoint();
        System.out.println(step);
        if (this.chessColor == ChessColor.WHITE) {                 //白棋卒
            if (source.getX()==6 &&source.getX() == destination.getX() + 2 && source.getY() == destination.getY() ) {   //第一步前进二
                int row = source.getX() - 1, col = destination.getY();
                super.setTwoStep(step);
                return chessComponents[row][col] instanceof EmptySlotComponent && chessComponents[row - 1][col] instanceof EmptySlotComponent;
            } else if (source.getX() == destination.getX() + 1 && source.getY() == destination.getY()) {           //前进一
                int row = source.getX() - 1, col = destination.getY();
                if(chessComponents[row][col] instanceof EmptySlotComponent&&row==0){
                    System.out.println("输入类型");
                    Scanner s=new Scanner(System.in);
                    super.upGratePawn(s.nextInt());           //此处的数字留做升变卒型用
                }
                return chessComponents[row][col] instanceof EmptySlotComponent;
            }else if(source.getX() == destination.getX() + 1 && (source.getY() == destination.getY()+1||source.getY() == destination.getY()-1)){    //斜向吃
                int row =destination.getX(), col = destination.getY();
                if (chessComponents[row+1][col] instanceof PawnChessComponent&&chessComponents[row+1][col].getChessColor()==ChessColor.BLACK&&chessComponents[row+1][col].getTwoStep()+1==step){  //斜吃过路卒
                    super.removePassPawn(chessComponents[row+1][col]);
                    return true;
                }
                if((!(chessComponents[row][col] instanceof EmptySlotComponent))&&row==0){
                    System.out.println("输入类型");
                    Scanner s=new Scanner(System.in);
                    super.upGratePawn(s.nextInt());           //此处的数字留做升变卒型用
                }
                return !(chessComponents[row][col] instanceof EmptySlotComponent);
            }else return false;
        } else {                 //黑棋卒
            if (source.getX()==1&&source.getX() == destination.getX() - 2 && source.getY() == destination.getY()) {    //第一步前进二
                int row = source.getX() + 1, col = destination.getY();
                super.setTwoStep(step);
                return chessComponents[row][col] instanceof EmptySlotComponent && chessComponents[row + 1][col] instanceof EmptySlotComponent;
            } else if (source.getX() == destination.getX() - 1 && source.getY() == destination.getY()) {             //前进一
                int row = source.getX() + 1, col = destination.getY();
                if(chessComponents[row][col] instanceof EmptySlotComponent&&row==7){
                    System.out.println("输入类型");
                    Scanner s=new Scanner(System.in);
                    super.upGratePawn(s.nextInt());           //此处的数字留做升变卒型用
                }
                return chessComponents[row][col] instanceof EmptySlotComponent;
            }else if(source.getX() == destination.getX() - 1 && (source.getY() == destination.getY()-1||source.getY() == destination.getY()+1)){     //斜向吃
                int row = destination.getX(), col = destination.getY();
                if (chessComponents[row-1][col] instanceof PawnChessComponent&&chessComponents[row-1][col].getChessColor()==ChessColor.WHITE&&chessComponents[row-1][col].getTwoStep()+1==step){
                    super.removePassPawn(chessComponents[row-1][col]);
                    return true;
                }
                if((!(chessComponents[row][col] instanceof EmptySlotComponent))&&row==7){
                    System.out.println("输入类型");
                    Scanner s=new Scanner(System.in);
                    super.upGratePawn(s.nextInt());           //此处的数字留做升变卒型用
                }
                return !(chessComponents[row][col] instanceof EmptySlotComponent);
            } else return false;
        }
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
        g.drawImage(pawnImage, 0, 0, getWidth(), getHeight(), this);
        g.setColor(Color.BLACK);
        if (isSelected()) { // Highlights the model if selected.
            g.setColor(Color.RED);
            g.drawOval(0, 0, getWidth(), getHeight());
        }
    }
}
