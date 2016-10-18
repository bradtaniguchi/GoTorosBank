package edu.csudh.goTorosBank;

import junit.framework.TestCase;

/**
 * Created by Bradley Taniguchi
 */
public class TestUser extends TestCase{
    protected User nullUser;
    //protected User genericUser;


    @Override
    protected void setUp() {
        nullUser = new User(100, "toro@gmail.com", "toro", "last", null);
    }

    public void testId() {
        assertEquals(100, nullUser.getId());
    }
    public void testUserName() {
        assertEquals("toro@gmail.com", nullUser.getUserAccountName());
    }
    public void testFirstName() {
        assertEquals("toro", nullUser.getUserFirstName());
    }

}