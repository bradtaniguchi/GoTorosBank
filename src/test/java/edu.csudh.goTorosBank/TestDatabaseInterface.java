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

    @Override
    protected void setUp() {
        database = new DatabaseInterface("jdbc:sqlite::resource:testGoTorosBank.db"); //use test database
    }

    public void testValidate() throws Exception {
        assertEquals(true, database.validate("toro", "password")); //user exists and password is right
        assertEquals(false, database.validate("toro", "NOTTHEPASSWOD")); //user exists, but password is wrong
        assertEquals(false, database.validate("blowJoe", "blah")); //this user doesn't exit at all
    }

    public void testGetUser() throws Exception {
        assertNotNull(database.getUser("toro"));
        assertNotNull(database.getUser("toro2"));

        /*Check if user is returned correctly with another test case checking accounts*/
    }
}
