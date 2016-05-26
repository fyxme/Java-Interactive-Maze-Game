/**
 * Class that contains a games Player and the Maze he will be playing
 */
public class GameInstance {
	private Player player;
	private Maze maze;
	
	public GameInstance (int width, int height, int sight, String name) {
		this.maze = new Maze(width,height,sight);
		
		this.player = new Player(name);
		this.player.setPosition(this.maze.getEndTile(this.maze.getStartTile()));
	}

	/**
	 * Print the Maze linked to this game Instance
	 */
	public String printMaze(int sight) {
		if(sight == 0) {
			return maze.getMazeFromArray(player.getPosition());
		}else {
			return maze.getImmediateFromArray(player.getPosition(), sight);
		}
	}
	
	/**
	 * Attempt to make a move
	 * @param mv the integer corresponding to the move
	 */
	public void move(int mv) {
		player.move(mv);
	}
	
}
