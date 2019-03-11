package puzzle;

import java.util.Arrays;

/**
 * A Node represents a state of the puzzle inside the tree of each algorithm.
 * @author Pedro Fonseca
 *
 */
public class Node implements Comparable<Node> {
	
	/**
	 * Contains all the tile numbers and their positions.
	 */
	int[][] puzzle;
	
	/**
	 * Depth inside the algorithm's tree.
	 * This can also be seen as amount of moves already made.
	 */
	int depth;
	
	/**
	 * Amount of rows the puzzle has.
	 */
	int rows;
	
	/**
	 * Amount of columns the puzzle has.
	 */
	int cols;
	
	/**
	 * Previous Node to this one.
	 * The parent Node that originated this one after a single specific move.
	 */
	Node prev;
	
	/**
	 * Character indicating the direction of the last move.
	 * <bold>l</bold> is left, <bold>r</bold> is right, <bold>u</bold> is up and <bold>d</bold> is down.
	 */
	char lastMove;
	
	/**
	 * Library of the general utility methods.
	 */
	General general;
	
	/**
	 * Constructor for new Nodes.
	 * @param puzzle
	 * @param rows
	 * @param cols
	 * @param general
	 */
	public Node(int[][] puzzle, int rows, int cols, General general) {
		depth = 0;
		this.rows = rows; 
		this.cols = cols;
		this.prev = null;
		this.lastMove = 's'; //s symbolizes the start. It is not presented in the solution.
		this.puzzle = new int[rows][cols];
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				this.puzzle[i][j] = puzzle[i][j];
			}
		}
		this.general = general;
	}
	
	/**
	 * Constructor used to create copies of Node objects.
	 * @param n
	 * @param general
	 */
	public Node(Node n, General general) {
		this.depth = n.depth;
		this.lastMove = n.lastMove;
		this.cols = n.cols;
		this.rows = n.rows;
		this.prev = n.prev;
		
		this.puzzle = new int[n.rows][n.cols];
		
		for (int i = 0; i < n.rows; i++) {
			for (int j = 0; j < n.cols; j++) {
				this.puzzle[i][j] = n.puzzle[i][j];
			}
		}
		this.general = general;
	}
	
	/**
	 * Getter that retrieves the puzzle's contents and number positions.
	 * @return Copy of the puzzle's 2D integer array.
	 */
	public int[][] getPuzzle(){
		int[][] copy = new int[rows][cols];

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				copy[i][j] = this.puzzle[i][j];
			}
		}
		return copy;
	}
	
	/**
	 * Getter for the number of rows of the puzzle.
	 * @return Number of rows.
	 */
	public int getRows() {
		return this.rows;
	}
	
	/**
	 * Getter for the number of columns of the puzzle.
	 * @return Number of columns.
	 */
	public int getCols() {
		return this.cols;
	}
	
	/**
	 * Getter for the last move.
	 * @return Character representing the last move's direction.
	 */
	public char getLastMove() {
		return lastMove;
	}
	
	/**
	 * Getter for the current depth.
	 * @return Integer that indicates the current depth of the puzzle.
	 */
	public int getDepth() {
		return depth;
	}
	
	/**
	 * Getter for an element of the puzzle in a specific location using integer coordinates.
	 * @param x coordinate
	 * @param y coordinate
	 * @return Tile's number
	 */
	public int getPuzzleElement(int x, int y) {
		return puzzle[x][y];
	}
	
	/**
	 * Sets a tile to be a different number.
	 * @param x coordinate
	 * @param y coordinate
	 * @param element New number.
	 */
	public void setPuzzleElement(int x, int y, int element) {
		puzzle[x][y] = element;
	}
	
	/**
	 * Sets the last move to a different one after a move is made.
	 * @param lm New last move.
	 */
	public void setLastMove(char lm) {
		this.lastMove = lm;
	}
	
	/**
	 * Setter for the depth of a Node.
	 * @param depth
	 */
	public void setDepth(int depth) {
		this.depth = depth;
	}
	
	/**
	 * Getter for the path taken to get to this Node.
	 * @return String indicating the path's moves in order.
	 */
	public String getPath() {
		String path = "";
		Node cur = this;
		try {
			while (!cur.prev.equals(null)) {
				path += cur.lastMove;
				cur = cur.prev;
			}
		} catch (NullPointerException e) {}
		return new StringBuilder(path).reverse().toString();
	}

	/**
	 * Getter for the amount of wrong tiles in this Node when compared to the solution.
	 * @return Integer with the number of wrong tiles.
	 */
	public int getWrongTiles() {
		int [][] sorted = general.generateSolution(rows, cols);
		int[][] sol = this.getPuzzle();
		int wrong = cols*rows;

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				if (sorted[i][j] == sol[i][j])
					wrong--;
			}
		}
		return wrong;
	}

	/**
	 * Prints the 2D array of the puzzle.
	 * @return String with the whole puzzle.
	 */
	public String toString() {
		String puzzle = "";
		for (int i = 0; i < this.rows; i++) {
			for (int j = 0; j < this.cols; j++) {
				puzzle += this.puzzle[i][j] + " ";
				if (j == (this.cols-1)) {
					puzzle += "\n";
				}
			}
		}
		return puzzle;
	}
	
	/**
	 * Method used to compare two Nodes that allows sorting the Nodes according to amount of wrong tiles inside the PriorityQueue used in BestFS, A* and SMA*.
	 */
	@Override
	public int compareTo(Node aux) {
		if (!general.getCurrentAlgo().equals("A*") || !general.getCurrentAlgo().equals("SMA*"))
		{
			if (this.getWrongTiles() > aux.getWrongTiles())
				return 1;
			else if (this.getWrongTiles() == aux.getWrongTiles())
				return 0;
			else return -1;
		} else {
			if (this.getWrongTiles()+this.depth > aux.getWrongTiles()+this.depth)
				return 1;
			else if (this.getWrongTiles()+this.depth == aux.getWrongTiles()+this.depth)
				return 0;
			else return -1;
		}
	}

	/**
	 * Produces a hash code for this Node to identify it.
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.deepHashCode(puzzle);
		return result;
	}

	/**
	 * Method used to check whether two Nodes are the same or different ones.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Node other = (Node) obj;
		if (!Arrays.deepEquals(puzzle, other.puzzle))
			return false;
		return true;
	}
}

	