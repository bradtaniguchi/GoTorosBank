package edu.csudh.goTorosBank;

import junit.framework.TestCase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import org.json.simple.JSONObject;

/**
 *
 * @author Jesus Cortez
 */
public class TestAccount extends TestCase {

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
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//dd/MM/yyyy
        Date d = new Date();
        SampleTransaction = new Transaction(TheYodaAccount, 1, 120.00f, "Food");
        TheYodaAccount.addTransaction(SampleTransaction);
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

    public void testToJSON() {
        HashMap sampleMap = new HashMap();
        sampleMap.put("accountNumber", 100);
        sampleMap.put("accountBalance", 900000000);
        sampleMap.put("user", Yoda);
        sampleMap.put("accountType", "LightSabers");

        HashMap testingValue = (JSONObject) TheYodaAccount.toJSON();

        assertEquals(sampleMap.get("accountNumber"), testingValue.get("accountNumber"));
        assertEquals(sampleMap.get("accountBalance"), testingValue.get("accountBalance"));
        //assertEquals(sampleMap.get("user"), testingValue.get("user"));
        assertEquals(sampleMap.get("accountType"), testingValue.get("accountType"));

    }
}