package edu.csudh.goTorosBank;
import java.sql.*;

/**
 * Created by brad on 10/7/16.
 * https://www.tutorialspoint.com/sqlite/sqlite_java.htm
 *
 * http://stackoverflow.com/questions/17907095/access-to-web-inf-in-a-java-rest-webservice
 */
public class DatabaseTest {
    private Connection c;
    public DatabaseTest() {
        try {
            Class.forName("org.sqlite.JDBC");
            this.c = DriverManager.getConnection("jdbc:sqlite:test.db");
            //getServletContext.getRealPath("/WEB-INF/database");
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
        System.out.println("Opened database successfully");
    }
}
