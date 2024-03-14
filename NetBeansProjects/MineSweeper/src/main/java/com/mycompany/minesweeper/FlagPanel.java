/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.minesweeper;

import static com.mycompany.minesweeper.MineButton.button_size;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.TextField;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author danielsanchez
 */
public class FlagPanel extends JPanel implements FlagPanelInterface {

    private int flagCounter;
    private JTextField textField;

    public FlagPanel() {

        setLayout(new FlowLayout());

        Image image = ConfigData.getInstance().getFLAG_IMAGE();
        
        Image newimg = image.getScaledInstance(15, 15, java.awt.Image.SCALE_SMOOTH);
        
        Icon iconFlag = new ImageIcon(newimg);
        
        JLabel label = new JLabel(iconFlag);
        
        add(label);
        
        flagCounter = 0;
        
        textField = new JTextField("");
        
        reset();
        
        textField.setBackground(Color.yellow);
        textField.setEditable(false);
        textField.setFocusable(false);
        textField.setPreferredSize(new Dimension(40,25));
        add(textField);
    }

    @Override
    public void reset() {
        
        flagCounter = ConfigData.getInstance().getNumRatio();
        
        textField.setText(""+flagCounter);
        
    }

    @Override
    public void incrementOne() {
        
        if(flagCounter < ConfigData.getInstance().getNumRatio()){
            
            flagCounter++;
            
            textField.setText("" + flagCounter);
        }
    }

    @Override
    public void subtractOne() {
        if(flagCounter > 0 ){
            
            flagCounter--;
            
            textField.setText("" + flagCounter);
            
        }
    }
    
    public int getFlagCounter(){
        
        return flagCounter;
        
    }

}
