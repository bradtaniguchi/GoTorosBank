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
        //Statement stmt = null; // use this later

        Class.forName("org.sqlite.JDBC");
        c = DriverManager.getConnection(connectionLink); //this will get the file in resources
        /*do other stuff here*/

        Statement statement = c.createStatement();
        statement.setQueryTimeout(30); // set timeout to 30 sec.

        ResultSet resultSet = statement.executeQuery("SELECT * from USERS");

        System.out.println("Here are the users");

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

}

