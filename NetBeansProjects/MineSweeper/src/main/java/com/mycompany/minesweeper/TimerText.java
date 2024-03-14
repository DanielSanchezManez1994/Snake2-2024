/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.minesweeper;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Action;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.Timer;

/**
 *
 * @author danielsanchez
 */
public class TimerText extends JTextField implements TimerInterface {

    private Timer timer;
    private int seconds;
    private int minutes;

    public TimerText() {

        seconds = 0;
        minutes = 0;
        setText("00 : 00");
            timer = new Timer(1000, new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent ae) {

                    seconds++;
                    if (seconds == 60) {
                        seconds = 0;
                        minutes++;
                    }
                    displayTime();
                }
            }
            );

        }

    @Override
    public void start() {
        
        if(!timer.isRunning()){
            timer.start();
        }
    }

    @Override
    public void stop() {
        if(timer.isRunning()){
            timer.stop();
        }
    }

    @Override
    public void reset() {
        
        timer.stop();
        seconds = 0;
        minutes = 0;
        setText("00 : 00");
    }

    private void displayTime() {
        
        setText((minutes < 10 ? "0" : "") + minutes + " : " + (seconds < 10 ? "0" : "") + seconds);

    }

}
