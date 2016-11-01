package edu.csudh.goTorosBank;

import junit.framework.TestCase;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by brad on 10/19/16.
 * Tester class for the Database Interface
 * TODO: Add more test cases, each for a function.
 */
public class TestDatabaseInterface extends TestCase {
    private DatabaseInterface database;
    private User toro = new User(1, "toro", "toros", "toros");  //this is a user in the database
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
        User user = database.getUser("toro");//this user should have two accounts
        assertEquals(2, user.getUserAccounts().size()); //get the number of User accounts
        for (Account acc : user.getUserAccounts()) {
            assertEquals(user, acc.getUser()); //see if pointer is same

            /*these just see if values exist, need to change these to legit tests*/
            assertNotNull(acc.getAccountType());
            assertTrue(acc.getAccountType().contentEquals("checking") ||
                    acc.getAccountType().contentEquals("savings"));

            assertNotNull(acc.getAccountBalance());
            assertEquals(100f,acc.getAccountBalance());

            assertNotNull(acc.getAccountNumber());
            assertTrue(acc.getAccountNumber() == 1 ||
                    acc.getAccountNumber() == 2);

            assertEquals(1,acc.getTransactions().size());

            //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//dd/MM/yyyy

            for(Transaction x:acc.getTransactions()){
                assertEquals((float) 100.0,x.getTransactionAmount());
                assertTrue(x.getTransactionNumber() == 1||
                        x.getTransactionNumber() == 2);
                assertEquals("ADDED MONEY",x.getTransactionDescription());
                assertEquals("1999-12-30 12:00:00",x.getDate());
            }


            assertNotNull(acc.getBills());
            assertTrue(acc.getBills().size() == 1 || acc.getBills().size() == 0);
            if(acc.getBills().size() != 0) {
                for (Bill x : acc.getBills()) {
                    assertEquals(4, x.getBillID());
                    assertEquals("Eddison", x.getBillName());
                    assertEquals("your gass bill", x.getBillDescaription());
                    assertEquals(100.0f, x.getBillAmmount());
                    assertEquals("1999-12-30 12:00:00", x.getBillDueDate());
                    assertEquals("done", x.getBillStatus());
                    /*get user id not found*/
                }
            }
        }
    }

    /*TODO: This test is failing*/
    public void testDeposit(){
        int accountID = 1; //test using the 1st account
        float amount = 50; //amount we are going to add
        float endAmount = 150; //the amount we want at the end
        try {
            database.deposit(accountID, amount, "Test Deposit");
        } catch(SQLException e){
            fail("Database ERROR! " + e.getMessage());

        } catch(ClassNotFoundException e) {
            fail("Database ERROR! " + e.getMessage());
        }

        try {
            Connection c = null;
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite::resource:testGoTorosBank.db");
            c.setAutoCommit(true);
            Statement statement = c.createStatement();
            //statement.setQueryTimeout(30); // set timeout to 30 sec.
            ResultSet resultSet;

            resultSet = statement.executeQuery(
                    "SELECT ACCOUNT_BALANCE " +
                    "FROM ACCOUNTS " +
                    "WHERE ACCOUNT_NUMBER="+accountID+";");

            assertEquals(endAmount, resultSet.getFloat("ACCOUNT_BALANCE"));

            resultSet.close();
            statement.close();
            c.close();

        } catch (ClassNotFoundException e) {
            fail("Verify Database ERROR! " + e.getMessage());
        } catch (SQLException e ) {
            fail("Verify Database ERROR! " + e.getMessage());
        }

        /*Undo our changes, set value back to 100*/
        try {
            Connection c = null;
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite::resource:testGoTorosBank.db");
            c.setAutoCommit(true);
            Statement statement = c.createStatement();
            ResultSet resultSet;

            float newAmount = 100;

            //used to change the balance in the database
            statement.executeUpdate(
                    "UPDATE ACCOUNTS "+
                    "SET ACCOUNT_BALANCE="+ newAmount + " " +
                    "WHERE ACCOUNT_NUMBER="+ 1 +";");


            statement.close();
            c.close();
        } catch (ClassNotFoundException e) {
            fail("Verify Database ERROR! " + e.getMessage());
        } catch (SQLException e ) {
            fail("Verify Database ERROR! " + e.getMessage());
        }

        /*Change the transaction amount*/
        try {
            Connection c;
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite::resource:testGoTorosBank.db");
            c.setAutoCommit(true);
            Statement statement = c.createStatement();
            ResultSet resultSet;


            /*Remove any extra test statements*/
            statement.executeUpdate(
                            "DELETE from TRANSACTIONS " +
                            "WHERE TRANSACTION_NUMBER > 2;");


            statement.close();
            c.close();
        } catch (ClassNotFoundException e) {
            fail("Cleanup Database ERROR! " + e.getMessage());
        } catch (SQLException e ) {
            fail("Cleanup Database ERROR! " + e.getMessage());
        }
    }

    //public void testWithdraw() {
    //    int accountID = 1;
    //    float amount = 50; /*The amount we are going to add*/
    //    float endAmount = 50; /*The amount we want to end up with*/
    //}
}
