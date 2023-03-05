

import java.util.NoSuchElementException;

public class BacktrackingBST implements Backtrack, ADTSet<BacktrackingBST.Node> {
    private Stack stack;
    private Stack redoStack;
    private BacktrackingBST.Node root = null;

    // Do not change the constructor's signature
    public BacktrackingBST(Stack stack, Stack redoStack) {
        this.stack = stack;
        this.redoStack = redoStack;
    }

    public Node getRoot() {
        if (root == null) {
            throw new NoSuchElementException("empty tree has no root");
        }
        return root;
    }

    public Node search(int k) {
        if (root == null)
            return null;

        return root.search(k);// search the node in Node Class
    }



    public Node getParent(Node node)
    {
        return root.getParent(node);
    }

    public void delete(Node node) {
        if (node == null | root == null)
            throw new RuntimeException("node is null");

        else  {
            Node parent = getParent(node); //save the node's parent before deleting it
            int key = node.getKey();
            root = root.delete(node);

//            stack.push(node);
            stack.push(key); //pushing the stack the key and the node's parent
            stack.push(parent);
            stack.push("delete");

            emptyRedoStack(); // //if operation was insert empty the redoStack

        }
    }
    public void redoDelete(Node node) { //this function deleting the desire node and doesnt updating the stack. we will use this function on the backtrack to send the desire node to the redoStack
        if (node == null | root == null)
            throw new RuntimeException("node is null");

        else  {
            Node parent = getParent(node); //save the node's parent before deleting it
            int key = node.getKey();
            root = root.delete(node);
        }
    }
    private void emptyRedoStack(){
        while (!redoStack.isEmpty())
            redoStack.pop();
    }

    public void insert(Node node) {

        if (root == null) { //if tree is empty' insert new root
            root = node;
            root.parent = null;
            stack.push(node);
            stack.push(null); //parent

        } else {
            root.insert(node); //node dealing with the correct inserting
            Node parent = getParent(node);
//            stack.push(node);
            stack.push(node.getKey());
            stack.push(parent);
        }
        stack.push("insert");
        emptyRedoStack(); // //if operation was insert empty the redoStack

    }


    public Node minimum() {
        return root.findMin();
    }

    public Node maximum() {
        return root.findMax();
    }

    public Node successor(Node node) {
        if (node.right != null) {
            return node.right.findMin(); // the successor will be the smallest node on the right side
        }

        Node successor = root.getParent(node);

        while (successor != null && node == successor.right) { //finding the lowest ancestor of node whose left child is also an ancestor of node
            node = successor;
            successor = successor.parent;
        }
        return successor;

    }

    public Node predecessor(Node node) {
        if (node.left != null) {
            return node.left.findMax();// the successor will be the largest node on the right side
        }

        Node predecessor = root.getParent(node);

        while (predecessor != null && node == predecessor.left) { //finding the biggest ancestor of node whose right child is also an ancestor of node
            node = predecessor;
            predecessor = predecessor.parent;
        }
        return predecessor;
    }

