import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

// Different maze algorithms
// http://www.jamisbuck.org/presentations/rubyconf2011/index.html
/**
 * Class containing the Maze
 *
 */
public class Maze {
	private Tile start_tile = null;
	private Tile[][] maze = null;
	private int width,height = 0;
	
	
	public Maze (int width, int height) {
		this.maze = new Tile[width][height];
		// initialise maze
		this.width = width;
		this.height = height;
		initialiseMaze(width,height);
		generateMaze();
	}
	
	private void initialiseMaze( int width, int height) {
		
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

		start_tile = temp[0][0]; // keep start tile only
		
		temp = null;
	}
	
	public String getMazeFromArray(Tile player_pos) {
		String ret = "";
		
		for (int z = 0; z < this.width; z++)
			ret += "+-";
		
		ret += "+\n";
		
		for (int y = 0; y < this.height; y++) {
			ret += "|"; // first wall on line
			String temp = "+";
			for (int x = 0; x < this.width; x++) {
				// current tile
				ret += player_pos == maze[x][y]? "P" : " ";
				// right wall
				ret += "";
				// bottom wall
				temp += (maze[x][y].getDown() != null && !maze[x][y].getDown().isWall())? " +" : "-+";
			}
			ret += "\n" + temp + "\n";
		}
		return ret;
	}
	
	private int getRandBetween(int a, int b) {
		
		return (int)(Math.random() * b + a);
	}
	
	public void recursiveBacktracker(Tile current, List<Tile> unvisited,Stack <Tile> stack) {
		
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
	
	public String printMaze(Tile player_pos) {
		Tile curr = start_tile;
		String ret = "";
		// print top wall
		while (curr != null) {
			ret += "+-";
			curr = curr.getDown() == null? null : curr.getDown().getConnectedTile(curr);
		}
		ret+="+\n";
		curr = start_tile;
		// print rest of maze
		while (curr != null) {
			ret += recursiveRowPrint(curr, new String(), new String(),player_pos);
			curr = curr.getDown() == null? null : curr.getDown().getConnectedTile(curr);
		}
		return ret;
	}
	
	
	private String recursiveRowPrint(Tile current,String l1, String l2, Tile player_pos) {
		
		// print left wall for border of maze
		if (current.getLeft() == null) {
			l1 += "|";
			l2 += "+";
		}

		// print tile
		if (current == player_pos) {
			l1 += "P";
		} else {
			l1 += " ";
		}
		
		// print below wall
		if (current.getDown() != null && !current.getDown().isWall()) {
			l2 += " ";
		} else {
			l2 += "-";
		}
		
		if (current.getRight() != null) {
			if (!current.getRight().isWall()) {
				l1 += " ";
				l2 += "+";
			} else {
				l1 += "|";
				l2 += "+";
			}
			return recursiveRowPrint(current.getRight().getConnectedTile(current),l1,l2,player_pos);
		} else {
			l1 += "|";
			l2 += "+";
			// end of line print wall
			return l1 +"\n"+ l2+"\n";
//			System.out.println(l1);
//			System.out.println(l2);
		}
	}
	
	public void generateMaze() {
		// add all cells to the unvisited list
		List<Tile> unvisited = getAllTiles(start_tile,null);
		
		// remove start tile from unvisited list
		unvisited.remove(start_tile);
		
		recursiveBacktracker(start_tile,unvisited,new Stack<Tile>());
	}
	
	private List<Tile> getTilesForLine(Tile current,List<Tile>tiles) {
		tiles.add(current);
		if (current.getRight() == null) return tiles;
		return getTilesForLine(current.getRight().getConnectedTile(current),tiles);
	}
	
	private List<Tile> getAllTiles(Tile current,List<Tile>tiles) {
		if (tiles == null) tiles = new ArrayList<Tile>();

		if (current.getDown() == null) return getTilesForLine(current,tiles);
		
		return getAllTiles(current.getDown().getConnectedTile(current),getTilesForLine(current,tiles));
	}

	public Tile getStartTile() {
		return this.start_tile;
	}

	public String getMazeAsString(Tile position) {
		return printMaze(position);
	}
}
