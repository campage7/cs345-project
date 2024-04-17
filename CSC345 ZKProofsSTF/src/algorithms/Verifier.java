package algorithms;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;

import structures.ZKProof;

public class Verifier {
	
    public static boolean verifyProof(ZKProof proof, String rootHash) {
        String currentHash = proof.getDataHash();
        for (String siblingHash : proof.getPathToRoot()) {
            currentHash = hashData(siblingHash + currentHash);
        }
        return currentHash.equals(rootHash);
    }

    private static String hashData(String data) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(data.getBytes(StandardCharsets.UTF_8));
            return HexFormat.of().formatHex(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 error", e);
        }
    }
}
