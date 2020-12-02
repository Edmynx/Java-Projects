//import java.awt.*;
//import java.awt.event.*;
//import javax.swing.*;
//import javax.swing.border.*;
//
///**
// * Lays out the frame and buttons for the maze solver.
// * Borrows from LifeFrame.java and ChoiceFrame.java.
// *
// * @author Scot Drysdale
// * @see MazePanel
// */
//
//public class MazeFrame extends JFrame {
//
//	private static final long serialVersionUID = 1L;
//	private MazePanel mazeCanvas;			  // Holds the drawing canvas
//	private JButton solveButton;			  // Holds the button for solving maze
//	private Timer timer;								// Holds the timer object solving maze
//  private JRadioButton stackButton;   // Solve the maze using a stack?
//  private JRadioButton queueButton;   //   using a queue?
//  private JRadioButton distButton;    //   using a distance-based priority queue?
//  private JRadioButton aStarButton;   //   using an A*-based priority queue?
//  private Maze maze;									// The maze to be solved
//  private char [][] mazeText;			   // The last maze read from  a file
//
//	/**
//	 * Constructs the frame and its buttons
//	 */
//	public MazeFrame()
//  {
//  	JButton stepButton;				// Holds the button to do next search step
//  	JButton clearButton;      // Clear the current maze
//  	JButton readButton;       // Read in a new maze from text file
//
//		final int DEFAULT_FRAME_HEIGHT = 600;
//		final int DEFAULT_FRAME_WIDTH = 500;
//	  final int DELAY = 100;	  // Delay in milliseconds between steps
//
//		setSize(DEFAULT_FRAME_WIDTH, DEFAULT_FRAME_HEIGHT);
//
//		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//
//		maze = null;              // No maze yet
//		mazeText = null;
//
//		StepListener stepListener = new StepListener();
//		timer = new Timer (DELAY, stepListener);
//
//		RadioListener radioListener = new RadioListener();
//
//		// construct components
//
//		mazeCanvas = new MazePanel();
//
//		stepButton = new JButton("Step");
//		stepButton.setToolTipText("Do one step in solving the maze");
//		stepButton.addActionListener(stepListener);
//
//		solveButton = new JButton("Solve");
//		solveButton.setToolTipText("Solve the maze");
//		solveButton.addActionListener(new SolveListener());
//
//		readButton = new JButton("Read");
//		readButton.setToolTipText("Read a new maze from a file");
//		readButton.addActionListener(new ReadListener());
//
//		clearButton = new JButton("Clear");
//		clearButton.setToolTipText("Clear the maze so can be re-solved");
//		clearButton.addActionListener(radioListener);
//
//		// add components to the controlButtonPanel.  As a JFrame it uses
//		// the Flow Layout manager.
//
//		JPanel controlButtonPanel = new JPanel();		// To group the buttons
//		controlButtonPanel.add(readButton);
//		controlButtonPanel.add(clearButton);
//		controlButtonPanel.add(stepButton);
//		controlButtonPanel.add(solveButton);
//
//
//  	// Radio buttons for the solver type.  Initially, stack is selected.
//
//    stackButton = new JRadioButton("Stack");
//    stackButton.setSelected(true);
//    stackButton.setToolTipText("Solves the maze using a stack");
//    stackButton.addActionListener(radioListener);
//
//    queueButton = new JRadioButton("Queue");
//    queueButton.setToolTipText("Solves the maze using a queue");
//    queueButton.addActionListener(radioListener);
//
//    distButton = new JRadioButton("Distance");
//    distButton.setToolTipText("Solves the maze using a distance-based priority queue");
//    distButton.addActionListener(radioListener);
//
//    aStarButton = new JRadioButton("A*");
//    aStarButton.setToolTipText("Solves the maze using an A*-based priority queue");
//    aStarButton.addActionListener(radioListener);
//
//    // Add the radio buttons to a button group to ensure that
//    // only one button is selected at a time.
//
//    ButtonGroup solveGroup = new ButtonGroup();
//    solveGroup.add(stackButton);
//    solveGroup.add(queueButton);
//    solveGroup.add(distButton);
//    solveGroup.add(aStarButton);
//
//    // A panel for the radio buttons.  Put a titled and etched border around it.
//
//    JPanel radioGroupPanel = new JPanel();
//    radioGroupPanel.add(stackButton);
//    radioGroupPanel.add(queueButton);
//    radioGroupPanel.add(distButton);
//    radioGroupPanel.add(aStarButton);
//    radioGroupPanel.setBorder(new TitledBorder(new EtchedBorder(),
//    		"Solution approach"));
//
//    // Line up the component panels.
//    // Make the control buttons and radio buttons panels line up vertically, using
//    // a Box Layout manager.
//
//    JPanel southPanel = new JPanel();
//    southPanel.setLayout(new BoxLayout(southPanel, BoxLayout.Y_AXIS));
//    southPanel.add(controlButtonPanel);
//    southPanel.add(radioGroupPanel);
//
//    // Add the two remaining panels to the content pane.
//
//    Container contentPane = getContentPane();
//    contentPane.add(mazeCanvas, BorderLayout.CENTER);
//    contentPane.add(southPanel, BorderLayout.SOUTH);
//   }
//
//  /**
//   * Handle step and timer events.  Performs one step of the maze
//   * search (unless the search is already finished).
//   */
//	private class StepListener implements ActionListener
//	{
//		public void actionPerformed(ActionEvent event)
//		{
//			if(maze == null || maze.searchDone()) {
//				timer.stop();		// No need to run after maze is solved
//				solveButton.setText("Solve");
//			}
//			else
//				maze.stepMaze();
//
//			repaint();
//		}
//	}
//
//  /**
//   *  Handle solve button - toggle timer on/off and label solve/pause
//   */
//	private class SolveListener implements ActionListener
//	{
//		public void actionPerformed(ActionEvent event)
//		{
//			if(timer.isRunning())
//			{
//				timer.stop();
//				solveButton.setText("Solve");
//			}
//			else
//			{
//				timer.start();
//				solveButton.setText("Pause");
//			}
//		}
//	}
//
//  /**
//   * Handle read button
//   */
//	private class ReadListener implements ActionListener
//	{
//		public void actionPerformed(ActionEvent event)
//		{
//		  mazeText = MazeIO.readMaze("data");
//		  createMaze();
//		}
//	}
//
//  /**
//   * Handle radio buttons (also works for clear button)\
//   */
//	private class RadioListener implements ActionListener
//	{
//		public void actionPerformed(ActionEvent event)
//		{
//		  createMaze();
//		}
//	}
//
//	/**
//	 * Creates a new maze given the current radio buttons and mazeText
//	 */
//	private void createMaze() {
//		if (mazeText == null)
//			maze = null;		// Cannot create a maze if text is bad
//	  else {
//	  	Sequencer seq = null;  // The sequencer to be used to solve the maze
//
//	  	if (stackButton.isSelected())
//	  		seq = new StackSequencer();
//	  	else if (queueButton.isSelected())
//	  		seq = new QueueSequencer();
//	  	else if (distButton.isSelected())
//	  		seq = new DistSequencer();
//	  	else if (aStarButton.isSelected())
//	  		seq = new AStarSequencer();
//	  	else
//	  		System.err.println("Error in MazeFrame.java - no radio button selected");
//
//	  	// We want to construct a maze, but because we can have errors in the
//	  	// text field we first ask if we make a valid maze.
//	  	Maze maybeMaze = new Maze(mazeText, seq);
//	  	if (maybeMaze.isValid())
//	  	  maze = maybeMaze;
//	  	else
//	  	{
//	  		maze = null;
//	  		mazeText = null;  // Don't want to get error messages each button click.
//	  	}
//	  }
//
//		mazeCanvas.setMaze(maze);  // Let the drawing canvas know the new maze
//		repaint();
//	}
//}
