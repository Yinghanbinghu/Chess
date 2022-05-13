package view;


import model.*;
import controller.ClickController;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 这个类表示面板上的棋盘组件对象
 */
public class Chessboard extends JComponent {
    /**
     * CHESSBOARD_SIZE： 棋盘是8 * 8的
     * <br>
     * BACKGROUND_COLORS: 棋盘的两种背景颜色
     * <br>
     * chessListener：棋盘监听棋子的行动
     * <br>
     * chessboard: 表示8 * 8的棋盘
     * <br>
     * currentColor: 当前行棋方
     */
    private static final int CHESSBOARD_SIZE = 8;
    private static int model = 1;

    private final ChessComponent[][] chessComponents = new ChessComponent[CHESSBOARD_SIZE][CHESSBOARD_SIZE];
    private ChessColor currentColor = ChessColor.WHITE;
    //all chessComponents in this chessboard are shared only one model controller  此棋盘中的所有棋子仅共享一个模型控制器
    private final ClickController clickController = new ClickController(this);
    private final int CHESS_SIZE;
    private int stepNum = 1;      //步数计算
    private Map<Integer, StringBuilder> map = new HashMap<>();
    private ArrayList<ChessboardPoint> currentCanMovePoint=null;

    private boolean ifHelp = true;

    public boolean isIfHelp() {
        return ifHelp;
    }

    public void setIfHelp() {
        this.ifHelp = !ifHelp;
    }

    public void stepCount(boolean b) {      //b留用于撤销
        if (b) {
            stepNum++;
        } else stepNum--;
    }


    public void helpCanMoveTo(ChessComponent first){          //显示移动位置
        if(first==null){
            currentCanMovePoint=null;
        }else currentCanMovePoint=first.ChessCanMove(chessComponents,stepNum);
        for (ChessComponent[] i:chessComponents) {
            for (ChessComponent j:i ) {
                j.setIfCanMoveOn(false);
            }
        }
        if (currentCanMovePoint!=null) {
            for (ChessboardPoint i : currentCanMovePoint) {
                chessComponents[i.getX()][i.getY()].setIfCanMoveOn(true);
            }
        }
        repaint();
    }

    public int getStepNum() {
        return stepNum;
    }

    public void initChessboard() {
        initiateEmptyChessboard();
        this.stepNum = 1;
        map = new HashMap<>();
        currentColor = ChessColor.WHITE;
        clickController.intiFirst();
        initRookOnBoard(0, 0, ChessColor.BLACK);
        initRookOnBoard(0, CHESSBOARD_SIZE - 1, ChessColor.BLACK);
        initRookOnBoard(CHESSBOARD_SIZE - 1, 0, ChessColor.WHITE);
        initRookOnBoard(CHESSBOARD_SIZE - 1, CHESSBOARD_SIZE - 1, ChessColor.WHITE);
        initQueenOnBoard(0, 3, ChessColor.BLACK);
        initQueenOnBoard(CHESSBOARD_SIZE - 1, 3, ChessColor.WHITE);
        initBishopOnBoard(0, 2, ChessColor.BLACK);
        initBishopOnBoard(0, 5, ChessColor.BLACK);
        initBishopOnBoard(CHESSBOARD_SIZE - 1, 2, ChessColor.WHITE);
        initBishopOnBoard(CHESSBOARD_SIZE - 1, 5, ChessColor.WHITE);
        for (int i = 0; i < CHESSBOARD_SIZE; i++) {
            initPawnOnBoard(CHESSBOARD_SIZE - 2, i, ChessColor.WHITE);
            initPawnOnBoard(1, i, ChessColor.BLACK);
        }
        initKnightOnBoard(0, 1, ChessColor.BLACK);
        initKnightOnBoard(0, CHESSBOARD_SIZE - 2, ChessColor.BLACK);
        initKnightOnBoard(CHESSBOARD_SIZE - 1, 1, ChessColor.WHITE);
        initKnightOnBoard(CHESSBOARD_SIZE - 1, CHESSBOARD_SIZE - 2, ChessColor.WHITE);
        initKingOnBoard(0, 4, ChessColor.BLACK);
        initKingOnBoard(CHESSBOARD_SIZE - 1, 4, ChessColor.WHITE);
        saveStep();
    }

