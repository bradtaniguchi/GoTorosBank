package edu.csudh.goTorosBank;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by brad on 10/12/16.
 * Based Off Database Model 10/12/16
 */
public class Transaction {
    private int transactionNumber;
    private String transactionDescription;
    private Date transactionDate;
    private float transactionAmount;
    private Account account;
    private SimpleDateFormat sdf;


    /**
     * PrimaryConstructor of a Transaction with the bank.
     * @param account the account object tied to this transaction
     * @param transactionAmount the amount this transaction is for (positive or negative float)
     * @param transactionDescription if there is one, a transaction description.
     */
    public Transaction(Account account, int transactionNumber, float transactionAmount, String transactionDescription) {
        this.transactionDescription = transactionDescription;
        this.transactionAmount = transactionAmount;
        this.transactionDate = new Date();
        this.transactionNumber = transactionNumber;
        this.account = account;
        sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//dd/MM/yyyy
    }

    public Transaction(Account account, int transactionNumber, float transactionAmount, String transactionDescription, Date date){
        this.transactionDescription = transactionDescription;
        this.transactionAmount = transactionAmount;
        this.transactionDate = date;
        this.transactionNumber = transactionNumber;
        this.account = account;
        sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//dd/MM/yyyy
    }
    @SuppressWarnings("unchecked")
    public JSONObject toJSON() {
        JSONObject transaction = new JSONObject();
        transaction.put("transactionNumber", this.transactionNumber);
        transaction.put("transactionAmount", this.transactionAmount);
        transaction.put("transactionDate", sdf.format(this.transactionDate));
        transaction.put("transactionDescription", this.transactionDescription);
        return transaction;
    }

    /**
     * Transaction Constructor
     *
     * @param account the account that will make the transaction
     * @param transactionNumber the transaction number
     * @param transactionAmount the amount of money that is being transacted
     */
    public Transaction(Account account, int transactionNumber, float transactionAmount) {
        this(account, transactionNumber, transactionAmount,""); /*call the other constructor*/
    }
    public int getTransactionNumber() {
        return transactionNumber;
    }
    public String getTransactionDescription() { return transactionDescription;}
    public float getTransactionAmount() {
        return transactionAmount;
    }
    public String getDate() { return sdf.format(transactionDate); }
    public Account getAccount(){
        return account;
    }
}
