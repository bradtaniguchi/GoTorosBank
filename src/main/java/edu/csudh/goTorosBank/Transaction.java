package edu.csudh.goTorosBank;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Transaction is a Class that holds all of the information of a transaction.
 * Transaction is a Class that holds all of the information of a transaction. that was
 * made in the database weather it was a deposit or withdrawl it will have all of these
 * attributes
 *
 * private int transactionNumber;
 * private String transactionDescription;
 * private Date transactionDate;
 * private float transactionAmount;
 * private Account account;
 * private SimpleDateFormat sdf;
 *
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

    /**
     * used to make a transaction and add a Date Object to it
     * @param account account that the transaaction is being made from
     * @param transactionNumber teh transaction number that is being made
     * @param transactionAmount that actual account number for the transaction
     * @param transactionDescription the transaction description
     * @param date the date that the transaction is being made in
     */
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

    /**
     *
     * @return int transaction number
     */
    public int getTransactionNumber() {
        return transactionNumber;
    }

    /**
     *
     * @return String with a description of a transaction
     */
    public String getTransactionDescription() { return transactionDescription;}

    /**
     *
     * @return float the ammount of the transaction
     */
    public float getTransactionAmount() {
        return transactionAmount;
    }

    /**
     *
     * @return String the Date the transaction was made already formated
     */
    public String getDate() { return sdf.format(transactionDate); }

    /**
     *
     * @return account that the transaction is being made for
     */
    public Account getAccount(){
        return account;
    }
}
