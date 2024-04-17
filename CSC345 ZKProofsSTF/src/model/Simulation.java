package model;

import java.util.List;
import java.util.ArrayList;
import java.util.Random;
import algorithms.Prover;
import algorithms.Sequencer;
import algorithms.Verifier;
import structures.Block;
import structures.MerkleTree;
import structures.Transaction;
import structures.ZKProof;

public class Simulation {
    private static final String[] TX_TYPES = {"MNT", "SWP", "SND"};

    public static void main(String[] args) {
        MerkleTree merkleTree = new MerkleTree();
        merkleTree.addNewState("Initial state");

        int numberOfBlocks = 10;
        for (int i = 1; i <= numberOfBlocks; i++) {
            List<Transaction> transactions = generateRandomTransactions();
            processBlock(transactions, i, merkleTree);
            if (i % 3 == 0) {
                simulateInvalidProof(i, merkleTree);
            }
        }
    }

    private static List<Transaction> generateRandomTransactions() {
        Random random = new Random();
        int numTransactions = random.nextInt(5) + 1;
        List<Transaction> transactions = new ArrayList<>();
        for (int i = 0; i < numTransactions; i++) {
            int txTypeIndex = random.nextInt(TX_TYPES.length);
            transactions.add(new Transaction(TX_TYPES[txTypeIndex]));
        }
        return transactions;
    }

    private static void processBlock(List<Transaction> transactions, int blockNumber, MerkleTree merkleTree) {
        Block newBlock = Sequencer.createBlock(transactions, blockNumber);
        String hashOfNewState = merkleTree.addNewState(newBlock.getFinalState());
        Prover prover = new Prover(merkleTree);
        ZKProof proof = prover.generateProof(hashOfNewState);

        if (proof != null) {
            boolean isValid = Verifier.verifyProof(proof, merkleTree.getRootHash());
            System.out.println("Block " + blockNumber + " proof valid? " + isValid);
            System.out.println("New state transition for block " + blockNumber + ": " + newBlock.getFinalState());
            System.out.println();
        } else {
            System.out.println("Proof could not be generated for block " + blockNumber);
        }
    }

    private static void simulateInvalidProof(int blockNumber, MerkleTree merkleTree) {
        String fakeState = "FAKE_STATE_" + blockNumber;
        String fakeStateHash = Verifier.hashData(fakeState);

        Prover prover = new Prover(merkleTree);
        ZKProof proof = prover.generateProof(fakeStateHash);

        if (proof == null) {
            System.out.println("Invalid proof simulation for next block. Proof could not be generated.");
        } else {
            boolean isValid = Verifier.verifyProof(proof, merkleTree.getRootHash());
            System.out.println("Invalid proof simulation for next block. This should not happen.");
        }
        System.out.println();
    }
}
