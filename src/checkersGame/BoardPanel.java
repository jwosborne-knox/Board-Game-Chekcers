package checkersGame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.*;

@SuppressWarnings("serial")
public class BoardPanel extends JPanel{
	private int length;
	private int height;
    private Scene scene;
    
    public BoardPanel(int length, Scene scene)
    {
        this.length = length;
        this.height = length + length / 17;
        this.scene=scene;
    }

	/* (non-Javadoc)
     * @see javax.swing.JComponent#paint(java.awt.Graphics)
     */
    public void paint(Graphics g) {
    	int row;
	    int col;
	    int x;
	    int y;
	
	    for ( row = 0;  row < 8;  row++ )
	    {
		    for ( col = 0;  col < 8;  col++)
		    {
		         x = col * length/8;
		         y = row * length/8;
		         if ( (row % 2) == (col % 2) )
		              g.setColor(Color.RED);
		         else
		              g.setColor(Color.BLACK);
		
		         g.fillRect(x, y, length/8, length/8);
		    }
	    }
        scene.draw(g);
    }
    
    /* (non-Javadoc)
     * @see javax.swing.JComponent#getMinimumSize()
     */
    public Dimension getMinimumSize() {
        return new Dimension(length, height);
    }
    
    /* (non-Javadoc)
     * @see javax.swing.JComponent#getMaximumSize()
     */
    public Dimension getMaximumSize() {
        return new Dimension(length, height);
    }
    
    /* (non-Javadoc)
     * @see javax.swing.JComponent#getPreferredSize()
     */
    public Dimension getPreferredSize() {
        return new Dimension(length, height);
    }
    
    /* (non-Javadoc)
     * @see java.awt.Component#isFocusable()
     */
    public boolean isFocusable() {
        return true;
    }
}
