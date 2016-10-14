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
    public DatabaseInterface() throws Exception { //specify Exception
        Connection c = null;

        Class.forName("org.sqlite.JDBC");
        c = DriverManager.getConnection("jdbc:sqlite::resource:GoTorosBank.db"); //this will get the file in resources
        /*do other stuff here*/
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
}

