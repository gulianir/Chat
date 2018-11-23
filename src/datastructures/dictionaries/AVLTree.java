package datastructures.dictionaries;

import cse332.datastructures.trees.BinarySearchTree;

/**
 * TODO: Replace this comment with your own as appropriate.
 *
 * AVLTree must be a subclass of BinarySearchTree<E> and must use inheritance
 * and callst o superclass methods to avoid unnecessary duplication or copying
 * of functionality.
 *
 * 1. Create a subclass of BSTNode, perhaps named AVLNode. 2. Override the
 * insert method such that it creates AVLNode instances instead of BSTNode
 * instances. 3. Do NOT "replace" the children array in BSTNode with a new
 * children array or left and right fields in AVLNode. This will instead mask
 * the super-class fields (i.e., the resulting node would actually have multiple
 * copies of the node fields, with code accessing one pair or the other
 * depending on the type of the references used to access the instance). Such
 * masking will lead to highly perplexing and erroneous behavior. Instead,
 * continue using the existing BSTNode children array. 4. If this class has
 * redundant methods, your score will be heavily penalized. 5. Cast children
 * array to AVLNode whenever necessary in your AVLTree. This will result a lot
 * of casts, so we recommend you make private methods that encapsulate those
 * casts. 6. Do NOT override the toString method. It is used for grading.
 */

public class AVLTree<K extends Comparable<K>, V> extends BinarySearchTree<K, V> {

    @Override
    protected AVLNode find(K key, V value) {
        AVLNode prev = null;
        AVLNode current = getRoot();

        int child = -1;

        while (current != null) {
            int direction = Integer.signum(key.compareTo(current.key));

            // We found the key!
            if (direction == 0) {
                return current;
            }
            else {
                // direction + 1 = {0, 2} -> {0, 1}
                child = Integer.signum(direction + 1);
                prev = current;
                current = getAVL(current.children[child]);
            }
        }

        // If value is not null, we need to actually add in the new value
        if (value != null) {
            current = new AVLNode(key, null);
            if (this.root == null) {
                this.root = current;
            }
            else {
                assert (child >= 0); // child should have been set in the loop
                                     // above
                prev.children[child] = current;
            }
            current.parent = prev;
            this.size++;
        }

        return current;
    }

    @Override
    public V insert(K key, V value) {
        if (key == null || value == null) {
            throw new IllegalArgumentException();
        }
        AVLNode current = find(key, value);

        this.fixAVLTree(current);

        V oldValue = current.value;
        current.value = value;
        return oldValue;
    }

    /*
     * Starts at the given node and iterates up using the parent pointer. Keeps
     * going until problem is detected and fixed.
     */
    private void fixAVLTree(AVLNode curr) {
        while (true) {
            if (curr == null) {
                break;
            }
            if (curr.parent != null && curr.parent.height != curr.height) {
                break;
            }

            if (!isBalanced(curr)) {
                int parentBalance = getParentBalance(curr);
                int balance1 = getBalance(curr);
                int balance2;
                // left is bigger
                if (balance1 > 0) {
                    // case 1
                    balance2 = getBalance(curr.getLeft());
                    if (balance2 > 0) {
                        // single right rotate
                        rotate(curr, 1, parentBalance);
                        break;
                    }
                    else { // case 2: LR
                        rotate(curr.getLeft(), -1, -1);
                        rotate(curr, 1, parentBalance);
                        break;
                    }

                }
                else { // right is bigger
                    balance2 = getBalance(curr.getRight());
                    // case 3
                    if (balance2 > 0) {
                        rotate(curr.getRight(), 1, 1);
                        rotate(curr, -1, parentBalance);
                        break;
                    }
                    else { // case 4
                        rotate(curr, -1, parentBalance);
                        break;
                    }
                }
            } else {
                if(curr.parent != null && curr.parent.height == curr.height) {
                    curr.parent.height++;
                }
            }
            curr = curr.parent;
        }

    }

