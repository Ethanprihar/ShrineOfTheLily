import java.util.ArrayList;

//In this class the following things must be accounted for:
//Rivers and Suns altering the boolean wet of the space they leave CHECK
//Keeping track of how many times a piece has moved CHECK
//Keeping track of the entire board and the states of all the spaces and pieces in it CHECK
// this class should always have selectSpace called by the GUI because selectSpace knows when to move or not to move
// in the graphics class, run selectSpace before you run winner so that it correctly knows when it is the end of a turn

public class Board
{
	Space[] spaces = new Space[127];
	int spaceWithPiece = -1; //location in list of space with selected piece
	int movesLeft;
	boolean turn = true;
	int lastPieceOrange = -1;
	int lastPiecePurple = -1;
	Space jumpedByEagle;
	ArrayList<Integer> wolves = new ArrayList<Integer>();
	ArrayList<Integer> lastOrangeWolves = new ArrayList<Integer>();
	ArrayList<Integer> lastPurpleWolves = new ArrayList<Integer>();
	boolean canWolvesMove = true;
	int xDif;
	int yDif;
	Space wolfStorage;
	ArrayList<Space> wolfSpaces;
	ArrayList<Integer> nextWolfSpaces;
	int winCount = 0;
	int winningTeam = -1;
	boolean endOfTurn = false;
	public Board()
	{
		int counter = 0;
		for (int i = -6; i <= 6; i++)
		{
			for (int j = -(12-Math.abs(i)); j <= (12-Math.abs(i)); j += 2)
			{
				spaces[counter] = new Space(j,i,null,false);
				counter++;
			}
		}
		spaces[0].piece = new Viper("Viper", true, 4, "Art/Viper_Orange.png");
		spaces[1].piece = new Oasis("Oasis", true, 2, "Art/Oasis_Orange.png");
		spaces[2].piece = new Sun("Sun", true, 3, "Art/Sun_Orange.png");
		spaces[3].piece = new Lily("Lily", true, 1, "Art/Lily_Orange.png");
		spaces[4].piece = new Sun("Sun", true, 3, "Art/Sun_Orange.png");
		spaces[5].piece = new Oasis("Oasis", true, 2, "Art/Oasis_Orange.png");
		spaces[6].piece = new Viper("Viper", true, 4, "Art/Viper_Orange.png");
		spaces[7].piece = new Wolf("Wolf", true, 2, "Art/Wolf_Orange.png");
		spaces[8].piece = new River("River", true, 3, "Art/River_Orange.png");
		spaces[9].piece = new Eagle("Eagle", true, 2, "Art/Eagle_Orange.png");
		spaces[10].piece = new Wolf("Wolf", true, 2, "Art/Wolf_Orange.png");
		spaces[11].piece = new Wolf("Wolf", true, 2, "Art/Wolf_Orange.png");
		spaces[12].piece = new Eagle("Eagle", true, 2, "Art/Eagle_Orange.png");
		spaces[13].piece = new River("River", true, 3, "Art/River_Orange.png");
		spaces[14].piece = new Wolf("Wolf", true, 2, "Art/Wolf_Orange.png");
		spaces[120].piece = new Viper("Viper", false, 4, "Art/Viper_Purple.png");
		spaces[121].piece = new Oasis("Oasis", false, 2, "Art/Oasis_Purple.png");
		spaces[122].piece = new Sun("Sun", false, 3, "Art/Sun_Purple.png");
		spaces[123].piece = new Lily("Lily", false, 1, "Art/Lily_Purple.png");
		spaces[124].piece = new Sun("Sun", false, 3, "Art/Sun_Purple.png");
		spaces[125].piece = new Oasis("Oasis", false, 2, "Art/Oasis_Purple.png");
		spaces[126].piece = new Viper("Viper", false, 4, "Art/Viper_Purple.png");
		spaces[112].piece = new Wolf("Wolf", false, 2, "Art/Wolf_Purple.png");
		spaces[113].piece = new River("River", false, 3, "Art/River_Purple.png");
		spaces[114].piece = new Eagle("Eagle", false, 2, "Art/Eagle_Purple.png");
		spaces[115].piece = new Wolf("Wolf", false, 2, "Art/Wolf_Purple.png");
		spaces[116].piece = new Wolf("Wolf", false, 2, "Art/Wolf_Purple.png");
		spaces[117].piece = new Eagle("Eagle", false, 2, "Art/Eagle_Purple.png");
		spaces[118].piece = new River("River", false, 3, "Art/River_Purple.png");
		spaces[119].piece = new Wolf("Wolf", false, 2, "Art/Wolf_Purple.png");
	}
	public void movePiece(int nextSpace)
	{
//checking if the wolves can move is done below
		if (spaceWithPiece != -1 && spaces[spaceWithPiece].piece.name.equals("Wolf")) // this if loop checks if all the wolves can move
		{
			canWolvesMove = spaces[spaceWithPiece].piece.canMove(spaces[spaceWithPiece], spaces[nextSpace], spaces); // wheather or not the wolves can move first depends on the alpha wolf
			xDif = spaces[nextSpace].locX - spaces[spaceWithPiece].locX;
			yDif = spaces[nextSpace].locY - spaces[spaceWithPiece].locY;
			wolfSpaces = new ArrayList<Space>();
			nextWolfSpaces = new ArrayList<Integer>();
			for (int w: wolves) // checking one wolf at a time
			{
				Space wolfSpace = new Space(spaces[w].locX, spaces[w].locY, spaces[w].piece, spaces[w].wet);
				wolfSpaces.add(wolfSpace); // creates a list of spaces not on the board that have wolves
				spaces[w].piece = null; // gets rid of the wolf that was at that space
				Space nextWolfSpace = new Space(spaces[w].locX + xDif, spaces[w].locY + yDif, null, false);
				for (int i = 0; i <= 126; i++)
				{
					if (spaces[i].equals(nextWolfSpace))
						nextWolfSpaces.add(i); // adds the location of the space that wolf would move to a list
				}
			}
			for (int i = 0; i < wolfSpaces.size(); i++)
			{
				if (canWolvesMove)
				{
					if (!wolfSpaces.get(i).piece.canMove(wolfSpaces.get(i), spaces[nextWolfSpaces.get(i)], spaces))
						canWolvesMove = false;
					if (nextWolfSpaces.get(i) == spaceWithPiece)
						canWolvesMove = true;
				}
			}
		}
		if (!canWolvesMove) // in the wolves can't move then reset the spaces to what they were
		{
			int counter = 0;
			for (int w: wolves) // this puts the wolves back
			{
				spaces[w] = wolfSpaces.get(counter);
				counter ++;
			}
		}
		else if (spaceWithPiece != -1 && spaces[spaceWithPiece].piece.canMove(spaces[spaceWithPiece], spaces[nextSpace], spaces))
		{	
			movesLeft --;
			if (spaces[spaceWithPiece].piece.name.equals("Sun")) //this does the wetness change for suns and rivers
				spaces[spaceWithPiece].wet = false;
			else if (spaces[spaceWithPiece].piece.name.equals("River"))
				spaces[spaceWithPiece].wet = true;
			else if (spaces[spaceWithPiece].piece.name.equals("Eagle")) // this is where we make special rules for the eagle
			{
				if (spaces[nextSpace].piece != null && spaces[spaceWithPiece].piece.team == spaces[nextSpace].piece.team)
					{
					jumpedByEagle = new Space(spaces[nextSpace].locX, spaces[nextSpace].locY, spaces[nextSpace].piece, spaces[nextSpace].wet);
					}
				if (movesLeft == 1)
				{
					spaces[nextSpace].piece = spaces[spaceWithPiece].piece;
					spaces[spaceWithPiece].piece = null;
					spaceWithPiece = nextSpace;
					checkForLily(nextSpace);
				}
				if (movesLeft == 0)
				{
					spaces[nextSpace].piece = spaces[spaceWithPiece].piece;
					if (jumpedByEagle != null)
						spaces[spaceWithPiece].piece = jumpedByEagle.piece;
					else
						spaces[spaceWithPiece].piece = null;
					jumpedByEagle = null;
					spaceWithPiece = nextSpace;
					checkForLily(nextSpace);
				}
			}		
			else if (spaces[spaceWithPiece].piece.name.equals("Wolf")) // move each piece, make sure the turn ends if any wolf attacks,
			{
				if (spaces[nextSpace].piece != null) // if first wolf attacks then turn is over
				{
					checkForLily(nextSpace);
					movesLeft = 0;
				}
				for (int i = 0; i < nextWolfSpaces.size(); i++) // if any other wolves attacked their turn is over
				{
					if (spaces[nextWolfSpaces.get(i)].piece != null)
					{
						checkForLily(nextWolfSpaces.get(i));
						movesLeft = 0;
					}
				}
				spaces[nextSpace].piece = spaces[spaceWithPiece].piece; //changing the location of the first sleected piece
				spaces[spaceWithPiece].piece = null; 
				for (int i = 0; i < wolfSpaces.size(); i++) // changing the location of all the wolves in the list that were moves
				{
					spaces[nextWolfSpaces.get(i)].piece = wolfSpaces.get(i).piece;
				}
				if (movesLeft != 0) // if the wolves turn is over then change the location of all the wolves
				{
					wolves.clear();
					for (int i = 0; i < nextWolfSpaces.size(); i++)
					{
						wolves.add(nextWolfSpaces.get(i));
					}
				}
			}
			if (spaces[spaceWithPiece].piece != null && !spaces[spaceWithPiece].piece.name.equals("Eagle") && !spaces[spaceWithPiece].piece.name.equals("Wolf")) // moving every other type of piece that isnt eagle or wolf
			{
				if (spaces[nextSpace].piece != null && !spaces[spaceWithPiece].piece.name.equals("Viper")) // if the piece attacked it's turn is over
				{
					movesLeft = 0;
				}
				else if (spaces[nextSpace].piece != null && spaces[spaceWithPiece].piece.name.equals("Viper"))
					movesLeft = 1;
				checkForLily(nextSpace);
				spaces[nextSpace].piece = spaces[spaceWithPiece].piece; //this might not work but if it does it changes the piece of the next space to be the piece we're moving
				spaces[spaceWithPiece].piece = null; //this might not work either
			}
			if (movesLeft == 0) // reseting the selected piece at the end of a turn
			{
				wolves.clear();
				if (turn)
				{
					lastPieceOrange = nextSpace;
					lastOrangeWolves.clear();
				}
				else
				{
					lastPiecePurple = nextSpace;
					lastPurpleWolves.clear();
				}
				if (nextWolfSpaces != null)
				{
					for (int w: nextWolfSpaces)
					{
						if (turn)
							lastOrangeWolves.add(w);
						else
							lastPurpleWolves.add(w);
					}
				}
				wolves.clear();
				if (nextWolfSpaces != null)
					nextWolfSpaces.clear();
				spaceWithPiece = -1;
				turn = !turn;
				endOfTurn = true;
			}
			else
				spaceWithPiece = nextSpace; // makes the space that we use be the space with the piece after its moved
		}
	}
	public void selectSpace(int space)
	{
		endOfTurn = false;
		if (spaceWithPiece == -1 && space != lastPieceOrange && space != lastPiecePurple && spaces[space].piece != null && spaces[space].piece.team == turn)
		{
			boolean movedLastTurn = false;
			if (turn)
			{
				for (int w: lastOrangeWolves)
				{
					if (w == space)
						movedLastTurn = true;
				}
			}
			else
			{
				for (int w: lastPurpleWolves)
				{
					if (w == space)
						movedLastTurn = true;
				}
			}
			if (!movedLastTurn)
			{
				spaceWithPiece = space;
				movesLeft = spaces[space].piece.moves;
			}
		}
		else if (spaceWithPiece != -1 && spaces[spaceWithPiece].piece.name.equals("Wolf") && spaces[space].piece != null && spaces[space].piece.team == turn && spaces[space].piece.name.equals("Wolf") && movesLeft == 2) // if you already selected a wolf and you just selected another ally wolf and the wolf hasnt moved yet
		{
			boolean movedLastTurn = false;
			if (turn)
			{
				for (int w: lastOrangeWolves)
				{
					if (w == space)
						movedLastTurn = true;
				}
			}
			else
			{
				for (int w: lastPurpleWolves)
				{
					if (w == space)
						movedLastTurn = true;
				}
			}
			if (!movedLastTurn)
			{	
				boolean notAdded = true;
				boolean addedFromStart = false;
				if (spaceWithPiece == space) // if you just clicked on the same wolf then dont add it
				{
					notAdded = false;
					addedFromStart = true;
				}
				for (int i: wolves) // for every selected wolf if you already added it than don't add it again
				{
					if (i == space)
					{
						notAdded = false;
						addedFromStart = true;
					}
				}
				if (wolves.size() == 0)
				{
					if (notAdded && spaces[space].isAdjacent(spaces[spaceWithPiece]))
					{
						wolves.add(space);
						notAdded = false;
					}
				}
				for (int i: wolves) // if its not added and its adjacent to any added wolves than add it ()()()()()()()()THISISTHEPROBLEM!!!!!!!! its not adding the first wolf because the wolf list is empty
				{
					if (notAdded && spaces[space].isAdjacent(spaces[i]))
					{
						wolves.add(space);
						notAdded = false;
					}
				}
				if (addedFromStart) // if you have already added the piece before, then try to move to that space
				{
					this.movePiece(space);
				}
			}
		}
		else
		{
			this.movePiece(space);
		}

	}
	public boolean winner()
	{
		if (spaces[63].piece != null && spaces[63].piece.name == "Lily" && movesLeft == 0 && endOfTurn)
		{
			if (spaces[63].piece.team)
				winningTeam = 1;
			else
				winningTeam = 0;
			if (spaces[63].piece.team && winningTeam == 1)
				winCount ++;
			else if (!spaces[63].piece.team && winningTeam == 0)
				winCount ++;
			else
				winCount = 0;
		}
		if (winCount == 4)
			return true;
		else
			return false;
	}
	public void checkForLily(int space)
	{
		if (spaces[space].piece != null && spaces[space].piece.name.equals("Lily") && ((spaces[space].piece.team && !turn) || (!spaces[space].piece.team && turn)))
		{
			if (turn)
				spaces[123].piece = spaces[space].piece;
			else
				spaces[3].piece = spaces[space].piece;
		}
			
	}
}
