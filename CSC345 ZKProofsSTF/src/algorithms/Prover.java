package algorithms;

import java.util.ArrayList;
import java.util.List;
import structures.TreeNode;
import structures.ZKProof;
import structures.MerkleTree;

public class Prover {
    private MerkleTree merkleTree;

    public Prover(MerkleTree merkleTree) {
        this.merkleTree = merkleTree;
    }

    public ZKProof generateProof(String finalStateHash) {
        List<String> pathToRoot = new ArrayList<>();
        TreeNode root = merkleTree.getRoot();
        if (collectPath(root, finalStateHash, pathToRoot)) {
            return new ZKProof(finalStateHash, pathToRoot);
        } else {
            return null;
        }
    }
    
    private boolean collectPath(TreeNode node, String hash, List<String> path) {
        if (node == null) return false;

        if (node.getHash().equals(hash)) {
            return true;
        }

        if (collectPath(node.getLeft(), hash, path)) {
            if (node.getRight() != null) {
                path.add(node.getRight().getHash());
            }
            return true;
        } else if (collectPath(node.getRight(), hash, path)) {
            if (node.getLeft() != null) {
                path.add(node.getLeft().getHash());
            }
            return true;
        }
        return false;
    }

}


