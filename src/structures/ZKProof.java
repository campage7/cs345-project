package structures;
import java.util.ArrayList;
import java.util.List;

public class ZKProof {
    private String leafHash; // hash of leaf node being proven
    private List<String> pathToRoot; // hashes from leaf to root

    public ZKProof(String leafHash) {
        this.leafHash = leafHash;
        this.pathToRoot = new ArrayList<>();
    }

    // adds hash to path; used to build path from leaf to root
    public void addPathNode(String hash) {
        pathToRoot.add(hash);
    }

    public String getLeafHash() { return leafHash; }

    public List<String> getPathToRoot() { return pathToRoot; }
}
