/**
 * Class that holds all the information of a connection between two tiles
 * Also tells if the connection is a wall or a path between the two tiles
 */
public class Connection {
	private Tile a = null;
	private Tile b = null;
	/**
	 * Is there a wall between tile a and tile b?
	 */
	private boolean isWall = true;
	
	public Connection (Tile a, Tile b) {
		this.a = a;
		this.b = b;
	}
	
	/**
	 * Check if there is a wall
	 * @return true if there is a wall else returns false
	 */
	public boolean isWall() {
		return this.isWall;
	}
	
	/**
	 * Get connected tile to Tile t
	 * @param t start tile
	 * @return the tile connected to the specified tile
	 */
	public Tile getConnectedTile(Tile t) {
		return (a == t)? b : a;
	}

	/**
	 * Remove the wall between two connected tiles
	 * @param t one of the connected tiles
	 */
	public void removeWall(Tile t) {
		if (this.a == t || this.b == t)
			this.isWall = false;
	}
	
}
