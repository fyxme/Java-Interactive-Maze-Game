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
    public static final Color WHITE = Color.WHITE;
    public static final Color BLACK = Color.decode("#3E2723");
    public static final Color RED = Color.decode("#F44336");
    public static final Color PATH1 = Color.decode("#DCEDC8");
    public static final Color PATH2 = Color.decode("#FFCCBC");
    public static final int PREFERRED_GRID_SIZE_PIXELS = 20;
    public static final int WALL_WIDTH = 20;
    
    public static final Color[] TERRAIN = {
        BLACK,
        WHITE
    };
    
    GameInstance gi = null;
    
	private int sight;
    public int rows;
    public int cols;
	private int w_width;
	private int w_height;
	private static final String DEFAULT_NAME = "Ronin the Conqueror of Worlds";
    
    // In reality you will probably want a class here to represent a map tile,
    // which will include things like dimensions, color, properties in the
    // game world.  Keeping simple just to illustrate.
    private Color[][] terrainGrid;

    public Map(int rows, int cols, int sight){
    	this.rows = rows;
    	this.cols = cols;
    	this.sight = sight;
    	
    	if(sight == 0) {
    		w_width = cols;
    		w_height = rows;
    	}else {
    		w_width = sight * 2 + 1;
    		w_height = rows * 2 + 1;
    	}
    	
        // generate new game instance
    	addKeyListener(this);
    	setFocusable(true);
    	setFocusTraversalKeysEnabled(false);
    	gi = new GameInstance(rows, cols, DEFAULT_NAME);
    	
    	this.terrainGrid = new Color[(w_height * 2 + 1)][(w_width * 2 + 1)];

    	System.out.println(gi.printMaze(this.sight));
    	updateGrid(gi.printMaze(this.sight));
 
        int preferredWidth = (w_width * 2 + 1) * PREFERRED_GRID_SIZE_PIXELS;
        int preferredHeight = (w_height * 2 + 1) * PREFERRED_GRID_SIZE_PIXELS;
        setPreferredSize(new Dimension(preferredWidth, preferredHeight));
    }

    public void updateGrid(String maze) {
    	int i = 0;
    	int j = 0;
    	for (String s : maze.split("\n")) {
    		j = 0;
    		char[] chars = s.toCharArray();
    		for (char c : chars) {
    			if (c == '+' || c == '-' || c == '|') {
    				// wall
    				this.terrainGrid[j][i] = BLACK;
    			} else if (c == 'P') {
    				// player
    				this.terrainGrid[j][i] = RED;
    			} else if (c == 'V') {
    				// playable space that has been visited
    				this.terrainGrid[j][i] = PATH1;
    			} else if (c == 'v') {
    				// playable space that has been backtracked
    				this.terrainGrid[j][i] = PATH2;
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
        int rectWidth = (100 * getWidth())/(w_width * 100 + 20 *  (w_width + 1));
        
        // int rectWidth = (getHeight() - (DEFAULT_WIDTH + 1) * 20 * rectWidth / 100)/DEFAULT_WIDTH;
        // rectWidth * DEFAULT_WIDTH * 100 = 100 * getHeight() - 20 * rectWidth
        // rectWidth * DEFAULT_WIDTH * 100 + 20 * rectWidth = 100 * getHeight()
        // rectWidth = (100 * getHeight())/(DEFAULT_WIDTH * 100 + 20);
        int rectHeight = (100 * getHeight())/(w_height * 100 + 20 *  (w_height + 1));
        // wall = 
        int wallWidth = 20 * rectWidth / 100;
        int wallHeight = 20 * rectHeight / 100;

        int x = 0;
        int y = 0;
        for (int i = 0; i < w_width * 2 + 1; i++) {
            for (int j = 0; j < w_height * 2 + 1; j++) {
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
                JFrame frame = new JFrame("BATTLEFIELD ONE     Soon\u2122");
                Map map = new Map(20,20,0);
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
		
		
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		if (keyCode == KeyEvent.VK_A || keyCode == KeyEvent.VK_LEFT)
        	gi.move(LEFT);
        else if (keyCode == KeyEvent.VK_D || keyCode == KeyEvent.VK_RIGHT)
        	gi.move(RIGHT);
        else if (keyCode == KeyEvent.VK_W || keyCode == KeyEvent.VK_UP)
        	gi.move(UP);
        else if (keyCode == KeyEvent.VK_S || keyCode == KeyEvent.VK_DOWN)
        	gi.move(DOWN);
		
		System.out.println(e.getKeyChar());
		
		// update maze
		updateGrid(gi.printMaze(this.sight));
		// update display
		repaint();
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}