    // directions: -1 for left rotate and 1 for right rotate
    // parentDirection is 1 if parent's right needs to be updated and -1
    // if parent's left needs to be updated. 0 if there is no parent (root).
    private void rotate(AVLNode curr, int direction, int parentDirection) {

        AVLNode childOfProblem = null;
        AVLNode movedTree = null;

        // Right rotate
        if (direction == 1) {
            childOfProblem = curr.getLeft();
            movedTree = curr.getLeft().getRight(); // can be null

            childOfProblem.setRight(curr);
            curr.setLeft(movedTree);

        }
        else { // left rotate
            childOfProblem = curr.getRight();
            movedTree = curr.getRight().getLeft();

            childOfProblem.setLeft(curr);
            curr.setRight(movedTree);
        }

        if (curr.parent != null) {
            // update parent's left child
            if (parentDirection == 1) {
                curr.parent.setRight(childOfProblem);
            }
            else {
                curr.parent.setLeft(childOfProblem);
            }
        }
        else {
            this.root = childOfProblem;
        }

        childOfProblem.parent = curr.parent;
        curr.parent = childOfProblem;
        if (movedTree != null) {
            movedTree.parent = curr;
        }

        updateHeight(curr);
        updateHeight(childOfProblem);
    }
    
    private int getHeight(AVLNode curr) {
        return (curr==null) ? -1 : curr.height;
    }
    
    private void updateHeight(AVLNode curr) {
        curr.height = Math.max(getHeight(curr.getRight()), getHeight(curr.getLeft())) + 1;
    }

    /*
     * Returns parent direction that needs to be updated, -1 for left child and 1
     * for right child.
     */
    private int getParentBalance(AVLNode curr) {
        if (curr.parent == null) {
            return 0;
        }
        if (curr.key.compareTo(curr.parent.key) > 0) {
            return 1;
        }
        else {
            return -1;
        }
    }

    private boolean isBalanced(AVLNode curr) {
        int balance = getBalance(curr);
        return balance >= -1 && balance <= 1;
    }

    /*
     * Returns the difference in heights between the left tree and right tree. Is
     * positive if left height > right height Is zero if left height = right height
     * Is negative if left height < right height
     */
    private int getBalance(AVLNode curr) {
        int leftHeight = -1;
        int rightHeight = -1;

        if (curr.getLeft() != null) {
            leftHeight = curr.getLeft().height;
        }

        if (curr.getRight() != null) {
            rightHeight = curr.getRight().height;
        }
        return leftHeight - rightHeight;
    }

    private AVLNode getRoot() {
        return getAVL(root);
    }

    @SuppressWarnings("unchecked")
    private AVLNode getAVL(BSTNode node) {
        return (AVLNode) node;
    }

    /*
     * Used for debugging purposes.
     * Recursively prints out entire given subtree in preorder fashion.
     */
    @SuppressWarnings("unused")
    private void printTree(AVLNode curr) {

        if (curr == null) {
            return;
        }
        String leftKey = "\t";
        String rightKey = "\t";

        if (curr.getLeft() != null) {
            leftKey = " " + curr.getLeft().key.toString();
        }

        if (curr.getRight() != null) {
            rightKey = " " + curr.getRight().key.toString();
        }

        System.err.println("(" + curr.key + ", " + curr.height + ")" + leftKey + rightKey);
        printTree(curr.getLeft());
        printTree(curr.getRight());

    }

    public class AVLNode extends BSTNode {
        public int height;
        public AVLNode parent;

        public AVLNode(K key, V value) {
            super(key, value);
            this.height = 0;
            this.parent = null;
        }

        public AVLNode getLeft() {
            return getAVL(children[0]);
        }

        public AVLNode getRight() {
            return getAVL(children[1]);
        }

        public void setLeft(AVLNode node) {
            this.children[0] = node;
        }

        public void setRight(AVLNode node) {
            this.children[1] = node;
        }

        public String toString() {
            if (parent == null) {
                return "Data: " + key + ", " + value + " Height: " + height + " Parent: "
                        + parent;
            }
            return "Data: " + key + ", " + value + " Height: " + height + " Parent: "
                    + parent.key + " parent's height " + parent.height;
        }

        public void printNode() {
            System.err.println("CURR: " + toString());
            System.err.println("LEFT: " + getLeft().toString());
            System.err.println("RIGHT " + getRight().toString());
        }

    }

}
