package model;

import view.ChessboardPoint;
import controller.WebClickController;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

/**
 * 这个类表示棋盘上的空位置
 */
public class WebEmptySlotComponent extends WebChessComponent {

    public WebEmptySlotComponent(ChessboardPoint chessboardPoint, Point location, WebClickController listener, int size) {
        super(chessboardPoint, location, ChessColor.NONE, listener, size);
        super.setChessName("_");
    }

    @Override
    public boolean canMoveTo(WebChessComponent[][] chessboard, ChessboardPoint destination, int step) {
        return false;
    }

    @Override
    public void loadResource() throws IOException {
        //No resource!
    }

    public ArrayList<ChessboardPoint> ChessCanMove(WebChessComponent[][] chessboard, int step){
        return new ArrayList<>();
    }
}

