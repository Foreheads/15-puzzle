package puzzle;

import java.util.HashSet;
import java.util.Stack;

/**
 * Depth-First Search algorithm procedure.
 * Explained by GeeksForGeeks at: https://www.geeksforgeeks.org/depth-first-search-or-dfs-for-a-graph/
 * @author Pedro Fonseca
 */
public class Dfs implements Algorithm {

	/**
	 * General methods provider instance.
	 */
	private General general;
	
	/**
	 * Name of the Algorithm.
	 */
	public String name = "Depth-First Search (DFS)";

	/**
	 * Stores the Nodes still to be visited in reversed order.
	 */
	private Stack<Node> toVisit;

	/**
	 * Stores the visited Nodes with a unique rule so that we do not run into redundancy.
	 */
	private HashSet<Node> visited;

	/**
	 * Constructor for the Algorithm.
	 */
	public Dfs(General general) {
		this.general = general;
		visited = new HashSet<>();
		toVisit = new Stack<>();
	}

	/**
	 * Procedure of "opening" a Node by getting its possible children, testing every move possible.
	 */
	public void getChildren(Node cur) {
		Node aux; //going to store the child.
		char[] dir = general.getDir();
		
		for (int i = 3; i >= 0; i--) {
			if((aux = general.movePossible(cur, dir[i])) != null) {
				toVisit.push(aux); //if move X is possible.
			}
		}
	}

	/**
	 * Method where the Algorithm is ran at.
	 * The path starts empty and depth as 0 for the root Node.
	 */
	public String run(Node start) {
		
		general.setCurrentAlgo(name);
		
		toVisit.push(start); //Push the first element so the algorithm can start running.

		Node current;

		while (!toVisit.isEmpty()) {
			current = toVisit.pop(); //take last element out of toVisit stack.
			general.totalNodes++;
			if (!visited.contains(current)) { //visited yet? If so, the node is ignored (and consequently the children too)

				//If it is the solution, the program finishes.
				if(general.checkSolution(current)) { //if the current node is the solution, the path taken is returned.
					return current.getPath();
				}
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
