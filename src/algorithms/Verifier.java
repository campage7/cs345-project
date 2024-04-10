package algorithms;

import java.util.List;

import structures.ZKProof;

public class Verifier {

	// verify proof by reconstructing path to root hash
	public static boolean verifyProof(ZKProof proof, String rootHash) {
		String currentHash = proof.getLeafHash();
		List<String> pathToRoot = proof.getPathToRoot();

		// iterate over path hashes and simulate "hashing up" process
		for (String pathHash : pathToRoot) {
			// TODO: consider child position (left/right) to correctly concatenate hashes
			// before hashing them together
			currentHash = simulateHash(currentHash + pathHash);
		}

		// proof is valid if reconstructed hash matches root hash
		return currentHash.equals(rootHash);
	}

	private static String simulateHash(String data) {
		// TODO: use the same hashing technique as TreeNode
		return Integer.toString(data.hashCode());
	}
}
