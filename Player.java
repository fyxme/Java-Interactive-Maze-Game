/**
 * Class which holds the specifications of a player
 * (position and name)
 */
public class Player {
	private Tile tile;
	private String name;
	
	/**
	 * Constructor class for the Player class
	 * @param name Name of the player
	 */
	public Player(String name) {
		this.name = name;
	}
	
	/**
	 * Set the position of the player (ie. Teleport player to that tile)
	 * @param tile The tile which the player is being teleported to
	 */
	public void setPosition(Tile tile) {
		this.tile = tile;
	}
	
	/**
	 * @return the tile on which the player is currently standing on
	 */
	public Tile getPosition() {
		return this.tile;
	}
	
	/**
	 * @return the name of the player
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Attempts to move to the left of the current tile
	 * @return true if the player is able to move else returns false
	 */
	public boolean moveLeft() {
		if (tile.getLeft() != null && !tile.getLeft().isWall()) {
			tile = tile.getLeft().getConnectedTile(tile);
			tile.visit();
			return true;
		}
		return false;
	}
	
	/**
	 * Attempts to move to the right of the current tile
	 * @return true if the player is able to move else returns false
	 */
	public boolean moveRight() {
		if (tile.getRight() != null && !tile.getRight().isWall()) {
			tile = tile.getRight().getConnectedTile(tile);
			tile.visit();
			return true;
		}
		return false;
	}
	
	/**
	 * Attempts to move to the north of the current tile
	 * @return true if the player is able to move else returns false
	 */
	public boolean moveUp() {
		if (tile.getUp() != null && !tile.getUp().isWall()) {
			tile = tile.getUp().getConnectedTile(tile);
			tile.visit();
			return true;
		}
		return false;
	}
	
	/**
	 * Attempts to move to the south of the current tile
	 * @return true if the player is able to move else returns false
	 */
	public boolean moveDown() {
		if (tile.getDown() != null && !tile.getDown().isWall()) {
			tile = tile.getDown().getConnectedTile(tile);
			tile.visit();
			return true;
		}
		return false;
	}
}