    public Chessboard(int width, int height) {
        setLayout(null); // Use absolute layout.
        setSize(width, height);
        CHESS_SIZE = width / 8;
        System.out.printf("chessboard size = %d, chess size = %d\n", width, CHESS_SIZE);

        initiateEmptyChessboard();

        // FIXME: Initialize chessboard for testing only.
        if (model == 1) {
            initRookOnBoard(0, 0, ChessColor.BLACK);
            initRookOnBoard(0, CHESSBOARD_SIZE - 1, ChessColor.BLACK);
            initRookOnBoard(CHESSBOARD_SIZE - 1, 0, ChessColor.WHITE);
            initRookOnBoard(CHESSBOARD_SIZE - 1, CHESSBOARD_SIZE - 1, ChessColor.WHITE);
            initQueenOnBoard(0, 3, ChessColor.BLACK);
            initQueenOnBoard(CHESSBOARD_SIZE - 1, 3, ChessColor.WHITE);
            initBishopOnBoard(0, 2, ChessColor.BLACK);
            initBishopOnBoard(0, 5, ChessColor.BLACK);
            initBishopOnBoard(CHESSBOARD_SIZE - 1, 2, ChessColor.WHITE);
            initBishopOnBoard(CHESSBOARD_SIZE - 1, 5, ChessColor.WHITE);
            for (int i = 0; i < CHESSBOARD_SIZE; i++) {
                initPawnOnBoard(CHESSBOARD_SIZE - 2, i, ChessColor.WHITE);
                initPawnOnBoard(1, i, ChessColor.BLACK);
            }
            initKnightOnBoard(0, 1, ChessColor.BLACK);
            initKnightOnBoard(0, CHESSBOARD_SIZE - 2, ChessColor.BLACK);
            initKnightOnBoard(CHESSBOARD_SIZE - 1, 1, ChessColor.WHITE);
            initKnightOnBoard(CHESSBOARD_SIZE - 1, CHESSBOARD_SIZE - 2, ChessColor.WHITE);
            initKingOnBoard(0, 4, ChessColor.BLACK);
            initKingOnBoard(CHESSBOARD_SIZE - 1, 4, ChessColor.WHITE);
        }
        if (model == 2) {
            initPawnOnBoard(CHESSBOARD_SIZE - 2, 1, ChessColor.WHITE);
            initPawnOnBoard(1, 1, ChessColor.BLACK);

        }
        if (model == 3) {
            initQueenOnBoard(0, 3, ChessColor.BLACK);
            initQueenOnBoard(CHESSBOARD_SIZE - 1, 3, ChessColor.WHITE);
        }
        saveStep();
    }

    public ChessComponent[][] getChessComponents() {
        return chessComponents;
    }

    public ChessColor getCurrentColor() {
        return currentColor;
    }

    public void putChessOnBoard(ChessComponent chessComponent) {
        int row = chessComponent.getChessboardPoint().getX(), col = chessComponent.getChessboardPoint().getY();

        if (chessComponents[row][col] != null) {
            remove(chessComponents[row][col]);
        }
        add(chessComponents[row][col] = chessComponent);
    }

    public void swapChessComponents(ChessComponent chess1, ChessComponent chess2) {
        // Note that chess1 has higher priority, 'destroys' chess2 if exists.
        if (!(chess2 instanceof EmptySlotComponent)) {
            remove(chess2);
            add(chess2 = new EmptySlotComponent(chess2.getChessboardPoint(), chess2.getLocation(), clickController, CHESS_SIZE));
        }
        chess1.swapLocation(chess2);
        int row1 = chess1.getChessboardPoint().getX(), col1 = chess1.getChessboardPoint().getY();
        chessComponents[row1][col1] = chess1;
        int row2 = chess2.getChessboardPoint().getX(), col2 = chess2.getChessboardPoint().getY();
        chessComponents[row2][col2] = chess2;

        chess1.repaint();
        chess2.repaint();
    }

    public void removePassPawn(ChessComponent passPawn) {       //吃过路卒
        remove(passPawn);
        add(passPawn = new EmptySlotComponent(passPawn.getChessboardPoint(), passPawn.getLocation(), clickController, CHESS_SIZE));
    }

