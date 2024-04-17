package structures;

public class Transaction {
    private String operation; // (e.g., BUY, SND, MNT)

    public Transaction(String operation) {
        this.operation = operation;
    }

    public String getOperation() {
        return operation;
    }
    
    public String execute(String currentState) {
        return currentState + operation; // Simplified state transition
    }
}

