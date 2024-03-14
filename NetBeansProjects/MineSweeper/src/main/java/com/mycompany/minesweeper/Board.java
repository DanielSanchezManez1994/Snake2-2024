/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.mycompany.minesweeper;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.LayoutManager;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.OverlayLayout;


/**
 *
 * @author danielsanchez
 */
public class Board extends javax.swing.JPanel {

    private int[][] logycalMatrix;
    private static Icon mineIcon = null;
    private MineButton[][] mineButtonMatrix;
    private MouseAdapter mouseAdapter = null;
    private JPanel[][] jPanelMatrix;
    private FlagPanelInterface flagPanelInterface;
    private TimerInterface timerInterface;
    private int counterWin;
    /**
     * Creates new form Board
     */
    public Board() {
        initComponents();
        int numRows = ConfigData.getInstance().getNumRows();
        int numCols = ConfigData.getInstance().getNumCols();
        setLayout(new GridLayout(numRows, numCols));

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
    }// </editor-fold>//GEN-END:initComponents

    public void initBoard() {

        if (mouseAdapter == null) {
            mouseAdapter = new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {

                    MineButton b = (MineButton) e.getComponent();

                    if (!e.isMetaDown()) {
                        timerInterface.start();
                        openAllButtons(b);
                        //checkCellMatrix(b.getRow(),b.getCol());
                    }
                }

            };
            initMatrix();

            initMineSweeperJpanels();

            printLogicMatrix();
        }
    }  

    

    private void openAllButtons(MineButton b) {

        if (b.getButtonState() == ButtonState.OPEN) {

            return;

        } else {
            if (b.getButtonState() == ButtonState.FLAG) {
                flagPanelInterface.incrementOne();
            }
            b.setButtonState(ButtonState.OPEN);
            b.setVisible(false);
            checkLose(b.getRow(),b.getCol());
            repaint();
            if (logycalMatrix[b.getRow()][b.getCol()] == 0) {
                for (int row = b.getRow() - 1; row <= b.getRow() + 1; row++) {
                    for (int col = b.getCol() - 1; col <= b.getCol() + 1; col++) {
                        if (isValid(row, col) && !b.equals(logycalMatrix[b.getRow()][b.getCol()])) {
                            openAllButtons(mineButtonMatrix[row][col]);
                        }
                    }
                }
            }

        }

    }

    public void initMineSweeperJpanels() {

        if (mineIcon == null) {
            Image image = new ImageIcon(getClass().getResource("/images/bomb.png")).getImage();
            Image newimg = image.getScaledInstance(ConfigData.getInstance().getButtonSize() - 2, ConfigData.getInstance().getButtonSize() - 2, java.awt.Image.SCALE_SMOOTH);
            mineIcon = new ImageIcon(newimg);
        }
        counterWin = 0;
        
        jPanelMatrix = new JPanel[ConfigData.getInstance().getNumRows()][ConfigData.getInstance().getNumCols()];

        mineButtonMatrix = new MineButton[ConfigData.getInstance().getNumRows()][ConfigData.getInstance().getNumCols()];
        for (int row = 0; row < ConfigData.getInstance().getNumRows(); row++) {
            for (int col = 0; col < ConfigData.getInstance().getNumCols(); col++) {

                JPanel panel = new JPanel();

                jPanelMatrix[row][col] = panel;

                Dimension dimension = new Dimension(ConfigData.getInstance().getButtonSize() - 2,
                        ConfigData.getInstance().getButtonSize() - 2);

                panel.setPreferredSize(dimension);

                LayoutManager mgr = new OverlayLayout(panel);
                panel.setLayout(mgr);

                JLabel label = new JLabel();

                MineButton b = new MineButton(row, col);

                b.addMouseListener(mouseAdapter);
                b.setFlagPanelInterface(flagPanelInterface);
                mineButtonMatrix[row][col] = b;

                // MODIFY THE NEXT METHOD IN ORDER TO MAKE IT CREATE A LABEL AND SET IT'S CONTENT IN THE CENTER AT THE SAME TIME
                setLabelContent(label, row, col);

                label.setHorizontalAlignment(JLabel.CENTER);

                panel.add(b);
                panel.add(label);

                add(panel);
            }
        }
    }

    public void initMatrix() {
        logycalMatrix = new int[ConfigData.getInstance().getNumRows()][ConfigData.getInstance().getNumCols()];
        logicMatrixToZero();
        initMines();
        for (int row = 0; row < ConfigData.getInstance().getNumRows(); row++) {
            for (int col = 0; col < ConfigData.getInstance().getNumCols(); col++) {
                if (logycalMatrix[row][col] != -1) {
                    logycalMatrix[row][col] = checkNumMines(row, col);
                }
            }
        }
    }

    private void initMines() {
        int count = 0;
        int ratio = ConfigData.getInstance().getNumRatio();
        int numRows = ConfigData.getInstance().getNumRows();
        int numCols = ConfigData.getInstance().getNumCols();
        int numMines = numRows * numCols * ratio / 100;

        while (count < numMines) {
            int row = (int) (Math.random() * numRows);
            int col = (int) (Math.random() * numCols);
            if (logycalMatrix[row][col] != -1) {
                logycalMatrix[row][col] = -1;
                count++;
            }

        }

    }

    private void logicMatrixToZero() {
        for (int row = 0; row < ConfigData.getInstance().getNumRows(); row++) {
            for (int col = 0; col < ConfigData.getInstance().getNumCols(); col++) {
                logycalMatrix[row][col] = 0;
            }
        }
    }

    private int checkNumMines(int row, int col) {
        int numMinesAround = 0;
        int minRow = row - 1;
        int minCol = col - 1;
        int maxRow = row + 1;
        int maxCol = col + 1;
        for (int i = minRow; i <= maxRow; i++) {
            for (int ii = minCol; ii <= maxCol; ii++) {
                if (isValid(i, ii)) {
                    if (logycalMatrix[i][ii] == -1) {
                        numMinesAround++;
                    }
                }

            }
        }
        return numMinesAround;
    }

    private boolean isValid(int row, int col) {
        return row >= 0 && col >= 0 && row < ConfigData.getInstance().getNumRows() && col < ConfigData.getInstance().getNumCols();
    }

    private void printLogicMatrix() {
        for (int row = 0; row < ConfigData.getInstance().getNumRows(); row++) {
            for (int col = 0; col < ConfigData.getInstance().getNumCols(); col++) {
                System.out.print(logycalMatrix[row][col] + "       ");
            }
            System.out.println("");
        }
    }

    public int getMatrixInfo(int row, int col) {
        return logycalMatrix[row][col];
    }

    public JPanel[][] getJPanelMatrix() {
        return jPanelMatrix;
    }

    public void deleteJpanels() {
        for (JPanel[] p1 : jPanelMatrix) {
            for (JPanel p2 : p1) {
                remove(p2);
            }
        }
    }

    private void setLabelContent(JLabel label, int row, int col) {
        int content;
        if ((content = getMatrixInfo(row, col)) == -1) {
            label.setIcon(mineIcon);
        } else if (content != 0) {
            label.setText("" + content);
        }
    }

    public void resetGame() {
        initMatrix();
        initMineSweeperJpanels();
    }

    public void renewLayout() {

        int numRows = ConfigData.getInstance().getNumRows();
        int numCols = ConfigData.getInstance().getNumCols();
        setLayout(new GridLayout(numRows, numCols));

    }

    public void setFlagPanelInterface(FlagPanelInterface flagPanelInterface) {
        this.flagPanelInterface = flagPanelInterface;
    }

    public void setTimerInterface(TimerInterface timerInterface) {
        this.timerInterface = timerInterface;

    }
    
    private void checkWin(int row, int col){
        int totalRows = ConfigData.getInstance().getNumRows();
        int totalCols = ConfigData.getInstance().getNumCols();
        
                if(mineButtonMatrix[row][col].getButtonState() == ButtonState.OPEN){
                    counterWin++;
                    if((totalCols * totalRows) == (counterWin + ConfigData.getInstance().getNumRatio())){
                        
                        processWinLoss(" You Win ");
                    }
                }
    }
    
    private void checkLose(int row, int col){
        if(logycalMatrix[row][col] == -1){
            openAllMines();
            processWinLoss("You Lose");
            checkWin(row,col);
        }
        
        
    }
    
    private void openAllMines(){
        int totalRows = ConfigData.getInstance().getNumRows();
        int totalCols = ConfigData.getInstance().getNumCols();
        for(int row = 0; row < totalRows ; row++){
            for(int col = 0; col < totalCols; col++){
                if(logycalMatrix[row][col] == -1){
                    mineButtonMatrix[row][col].setVisible(false);
                }
            }
        }
    }
    
    private void processWinLoss(String message){
        timerInterface.stop();
        JOptionPane.showMessageDialog(this, message);
        
        return;
    }
    
    
  //  private void checkNumFlags(){
  //     if(flagPanelInterface.getFlagCounter() == 0){
  //       System.out.println("Do you want to open all the cells?");
  //      }
  //  }
}


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
