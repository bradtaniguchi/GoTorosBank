package edu.csudh.goTorosBank;

/**
 * Created by brad on 10/12/16.
 * Based Off Database Model 10/12/16
 */
public class Transaction {
    private int transactionNumber;
    private String transactionDescription;
    private float transactionAmount;
    private Account account;

    /**
     * PrimaryConstructor of a Transaction with the bank.
     * @param account the account object tied to this transaction
     * @param transactionAmount the amount this transaction is for (positive or negative float)
     * @param transactionDescription if there is one, a transaction description.
     */
    public Transaction(Account account, float transactionAmount,
                       String transactionDescription) {
        this.transactionDescription = transactionDescription;
        this.transactionAmount = transactionAmount;
        this.account = account;
    }

    /**
     * Secondary Constructor, if the transaction has no given transactionDescription
     */
    public Transaction(Account account, float transactionAmount) {
        this(account, transactionAmount, ""); /*call the other constructor*/
    }

    /*Getters, no setters as things are set in stone after initialization*/
    public int getTransactionNumber() {
        return transactionNumber;
    }
    public String getTransactionDescription() {
        return transactionDescription;
    }
    public float getTransactionAmount() {
        return transactionAmount;
    }
    public Account getAccount(){
        return account;
    }
}
