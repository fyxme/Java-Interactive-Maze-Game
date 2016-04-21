import java.util.ArrayList;
import java.util.List;

public class Tile {
	private Connection right = null;
	private Connection left = null;
	private Connection up = null;
	private Connection down = null;
	
	private boolean playable = false;
	
	public Tile() {
	}

	public boolean isWall() {
		return !this.playable;
	}

	public Connection getLeft() {
		return this.left;
	}
	
	public void setLeft(Connection left) {
		this.left = left;
	}
	
	public Connection getRight() {
		return this.right;
	}
	
	public void setRight(Connection right) {
		this.right = right;
	}
	
	public Connection getUp() {
		return this.up;
	}
	
	public void setUp(Connection up) {
		this.up = up;
	}
	
	public Connection getDown() {
		return this.down;
	}
	
	public void setDown(Connection down) {
		this.down = down;
	}

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

	public void removeWall(Tile new_curr) {
		if (right != null)
			right.removeWall(new_curr);
		if (left != null)
			left.removeWall(new_curr);
		if (up != null)
			up.removeWall(new_curr);
		if (down != null)
			down.removeWall(new_curr);
	}
}
