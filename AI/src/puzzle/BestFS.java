package puzzle;

import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Stack;

/**
 * Best-First Search algorithm procedure.
 * Explained by GeeksForGeeks at: https://www.geeksforgeeks.org/best-first-search-informed-search/
 * @author Pedro Fonseca
 */
public class BestFS implements Algorithm {

	/**
	 * General methods provider instance.
	 */
	private General general;
	
	/**
	 * Name of the Algorithm.
	 */
	public String name = "Best First Search (BestFS)";

	/**
	 * Stores the Nodes still to be visited in reversed order.
	 */
	private Stack<Node> toVisit;
	
	/**
	 * Stores the visited Nodes with a unique rule so that we do not run into redundancy.
	 */
	private HashSet<Node> visited;
	
	/**
	 * Stores all the Nodes to visit in descending order of number of wrong tiles.
	 */
	private PriorityQueue<Node> wrongTiles;

	/**
	 * Constructor for the Algorithm.
	 */
	public BestFS(General general) {
		this.general = general;
		toVisit = new Stack<>();
		visited = new HashSet<>();
		wrongTiles = new PriorityQueue<>();
	}

	/**
	 * Procedure of "opening" a Node by getting its possible children, testing every move possible.
	 */
	public void getChildren (Node cur) {
		Node aux;
		
		char[] dir = general.getDir();

		//Inserting every possible new move to the toVisit Stack.
		for (int i = 3; i >= 0; i--) {
			if((aux = general.movePossible(cur, dir[i])) != null)
				toVisit.push(aux);
		}

		//Removing every Node from the toVisit Stack and inserting everything in the wrongTiles PriorityQueue where all the Nodes are sorted automatically.
		Node popped;
		for (int i = 0; i < toVisit.size(); i++) {
			popped = toVisit.pop();
			wrongTiles.add(popped);
		}
		
		//Sorted elements are passed back to toVisit.
		for (Node n : wrongTiles) {
			toVisit.push(n);
		}
		
		
	}

	/**
	 * Method where the Algorithm is ran at.
	 * The path starts empty and depth as 0 for the root Node.
	 */
	public String run(Node root) {
		
		general.setCurrentAlgo(name);
		
		 //Push the first element so the algorithm can start running.
		toVisit.push(root);
		Node current;
		
		while (toVisit.size() > 0) {
			current = toVisit.pop();
			general.totalNodes++;
			
			if (!visited.contains(current)) {
				if (general.checkSolution(current))
					return current.getPath();

				getChildren(current);
				visited.add(current);
			}
		}
		
		return "Impossible";
	}

	/**
	 * Similar to a toString method, returns the name of the Algorithm.
	 */
	public String getName() {
		return name;
	}
	
}
