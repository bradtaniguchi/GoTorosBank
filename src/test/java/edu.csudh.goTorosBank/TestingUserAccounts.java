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
}