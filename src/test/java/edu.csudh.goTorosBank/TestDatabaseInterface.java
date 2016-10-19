package edu.csudh.goTorosBank;

import junit.framework.TestCase;
import java.util.ArrayList;

/**
 * Created by brad on 10/19/16.
 * Tester class for the Database Interface
 * TODO: Add more test cases, each for a function.
 */
public class TestDatabaseInterface extends TestCase {
    private DatabaseInterface database;
    private String testDatabasePath;
    private String toroUser;
    private String toroPassword;

    @Override
    protected void setUp() {
        toroUser = "toro";
        toroPassword = "password";
        testDatabasePath = "jdbc:sqlite::resource:testGoTorosBank.db"; //utilize the TEST database
        database = new DatabaseInterface(testDatabasePath);
    }

    public void testGetUser() throws Exception{
        assertNotNull(database.getUser("toro"));
        assertNotNull(database.getUser("toro2"));
        assertNotNull(database.getUser("crosby"));
        assertNotNull(database.getUser("brad"));
    }

}
