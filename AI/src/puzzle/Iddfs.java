package puzzle;

import java.util.HashSet;
import java.util.Stack;

/**
 * Iterative Deepening Depth-First	 algorithm procedure.
 * Explained by GeeksForGeeks at: https://www.geeksforgeeks.org/iterative-deepening-searchids-iterative-deepening-depth-first-searchiddfs/
 * @author Pedro Fonseca
 */
public class Iddfs implements Algorithm {

	/**
	 * General methods provider instance.
	 */
	private General general;

	/**
	 * Name of the Algorithm.
	 */
	public String name = "Iterative Deepening Depth-First Search (IDDFS)";

	/**
	 * Stores the Nodes still to be visited in reversed order.
	 */
	private Stack<Node> toVisit = new Stack<>();

	/**
	 * Stores the visited Nodes with a unique rule so that we do not run into redundancy.
	 */
	private HashSet<Node> visited = new HashSet<>();

	/**
	 * Constructor for the Algorithm.
	 */
	public Iddfs(General general) {
		this.general = general;
	}
	
	/**
	 * Procedure of "opening" a Node by getting its possible children, testing every move possible.
	 * Similarly to DFS, just inserts the children nodes into the stack in reverse order.
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
		
		int frontier = 1;
		
		Node current;
		
		while (frontier<50) { 		//limit can be eliminated if statement is simply "true".
			while (toVisit.size() > 0) {
				current = toVisit.pop(); //take last element out of toVisit stack.
				
				general.totalNodes++; //counts number of visited nodes.
			
				if(general.checkSolution(current)) { //if the current node is the solution, the path taken is returned.
					return current.getPath();
				}

				if (!visited.contains(current) && current.depth < frontier) {
					getChildren(current);
					visited.add(current);
				}
			}
			
			//The algorithm can now go one more level down.
			frontier++;
			//Reset of the toVisit Stack.
			toVisit.push(start);
			//Reset of the visited Set.
			visited = new HashSet<>();
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
