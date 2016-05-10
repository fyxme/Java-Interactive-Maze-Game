import java.util.ArrayList;
import java.util.List;

/**
 * Class that represents a tile on the board
 */
public class Tile {
	/**
	 * Connections to other NSWE tiles
	 */
	private Connection right = null;
	private Connection left = null;
	private Connection up = null;
	private Connection down = null;

	/**
	 * @return the left tile connected to this tile
	 */
	public Connection getLeft() {
		return this.left;
	}
	
	/**
	 * Connect a tile to the left of this tile
	 * @param left the tile to connect to the left of this tile
	 */
	public void setLeft(Connection left) {
		this.left = left;
	}
	
	/**
	 * @return the right tile connected to this tile
	 */
	public Connection getRight() {
		return this.right;
	}
	
	/**
	 * Connect a tile to the right of this tile
	 * @param right the tile to connect to the right of this tile
	 */
	public void setRight(Connection right) {
		this.right = right;
	}
	
	/**
	 * Connect a tile to the above of this tile
	 * @param above the tile to connect to the above of this tile
	 */
	public Connection getUp() {
		return this.up;
	}
	
	/**
	 * @return the above tile connected to this tile
	 */
	public void setUp(Connection up) {
		this.up = up;
	}
	
	/**
	 * @return the below tile connected to this tile
	 */
	public Connection getDown() {
		return this.down;
	}
	
	/**
	 * Connect a tile to the below of this tile
	 * @param below the tile to connect to the below of this tile
	 */
	public void setDown(Connection down) {
		this.down = down;
	}

	/**
	 * @return a list of tile that are connected with this tile
	 */
	public List<Tile> getNeighboors() {
		List<Tile> out = new ArrayList<Tile>();
		if (right != null)
			out.add(right.getConnectedTile(this));
		if (left != null)
			out.add(left.getConnectedTile(this));
		if (up != null)
			out.add(up.getConnectedTile(this));
		if (down != null)
			out.add(down.getConnectedTile(this));
		return out;
	}

	/**
	 * Remove a wall between this tile and the given tile
	 * @param t the wall to remove
	 */
	public void removeWall(Tile t) {
		if (right != null)
			right.removeWall(t);
		if (left != null)
			left.removeWall(t);
		if (up != null)
			up.removeWall(t);
		if (down != null)
			down.removeWall(t);
	}
}
