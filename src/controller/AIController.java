package controller;

import model.ChessColor;
import model.ChessComponent;
import view.Chessboard;
import view.ChessboardPoint;

import java.util.ArrayList;
import java.util.Random;

public class AIController {
    Chessboard chessboard;
    Chessboard AiChessboard;
    ChessComponent[][] chessComponents;

    public AIController(Chessboard chessboard) {
        this.chessboard=chessboard;
    }

    public void setChessboard(Chessboard chessboard) {
        this.chessboard = chessboard;
        this.chessComponents=chessboard.getChessComponents();
    }

    public void random(ChessColor chessColor) {
        int totalColorChess = 0;
        for (ChessComponent[] i : chessComponents) {
            for (ChessComponent j : i) {
                if (j.getChessColor()==chessColor) totalColorChess++;
            }
        }

        Loop:while (true){
            Random r=new Random();
            int a=r.nextInt(totalColorChess-1)+1;
            int b=1;
            for (ChessComponent[] i : chessComponents) {
                for (ChessComponent j : i) {
                    if (j.getChessColor()==chessColor) {
                        if (totalColorChess!=a)b++;
                        else {
                            ArrayList<ChessboardPoint> c=j.ChessCanMove(chessComponents,chessboard.getStepNum());
                            if (c.size()==0) continue Loop;
                            else{
                                chessboard.getClickController().onClick(j);
                                int d=r.nextInt(c.size()-1)+1;
                                int e=1;
                                for (ChessboardPoint f:c        ) {
                                    if(e!=d) e++;
                                    else chessboard.getClickController().onClick(chessComponents[f.getX()][f.getY()]);
                                }
                            }
                            break ;
                        }
                    }
                }
            }
        }
    }
}
