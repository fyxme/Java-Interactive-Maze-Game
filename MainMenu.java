import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
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

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;



public class MainMenu extends JPanel {

    private boolean isMouseOverPlay = false;
    private boolean isMouseOverSettings = false;
    private boolean isMouseOverHelp = false;

    private GameDisplay displayController;

	BufferedImage backgroundImage;
	public MainMenu(GameDisplay displayController){
    	setFocusable(true);

        int preferredWidth = 473;
        int preferredHeight = 473;

        System.out.println(preferredHeight + ", " + preferredWidth);
        setPreferredSize(new Dimension(preferredWidth, preferredHeight));

        this.displayController = displayController;

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
    	
        if ( isMouseOverPlay ) {
            g.setColor(Color.red);
        } else {
            g.setColor(Color.black);
        }
    	centreString(g, play, "Play", font);

        if ( isMouseOverSettings ) {
            g.setColor(Color.red);
        } else {
            g.setColor(Color.black);
        }
    	centreString(g, settings, "Settings", font);

        if ( isMouseOverHelp ) {
            g.setColor(Color.red);
        } else {
            g.setColor(Color.black);
        }
    	centreString(g, help, "Help", font);
    	//centreString(g, play, "Play", font);

        //add listener to buttons
    	addMouseListener(new MouseAdapter() {
   			@Override
   			public void mouseClicked(MouseEvent e) {
        		    if (play.contains(e.getX(), e.getY())){
        			System.out.println("clicked play");
                                firePropertyChange("Map", 0 , 0);
                                displayController.swapPanel("Map");
        		    }

        		    if (settings.contains(e.getX(), e.getY())){
        			System.out.println("clicked settings");
        		    }

        		    if (help.contains(e.getX(), e.getY())){
        			System.out.println("clicked help");
        		    }
   			}
                        @Override
                        public void mouseEntered(MouseEvent e) {
        		    if (play.contains(e.getX(), e.getY())){
                                isMouseOverPlay = true;
                                System.out.println("entered play");
        		    }

        		    if (settings.contains(e.getX(), e.getY())){
                                isMouseOverSettings = true;
                                System.out.println("entered settings");
        		    }

        		    if (help.contains(e.getX(), e.getY())){
                                isMouseOverHelp = true;
                                System.out.println("entered help");
        		    }
                        }
                        @Override
                        public void mouseExited(MouseEvent e) {
        		    if (play.contains(e.getX(), e.getY())){
                                isMouseOverPlay = false;
                                System.out.println("exited play");
        		    }

        		    if (settings.contains(e.getX(), e.getY())){
                                isMouseOverSettings = false;
                                System.out.println("exited settings");
        		    }

        		    if (help.contains(e.getX(), e.getY())){
                                isMouseOverHelp = false;
                                System.out.println("exited help");
        		    }
                        }

        

		});


        g.setFont(font);
        g.setColor(Color.black);

    }

   
    //centre string inside rectangle
    //from http://stackoverflow.com/questions/27706197/how-can-i-center-graphics-drawstring-in-java
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
}
