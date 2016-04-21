
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
	
	public GameInstance (int width, int height, String name) {
		this.maze = new Maze(width,height);
		
		this.player = new Player(name);
		
		this.player.setPosition(this.maze.getStartTile());
	}

	public void printMaze() {
		maze.printMaze(player.getPosition());
	}
	
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
	
}
