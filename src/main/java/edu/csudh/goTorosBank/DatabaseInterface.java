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
    
    public static void main (String [] args){
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:GoTorosBank.db");
            //System.out.println("Database opened successfully!");
        }catch (Exception e){
            System.err.println( e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("Database opened successfully!");
    }
}
