package checkersGame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

@SuppressWarnings("serial")
public class Board extends JFrame{
	private int length;
	private BoardPanel boardPanel;
	private Scene scene;

    public Board(int length)
    {
    	setTitle("Checkers?");
        scene=new Scene();
        this.length = length; 
        
        // create our canvas, add to this frame's content pane
        boardPanel = new BoardPanel(length,scene);
        this.getContentPane().add(boardPanel, BorderLayout.CENTER);
        this.setResizable(false);
        this.pack();
        this.setLocation(100,100);
        
        // Add key and mouse listeners to our canvas
        initializeMouseListener();
        initializeKeyListener();
        
        // initialize the menu options
        initializeMenu();
        
        //initialize the pieces
        initializeBoard();

        // Handle closing the window.
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }
    
    private void initializeMouseListener()
    {
        MouseAdapter a = new MouseAdapter() {
            
            public void mouseClicked(MouseEvent e)
            {
                System.out.printf("Mouse cliked at (%d, %d)\n", e.getX(), e.getY());
                
                if (e.getButton()==MouseEvent.BUTTON1) { 
                	Point p = e.getPoint();
                    System.out.printf("Left click is (%d, %d)\n", p.x, p.y);
                    List<Checker> selected = scene.select(p);
                    for (Checker s : scene.getCheckerList()){
                        s.setSelected(false);
                    }
                    if (selected.size() > 0){
                        for (Checker s : selected){
                            s.setSelected(true);
                        }
                    }
                    int[] temp = scene.getSelectedCoordinates();
                    System.out.printf("Select %d shapes\n", selected.size());
                    System.out.printf("Selected Coordinates (%d,%d) \n", temp[0], temp[1]);
                    repaint();
                } else if (e.getButton()==MouseEvent.BUTTON2) {
                    // apparently this is middle click
                } else if (e.getButton()==MouseEvent.BUTTON3){
                    // right right-click
                }
            };
            
            /* (non-Javadoc)
             * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
             */
            public void mousePressed(MouseEvent e)
            {
                System.out.printf("mouse pressed at (%d, %d)\n", e.getX(), e.getY());
                
            }

            /* (non-Javadoc)
             * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
             */
            public void mouseReleased(MouseEvent e)
            {
                System.out.printf("mouse released at (%d, %d)\n", e.getX(), e.getY());
                repaint();
            }
            
            @Override
            public void mouseDragged(MouseEvent e) {
                System.out.printf("mouse drag! (%d, %d)\n", e.getX(), e.getY());
                repaint();
            }

            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
            	System.out.printf("mouse spin! (%g)\n", e.getPreciseWheelRotation());
            	if(e.getWheelRotation() > 0) {
            		
            	}else {
            		
            	}
            	repaint();
            }
        };
        
