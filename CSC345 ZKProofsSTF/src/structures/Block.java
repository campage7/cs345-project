package structures;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;
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

    public String computeStateHash() {
        return hashData(finalState);
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
}


