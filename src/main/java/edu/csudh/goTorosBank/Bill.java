package edu.csudh.goTorosBank;

/**
 * Created by crosby on 10/13/16.
 *
 */
public class Bill {
    private int billID;
    private String billName;
    private String billDescription;
    private double billAmount;
    private String billDueDate; //CHANGE THIS TO A FORMATTED DATE
    private String billStatus; //remove this later, replace with enum
    /*Change below to enums*/
    //private enum billStatus { PAID, UNPAID, PENDING }; //the 3 different possibilities
    //private int uID;
    private Account account;

    public Bill(int billID, String billName, String billDescription, double billAmount,
                String billDueDate, String billStatus, Account account) {
        this.billID = billID;
        this.billName = billName;
        this.billDescription = billDescription;
        this.billAmount = billAmount;
        this.billDueDate = billDueDate;
        this.billStatus = billStatus;
        //this.uID = uID;
        this.account= account;
    }

    public int getBillID(){
        return billID;
    }
    
    public String getBillName(){
    return billName;
    }
    
    public String getBillDescaription(){
        return billDescription;
    }
    
    public double getBillAmmount(){
        return billAmount;
    }
    
    public String getBillDueDate(){
        return billDueDate;
    }
    
    public String getBillStatus(){
        return billStatus;
    }

    public Account AccountNumer(){
        return account;
    }
}
