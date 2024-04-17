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
                processInvalidBlock(transactions, i, merkleTree);
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
            System.out.println("Is the proof for block " + blockNumber + " valid? " + isValid);
            if (isValid) {
                System.out.println("New state transition for block " + blockNumber + ": " + newBlock.getFinalState());
                System.out.println();
            }
        } else {
            System.out.println("Proof could not be generated for block " + blockNumber);
        }
    }
    
    private static void processInvalidBlock(List<Transaction> transactions, int blockNumber, MerkleTree merkleTree) {
        Block newBlock = Sequencer.createBlock(transactions, blockNumber);

        String invalidState = newBlock.getFinalState() + "ERROR";
        String hashOfInvalidState = merkleTree.addNewState(invalidState);  // add an incorrect state
        
        Prover prover = new Prover(merkleTree);
        ZKProof proof = prover.generateProof(hashOfInvalidState);

        if (proof != null) {
            boolean isValid = Verifier.verifyProof(proof, merkleTree.getRootHash());
            System.out.println("Is the proof for invalid block " + blockNumber + " valid? " + isValid);
            if (!isValid) {
                System.out.println("Invalid block detected: block " + blockNumber);
            } else {
                System.out.println("Invalid block went undetected! block " + blockNumber);
            }
        } else {
            System.out.println("Proof could not be generated for invalid block " + blockNumber);
        }
        System.out.println();
    }

}
