package edu.csudh.goTorosBank;

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

    /* Account Constructor */
    public Account(int accountNumber, int accountBalance, User user, String accountType,
                   ArrayList<Transaction> transactions){
        this.accountNumber = accountNumber;
        this.accountType = accountType;
        this.accountBalance = accountBalance;
        this.user = user;
        this.transactions = transactions;
    }

    public Account(int accountNumber, int accountBalance, User user, String accountType) {
        this(accountNumber, accountBalance, user, accountType, null);
    }
    /* setter methods */
    public void addTransaction(Transaction transaction) {
        this.transactions.add(transaction);
    }
    public void addTransactiosn(ArrayList<Transaction> transactions) {
        this.transactions.addAll(transactions);
    }
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
    
    //needs to get userID...Modification coming soon
    public User getUser(){
        return user;
    }
    
}
