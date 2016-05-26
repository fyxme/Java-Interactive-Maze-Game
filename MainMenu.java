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
import javax.swing.JButton;


import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.ImageIcon;



public class MainMenu extends JPanel {

    private GameDisplay displayController;
    private final Color buttonColor;
    private int gap = 10;
    private BufferedImage backgroundImage;

    public MainMenu(GameDisplay displayController){
        setFocusable(true);
        setLayout(null);

        int preferredWidth = 473;
        int preferredHeight = 473;

        System.out.println(preferredHeight + ", " + preferredWidth);
        setPreferredSize(new Dimension(preferredWidth, preferredHeight));

        try {                
            this.backgroundImage = ImageIO.read(new File("bliss.jpg"));
        } catch (IOException ex) {
            // handle exception...
        }
/*        
<<<<<<< HEAD

        addButtons();

      

        buttonColor = new Color((float)50/255, (float)50/255, (float)50/255, (float)0.65);

        this.displayController = displayController;   
    }



    private void addButtons(){

        Dimension menuDimension = this.getPreferredSize();
        int buttonHeight = (int)(menuDimension.getHeight()/10);
        int buttonWidth = (int)(menuDimension.getWidth()/4);
        JButton play = new JButton("Play", new ImageIcon(backgroundImage));
        JButton settings = new JButton("Settings");
        JButton quit = new JButton("Quit");

        play.setBounds(buttonWidth*3/2, 
            buttonHeight*9/2, buttonWidth, buttonHeight);
        settings.setBounds(buttonWidth*3/2, 
            (buttonHeight*9/2 + buttonHeight + gap), buttonWidth, buttonHeight);
        quit.setBounds(buttonWidth*3/2, 
            (buttonHeight*9/2 + (buttonHeight + gap)*2 ), buttonWidth, buttonHeight);

        play.setSize(buttonWidth, buttonHeight);
        settings.setSize(buttonWidth, buttonHeight);
        quit.setSize(buttonWidth, buttonHeight);

        play.setLocation(buttonWidth*3/2, buttonHeight*9/2);

        play.setVisible(true);
        settings.setVisible(true);
        quit.setVisible(true);

        play.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                displayController.swapPanel("Map");
            }
        });

        settings.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                displayController.swapPanel("SettingsMenu");
            }
        });

        quit.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                System.exit(1);
            }
        });

        add(play);
        add(settings);
        add(quit);

    }

    public void paintComponent(Graphics g){
    	super.paintComponent(g);
    	g.drawImage(backgroundImage, 0, 0, null);
        Dimension menuDimension = this.getSize();
        int titleHeight = (int)(menuDimension.getHeight()/10);
        int titleWidth = (int)(menuDimension.getWidth()/2);
        Font font = new Font("arial", Font.BOLD, 20);
        Rectangle title = new Rectangle(titleWidth*1/2, 
            titleHeight*3/2, titleWidth, titleHeight);
        fillButton(g, title);
        centreString(g, title, "Battlefield One", font, Color.WHITE);
=======
*/

        addButtons();

      

        buttonColor = new Color((float)50/255, (float)50/255, (float)50/255, (float)0.65);

        this.displayController = displayController;   
    }
/*
<<<<<<< HEAD
   
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

    private void fillButton(Graphics g, Rectangle r){
        g.setColor(buttonColor);
        g.fillRoundRect((int)r.getX(), (int)r.getY(), 
            (int)r.getWidth(), (int)r.getHeight(), 10, 10);
    }

}
=======
*/

    private void addButtons(){

        Dimension menuDimension = this.getPreferredSize();
        int buttonHeight = (int)(menuDimension.getHeight()/10);
        int buttonWidth = (int)(menuDimension.getWidth()/4);
        JButton play = new JButton("Play", new ImageIcon(backgroundImage));
        JButton settings = new JButton("Settings");
        JButton quit = new JButton("Quit");

        play.setBounds(buttonWidth*3/2, 
            buttonHeight*9/2, buttonWidth, buttonHeight);
        settings.setBounds(buttonWidth*3/2, 
            (buttonHeight*9/2 + buttonHeight + gap), buttonWidth, buttonHeight);
        quit.setBounds(buttonWidth*3/2, 
            (buttonHeight*9/2 + (buttonHeight + gap)*2 ), buttonWidth, buttonHeight);

        play.setSize(buttonWidth, buttonHeight);
        settings.setSize(buttonWidth, buttonHeight);
        quit.setSize(buttonWidth, buttonHeight);

        play.setLocation(buttonWidth*3/2, buttonHeight*9/2);

        play.setVisible(true);
        settings.setVisible(true);
        quit.setVisible(true);

        play.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                displayController.swapPanel("Map");
            }
        });

        settings.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                displayController.swapPanel("SettingsMenu");
            }
        });

        quit.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                System.exit(1);
            }
        });

        add(play);
        add(settings);
        add(quit);

    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, null);
        Dimension menuDimension = this.getSize();
        int titleHeight = (int)(menuDimension.getHeight()/10);
        int titleWidth = (int)(menuDimension.getWidth()/2);
        Font font = new Font("arial", Font.BOLD, 20);
        Rectangle title = new Rectangle(titleWidth*1/2, 
            titleHeight*3/2, titleWidth, titleHeight);
        fillButton(g, title);
        centreString(g, title, "Battlefield One", font, Color.WHITE);

    }

   
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

    private void fillButton(Graphics g, Rectangle r){
        g.setColor(buttonColor);
        g.fillRoundRect((int)r.getX(), (int)r.getY(), 
            (int)r.getWidth(), (int)r.getHeight(), 10, 10);
    }

}
