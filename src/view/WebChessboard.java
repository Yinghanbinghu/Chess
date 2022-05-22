package view;


import controller.WebTimeController;
import model.*;
import controller.WebClickController;
import web.WebListener;
import web.WebProxy;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.*;

/**
 * 这个类表示面板上的棋盘组件对象
 */
public class WebChessboard extends JComponent implements WebListener {
    public static final char INVITE_CONNECT = 'a', ACCEPT_INVITE = 'b', REJECT_INVITE = 'c', GET_ACCOUNT = 'd', DISCONNECT = 'e', GAME_ACTION = 'f', INVITE_REGRET = 'g', ACCEPT_REGRET='h', REJECT_REGRET='i', UP_GRATE_PAWN='j',ASK_WATCHING='k',NEXT_WATCHING_ADMISSION='l',ACCEPT_WATCHING='m',REJECT_WATCHING='n',LEAVE_WATCHING='o',ASK_NEW='p',ACCEPT_NEW='q',REJECT_NEW='r',TIME_OUT='s',GAME_OVER1='t',PREPARED='u',WATCHING_END='v',CONTINUE_WATCHING='w',WATCHING_RESET='x',GAME_OVER2='y',NOT_READY='z',ALREADY_IN_GAME='A';
    private JButton findPlayButton;
    private JLabel idLabel;
    private JButton regretButton;
    private JButton watchButton;
    private JButton quitButton;
    private JButton newGameButton;
    private WebProxy proxy;
    private WebTimeController time;
    public String myID, opponentID="还没有对手";
    private String action="";
    public ChessColor myColor, opponentColor;
    private ArrayList<String> watchers=new ArrayList<>();
    private String watching1="不在观战",watching2="不在观战";
    private boolean opponentReady=true;

    public void setOpponentReady(boolean opponentReady) {
        this.opponentReady = opponentReady;
    }
    public boolean isOpponentReady(){
        return opponentReady;
    }

    public WebTimeController getTime() {
        return time;
    }

    public WebProxy getProxy(){
        return proxy;
    }
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

    private final WebChessComponent[][] chessComponents = new WebChessComponent[CHESSBOARD_SIZE][CHESSBOARD_SIZE];
    private ChessColor currentColor = ChessColor.WHITE;
    //all chessComponents in this chessboard are shared only one model controller  此棋盘中的所有棋子仅共享一个模型控制器
    private final WebClickController clickController = new WebClickController(this);
    private final int CHESS_SIZE;
    private int stepNum = 1;      //步数计算
    private Map<Integer, StringBuilder> map = new HashMap<>();
    private ArrayList<ChessboardPoint> currentCanMovePoint = null;


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

    public ArrayList<String> getWatchers() {
        return watchers;
    }

