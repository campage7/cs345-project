package structures;

public class MerkleTree {
    private TreeNode root;

    public MerkleTree() {
        this.root = null;
    }

    public String addNewState(String newState) {
        TreeNode newNode = new TreeNode(newState);  // constructor hashes the newState string
        if (root == null) {
            root = newNode;
        } else {
            root = new TreeNode(root, newNode); // create new root with the old root and the new node as children
        }
//        System.out.println("New Root Hash: " + root.getHash());
        return newNode.getHash();
    }

    public TreeNode getRoot() {
        return root;
    }

    public String getRootHash() {
        if (root != null) {
            return root.getHash();
        }
        return null;
    }
}

