/**
 * Class that contains a games Player and the Maze he will be playing
 */
public class GameInstance {
	private Player player;
	private Maze maze; 
	
	/**
	 * Constants used for movement case so 
	 * it allows for further customisation
	 * (ie. mapping 'move left' to a different key...)
	 */
	private static final int LEFT = 0;
	private static final int RIGHT = 1;
	private static final int UP = 2;
	private static final int DOWN = 3;
	
	public GameInstance (int width, int height, int sight, String name) {
		this.maze = new Maze(width,height,sight);
		
		this.player = new Player(name);
		
		this.player.setPosition(this.maze.getEndTile(this.maze.getStartTile()));
	}

	/**
	 * Print the Maze linked to this game Instance
	 */
	public void printMaze() {
		maze.printMaze(player.getPosition());
	}
	
	/**
	 * Attempt to make a move
	 * @param mv the integer corresponding to the move
	 */
	public void move(int mv) {
		if (mv == LEFT) {
			player.moveLeft();
		} else if (mv == RIGHT) {
			player.moveRight();
		} else if (mv == UP) {
			player.moveUp();
		} else if (mv == DOWN) {
			player.moveDown();
		}
	}

	public String getMazeAsString() {
		return maze.getMazeAsString(player.getPosition());
		
	}
	
}