    public void helpCanMoveTo(WebChessComponent first) {          //显示移动位置
        if (first == null) {
            currentCanMovePoint = null;
        } else currentCanMovePoint = first.ChessCanMove(chessComponents, stepNum);
        for (WebChessComponent[] i : chessComponents) {
            for (WebChessComponent j : i) {
                j.setIfCanMoveOn(false);
            }
        }
        if (currentCanMovePoint != null) {
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

    public WebChessboard(int width, int height, JButton connectButton, JLabel idLabel, JButton regretButton, JButton watchButton, JButton quitButton, JButton newGameButton, WebTimeController time) {

        this.idLabel=idLabel;
        this.time=time;
        time.setShow(false);
        proxy = new WebProxy(this,"103.46.128.49",37856);

        findPlayButton=connectButton;
        this.regretButton=regretButton;
        this.watchButton=watchButton;
        this.quitButton=quitButton;
        this.newGameButton=newGameButton;

        setLayout(null); // Use absolute layout.
        setSize(width, height);
        CHESS_SIZE = width / 8;
        System.out.printf("chessboard size = %d, chess size = %d\n", width, CHESS_SIZE);

        initiateEmptyChessboard();

        // FIXME: Initialize chessboard for testing only.
        setMyConnectButton(findPlayButton);
        setMyRegretButton(this.regretButton);
        setMyWatchButton(this.watchButton);
        setMyQuitButton(this.quitButton);
        setMyNewGameButton(this.newGameButton);
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

    public WebChessComponent[][] getChessComponents() {
        return chessComponents;
    }

    public WebClickController getClickController() {
        return clickController;
    }

    public ChessColor getCurrentColor() {
        return currentColor;
    }

    public void putChessOnBoard(WebChessComponent chessComponent) {
        int row = chessComponent.getChessboardPoint().getX(), col = chessComponent.getChessboardPoint().getY();

        if (chessComponents[row][col] != null) {
            remove(chessComponents[row][col]);
        }
        add(chessComponents[row][col] = chessComponent);
    }

    public void swapChessComponents(WebChessComponent chess1, WebChessComponent chess2) {
        // Note that chess1 has higher priority, 'destroys' chess2 if exists.
        if (!(chess2 instanceof WebEmptySlotComponent)) {
            remove(chess2);
            add(chess2 = new WebEmptySlotComponent(chess2.getChessboardPoint(), chess2.getLocation(), clickController, CHESS_SIZE));
        }
        chess1.swapLocation(chess2);
        int row1 = chess1.getChessboardPoint().getX(), col1 = chess1.getChessboardPoint().getY();
        chessComponents[row1][col1] = chess1;
        int row2 = chess2.getChessboardPoint().getX(), col2 = chess2.getChessboardPoint().getY();
        chessComponents[row2][col2] = chess2;

        chess1.repaint();
        chess2.repaint();
    }

    public void removePassPawn(WebChessComponent passPawn) {       //吃过路卒
        remove(passPawn);
        add(passPawn = new WebEmptySlotComponent(passPawn.getChessboardPoint(), passPawn.getLocation(), clickController, CHESS_SIZE));
        chessComponents[passPawn.getChessboardPoint().getX()][passPawn.getChessboardPoint().getY()] = passPawn;
        passPawn.repaint();
    }

    public void upGratePawn(WebChessComponent upGratePawn, char type) {    //卒升变
        remove(upGratePawn);
        if (upGratePawn.getChessColor()==myColor) {
            Object[] obj2 = {"后", "车", "马", "相"};
            String s = (String) JOptionPane.showInputDialog(null, "请选择你的升变类型:\n", "兵升变", JOptionPane.WARNING_MESSAGE, new ImageIcon("icon.png"), obj2, "后");
            switch (s) {
                case "后":
                    putChessOnBoard(upGratePawn = new WebQueenChessComponent(upGratePawn.getChessboardPoint(), upGratePawn.getLocation(), upGratePawn.getChessColor(), clickController, CHESS_SIZE));
                    upGratePawn.repaint();
                    proxy.send(opponentID+"#"+UP_GRATE_PAWN+upGratePawn.getChessboardPoint().getX()+upGratePawn.getChessboardPoint().getY()+0);
                    try {
                        Thread.sleep(20);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    for (int i=0;i<watchers.size();i++){
                        proxy.send(watchers.get(i)+"#"+UP_GRATE_PAWN+upGratePawn.getChessboardPoint().getX()+upGratePawn.getChessboardPoint().getY()+0);
                        try {
                            Thread.sleep(20);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case "车":
                    putChessOnBoard(upGratePawn = new WebRookChessComponent(upGratePawn.getChessboardPoint(), upGratePawn.getLocation(), upGratePawn.getChessColor(), clickController, CHESS_SIZE));
                    upGratePawn.repaint();
                    proxy.send(opponentID+"#"+UP_GRATE_PAWN+upGratePawn.getChessboardPoint().getX()+upGratePawn.getChessboardPoint().getY()+1);
                    try {
                        Thread.sleep(20);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    for (int i=0;i<watchers.size();i++){
                        proxy.send(watchers.get(i)+"#"+UP_GRATE_PAWN+upGratePawn.getChessboardPoint().getX()+upGratePawn.getChessboardPoint().getY()+1);
                        try {
                            Thread.sleep(20);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case "马":
                    putChessOnBoard(upGratePawn = new WebKnightChessComponent(upGratePawn.getChessboardPoint(), upGratePawn.getLocation(), upGratePawn.getChessColor(), clickController, CHESS_SIZE));
                    upGratePawn.repaint();
                    proxy.send(opponentID+"#"+UP_GRATE_PAWN+upGratePawn.getChessboardPoint().getX()+upGratePawn.getChessboardPoint().getY()+2);
                    try {
                        Thread.sleep(20);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    for (int i=0;i<watchers.size();i++){
                        proxy.send(watchers.get(i)+"#"+UP_GRATE_PAWN+upGratePawn.getChessboardPoint().getX()+upGratePawn.getChessboardPoint().getY()+2);
                        try {
                            Thread.sleep(20);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                default:
                    putChessOnBoard(upGratePawn = new WebBishopChessComponent(upGratePawn.getChessboardPoint(), upGratePawn.getLocation(), upGratePawn.getChessColor(), clickController, CHESS_SIZE));
                    upGratePawn.repaint();
                    proxy.send(opponentID+"#"+UP_GRATE_PAWN+upGratePawn.getChessboardPoint().getX()+upGratePawn.getChessboardPoint().getY()+3);
                    try {
                        Thread.sleep(20);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    for (int i=0;i<watchers.size();i++){
                        proxy.send(watchers.get(i)+"#"+UP_GRATE_PAWN+upGratePawn.getChessboardPoint().getX()+upGratePawn.getChessboardPoint().getY()+3);
                        try {
                            Thread.sleep(20);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
            }
        } else {
            switch (type){
                case '0':
                    putChessOnBoard(upGratePawn = new WebQueenChessComponent(upGratePawn.getChessboardPoint(), upGratePawn.getLocation(), upGratePawn.getChessColor(), clickController, CHESS_SIZE));
                    upGratePawn.repaint();
                    break;
                case '1':
                    putChessOnBoard(upGratePawn = new WebRookChessComponent(upGratePawn.getChessboardPoint(), upGratePawn.getLocation(), upGratePawn.getChessColor(), clickController, CHESS_SIZE));
                    upGratePawn.repaint();
                    break;
                case '2':
                    putChessOnBoard(upGratePawn = new WebKnightChessComponent(upGratePawn.getChessboardPoint(), upGratePawn.getLocation(), upGratePawn.getChessColor(), clickController, CHESS_SIZE));
                    upGratePawn.repaint();
                    break;
                case '3':
                    putChessOnBoard(upGratePawn = new WebBishopChessComponent(upGratePawn.getChessboardPoint(), upGratePawn.getLocation(), upGratePawn.getChessColor(), clickController, CHESS_SIZE));
                    upGratePawn.repaint();
                    break;
            }
        }
    }


    public void loadCurrentGame(ChessColor currentColor, String currentChessBoard, int step) {
        this.currentColor = currentColor;
        stepNum = step;
        initiateEmptyChessboard();
        clickController.intiFirst();
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
                putChessOnBoard(new WebEmptySlotComponent(new ChessboardPoint(i, j), calculatePoint(i, j), clickController, CHESS_SIZE));
            }
        }
    }

    public void swapColor() {
        currentColor = currentColor == ChessColor.BLACK ? ChessColor.WHITE : ChessColor.BLACK;
        time.restart();
    }

    private void initRookOnBoard(int row, int col, ChessColor color) {
        WebChessComponent chessComponent = new WebRookChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }

    private void initQueenOnBoard(int row, int col, ChessColor color) {
        WebChessComponent chessComponent = new WebQueenChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }

    private void initBishopOnBoard(int row, int col, ChessColor color) {
        WebChessComponent chessComponent = new WebBishopChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }

    private void initPawnOnBoard(int row, int col, ChessColor color) {
        WebChessComponent chessComponent = new WebPawnChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }

    private void initKnightOnBoard(int row, int col, ChessColor color) {
        WebChessComponent chessComponent = new WebKnightChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }

    private void initKingOnBoard(int row, int col, ChessColor color) {
        WebChessComponent chessComponent = new WebKingChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE);
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

    public void regret() {
        if (stepNum <= 1) {
            JOptionPane.showMessageDialog(null, "初始步骤", "提示", JOptionPane.WARNING_MESSAGE);
        } else {
            Integer I = stepNum - 1;
            ChessColor regretColor = null;
            regretColor= currentColor==ChessColor.WHITE? ChessColor.BLACK:ChessColor.WHITE;
            loadCurrentGame(regretColor, map.get(I).subSequence(2, 73).toString(), stepNum - 1);
        }
        time.restart();
    }

    public boolean loadGame(File file) {
        Map<Integer, StringBuilder> map1 = new HashMap<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            map1 = (Map<Integer, StringBuilder>) ois.readObject();
            System.out.println(map1);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "文件有改动", "提示", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        StringBuilder sb = new StringBuilder();
        System.out.println("加载中");
        int endStep = map1.size();
        Integer I = 1;
        for (; I <= endStep; I++) {
            System.out.println(I);
            if (!map1.containsKey(I)) {
                JOptionPane.showMessageDialog(null, "105", "含非法移动", JOptionPane.WARNING_MESSAGE);
                return false;
            } else {
                sb = map1.get(I);
                System.out.println(sb);
                if (sb.charAt(0) != 'w' && sb.charAt(0) != 'b') {
                    JOptionPane.showMessageDialog(null, "103", "无行棋方", JOptionPane.WARNING_MESSAGE);
                    return false;
                }
                if (!(sb.charAt(sb.length()-7) == 'S' && sb.charAt(sb.length()-6) == 't' && sb.charAt(sb.length()-5) == 'e' && sb.charAt(sb.length()-4) == 'p' && sb.charAt(sb.length()-3) == ':')) {
                    JOptionPane.showMessageDialog(null, "106", "缺少当前步", JOptionPane.WARNING_MESSAGE);
                    return false;
                }
                if (sb.charAt(1) != '\n') {
                    JOptionPane.showMessageDialog(null, "101", "棋盘并非8*8", JOptionPane.WARNING_MESSAGE);
                    return false;
                }
                if (sb.length() != 81) {
                    JOptionPane.showMessageDialog(null, "101", "棋盘并非8*8", JOptionPane.WARNING_MESSAGE);
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
                            JOptionPane.showMessageDialog(null, "102", "棋子并非六种之一，棋子并非黑白棋子", JOptionPane.WARNING_MESSAGE);
                            return false;
                        }
                    }
                    if (sb.charAt(i * 9 + 1) != '\n') {
                        JOptionPane.showMessageDialog(null, "101", "棋盘并非8*8", JOptionPane.WARNING_MESSAGE);
                        return false;
                    }
                }
            }
        }
        System.out.println("加载结束");
        ChessColor loadColor = null;
        if (sb.charAt(0) == 'w') {
            loadColor = ChessColor.WHITE;
        } else if (sb.charAt(0) == 'b') {
            loadColor = ChessColor.BLACK;
        }
        loadCurrentGame(loadColor, (String) sb.subSequence(2, 73), endStep);
        this.map = map1;
        JOptionPane.showMessageDialog(null, "加载成功", "提示", JOptionPane.WARNING_MESSAGE);
        return true;
    }

    public void saveStep() {
        StringBuilder sb = new StringBuilder();
        if (currentColor == ChessColor.WHITE) {
            sb.append("w" + "\n");
        } else sb.append("b" + "\n");
        for (WebChessComponent[] i : chessComponents) {
            for (WebChessComponent j : i) {
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
             ) {
            oos.writeObject(map);
            System.out.println(map);
            JOptionPane.showMessageDialog(null, "保存成功", "提示", JOptionPane.WARNING_MESSAGE);
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
        for (WebChessComponent[] x : chessComponents) {
            for (WebChessComponent y : x) {
                if (y.getChessColor() == currentColor && y.ChessCanMove(chessComponents, getStepNum()).size() != 0) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean ifKingCanBeEat() {
        boolean b = false;
        for (WebChessComponent[] x : chessComponents) {
            for (WebChessComponent y : x) {
                if (y.getChessColor() != currentColor && y.canEatKing(getStepNum(), chessComponents)) {
                    y.setIfCanEatKing(true);
                    b = true;
                } else y.setIfCanEatKing(false);
            }
        }
        return b;
    }

    public void playback(){
        while (stepNum>1){
            regret();
            repaint();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void gettingAction(String message) {
        System.out.println(message);
        myAction(message.charAt(0), message.substring(1));
    }

    private void myAction(char flag, String message) {
        switch (flag) {
            case INVITE_CONNECT:
            if (opponentID.equals("还没有对手")){
                int a = (int) JOptionPane.showConfirmDialog(null, "连接", "有人想要连接你，它的ID是：" + message, 1);
                if (a == 0) {
                    this.initChessboard();
                    opponentID = message;
                    Random random = new Random();
                    int color1 = random.nextInt(2);
                    proxy.send(message + "#" + ACCEPT_INVITE + myID + String.format("%d", color1));
                    if (color1 == 0) {
                        myColor = ChessColor.WHITE;
                        opponentColor = ChessColor.BLACK;
                        JOptionPane.showMessageDialog(null, "你同意了连接，你随机到的颜色是白");
                    } else {
                        myColor = ChessColor.BLACK;
                        opponentColor = ChessColor.WHITE;
                        JOptionPane.showMessageDialog(null, "你同意了连接，你随机到的颜色是黑");
                    }
                    time.setShow(true);
                } else {
                    proxy.send(message + "#" + REJECT_INVITE);
                }
            }else {
                proxy.send(message+"#"+ALREADY_IN_GAME);
            }
                break;
            case ACCEPT_INVITE:
                this.initChessboard();
                int color1=(int)message.charAt(message.length()-1)-48;
                opponentID = message.substring(0,message.length()-1);
                if (color1==0) {
                    myColor=ChessColor.BLACK;opponentColor=ChessColor.WHITE;
                    JOptionPane.showMessageDialog(null, "游戏开始，你随机到的颜色是黑");
                } else {
                    myColor=ChessColor.WHITE;opponentColor=ChessColor.BLACK;
                    JOptionPane.showMessageDialog(null, "游戏开始，你随机到的颜色是白");
                }
                time.setShow(true);
                break;
            case REJECT_INVITE:
                JOptionPane.showMessageDialog(null, "对方拒绝了您");
                break;
            case GET_ACCOUNT:
                myID = message;
                idLabel.setText("你的ID是："+myID);
                idLabel.setFont(new Font("方正舒体", Font.BOLD, 20));
                break;
            case DISCONNECT:
                JOptionPane.showMessageDialog(null,"对手跑了");
                opponentID = "还没有对手";
                time.setShow(false);
                break;
            case GAME_ACTION:
                webClick(message);
                break;
            case INVITE_REGRET:
                int b = (int)JOptionPane.showConfirmDialog(null,"对方申请悔棋", "悔棋",1);
                if(b == 0){
                    proxy.send(opponentID + "#" + ACCEPT_REGRET);
                    try {
                        Thread.sleep(20);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    for (int i=0;i<watchers.size();i++){
                        proxy.send(watchers.get(i) + "#" + ACCEPT_REGRET);
                        try {
                            Thread.sleep(20);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    this.regret();
                    this.regret();
                }else{
                    proxy.send(opponentID + "#" + REJECT_REGRET);
                }
                break;
            case ACCEPT_REGRET:
                this.regret();
                this.regret();
                break;
            case REJECT_REGRET:
                JOptionPane.showMessageDialog(null,"对手拒绝了悔棋");
                break;
            case UP_GRATE_PAWN:
                this.upGratePawn(chessComponents[Integer.parseInt(String.valueOf(message.charAt(0)))][Integer.parseInt(String.valueOf(message.charAt(1)))],message.charAt(2));
                break;
            case ASK_WATCHING:
                int c = (int)JOptionPane.showConfirmDialog(null,"ID"+message+"申请观战", "观战",1);
                if(c == 0){
                    proxy.send(opponentID + "#" + NEXT_WATCHING_ADMISSION + message);
                    StringBuilder sb=new StringBuilder();
                    for (WebChessComponent[] i : chessComponents) {
                        for (WebChessComponent j : i) {
                            sb.append(j.getChessName());
                        }
                        sb.append("\n");
                    }
                    String chessBoard=sb.toString();
                    loadCurrentGame(currentColor,chessBoard,stepNum);
                    watchers.add(message);
                }else{
                    proxy.send(message + "#" + REJECT_WATCHING);
                }
                break;
            case NEXT_WATCHING_ADMISSION:
                int d = (int)JOptionPane.showConfirmDialog(null,"ID"+message+"申请观战", "观战",1);
                if(d == 0){
                    String color;
                    if (currentColor == ChessColor.WHITE) {
                        color="w";
                    } else {
                        color="b";
                    }
                    StringBuilder sb=new StringBuilder();
                    for (WebChessComponent[] i : chessComponents) {
                        for (WebChessComponent j : i) {
                            sb.append(j.getChessName());
                        }
                        sb.append("\n");
                    }
                    String chessBoard=sb.toString();
                    proxy.send(message + "#" + ACCEPT_WATCHING + color +"/"+chessBoard+"/"+ stepNum+"/"+myID);
                    loadCurrentGame(currentColor,chessBoard,stepNum);
                    watchers.add(message);
                }else{
                    proxy.send(message + "#" + REJECT_WATCHING);
                }
                break;
            case REJECT_WATCHING:
                JOptionPane.showMessageDialog(null,"对方拒绝了观战");
                watching1="不在观战";watching2="不在观战";
                break;
            case ACCEPT_WATCHING:
                System.out.println(message);
                String[] list=message.split("/");
                ChessColor color;
                if (list[0].equals("w")) {color=ChessColor.WHITE;}
                else {color=ChessColor.BLACK;}
                loadCurrentGame(color,list[1],Integer.parseInt(list[2]));
                watching2=list[3];
            case LEAVE_WATCHING:
                watchers.remove(message);
                break;
            case ASK_NEW:
                int e = (int)JOptionPane.showConfirmDialog(null,"对方申请求和", "求和",1);
                if(e == 0){
                    JOptionPane.showMessageDialog(null,"和棋");
                    proxy.send(opponentID + "#" + ACCEPT_NEW);
                    try {
                        Thread.sleep(20);
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                    for (int i=0;i<watchers.size();i++){
                        proxy.send(watchers.get(i) + "#" + ACCEPT_NEW);
                        try {
                            Thread.sleep(20);
                        } catch (InterruptedException e1) {
                            e1.printStackTrace();
                        }
                    }
                } else {
                    proxy.send(opponentID+"#"+REJECT_NEW);
                }
                break;
            case ACCEPT_NEW:
                time.setShow(false);
                int n2 = JOptionPane.showConfirmDialog(null, "是否继续观战?", "和棋", JOptionPane.YES_NO_OPTION);
                if (n2==1) {
                    proxy.send(watching1+"#"+CONTINUE_WATCHING+myID);
                } else{
                    watching1 = "不在观战";
                    watching2 = "不在观战";
                    proxy.send(watching1 + "#" + LEAVE_WATCHING + myID);
                    proxy.send(watching2 + "#" + LEAVE_WATCHING + myID);
                }
                break;
            case REJECT_NEW:
                JOptionPane.showMessageDialog(null,"对方拒绝了和棋");
                break;
            case TIME_OUT:
                this.swapColor();
                break;
            case GAME_OVER1:
                int n;
                if (message.equals("0")){
                    n = JOptionPane.showConfirmDialog(null, "是否继续观战?", "白方获胜", JOptionPane.YES_NO_OPTION);
                }else {
                    n = JOptionPane.showConfirmDialog(null, "是否继续观战?", "白方获胜", JOptionPane.YES_NO_OPTION);
                }
                if (n==1) {
                    proxy.send(watching1+"#"+CONTINUE_WATCHING+myID);
                } else{
                    watching1 = "不在观战";
                    watching2 = "不在观战";
                    proxy.send(watching1 + "#" + LEAVE_WATCHING + myID);
                    proxy.send(watching2 + "#" + LEAVE_WATCHING + myID);
                }
                break;
            case PREPARED:
                setOpponentReady(true);
                break;
            case WATCHING_END:
                watching1 = "不在观战";
                watching2 = "不在观战";
                JOptionPane.showMessageDialog(null,"对方结束了对局");
                break;
            case CONTINUE_WATCHING:
                if (!isOpponentReady()){
                    proxy.send(message+"#"+NOT_READY);
                } else {
                    String color2;
                    if (currentColor == ChessColor.WHITE) {
                        color2 = "w";
                    } else {
                        color2 = "b";
                    }
                    StringBuilder sb1 = new StringBuilder();
                    for (WebChessComponent[] i : chessComponents) {
                        for (WebChessComponent j : i) {
                            sb1.append(j.getChessName());
                        }
                        sb1.append("\n");
                    }
                    String chessBoard1 = sb1.toString();
                    proxy.send(message + "#" + ACCEPT_WATCHING + color2 + "/" + chessBoard1 + "/" + stepNum + "/" + myID);
                    proxy.send(opponentID + "#" + WATCHING_RESET);
                    loadCurrentGame(currentColor, chessBoard1, stepNum);
                }
                break;
            case WATCHING_RESET:
                StringBuilder sb2=new StringBuilder();
                for (WebChessComponent[] i : chessComponents) {
                    for (WebChessComponent j : i) {
                        sb2.append(j.getChessName());
                    }
                    sb2.append("\n");
                }
                String chessBoard2=sb2.toString();
                loadCurrentGame(currentColor,chessBoard2,stepNum);
                break;
            case GAME_OVER2:
                time.setShow(false);
                int n1 = JOptionPane.showConfirmDialog(null, "是否继续观战?", "无子可动和棋", JOptionPane.YES_NO_OPTION);
                if (n1==1) {
                    proxy.send(watching1+"#"+CONTINUE_WATCHING+myID);
                } else{
                    watching1 = "不在观战";
                    watching2 = "不在观战";
                    proxy.send(watching1 + "#" + LEAVE_WATCHING + myID);
                    proxy.send(watching2 + "#" + LEAVE_WATCHING + myID);
                }
                break;
            case NOT_READY:
                int n3 = JOptionPane.showConfirmDialog(null, "是否继续观战?", null, JOptionPane.YES_NO_OPTION);
                if (n3==1) {
                    proxy.send(watching1+"#"+CONTINUE_WATCHING+myID);
                } else{
                    watching1 = "不在观战";
                    watching2 = "不在观战";
                    proxy.send(watching1 + "#" + LEAVE_WATCHING + myID);
                    proxy.send(watching2 + "#" + LEAVE_WATCHING + myID);
                }
                break;
            case ALREADY_IN_GAME:
                JOptionPane.showMessageDialog(null,"对方已经在对局中了");
                break;
        }
    }

    public void webClick(String s){
        this.clickController.webOnClick(chessComponents[(int)s.charAt(0)-(int)'0'][(int)s.charAt(1)-(int)'0']);
    }

    @Override
    public void showError() {
        JOptionPane.showMessageDialog(null,"网络错误！");
    }

    private void setMyConnectButton(JButton button){

            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (opponentID.equals("还没有对手")) {
                        String str = (String) JOptionPane.showInputDialog(null, "输入对手ID");
                        if (str == null || str.isEmpty()) return;
                        try {
                            int a = Integer.parseInt(str);
                            proxy.send(str + "#" + INVITE_CONNECT + myID);
                        } catch (NumberFormatException e2) {
                            JOptionPane.showMessageDialog(null, "您的输入不合法");
                        }
                    }
                }
            });

    }

    private void setMyNewGameButton(JButton button){

            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (!opponentID.equals("还没有对手")) {
                        int a = (int) JOptionPane.showConfirmDialog(null, "申请求和？", "求和", 1);
                        if (a == 0) {
                            try {
                                proxy.send(opponentID + "#" + ASK_NEW);
                            } catch (NumberFormatException e2) {
                                e2.printStackTrace();
                                JOptionPane.showMessageDialog(null, "求和失效");
                            }
                        }
                    }
                }
            });

    }

    private void setMyQuitButton(JButton button){

            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (!opponentID.equals("还没有对手")) {
                        int a = (int) JOptionPane.showConfirmDialog(null, "你确定要逃跑吗", "退出", 1);
                        if (a == 0) {
                            proxy.send(opponentID + "#" + DISCONNECT);
                            opponentID = "还没有对手";
                        }
                    } else {
                        int a = (int) JOptionPane.showConfirmDialog(null, "你确定要退出吗", "退出", 1);
                        if (a == 0) {
                            opponentID = "还没有对手";
                        }
                    }
                }
            });
    }

    private void setMyRegretButton(JButton button){
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentColor==myColor) {
                    try {
                        proxy.send(opponentID + "#" + INVITE_REGRET);
                    } catch (NumberFormatException e2) {
                        JOptionPane.showMessageDialog(null, "您的输入不合法");
                    }
                }
            }
        });
    }

    private void setMyWatchButton(JButton button) {

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (opponentID.equals("还没有对手")) {
                    if (watching1.equals("不在观战") && watching2.equals("不在观战")) {
                        String str = (String) JOptionPane.showInputDialog(null, "输入观战ID");
                        if (str == null || str.isEmpty()) return;
                        try {
                            int a = Integer.parseInt(str);
                            watching1 = str;
                            proxy.send(str + "#" + ASK_WATCHING + myID);
                        } catch (NumberFormatException e2) {
                            JOptionPane.showMessageDialog(null, "您的输入不合法");
                        }
                    } else {
                        proxy.send(watching1 + "#" + LEAVE_WATCHING + myID);
                        proxy.send(watching2 + "#" + LEAVE_WATCHING + myID);
                        watching1 = "不在观战";
                        watching2 = "不在观战";
                        String str = (String) JOptionPane.showInputDialog(null, "输入观战ID");
                        if (str == null || str.isEmpty()) return;
                        try {
                            int a = Integer.parseInt(str);
                            watching1 = str;
                            proxy.send(str + "#" + ASK_WATCHING + myID);
                        } catch (NumberFormatException e2) {
                            JOptionPane.showMessageDialog(null, "您的输入不合法");
                        }
                    }
                }
            }
        });
    }
}

