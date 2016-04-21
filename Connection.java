
public class Connection {
	private Tile a = null;
	private Tile b = null;
	private boolean isWall = true;
	
	public Connection (Tile a, Tile b) {
		this.a = a;
		this.b = b;
	}
	
	public boolean isWall() {
		return this.isWall;
	}
	
	public Tile getConnectedTile(Tile t) {
		return (a == t)? b : a;
	}

	public void removeWall(Tile new_curr) {
		if (this.a == new_curr || this.b == new_curr)
			this.isWall = false;
	}
	
}
