/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.minesweeper;

import static com.mycompany.minesweeper.ButtonState.CLOSED;
import static com.mycompany.minesweeper.ButtonState.FLAG;
import static com.mycompany.minesweeper.ButtonState.QUESTIONMARK;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingUtilities;

/**
 *
 * @author danielsanchez
 */
public class MineButton extends JButton {

    private int row;
    private int col;

    public static int button_size;

    /**
     *
     */
   
    private static Icon iconButton = null;
    private static Icon iconButtonPress = null;
    
    private static MouseAdapter mouseAdapter = null;
    private ButtonState buttonState;
    
    private FlagPanelInterface flagPanelInterface;

   
    public MineButton(int row, int col) {
        super();
        buttonState = ButtonState.CLOSED;
        this.row = row;
        this.col = col;
        
        button_size = ConfigData.getInstance().getButtonSize();
        
        setMargin(new Insets(0, 0, 0, 0));
        setContentAreaFilled(false);
        setBorderPainted(false);
        
        if (mouseAdapter == null) {
            mouseAdapter = new MouseAdapter() {

                @Override
                public void mousePressed(MouseEvent e) {
                    if (SwingUtilities.isLeftMouseButton(e)) {
                         MineButton b = (MineButton) e.getComponent();
                         b.setIcon(iconButtonPress);
                    }                   
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    MineButton b = (MineButton) e.getComponent();
                   
                    if (SwingUtilities.isRightMouseButton(e)) {
                        
                        chooseButtonState(b);
                        
                        repaint();
                        
                        
                    }
                     

                }
            };
        };
        setIcon();
        addMouseListener(mouseAdapter);
    }

    private void setIcon() {
        if (iconButton == null) {
            Image image = ConfigData.getInstance().getBUTON_IMAGE();
            Image newimg = image.getScaledInstance(button_size, button_size, java.awt.Image.SCALE_SMOOTH);
            iconButton = new ImageIcon(newimg);
        }

        if (iconButtonPress == null) {

            Image image = new ImageIcon(getClass().getResource("/images/boton_pressed.jpg")).getImage();
            Image newimg = image.getScaledInstance(button_size, button_size, java.awt.Image.SCALE_SMOOTH);
            iconButtonPress = new ImageIcon(newimg);
        }
        setIcon(iconButton);
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public ButtonState getButtonState() {
        return buttonState;
    }
    
    public void setButtonState(ButtonState buttonState){
        this.buttonState = buttonState;
    }
    
    public void getButtonSize(){
        
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(buttonState == ButtonState.FLAG){
            Icon flag = getIcon(ConfigData.getInstance().getFLAG_IMAGE());
            flag.paintIcon(this, g, 0, 0);
        } else if (buttonState == ButtonState.QUESTIONMARK){
            Icon question = getIcon(ConfigData.getInstance().getQUESTION_IMAGE());
            question.paintIcon(this, g, 0, 0);
        }
    }

    public void chooseButtonState(MineButton b) {
        if (b.buttonState == ButtonState.CLOSED || b.buttonState == ButtonState.FLAG || b.buttonState == ButtonState.QUESTIONMARK) {
            switch (b.buttonState) {
                case CLOSED:
                    b.buttonState = ButtonState.FLAG;
                    System.out.println("FLAG");
                    flagPanelInterface.subtractOne();
  
                    break;
                case FLAG:
                    b.buttonState = ButtonState.QUESTIONMARK;
                    System.out.println("QUESTIONMARK");
                    flagPanelInterface.incrementOne();
                    break;
                case QUESTIONMARK:
                    b.buttonState = ButtonState.CLOSED;
                    System.out.println("CLOSED");

                    break;
            }
        }
    }
    
    public Icon getIcon(Image image){
       Image newImage  = image.getScaledInstance(button_size, button_size, java.awt.Image.SCALE_SMOOTH);
       Icon icon = new ImageIcon(newImage);
       return icon;
    }
    
    public void setFlagPanelInterface(FlagPanelInterface flagPanelInterface){
        this.flagPanelInterface = flagPanelInterface;
    }
    
}
