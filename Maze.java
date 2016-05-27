import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Class containing the Maze
 */
public class Maze {
	private Tile initial_tile = null;
	private Tile[][] maze = null;
	private int width,height = 0;
	
	/**
	 * Maze constructor class
	 * @param width Width of the maze to generate
	 * @param height Height of the maze to generate
	 */
	public Maze (int width, int height) {
		this.maze = new Tile[width][height];
		// initialise maze
		this.width = width;
		this.height = height;
		initialiseMaze(width,height);
		generateMaze();
	}
	
	/**
	 * Initialise the Maze based on given arguments
	 * @param width Width to generate
	 * @param height Height to generate
	 */
	private void initialiseMaze(int width, int height) {
		
		Tile[][] temp = new Tile[width][height];

		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				Tile n = new Tile();
				maze[x][y] = n;
				Connection c = null;
				temp[x][y] = n;
				
				// vertical connection
				if (y != 0) {
					c = new Connection (temp[x][y-1],n); 
					temp[x][y-1].setDown(c);
					n.setUp(c);
				}
				
				// horizontal connection
				if (x != 0) {
					c = new Connection(temp[x-1][y],n);
					temp[x-1][y].setRight(c);
					n.setLeft(c);
				}
			}
		}

		initial_tile = temp[0][0]; // keep start tile only
		
		temp = null;
	}
	
	/**
	 * Get the whole maze as an array
	 * @param player_pos The tile on which the player is
	 * @return a String containing the Maze
	 */
	public String getMazeFromArray(Tile player_pos) {
		String ret = "";
		
		//print top wall
		for (int z = 0; z < this.width; z++)
			ret += "+-";
		ret += "+\n";
		//print the rest
		for (int y = 0; y < this.height; y++) {
			ret += "|"; // first wall on line
			String temp = "+";
			for (int x = 0; x < this.width; x++) {
				// current tile
				if(maze[x][y] == player_pos) {
					ret += "P";
				} else if(maze[x][y] == getEndTile()) {
					ret += "E";
				} else if(maze[x][y].hasBeenVisited() > 0) {
					if (maze[x][y].hasBeenVisited()%2 == 1) {
						ret += "V";
					} else {
						ret += "v";
					}
				} else {
					ret += " ";
				}
				// right wall
				if (maze[x][y].getRight() != null && !maze[x][y].getRight().isWall()) {
					// stupid cases.... checks if the player is ahead of it so it can colour in the walls between the current and the player	
					if( ((maze[x][y].hasBeenVisited() > 0) || (maze[x][y] == player_pos))
					 && ((maze[x][y].getRight().getConnectedTile(maze[x][y]).hasBeenVisited() > 0)
					  || (maze[x][y].getRight().getConnectedTile(maze[x][y]) == player_pos)) ) {
						
						if( ((maze[x][y].hasBeenVisited()%2 == 1) || (maze[x][y] == player_pos))
					     && ((maze[x][y].getRight().getConnectedTile(maze[x][y]).hasBeenVisited()%2 == 1)
					      || (maze[x][y].getRight().getConnectedTile(maze[x][y]) == player_pos)) ) {
							ret += "V";
						} else {
							ret += "v";
						}
					} else {
						ret += " ";
					}
				} else {
					ret += "|";
				}
				// bottom wall
				if (maze[x][y].getDown() != null && !maze[x][y].getDown().isWall()) {
					// stupid cases.... checks if the player is ahead of it so it can colour in the walls between the current and the player
					if( ((maze[x][y].hasBeenVisited() > 0) || (maze[x][y] == player_pos))
					 && ((maze[x][y].getDown().getConnectedTile(maze[x][y]).hasBeenVisited() > 0)
					  || (maze[x][y].getDown().getConnectedTile(maze[x][y]) == player_pos)) ) {
						
						if( ((maze[x][y].hasBeenVisited()%2 == 1) || (maze[x][y] == player_pos))
					     && ((maze[x][y].getDown().getConnectedTile(maze[x][y]).hasBeenVisited()%2 == 1)
 					      || (maze[x][y].getDown().getConnectedTile(maze[x][y]) == player_pos)) ) {
							temp += "V+";
						} else {
							temp += "v+";
						}
					} else {
						temp += " +";
					}
				} else {
					temp += "-+";
				}
			}
			ret += "\n" + temp + "\n";
		}
		return ret;
	}
	
	/**
	 * Get the surroundings based on the perpendicular sight distance specified
	 * @param player_pos The Tile on which the player is
	 * @param sightRadius The sight radius
	 * @return a String containing the surroundings of the player based on sight radius
	 */
	public String getImmediateFromArray(Tile player_pos, int sightRadius) {
		//finding the position of the Player
		int playerX = 0, playerY = 0;
		outerloop:
		for (int y = 0; y < this.height; y++) {
			for (int x = 0; x < this.width; x++) {
				if(maze[x][y] == player_pos) {
					playerX = x;
					playerY = y;
					break outerloop;
				}
			}
		}
		
		//printing around the player
		String ret = "";
		//print top wall
		for (int z = 0; z < sightRadius * 2 + 1; z++)
			ret += "++";
		ret += "+\n";
		//print the rest
		for (int y = playerY-sightRadius; y <= playerY+sightRadius; y++) {
			ret += "|"; // first wall on line
			String temp = "+";
			for (int x = playerX-sightRadius; x <= playerX+sightRadius; x++) {
				// current tile
				if( (x >= this.width) || (x < 0) || (y >= this.height) || (y < 0) ) {
					ret += "+";
				} else {
					if(maze[x][y] == player_pos) {
						ret += "P";
					} else if(maze[x][y] == getEndTile()) {
						ret += "E";
					}else if(maze[x][y].hasBeenVisited() > 0) {
						if (maze[x][y].hasBeenVisited()%2 == 1) {
							ret += "V";
						} else {
							ret += "v";
						}
					} else {
						ret += " ";
					}
				}
				// right wall
				if(x == playerX+sightRadius) {
					ret += "|";
				}else if( (x >= this.width) || (x < 0) || (y >= this.height) || (y < 0) ) {
					ret += "+";
				} else {
					if (maze[x][y].getRight() != null && !maze[x][y].getRight().isWall()) {
						// stupid cases.... checks if the player is ahead of it so it can colour in the walls between the current and the player	
						if( ((maze[x][y].hasBeenVisited() > 0) || (maze[x][y] == player_pos))
						 && ((maze[x][y].getRight().getConnectedTile(maze[x][y]).hasBeenVisited() > 0)
						  || (maze[x][y].getRight().getConnectedTile(maze[x][y]) == player_pos)) ) {
							
							if( ((maze[x][y].hasBeenVisited()%2 == 1) || (maze[x][y] == player_pos))
						     && ((maze[x][y].getRight().getConnectedTile(maze[x][y]).hasBeenVisited()%2 == 1)
						      || (maze[x][y].getRight().getConnectedTile(maze[x][y]) == player_pos)) ) {
								ret += "V";
							} else {
								ret += "v";
							}
						} else {
							ret += " ";
						}
					} else {
						ret += "|";
					}
				}
				// bottom wall
				if(y == playerY+sightRadius) {
					temp += "++";
				}else if( (x >= this.width) || (x < 0) || (y >= this.height) || (y < 0) ) {
					temp += "++";
				} else {
					if (maze[x][y].getDown() != null && !maze[x][y].getDown().isWall()) {
						// stupid cases.... checks if the player is ahead of it so it can colour in the walls between the current and the player
						if( ((maze[x][y].hasBeenVisited() > 0) || (maze[x][y] == player_pos))
						 && ((maze[x][y].getDown().getConnectedTile(maze[x][y]).hasBeenVisited() > 0)
						  || (maze[x][y].getDown().getConnectedTile(maze[x][y]) == player_pos)) ) {
							
							if( ((maze[x][y].hasBeenVisited()%2 == 1) || (maze[x][y] == player_pos))
						     && ((maze[x][y].getDown().getConnectedTile(maze[x][y]).hasBeenVisited()%2 == 1)
	 					      || (maze[x][y].getDown().getConnectedTile(maze[x][y]) == player_pos)) ) {
								temp += "V+";
							} else {
								temp += "v+";
							}
						} else {
							temp += " +";
						}
					} else {
						temp += "-+";
					}
				}
			}
			ret += "\n" + temp + "\n";
		}
		return ret;
	}
	
	/**
	 * Helper function to get a random in between 2 values
	 * @param a Min value
	 * @param b Max value
	 * @return an random integer between the two values
	 */
	private int getRandBetween(int a, int b) {
		
		return (int)(Math.random() * b + a);
	}
	
	/**
	 * Generate a maze using recursive backtracking
	 * @param current Current tile
	 * @param unvisited List of unvisited Tiles
	 * @param stack Stack of Tiles
	 */
	private void recursiveBacktracker(Tile current, List<Tile> unvisited,Stack <Tile> stack) {
		
		if (unvisited.isEmpty()) return;
	
		// get the unvisited neighboors of this cell
		List<Tile> unvisited_neigh = current.getNeighboors();

		unvisited_neigh.retainAll(unvisited);
		
		// check if any are in the unvisited list
		if (!unvisited_neigh.isEmpty()) {
			// choose one of the unvisited neighboors randomly and mark it as visited
			int r_num = getRandBetween(0,unvisited_neigh.size());
			Tile new_curr = unvisited_neigh.get(r_num);
			unvisited.remove(new_curr); // mark as visited
			
			// push the current cell to the stack
			stack.push(current);
			
			unvisited_neigh = null; // make space
			
			// remove the wall between the current cell and the chosen cell
			current.removeWall(new_curr);
			
			// call recursiveBacktracker(chosen_cell,unvisited,stack)
			recursiveBacktracker(new_curr,unvisited,stack);

		// Else if stack.notempty
		} else if (!stack.isEmpty()) {
			// pop stack
			recursiveBacktracker(stack.pop(),unvisited,stack);
		}
		
	}
	
	/**
	 * Generate the Maze using recursive backtracking algorithm
	 */
	private void generateMaze() {
		// add all cells to the unvisited list
		List<Tile> unvisited = getAllTiles(initial_tile,null);
		
		// remove start tile from unvisited list
		unvisited.remove(initial_tile);
		
		recursiveBacktracker(initial_tile,unvisited,new Stack<Tile>());
	}
	
	/**
	 * Helper function to get the all the tiles in a line
	 * @param current Current tile
	 * @param tiles List of tiles in the line
	 * @return a List of Tile
	 */
	private List<Tile> getTilesForLine(Tile current,List<Tile>tiles) {
		tiles.add(current);
		if (current.getRight() == null) return tiles;
		return getTilesForLine(current.getRight().getConnectedTile(current),tiles);
	}
	
	/**
	 * Helper function to get all Tiles of the Maze
	 * @param current Current tile
	 * @param tiles List of all tiles inside of the Maze
	 * @return the List of Tiles
	 */
	private List<Tile> getAllTiles(Tile current,List<Tile>tiles) {
		if (tiles == null) tiles = new ArrayList<Tile>();

		if (current.getDown() == null) return getTilesForLine(current,tiles);
		
		return getAllTiles(current.getDown().getConnectedTile(current),getTilesForLine(current,tiles));
	}

	/**
	 * Get the last tile in the Maze. 
	 * Considering the player starts in the bottom right, 
	 * the end Tile is in the Top Left 
	 * @return The last tile in the maze
	 */
	public Tile getEndTile() {
		return this.initial_tile;
	}
	
	/**
	 * Get the Starting Tile
	 * The starting tile is the most bottom right Tile.
	 * This is a design decision made 
	 * so that there are more choices to make inside of the maze
	 * @return
	 */
	public Tile getStartTile() {
		return maze[width-1][height-1];
	}
	
	/**
	 * @return The number of red tiles
	 */
	public int getRedCount() {
		int c = 0;
		for(int i = 0; i < this.width; i++) {
			for(int j = 0; j < this.height; j++) {
				if (maze[i][j].hasBeenVisited() == 2) {
					c++;
				}
			}
		}
		return c;
	}
	
	/**
	 * @return The number of green Tiles
	 */
	public int getGreenCount() {
		int c = 0;
		for(int i = 0; i < this.width; i++) {
			for(int j = 0; j < this.height; j++) {
				if (maze[i][j].hasBeenVisited() == 1) {
					c++;
				}
			}
		}
		return c;
	}
	
	/**
	 * @return The number of unvisited Tiles
	 */
	public int getUnvisitedCount() {
		return this.getTotalCount() - this.getGreenCount() - this.getRedCount();
	}
	
	/**
	 * @return The total number of Tiles
	 */
	public int getTotalCount() {
		return this.width * this.height;
	}
}