        boardPanel.addMouseMotionListener(a);
        boardPanel.addMouseListener(a);
        boardPanel.addMouseWheelListener(a);
    }
    
    /**
     * Initialize the menu options
     */
    private void initializeMenu()
    {
        // menu bar
        JMenuBar menuBar = new JMenuBar();
        
        // file menu
        JMenu fileMenu=new JMenu("File");
        menuBar.add(fileMenu);
        // exit
        JMenuItem itemExit = new JMenuItem ("Exit");
        fileMenu.add(itemExit);
        itemExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String text=e.getActionCommand();
                System.out.println(text);
                System.exit(0);
            }
        });
        
        // move mode menu
        // ###TODO make move menu out of operation menu
        JMenu operationModeMenu=new JMenu("Move");
        menuBar.add(operationModeMenu);
        
        // move options
        addToMenu(operationModeMenu, "Up Left", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	int[] temp = scene.getSelectedCoordinates();
        		if(temp[0]==0||temp[1]==0)
        			return;
        		if(scene.checkBorder(temp[0]-1, temp[1]-1)) {
        			if(scene.checkBorder(temp[0]-2, temp[1]-2))
        				return;
        			if(temp[0]==1||temp[1]==1)
            			return;
        			scene.removeChecker(scene.getCheckerAtCoordinates(temp[0]-1, temp[1]-1));
        			scene.moveSelected(-2, -2, length);
        		}else
        			scene.moveSelected(-1, -1, length);
        		repaint();
            }
        });
        
        addToMenu(operationModeMenu, "Up Right", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	int[] temp = scene.getSelectedCoordinates();
        		if(temp[0]==7||temp[1]==0)
        			return;
        		if(scene.checkBorder(temp[0]+1, temp[1]-1)) {
        			if(scene.checkBorder(temp[0]+2, temp[1]-2))
        				return;
        			if(temp[0]==6||temp[1]==1)
            			return;
        			scene.removeChecker(scene.getCheckerAtCoordinates(temp[0]+1, temp[1]-1));
        			scene.moveSelected(2, -2, length);
        		}else
        			scene.moveSelected(1, -1, length);
        		repaint();
            }
        });
        
        addToMenu(operationModeMenu, "Down Left", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	int[] temp = scene.getSelectedCoordinates();
        		if(temp[0]==0||temp[1]==7)
        			return;
        		if(scene.checkBorder(temp[0]-1, temp[1]+1)) {
        			if(scene.checkBorder(temp[0]-2, temp[1]+2))
        				return;
        			if(temp[0]==1||temp[1]==6)
            			return;
        			scene.removeChecker(scene.getCheckerAtCoordinates(temp[0]-1, temp[1]+1));
        			scene.moveSelected(-2, 2, length);
        		}else
        			scene.moveSelected(-1, 1, length);
        		repaint();
            }
        });
        
        addToMenu(operationModeMenu, "Down Right", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	int[] temp = scene.getSelectedCoordinates();
        		if(temp[0]==7||temp[1]==7)
        			return;
        		if(scene.checkBorder(temp[0]+1, temp[1]+1)) {
        			if(scene.checkBorder(temp[0]+2, temp[1]+2))
        				return;
        			if(temp[0]==6||temp[1]==6)
            			return;
        			scene.removeChecker(scene.getCheckerAtCoordinates(temp[0]+1, temp[1]+1));
        			scene.moveSelected(2, 2, length);
        		}else
        			scene.moveSelected(1, 1, length);
        		repaint();
            }
        });

        // set the menu bar for this frame
        this.setJMenuBar(menuBar);
    }
    
    // Awesome helper method!
    private void addToMenu(JMenu menu, String title, ActionListener listener) {
    	JMenuItem menuItem = new JMenuItem(title);
    	menu.add(menuItem);
    	menuItem.addActionListener(listener);
    }
    
    /**
     * Initialize the keyboard listener.
     */
    private void initializeKeyListener()
    {
        boardPanel.addKeyListener(new KeyListener() {
            public void keyPressed(KeyEvent e) {
            	// Called when you push a key down
            	System.out.println("key pressed: " + e.getKeyChar());
            	if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            		int[] temp = scene.getSelectedCoordinates();
            		if(temp[0]==0||temp[1]==0)
            			return;
            		if(scene.checkBorder(temp[0]-1, temp[1]-1)) {
            			if(scene.checkBorder(temp[0]-2, temp[1]-2))
            				return;
            			if(temp[0]==1||temp[1]==1)
                			return;
            			scene.removeChecker(scene.getCheckerAtCoordinates(temp[0]-1, temp[1]-1));
            			scene.moveSelected(-2, -2, length);
            		}else
            			scene.moveSelected(-1, -1, length);
            		
            	} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            		int[] temp = scene.getSelectedCoordinates();
            		if(temp[0]==0||temp[1]==7)
            			return;
            		if(scene.checkBorder(temp[0]-1, temp[1]+1)) {
            			if(scene.checkBorder(temp[0]-2, temp[1]+2))
            				return;
            			if(temp[0]==1||temp[1]==6)
                			return;
            			scene.removeChecker(scene.getCheckerAtCoordinates(temp[0]-1, temp[1]+1));
            			scene.moveSelected(-2, 2, length);
            		}else
            			scene.moveSelected(-1, 1, length);
            		
            	} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            		int[] temp = scene.getSelectedCoordinates();
            		if(temp[0]==7||temp[1]==7)
            			return;
            		if(scene.checkBorder(temp[0]+1, temp[1]+1)) {
            			if(scene.checkBorder(temp[0]+2, temp[1]+2))
            				return;
            			if(temp[0]==6||temp[1]==6)
                			return;
            			scene.removeChecker(scene.getCheckerAtCoordinates(temp[0]+1, temp[1]+1));
            			scene.moveSelected(2, 2, length);
            		}else
            			scene.moveSelected(1, 1, length);
            	} else if (e.getKeyCode() == KeyEvent.VK_UP) {
            		int[] temp = scene.getSelectedCoordinates();
            		if(temp[0]==7||temp[1]==0)
            			return;
            		if(scene.checkBorder(temp[0]+1, temp[1]-1)) {
            			if(scene.checkBorder(temp[0]+2, temp[1]-2))
            				return;
            			if(temp[0]==6||temp[1]==1)
                			return;
            			scene.removeChecker(scene.getCheckerAtCoordinates(temp[0]+1, temp[1]-1));
            			scene.moveSelected(2, -2, length);
            		}else
            			scene.moveSelected(1, -1, length);
            	} 
            	scene.checkToKing();
            	repaint();
            }
            public void keyReleased(KeyEvent e){
            	// Called when you release a key and it goes up
            	System.out.println("key released: " + e.getKeyChar());
            }
            public void keyTyped(KeyEvent e) {
            	// Gets called when you push a key down and then release it,
            	// without pushing any other keys in between
            	System.out.println("key typed: " + e.getKeyChar());
            	if (e.getKeyChar() == KeyEvent.VK_DELETE) {
            		scene.removeSelected();
            	}
            	repaint();
            }
        });
    }
    
    private void initializeBoard() {
    	Checker black1 = new Checker(Color.BLACK, new Point(length/16, length/16), (length/8)-5, 0, 0);
    	Checker black2 = new Checker(Color.BLACK, new Point(length/16+(length/4), length/16), (length/8)-5, 2, 0);
    	Checker black3 = new Checker(Color.BLACK, new Point(length/16+2*(length/4), length/16), (length/8)-5, 4, 0);
    	Checker black4 = new Checker(Color.BLACK, new Point(length/16+3*(length/4), length/16), (length/8)-5, 6, 0);
    	
    	Checker black5 = new Checker(Color.BLACK, new Point(length/16+(length/8), length/16+(length/8)), (length/8)-5, 1, 1);
    	Checker black6 = new Checker(Color.BLACK, new Point(length/16+3*(length/8), length/16+(length/8)), (length/8)-5, 3, 1);
    	Checker black7 = new Checker(Color.BLACK, new Point(length/16+5*(length/8), length/16+(length/8)), (length/8)-5, 5, 1);
    	Checker black8 = new Checker(Color.BLACK, new Point(length/16+7*(length/8), length/16+(length/8)), (length/8)-5, 7, 1);
    	
    	Checker white1 = new Checker(Color.WHITE, new Point(length/16, length-(length/16+(length/8))), (length/8)-5, 0, 6);
    	Checker white2 = new Checker(Color.WHITE, new Point(length/16+(length/4), length-(length/16+(length/8))), (length/8)-5, 2, 6);
    	Checker white3 = new Checker(Color.WHITE, new Point(length/16+2*(length/4), length-(length/16+(length/8))), (length/8)-5, 4, 6);
    	Checker white4 = new Checker(Color.WHITE, new Point(length/16+3*(length/4), length-(length/16+(length/8))), (length/8)-5, 6, 6);
    	
    	Checker white5 = new Checker(Color.WHITE, new Point(length/16+(length/8), length-(length/16)), (length/8)-5, 1, 7);
    	Checker white6 = new Checker(Color.WHITE, new Point(length/16+3*(length/8), length-(length/16)), (length/8)-5, 3, 7);
    	Checker white7 = new Checker(Color.WHITE, new Point(length/16+5*(length/8), length-(length/16)), (length/8)-5, 5, 7);
    	Checker white8 = new Checker(Color.WHITE, new Point(length/16+7*(length/8), length-(length/16)), (length/8)-5, 7, 7);
    	
    	scene.addChecker(black1);
    	scene.addChecker(black2);
    	scene.addChecker(black3);
    	scene.addChecker(black4);
    	scene.addChecker(black5);
    	scene.addChecker(black6);
    	scene.addChecker(black7);
    	scene.addChecker(black8);
    	
    	scene.addChecker(white1);
    	scene.addChecker(white2);
    	scene.addChecker(white3);
    	scene.addChecker(white4);
    	scene.addChecker(white5);
    	scene.addChecker(white6);
    	scene.addChecker(white7);
    	scene.addChecker(white8);
    }
    
	/**
     * @param args
     */
    public static void main(String[] args)
    {
        Board board = new Board(400);
        board.setVisible(true);
    }
}
