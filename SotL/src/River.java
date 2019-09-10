public class River extends Piece
{
	public River(String name, boolean team, int moves, String image)
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
		return false;
	}
}