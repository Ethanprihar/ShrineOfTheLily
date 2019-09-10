public class Lily extends Piece
{
	public Lily(String name, boolean team, int moves, String image)
	{
		super(name, team, moves, image);
	}
	public boolean canMove(Space current, Space next, Space[] spaces)
	{
		if (current.isAdjacent(next))
		{
			if (next.piece==null)
				return true;
			else
				return this.canAttack(current, next, spaces);
		}
		else
			return false;
	}
	public boolean canAttack(Space current, Space opponentSpace, Space[] spaces)
	{
		if (opponentSpace.locX == 0 && opponentSpace.locY == 0)
			return true;
		else
			return false;
	}
}