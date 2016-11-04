package edu.csudh.goTorosBank;

import org.json.simple.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Keeps track of all Billing information.
 * Information like bill id, bill name, bill description, bill amount, bill due date and bill status
 * and can have the account that is associated with it
 * Created by crosby on 10/13/16.
 */

public class Bill {
    private int billID;
    private String billName;
    private String billDescription;
    private float billAmount;
    private Date billDueDate; //CHANGE THIS TO A FORMATTED DATE Done
    private String billStatus; //remove this later, replace with enum
    private SimpleDateFormat sdf;
    /*Change below to enums*/
    //private enum billStatus { PAID, UNPAID, PENDING }; //the 3 different possibilities
    //private int uID;
    private Account account;

    public Bill(int billID, String billName, String billDescription, float billAmount,
                Date billDueDate, String billStatus, Account account) {
        this.billID = billID;
        this.billName = billName;
        this.billDescription = billDescription;
        this.billAmount = billAmount;
        this.billDueDate = billDueDate;//we need to ask the user for a due date
        this.billStatus = billStatus;
        //this.uID = uID;
        this.account= account;
        sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//dd/MM/yyyy
    }

    @SuppressWarnings("unchecked")
    public JSONObject toJSON() {
        JSONObject bill = new JSONObject();
        bill.put("billID", this.billID);
        bill.put("billName", this.billName);
        bill.put("billAmount", this.billAmount);
        bill.put("billStatus", this.billStatus);
        bill.put("billDueDate", sdf.format(this.billDueDate)); //this needs to be a date!
        return bill;
    }
    /* getters */
    public int getBillID(){
        return billID;
    }
    public String getBillName(){
    return billName;
    }
    public String getBillDescaription(){
        return billDescription;
    }
    public float getBillAmmount(){
        return billAmount;
    }

    public String getBillDueDate(){
        /*return formatted string, instead of Date*/
        return sdf.format(billDueDate);
    }
    public String getBillStatus(){
        return billStatus;
    }
    public Account getAccount(){
        return account;
    }
}
