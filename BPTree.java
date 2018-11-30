/**
 * Filename:   BPTree.java
 * Project:    Team Project
 * Authors:    Debra Deppeler, Xudong Tang, Yixian Gan, 
 *			Yiye Dang, Daoxing Zhang, Qiuhong Li
 * Emails:     xtang75@wisc.edu, ygan23@wisc.edu, dang6@wisc.edu, 
 *			dzhang268@wisc.edu, qli288@wisc.edu
 *
 * Semester:   Fall 2018
 * Course:     CS400
 * Lecture:    lec001
 * 
 * Due Date:   Before 10pm on November 30, 2018
 * Version:    1.0
 * 
 * Credits:    N/A
 * 
 * Bugs:       no known bugs
 */

package application;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Implementation of a B+ tree to allow efficient access to many different
 * indexes of a large data set. BPTree objects are created for each type of
 * index needed by the program. BPTrees provide an efficient range search as
 * compared to other types of data structures due to the ability to perform
 * log_m N lookups and linear in-order traversals of the data items.
 * 
 * @author sapan (sapan@cs.wisc.edu)
 * @author Xudong Tang (xtang75@wisc.edu)
 * @author Yixian Gan (ygan23@wisc.edu)
 * @author Yiye Dang (dang6@wisc.edu)
 * @author Daoxing Zhang (dzhang268@wisc.edu)
 * @author Qiuhong Li (qli288@wisc.edu)
 *
 * @param <K> key - expect a string that is the type of id for each item
 * @param <V> value - expect a user-defined type that stores all data for 
 * 		a food item
 */
public class BPTree<K extends Comparable<K>, V> implements BPTreeADT<K, V> {

	// Root of the tree
	private Node root;

	// Branching factor is the number of children nodes
	// for internal nodes of the tree
	private int branchingFactor;

	/**
	 * Public constructor
	 * 
	 * @param branchingFactor the specific branching factor
	 */
	public BPTree(int branchingFactor) {
		if (branchingFactor <= 2) {
			throw new IllegalArgumentException("Illegal branching factor: "
					+ branchingFactor);
		}
		this.branchingFactor = branchingFactor;
		this.root = new LeafNode();
	}

	/**
	 * Inserts the key and value in the appropriate nodes in the tree
	 * 
	 * Note: key-value pairs with duplicate keys can be inserted into the tree.
	 * 
	 * @see BPTreeADT#insert(java.lang.Object, java.lang.Object)
	 * @param key   the specific key
	 * @param value the specific value
	 */
	@Override
	public void insert(K key, V value) {
		if (key == null) {
			return;
		}
		// insert to the current root
		this.root.insert(key, value);

		// if the next level of nodes is full, split the next level of nodes
		// and create a new higher level of nodes
		if (this.root.isOverflow()) {
			// the new root after inserting one key-value pair
			InternalNode newRoot = new InternalNode();
			newRoot.children.add(root);
			newRoot.children.add(root.split());
			newRoot.keys.add(newRoot.children.get(1).getFirstLeafKey());
			this.root = newRoot;
		}
	}

	/**
	 * Gets the values that satisfy the given range search arguments.
	 * 
	 * If key is null or not found, return empty list. If comparator is null, 
	 * empty, or not according to required form, return empty list.
	 * 
	 * @see BPTreeADT#rangeSearch(java.lang.Object, java.lang.String)
	 * @param key        to be searched
	 * @param comparator is a string
	 * @return list of values that are the result of the range search; 
	 * 			if nothing found, return empty list
	 */
	@Override
	public List<V> rangeSearch(K key, String comparator) {
		// determine whether the comparator is the correct format
		if (!comparator.contentEquals(">=") && !comparator.contentEquals("==")
				&& !comparator.contentEquals("<="))
			return new ArrayList<V>();
		// Do the rangeSearch according to the level type of the root
		return root.rangeSearch(key, comparator);
	}

	/**
	 * Returns a string representation for the tree This method is
	 * provided to students in the implementation.
	 * 
	 * @see java.lang.Object#toString()
	 * @return a string representation
	 */
	@Override
	public String toString() {
		Queue<List<Node>> queue = new LinkedList<List<Node>>();
		queue.add(Arrays.asList(root));
		StringBuilder sb = new StringBuilder();
		while (!queue.isEmpty()) {
			Queue<List<Node>> nextQueue = new LinkedList<List<Node>>();
			while (!queue.isEmpty()) {
				List<Node> nodes = queue.remove();
				sb.append('{');
				Iterator<Node> it = nodes.iterator();
				while (it.hasNext()) {
					Node node = it.next();
					sb.append(node.toString());
					if (it.hasNext())
						sb.append(", ");
					if (node instanceof BPTree.InternalNode)
						nextQueue.add(((InternalNode) node).children);
				}
				sb.append('}');
				if (!queue.isEmpty())
					sb.append(", ");
				else {
					sb.append('\n');
				}
			}
			queue = nextQueue;
		}
		return sb.toString();
	}

