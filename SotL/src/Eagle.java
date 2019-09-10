public class Eagle extends Piece
{
	boolean lastMove;
	int moveCounter;
	boolean noSun ;
	boolean hasAttacked;
	
	public Eagle(String name, boolean team, int moves, String image)
	{
		super(name, team, moves, image);
		moveCounter = 0;
		noSun = true;
		lastMove = false;
		hasAttacked = false;
	}
	public boolean canMove(Space current, Space next, Space[] spaces)
	{
		if (moveCounter == 1)
			lastMove = true;
		else 
			lastMove = false;
		if (moveCounter == moves)
		{
			moveCounter = 0;
			noSun = true;
			hasAttacked = false;
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
			if (next.piece != null && !next.wet)
				return this.canAttack(current, next, spaces);
			else if (!(next.wet) || !lastMove)
			{
				moveCounter ++;
				return true;
			}
			else
				return false;
		}
		else
			return false;
	}
	public boolean canAttack(Space current, Space opponentSpace, Space[] spaces)
	{
		if (opponentSpace.piece.team == team && lastMove) //if the eagle already attacked or its the eagles last move and it is trying to land on its allys space, then no
			return false;
		else if (opponentSpace.piece.team == team) // if the eagle is not on its last move and hasn't attacked then if it wants to move onto its allys it can and store the allys space
		{
			moveCounter ++;
			return true;
		}
		else if (hasAttacked)
			return false;
		for(Space s: spaces)
		{
			if (s.isAdjacent(opponentSpace) && s.piece != null && s.piece.name.equals("Oasis"))
				return false;
		}
		if (noSun) // if the piece moves increment moveCounter
			moveCounter ++;
		hasAttacked = true;
		return noSun;
	}
}