package TrinaryTree;

/**
 * This is the TrinaryTree method that defines the object of the tri tree
 * that stores integer
 */
import java.util.LinkedList;
import java.util.Queue;

public class TrinaryTree {
	private TrinaryTreeNode root;
	
	/**
	 * constructor, construct the Trinary tree to make it ready for use
	 */
	public TrinaryTree() {
		root = null;
	}
	
	/**
	 * insert the data into the tree
	 * 
	 * @param data the data to be inserted
	 */
	public void insert(int data) {
		root = insertHelper(root, data);
	}
	
	/*
	 * Helper method, recursively calls
	 * Base Case: when we find a place to insert the data
	 * Recursively calls when the node is not null 
	 */
	private TrinaryTreeNode insertHelper(TrinaryTreeNode node, int data) {
		if (node == null) {
			node = new TrinaryTreeNode(data);
		} else {
			if (data == node.data) {
				node.mid = insertHelper(node.mid, data);
			} else if (data < node.data) {
				node.left = insertHelper(node.left, data);
			} else if (data > node.data ){
				node.right = insertHelper(node.right, data);
			} 
		}
		return node;
	}
	
	/**
	 * Delete the data passed in from the tree. 
	 * If there are duplicated nodes with the same value, the leaf node is been deleted
	 * If non-leaf node is deleted, will replace with the smallest value in right child
	 * 
	 * @throws IllegalArgumentException if the root is null
	 * @throws NullPointerException if the data passed in does not exist in the tree
	 * @param data the data of the node to be deleted
	 */
	public void delete(int data) {
		if (root == null) {
			throw new IllegalArgumentException("the root cannot be null!");
		}
		root = deleteHelper(root, data);
	}
	
	/*
	 * Recursively calls when we do not find the right node to be deleted
	 * Base Case when we find the right data and do possible replacement 
	 * for the deleted data
	 */
	private TrinaryTreeNode deleteHelper(TrinaryTreeNode root, int data) {
		// when we find the right data, but still need to determine
		// whether it has duplicate or not, leaf or non-leaf
		if (root.data == data) {
			if (root.mid != null) {			// if there are duplicated values
				root.mid = deleteHelper(root.mid, data);
			} else if(root.right != null) {		// we can find a min from right child
				int min = getMin(root.right);
				root.data = min;
				root.right = deleteHelper(root.right, min);
			} else {						// else replace it with anything on left
				root = root.left;
			}
		} else if (root.data > data) {
			root.left = deleteHelper(root.left, data);
		} else {
			root.right = deleteHelper(root.right, data);
		}
		return root;
	}

	/**
	 * returns the minimum number under the root passed in
	 * 
	 * @requires root cannot be null
	 * @param root the place we are started to look for the min
	 * @return the minimum number under the root
	 */
	public int getMin(TrinaryTreeNode root) {
		if (root.left == null) {
			return root.data;
		} else {
			return getMin(root.left);
		}
	}
	
	/**
	 * Returns a string that output the tree in level order 
	 * each node is separated by space
	 * empty tree has a empty string returned
	 * 
	 * @return a string representing the tree
	 */
	public String toStringLevelOrder() {
		String output = "";
		if (root != null) {
			Queue<TrinaryTreeNode> q = new LinkedList<TrinaryTreeNode>();
			q.add(root);
			
			// save all the nodes in queue, and dequeue one by one to keep the
			// level order
			while (!q.isEmpty()) {
				TrinaryTreeNode temp = q.poll();
				output += temp.data + " ";
				
				if (temp.left != null) {
					q.add(temp.left);
				}			
				if (temp.mid!= null) {
					q.add(temp.mid);
				}
				if (temp.right!= null) {
					q.add(temp.right);
				}
			}
		}
		return output;
	}
}