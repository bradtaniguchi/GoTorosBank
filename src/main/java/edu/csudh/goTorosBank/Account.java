package edu.csudh.goTorosBank;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;

/**
 * User - an account object. This class represents a User's finances.
 * 
 * @author Jesus Cortez, Bradley Taniguchi
 */

public class Account{

    private int accountNumber;
    private String accountType; 
    private int accountBalance;
    private User user;
    private ArrayList<Transaction> transactions;
    private ArrayList<Bill> bills;

    /**
     * account constructor with transaction and bills
     * @param accountNumber account number for the account
     * @param accountBalance account balance of the account
     * @param user the user associated with the account
     * @param accountType the account type
     * @param transactions the Array list of transactions
     * @param bills the array list of bills
     */
    public Account(int accountNumber, int accountBalance, User user, String accountType,
                   ArrayList<Transaction> transactions, ArrayList<Bill> bills){
        this.accountNumber = accountNumber;
        this.accountType = accountType;
        this.accountBalance = accountBalance;
        this.user = user;
        if(transactions == null ) {
            this.transactions = new ArrayList<Transaction>();
        } else {
            this.transactions = transactions;
        }
        if(transactions == null ) {
            this.bills = new ArrayList<Bill>();
        } else {
            this.bills = bills;
        }
    }

    /**
     * constructor for the account with transactions
     * @param accountNumber account number for the account
     * @param accountBalance account balance of the account
     * @param user the user associated with the account
     * @param accountType the account type
     * @param transactions the Array list of transactions
     */
    public Account(int accountNumber, int accountBalance, User user, String accountType,
                   ArrayList<Transaction> transactions) {
        this(accountNumber, accountBalance, user, accountType, transactions, null);
    }

    /**
     * constructor for account with no transaction or bills
     * @param accountNumber account number for the account
     * @param accountBalance account balance of the account
     * @param user the user associated with the account
     * @param accountType the account type
     */
    public Account(int accountNumber, int accountBalance, User user, String accountType) {
        this(accountNumber, accountBalance, user, accountType, null, null);
    }
    @SuppressWarnings("unchecked")
    public JSONObject toJSON() {
        JSONObject account = new JSONObject();
        JSONArray jsonTransactions = new JSONArray();
        JSONArray jsonBills = new JSONArray();
        account.put("accountBalance", this.accountBalance);
        account.put("accountNumber", this.accountNumber);
        account.put("accountType", this.accountType);

        /*setup the accounts for the user*/
        for (Transaction trans : transactions) {
            jsonTransactions.add(trans.toJSON()); //finish this once function added to classes
        }
        for (Bill bill : bills) {
            jsonBills.add(bill.toJSON());
        }
        account.put("transactions", jsonTransactions);
        account.put("bills", jsonBills);
        return account;
    }
    /* setter methods */
    public void addTransaction(Transaction transaction) {
        this.transactions.add(transaction);
    }
    public void addTransactions(ArrayList<Transaction> transactions) {
        this.transactions.addAll(transactions);
    }
    public void addBill(Bill bill) { this.bills.add(bill); }
    public void addBills(ArrayList<Bill> bills) { this.bills.addAll(bills);}
    /*  getter methods    */
    
    public int getAccountNumber(){
        return accountNumber;
    }
    public String getAccountType(){
        return accountType;
    }
    public int getAccountBalance(){
        return accountBalance;
    }
    //needs to get userID...Modification coming soon, its ok
    public User getUser(){
        return user;
    }
    public ArrayList<Transaction> getTransactions() {return transactions; }
    public ArrayList<Bill> getBills() {return bills; }
}
