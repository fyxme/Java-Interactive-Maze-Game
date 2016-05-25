/*
 * Copyright (c) 1995, 2008, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Oracle or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */ 

import java.awt.*;
import javax.swing.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/* TopLevelDemo.java requires no other files. */
public class GameDisplay {
    private JPanel cards;

    public GameDisplay(){
        createAndShowGUI();
    }
    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */

    private void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("TopLevelDemo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        //Create the menu bar.  Make it have a green background.
        /*JMenuBar greenMenuBar = new JMenuBar();
        greenMenuBar.setOpaque(true);
        greenMenuBar.setPreferredSize(new Dimension(200, 20));
        */
        
        //Create a yellow label to put in the content pane.
        JLabel yellowLabel = new JLabel();
        yellowLabel.setOpaque(true);
        yellowLabel.setBackground(Color.WHITE);
        yellowLabel.setPreferredSize(new Dimension(200, 180));

        //Set the menu bar and add the label to the content pane.
        //frame.setJMenuBar(greenMenuBar);
        //frame.getContentPane().add(yellowLabel, BorderLayout.CENTER);

        cards = new JPanel(new CardLayout());

        cards.add(new MainMenu(this), "MainMenu");
        JPanel map = new Map();
        map.setFocusable(true);
        cards.add(map, "Map");
        frame.getContentPane().add(cards);
        
        
        cards.addPropertyChangeListener(new PropertyChangeListener(){
            
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                System.out.println("dfjlsdjfk");
                CardLayout cl = (CardLayout)(cards.getLayout());
                cl.show(cards, (String)evt.getPropertyName());
                System.out.println("switching to: " + (String)evt.getPropertyName());
                }
        });
        
        //Display the window.
        frame.pack();
        frame.setResizable(false);
        frame.setVisible(true);
        
    }

    public void swapPanel(String panelName){
        System.out.println("dfjlsdjfk");
        CardLayout cl = (CardLayout)(cards.getLayout());
        cl.show(cards, panelName);
        for(Component c : cards.getComponents()){
            if(c.isVisible()){
                c.setFocusable(true);
                c.requestFocusInWindow();
            }
        }
        System.out.println("switching to: " + panelName);
    }


    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                GameDisplay g = new GameDisplay();
            //inloop();
            }
        });
    }
}
