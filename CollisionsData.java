package project5;
/* *
 * This class is an AVL tree storing all collision records
 * @author aubreyzhou
 */
public class CollisionsData {
	protected Node root;
	protected int size;
	protected boolean found;
	protected int[]data;
	
	public CollisionsData() {
		this.root=null;
		size=0;
	}
	/**
	 * Add the given data item to the tree and balance the tree. If item is null, 
	 * the tree does not change. If item already exists, the tree does not change. 
	 * Implementation is based on BST-Recursive
	 * @param item the new element to be added to the tree
	 */
	public void add(Collision item) {
		if (item == null)
			return;
		root = add (root, item);
	}
	
	/*
	 * Actual recursive implementation of add.  
	 * Implementation is based on BST-Recursive
	 * @param item the new element to be added to the tree
	 */
	private Node add(Node node, Collision item) {
		//if root is null, assign item to root
		if (node == null) { 
			size++;
			return new Node(item);
		}
		if (node.data.compareTo(item) > 0) {
			node.left = add(node.left, item);
		}
			
		else if (node.data.compareTo(item) < 0) {
			node.right = add(node.right, item);
		}
		updateHeight(node);
		return balance(node);	
		 
	}
	/*
	 * Balance the tree based on given node's balance factor
	 * @param n node to start balancing
	 */
	private Node balance(Node n) {
		if(n==null)return null;
		//left subtree is too big
		if(balanceFactor(n)<-1) {
			//left subtree of given node's left subtree is too big
			//perform LL rotation;otherwise LR rotation
			if(balanceFactor(n.left)<=0)return balanceLL(n);
			else return balanceLR(n);
		}
		//right subtree is too big
		else if(balanceFactor(n)>1) {
			//right subtree of given node's right subtree is too big
			//perform RR rotation;otherwise RL rotation
			if(balanceFactor(n.right)>=0) return balanceRR(n);
			else return balanceRL(n);
		}
		return n;
	}
	/*
	 * Calculate the balance factor of the given node, which is the different between 
	 * height of its left subtree and height of its right subtree
	 * @param n node to calculate balance factor
	 */
	private int balanceFactor (Node n) {
		if ( n.right == null )return -n.height;
		else if ( n.left == null )return n.height;
		return n.right.height - n.left.height;
	}
	/*
	 * update the height of given node
	 * @param n node to update height
	 */
	private void updateHeight (Node n) {
		if(n==null)return;
		if(n.left==null&&n.right==null) n.height=0;
		else if(n.left==null) n.height = n.right.height+1;
		else if(n.right==null) n.height = n.left.height+1;
		else n.height=Math.max(n.right.height,n.left.height)+1;
	}
	/*
	 * rotate the subtree to the right. 
	 * the right subtree of left children of a becomes the left subtree of a.
	 * @param a node to perform balance on
	 * @return reference to the new root of the subtree after rotation
	 */
	private Node balanceLL(Node a) {
		Node b=a.left;
		a.left=b.right;
		b.right=a;
		//update all nodes' height
		updateHeight(a);
		updateHeight(b);
		return b;
	}
	/*
	 * rotate the subtree to the left
	 * the left subtree of right children of a becomes the right subtree of a
	 * @param a node to perform balance on
	 * @return reference to the new root of the subtree after rotation
	 */
	private Node balanceRR(Node a) {
		Node b=a.right;
		a.right=b.left;
		b.left=a;
		//update all nodes' height
		updateHeight(a);
		updateHeight(b);
		return b;
	}
	/*
	 * rotate to the left at left children of a and then rotate to the right at node a.
	 * right child of left child of a becomes the root
	 * a's left child and a are new root's left and right child
	 * @param a node to perform balance on
	 * @return reference to the new root of the subtree after rotation
	 */
	private Node balanceLR(Node a) {
		Node b=a.left;
		Node c=b.right;
		a.left=c.right;
		b.right=c.left;
		c.left=b;
		c.right=a;
		//update all nodes' height
		updateHeight(a);
		updateHeight(b);
		updateHeight(c);
		return c;
	}
	/*
	 * rotate to the right at right child of a and then rotate to the left at node a.
	 * left child of right child of a becomes the root 
	 * a's right child and a are new root's left and right child
	 * @param a node to perform balance on
	 * @return reference to the new root of the subtree after rotation
	 */
	private Node balanceRL(Node a) {
		Node b=a.right;
		Node c=b.left;
		a.right=c.left;
		b.left=c.right;
		c.left=a;
		c.right=b;
		//update all nodes' height
		updateHeight(a);
		updateHeight(b);
		updateHeight(c);
		return c;
	}
	/**
	 * Remove the item from the tree. If item is null the tree remains unchanged. If
	 * item is not found in the tree, the tree remains unchanged.
	 * 
	 * @param target the item to be removed from this tree 
	 */
	public boolean remove(Collision target)
	{
		root = recRemove(target, root);
		return found;
	}


