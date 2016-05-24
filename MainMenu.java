import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.awt.font.FontRenderContext;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.awt.image.BufferedImage;
import java.io.File;
import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;



public class MainMenu extends JPanel {
	public static final int PREFERRED_GRID_SIZE_PIXELS = 20;
	public static final int WALL_WIDTH = 20;

	private static final int DEFAULT_WIDTH = 20;
	private static final int DEFAULT_HEIGHT = 20;
	private static final String DEFAULT_NAME = "Ronin the Conqueror of Worlds";

    private GameDisplay mainDisplay;

	BufferedImage backgroundImage;
	 public MainMenu(GameDisplay mainDisplay){
//    	this.terrainGrid = new Color[NUM_ROWS][NUM_COLS];
        // generate new game instance
    	setFocusable(true);
    	setFocusTraversalKeysEnabled(false);
    	
 
        int preferredWidth = (DEFAULT_WIDTH * 2 + 1) * PREFERRED_GRID_SIZE_PIXELS;
        int preferredHeight = (DEFAULT_HEIGHT * 2 + 1) * PREFERRED_GRID_SIZE_PIXELS;
        setPreferredSize(new Dimension(preferredWidth, preferredHeight));

        this.mainDisplay = mainDisplay;

        try {                
        	this.backgroundImage = ImageIO.read(new File("bliss.jpg"));
        } catch (IOException ex) {
            // handle exception...
        }
    	
    }

    public void paintComponent(Graphics g){
    	super.paintComponent(g);
    	g.drawImage(backgroundImage, 0, 0, null);
    	Dimension menuDimension = this.getSize();
    	int buttonHeight = (int)(menuDimension.getHeight()/10);
    	int buttonWidth = (int)(menuDimension.getWidth()/4);
    	Font font = new Font("arial", Font.BOLD, 20);
    	
    	//create buttons
    	final Rectangle play = new Rectangle(buttonWidth*3/2, 
    		buttonHeight*9/2, buttonWidth, buttonHeight);
    	final Rectangle settings = new Rectangle(buttonWidth*3/2, 
    		(buttonHeight*9/2 + buttonHeight), buttonWidth, buttonHeight);
    	final Rectangle help = new Rectangle(buttonWidth*3/2, 
    		(buttonHeight*9/2 + buttonHeight*2), buttonWidth, buttonHeight);
    	
    	centreString(g, play, "Play", font);
    	centreString(g, settings, "Settings", font);
    	centreString(g, help, "Help", font);
    	//centreString(g, play, "Play", font);
    	addMouseListener(new MouseAdapter() {
   			@Override
   			public void mouseClicked(MouseEvent e) {
   				if (play.contains(e.getX(), e.getY())){
        			System.out.println("clicked play");
                    firePropertyChange("Map", 0 , 0);
                    mainDisplay.swapPanel("Map");
        		}

        		if (settings.contains(e.getX(), e.getY())){
        			System.out.println("clicked settings");
        		}

        		if (help.contains(e.getX(), e.getY())){
        			System.out.println("clicked help");
        		}
   			}
		});


        g.setFont(font);
        g.setColor(Color.black);
        g.drawString("BATTLEFIELD ONE", ((DEFAULT_WIDTH * 2 + 1) * PREFERRED_GRID_SIZE_PIXELS)/4, 100);

    }

   

    public void centreString(Graphics g, Rectangle r, String s, Font font) {
    	FontRenderContext frc = 
            new FontRenderContext(null, true, true);

	    Rectangle2D r2D = font.getStringBounds(s, frc);
	    int rWidth = (int) Math.round(r2D.getWidth());
	    int rHeight = (int) Math.round(r2D.getHeight());
	    int rX = (int) Math.round(r2D.getX());
	    int rY = (int) Math.round(r2D.getY());

	    int a = (r.width / 2) - (rWidth / 2) - rX;
	    int b = (r.height / 2) - (rHeight / 2) - rY;

	    g.setFont(font);
	    g.drawString(s, r.x + a, r.y + b);
	}
    /*
    public static void main(String[] args) {
        // http://docs.oracle.com/javase/tutorial/uiswing/concurrency/initial.html
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JFrame frame = new JFrame("Battlefield ONE: coming Soon\u2122");
                MainMenu map = new MainMenu();
                frame.add(map);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                frame.setVisible(true);
                frame.setFocusable(true);
            }
        });
    }
    */
}