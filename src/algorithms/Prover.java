package algorithms;
import java.util.ArrayList;
import java.util.List;

import structures.TreeNode;
import structures.ZKProof;

public class Prover {
    private TreeNode root;

    // initialize Prover with commitment tree root (Merkle tree of txData)
    public Prover(TreeNode root) {
        this.root = root;
    }

    // generate ZKProof for given dataHash
    public ZKProof generateProof(String dataHash) {
        List<String> pathToRoot = new ArrayList<>();
        
        if (collectPath(this.root, dataHash, pathToRoot)) {
            ZKProof proof = new ZKProof(dataHash);
            pathToRoot.forEach(proof::addPathNode);  // ZKProof object stores Prover's path of hashes
            return proof;
        }
        return null; // dataHash not found
    }

    // collect path of hashes from node with value dataHash up to root
    private boolean collectPath(TreeNode node, String dataHash, List<String> path) {
        if (node == null) {
            return false; // base case >> reached a leaf's child
        }
        if (node.getHash().equals(dataHash)) {
            return true; // base case >> node with dataHash found
        }

        if (collectPath(node.getLeft(), dataHash, path)) {
            if (node.getRight() != null) {
                path.add(node.getRight().getHash());
            }
            return true;
        }
        if (collectPath(node.getRight(), dataHash, path)) {
            if (node.getLeft() != null) {
                path.add(node.getLeft().getHash());
            }
            return true;
        }
        // node is not part of path; backtrack
        return false;
    }
}
