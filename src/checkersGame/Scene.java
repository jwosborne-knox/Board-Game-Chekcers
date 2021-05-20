package checkersGame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Scene {
	private List<Checker> checkerList = new LinkedList<Checker>();
    
    /**
     * Draw all the shapes in the scene using the given Graphics object.
     * @param g
     */
    public void draw(Graphics g) {
        for (Checker s : checkerList) {
            if (s!=null){
                s.draw(g);
            }
        }
    }
    
    public Iterator<Checker> iterator() {
        return checkerList.iterator();
    }
    
    public boolean checkBorder(int x, int y) {
    	for(Checker s: checkerList){
    		if(s.getCoordinates()[0] == x && s.getCoordinates()[1] == y){
    			return true;
    		}
    	}
    	return false;
    }
    
    public int[] getSelectedCoordinates() {
    	for(Checker s: checkerList){
    		if(s.isSelected()){
    			return s.getCoordinates();
    		}
    	}
    	return null;
    }
    
    public void checkToKing() {
    	for(Checker s: checkerList){
    		if(s.getColor().equals(Color.BLACK) && s.getCoordinates()[1] == 7){
    			s.setKinged();
    		}
    		if(s.getColor().equals(Color.WHITE) && s.getCoordinates()[1] == 0){
    			s.setKinged();
    		}
    	}
    }
    
    public Checker getCheckerAtCoordinates(int x, int y) {
    	for(Checker s: checkerList){
    		if(s.getCoordinates()[0] == x && s.getCoordinates()[1] == y){
    			return s;
    		}
    	}
    	return null;
    }
    
    /**
     * Return a list of shapes that contain the given point.
     * @param point The point
     * @return A list of shapes that contain the given point.
     */
    public List<Checker> select(Point point)
    {
        List<Checker> selected = new LinkedList<Checker>();
        for (Checker s : checkerList){
            if (s.contains(point)){
                selected.add(s);
            }
        }
        return selected;
    }
    
    /**
     * Return a list of shapes in the scene that intersect the given shape.
     * @param s The shape
     * @return A list of shapes intersecting the given shape.
     */
    public List<Checker> select(Checker shape)
    {
        List<Checker> selected = new LinkedList<Checker>();
        for (Checker s : checkerList){
            if (s.intersects(shape)){
                selected.add(s);
            }
        }
        return selected;
    }
    
    /**
     * Add a shape to the scene.  It will be rendered next time
     * the draw() method is invoked.
     * @param s
     */
    public void addChecker(Checker s) {
        checkerList.add(s);
    }
    
    /**
     * Remove a list of shapes from the given scene.
     * @param shapesToRemove
     */
    public void removeShapes(Collection<Checker> shapesToRemove) {
        checkerList.removeAll(shapesToRemove);
    }
    
    public void removeSelected() {
    	// lambdas are SO FREAKING COOL!
    	checkerList.removeIf(s -> s.isSelected());
    }
    
    public void removeChecker(Checker c) {
    	// lambdas are SO FREAKING COOL!
    	checkerList.removeIf(s -> s.equals(c));
    }
    
    public String toString() {
        String shapeText = "";
        for (Checker s : checkerList) {
            shapeText += s.toString() + "\n";
        }
        return shapeText;
    }

	public List<Checker> getCheckerList() {
		return checkerList;
	}

	public void moveSelected(int x, int y, int length) {
		for (Checker s : checkerList) {
			if (s.isSelected()) s.moveCoordinates(x, y);
			if (s.isSelected()) s.move(x * (length/8), y * (length/8));
			if (s.isSelected()) s.setSelected(false);;
		}
	}
}
