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
    private ArrayList<Bill> bills;

    /* Account Constructor */
    public Account(int accountNumber, int accountBalance, User user, String accountType,
                   ArrayList<Transaction> transactions, ArrayList<Bill> bills){
        this.accountNumber = accountNumber;
        this.accountType = accountType;
        this.accountBalance = accountBalance;
        this.user = user;
        this.transactions = transactions;
        this.bills = bills;
    }
    public Account(int accountNumber, int accountBalance, User user, String accountType,
                   ArrayList<Transaction> transactions) {
        this(accountNumber, accountBalance, user, accountType, transactions, null);
    }
    public Account(int accountNumber, int accountBalance, User user, String accountType) {
        this(accountNumber, accountBalance, user, accountType, null, null);
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
