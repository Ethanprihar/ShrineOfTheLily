public abstract class Piece
{
	String name; //name of the piece
	boolean team; //what team the piece is on
	int moves; //total number of moves a piece has
	String image;
	
	public Piece(String name, boolean team, int moves, String image)
	{
		this.name = name;
		this.team = team;
		this.moves = moves;
		this.image = image;
	}
	public abstract boolean canMove(Space current, Space next, Space[] spaces); //method to see if the piece can move
	
	public abstract boolean canAttack(Space current, Space opponentSpace, Space[] spaces); // method to see if the piece can attack another piece
}
