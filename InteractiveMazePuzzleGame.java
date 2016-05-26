import java.util.Scanner;

/**
 * Main class for the InteractiveMazePuzzleGame.
 * Sets up and starts the game
 * 
 * Written for COMP2911 - Group Project
 * Semester 1 - 2016
 * 
 * @version 0.1
 */
public class InteractiveMazePuzzleGame {
	GameInstance gi = null;
	Scanner sc = null;
	
	private static final int DEFAULT_WIDTH = 40;
	private static final int DEFAULT_HEIGHT = 40;
	private static final String DEFAULT_NAME = "Ronin the Conqueror of Worlds";
	
	/**
	 * Constants used for movement case so 
	 * it allows for further customisation
	 * (ie. mapping 'move left' to a different key...)
	 */
	private static final int LEFT = 0;
	private static final int RIGHT = 1;
	private static final int UP = 2;
	private static final int DOWN = 3;
	
	public InteractiveMazePuzzleGame() {
		gi = new GameInstance(DEFAULT_WIDTH, DEFAULT_HEIGHT, 3,  DEFAULT_NAME);
	}
	
	/**
	 * Main entry point into the program
	 * Create a simple instance of a maze and a player inside of it
	 * @param args
	 */
	public static void main(String[] args) {
		// generate a new game instance
		InteractiveMazePuzzleGame impg = new InteractiveMazePuzzleGame();
		impg.play();
	}
	
	/**
	 * Allows the player to move around the maze in a terminal window 
	 * @precondition gi has been initialised and contains a maze
	 */
	public void play() {
		System.out.println("To move use the arrow keys or WASD");		
		try {
        	sc = new Scanner(System.in);
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                // make move
                if (line.matches("^[aA]"))
                	gi.move(LEFT);
                else if (line.matches("^[dD]"))
                	gi.move(RIGHT);
                else if (line.matches("^[wW]"))
                	gi.move(UP);
                else if (line.matches("^[sS]"))
                	gi.move(DOWN);
            }
        }
        catch (Exception e) {} 
        finally {
            if (sc != null) sc.close();
        }
	}
}
