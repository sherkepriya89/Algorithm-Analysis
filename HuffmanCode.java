/******************************************************************************
*Program Features: This program reads a text file and prints the frequency table and build the huffman code table and encodes the message*
*******************************************************************************/

package programmingAssignments;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.PriorityQueue;
import java.io.IOException;
import java.util.Comparator;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class HuffmanCode {
	private String arg, encodedStr;
	public HashMap<Character, Integer> hmapWC; // for occurrence count
	public HashMap<Character, String> hmapCode; // for code(character/code)
	private PriorityQueue<node> pq; // for MinHeap
	private int counter; // Unique id assigned to each node
	private int treeSize; // No of total nodes in the tree
	private node root;
	
	//Getter and Setters for counter and treeSize.
	public int getCounter() {
		return counter;
	}
	public void setCounter(int counter) {
		this.counter = counter;
	}
	public int getTreeSize() {
		return treeSize;
	}
	public void setTreeSize(int treeSize) {
		this.treeSize = treeSize;
	}


	// Inner class
	private class node {
		int weight;
		char ch;
		node left, right;

		// Constructor for class node
		private node(Character ch, Integer weight, node left, node right) {
			this.weight = weight;
			this.ch = ch;
			this.left = left;
			this.right = right;
		}
	}
	
	private boolean isLeaf(node n) {
		return (n.left == null) && (n.right == null);
	}

	// Constructor
	public ProgrammingAssignment4PartIII(String arg, boolean show) {
		this.setCounter(0);
		this.arg = arg;
		hmapWC = new HashMap<Character, Integer>();
		hmapCode = new HashMap<Character, String>();
		pq = new PriorityQueue<node>(1, new Comparator<node>() {
			@Override
			public int compare(node n1, node n2) {
				if (n1.weight < n2.weight)
					return -1;
				else if (n1.weight > n2.weight)
					return 1;
				return 0;
			}
		});
		char c = 'a';
		while (c <= 'z') {
			hmapWC.put(c, 0);
			c++;
		}
		getFrequency(); // Call the getFrequency Function
		buildTree(); // Call the buildTree Function
		buildCodeTable(); // Call the buildCodeTable
	}

	// Function to count the frequency.
	private void getFrequency() {
		Character ch;
		Integer weight;
		for (int i = 0; i < arg.length(); i++) {
			ch = new Character(arg.charAt(i));
			if (hmapWC.containsKey(ch) == false)
				weight = new Integer(1);
			else
				weight = hmapWC.get(ch) + 1;
			hmapWC.put(ch, weight);
		}
	}

	// Function to build the tree.
	private void buildTree() {
		for (Map.Entry<Character, Integer> entry : hmapWC.entrySet()) {
			Character ch = entry.getKey();
			Integer weight = entry.getValue();
			node n = new node(ch, weight, null, null);
			pq.offer(n);
		}
		node left, right;
		while (!pq.isEmpty()) {
			left = pq.poll();
			setTreeSize(getTreeSize() + 1);
			if (pq.peek() != null) {
				right = pq.poll();
				setTreeSize(getTreeSize() + 1);
				root = new node('\0', left.weight + right.weight, left, right);
			} else { // only left child. right=null
				root = new node('\0', left.weight, left, null);
			}

			if (pq.peek() != null) {
				pq.offer(root);
			} else { // = Top root. Finished building the tree.
				setTreeSize(getTreeSize() + 1);
				break;
			}
		}
	}

	// Function to build code table.
	private void buildCodeTable() {
		String code = "";
		node n = root;
		buildCodeRecursion(n, code); // Recursion
	}

	private void buildCodeRecursion(node n, String code) {
		if (n != null) {
			if (!isLeaf(n)) { // n = internal node
				buildCodeRecursion(n.left, code + '0');
				buildCodeRecursion(n.right, code + '1');
			} else { // n = Leaf node
				hmapCode.put(n.ch, code); // for {character:code}
			}
		}
	}
	
	//Function to encode
	public String encode(){
		StringBuilder sb = new StringBuilder();
		Character ch;
		for(int i=0; i<arg.length(); i++){
			ch = arg.charAt(i);
			sb.append(hmapCode.get(ch));
		}
		encodedStr = sb.toString();
		return encodedStr;
	}
	

	// Function to read the file.
	public static String readFile(String fname) {
		StringBuilder sb = new StringBuilder();
		File filename = new File(fname);
		try (BufferedReader in = new BufferedReader(new FileReader(filename))) {
			String line = in.readLine();
			while (line != null) {
				sb.append(line + "\n");
				line = in.readLine();
			}
		} catch (IOException e) {
			System.out.println(e);
		}
		return sb.toString();
	}
	

	public static void main(String[] args) {
		System.out.println("----- Test START -----");
		boolean show = true;

		// Read the File
		String orgFile = "/Users/psherke/Downloads/test/filetoencode.txt"; // Enter the file path here
		String orgString = readFile(orgFile);

		// Call the constructor
		ProgrammingAssignment4PartIII h = new ProgrammingAssignment4PartIII(orgString, show);

		// Print the Frequency Table
		if (show) {
			System.out.println("\n============= Frequency Table =============");
			for (Entry<Character, Integer> entry : h.hmapWC.entrySet()) {
				String key = entry.getKey().toString();
				int val = entry.getValue();
				if (key.equals("\n"))
					key = "!";
				if (key.equals(" "))
					key = "-";
				if (key.equals("."))
					key = ".";
				System.out.println(key + " " + val);
			}
			
			// Print the Code Table
			System.out.print("\n============= Code Table =============\n");
			for (Entry<Character, String> entry : h.hmapCode.entrySet()) {
				String key = entry.getKey().toString();
				String val = entry.getValue();
				if (key.equals("\n"))
					key = "!";
				if (key.equals(" "))
					key = "-";
				if (key.equals("."))
					key = ".";
				System.out.println(key + ": " + val);
			}
		}
		

		System.out.print("\n============= Message =============\n");
		System.out.println(orgString);
		System.out.print("\n============= Encoding the Message =============\n");
	    String e = h.encode();
	    System.out.println(e);
	    
		System.out.println("\n----- Test DONE -----\n");
	}

}
