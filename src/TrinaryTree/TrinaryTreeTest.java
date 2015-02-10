package TrinaryTree;

import static org.junit.Assert.*;

import org.junit.Test;

public class TrinaryTreeTest {
	private TrinaryTree t = new TrinaryTree();
	
	@Test
	public void TestInsertRegularLinkedList() {
		t.insert(1);
		t.insert(2);
		t.insert(3);
		assertEquals("1 2 3 ", t.toStringLevelOrder());
	}
	
	@Test
	public void TestInsertDuplicate() {
		t.insert(2);
		t.insert(2);
		t.insert(2);
		assertEquals("2 2 2 ", t.toStringLevelOrder());
	}
	
	@Test
	public void TestInsertComplicated() {
		setUpExample();
		assertEquals("5 4 5 9 2 7 2 ", t.toStringLevelOrder());
	}
	
	@Test
	public void TestDeleteSimple() {
		t.insert(2);
		t.delete(2);
		assertEquals("", t.toStringLevelOrder());
	}
	
	@Test
	public void TestDeleteNotDuplicatedLeaf() {
		setUpExample();
		t.delete(7);
		assertEquals("5 4 5 9 2 2 ", t.toStringLevelOrder());
	}
	
	@Test
	public void TestDeleteDuplicatedLeaf() {
		setUpExample();
		t.delete(2);
		assertEquals("5 4 5 9 2 7 ", t.toStringLevelOrder());
	}
	
	@Test
	public void TestDeleteNotDuplicatedNonLeaf() {
		setUpExample();
		t.delete(4);
		assertEquals("5 2 5 9 2 7 ", t.toStringLevelOrder());
	}
	
	@Test
	public void TestDeleteDuplicatedNonLeaf() {
		setUpExample();
		t.insert(4);
		t.delete(4);
		assertEquals("5 4 5 9 2 7 2 ", t.toStringLevelOrder());
	}
	
	@Test
	public void TestDeleteNonLeafRightMin() {
		setUpExample();
		t.insert(8);
		t.delete(7);
		assertEquals("5 4 5 9 2 8 2 ", t.toStringLevelOrder());
	}
	
	@Test(expected=NullPointerException.class)
	public void TestDeleteNotFound() {
		t.insert(5);
		t.delete(3);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void TestNullTree() {
		t.delete(2);
	}
	
	private void setUpExample() {
		t.insert(5);
		t.insert(4);
		t.insert(9);
		t.insert(5);
		t.insert(7);
		t.insert(2);
		t.insert(2);
	}
}