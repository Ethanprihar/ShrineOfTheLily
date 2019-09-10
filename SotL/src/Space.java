public class Space
{
	int locX;
	int locY;
	Piece piece;
	boolean wet;

	public Space(int locX, int locY, Piece piece, boolean wet)
	{
		this.locX = locX;
		this.locY = locY;
		this.piece = piece;
		this.wet = wet;
	}
	public boolean isAdjacent(Space otherSpace)
	{
		if (Math.abs(otherSpace.locX - locX) == 2 && otherSpace.locY == locY)
			return true;
		else if (Math.abs(otherSpace.locX - locX) == 1 && Math.abs(otherSpace.locY - locY) == 1)
			return true;
		else
			return false;
	}
	public boolean equals(Space space)
	{
		if (locX == space.locX && locY == space.locY)
			return true;
		else
			return false;
	}
}