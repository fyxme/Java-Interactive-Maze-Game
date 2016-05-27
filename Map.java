import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.Rectangle;
import java.awt.Font;


import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JTextArea;

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
	private boolean gameCompleted;
	public int score;
	
	final private JButton rageQuit = new JButton("Rage Quit!");
	final private JButton playAgain = new JButton("Play Again");
	final private JButton mainMenu = new JButton("Main Menu");

    private GameDisplay displayController;
    
    // In reality you will probably want a class here to represent a map tile,
    // which will include things like dimensions, color, properties in the
    // game world.  Keeping simple just to illustrate.
    private Color[][] terrainGrid;
    

    
    /**
     * @param displayController	a GameDisplay object
     * @param dimension			dimension of the maze to be generated
     * @param sight				vision radius of the player
     * @precondition			a GameDisplay must exist & (sight < (mazeSize/2 - 1))
     * @postcondition			a Map is created with a maze generated to the dimension supplied
     */
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
    	gameCompleted = false;
    	score = 0;
    	
    	this.terrainGrid = new Color[(dimension * 2 + 1)][(dimension * 2 + 1)];

    	System.out.println(gi.printMaze(this.sight));
    	updateGrid(gi.printMaze(this.sight));

 
        setPreferredSize(new Dimension(windowDimension + 250, windowDimension));

        this.displayController = displayController;
        addButtons();
        addHowToPlay();
    }
    
    /**
     * @precondition	SettingsMenu constructor is run
     * @postcondition	JTextArea containing instructions are added to the panel
     */
    private void addHowToPlay(){
    	Dimension menuDimension = this.getPreferredSize();
        int width = (int)menuDimension.getWidth();
        int labelHeight = (int)(menuDimension.getHeight()/10);
        int labelWidth = 200;
        int gap = 24;
        Font textFont = new Font("Arial", Font.PLAIN, 20 );
        
        String howToPlayText = ("Use the arrow keys or the 'WASD' keys to try and"
        		+ " reach the top right corner of the map!");
        JTextArea text = new JTextArea(howToPlayText);

        text.setBounds(width - labelWidth - gap, labelHeight*3, labelWidth, labelHeight*5);
        text.setLocation(width - labelWidth - gap, labelHeight*3);
        
        text.setLineWrap(true);
        text.setWrapStyleWord(true);
        text.setFont(textFont);
        text.setOpaque(false);
        text.setBackground(new Color((float)50/255, (float)50/255, (float)50/255, (float)0.5));
        text.setEditable(false);
        text.setEnabled(false);
        text.setDisabledTextColor(BLACK);
        add(text);
    }
    
    /**
     * @precondition	SettingsMenu constructor is run
     * @postcondition	JButtons are added to the panel
     */
    private void addButtons(){
        Dimension menuDimension = this.getPreferredSize();
        int width = (int)menuDimension.getWidth();
        int buttonHeight = (int)(menuDimension.getHeight()/10);
        int buttonWidth = 200;
        int gap = 24;
        Font buttonFont = new Font("Arial", Font.BOLD, 30 );

        rageQuit.setSize(buttonWidth, buttonHeight);
        rageQuit.setBounds(width - buttonWidth - gap, buttonHeight * 17/2, buttonWidth, buttonHeight);
        rageQuit.setLocation(width - buttonWidth - gap, buttonHeight*17/2);
        rageQuit.setFont(buttonFont);
        
        buttonFont = new Font("Arial", Font.PLAIN, 20 );
        buttonWidth = (width - 250)/4;
        playAgain.setSize(buttonWidth, buttonHeight);
        playAgain.setBounds(buttonWidth - gap, buttonHeight * 7, buttonWidth, buttonHeight);
        playAgain.setLocation(buttonWidth - gap, buttonHeight * 7);
        playAgain.setFont(buttonFont);
        playAgain.setEnabled(false);
        playAgain.setVisible(false);
        
        mainMenu.setSize(buttonWidth, buttonHeight);
        mainMenu.setBounds(buttonWidth*2 + gap, buttonHeight * 7, buttonWidth, buttonHeight);
        mainMenu.setLocation(buttonWidth*2 + gap, buttonHeight * 7);
        mainMenu.setFont(buttonFont);
        mainMenu.setEnabled(false);
        mainMenu.setVisible(false);
        
        
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
                rageQuit.setForeground(Color.BLACK);
            }
        });
        
        mainMenu.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                displayController.swapPanel("MainMenu");
            }
        });
        
        playAgain.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                displayController.swapPanel("Map");
            }
        });

        add(rageQuit);
        add(playAgain);
        add(mainMenu);
    }

    /**
     * @param maze		an ASCII representation of maze
     * @precondition	valid maze string
     * @postcondition	maze is converted a 2d array of Colors
     */
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
    
    /* (non-Javadoc)
     * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
     */
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
        
        
        Dimension menuDimension = this.getPreferredSize();
        int height = (int)menuDimension.getHeight();
        int width = (int)menuDimension.getWidth();
        int titleHeight = (int)(menuDimension.getHeight()/10);
        int titleWidth = 200;
        int gap = 24;
        
        //paint right menu background
        Rectangle menuRect = new Rectangle( (int)(width - titleWidth - gap*1.5), gap/2, titleWidth + gap, height-gap);
        g.setColor(new Color((float)50/255, (float)50/255, (float)50/255, (float)0.2));
        g.fillRoundRect((int)menuRect.getX(), (int)menuRect.getY(), 
                (int)menuRect.getWidth(), (int)menuRect.getHeight(), 10, 10);
        
        //paint scores
        Rectangle title = new Rectangle(width - titleWidth - gap, titleHeight*1, titleWidth, titleHeight);
        Rectangle red = new Rectangle(width - titleWidth - gap, titleHeight*11/2, titleWidth, titleHeight/2);
        Rectangle green = new Rectangle(width - titleWidth - gap, titleHeight*5, titleWidth, titleHeight/2);
        Rectangle unvisited = new Rectangle(width - titleWidth - gap, titleHeight*6, titleWidth, titleHeight/2);
        String strRed = ("Red Tiles: " + Integer.toString(maze.getRedCount()));
        String strGreen = ("Green Tiles: " + Integer.toString(maze.getGreenCount()));
        String strUnvisited = ("Unvisited Tiles : " + Integer.toString(maze.getUnvisitedCount()));
        
        
        Font countFont = new Font("Arial", Font.PLAIN, 15);
        centreString(g, title, "Instructions", new Font("Arial", Font.BOLD, 30), BLACK);
        centreString(g, red, strRed, countFont, BLACK);
        centreString(g, green, strGreen, countFont, BLACK);
        centreString(g, unvisited, strUnvisited, countFont, BLACK);
        
        //paint overlay if game completed
        if(gameCompleted){
        	paintGameCompleteOverlay(g, height, width - 250);
        }
        
    }
    
    /**
     * @param g			Graphics to be painted on
     * @param height	height of maze area
     * @param width		width of maze area
     * @precondition	Game has completed and is called within paintComponent
     * @postcondition	an overlay is painted over the maze showing the score
     */
    private void paintGameCompleteOverlay(Graphics g, int height, int width){
    	Rectangle overlayRect = new Rectangle( 0, 0, width, height);
        g.setColor(new Color((float)50/255, (float)50/255, (float)50/255, (float)0.95));
        g.fillRoundRect((int)overlayRect.getX(), (int)overlayRect.getY(), 
                (int)overlayRect.getWidth(), (int)overlayRect.getHeight(), 10, 10);
        int titleHeight = height/10;
        int titleWidth = width/4;
        
        Rectangle title = new Rectangle(titleWidth, titleHeight/2, titleWidth*2, titleHeight);
        centreString(g, title, "Congratulations!", new Font("Arial", Font.BOLD, 40), WHITE);
        Rectangle title2 = new Rectangle(titleWidth, titleHeight*3/2, titleWidth*2, titleHeight);
        centreString(g, title2, "You reached the end!", new Font("Arial", Font.BOLD, 30), WHITE);
        
        Rectangle score1 = new Rectangle(0, titleHeight*7/2, titleWidth*3, titleHeight);
        Rectangle score2 = new Rectangle(titleWidth, titleHeight*7/2, titleWidth*3, titleHeight);
        
        if(score < maze.getUnvisitedCount()){
        	score = 100*maze.getGreenCount()/(maze.getTotalCount()-maze.getUnvisitedCount());
        }
        
        centreString(g, score1, "Efficiency : ", new Font("Arial", Font.BOLD, 30), WHITE);
        centreString(g, score2, Integer.toString(score)+"%", new Font("Arial", Font.BOLD, 30), WHITE);
        
        
    }
    
    /**
     * @param g			Graphics to be painted on
     * @param r			Rectangle for string to be centred in
     * @param s			String to be drawn
     * @param font		Font for string to be drawn with
     * @param c			Color for string to be drawn with
     * @precondition	Parameters exist and called within paintComponent
     * @postcondition	A string is drawn in the centre of Rectangle r
     */
    //centre string inside rectangle
    //from http://stackoverflow.com/questions/27706197/how-can-i-center-graphics-drawstring-in-java
    private void centreString(Graphics g, Rectangle r, String s, Font font, Color c) {
        FontRenderContext frc = 
            new FontRenderContext(null, true, true);

        Rectangle2D r2D = font.getStringBounds(s, frc);
        int rWidth = (int) Math.round(r2D.getWidth());
        int rHeight = (int) Math.round(r2D.getHeight());
        int rX = (int) Math.round(r2D.getX());
        int rY = (int) Math.round(r2D.getY());

        int a = (r.width / 2) - (rWidth / 2) - rX;
        int b = (r.height / 2) - (rHeight / 2) - rY;
        g.setColor(c);
        g.setFont(font);
        g.drawString(s, r.x + a, r.y + b);
    }
    
	/* (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyTyped(java.awt.event.KeyEvent)
	 */
	@Override
	public void keyTyped(KeyEvent e) {
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
	 */
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
		
		//set button visible if game is completed
		if(gi.isCompleted()){
			gameCompleted = true;
			mainMenu.setEnabled(true);
			mainMenu.setVisible(true);
			playAgain.setEnabled(true);
			playAgain.setVisible(true);
			
		}
		// update display
		repaint();	
	}

	/* (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyReleased(java.awt.event.KeyEvent)
	 */
	@Override
	public void keyReleased(KeyEvent e) {
	}
}
