package puzzle;

import java.util.HashSet;
import java.util.Stack;

/**
 * Breadth-First Search algorithm procedure.
 * Explained by GeeksForGeeks at: https://www.geeksforgeeks.org/breadth-first-search-or-bfs-for-a-graph/
 * @author Pedro Fonseca
 */
public class Bfs implements Algorithm {
	
	/**
	 * General methods provider instance.
	 */
	private General general;

	/**
	 * Name of the Algorithm.
	 */
	public String name= "Breadth First Search (BFS)";

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
	public Bfs(General general) {
		this.general = general;
		toVisit = new Stack<>();
		visited = new HashSet<>();
	}

	/**
	 * Procedure of "opening" a Node by getting its possible children, testing every move possible.
	 */
	public void getChildren(Node cur) {
		
		general.setCurrentAlgo(name);
		
		Stack<Node> newToVisit = new Stack<Node>();
		
		//Children inserted in an empty Stack.
		for (int i = 3; i >= 0; i--) {
			char dir = general.getDir()[i];
			
			Node aux = general.movePossible(cur, dir);
			if (aux != null) { //If is possible to visit it
				newToVisit.push(aux);
			}
		}

		//Every Node set to visit is inserted in the same Stack at the end.
		for (Node n : toVisit) {
			newToVisit.push(n);
		}
		
		//The new stack becomes the toVisit Stack.
		toVisit = newToVisit;
	}

	/**
	 * Method where the Algorithm is ran at.
	 * The path starts empty and depth as 0 for the root Node.
	 */
	public String run(Node start) {
		 //Push the first element so the algorithm can start running.
		toVisit.push(start);
		
		Node current;
		
		while (!toVisit.isEmpty()) {
			
			 //take last element out of toVisit stack.
			current = toVisit.pop();
			general.totalNodes++;
			
			//if the current node is the solution, the path taken is returned.
			if(general.checkSolution(current)) {
				return current.getPath();
			}
			
			if (!visited.contains(current)) {
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
