import view.MainFrame;
import view.WebChessGameFrame;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainFrame mainFrame = new MainFrame(700, 394);
            mainFrame.setVisible(true);
        });
        /*SwingUtilities.invokeLater(() -> {
            WebChessGameFrame mainFrame = new WebChessGameFrame(1000, 760);
            mainFrame.setVisible(true);
        });
        SwingUtilities.invokeLater(() -> {
            WebChessGameFrame mainFrame = new WebChessGameFrame(1000, 760);
            mainFrame.setVisible(true);
        });
        SwingUtilities.invokeLater(() -> {
            WebChessGameFrame mainFrame = new WebChessGameFrame(1000, 760);
            mainFrame.setVisible(true);
        });*/
    }
}
