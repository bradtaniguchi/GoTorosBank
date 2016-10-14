package edu.csudh.goTorosBank;

/**
 * User - an account object. This class represents a User's finances.
 * 
 * @author Jesus Cortez
 */

public class Account{

    private int accountNumber;
    private String accountType; 
    private int accountBalance;
    private User user;
    
    /* Account Constructor */
    public Account(int accountNumber, int accountBalance, User user, String accountType){       
        this.accountNumber = accountNumber;
        this.accountType = accountType;
        this.accountBalance = accountBalance;
        this.user = user;
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
