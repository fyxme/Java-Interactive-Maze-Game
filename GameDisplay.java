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

/* TopLevelDemo.java requires no other files. */
public class GameDisplay {
    private JPanel cards;
    private int mazeSize = 20;
    private int sight = 5;

    private JFrame mainFrame;

    /**
     * @precondition	existing object GameDisplay
     * @postcondition	returns maze size as an int
     * @return			mazeSize
     */
    public int getMazeSize(){
        return mazeSize;
    }

    /**
     * @precondition	existing object GameDisplay
     * @postcondition	returns sight radius as an int
     * @return			sight
     */
    public int getSight(){
        return sight;
    }
    
    
    /**
     * @precondition	GameDisplay is called in run() inside javax.swing.SwingUtilities.invokeLater()
     * @postcondition	GameDisplay object created and placed in java swing run queue
     * 					the game will be displayed in a JFrame
     */
    public GameDisplay(){
        createAndShowGUI();
    }



    /**
     * @precondition	GameDisplay constructor is called
     * @postcondition	constructs the GameDisplay
     */
    private void createAndShowGUI() {
        //Create and set up the window.
        mainFrame = new JFrame("Battlefield One");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //create CardLayout manager
        cards = new JPanel(new CardLayout());

        //add JPanels
        cards.add(new MainMenu(this), "MainMenu");
        cards.add(new SettingsMenu(this), "SettingsMenu");
        swapPanel("MainMenu");
        

        //Display the window.
        mainFrame.getContentPane().add(cards);
        mainFrame.pack();
        mainFrame.setResizable(false);
        mainFrame.setVisible(true);    
    }

    /**
     * @param panelName	name of panel in CardLayout to switch to
     * @precondition	panelName is a valid name of a panel in CardLayout or is "Map"
     * @postcondition	old panel is hidden and a new panel with name panelName is shown, 
     * 					if panelName is "Map" a new Map is created and shown
     */
    public void swapPanel(String panelName){
        CardLayout cl = (CardLayout)(cards.getLayout());

        if(panelName.equals("Map")){
            cards.add(new Map(this, mazeSize, sight), "Map");
        }

        cl.show(cards, panelName);
        for(Component c : cards.getComponents()){
            if(c.isVisible()){
                c.setFocusable(true);
                c.requestFocusInWindow();
                cards.setPreferredSize(c.getPreferredSize());
                mainFrame.pack();
            }
        }

        System.out.println("switching to: " + panelName);
    }



    /**
     * @param mazeSize	size of maze to be generated
     * @param sight		radius of vision in game (sight == 0 means the full maze is displayed)
     * @precondition 	(sight < (mazeSize/2 - 1))
     * @postcondition	mazeSize and sight fields are changed
     */
    public void setMazeSettings(int mazeSize, int sight){
        this.mazeSize = mazeSize;
        this.sight = sight;
    }

    /**
     * main
     */
    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                GameDisplay g = new GameDisplay();
            }
        });
    }
}
