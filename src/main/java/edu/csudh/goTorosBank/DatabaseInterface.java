/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.csudh.goTorosBank;

import com.sun.org.apache.bcel.internal.generic.SWAP;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 *
 * @authors Rudy, Daniel, Bradley, Crosby, Jesus
 */
public class DatabaseInterface {

    private String connectionLink = "jdbc:sqlite::resource:GoTorosBank.db";

    public DatabaseInterface() {
    }

    /**
     * checks all users in the database and makes sure that the user name and
     * password is inside the database
     *
     * @param username the users name
     * @param userpassword the users password
     * @return true if users is inside the data base false if the user is not in
     * the data base
     * @throws ClassNotFoundException checks if file is inside
     * @throws SQLException checks for sql exceptions
     */
    public boolean validate(String username, String userpassword)
            throws ClassNotFoundException, SQLException {

        Connection c = null;

        Class.forName("org.sqlite.JDBC");
        c = DriverManager.getConnection(connectionLink); //this will get the file in resources
        /*do other stuff here*/

        Statement statement = c.createStatement();
        statement.setQueryTimeout(30); // set timeout to 30 sec.

        ResultSet resultSet = statement.executeQuery("SELECT * FROM USERS");

        while (resultSet.next()) {
            // iterate & read the result set
            String usname = resultSet.getString("USERNAME");
            String uspass = resultSet.getString("PASSWD");

            if(username.contentEquals(usname) && userpassword.contentEquals(uspass)){
                c.close();
                return true;
            }
        }
        c.close();
        return false;
    }
    
    private void getAccounts(int userID) throws SQLException, ClassNotFoundException{
        Connection c = null;
        Statement stmt = null;
        ResultSet resultSet = null;
        
        Class.forName("org.sqlite.JDBC");
        c = DriverManager.getConnection(connectionLink);
       
        stmt = c.createStatement();
       resultSet = stmt.executeQuery("SELECT * ACCOUNTS, USER"
                                    + "WHERE ACCOUNTS.UID = USER.UID;");
        
        while (resultSet.next()){
            int id = resultSet.getInt("UID");
            
           if(userID == id){
            String username = resultSet.getString("USERNAME");   
            int accountNumber = resultSet.getInt("ACCOUNT_NUMBER");
            String accountType = resultSet.getString("ACCOUNT_TYPE");
            int accountBalance = resultSet.getInt("ACCOUNT_BALANCE"); 
            
            Account account = new Account(accountNumber, accountBalance, getUser(username), accountType);
            }
        }
        
            resultSet.close();
            stmt.close();
            c.close();
    }

    /**
     * just uses the withdraw and deposit finction to make a transaction
     *
     * @param accountIDFrom the id that the money is comming from
     * @param accountIDTo the id for the account that the money is comming from
     * @param amount the amount of money that will be passed
     * @throws SQLException
     * @throws ClassNotFoundException
     */

    public void transfer(int accountIDFrom, int accountIDTo,double amount)
            throws SQLException,ClassNotFoundException{
        withdraw(accountIDFrom,amount,"Tansfer withdraw to account number: "+accountIDTo);
        deposit(accountIDTo,amount,"Tansfer deposit from account number: "+accountIDFrom);
    }

    /**
     * enter information and the withdrawl will be mad all you have to do is enter account
     * ID amount and description
     *
     * @param accountIDFrom ID of the account that is being withdrew from
     * @param amount amount of money being transfered
     * @param description description of transfer
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public void withdraw(int accountIDFrom,double amount, String description)
            throws SQLException,ClassNotFoundException{
        int accountbalance;
        Connection c = null;

        Class.forName("org.sqlite.JDBC");
        c = DriverManager.getConnection(connectionLink); //this will get the file in resources
        /*do other stuff here*/

        Statement statement = c.createStatement();
        statement.setQueryTimeout(30); // set timeout to 30 sec.
        ResultSet resultSet;

        //get custumers account balance
        resultSet = statement.executeQuery(
                "SELECT ACCOUNT_BALANCE " +
                "FROM ACCOUNTS " +
                "WHERE ACCOUNT_NUMBER="+accountIDFrom+";");

        accountbalance = resultSet.getInt("ACCOUNT_BALANCE");

        //subtract ammount from balance
        accountbalance-=amount;

        //used to change the balance in the database
        statement.executeUpdate(
                "UPDATE ACCOUNTS"+
                "SET ACCOUNT_BALANCE="+ accountbalance +
                "WHERE ACCOUNT_NUMBER="+accountIDFrom+";");

        c.close();

