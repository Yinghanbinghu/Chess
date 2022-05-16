package controller;

import view.Chessboard;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class GameController {
    private Chessboard chessboard;

    public GameController(Chessboard chessboard) {
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
