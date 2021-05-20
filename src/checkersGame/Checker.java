package checkersGame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

public class Checker {

	private int diameter;
	private int[] coordinates;
    protected Point anchorPoint;
	protected boolean selected;
	protected BoundingBox boundingBox;
	private Color color;

    public Checker(Color color, Point center, int diameter, int x, int y) {
    	coordinates = new int[2];
    	this.setCoordinates(x, y);
    	this.anchorPoint = center;
		this.color = color;
        boundingBox = new BoundingBox(center.x - diameter/2, center.x + diameter/2, center.y - diameter/2, center.y + diameter/2);
        this.diameter = diameter;
	}

	public void setKinged() {
		if(color.equals(Color.BLACK)) {
			color = new Color(75, 75, 75);
			return;
		}
		if(color.equals(Color.WHITE)) {
			color = new Color(200, 200, 200);
			return;
		}
	}

	public void draw(Graphics g) {
        if (isSelected()){
            g.setColor(getColor().darker());
        } else {
            g.setColor(getColor());
        }
        g.fillOval((int)getAnchorPoint().getX() - diameter/2,
                (int)getAnchorPoint().getY() - diameter/2,
                diameter,
                diameter);
    }
    
    public String toString() {
        return String.format("Checker %d %d %d %s %s", 
                this.getAnchorPoint().x, 
                this.getAnchorPoint().y,
                this.diameter,
                this.isSelected());
    }
	
	public void setAnchorPoint(Point p) {
        this.anchorPoint = p;
    }
    public Color getColor() {
        return color;
    }
    public boolean isSelected() {
        return selected;
    }
    public void setSelected(boolean selected) {
        this.selected = selected;
    }
    public Point getAnchorPoint() {
        return anchorPoint;
    }
    public boolean intersects(Checker other) {
    	Checker s = other;
        return boundingBox.intersects(s.getBoundingBox());
    }

    public boolean contains(Point point) {
        return boundingBox.contains(point);
    }

	public void move(int dx, int dy) {
		this.setAnchorPoint(new Point(this.anchorPoint.x + dx, this.anchorPoint.y + dy));
		this.boundingBox.move(dx, dy);
	}
	
	 /**
     * Returns the bounding box of this shape. The bounding box
     * is the max/min X and Y coordinates.
     * @return
     */
    BoundingBox getBoundingBox() {
    	return this.boundingBox;
    }

	public int[] getCoordinates() {
		return coordinates;
	}

	public void setCoordinates(int x, int y) {
		this.coordinates[0] = x;
		this.coordinates[1] = y;
	}

	public void moveCoordinates(int x, int y) {
		coordinates[0] += x;
		coordinates[1] += y;
	}
}
