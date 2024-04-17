package algorithms;

import java.util.List;

import structures.Block;
import structures.Transaction;

public class Sequencer {
    public static Block createBlock(List<Transaction> transactions, int blockNumber) {
        StringBuilder newState = new StringBuilder();
        transactions.forEach(tx -> newState.append(tx.getOperation()));
        newState.append(blockNumber);
        
        return new Block(transactions, newState.toString(), blockNumber);
    }
}
