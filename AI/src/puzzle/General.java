package puzzle;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Stack;

/**
 * General class holds all the basic operations to be done during the achievement of the solution.
 * @author Pedro Fonseca & Lázaro Sevilla
 *
 */
public class General {
	
	/**
	 * Number of rows of the puzzle.
	 */
	public int rows;
	
	/**
	 * Number of columns of the puzzle.
	 */
	public int cols;
	
	/**
	 * The starting state of the puzzle, given by the user.
	 * A state can also be called a Node, considering that the algorithms work as trees.
	 */
	private Node root;
	
	/**
	 * Stores the visited nodes (no repetition allowed).
	 */
	HashSet<int[][]> visited;
	
	/**
	 * Total number of nodes visited.
	 */
	int totalNodes;
	
	/**
	 * Stores the Nodes still to be visited in the reversed order they should be visited.
	 * This way, the first to be visited is the first to be popped.
	 */
	private Stack<Node> toVisit;
	
	/**
	 * Stores the possible moves represented by chars.
	 */
	private char dir[];
	
	/**
	 * Name of the algorithm being applied.
	 */
	private String currentAlgo;

	/**
	 * Constructor of the General methods class.
	 * @param puzzle Current state of the puzzle in a 2D integer array.
	 * @param rows Number of rows of the puzzle.
	 * @param cols Number of columns of the puzzle.
	 */
	public General(int[][] puzzle, int rows, int cols) {
		this.rows = rows;
		this.cols = cols;
		
		root = new Node(puzzle, this.rows, this.cols, this);
		visited = new HashSet<int[][]>();
		toVisit = new Stack<Node>();
		totalNodes = 0;
		
		dir = new char[4];
		dir[0] = 'l';
		dir[1] = 'd';
		dir[2] = 'r';
		dir[3] = 'u';
	}
	
	/**
	 * Getter for the root Node.
	 * @return Root Node
	 */
	public Node getRoot() {
		return root;
	}
	
	/**
	 * Getter for the total number of Nodes visited.
	 * @return Total of Nodes visited.
	 */
	public int getTotalNodes() {
		return totalNodes;
	}
	
	/**
	 * Getter for the name of the algorithm being used.
	 * @return Name of the algorithm
	 */
	public String getCurrentAlgo() {
		return currentAlgo;
	}

	/**
	 * Defines the algorithm to be used.
	 * @param currentAlgo Algorithm to be used
	 */
	public void setCurrentAlgo(String currentAlgo) {
		this.currentAlgo = currentAlgo;
	}
	
	/**
	 * Getter for the toVisit Stack.
	 * @return Stack with all the Nodes to be visited.
	 */
	public Stack<Node> getToVisit(){
		Stack<Node> copy = new Stack<Node>();
		for (Node n : this.toVisit) {
			copy.push(n);
		}
		return copy;
	}

	/**
	 * Marks another Node to be visited by adding it to the toVisit Stack.
	 * @param n Node to be visited.
	 */
	public void insertVisit(Node n) {
		toVisit.push(n);
	}
	
	/**
	 * Changes the list of Nodes and its order to a different one.
	 * @param stack New list of Nodes to visit.
	 */
	public void setToVisit(Stack<Node> stack) {
		for (Node n : stack) {
			toVisit.push(n);
		}
	}
	
	/**
	 * Getter for the directions to be taken by the empty tile.
	 * @return Array of the directions possible in chars.
	 */
	public char[] getDir() {
		char[] copy = Arrays.copyOf(dir, dir.length);
		return copy;
	}
	
	/**
	 * Generates the perfect puzzle, the wanted solution.
	 * @param rows Number of rows.
	 * @param cols Number of columns.
	 * @return Solved puzzle used to compare it to the current Node.
	 */
	public int[][] generateSolution(int rows, int cols) {
		int[][] sol = new int[rows][cols];
		int cont = 1;
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				if (i == (rows-1) && j == (cols-1)) {
					sol[i][j] = 0;
				} else {
					sol[i][j] = cont;
					cont++;
				}
			}
		}
		return sol;
	}
	
	/**
	 * Checks whether the present Node is the solution.
	 * @param n Current node.
	 * @return Status of the puzzle being solved or not.
	 */
	public boolean checkSolution(Node n) {
		int[][] sorted = generateSolution(rows, cols);
		int[][] sol = n.getPuzzle();
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				if (sorted[i][j] != sol[i][j])
					return false;
			}
		}
		return true;
	}

	/**
	 * Returns zero's (the empty tile) coordinates.
	 * @param n Current Node.
	 * @return Position of it in an array with two integers or null in case of failure.
	 */
	public int[] getZeroPos(Node n) {
		int[][] puzzle = n.getPuzzle();
		int rows = n.rows;
		int cols = n.cols;
		
		for (int i = 0; i < rows; i++) {
			for  (int j = 0; j < cols; j++) {
				if (puzzle[i][j] == 0) {
					int[] pos = new int[2];
					pos[0] = i;
					pos[1] = j;
					return pos;
				}
			}
		}
		return null;
	}
	
	/**
	 * Tests if a move is possible and, if it is, returns the child originated by it.
	 * @param n Father Node.
	 * @param dir Direction to be used in the move tested.
	 * @return Child produced by the move.
	 */
	
	public Node movePossible(Node n, char dir) {
		int aux = -1; //helps swapping the cells
		int change[] = {0, 0};
		
		//splitting new child from the father
		Node temp = new Node(n, this);
		
		switch(dir) {
		case 'u': change[0]--;
			break;
		case 'd': change[0]++;
			break;
		case 'l': change[1]--;
			break;
		case 'r': change[1]++;
			break;
		}
		
		int[] zeroPos = getZeroPos(n);
		
		if ((zeroPos[0]+change[0] < 0) || (zeroPos[1]+change[1] < 0) || (zeroPos[0]+change[0] == rows) 
				|| (zeroPos[1]+change[1] == cols)) { //Impossible case
			return null;
		} else {
			aux = temp.getPuzzleElement(zeroPos[0]+change[0], zeroPos[1]+change[1]); //saves temporarily the value of the cell switching with 0.
			temp.setPuzzleElement(zeroPos[0]+change[0], zeroPos[1]+change[1], 0);
			temp.setPuzzleElement(zeroPos[0], zeroPos[1], aux);
		}
		
		//Positions have been changed at this point, the move is complete.
		//Children have a higher depth than their parent.
		temp.depth++;
		
		//The path has now got another letter.
		temp.lastMove = dir;
		
		//The previous Node to the new one is the Node n we received as an argument.
		temp.prev = new Node(n, this);
		
		return temp;
	}
	
}