    @Override
//    public void backtrack() {
//
//        if (!stack.isEmpty()) {
//            String operator = (String) stack.pop();
//            if (operator.equals("delete")) { // if the operartion was delete we need to insert the current node to the same place as before
//                Node stackParent = (Node)stack.pop(); // node's parent
//                Node insertNode = (Node)stack.pop(); //node to insert
//                if(stackParent.getKey() > insertNode.getKey()){ // the node was on the left side
//                    if(stackParent.left == null) {  // the node was a leave before deleting
//
//                        stackParent.left = insertNode;
//                    }
//                    else {  //the node has a child - insert it to the left side
//
//                        Node tempLeft = stackParent.left;
//                        insertNode.left = null;
//                        insertNode.right = null;
//                        stackParent.left = insertNode;
//                        if(tempLeft.getKey() > insertNode.getKey()) {
//                            stackParent.left.right = tempLeft;
//                        }
//                        else{
//                            stackParent.left.left = tempLeft;
//                        }
//                    }
//                }
//                else {// the node was on the right side
//                    //there are 3 diffrentt situaton
//                    if(stackParent.right == null){// #1 the node was a leave
//                        stackParent.right = insertNode;
//                    }
//                    else{
//                        if(stackParent.left == null) { // #2 the node had 1 child
//
//                            Node tempRight = stackParent.right;
//                            stackParent.right = insertNode;
//                            stackParent.right.right = tempRight.right;
//
//
//                        }
//                        else{ //#3 the node had 2 child
//                            Node tempRight = stackParent.right;
//                            stackParent.right = insertNode;
//                            stackParent.right.right=tempRight.right;
//                            stackParent.right.left = tempRight.left;
//                            tempRight.right = null;
//                            tempRight.left = null;
//                            stackParent.right.insert(tempRight);
//
//                        }
//                    }
//                }
//            }
//        }
//    }
    public void backtrack() {

        if (!stack.isEmpty()) {
            String operator = (String) stack.pop();
            if (operator.equals("delete")) { // if the operartion was delete we need to insert the current node to the same place as before

                Node stackParent = (Node)stack.pop(); // node's parent
//                Node insertNode = (Node)stack.pop(); //node to insert
                int newKey = (int)stack.pop();// taking the node's key that we removed
//                redoStack.push(stackParent);
                redoStack.push(newKey);
                redoStack.push("delete");

                    if(stackParent.getKey() > newKey){ // the node we want to delete was on the left side

                    if(stackParent.left == null) {  // the node was a leave before deleting
                        stackParent.left = new Node(newKey,null);
                        stackParent.left.setParent(stackParent);
                    }
                    else {  //the node has a child - insert it to the left side
                        Node tempLeft = stackParent.left;
                        Node addedNode = new Node(newKey,null);
                        stackParent.left = addedNode;
                        addedNode.setParent(stackParent);

                        if(tempLeft.getKey() > newKey) {
                            addedNode.right = tempLeft;
                        }
                        else{
                            addedNode.left = tempLeft;
                        }
                    }
                }

                else {// the node was on the right side

                    //there are 3 different options
                    Node addedNode = new Node(newKey,null);
                    // #1 the node was a leave
                    if(stackParent.right == null){
                        stackParent.right = addedNode;
                        addedNode.setParent(stackParent);

                    }
                    else{
                        // #2 the node had 1 child
                        Node tempRight = stackParent.right;
                        if(stackParent.right.left == null) {
                            stackParent.right = addedNode;
                            addedNode.setParent(stackParent);

                            if(tempRight.getKey() > newKey) {
                                addedNode.right = tempRight;
                            }
                            else{
                                addedNode.left = tempRight;
                            }

                        }

                        else{
                            //#3 the node had 2 children
                            int changedKey = stackParent.right.getKey();
                            stackParent.right.setKey(newKey);
                            Node nodeToAdd = new Node(changedKey,null);
                            stackParent.right.insert(nodeToAdd);
                        }
                    }
                }
            }

            if (operator.equals("insert")){// if the operartion was insert we need to delete the current node

                Node stackParent = (Node)stack.pop(); // node's parent
                int newKey = (int)stack.pop();// taking the node's key that we add
                Node toDelete = new Node(newKey,null);
//                redoStack.push(stackParent);
                redoStack.push(newKey);
                redoStack.push("insert");
                redoDelete(toDelete);

            }
        }
    }
    public void insertAsRoot(int key){
        Node newRoot = new Node(key,null);
        newRoot.setParent(null);


            if(root.getKey() > key){ //inserting the root as a right child to the new root
                if(root.left == null){
                Node currRoot = new Node(root.getKey(),null);
                root = newRoot;
                root.right = currRoot;
                            }
                else {
                    if(root.right == null){
                        Node currRoot = new Node(root.getKey(),null);
                        root = newRoot;
                        root.right = currRoot;
                    }
                    else{
                        Node currRoot = new Node(root.getKey(),null);
                        root = newRoot;
                        root.right.insert(currRoot);
                    }
                }

        }
    }

