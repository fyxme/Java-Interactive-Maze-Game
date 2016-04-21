
public class GameInstance {
	Player player;
	Maze maze; 
	
	public GameInstance (int width, int height, String name) {
		this.maze = new Maze(width,height);
		
		this.player = new Player(name);
		
		this.player.setPosition(this.maze.getStartTile());
	}

	public void printMaze() {
		maze.printMaze();
	}
	
	public void move(int mv) {
		// TODO Auto-generated method stub
	}
	
}
