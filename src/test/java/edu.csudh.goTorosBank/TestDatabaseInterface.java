package edu.csudh.goTorosBank;

import junit.framework.TestCase;

import java.sql.*;
import java.util.Date;
import java.text.ParseException;
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
        try {
            Connection c;
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite::resource:testGoTorosBank.db");
            Statement statement = c.createStatement();
            float newAmount = 100;
            c.setAutoCommit(false);
            //used to change the balance in the database
            statement.executeUpdate(
                    "UPDATE ACCOUNTS "+
                    "SET ACCOUNT_BALANCE="+ newAmount + " " +
                    "WHERE ACCOUNT_NUMBER="+ 1 +";");

            /*Remove any extra transactions from the test database.*/
            statement.executeUpdate(
                    "DELETE from TRANSACTIONS " +
                    "WHERE TRANSACTION_NUMBER > 2;");
            c.commit();
            statement.close();
            c.close();
        } catch (ClassNotFoundException e) {
            fail("Setup Database ERROR! " + e.getMessage());
        } catch (SQLException e ) {
            fail("Setup Database ERROR! " + e.getMessage());
        }
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


//            assertNotNull(acc.getBills());
            assertTrue(acc.getBills().size() == 1 || acc.getBills().size() == 0);
            if(acc.getBills().size() > 0) {
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
    }

    public void testWithdraw() {
        int accountID = 1; //test using the 1st account
        float amount = 30; //withdraw 30 dollars
        float endAmount = 70; //the amount we want at the end

        Connection c;
        Statement statement;
        ResultSet resultSet;
        try {
            database.withdraw(accountID, amount, "Test Withdraw");
        } catch (SQLException e) {
            fail("Database ERROR! " + e.getMessage());
        } catch (ClassNotFoundException e) {
            fail("Database ERROR! " + e.getMessage());
        }

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite::resource:testGoTorosBank.db");

            statement = c.createStatement();

            resultSet = statement.executeQuery(
                    "SELECT ACCOUNT_BALANCE " +
                    "FROM ACCOUNTS " +
                    "WHERE ACCOUNT_NUMBER=" + accountID+";");

            assertEquals(endAmount, resultSet.getFloat("ACCOUNT_BALANCE"));

            resultSet.close();
            statement.close();
            c.close();
        } catch (ClassNotFoundException e) {
            fail("Verify Database ERROR! " + e.getMessage());
        } catch(SQLException e) {
            fail("Verify Database ERROR! " + e.getMessage());
        }
    }

    public void testPayBill()
    {
        int BID = 5;
        float amount = 50;

        Connection c;
        Statement statement;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ResultSet resultSet;

        try
        {
            database.payBill(BID);
        }
        catch(ClassNotFoundException e)
        {
            fail("Database ERROR! " + e.getMessage());
        }
        catch (SQLException e)
        {
            fail("Database ERROR! " + e.getMessage());
        }
        catch (ParseException e)
        {
            fail("Database ERROR! " + e.getMessage());
        }

        try
        {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite::resource:testGoTorosBank.db");
            Date billDueDate = sdf.parse("1999-12-30 12:00:00");

            statement = c.createStatement();

            statement.executeUpdate("INSERT INTO BILLS(BID, BILL_NAME, BILL_DESCRIPTION, BILL_AMOUNT, " +
                    "BILL_DUE_DATE, BILL_STATUS, UID, ACCOUNT_NUMBER) VALUES" +
                    " ( 5, 'Bill TEST', 'Pay your bill', 50.00, " + billDueDate + ", 'active', 2, 3);");

            //resultSet.close();
            statement.close();
            c.close();
        }
        catch (ClassNotFoundException e)
        {
            fail("Verify Database ERROR! " + e.getMessage());
        }
        catch (SQLException e)
        {
            fail("Verify Database ERROR! " + e.getMessage());
        }
        catch (ParseException e)
        {
            fail("Verify Database ERROR! " + e.getMessage());
        }
    }
    /*public void testTransfer(){
        float endingTo = 120;
        float endingFrom = 80;
        int accountIDTo = 1;
        int accountIDFrom = 2;

        try {
            database.transfer(1,2,20);
        } catch (ClassNotFoundException e) {
            fail("Transfer ERROR! " + e.getMessage());
        } catch(SQLException e) {
            fail("Transfer ERROR! " + e.getMessage());
        }

        Connection c;
        Statement statement;
        ResultSet resultSet;

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite::resource:testGoTorosBank.db");

            statement = c.createStatement();

            resultSet = statement.executeQuery(
                    "SELECT ACCOUNT_BALANCE " +
                            "FROM ACCOUNTS " +
                            "WHERE ACCOUNT_NUMBER=" + accountIDTo+";");

            assertEquals(endingTo, resultSet.getFloat("ACCOUNT_BALANCE"));

            resultSet.close();
            statement.close();
            c.close();
        } catch (ClassNotFoundException e) {
            fail("Verify Database 1 ERROR! " + e.getMessage());
        } catch(SQLException e) {
            fail("Verify Database 1 ERROR! " + e.getMessage());
        }
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite::resource:testGoTorosBank.db");

            statement = c.createStatement();

            resultSet = statement.executeQuery(
                    "SELECT ACCOUNT_BALANCE " +
                            "FROM ACCOUNTS " +
                            "WHERE ACCOUNT_NUMBER=" + accountIDFrom+";");

            assertEquals(endingFrom, resultSet.getFloat("ACCOUNT_BALANCE"));

            resultSet.close();
            statement.close();
            c.close();
        } catch (ClassNotFoundException e) {
            fail("Verify Database 2 ERROR! " + e.getMessage());
        } catch(SQLException e) {
            fail("Verify Database 2 ERROR! " + e.getMessage());
        }
    }*/
    @Override
    public void tearDown() {
        /*Undo our changes, set value back to 100*/
        Connection c = null;
        Statement statement = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite::resource:testGoTorosBank.db");
            statement = c.createStatement();

            float newAmount = 100;
            c.setAutoCommit(false);
            //used to change the balance in the database
            statement.executeUpdate(
                    "UPDATE ACCOUNTS "+
                            "SET ACCOUNT_BALANCE="+ newAmount + " " +
                            "WHERE ACCOUNT_NUMBER="+ 1 +";");

            c.commit();
            statement.close();
            c.close();
        } catch (ClassNotFoundException e) {
            fail("Teardown Database ERROR! (Account1) " + e.getMessage());
        } catch (SQLException e ) {
            fail("Teardown Database ERROR! (Account1) " + e.getMessage());
        }

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite::resource:testGoTorosBank.db");
            statement = c.createStatement();

            float newAmount = 100;
            c.setAutoCommit(false);
            //used to change the balance in the database
            statement.executeUpdate(
                    "UPDATE ACCOUNTS "+
                            "SET ACCOUNT_BALANCE="+ newAmount + " " +
                            "WHERE ACCOUNT_NUMBER="+ 2 +";");
            c.commit();
            statement.close();
            c.close();
        } catch (ClassNotFoundException e) {
            fail("Teardown Database ERROR! (Account2) " + e.getMessage());
        } catch (SQLException e ) {
            fail("Teardown Database ERROR! (Account2) " + e.getMessage());
        }
        /*COMBINE THESE TWO AT A LATER TIME*/
        /*Change the transaction amount*/
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite::resource:testGoTorosBank.db");
            statement = c.createStatement();

            c.setAutoCommit(false);
            /*Remove any extra test statements*/
            statement.executeUpdate(
                    "DELETE from TRANSACTIONS " +
                            "WHERE TRANSACTION_NUMBER > 2;");

            c.commit();
            statement.close();
            c.close();
        } catch (ClassNotFoundException e) {
            fail("Teardown Database ERROR! (Transactions) " + e.getMessage());
        } catch (SQLException e ) {
            fail("Teardown Database ERROR! (Transactions) " + e.getMessage());
        }
    }
}
