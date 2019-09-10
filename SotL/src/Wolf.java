public class Wolf extends Piece
{
	int moveCounter;
	boolean noSun;
	
	public Wolf(String name, boolean team, int moves, String image)
	{
		super(name, team, moves, image);
		moveCounter = 0;
		noSun = true;
	}
	public boolean canMove(Space current, Space next, Space[] spaces)
	{
		if (moveCounter == moves)
		{
			moveCounter = 0;
			noSun = true;
		}
		if (moveCounter == 0) // if the piece hasn't moved yet check if its adjacent to a sun and store that information
		{
			for(Space s: spaces)
			{
				if (s.isAdjacent(current) && s.piece != null && s.piece.name.equals("Sun"))
					noSun = false;
			}
		}
		if (current.isAdjacent(next))
		{
			if (next.piece==null && !(next.wet))
			{
				moveCounter ++; // when the piece moves, increment movecounter
				return true;
			}
			else if (!(next.wet))
				return this.canAttack(current, next, spaces);
			else
				return false;
		}
		else
			return false;
	}
	public boolean canAttack(Space current, Space opponentSpace, Space[] spaces)
	{
		if (opponentSpace.piece.team == team)
			return false;
			for(Space s: spaces)
			{
				if (s.isAdjacent(opponentSpace) && s.piece != null && s.piece.name.equals("Oasis"))
					return false;
			}
		if (noSun) // if the piece moves increment moveCounter
			moveCounter = moves;
		return noSun;
	}
}