	/*
	 * Actual recursive implementation of remove method: find the node to remove.  
	 * 
	 * @param target the item to be removed from this tree 
	 */
	private Node recRemove(Collision target, Node node)
	{
		if (node == null)
			found = false;
		//if current node is bigger
		else if (target.compareTo(node.data) < 0)
			//check its left child
			node.left = recRemove(target, node.left);
		//if current node is smaller
		else if (target.compareTo(node.data) > 0)
			//check its right child
			node.right = recRemove(target, node.right );
		else {
			//given node is the target, remove it
			node = removeNode(node);
			found = true;
		}
		return node;
	}
	
	/*
	 * Actual recursive implementation of remove method: perform the removal.  
	 * 
	 * @param target the item to be removed from this tree 
	 * @return a reference to the node itself, or to the modified subtree 
	 */
	private Node removeNode(Node node)
	{
		Collision data;
		//if given node only has one child, return the child's reference
		if (node.left == null)
			return node.right ;
		else if (node.right  == null)
			return node.left;
		//if given node has two children, find the rightmost node a in its left subtree
		//replace given node with a, then remove original node a
		else {
			data = getPredecessor(node.left);
			node.data = data;
			node.left = recRemove(data, node.left);
			//update height of given node and balance
			updateHeight(node);
			return balance(node);
		}
	}
	/*
	 * Returns the information held in the rightmost node of subtree  
	 * 
	 * @param subtree root of the subtree within which to search for the rightmost node 
	 * @return returns data stored in the rightmost node of subtree  
	 */
	private Collision getPredecessor(Node subtree)
	{
		if (subtree==null) throw new NullPointerException("getPredecessor called with an empty subtree");
		Node temp = subtree;
		while (temp.right != null)
			temp = temp.right ;
		return temp.data;
	}
	
	/**
	 * Produces tree like string representation of this BST.
	 * @return string containing tree-like representation of this BST.
	 */
	public String toStringTreeFormat() {
		StringBuilder s = new StringBuilder();

		preOrderPrint(root, 0, s);
		return s.toString();
	}