	/**
	 * This abstract class represents any type of node in the tree.
	 * This class is a super class of the LeafNode and InternalNode types.
	 * 
	 * @author sapan
	 * @author Xudong Tang (xtang75@wisc.edu)
	 * @author Yixian Gan (ygan23@wisc.edu)
	 * @author Yiye Dang (dang6@wisc.edu)
	 * @author Daoxing Zhang (dzhang268@wisc.edu)
	 * @author Qiuhong Li (qli288@wisc.edu)
	 */
	private abstract class Node {

		// List of keys
		List<K> keys;

		/**
		 * Package constructor
		 */
		Node() {
			this.keys = new ArrayList<>();
		}

		/**
		 * Inserts key and value in the appropriate leaf node and 
		 * balances the tree if required by splitting
		 * 
		 * @param key the specific key
		 * @param value the specific value
		 */
		abstract void insert(K key, V value);

		/**
		 * Gets the first leaf key of the tree
		 * 
		 * @return the first leaf key of the tree
		 */
		abstract K getFirstLeafKey();

		/**
		 * Gets the new sibling created after splitting the node
		 * 
		 * @return the new sibling created after splitting the node
		 */
		abstract Node split();

		/**
		 * Gets the values that satisfy the given range search arguments.
		 * 
		 * If key is null or not found, return empty list. If comparator is null, 
		 * empty, or not according to required form, return empty list.
		 * 
		 * @param key to be searched
		 * @param comparator is a string
		 * @return list of values that are the result of the range search; 
		 * 			if nothing found, return empty list
		 */
		abstract List<V> rangeSearch(K key, String comparator);

		/**
		 * Determines whether the size of the Nodes size is larger than 
		 * the branch factor
		 * 
		 * @return true if the size of the Nodes is larger than the branch
		 * 			factor; otherwise, return false
		 */
		abstract boolean isOverflow();

		/**
		 * Returns a string representation for the tree This method is 
		 * provided to students in the implementation.
		 * 
		 * @see java.lang.Object#toString()
		 * @return a string representation
		 */
		public String toString() {
			return keys.toString();
		}

	} // End of abstract class Node

	/**
	 * This class represents an internal node (index Nodes) of the tree. 
	 * This class is a concrete sub class of the abstract Node class 
	 * and provides implementation of the operations required for 
	 * internal (non-leaf) nodes.
	 * 
	 * @author sapan
	 * @author Xudong Tang (xtang75@wisc.edu)
	 * @author Yixian Gan (ygan23@wisc.edu)
	 * @author Yiye Dang (dang6@wisc.edu)
	 * @author Daoxing Zhang (dzhang268@wisc.edu)
	 * @author Qiuhong Li (qli288@wisc.edu)
	 */
	private class InternalNode extends Node {

		// List of children nodes
		List<Node> children;

		/**
		 * Package constructor
		 */
		InternalNode() {
			super();
			children = new ArrayList<>();
		}

		/**
		 * Gets the first leaf key of the tree
		 * 
		 * @see BPTree.Node#getFirstLeafKey()
		 * @return the first leaf key of the tree
		 */
		K getFirstLeafKey() {
			if (children.isEmpty()) {
				return null;
			}
			return children.get(0).getFirstLeafKey();
		}

		/**
		 * Determines whether the size of the Nodes list is larger than
		 * the branch factor
		 * 
		 * @see BPTree.Node#isOverflow()
		 * @return true if the size of the Nodes is larger than 
		 * 			the branch factor; otherwise, false
		 */
		boolean isOverflow() {
			return this.keys.size() >= branchingFactor;
		}

		/**
		 * Inserts key and value in the appropriate leaf Nodes and 
		 * internal Nodes and balances the tree if required by splitting
		 * 
		 * @see BPTree.Node#insert(java.lang.Comparable, java.lang.Object)
		 * @param key   the specific key
		 * @param value the specific value
		 */
		void insert(K key, V value) {
			int index = 0; // the index of right key of this key
			Node rightKid = null; // the right key of this key
			// find the appropriate index to insert the key
			try {
				while (key.compareTo(keys.get(index)) >= 0) {
					index++;
				}
				rightKid = this.children.get(index);
			} catch (IndexOutOfBoundsException e) {
				rightKid = this.children.get(this.children.size() - 1);
				index = this.children.size() - 1;
			}
			// insert to the next level Nodes
			rightKid.insert(key, value);

			// split the next level Nodes when it is full and 
			//create a new level of Nodes of the same type
			// (leaf or internal)
			if (rightKid.isOverflow()) {
				Node newKid = rightKid.split();
				try {
					keys.add(index, newKid.getFirstLeafKey());
					children.add(index + 1, newKid);
				} catch (IndexOutOfBoundsException e) {
					keys.add(newKid.getFirstLeafKey());
					children.add(newKid);
				}
			}
		}

