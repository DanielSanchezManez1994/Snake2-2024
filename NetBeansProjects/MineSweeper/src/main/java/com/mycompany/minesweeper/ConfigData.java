/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Singleton.java to edit this template
 */
package com.mycompany.minesweeper;

import static com.mycompany.minesweeper.LevelType.BEGINNER;
import static com.mycompany.minesweeper.LevelType.DIFFYCULT;
import static com.mycompany.minesweeper.LevelType.INTERMEDIATE;
import java.awt.Image;
import javax.swing.ImageIcon;

/**
 *
 * @author danielsanchez
 */
public class ConfigData {

    public static final int NUM_ROWS_BEGINNER = 10;
    public static final int NUM_COLS_BEGINNER = 10;
    public static final int RATIO_BEGINNER = 10; // 10%
    public static final int NUM_ROWS_INTERMEDIATE = 16;
    public static final int NUM_COLS_INTERMEDIATE = 16;
    public static final int RATIO_INTERMEDIATE = 20; // 15%
    public static final int NUM_ROWS_HARD = 25;
    public static final int NUM_COLS_HARD = 25;
    public static final int RATIO_HARD = 50; // 25%
    public  final Image BUTON_IMAGE = new ImageIcon(getClass().getResource("/images/boton.jpg")).getImage();
    public  final Image FLAG_IMAGE = new ImageIcon(getClass().getResource("/images/flag.png")).getImage();
    public  final Image QUESTION_IMAGE = new ImageIcon(getClass().getResource("/images/question.png")).getImage();
    public static int numFlags;

    private static ConfigData instance;
    private LevelType level;
    private static final int BUTTON_SIZE = 30;

    private ConfigData() {
        level = LevelType.BEGINNER;
    }

    public static ConfigData getInstance() {
        if (instance == null) {
            instance = new ConfigData();
        }
        return instance;
    }

    public void setLevel(LevelType level){
        this.level = level;
    }
    
    public LevelType getLevelType(){
        return this.level;
    }
    
    public int getNumRows() {
        switch (level) {
            case BEGINNER:
                return NUM_ROWS_BEGINNER;
            case INTERMEDIATE:
                return NUM_ROWS_INTERMEDIATE;
            case DIFFYCULT:
                return NUM_ROWS_HARD;
            default:
                return NUM_COLS_BEGINNER;
        }
    }
    
    public int getButtonSize(){
       return BUTTON_SIZE;
    }

    public Image getBUTON_IMAGE() {
        return BUTON_IMAGE;
    }

    public Image getFLAG_IMAGE() {
        return FLAG_IMAGE;
    }

    public Image getQUESTION_IMAGE() {
        return QUESTION_IMAGE;
    }

    public int getNumCols() {
        switch (level) {
            case BEGINNER:
                return NUM_COLS_BEGINNER;
            case INTERMEDIATE:
                return NUM_COLS_INTERMEDIATE;
            case DIFFYCULT:
                return NUM_COLS_HARD;
            default:
               return NUM_COLS_BEGINNER;
        }
    }
    
        public int getNumRatio() {
        switch (level) {
            case BEGINNER:
                return RATIO_BEGINNER;
            case INTERMEDIATE:
                return RATIO_INTERMEDIATE;
            case DIFFYCULT:
                return RATIO_HARD;
            default:
                throw new RuntimeException("Unknown Level");
        }
    }

}
