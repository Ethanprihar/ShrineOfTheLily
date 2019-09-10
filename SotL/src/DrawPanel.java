// when it's time to turn this into a jar file, place (the thing that was here) before every file
// then you will have to open the jar file and place the Art folder inside of it

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class DrawPanel extends JPanel implements MouseListener
{
	Board board;
	
	public DrawPanel()                       // set up graphics window
    {
        super(); 
        board = new Board();
        this.addMouseListener(this);
    }
    public static void main(String[] args)
    {
        DrawPanel screen = new DrawPanel();                    // window for drawing
        JFrame game = new JFrame();                            // the program itself
        game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);   // set frame to exit when it is closed
        game.add(screen);                                      // more setup stuff
        game.setSize(733, 762);         // board is 715 x 715 pixels but that size is cause the window has bars
        game.setVisible(true);
        //SET UP A WAY TO INTERACT WITH THE PIECES
    }
    public void paintComponent(Graphics g)  // draw graphics in the panel
    {
        super.paintComponent(g);            // Setup stuff
        ImageIcon image = null;
        image = new ImageIcon(getClass().getResource("Art/Board.png")); //display the board
        image.paintIcon(this, g, 0, 0);
        for (int i = 0; i <= 126; i++) // display the pieces
        {
        	int x = makeX(board.spaces[i].locX);
        	int y = makeY(board.spaces[i].locY);               
            if (board.spaces[i].wet)
            {
            	image = new ImageIcon(getClass().getResource("Art/Wet_Space.png"));  // this should draw a wet space when a space is wet
            	image.paintIcon(this, g, x, y - 4);
            }
        	if (board.spaces[i].piece != null)
           	{
        		image = new ImageIcon(getClass().getResource("Art/Not_Selectable.png")); // coloring in the not selectable pieces
           		if (board.lastPieceOrange == i)
           			image.paintIcon(this, g, x, y - 4);
           		if (board.lastOrangeWolves != null)
           		{
           			for (int w : board.lastOrangeWolves)
           			{
           				if (w == i)
           					image.paintIcon(this, g, x, y - 4);
           			}
           		}
		   		if (board.lastPiecePurple == i)
       				image.paintIcon(this, g, x, y - 4);
	        	if (board.lastPurpleWolves != null)
	        	{
		        	for (int w : board.lastPurpleWolves)
		        	{
		        		if (w == i)
		        			image.paintIcon(this, g, x, y - 4);
		        	}
	        	}
		    	if (board.spaceWithPiece == i) // coloring in the selected piece
		       	{
		    		if (board.spaces[i].piece.name.equals("Wolf") && board.wolves.size() != 0) // makes the alpha wolf look different
		    			image = new ImageIcon(getClass().getResource("Art/Alpha_Wolf.png"));
		    		else
		    			image = new ImageIcon(getClass().getResource("Art/Selected_Border.png")); // this outlines the selected piece
		    		image.paintIcon(this, g, x, y - 4);
		       	}
		    	if (board.wolves != null)
		    	{
			    	for (int w: board.wolves) // this outlines selected wolves
		    		{
		    			if (w == i)
		    			{
		    				image = new ImageIcon(getClass().getResource("Art/Selected_Border.png"));
	           				image.paintIcon(this, g, x, y - 4);
		    			}
		    		}
		    	}
        		image = new ImageIcon(getClass().getResource(board.spaces[i].piece.image)); // this draws the piece in a space if it exists
        		image.paintIcon(this, g, x, y);
            }
        }
        if (board.winner())
		{
			if (board.spaces[63].piece.team)
			{
				image = new ImageIcon(getClass().getResource("Art/Orange_Wins.png")); //orange wins
				image.paintIcon(this, g, 0, 0);
			}
			else
			{
				image = new ImageIcon(getClass().getResource("Art/Purple_Wins.png")); //purple wins
				image.paintIcon(this, g, 0, 0);
			}
		}
    }
	public void mousePressed(MouseEvent m)
	{
		for (int i = 0; i <= 126; i++) // this checks if a mouse click is on a piece and selects the piece if it is
		{
			int x = makeX(board.spaces[i].locX);
        	int y = makeY(board.spaces[i].locY);
			if (m.getX() >= (x + 10) && m.getX() <= (x + 45) && m.getY() >= (y + 10) && m.getY() <= (y + 45))
			{
				board.selectSpace(i);
			}
		}
		repaint();
	}
////////////////////////NOT USED MOUSE LISTENERS
	public void mouseEntered(MouseEvent m){}
	public void mouseExited(MouseEvent m){}
	public void mouseClicked(MouseEvent m){}
	public void mouseReleased(MouseEvent arg0){}
////////////////////////NOT USED MOUSE LISTENERS
	public int makeX(int x)// this assigns the x coordinate of the pixel to display the piece at
    {
    	if (x % 2 == 0)                   
    		return (((x / 2) + 6) * 55);
    	else
    		return (27 + ((((x - 1) / 2) + 6)*55));
    }
    public int makeY(int y)// this assigns the y coordinate of the pixel to display the piece at
    {
    	return (int) ( 44 + (47.63 * (6 - y)));
    }
}