    public void upGratePawn(ChessComponent upGratePawn) {    //卒升变
        remove(upGratePawn);
        Object[] obj2 = {"后", "车", "马", "相"};
        String s = (String) JOptionPane.showInputDialog(null, "请选择你的升变类型:\n", "兵升变", JOptionPane.WARNING_MESSAGE, new ImageIcon("icon.png"), obj2, "后");
        switch (s) {
            case "后" -> {
                putChessOnBoard(upGratePawn = new QueenChessComponent(upGratePawn.getChessboardPoint(), upGratePawn.getLocation(), upGratePawn.getChessColor(), clickController, CHESS_SIZE));
                upGratePawn.repaint();
            }
            case "车" -> {
                putChessOnBoard(upGratePawn = new RookChessComponent(upGratePawn.getChessboardPoint(), upGratePawn.getLocation(), upGratePawn.getChessColor(), clickController, CHESS_SIZE));
                upGratePawn.repaint();
            }
            case "马" -> {
                putChessOnBoard(upGratePawn = new KnightChessComponent(upGratePawn.getChessboardPoint(), upGratePawn.getLocation(), upGratePawn.getChessColor(), clickController, CHESS_SIZE));
                upGratePawn.repaint();
            }
            default -> {
                putChessOnBoard(upGratePawn = new BishopChessComponent(upGratePawn.getChessboardPoint(), upGratePawn.getLocation(), upGratePawn.getChessColor(), clickController, CHESS_SIZE));
                upGratePawn.repaint();
            }
        }
    }


