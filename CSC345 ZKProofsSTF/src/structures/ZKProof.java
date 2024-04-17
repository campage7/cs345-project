package structures;

import java.util.List;

/**
 * ZKProof class holds the necessary components of a Merkle proof.
 * This includes the hash of the data being proven (dataHash) and the path to the Merkle tree root.
 * This allows a verifier to check the validity of the data without having access to the actual data.
 */
public class ZKProof {
    private String dataHash;         // The hash of the data (or state) for which the proof is generated.
    private List<String> pathToRoot; // The list of hashes forming the path from the data to the Merkle tree root.

    /**
     * Constructor for ZKProof.
     * @param dataHash The hash of the data being proven.
     * @param pathToRoot A list of sibling hashes from the data up to the root of the tree.
     */
    public ZKProof(String dataHash, List<String> pathToRoot) {
        this.dataHash = dataHash;
        this.pathToRoot = pathToRoot;
    }

    /**
     * Returns the hash of the data being proven.
     * @return The data hash.
     */
    public String getDataHash() {
        return dataHash;
    }

    /**
     * Returns the path of hashes from the data to the Merkle tree root.
     * @return The list of hashes forming the path.
     */
    public List<String> getPathToRoot() {
        return pathToRoot;
    }
}