		/**
		 * Gets the new sibling created after splitting the node
		 * 
		 * @see BPTree.Node#split()
		 * @return the new sibling created after splitting the node
		 */
		Node split() {
			// the middle index of the current internal Nodes
			int splitIndex = (int) Math.ceil(branchingFactor / 2);
			// the new internal Nodes after splitting
			InternalNode sibling = new InternalNode();
			// spilt the original internal Nodes into two Nodes
			while (this.children.size() > splitIndex) {
				sibling.children.add(this.children.get(splitIndex));
				this.keys.remove(splitIndex - 1);
				this.children.remove(splitIndex);
			}
			for (int i = 1; i < sibling.children.size(); i++) {
				sibling.keys.add(sibling.children.get(i).getFirstLeafKey());
			}
			return sibling;
		}

		/**
		 * Gets the values that satisfy the given range search arguments.
		 * 
		 * If key is null or not found, return empty list. If comparator 
		 * is null, empty, or not according to required form, 
		 * return empty list.
		 * 
		 * @see BPTreeADT#rangeSearch(java.lang.Object, java.lang.String)
		 * @param key        to be searched
		 * @param comparator is a string
		 * @return list of values that are the result of the range search; 
		 * 			if nothing found, return empty list
		 */
		List<V> rangeSearch(K key, String comparator) {
			int index = 0; // the index of right key of this key
			Node rightKid = null; // the right key of this key

			try {
				while (key.compareTo(keys.get(index)) >= 0) {
					index++;
				}
				rightKid = this.children.get(index);
			} catch (IndexOutOfBoundsException e) {
				rightKid = this.children.get(this.children.size() - 1);
			}
			// pass down to the next level
			return rightKid.rangeSearch(key, comparator);
		}

	} // End of class InternalNode

	/**
	 * This class represents a leaf node of the tree. 
	 * This class is a concrete sub class of the abstract 
	 * Node class and provides implementation of the
	 * operations that required for leaf nodes.
	 * 
	 * @author sapan
	 * @author Xudong Tang (xtang75@wisc.edu)
	 * @author Yixian Gan (ygan23@wisc.edu)
	 * @author Yiye Dang (dang6@wisc.edu)
	 * @author Daoxing Zhang (dzhang268@wisc.edu)
	 * @author Qiuhong Li (qli288@wisc.edu)
	 */
	private class LeafNode extends Node {

		// List of values
		List<V> values;

		// Reference to the next leaf node
		LeafNode next;

		// Reference to the previous leaf node
		LeafNode previous;

		/**
		 * Package constructor
		 */
		LeafNode() {
			super();
			this.values = new ArrayList<V>();
			this.next = null;
			this.previous = null;
		}

		/**
		 * Gets the first leaf key of the tree
		 * 
		 * @see BPTree.Node#getFirstLeafKey()
		 * @return the first leaf key of the tree
		 */
		K getFirstLeafKey() {
			return this.keys.get(0);
		}

		/**
		 * Determines whether the size of the Nodes is larger than 
		 * the branch factor
		 * 
		 * @see BPTree.Node#isOverflow()
		 * @return true if the size of the Nodes is larger than 
		 * 			the branch factor; otherwise, false
		 */
		boolean isOverflow() {
			return this.keys.size() >= branchingFactor;
		}

		/**
		 * Inserts key and value in the appropriate leaf node and 
		 * balances the tree if required by splitting
		 * 
		 * @see BPTree.Node#insert(java.lang.Comparable, java.lang.Object)
		 * @param key   the specific key
		 * @param value the specific value
		 */
		void insert(K key, V value) {
			// add to this leaf node if it is empty
			if (this.keys.isEmpty()) {
				this.keys.add(key);
				this.values.add(value);
			}
			// add to this leaf node if it is not empty
			else {
				for (int i = 0; i < keys.size(); i++) {
					if (key.compareTo(keys.get(i)) <= 0) {
						this.keys.add(i, key);
						this.values.add(i, value);
						return;
					}
				}
				keys.add(key);
				values.add(value);
			}
		}

		/**
		 * Gets the new sibling created after splitting the node
		 * 
		 * @see BPTree.Node#split()
		 * @return the new sibling created after splitting the node
		 */
		Node split() {
			// the middle index of the leaf Nodes
			int splitIndex = (int) Math.ceil(branchingFactor / 2);
			// the new leaf Nodes after splitting
			LeafNode sibling = new LeafNode();
			// spilt the original leaf Nodes into two leaf Nodes
			while (this.keys.size() > splitIndex) {
				sibling.keys.add(this.keys.get(splitIndex));
				sibling.values.add(values.get(splitIndex));
				this.keys.remove(splitIndex);
				this.values.remove(splitIndex);
			}
			// set the double linked relation
			sibling.previous = this;
			sibling.next = this.next;
			if (this.next != null) {
				this.next.previous = sibling;
			}
			this.next = sibling;
			return sibling;
		}

