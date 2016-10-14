/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.csudh.goTorosBank;

import java.sql.*;
import java.util.ArrayList;

/**
 *
 * @author Rudy
 */
public class DatabaseInterface {

    private String connectionLink = "jdbc:sqlite::resource:GoTorosBank.db";

    public DatabaseInterface() {
    }

    // this is the tester for the main class
    /*
     public static void main(String[] args) throws SQLException, ClassNotFoundException{
     System.out.println(validate("toro","password"));
     }
     */
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



        ResultSet resultSet = statement.executeQuery("SELECT * FROM ");

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

    /**
     * just uses the withdraw and deposit finction to make a transaction
     *
     * @param accountIDFrom the id that the money is comming from
     * @param accountIDTo the id for the account that the money is comming from
     * @param amount the amount of money that will be passed
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public void transfer(int accountIDFrom, int accountIDTo,int amount) throws SQLException,ClassNotFoundException{
        withdaw(accountIDFrom,amount);
        deposit(accountIDTo,amount);
    }

    // Functions to make:
    //public validateUser(String user, String pass) - Crosby
    //public getUser(String username) - brad
    //  public payBill(int billID) -
    //  public transfer(int accountIDFrom, int accountIDTo,int amount) - Crosby
    //  public withdraw(int accountID, float amount) -
    //  public deposit(int accountID, float amount) -
    //private getAccounts(int userID) - Rudy
    //private getTransactions(int accountNumber) - Daniel
    //private getBills(int accountNumber) - Jesus

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
