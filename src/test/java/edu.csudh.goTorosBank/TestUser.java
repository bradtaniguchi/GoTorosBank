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
        assertEquals("Last", nullUser.getUserLastName());
        assertEquals("Blow", genericUser.getUserLastName());
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
    
    public void testToJSON(){
        Map mapUser = new HashMap();
        JSONArray jsonAccounts = new JSONArray();
        mapUser.put("id", 100);
        mapUser.put("userAccountName", "toro@gmail.com");
        mapUser.put("userFisrtName","Toro");
        mapUser.put("accounts", jsonAccounts);
        mapUser.put("userLastName", "Last");
        
        for(Object keyObj : mapUser.keySet()){
            String key = keyObj.toString();
            System.out.println(key + " - " + mapUser.get(key));
        }
        
        System.out.println(mapUser.toString());
        //System.out.println(nullUser.toJSON().toString());
        //assertTrue(user.toString().equals(nullUser.toJSON().toString()));
        
        
        
}
}
