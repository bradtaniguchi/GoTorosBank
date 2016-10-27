package edu.csudh.goTorosBank;

import junit.framework.TestCase;
import org.json.simple.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by Bradley Taniguchi
 */
public class testTransaction extends TestCase {
    private User user;
    private Account account;
    private Transaction transaction; //this is what we are testing
    private ArrayList<Account> accounts;
    private ArrayList<Transaction> transactions;
    private Date d = new Date(2016,4,5);
    private SimpleDateFormat sdf;

    @Override
    protected void setUp() {
        account = new Account(0, 100, user, "someType", transactions); //no bills, as we aren't testing that
        accounts = new ArrayList<Account>();
        accounts.add(account); /*now actually add */
        transaction = new Transaction(account, 1, 100, "description",d); //main thing we are testing
        user = new User(100, "joeblow@gmail.com",  "Joe", "Blow", accounts);
        sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//dd/MM/yyyy;


    }

    public void testGetAccount() {
        Account testAccount = transaction.getAccount();
        assertSame(testAccount, account);
    }

    public void testGetDate() {
        assertTrue(transaction.getDate().equals(sdf.format(d)));
    }

    public void testGetTransactionAmount() {
        float testAmount = 100;
        assertEquals(testAmount, transaction.getTransactionAmount());
    }

    public void testGetTransactionDescription() {
        String testDescription = "description";
        assertEquals(testDescription, transaction.getTransactionDescription());
    }

    public void testGetTransactionNumber() {
        int testNumber = 1;
        assertEquals(testNumber, transaction.getTransactionNumber());
    }
    @SuppressWarnings("unchecked")
    public void testToJSON() {
        HashMap testMap = new HashMap();
        testMap.put("transactionNumber", 1);
        testMap.put("transactionAmount", 100.0);
        testMap.put("transactionDate", d);
        testMap.put("transactionDescription", "description");

        HashMap valuesToTest = (JSONObject) transaction.toJSON();
        assertEquals(testMap.get("transactionNumber"), valuesToTest.get("transactionNumber"));

        /*some odd stuff is happening with the floats, checking strings instead...*/
        assertEquals(testMap.get("transactionAmount").toString(), valuesToTest.get("transactionAmount").toString());
        assertEquals(testMap.get("transactionDate"), valuesToTest.get("transactionDate"));
        assertEquals(testMap.get("transactionDescription"), valuesToTest.get("transactionDescription"));

    }
}