    @Override
    public void retrack() {
        if(!redoStack.isEmpty()){
            String operation = (String) redoStack.pop();
            int key = (int) redoStack.pop();
//            Node stackParent = (Node) redoStack.pop();

            if(operation == "delete"){ //need to cancel the backtrack's insertion
                Node toDelete = new Node(key,null);
                delete(toDelete);
            }

            else{//need to cancel the backtrack's delete
                Node toAdd = new Node(key,null);
                insert(toAdd);
            }

        }
    }

    public void printPreOrder() {
        if (root == null)
            return;

        root.preorder(root);
    }

    @Override
    public void print() {
        printPreOrder();
    }

    public String toString() {
        if (root != null) {
            System.out.println("***************************");
            return root.toString2();
        } else
            return "Empty Tree";
    }

    public static class Node {
        // These fields are public for grading purposes. By coding conventions and best practice they should be private.
        public BacktrackingBST.Node left;
        public BacktrackingBST.Node right;

        private BacktrackingBST.Node parent;
        private int key;
        private Object value;

        public Node(int key, Object value) {
            this.key = key;
            this.value = value;
        }

        public Node search(int k) {
            if (k == key)
                return this;

            if (k < key) {
                if (left != null)
                    return left.search(k);
            } else {
                if (right != null)
                    return right.search(k);
            }


            return null;
        }

        public Node getParent(Node node) {

            if (node.getKey() == key)
                return this.parent;

            if (node.getKey() < key)
                return left.getParent(node);

            else if (node.getKey() > key)
                return right.getParent(node);

            return null;

        }
        public Node getRight(){
            return this.right;
        }

        public void setParent(Node parent){
            this.parent = parent;
        }
        public void insert(Node node) {

            if (node.getKey() < key) {
                if (left == null) {
//                    left = new BacktrackingBST.Node(node.getKey(), null);
                    left = node;
                    left.setParent(this);
//                    return this;

                } else

                    left.insert(node);
            }

           else if (node.getKey() > key) {

                if (right == null) {
//                    right = new BacktrackingBST.Node(node.getKey(), null);
                    right = node;
                    right.setParent(this);
//                    return this;
                }
                else

                    right.insert(node);
            }
//            return this;

        }
        public Node delete(Node node) {

            if (node.getKey() < key) {
                if (left != null) {
                    left = left.delete(node);
                }
            } else if (node.getKey() > key) {
                if (right != null) {
                    right = right.delete(node);
                }

            } else { //need to remove the data in this node
                if (left == null | right == null) { // 0 1 children
                    if (left == null) { // (base{
                        if (right != null)
                            right.parent = this.parent;
                        return right;
                    } else {
                        if (left != null)
                            left.parent = this.parent;
                        return left;
                    }
                } else { // this node has two children
                    Node toDelete = right.findMin();
                    this.key = toDelete.getKey();
                    right = right.delete(toDelete);
                }

            }
            return this;
        }


        private Node findMin() {
            Node currNode = this;
            while (currNode.left != null) {
                currNode = currNode.left;
            }

            return currNode;
        }

        private Node findMax() {
            Node currNode = this;
            while (currNode.right != null) {
                currNode = currNode.right;
            }

            return currNode;
        }

        public int getKey() {
            return key;
        }
        public void setKey(int key){
            this.key = key;
        }
        public Object getValue() {
            return value;
        }


        public static void preorder(Node node) {
            // return if the current node is empty
            if (node == null) {
                return;
            }
            // Display the data part of the root (or current node)
            System.out.print(node.key + " ");

            // Traverse the left subtree
            preorder(node.left);

            // Traverse the right subtree
            preorder(node.right);
        }

        public String toString() {
            return "" + this.getKey() + "";
        }

        public String toString2() {
            String d = "";
            return toString2(d);
        }

        private String toString2(String d) {
            String s = "";
            if (right != null)
                s = s + right.toString2(d + "  ");
            s = s + d + getKey() + "\n";
            if (left != null)
                s = s + left.toString2(d + "  ");
            return s;
        }
    }


