package edu.csudh.goTorosBank;

import junit.framework.TestCase;
import java.util.ArrayList;

/**
 *
 * @author Jesus Cortez
 */
public class TestingUserAccounts extends TestCase {

    protected User Dummy;
    protected User Yoda;
    protected Account DummyAccount1;
    protected Account DummyAccount2;
    protected Account TheYodaAccount;
    protected ArrayList<Account> accounts;
    protected ArrayList<Account> yodaAccounts;
    protected Transaction SampleTransaction;

    @Override
    protected void setUp() {

        accounts = new ArrayList<Account>();
        Dummy = new User(100, "jeus@gmail.com", "Jeus", "Cortez", accounts);
        DummyAccount1 = new Account(900, 100000, Dummy, "gambling"); //account 1
        DummyAccount2 = new Account(310, 2000, Dummy, "savings"); //account 2        
        accounts.add(DummyAccount1);
        accounts.add(DummyAccount2);
        //Dummy has two accounts: gambling and savings
        yodaAccounts = new ArrayList<Account>();
        Yoda = new User(1, "UseTheForce@StarWars.com", "Yoda", "YoMama", yodaAccounts);
        TheYodaAccount = new Account(100, 900000000, Yoda, "LightSabers");
        yodaAccounts.add(TheYodaAccount);
        //Yoda has one account: LightSabers
        SampleTransaction = new Transaction(TheYodaAccount, 1, 120.00f, "May 18 2010", "Food");
        TheYodaAccount.addTransaction(SampleTransaction);
    }

    public void testId() {
        assertEquals(100, Dummy.getId());
        assertEquals(1, Yoda.getId());
    }

    public void testFirstName() {
        assertEquals("Jeus", Dummy.getUserFirstName());
        assertEquals("Yoda", Yoda.getUserFirstName());
    }

    public void testLastName() {
        assertEquals("Cortez", Dummy.getUserLastName());
        assertEquals("YoMama", Yoda.getUserLastName());
    }

    public void testAccounts() {
        //checks for AccountName to not be null
        assertNotNull(Dummy.getUserAccountName());
        assertNotNull(Yoda.getUserAccountName());
        //attempt to verify that 2 accounts exists for Dummy
        assertEquals(2, Dummy.getUserAccounts().size());
        //attempt to verify that 1 account exists for Yoda
        assertEquals(1, Yoda.getUserAccounts().size());
    }

    public void testAccountNumber() {
        assertEquals(900, DummyAccount1.getAccountNumber());
        assertEquals(310, DummyAccount2.getAccountNumber());
        assertEquals(100, TheYodaAccount.getAccountNumber());
    }

    public void testAccountType() {
        assertEquals("gambling", DummyAccount1.getAccountType());
        assertEquals("savings", DummyAccount2.getAccountType());
        assertEquals("LightSabers", TheYodaAccount.getAccountType());
    }

    public void testAccountBalance() {
        assertEquals(100000, DummyAccount1.getAccountBalance());
        assertEquals(2000, DummyAccount2.getAccountBalance());
        assertEquals(900000000, TheYodaAccount.getAccountBalance());
    }

    public void testRetrievingUser() {
        assertEquals(Dummy, DummyAccount1.getUser());
        assertEquals(Dummy, DummyAccount2.getUser());
        assertEquals(Yoda, TheYodaAccount.getUser());
    }

    public void testTransactions() {
        //checks for list to not be null
        assertNotNull(TheYodaAccount.getTransactions());

        //checks that the trasnaction is inside the list
        assertEquals(1, TheYodaAccount.getTransactions().size());
    }
}