        //now add a transfer row for the transaction
        addTransaction(accountIDFrom,description,amount);
    }

    /**
     *  enter the information below and it will automaticly change account balance
     *  update the account and a transaction will be made
     * @param accountID account ID
     * @param amount amount to be deposited
     * @param description description
     */
    public void deposit(int accountID, double amount, String description)throws SQLException, ClassNotFoundException{
        int accountbalance;
        Connection c = null;

        Class.forName("org.sqlite.JDBC");
        c = DriverManager.getConnection(connectionLink); //this will get the file in resources
        /*do other stuff here*/

        Statement statement = c.createStatement();
        statement.setQueryTimeout(30); // set timeout to 30 sec.
        ResultSet resultSet;

        //get custumers account balance
        resultSet = statement.executeQuery(
                "SELECT ACCOUNT_BALANCE " +
                        "FROM ACCOUNTS " +
                        "WHERE ACCOUNT_NUMBER="+accountID+";");

        accountbalance = resultSet.getInt("ACCOUNT_BALANCE");

        //add ammount from balance
        accountbalance+=amount;

        //used to change the balance in the database
        statement.executeUpdate(
                "UPDATE ACCOUNTS"+
                        "SET ACCOUNT_BALANCE="+ accountbalance +
                        "WHERE ACCOUNT_NUMBER="+accountID+";");

        c.close();

        //now add a transfer row for the transaction
        addTransaction(accountID,description,amount);
    }

    /**
     * this will add a transaction to the transaction database
     * @param accountNumber account number the transaction was made
     * @param transactionDescription the description on the transaction
     * @param amount amount that was going to go in the transaction
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public void addTransaction(int accountNumber, String transactionDescription, double amount)
            throws SQLException,ClassNotFoundException{
        Connection c = null;

        Class.forName("org.sqlite.JDBC");
        c = DriverManager.getConnection(connectionLink); //this will get the file in resources
        /*do other stuff here*/

        Statement statement = c.createStatement();
        statement.setQueryTimeout(30); // set timeout to 30 sec.

        //get the largest transaction number and add 1 to it
        ResultSet resultSet = statement.executeQuery("" +
                "SELECT MAX(TRANSACTION_NUMBER) as MAX" +
                "FROM TRANSACTIONS" +
                "WHERE ACCOUNT_NUMBER="+accountNumber+");");

        //used to save the number
        int newTransactionNumber = 0;

        while (resultSet.next()) {
            newTransactionNumber = resultSet.getInt("MAX")+1;
        }

        //used to get the real date for transaction time
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        String dateAndTime = dateFormat.format(cal.getTime());

        //will add the new transaction to the database
        statement.executeUpdate("INSERT INTO TRANSACTIONS VALUES ("+
                        newTransactionNumber+","+
                        transactionDescription+","+
                        amount+","+
                        dateAndTime+","+
                        accountNumber+");");

        c.close();

    }

    // Functions to make:
    //public validateUser(String user, String pass) - Crosby
    //public getUser(String username) - brad
    //  public payBill(int billID) -
    //  public transfer(int accountIDFrom, int accountIDTo,int amount) - Crosby
    //  public withdraw(int accountID, float amount, String Description) - Crosby
    //  public deposit(int accountID, float amount, String Descriptioin) - Crosby
    //private getAccounts(int userID) - Rudy
    //private getTransactions(int accountNumber) - Daniel
    //private getBills(int accountNumber) - Jesus
    //private addTransaction(int accountNumber,String Description, double transactionAmount,
    //                       String date) - Crosby

    public User getUser(String username) throws SQLException, ClassNotFoundException {
        Connection c = null;
        Statement stmt = null;
        Class.forName("org.sqlite.JDBC");
        c = DriverManager.getConnection("sdbc:sqlite::resource:GoTorosBank.db");

        stmt = c.createStatement();

        ResultSet resultSet = stmt.executeQuery("SELECT * FROM USERS;");
        while (resultSet.next()) {
            String userName = resultSet.getString("USERNAME");
            if (resultSet.getString("USERNAME").equals(username)) { //if the username is equal to the give, we have our user

                User user = new User(resultSet.getInt("UID"),
                        userName, resultSet.getString("FIRST_NAME"),
                        resultSet.getString("LAST_NAME"), null); //brad finish
                resultSet.close();
                stmt.close();
                c.close();

                return user;
            }
        }
        /*there was no user!*/
        return null;
    }
    private void getBills(int accountNumber) throws ClassNotFoundException, SQLException {
        ArrayList<Bill> Bills = new ArrayList();
        Class.forName("org.sqlite.JDBC");
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DriverManager.getConnection("jdbc:sqlite::resource:GoTorosBank.db"); //this will get the file in resources
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM BILLS");
            while (resultSet.next()) {

                int BillsID = resultSet.getInt("BID");
                String Bill_Name = resultSet.getString("BILL_NAME");
                String Bill_Description = resultSet.getString("BILL_DESCRIPTION");
                double Bill_Amount = resultSet.getDouble("BILL_AMOUNT");
                String Bill_Due_Date = resultSet.getString("BILL_DUE_DATE");
                String Bill_Status = resultSet.getString("BILL_STATUS");
                int Uid = resultSet.getInt("UID");
                int Account_Number = resultSet.getInt("ACCOUNT_NUMBER");

                Bill bill = new Bill(BillsID, Bill_Name, Bill_Description, Bill_Amount, Bill_Due_Date, Bill_Status, Uid, Account_Number);
                Bills.add(bill);
            }


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                resultSet.close();
                statement.close();
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}
