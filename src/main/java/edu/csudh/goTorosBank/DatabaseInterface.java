/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.csudh.goTorosBank;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import org.json.simple.JSONObject;
import java.sql.*;

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
     *  checks all users in the database and makes sure that the user name and password
     *  is inside the database
     * @param username the users name
     * @param userpassword the users password
     * @return true if users is inside the data base false if the user is not in the data base
     * @throws ClassNotFoundException checks if file is inside
     * @throws SQLException checks for sql exceptions
     */
    public boolean validate(String username, String userpassword)
            throws ClassNotFoundException, SQLException{

        Connection c = null;

        Class.forName("org.sqlite.JDBC");
        c = DriverManager.getConnection(connectionLink); //this will get the file in resources
        /*do other stuff here*/

        Statement statement = c.createStatement();
        statement.setQueryTimeout(30); // set timeout to 30 sec.

        ResultSet resultSet = statement.executeQuery("SELECT * from USERS");

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
    // Functions to make:
    //public validateUser(String user, String pass) - Crosby
    //public getUser(String username) - brad
    //  public payBill(int billID) -
    //  public transfer(int accountIDFrom, int accountIDTo) -
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

            }

        }
        resultSet.close();
        stmt.close();
        c.close();
    }
}