		/**
		 * Gets the values that satisfy the given range search arguments.
		 * 
		 * If key is null or not found, return empty list. If comparator 
		 * is null, empty, or not according to required form, return empty 
		 * list.
		 * 
		 * @see BPTreeADT#rangeSearch(java.lang.Object, java.lang.String)
		 * @param key        to be searched
		 * @param comparator is a string
		 * @return list of values that are the result of the range search; 
		 * 			if nothing  found, return empty list
		 */
		List<V> rangeSearch(K key, String comparator) {
			// If key is null or comparator is null, return empty list.
			if (comparator == null || comparator.isEmpty()) {
				return new ArrayList<V>();
			}
			// list of values that are the result of the range search
			List<V> qualified = new ArrayList<>();

			// comparator is "=="
			if (comparator.equals("==")) {
				int index = 0; // the index of each key
				try {
					// search current leaf node
					while (!key.equals(this.keys.get(index))) {
						index++;
					}
					for (int i = index; i < keys.size(); i++) {
						if (!key.equals(keys.get(i))) {
							break;
						}
						qualified.add(values.get(i));
					}

					// the next leaf Nodes of current leaf node
					LeafNode curNode = this.next; 
					// boolean value to determine the end of the BP Tree
					boolean done = false;
					// search the next leaf Nodes
					while (curNode != null && !done) {
						for (int i = 0; i < curNode.keys.size(); i++) {
							if (!curNode.keys.get(i).equals(key)) {
								done = true;
								break;
							}
							qualified.add(curNode.values.get(i));
						}
						curNode = curNode.next;
					}

					// the previous leaf Nodes of current leaf node
					curNode = this.previous;
					done = false;
					// search the previous leaf Nodes
					while (curNode != null && !done) {
						for (int i = curNode.keys.size() - 1; i > -1; i--) {
							if (!curNode.keys.get(i).equals(key)) {
								done = true;
								break;
							}
							qualified.add(curNode.values.get(i));
						}
						curNode = curNode.previous;
					}
				} catch (IndexOutOfBoundsException e) {
					return qualified;
				}
			}

			// case "<="
			else if (comparator.equals("<=")) {
				LeafNode curNode = this.next; 
				boolean done = false;
				while (curNode != null && !done) {
					for (int i = 0; i < curNode.keys.size(); i++) {
						if (key.compareTo(curNode.keys.get(i)) < 0) {
							done = true;
							break;
						}
						qualified.add(curNode.values.get(i));
					}
					curNode = curNode.next;
				}

				// the index of each key of current leaf node
				int index = this.keys.size() - 1; 
				// search current leaf node
				try {
					while (key.compareTo(this.keys.get(index)) < 0) {
						index--;
					}
					for (int i = index; i >= 0; i--) {
						qualified.add(values.get(i));
					}

					// add all the keys of previous leaf Nodes to the qualified
					curNode = this.previous;
					while (curNode != null) {
						for (int i = curNode.keys.size() - 1; i > -1; i--) {
							qualified.add(curNode.values.get(i));
						}
						curNode = curNode.previous;
					}
				} catch (IndexOutOfBoundsException e) {
					return qualified;
				}
			}

			// case ">="
			else if (comparator.equals(">=")) {
				LeafNode curNode = this.previous;
				boolean done = false;

				// search the previous leaf Nodes
				while (curNode != null && !done) {
					for (int i = curNode.keys.size() - 1; i > -1; i--) {
						if (key.compareTo(curNode.keys.get(i)) > 0) {
							done = true;
							break;
						}
						qualified.add(curNode.values.get(i));
					}
					curNode = curNode.previous;
				}

				int index = 0; // the index of each key of current leaf node
				try {
					while (key.compareTo(this.keys.get(index)) > 0) {
						index++;
					}
					for (int i = index; i < keys.size(); i++) {
						qualified.add(values.get(i));
					}

					// add all the keys of next leaf Nodes to the qualified
					curNode = this.next;
					while (curNode != null) {
						for (int i = 0; i < curNode.keys.size(); i++) {
							qualified.add(curNode.values.get(i));
						}
						curNode = curNode.next;
					}
				} catch (IndexOutOfBoundsException e) {
					System.out.println("WARNING!!!!!!!!");
					return qualified;
				}
			}
			return qualified;
		}

	} // End of class LeafNode

} // End of class BPTree
