/**
 * Building a BinaryTree_Editted from outside the BinaryTree_Editted class.
 * Note that left and right are private, so can't set them directly.  Normally use
 * parseNewick method to create a tree, here we do it manually.
 * @author Tim Pierson, Dartmouth CS10, Spring 2019
 *
 */
public class BuildTree {
	/**
	 * Build tree:
	 * 				G
	 * 			  /   \
	 * 			B		F
	 * 		   / \     / \
	 * 		  A   C	  D   E
	 */

	public static void main(String[] args) {
		//create left subtree
		BinaryTree_Editted<String> left = new BinaryTree_Editted<String>("B",
				new BinaryTree_Editted<String>("A"),
				new BinaryTree_Editted<String>("C"));
		//create right subtree
		BinaryTree_Editted<String> right = new BinaryTree_Editted<String>("F",
				new BinaryTree_Editted<String>("D"),
				new BinaryTree_Editted<String>("E"));
		//create root
		BinaryTree_Editted<String> root = new BinaryTree_Editted<String>("G",left,right);
		System.out.println(root);
		
	}

}
