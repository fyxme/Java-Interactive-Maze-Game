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
    
    public static final Color[] TERRAIN = {
        BLACK,
        WHITE
    };
    
    GameInstance gi = null;
    
    private int block_pixel_dimension,wall_pixel_dimension;
	private int sight;
	private int dimension;
	private int wallFraction = 8;
	private static final String DEFAULT_NAME = "Ronin the Conqueror of Worlds";

    private GameDisplay displayController;
    
    // In reality you will probably want a class here to represent a map tile,
    // which will include things like dimensions, color, properties in the
    // game world.  Keeping simple just to illustrate.
    private Color[][] terrainGrid;

//<<<<<<< HEAD
    
    public Map(GameDisplay displayController, int dimension, int sight){
    	this.sight = sight;
		this.dimension = dimension;
    	
		int windowDimension = 700;
		
    	if(sight > 0) {
    		this.dimension = sight*2+1;
    	}
    	
    	wall_pixel_dimension = windowDimension/(this.dimension*wallFraction);
    	block_pixel_dimension = wall_pixel_dimension*wallFraction;
		windowDimension = (block_pixel_dimension*(this.dimension))+wall_pixel_dimension;
/*    	
=======
    public Map(GameDisplay displayController){
//    	this.terrainGrid = new Color[NUM_ROWS][NUM_COLS];
>>>>>>> ui
*/
        // generate new game instance
    	addKeyListener(this);
    	setFocusable(true);
    	setFocusTraversalKeysEnabled(false);
//<<<<<<< HEAD
    	gi = new GameInstance(dimension, dimension, sight, DEFAULT_NAME);
/*
=======
    	gi = new GameInstance(MAZE_ROWS, MAZE_COLS, 3, DEFAULT_NAME);
>>>>>>> ui
*/
    	
    	this.terrainGrid = new Color[(dimension * 2 + 1)][(dimension * 2 + 1)];

    	System.out.println(gi.printMaze(this.sight));
    	updateGrid(gi.printMaze(this.sight));
 
//<<<<<<< HEAD
        setPreferredSize(new Dimension(windowDimension, windowDimension));

        this.displayController = displayController;
/*
=======
        int preferredWidth = (WINDOW_WIDTH * 2 + 1) * PREFERRED_GRID_SIZE_PIXELS;
        int preferredHeight = (WINDOW_HEIGHT * 2 + 1) * PREFERRED_GRID_SIZE_PIXELS;
        setPreferredSize(new Dimension(preferredWidth, preferredHeight));

        this.displayController = displayController;
    }

    public Map(GameDisplay displayController, int mazeSize, int sight){
//      this.terrainGrid = new Color[NUM_ROWS][NUM_COLS];
        // generate new game instance
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        gi = new GameInstance(mazeSize, mazeSize, sight, DEFAULT_NAME);
        
        this.terrainGrid = new Color[(WINDOW_WIDTH * 2 + 1)][(WINDOW_HEIGHT * 2 + 1)];

        System.out.println(gi.getMazeAsString());
        updateGrid(gi.getMazeAsString());
 
        int preferredWidth = (WINDOW_WIDTH * 2 + 1) * PREFERRED_GRID_SIZE_PIXELS;
        int preferredHeight = (WINDOW_HEIGHT * 2 + 1) * PREFERRED_GRID_SIZE_PIXELS;
        setPreferredSize(new Dimension(preferredWidth, preferredHeight));

        this.displayController = displayController;
>>>>>>> ui
*/
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
        
        int rectDimension = block_pixel_dimension - wall_pixel_dimension;

        int x = 0;
        int y = 0;
        for (int i = 0; i < dimension * 2 + 1; i++) {
            for (int j = 0; j < dimension * 2 + 1; j++) {
                // Upper left corner of this terrain rect
            	x = ((int)i/2) * rectDimension + (int)(i+1)/2 * wall_pixel_dimension;
                y = ((int)j/2) * rectDimension + (int)(j+1)/2 * wall_pixel_dimension;
                Color terrainColor = terrainGrid[i][j];
                g.setColor(terrainColor);
                
                int width = (i%2 == 0)? wall_pixel_dimension : rectDimension; 
                int height = (j%2 == 0)? wall_pixel_dimension : rectDimension;
                g.fillRect(x, y, width, height);
            }
        }
    }

    /*public static void main(String[] args) {
        // http://docs.oracle.com/javase/tutorial/uiswing/concurrency/initial.html
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JFrame frame = new JFrame("BATTLEFIELD ONE     Soon\u2122");
                Map map = new Map(30,5);
                frame.setResizable(false);
                frame.addKeyListener(map.getKeyListeners()[0]);
                frame.add(map);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                frame.setVisible(true);
                frame.setFocusable(true);
                frame.setResizable(false);
            }
        });
    }
    */

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
