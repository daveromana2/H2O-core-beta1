package ai.montecarloheuristic5;

/**
 * Classe che implementa il root
 * @author Mina
 *
 */

public class Root {
	private Node root;

	/**
	 * It creates a root and bond it with a given node.
	 * @param node Node with initial stage.
	 */
	public Root(Node node) {
		this.root = node;
	}
	
	/**
	 * @return the root
	 */
	public Node getRoot() {
		return root;
	}
}
