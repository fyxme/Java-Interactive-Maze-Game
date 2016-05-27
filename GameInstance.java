/**
 * Class that contains a games Player and the Maze he will be playing
 */
public class GameInstance {
	private Player player;
	private Maze maze;
	
	public GameInstance (int width, int height, int sight, String name) {
		this.maze = new Maze(width,height);
		
		this.player = new Player(name);
		this.player.setPosition(this.maze.getStartTile());
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
	
	/**
	 * Returns the maze being used
	 * @return
	 */
	public Maze getMaze() {
		return maze;
	}
	
	/**
	 * @return True if the game is complete else returns false
	 */
	public boolean isCompleted(){
		if(maze.getEndTile() == player.getPosition()){
			return true;
		}
		return false;
	}
}
