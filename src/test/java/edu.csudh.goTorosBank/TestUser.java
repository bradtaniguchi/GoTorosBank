package edu.csudh.goTorosBank;

import junit.framework.TestCase;
import java.util.ArrayList;
/**
 * Created by Bradley Taniguchi
 */
public class TestUser extends TestCase{
    protected User nullUser;
    protected User genericUser;
    protected Account basicAccount;
    protected ArrayList<Account> accounts;

    @Override
    protected void setUp() {
        nullUser = new User(100, "toro@gmail.com", "Toro", "Last", null);
        basicAccount = new Account(1, 1000, genericUser, null); //account with no bills, or
        accounts = new ArrayList<Account>();
        accounts.add(basicAccount);
        genericUser = new User(200, "joe@gmail.com", "Joe", "Blow", accounts);
    }

    public void testId() {
        assertEquals(100, nullUser.getId());
        assertEquals(200, genericUser.getId());
    }
    public void testUserName() {
        assertEquals("toro@gmail.com", nullUser.getUserAccountName());
        assertEquals("joe@gmail.com", genericUser.getUserAccountName());
    }
    public void testFirstName() {
        assertEquals("Toro", nullUser.getUserFirstName());
        assertEquals("Joe", genericUser.getUserFirstName());
    }
    public void testLastName() {
        assertEquals("Last", nullUser.getUserLastname());
        assertEquals("Blow", genericUser.getUserLastname());
    }

    /* TODO: Fix this once we can check if Accounts, Users are equal*/
    public void testGetAccounts() {
        assertNotNull(nullUser.getUserAccounts());
        ArrayList<Account> testAccounts = new ArrayList<Account>();
        Account testAccount = new Account(1, 1000, genericUser, null);
        testAccounts.add(testAccount);
        assertEquals(1, genericUser.getUserAccounts().size());
        /*add one where we test the account itself*/
    }
}