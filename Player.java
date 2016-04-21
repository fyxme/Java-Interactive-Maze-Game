
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
}