	/*
	 * Actual recursive implementation of preorder traversal to produce tree-like string
	 * representation of this tree.
	 * 
	 * @param tree the root of the current subtree
	 * @param level level (depth) of the current recursive call in the tree to
	 *   determine the indentation of each item
	 * @param output the string that accumulated the string representation of this
	 *   BST
	 */
	private void preOrderPrint(Node tree, int level, StringBuilder output) {
		if (tree != null) {
			String spaces = "\n";
			if (level > 0) {
				for (int i = 0; i < level - 1; i++)
					spaces += "   ";
				spaces += "|--";
			}
			output.append(spaces);
			output.append(tree.data);
			preOrderPrint(tree.left, level + 1, output);
			preOrderPrint(tree.right , level + 1, output);
		}
		// uncomment the part below to show "null children" in the output
		else {
			String spaces = "\n";
			if (level > 0) {
				for (int i = 0; i < level - 1; i++)
					spaces += "   ";
				spaces += "|--";
			}
			output.append(spaces);
			output.append("null");
		}
	}
	/**
	 * returns a string containing information about the numbers of fatalities and 
	 * injuries for a given zip code and date range.
	 * @param zip the zip code given
	 * @param dateBegin the start date given
	 * @param dateEnd the end date given
	 * @return
	 */
	public String getReport ( String zip, Date dateBegin, Date dateEnd) {
		data=new int[9];
		//start from root and traverse the tree to find nodes that match the given node with
		//zip code and date range
		matchDate(root,zip,dateBegin,dateEnd);
		int total=data[0];
		int iPersons=data[1];
		int iPedestrians=data[2];
		int iCyclists=data[3];
		int iMotorists=data[4];
		int kPersons=data[5];
		int kPedestrians=data[6];
		int kCyclists=data[7];
		int kMotorists=data[8];
		
		String report = "Total number of collisions:"+total+"\n"+"Number of injuries:"
		+iPersons+"\n"+"       pedestrians:"+iPedestrians+"\n"+"          cyclists:"+iCyclists+
		"\n"+"         motorists:"+iMotorists+"\n"+"Number of fatalities:"+kPersons+"\n"
		+"         pedestrians:"+kPedestrians+"\n"+"            cyclists:"
				+kCyclists+"\n"+"           motorists:"+kMotorists;
		return report;
	}
	
	/*
	 * Traverses the CollisionsData and update the data array according to Collision objects
	 * that match the given zip code and is in the date range
	 * 
	 * @param a node that starts traversing from
	 * @param zip the given zip code
	 * @param dateBegin the start date
	 * @param dateEnd the end date
	 */
	private void matchDate(Node a, String zip, Date dateBegin, Date dateEnd) {
		//when the node is null, the previous node is leaf
		if(a==null) return;
		if(a.data.getZip()==null)return;
		//zip code matches
		if(a.data.getZip().compareTo(zip)==0){
			//date is earlier than start date
			if(a.data.getDate().compareTo(dateBegin)<0) {
				//start traversing from the right children of this node, which has later start date
				matchDate(a.right,zip,dateBegin,dateEnd);
			}
			//date is later than start date and earlier than end date
			else if(a.data.getDate().compareTo(dateEnd)<=0){
				data[0]+=1;
				data[1]+=a.data.getPersonsInjured();
				data[2]+=a.data.getPedestriansInjured();
				data[3]+=a.data.getCyclistsInjured();
				data[4]+=a.data.getMotoristsInjured();
				data[5]+=a.data.getPersonsKilled();
				data[6]+=a.data.getPedestriansKilled();
				data[7]+=a.data.getCyclistsKilled();
				data[8]+=a.data.getMotoristsKilled();
				//continue traversing from both children of this node
				matchDate(a.left,zip,dateBegin,dateEnd);
				matchDate(a.right,zip,dateBegin,dateEnd);
			}
			//date is later than end date
			else
				//start traversing from the left children of this node, which has earlier end date
				matchDate(a.left,zip,dateBegin,dateEnd);
			
		}
		//zip code is smaller than expected
		else if(a.data.getZip().compareTo(zip)<0) {
			//start traversing from the right children of this node, which has bigger zip code
			matchDate(a.right,zip,dateBegin,dateEnd);
		}
		//zip code is bigger than expected
		else if(a.data.getZip().compareTo(zip)>0) {
			//start traversing from the left children of this node, which has smaller zip code
			matchDate(a.left,zip,dateBegin,dateEnd);
		}
	}

	
	protected static class Node implements Comparable<Node>{
		protected Node  left;  //reference to the left subtree 
		protected Node  right; //reference to the right subtree
		protected Collision data;            //data item stored in the node
		protected int height; 
	/**
	* Constructs a BSTNode initializing the data part 
	* according to the parameter and setting both 
	* references to subtrees to null.
	* @param data
	*    data to be stored in the node
	*/
		protected Node(Collision data) {
			this.data = data;
			left = null;
			right = null;
			height = 0; 
		}

		/* (non-Javadoc)
		* @see java.lang.Object#toString()
		*/
		@Override
		public String toString() {
		return data.toString();
		}

		public int compareTo(Node o) {
			return this.data.compareTo(o.data);
		}

	}
}
