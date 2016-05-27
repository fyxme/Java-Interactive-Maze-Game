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
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


import javax.swing.JPanel;


public class MainMenu extends JPanel {

    private GameDisplay displayController;
    private final Color buttonColor;
    private int gap = 10;
    private BufferedImage backgroundImage;
    final JButton play = new JButton("Play");
    final JButton settings = new JButton("Settings");
    final JButton quit = new JButton("Quit");

    /**
     * @param displayController	a GameDisplay object
     * @precondition			a GameDisplay must exist
     * @postcondition			a MainMenu object is constructed
     */
    public MainMenu(GameDisplay displayController){
        setFocusable(true);
        setLayout(null);

        int preferredWidth = 473;
        int preferredHeight = 473;

        System.out.println(preferredHeight + ", " + preferredWidth);
        setPreferredSize(new Dimension(preferredWidth, preferredHeight));

        try {                
            this.backgroundImage = ImageIO.read(new File("./src/bliss.jpg"));
        } catch (IOException ex) {
            System.exit(1);
        }

        addButtons();

        buttonColor = new Color((float)50/255, (float)50/255, (float)50/255, (float)0.65);

        this.displayController = displayController;   
    }
    

    
    /**
     * @precondition	MainMenu constructor is run
     * @postcondition	Buttons are added to MainMenu
     */
    private void addButtons(){

        Dimension menuDimension = this.getPreferredSize();
        int buttonHeight = (int)(menuDimension.getHeight()/10);
        int buttonWidth = (int)(menuDimension.getWidth()/4);
        Font buttonFont = new Font("Arial", Font.PLAIN, 20);

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

        play.setFont(buttonFont);
        settings.setFont(buttonFont);
        quit.setFont(buttonFont);

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

        play.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
               switch (e.getKeyCode()) {
               case KeyEvent.VK_UP:
                  quit.requestFocus();
                  break;
               case KeyEvent.VK_DOWN:
                  settings.requestFocus();
                  break;
               case KeyEvent.VK_LEFT:
                  quit.requestFocus();
                  break;
               case KeyEvent.VK_RIGHT:
                  settings.requestFocus();
                  break;
               case KeyEvent.VK_ENTER:
            	   ((JButton) e.getComponent()).doClick();
            	   displayController.swapPanel("Map");
            	   break;
               default:
                  break;
               }
            }
        });
        
        settings.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
               switch (e.getKeyCode()) {
               case KeyEvent.VK_UP:
                  play.requestFocus();
                  break;
               case KeyEvent.VK_DOWN:
                  quit.requestFocus();
                  break;
               case KeyEvent.VK_LEFT:
                  play.requestFocus();
                  break;
               case KeyEvent.VK_RIGHT:
            	  quit.requestFocus();
                  break;
               case KeyEvent.VK_ENTER:
            	   ((JButton) e.getComponent()).doClick();
            	   displayController.swapPanel("SettingsMenu");
            	   break;
               default:
                  break;
               }
            }
        });
        
        quit.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
               switch (e.getKeyCode()) {
               case KeyEvent.VK_UP:
                  settings.requestFocus();
                  break;
               case KeyEvent.VK_DOWN:
                  play.requestFocus();
                  break;
               case KeyEvent.VK_LEFT:
                  settings.requestFocus();
                  break;
               case KeyEvent.VK_RIGHT:
                  play.requestFocus();
                  break;
               case KeyEvent.VK_ENTER:
            	   ((JButton) e.getComponent()).doClick();
            	   System.exit(1);
            	   break;
               default:
                  break;
               }
            }
        });
        add(play);
        add(settings);
        add(quit);
    }

    /* (non-Javadoc)
     * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
     */
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

    /**
     * @param g			Graphics to be painted on
     * @param r			Rectangle to be filled
     * @precondition	Parameters exist and called within paintComponent
     * @postcondition	Rectangle r is filled with fillRoundRect
     */
    private void fillButton(Graphics g, Rectangle r){
        g.setColor(buttonColor);
        g.fillRoundRect((int)r.getX(), (int)r.getY(), 
            (int)r.getWidth(), (int)r.getHeight(), 10, 10);
    }

}