    public static void main(String[] args) {
        System.out.println("check part 4 Q3:");
        Stack s1 = new Stack();
        Stack s2 = new Stack();
        BacktrackingBST tree = new BacktrackingBST(s1, s2);
//        tree.backtrack();
        BacktrackingBST.Node n120 = new BacktrackingBST.Node(120, null);
        BacktrackingBST.Node n100 = new BacktrackingBST.Node(100, null);
        BacktrackingBST.Node n13 = new BacktrackingBST.Node(13, null);
        BacktrackingBST.Node n56 = new BacktrackingBST.Node(56, null);
        BacktrackingBST.Node n87 = new BacktrackingBST.Node(87, null);
        BacktrackingBST.Node n230 = new BacktrackingBST.Node(230, null);
        BacktrackingBST.Node n40 = new BacktrackingBST.Node(40, null);
        BacktrackingBST.Node n22 = new BacktrackingBST.Node(22, null);
        BacktrackingBST.Node n80 = new BacktrackingBST.Node(80, null);

        BacktrackingBST.Node n240 = new BacktrackingBST.Node(240, null);

        tree.insert(n120);
        tree.insert(n100);
        tree.insert(n13);
        tree.insert(n56);
        tree.insert(n87);
        tree.insert(n230);
        tree.insert(n40);
        tree.insert(n22);
        tree.insert(n80);
        System.out.println("the tree at beggining");
        System.out.println(tree.toString());
        System.out.println("+++++++++++++++++++++++++++++++++");

//        tree.delete(n120);
//        System.out.println("delete 120");
//        System.out.println(tree.toString());
//        System.out.println("+++++++++++++++++++++++++++++++++");
//        tree.backtrack();
//        System.out.println("inserting 120");
//        System.out.println(tree.toString());
//        System.out.println("+++++++++++++++++++++++++++++++++");

        tree.backtrack();
        System.out.println("delete 80");
        System.out.println(tree.toString());
        System.out.println("+++++++++++++++++++++++++++++++++");
        tree.backtrack();
        System.out.println("delete 22");
        System.out.println(tree.toString());
        System.out.println("+++++++++++++++++++++++++++++++++");

        tree.backtrack();
        System.out.println("delete 40");
        System.out.println(tree.toString());
        System.out.println("+++++++++++++++++++++++++++++++++");

        tree.retrack();
        System.out.println("inerting 40");
        System.out.println(tree.toString());
        System.out.println("+++++++++++++++++++++++++++++++++");

        tree.delete(n40);
        System.out.println("deleting 40");
        System.out.println(tree.toString());
        System.out.println("+++++++++++++++++++++++++++++++++");

        tree.retrack();
        tree.retrack();
        System.out.println("should do nothing!");
        System.out.println(tree.toString());
        System.out.println("+++++++++++++++++++++++++++++++++");

        tree.insert(n240);
        System.out.println("inserting 240");
        System.out.println(tree.toString());
        System.out.println("+++++++++++++++++++++++++++++++++");

        tree.retrack();
        System.out.println("should do nothing!");
        System.out.println(tree.toString());
        System.out.println("+++++++++++++++++++++++++++++++++");

        tree.backtrack();
        System.out.println("delete 240!");
        System.out.println(tree.toString());
        System.out.println("+++++++++++++++++++++++++++++++++");

        tree.backtrack();
        System.out.println("insert40!");
        System.out.println(tree.toString());
        System.out.println("+++++++++++++++++++++++++++++++++");

        tree.retrack();
        System.out.println("delete 40!");
        System.out.println(tree.toString());
        System.out.println("+++++++++++++++++++++++++++++++++");

        tree.retrack();
        System.out.println("insert 240");
        System.out.println(tree.toString());
        System.out.println("+++++++++++++++++++++++++++++++++");

        tree.retrack();
        tree.retrack();
        System.out.println("should do nothing!");
        System.out.println(tree.toString());
        System.out.println("+++++++++++++++++++++++++++++++++");
//
//        tree.delete(n120);
//        System.out.println("delete 120");
//        System.out.println(tree.toString());
//        System.out.println("+++++++++++++++++++++++++++++++++");
//
//        tree.backtrack();
//        System.out.println("insert 120");
//        System.out.println(tree.toString());
//        System.out.println("+++++++++++++++++++++++++++++++++");







//





    }

}

