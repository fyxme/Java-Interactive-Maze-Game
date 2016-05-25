/**
 * Class that contains a games Player and the Maze he will be playing
 */
public class GameInstance {
	private Player player;
	private Maze maze;
	
	public GameInstance (int width, int height, String name) {
		this.maze = new Maze(width,height);
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
		player.move(mv);
	}

	/**
	 * @return Return the maze as a String
	 */
	public String getMazeAsString() {
		return maze.getMazeAsString(player.getPosition());
		
	}
	
}
