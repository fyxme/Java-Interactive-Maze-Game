import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class Map extends JPanel implements KeyListener{
	private static final int LEFT = 0;
	private static final int RIGHT = 1;
	private static final int UP = 2;
	private static final int DOWN = 3;
    public static final Color WHITE = Color.decode("#E0F2F1");
    public static final Color BLACK = Color.decode("#3E2723");
    public static final Color RED = Color.decode("#F44336");
    
    public static final Color[] TERRAIN = {
        BLACK,
        WHITE
    };
    
    public static final int NUM_ROWS = 20;
    public static final int NUM_COLS = 20;

    public static final int PREFERRED_GRID_SIZE_PIXELS = 20;
    public static final int WALL_WIDTH = 20;

    GameInstance gi = null;
	private static final int DEFAULT_WIDTH = 20;
	private static final int DEFAULT_HEIGHT = 20;
	private static final String DEFAULT_NAME = "Ronin the Conqueror of Worlds";
    
    // In reality you will probably want a class here to represent a map tile,
    // which will include things like dimensions, color, properties in the
    // game world.  Keeping simple just to illustrate.
    private Color[][] terrainGrid;

    public Map(){
//    	this.terrainGrid = new Color[NUM_ROWS][NUM_COLS];
        // generate new game instance
    	addKeyListener(this);
    	setFocusable(true);
    	setFocusTraversalKeysEnabled(false);
    	gi = new GameInstance(DEFAULT_WIDTH, DEFAULT_HEIGHT, DEFAULT_NAME);
    	
    	this.terrainGrid = new Color[(DEFAULT_WIDTH * 2 + 1)][(DEFAULT_HEIGHT * 2 + 1)];

    	System.out.println(gi.getMazeAsString());
    	updateGrid(gi.getMazeAsString());
 
        int preferredWidth = (DEFAULT_WIDTH * 2 + 1) * PREFERRED_GRID_SIZE_PIXELS;
        int preferredHeight = (DEFAULT_HEIGHT * 2 + 1) * PREFERRED_GRID_SIZE_PIXELS;
        setPreferredSize(new Dimension(preferredWidth, preferredHeight));
    }

    public void updateGrid(String maze) {
    	int i = 0;
    	int j = 0;
    	for (String s : maze.split("\n")) {
    		j = 0;
    		for (String c : s.split("")) {
    			if (c.equals("+") || c.equals("-") || c.equals("|")) {
    				// wall
    				this.terrainGrid[j][i] = BLACK;
    			} else if (c.equals("P")) {
    				// player
    				this.terrainGrid[j][i] = RED;
    			} else {
    				// playable space
    				this.terrainGrid[j][i] = WHITE;
    			}
    			j++;
    		}
    		i++;
    	}
    }
    
    @Override
    public void paintComponent(Graphics g) {
        // Important to call super class method
        super.paintComponent(g);
        // Clear the board
        g.clearRect(0, 0, getWidth(), getHeight());
        // Draw the grid
//        DEFAULT_WIDTH = 20;
        int rectWidth = (100 * getWidth())/(DEFAULT_WIDTH * 100 + 20 *  (DEFAULT_WIDTH + 1));
        
        // int rectWidth = (getHeight() - (DEFAULT_WIDTH + 1) * 20 * rectWidth / 100)/DEFAULT_WIDTH;
        // rectWidth * DEFAULT_WIDTH * 100 = 100 * getHeight() - 20 * rectWidth
        // rectWidth * DEFAULT_WIDTH * 100 + 20 * rectWidth = 100 * getHeight()
        // rectWidth = (100 * getHeight())/(DEFAULT_WIDTH * 100 + 20);
        int rectHeight = (100 * getHeight())/(DEFAULT_HEIGHT * 100 + 20 *  (DEFAULT_HEIGHT + 1));
        // wall = 
        int wallWidth = 20 * rectWidth / 100;
        int wallHeight = 20 * rectHeight / 100;

        int x = 0;
        int y = 0;
        for (int i = 0; i < DEFAULT_WIDTH * 2 + 1; i++) {
            for (int j = 0; j < DEFAULT_HEIGHT * 2 + 1; j++) {
                // Upper left corner of this terrain rect
            	x = ((int)i/2) * rectWidth + (int)(i+1)/2 * wallHeight;
                y = ((int)j/2) * rectHeight + (int)(j+1)/2 * wallWidth;
                System.out.println(x);
                System.out.println(y);
                Color terrainColor = terrainGrid[i][j];
                g.setColor(terrainColor);
                
                int width = (i%2 == 0)? wallWidth : rectWidth; 
                int height = (j%2 == 0)? wallWidth : rectWidth;
                g.fillRect(x, y, width, height);
            }
        }
    }

    public static void main(String[] args) {
        // http://docs.oracle.com/javase/tutorial/uiswing/concurrency/initial.html
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JFrame frame = new JFrame("BATTLEFIELD ONE");
                Map map = new Map();
                frame.addKeyListener(map.getKeyListeners()[0]);
                frame.add(map);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                frame.setVisible(true);
                frame.setFocusable(true);
            }
        });
    }

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		char c = e.getKeyChar();
		if (c == 'a')
        	gi.move(LEFT);
        else if (c == 'd')
        	gi.move(RIGHT);
        else if (c == 'w')
        	gi.move(UP);
        else if (c == 's')
        	gi.move(DOWN);
		
		System.out.println(c);
		
		// update maze
		updateGrid(gi.getMazeAsString());
		// update display
		repaint();
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}