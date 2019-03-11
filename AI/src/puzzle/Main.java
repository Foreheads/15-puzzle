package puzzle;

import java.util.Scanner;

/**
 * Main program class.
 * @author Pedro Fonseca & Lázaro Sevilla
 *
 */

public class Main {

	/**
	 * Main method of the program.
	 * @param args
	 */
	public static void main(String[] args) {
		
		//Used to read the user's input.
		Scanner scanner = new Scanner(System.in);

		//Reading the size wanted by the user.
		System.out.println("Length of the side of the puzzle:");
		int side = scanner.nextInt();

		//Reading the puzzle
		System.out.println("Insert puzzle numbers (separated by a space): ");
		int[][] matrix = new int[side][side];
		for (int i = 0; i < side; i++) {
			for (int j = 0; j < side; j++) {
				matrix[i][j] = scanner.nextInt();
			}
		}
		
		General general = new General(matrix, side, side);

		if (general.checkSolution(general.getRoot())) {
			System.out.println("The puzzle given is already solved!");
		} else {
			//Starts counting execution time for the algorithm.
			long start = System.currentTimeMillis();

			//Uncomment only the algo to be used.
			Algorithm a = new Dfs(general);
			//Algorithm a = new Bfs(general);
			//Algorithm a = new Iddfs(general);
			//Algorithm a = new BestFS(general);
			//Algorithm a = new Astar(general);
			//Algorithm a = new SMAstar(general);
			
			// Solution is stored in a String.
			String sol = a.run(general.getRoot()); //feeds the algorithm it's puzzle to solve.
			
			//Stops execution time tracking.
			long time = System.currentTimeMillis() - start;
			
			//Final results are displayed.
			if (sol == "Impossible") {
				System.out.println("\nThere's no solution for this puzzle.");
			} else {
				System.out.println("\n" + a.getName());
				System.out.println("Solution: " + sol.toUpperCase());
				System.out.println("Amount of moves: " + sol.length());
				System.out.println("Amount of visited nodes: " + general.totalNodes);
				System.out.println("Execution time: " + time + " ms (" + time/1000 + "s)");
			}
		} 
		
		
	}
}