    public void loadCurrentGame(ChessColor currentColor, String currentChessBoard, int step) {
        this.currentColor = currentColor;
        stepNum = step;
        initiateEmptyChessboard();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                switch (currentChessBoard.charAt(9 * i + j)) {
                    case 'R':
                        initRookOnBoard(i, j, ChessColor.BLACK);
                        break;
                    case 'N':
                        initKnightOnBoard(i, j, ChessColor.BLACK);
                        break;
                    case 'B':
                        initBishopOnBoard(i, j, ChessColor.BLACK);
                        break;
                    case 'Q':
                        initQueenOnBoard(i, j, ChessColor.BLACK);
                        break;
                    case 'K':
                        initKingOnBoard(i, j, ChessColor.BLACK);
                        break;
                    case 'P':
                        initPawnOnBoard(i, j, ChessColor.BLACK);
                        break;
                    case 'r':
                        initRookOnBoard(i, j, ChessColor.WHITE);
                        break;
                    case 'n':
                        initKnightOnBoard(i, j, ChessColor.WHITE);
                        break;
                    case 'b':
                        initBishopOnBoard(i, j, ChessColor.WHITE);
                        break;
                    case 'q':
                        initQueenOnBoard(i, j, ChessColor.WHITE);
                        break;
                    case 'k':
                        initKingOnBoard(i, j, ChessColor.WHITE);
                        break;
                    case 'p':
                        initPawnOnBoard(i, j, ChessColor.WHITE);
                        break;
                }

            }
        }
        repaint();
    }

    public void initiateEmptyChessboard() {
        for (int i = 0; i < chessComponents.length; i++) {
            for (int j = 0; j < chessComponents[i].length; j++) {
                putChessOnBoard(new EmptySlotComponent(new ChessboardPoint(i, j), calculatePoint(i, j), clickController, CHESS_SIZE));
            }
        }
    }

    public void swapColor() {
        currentColor = currentColor == ChessColor.BLACK ? ChessColor.WHITE : ChessColor.BLACK;
    }

    private void initRookOnBoard(int row, int col, ChessColor color) {
        ChessComponent chessComponent = new RookChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }

    private void initQueenOnBoard(int row, int col, ChessColor color) {
        ChessComponent chessComponent = new QueenChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }

    private void initBishopOnBoard(int row, int col, ChessColor color) {
        ChessComponent chessComponent = new BishopChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }

    private void initPawnOnBoard(int row, int col, ChessColor color) {
        ChessComponent chessComponent = new PawnChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }

    private void initKnightOnBoard(int row, int col, ChessColor color) {
        ChessComponent chessComponent = new KnightChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }

    private void initKingOnBoard(int row, int col, ChessColor color) {
        ChessComponent chessComponent = new KingChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    }


    private Point calculatePoint(int row, int col) {
        return new Point(col * CHESS_SIZE, row * CHESS_SIZE);
    }

    public void regret(){
        if(stepNum<=1){
            JOptionPane.showMessageDialog(null, "初始步骤", "提示",JOptionPane.WARNING_MESSAGE);
        }else {
        Integer I=stepNum-1;
        ChessColor regretColor=null;
        if(currentColor==ChessColor.WHITE){
            regretColor=ChessColor.BLACK;
        }else if (currentColor==ChessColor.BLACK){
            regretColor=ChessColor.WHITE;
        }
        loadCurrentGame(regretColor,map.get(I).subSequence(2,73).toString(),stepNum-1);
        }
    }

    public boolean loadGame(File file) {
        Map<Integer, StringBuilder> map1 = new HashMap<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("./resource/save1.txt"))) {
            map1 = (Map<Integer, StringBuilder>) ois.readObject();
            System.out.println(map1);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "文件有改动", "提示",JOptionPane.WARNING_MESSAGE);
            return false;
        }
        StringBuilder sb = new StringBuilder();
        System.out.println("加载中");
        int endStep = map1.size();
        Integer I = 1;
        for (; I <= endStep; I++) {
            System.out.println(I);
            if (!map1.containsKey(I)) {
                JOptionPane.showMessageDialog(null, "含非法移动", "提示",JOptionPane.WARNING_MESSAGE);
                return false;
            } else {
                sb = map1.get(I);
                System.out.println(sb);
                if (sb.length() != 81) {
                    JOptionPane.showMessageDialog(null, "棋盘大小非法", "提示",JOptionPane.WARNING_MESSAGE);
                    return false;
                }
                if (sb.charAt(0) != 'w' && sb.charAt(0) != 'b') {
                    JOptionPane.showMessageDialog(null, "没有当前玩家", "提示",JOptionPane.WARNING_MESSAGE);
                    return false;
                }
                if (sb.charAt(1) != '\n') {
                    JOptionPane.showMessageDialog(null, "格式错误", "提示",JOptionPane.WARNING_MESSAGE);
                    return false;
                }
                char[] chessGroup = {'B', 'K', 'N', 'P', 'Q', 'R', '_', 'b', 'k', 'n', 'p', 'q', 'r'};
                for (int i = 1; i <= 8; i++) {
                    for (int j = -7 + i * 9; j <= i * 9; j++) {
                        boolean ifIsChess = false;
                        for (char k : chessGroup) {
                            if (k == sb.charAt(j)) ifIsChess = true;
                        }
                        if (!ifIsChess) {
                            JOptionPane.showMessageDialog(null, "棋子错误", "提示",JOptionPane.WARNING_MESSAGE);
                            return false;
                        }
                    }
                    if (sb.charAt(i * 9 + 1) != '\n') {
                        JOptionPane.showMessageDialog(null, "格式错误", "提示",JOptionPane.WARNING_MESSAGE);
                        return false;
                    }
                }
                if (!(sb.charAt(74) == 'S' && sb.charAt(75) == 't' && sb.charAt(76) == 'e' && sb.charAt(77) == 'p' && sb.charAt(78) == ':')) {
                    JOptionPane.showMessageDialog(null, "格式错误", "提示",JOptionPane.WARNING_MESSAGE);
                    return false;
                }
            }
        }
        System.out.println("加载结束");
        ChessColor loadColor = null;
        if(sb.charAt(0)=='w'){
            loadColor=ChessColor.WHITE;
        }else if (sb.charAt(0)=='b'){
            loadColor=ChessColor.BLACK;
        }
        loadCurrentGame(loadColor, (String) sb.subSequence(2,73),endStep);
        this.map=map1;
        JOptionPane.showMessageDialog(null, "加载成功", "提示",JOptionPane.WARNING_MESSAGE);
        return true;
    }

    public void saveStep() {
        StringBuilder sb = new StringBuilder();
        if (currentColor == ChessColor.WHITE) {
            sb.append("w" + "\n");
        } else sb.append("b" + "\n");
        for (ChessComponent[] i : chessComponents) {
            for (ChessComponent j : i) {
                sb.append(j.getChessName());
            }
            sb.append("\n");
        }
        sb.append("Step:" + stepNum + "\n");
        map.put(this.stepNum, sb);
        System.out.println(sb);
    }


    public void saveGame() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("./resource/save1.txt"));
             ObjectInputStream ois = new ObjectInputStream(new FileInputStream("./resource/save1.txt"))) {
            oos.writeObject(map);
            System.out.println(map);
        } catch (Exception ignored) {
        }
        /*try {
            File f=new File("./resource/save1.txt");
            System.out.println(f.exists());
            BufferedWriter bf = new BufferedWriter(new FileWriter(f));
            bf.write(String.valueOf(sb));
            bf.flush();
            bf.close();
        } catch (Exception ignored) {
        }*/
    }

    public boolean canMove() {
        for (ChessComponent[] x : chessComponents) {
            for (ChessComponent y : x) {
                if (y.getChessColor() == currentColor && y.ChessCanMove(chessComponents, getStepNum()).size() != 0) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean ifKingCanBeEat() {
        for (ChessComponent[] x : chessComponents) {
            for (ChessComponent y : x) {
                if (y.getChessColor() != currentColor && y.canEatKing(getStepNum(), chessComponents)) {
                    return true;
                }
            }
        }
        return false;
    }
}
