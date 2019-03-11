package puzzle;

/**
 * Interface to be implemented by every Algorithm class.
 * @author Pedro Fonseca
 *
 */
public interface Algorithm {
	
	/**
	 * Method that should hold the procedure of the Algorithm to retrieve a Node's children Nodes.
	 * @param root Father Node.
	 */
	void getChildren (Node root);
	
	/**
	 * Method that should hold the procedure of the Algorithm running properly.
	 * @param root Starting Node.
	 * @return String with the solution (all moves done).
	 */
	String run (Node root);
	
	/**
	 * Identification of the Algorithm used.
	 * @return Name of the Algorithm.
	 */
	String getName();
}
