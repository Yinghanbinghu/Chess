package controller;

import view.WebChessboard;

import java.io.File;

public class WebGameController {
    private WebChessboard chessboard;

    public WebGameController(WebChessboard chessboard) {
        this.chessboard = chessboard;
    }

    public void setHelpModel(){
        chessboard.setIfHelp();
    }

    public boolean loadGameFromFile(File f) {
       return chessboard.loadGame(f);
    }
    public boolean getHelpModel(){
        return chessboard.isIfHelp();
    }
    public void saveGame(){
        chessboard.saveGame();
    }
    public void newGame(){
        chessboard.initChessboard();
        chessboard.repaint();
    }
    public void regret(){
        chessboard.regret();
    }
    public void playback(){
        chessboard.playback();
    }

}
