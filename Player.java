
public class Player {
	private Tile tile;
	private String name;
	
	public Player(String name) {
		this.name = name;
	}
	
	public void setPosition(Tile tile) {
		this.tile = tile;
	}
	
	public Tile getPosition() {
		return this.tile;
	}
	
	public String getName() {
		return this.name;
	}

	public boolean moveLeft() {
		if (tile.getLeft() != null && !tile.getLeft().isWall()) {
			tile = tile.getLeft().getConnectedTile(tile);
			return true;
		}
		return false;
	}
	
	public boolean moveRight() {
		if (tile.getRight() != null && !tile.getRight().isWall()) {
			tile = tile.getRight().getConnectedTile(tile);
			return true;
		}
		return false;
	}
	
	public boolean moveUp() {
		if (tile.getUp() != null && !tile.getUp().isWall()) {
			tile = tile.getUp().getConnectedTile(tile);
			return true;
		}
		return false;
	}
	
	public boolean moveDown() {
		if (tile.getDown() != null && !tile.getDown().isWall()) {
			tile = tile.getDown().getConnectedTile(tile);
			return true;
		}
		return false;
	}
}
