import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Rectangle;
import java.awt.Font;



import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.JButton;

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
    
    private Maze maze;
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

    
    public Map(GameDisplay displayController, int dimension, int sight){
    	this.sight = sight;
		this.dimension = dimension;
        this.displayController = displayController;

        setLayout(null);
		int windowDimension = 700;
		
    	if(sight > 0) {
    		this.dimension = sight*2+1;
    	}
    	
    	wall_pixel_dimension = windowDimension/(this.dimension*wallFraction);
    	block_pixel_dimension = wall_pixel_dimension*wallFraction;
		windowDimension = (block_pixel_dimension*(this.dimension))+wall_pixel_dimension;
        // generate new game instance
    	addKeyListener(this);
    	setFocusable(true);
    	setFocusTraversalKeysEnabled(false);
    	gi = new GameInstance(dimension, dimension, sight, DEFAULT_NAME);
    	this.maze = gi.getMaze();
    	
    	this.terrainGrid = new Color[(dimension * 2 + 1)][(dimension * 2 + 1)];

    	System.out.println(gi.printMaze(this.sight));
    	updateGrid(gi.printMaze(this.sight));

 
        setPreferredSize(new Dimension(windowDimension + 250, windowDimension));

        this.displayController = displayController;
        addButton();
    }

    private void addButton(){
        Dimension menuDimension = this.getPreferredSize();
        int buttonHeight = (int)(menuDimension.getHeight()/10);
        int buttonWidth = (int)(menuDimension.getWidth()/4);
        Font buttonFont = new Font("Arial", Font.BOLD, 15 );


        final JButton rageQuit = new JButton("Rage Quit!");
        rageQuit.setOpaque(false);
        rageQuit.setContentAreaFilled(false);
        rageQuit.setSize(buttonWidth, buttonHeight);
        rageQuit.setBounds(0, buttonHeight*9, buttonWidth, buttonHeight);
        rageQuit.setLocation(0, buttonHeight*9);
        rageQuit.setForeground(Color.WHITE);
        rageQuit.setBorderPainted(false);
        rageQuit.setFont(buttonFont);

        


        rageQuit.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                displayController.swapPanel("MainMenu");
            }
            @Override
            public void mouseEntered(MouseEvent e){
                rageQuit.setForeground(Color.RED);
            }
            @Override
            public void mouseExited(MouseEvent e){
                rageQuit.setForeground(Color.WHITE);
            }
        });

        add(rageQuit);
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

        //paint rageQuit button
        Dimension menuDimension = this.getPreferredSize();
               int buttonHeight = (int)(menuDimension.getHeight()/10);
        int buttonWidth = (int)(menuDimension.getWidth()/4);

        Rectangle rageQuitRect = new Rectangle(0, buttonHeight*9, buttonWidth, buttonHeight);
        g.setColor(new Color((float)50/255, (float)50/255, (float)50/255, (float)0.5));
        g.fillRoundRect((int)rageQuitRect.getX() + 10, (int)rageQuitRect.getY() + 10, 
            (int)rageQuitRect.getWidth()-20, (int)rageQuitRect.getHeight()- 20, 10, 10);

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
