package TrinaryTree;

/**
 * @author Cyndi
 * This defines the object TrinaryTreeNode that is usable in Trinary Search Tree
 * Client have access to its mid, left and right node and its data
 * 			*** I have not  written any getMid, getLeft or getRight method
 * 				with the strategy of copy-in and copy-out to make the nodes immutable
 */
public class TrinaryTreeNode {
	public TrinaryTreeNode mid;
	public TrinaryTreeNode left;
	public TrinaryTreeNode right;
	public int data;
	
	/**
	 * Constructor
	 * @param data the integer data to be assigned to the node
	 */
	public TrinaryTreeNode(int data) {
		this.data = data;
		right = null;
		left = null;
		mid = null;
	}
}