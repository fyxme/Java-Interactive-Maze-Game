/**
 * Class which holds the specifications of a player
 * (position and name)
 */
public class Player {
	private Tile tile;
	private String name;
	private int steps_taken = 0;
	
	/**
	 * Constructor class for the Player class
	 * @param name Name of the player
	 */
	public Player(String name) {
		this.name = name;
		this.tile = null;
	}
	
	/**
	 * @return Return the number of steps the user has taken
	 */
	public int getStepsTaken() {
		return this.steps_taken;
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
	 * Attempts to move in the specified direction
	 * @return true if the player is able to move else returns false
	 */
	public boolean move(int mv) {
		Connection c = tile.getConnection(mv);
		if (c != null && !c.isWall()) {
			if(c.getConnectedTile(tile).hasBeenVisited() == 1) {
				tile.setVisit(2);
			} else {
				tile.setVisit(1);
			}
			tile = c.getConnectedTile(tile);
			steps_taken++;
			return true;
		}
		return false;
	}
}
