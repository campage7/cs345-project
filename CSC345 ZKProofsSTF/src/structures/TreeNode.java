package structures;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;

public class TreeNode {
    private String hash;
    private TreeNode left;
    private TreeNode right;

    public TreeNode(String data) {
        this.hash = hashData(data);
 //       System.out.println("Creating leaf node with hash: " + hash);
    }

    public TreeNode(TreeNode left, TreeNode right) {
        this.left = left;
        this.right = right;
        this.hash = hashData(left.hash + right.hash);
//        System.out.println("Creating parent node with hash: " + hash + " from left: " + left.hash + " right: " + (right != null ? right.hash : "null"));
    }

    private static String hashData(String data) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(data.getBytes(StandardCharsets.UTF_8));
            return HexFormat.of().formatHex(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 algorithm not available", e);
        }
    }

    public String getHash() {
        return hash;
    }
    
    public TreeNode getLeft() {
    	return left;
    }
    
    public TreeNode getRight() {
    	return right;
    }
}

