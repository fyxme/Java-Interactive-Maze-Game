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

import javax.swing.JPanel;
import javax.swing.JFormattedTextField;
import java.text.NumberFormat;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;

public class SettingsMenu extends JPanel {

    private GameDisplay displayController;
    private final Color buttonColor;
    private BufferedImage backgroundImage;
    private JFormattedTextField size;
    private JFormattedTextField vision;

    /**
     * @param displayController	a GameDisplay object
     * @precondition			a GameDisplay must exist
     * @postcondition			a SettingsMenu object is constructed
     */
    public SettingsMenu(GameDisplay displayController){
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

        this.displayController = displayController;
        
        addComponents();
        addFields();

        buttonColor = new Color((float)50/255, (float)50/255, (float)50/255, (float)0.65);

    }

    /**
     * @precondition	SettingsMenu constructor is run
     * @postcondition	JFormattedTextFields are added to the panel
     */
    private void addFields(){
        Dimension menuDimension = this.getPreferredSize();
        int fieldHeight = (int)(menuDimension.getHeight()/20);
        int fieldWidth = (int)(menuDimension.getWidth()/4);
        size = new JFormattedTextField(NumberFormat.getNumberInstance());
        vision = new JFormattedTextField(NumberFormat.getNumberInstance());
        
        System.out.println(displayController.getMazeSize());
        size.setValue(new Integer(displayController.getMazeSize()));
        vision.setValue(new Integer(displayController.getSight()));

        size.addPropertyChangeListener("value1", new PropertyChangeListener(){
            @Override
            public void propertyChange(PropertyChangeEvent e) {
                size.setValue(e.getNewValue());
                System.out.println(e.getNewValue());
            }
        });

        vision.addPropertyChangeListener("value2", new PropertyChangeListener(){
            @Override
            public void propertyChange(PropertyChangeEvent e) {
                vision.setValue(e.getNewValue());
                System.out.println(e.getNewValue());
            }
        });
        //size.setColumnns(100);
        size.setSize(fieldWidth, fieldHeight);
        size.setSize(fieldWidth, fieldHeight);
        size.setLocation(fieldWidth*2 + 20, fieldHeight*10);

        vision.setSize(fieldWidth, fieldHeight);
        vision.setSize(fieldWidth, fieldHeight);
        vision.setLocation(fieldWidth*2 + 20, fieldHeight*12);

        size.setVisible(true);
        vision.setVisible(true);

        add(size);
        add(vision);

    }

    /**
     * @precondition	SettingsMenu constructor is run
     * @postcondition	JButtons are added to Settings Menu
     */
    private void addComponents(){

        Dimension menuDimension = this.getPreferredSize();
        int buttonHeight = (int)(menuDimension.getHeight()/10);
        int buttonWidth = (int)(menuDimension.getWidth()/4);
        JButton easyPreset = new JButton("Easy");
        JButton medPreset = new JButton("Medium");
        JButton hardPreset = new JButton("Hard");
        JButton done = new JButton("Done");
        Font buttonFont = new Font("Arial", Font.PLAIN, 20);

        easyPreset.setBounds(buttonWidth*1/2, 
            buttonHeight*3, buttonWidth, buttonHeight);
        medPreset.setBounds((buttonWidth*1/2 + buttonWidth), 
            buttonHeight*3, buttonWidth, buttonHeight);
        hardPreset.setBounds((buttonWidth*1/2 + buttonWidth*2), 
            buttonHeight*3, buttonWidth, buttonHeight);
        done.setBounds((buttonWidth*1/2 + buttonWidth), 
            buttonHeight*7, buttonWidth, buttonHeight);

        easyPreset.setSize(buttonWidth, buttonHeight);
        medPreset.setSize(buttonWidth, buttonHeight);
        hardPreset.setSize(buttonWidth, buttonHeight);

        easyPreset.setLocation(buttonWidth*1/2, buttonHeight*3);

        easyPreset.setVisible(true);
        medPreset.setVisible(true);
        hardPreset.setVisible(true);

        easyPreset.setFont(buttonFont);
        medPreset.setFont(buttonFont);
        hardPreset.setFont(buttonFont);
        done.setFont(buttonFont);

        easyPreset.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                size.firePropertyChange("value1", 0, 10);
                vision.firePropertyChange("value2", -1, 0);
            }
        });

        medPreset.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                size.firePropertyChange("value1", 0, 20);
                vision.firePropertyChange("value2", 0, 5);
            }
        });

        hardPreset.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                size.firePropertyChange("value1", 0, 30);
                vision.firePropertyChange("value2", 0, 3);
            }
        });

        done.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                System.out.println(vision.getValue());
                displayController.setMazeSettings(((Number)size.getValue()).intValue(), ((Number)vision.getValue()).intValue());

                displayController.swapPanel("MainMenu");
            }
        });

        add(easyPreset);
        add(medPreset);
        add(hardPreset);
        add(done);

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
        int labelHeight = (int)(menuDimension.getHeight()/20);
        int labelWidth = (int)(menuDimension.getWidth()/4);
        Font titleFont = new Font("arial", Font.BOLD, 20);
        Font labelFont = new Font("arial", Font.PLAIN, 20);
       
        Rectangle title = new Rectangle(titleWidth*1/2, 
            titleHeight*3/2, titleWidth, titleHeight);
        Rectangle mazeSize = new Rectangle(labelWidth*1/2, 
            labelHeight*29/3, labelWidth, labelHeight*4/3);
        Rectangle visionSize = new Rectangle(labelWidth*1/2, 
            labelHeight*35/3, labelWidth, labelHeight*4/3);
        
        fillButton(g, mazeSize);
        fillButton(g, visionSize);
        fillButton(g, title);
        centreString(g, title, "Settings", titleFont, Color.WHITE);
        centreString(g, mazeSize, "Maze Size:", labelFont, Color.WHITE);
        centreString(g, visionSize, "Vision Size:", labelFont, Color.WHITE);

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
