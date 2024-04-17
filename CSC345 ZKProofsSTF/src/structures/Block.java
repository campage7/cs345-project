package structures;

import java.util.List;

public class Block {
    private List<Transaction> transactions;
    private String finalState;
    private int blockNumber;

    public Block(List<Transaction> transactions, String finalState, int blockNumber) {
        this.transactions = transactions;
        this.finalState = finalState;
        this.blockNumber = blockNumber;
    }

    public String getFinalState() {
        return finalState;
    }

    public int getBlockNumber() {
        return blockNumber;
    }
}
