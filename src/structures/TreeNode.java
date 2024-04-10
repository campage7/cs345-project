package structures;

public class TreeNode {
    private String hash;
    private TreeNode left;
    private TreeNode right;

    // constructor for leaf nodes
    public TreeNode(String data) {
        this.hash = hashData(data);
        this.left = null;
        this.right = null;
    }

    // constructor for non-leaf nodes
    public TreeNode(TreeNode left, TreeNode right) {
        this.left = left;
        this.right = right;
        this.hash = hashData(left.hash + right.hash);
    }
    
    public String getHash() { return hash; }

    public TreeNode getLeft() { return left; }

    public TreeNode getRight() { return right; }

    // TODO: implement custom hashing function?
    private String hashData(String data) {
        return Integer.toString(data.hashCode());
    }
}

