package edu.csudh.goTorosBank;

import junit.framework.TestCase;

import java.util.Date;

/**
 * Created by crosby on 10/25/16.
 */
public class TestBills extends TestCase{
    private Bill bill;
    private User user = new User(1,"Test","first","last");//new to make user to make account to add to bill
    private Account account = new Account(1,30,user,"Checking");//need to make account to test
    private Date d = new Date(2016,4,5);
    @Override
    protected void setUp() {
        bill = new Bill(1,"Bill Name","first bill",20,d,"Late",account);
    }
    public void testBillID(){
        assertTrue(bill.getBillID()== 1);
    }
    public void testAccount(){
        assertTrue(bill.AccountNumer()== account);

    }
    public void testBillDueDate(){
        assertTrue(bill.getBillDueDate()== d);

    }
    public void testBillAmount(){
        assertTrue(bill.getBillAmmount()== 20);

    }
    public void testBillName(){
        assertTrue(bill.getBillName()=="Bill Name");

    }
    public void testBillStatus(){
        assertTrue(bill.getBillStatus()=="Late");

    }
    public void testBillDescription(){
        assertTrue(bill.getBillDescaription()=="first bill");
    }
}
