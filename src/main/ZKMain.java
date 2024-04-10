package main;
import algorithms.Prover;
import algorithms.Verifier;
import structures.TreeNode;
import structures.ZKProof;

public class ZKMain {

    public static void main(String[] args) {
        // txs and their hashes
        String tx1 = "tx1Data";
        String tx2 = "tx2Data";
        String tx3 = "tx3Data";
        String tx4 = "tx4Data";

        // leaf nodes for each tx 
        TreeNode nodeTx1 = new TreeNode(tx1);
        TreeNode nodeTx2 = new TreeNode(tx2);
        TreeNode nodeTx3 = new TreeNode(tx3);
        TreeNode nodeTx4 = new TreeNode(tx4);

        // build next layer of tree
        TreeNode parent1 = new TreeNode(nodeTx1, nodeTx2);
        TreeNode parent2 = new TreeNode(nodeTx3, nodeTx4);

        // create root node
        TreeNode root = new TreeNode(parent1, parent2);

        // initialize Prover with tree root
        Prover prover = new Prover(root);

        // generate proof for tx1
        ZKProof proofForTx1 = prover.generateProof(nodeTx1.getHash());

        if (proofForTx1 != null) {
            // output the proof details
            System.out.println("Proof for tx1:");
            System.out.println("Leaf Hash: " + proofForTx1.getLeafHash());
            System.out.println("Path to Root: " + proofForTx1.getPathToRoot());

            // verify the proof
            boolean isValid = Verifier.verifyProof(proofForTx1, root.getHash());
            System.out.println("Is the proof for tx1 valid? " + isValid);
        } else {
            System.out.println("No proof could be generated for tx1.");
        }
    }
}

