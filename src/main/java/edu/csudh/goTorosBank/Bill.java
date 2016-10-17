package edu.csudh.goTorosBank;

/**
 * Created by crosby on 10/13/16.
 *
 */
public class Bill {
    private int bId;
    private String bill_name;
    private String bill_description;
    private double bill_amount;
    private String bill_due_date;
    private String bill_status;
    private int uID;
    private int account_number;

    public Bill(int bId, String bill_name, String bill_description, double bill_amount,
                String bill_due_date, String bill_status, int uID, int account_number) {
        this.bId = bId;
        this.bill_name = bill_name;
        this.bill_description = bill_description;
        this.bill_amount = bill_amount;
        this.bill_due_date = bill_due_date;
        this.bill_status = bill_status;
        this.uID = uID;
        this.account_number = account_number;
    }

    public int getBillID(){
        return bId;
    }
    
    public String getBillName(){
    return bill_name;
    }
    
    public String getBillDescaription(){
        return bill_description;
    }
    
    public double getBillAmmount(){
        return bill_amount;
    }
    
    public String getBillDueDate(){
        return bill_due_date;
    }
    
    public String getBillStatus(){
        return bill_status;
    }
    
    public int getUID(){
    return uID;
    }
    
    public int AccountNumer(){
        return account_number;
    }
}
