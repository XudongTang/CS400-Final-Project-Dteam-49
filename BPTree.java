package application;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

/**
 * Implementation of a B+ tree to allow efficient access to
 * many different indexes of a large data set. 
 * BPTree objects are created for each type of index
 * needed by the program.  BPTrees provide an efficient
 * range search as compared to other types of data structures
 * due to the ability to perform log_m N lookups and
 * linear in-order traversals of the data items.
 * 
 * @author sapan (sapan@cs.wisc.edu)
 *
 * @param <K> key - expect a string that is the type of id for each item
 * @param <V> value - expect a user-defined type that stores all data for a food item
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
     * @param branchingFactor 
     */
    public BPTree(int branchingFactor) {
        if (branchingFactor <= 2) {
            throw new IllegalArgumentException(
               "Illegal branching factor: " + branchingFactor);
        }
        this.branchingFactor = branchingFactor;
        this.root = new LeafNode();
    }
    
    
    /*
     * (non-Javadoc)
     * @see BPTreeADT#insert(java.lang.Object, java.lang.Object)
     */
    @Override
    public void insert(K key, V value) {
        this.root.insert(key, value);
        
        if (this.root.isOverflow()) {
        	InternalNode newRoot = new InternalNode();
        	newRoot.children.add(root);
        	newRoot.children.add(root.split());
        	newRoot.keys.add(newRoot.children.get(1).getFirstLeafKey());
        	this.root = newRoot;
        }
    }
    
    
    /*
     * (non-Javadoc)
     * @see BPTreeADT#rangeSearch(java.lang.Object, java.lang.String)
     */
    @Override
    public List<V> rangeSearch(K key, String comparator) {
        if (!comparator.contentEquals(">=") && 
            !comparator.contentEquals("==") && 
            !comparator.contentEquals("<=") )
            return new ArrayList<V>();
       return root.rangeSearch(key, comparator);
    }
    
    
    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
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
    
    public void testPrint() {
//    	Node curNode = root;
//    	while(!curNode.children.isEmpty()) {
//    		curNode = curNode.children.get(0);
//    	}
//    	LeafNode a = (LeafNode)curNode;
//    	while(curNode != null) {
//    		for (int i = 0; i < a.keys.size(); i++) {
//    			System.out.print(a.values.get(i));
//    		}
//    		a = a.next;
//    	}
    	
    	LeafNode a = (LeafNode) root;
    	for (int i = 0; i < a.values.size();i++) {
    		System.out.println(a.values.get(i));
    	}
    	a=a.next;
    	System.out.println("\\\\\\\\\\\\");
    	for (int i = 0; i < a.values.size();i++) {
    		System.out.println(a.values.get(i));
    	}
    		
    }
    
    /**
     * This abstract class represents any type of node in the tree
     * This class is a super class of the LeafNode and InternalNode types.
     * 
     * @author sapan
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
         * Inserts key and value in the appropriate leaf node 
         * and balances the tree if required by splitting
         *  
         * @param key
         * @param value
         */
        abstract void insert(K key, V value);

        /**
         * Gets the first leaf key of the tree
         * 
         * @return key
         */
        abstract K getFirstLeafKey();
        
        /**
         * Gets the new sibling created after splitting the node
         * 
         * @return Node
         */
        abstract Node split();
        
        /*
         * (non-Javadoc)
         * @see BPTree#rangeSearch(java.lang.Object, java.lang.String)
         */
        abstract List<V> rangeSearch(K key, String comparator);

        /**
         * 
         * @return boolean
         */
        abstract boolean isOverflow();
        
        public String toString() {
            return keys.toString();
        }
    
    } // End of abstract class Node
    
    /**
     * This class represents an internal node of the tree.
     * This class is a concrete sub class of the abstract Node class
     * and provides implementation of the operations
     * required for internal (non-leaf) nodes.
     * 
     * @author sapan
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
         * (non-Javadoc)
         * @see BPTree.Node#getFirstLeafKey()
         */
        K getFirstLeafKey() {
            if (children.isEmpty()) {
            	return null;
            }
            return children.get(0).getFirstLeafKey();
        }
        
        /**
         * (non-Javadoc)
         * @see BPTree.Node#isOverflow()
         */
        boolean isOverflow() {
            return this.keys.size() >= branchingFactor;
        }
        
        /**
         * (non-Javadoc)
         * @see BPTree.Node#insert(java.lang.Comparable, java.lang.Object)
         */
        void insert(K key, V value) {
            int index = 0;
            Node rightKid = null;
            try {
            	while(key.compareTo(keys.get(index)) >= 0) {
            		index++;
            	}
            	rightKid = this.children.get(index);
            } catch(IndexOutOfBoundsException e) {
            	rightKid = this.children.get(this.children.size() - 1);
            	index = this.children.size() - 1;
            }
            rightKid.insert(key, value);
            
            if(rightKid.isOverflow()) {
            	Node newKid = rightKid.split();
            	try {
            		keys.add(index, newKid.getFirstLeafKey());
            		children.add(index + 1, newKid);
            	} catch(IndexOutOfBoundsException e) {
            		keys.add(newKid.getFirstLeafKey());
            		children.add(newKid);
            	}
            }
        }
        
        /**
         * (non-Javadoc)
         * @see BPTree.Node#split()
         */
        Node split() {
        	int splitIndex = (int) Math.ceil(branchingFactor / 2);
			InternalNode sibling = new InternalNode();
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
         * (non-Javadoc)
         * @see BPTree.Node#rangeSearch(java.lang.Comparable, java.lang.String)
         */
        List<V> rangeSearch(K key, String comparator) {
        	 int index = 0;
             Node rightKid = null;
             
             try {
             	while(key.compareTo(keys.get(index)) >= 0) {
             		index++;
             	}
             	rightKid = this.children.get(index);
             } catch(IndexOutOfBoundsException e) {
             	rightKid = this.children.get(this.children.size() - 1);
             }
             return rightKid.rangeSearch(key, comparator);
        }
    
    } // End of class InternalNode
    
    
    /**
     * This class represents a leaf node of the tree.
     * This class is a concrete sub class of the abstract Node class
     * and provides implementation of the operations that
     * required for leaf nodes.
     * 
     * @author sapan
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
         * (non-Javadoc)
         * @see BPTree.Node#getFirstLeafKey()
         */
        K getFirstLeafKey() {
            return this.keys.get(0);
        }
        
        /**
         * (non-Javadoc)
         * @see BPTree.Node#isOverflow()
         */
        boolean isOverflow() {
            return this.keys.size() >= branchingFactor;
        }
        
        /**
         * (non-Javadoc)
         * @see BPTree.Node#insert(Comparable, Object)
         */
        void insert(K key, V value) {
            if (this.keys.isEmpty()) {
            	this.keys.add(key);
            	this.values.add(value);
            } else {
            	for (int i = 0; i < keys.size(); i++) {
            		if(key.compareTo(keys.get(i)) <= 0) {
            			this.keys.add(i, key);
            			this.values.add(i,value);
            			return;
            		}
            	}
            	keys.add(key);
    			values.add(value);
            }
        }
            
        
        /**
         * (non-Javadoc)
         * @see BPTree.Node#split()
         */
        Node split() {
			int splitIndex = (int) Math.ceil(branchingFactor / 2);
			LeafNode sibling = new LeafNode();
			while (this.keys.size() > splitIndex) {
				sibling.keys.add(this.keys.get(splitIndex));
				sibling.values.add(values.get(splitIndex));
				this.keys.remove(splitIndex);
				this.values.remove(splitIndex);
			}
			sibling.previous = this;
			sibling.next = this.next;
			this.next = sibling;
			return sibling;
        }
        
        /**
         * (non-Javadoc)
         * @see BPTree.Node#rangeSearch(Comparable, String)
         */
        List<V> rangeSearch(K key, String comparator) {
            if (comparator == null || comparator.isEmpty()) {
            	return new ArrayList<V>();
            }
            ArrayList<V> qualified = new ArrayList<>();
            
            //comparator is "=="
            if(comparator.equals("==")) {
            	int index = 0; 
            	try {
            		while (!key.equals(this.keys.get(index))) {
            			index++;
            		}	
            		for (int i = index; i < keys.size();i++) {
            			if(!key.equals(keys.get(i))) {
            				break;
            			}
            			qualified.add(values.get(i));
            		}
            		
            		LeafNode curNode = this.next;
            		boolean done = false;
            		while(curNode != null && !done) {
            			for (int i = 0; i < curNode.keys.size(); i++) {
            				if (!curNode.keys.get(i).equals(key)) {
            					done = true; 
            					break;
            				}
            				qualified.add(curNode.values.get(i));
            			}
            			curNode = curNode.next;
            		}
            		
            		curNode = this.previous;
            		done = false;
            		while(curNode != null && !done) {
            			for (int i = curNode.keys.size() - 1; i > -1; i--) {
            				if (!curNode.keys.get(i).equals(key)) {
            					done = true; 
            					break;
            				}
            				qualified.add(curNode.values.get(i));
            			}
            			curNode = curNode.previous;
            		}
            	}catch (IndexOutOfBoundsException e) {
            		return qualified;
            	}
            } 
            
            // case "<="
            else if (comparator.equals("<=")){
            	LeafNode curNode = this.next;
            	boolean done = false;
            	while(curNode != null && !done) {
            		for(int i = 0; i < curNode.keys.size(); i++) {
            			if(!key.equals(curNode.keys.get(i))) {
            				done = true;
            				break;
            			}
            			qualified.add(curNode.values.get(i));
            		}
            		curNode = curNode.next;
            	}
            	
            	
            	int index = keys.size() - 1; 
            	try {
            		while (key.compareTo(this.keys.get(index)) < 0) {
            			index--;
            		}	
            		for (int i = index; i >= 0 ;i--) {
            			qualified.add(values.get(index));
            		}
            		
            		curNode = this.previous;
            		while(curNode != null) {
            			for (int i = curNode.keys.size(); i > -1 ; i--) {
            				qualified.add(curNode.values.get(i));
            			}
            			curNode = curNode.previous;
            		}
            	}catch (IndexOutOfBoundsException e) {
            		return qualified;
            	}
            } 
            
            //case ">="
            else if (comparator.equals(">=")) {
            	LeafNode curNode = this.previous;
            	boolean done = false;
            	while(curNode != null && !done) {
            		for(int i = curNode.keys.size() - 1; i > -1; i--) {
            			if(!key.equals(curNode.keys.get(i))) {
            				done = true;
            				break;
            			}
            			qualified.add(curNode.values.get(i));
            		}
            		curNode = curNode.previous;
            	}
            	
            	
            	int index = 0; 
            	try {
            		while (key.compareTo(this.keys.get(index)) > 0) {
            			index++;
            		}	
            		for (int i = index; i < keys.size() ;i++) {
            			qualified.add(values.get(index));
            		}
            		
            		curNode = this.next;
            		while(curNode != null) {
            			for (int i = 0; i < curNode.keys.size(); i++) {
            				qualified.add(curNode.values.get(i));
            			}
            			curNode = curNode.next;
            		}
            	}catch (IndexOutOfBoundsException e) {
            		return qualified;
            	}
            }
            return qualified;
        }
        
        
        
    } // End of class LeafNode
    
    
    /**
     * Contains a basic test scenario for a BPTree instance.
     * It shows a simple example of the use of this class
     * and its related types.
     * 
     * @param args
     */
    public static void main(String[] args) {
        // create empty BPTree with branching factor of 3
        BPTree<Double, Double> bpTree = new BPTree<>(4);

        // create a pseudo random number generator
        Random rnd1 = new Random();

        // some value to add to the BPTree
        Double[] dd = {0.0d, 0.5d, 0.2d, 0.8d};

        // build an ArrayList of those value and add to BPTree also
        // allows for comparing the contents of the ArrayList 
        // against the contents and functionality of the BPTree
        // does not ensure BPTree is implemented correctly
        // just that it functions as a data structure with
        // insert, rangeSearch, and toString() working.
        List<Double> list = new ArrayList<>();
        int count = 0;
        for (int i = 0; i < 400; i++) {
            Double j = dd[rnd1.nextInt(4)];
            list.add(j);
            bpTree.insert(j,j);
            if(j.compareTo(0.5) == 0) {count++;}
        }
        System.out.println("\n\nTree structure:\n" + bpTree.toString());

        List<Double> filteredValues = bpTree.rangeSearch(0.5d, "==");
        System.out.println(count);
        System.out.println(filteredValues.size() + "Filtered values: " + filteredValues.toString());
    }

} // End of class BPTree
