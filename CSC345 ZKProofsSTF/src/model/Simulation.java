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
        String lastValidState = "Initial state";
        for (int i = 1; i <= numberOfBlocks; i++) {
            List<Transaction> transactions = generateRandomTransactions();
            lastValidState = processBlock(transactions, i, merkleTree, lastValidState);
            if (i % 3 == 0) { 
                processInvalidBlock(transactions, i, merkleTree, lastValidState);
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
    
    private static String processBlock(List<Transaction> transactions, int blockNumber, MerkleTree merkleTree, String lastValidState) {
        Block newBlock = Sequencer.createBlock(transactions, blockNumber);
        String newState = newBlock.getFinalState();
        if (isValidStateTransition(lastValidState, newState)) {
            String hashOfNewState = merkleTree.addNewState(newState);
            Prover prover = new Prover(merkleTree);
            ZKProof proof = prover.generateProof(hashOfNewState);
            
            if (proof != null) {
                boolean isValid = Verifier.verifyProof(proof, merkleTree.getRootHash());
                System.out.println("Is the proof for block " + blockNumber + " valid? " + isValid);
                if (isValid) {
                    System.out.println("New state transition for block " + blockNumber + ": " + newState);
                    System.out.println();
                    return newState;
                }
            } else {
                System.out.println("Proof could not be generated for block " + blockNumber);
            }
        } else {
            System.out.println("Invalid state transition detected before adding to tree: block " + blockNumber);
        }
        return lastValidState;
    }
    
    private static void processInvalidBlock(List<Transaction> transactions, int blockNumber, MerkleTree merkleTree, String lastValidState) {
        Block newBlock = Sequencer.createBlock(transactions, blockNumber);
        String invalidState = newBlock.getFinalState() + "ERROR";
        if (isValidStateTransition(lastValidState, invalidState)) {
            System.out.println("Invalid state passed validation, which should not happen: block " + blockNumber);
        } else {
            System.out.println("Correctly identified invalid block: block " + blockNumber);
        }
        System.out.println();
    }

    private static boolean isValidStateTransition(String previousState, String newState) {
        return !newState.contains("ERROR");
    }
}

