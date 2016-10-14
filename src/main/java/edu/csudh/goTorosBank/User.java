package edu.csudh.goTorosBank;

import java.util.ArrayList;

/**
 * User - an account object. This class represents a User's financies.
 * */
public class User {
    private int id;
    private String userAccountName;
    private String userFirstName;
    private String userLastname;
    private ArrayList<Account> accounts;

    /**
     *  this has the users inforamtion for everything
     *  ... if user has one account it will run this constructer
     * @param id the id associated with the user
     * @param useraccountname the user name for the account
     * @param userfirstname the first name of the user
     * @param userlastname last name of the user
     * @param ac list of accounts
     */
    public User(int id, String useraccountname,String userfirstname,
                String userlastname,ArrayList<Account> ac) {
        this.id = id;
        this.userAccountName = useraccountname;
        this.userFirstName = userfirstname;
        this.userLastname = userlastname;
        this.accounts = ac;
    }

    /**
     * getter for the getID
     * @return
     */
    public int getId() {
        return id;
    }

    public String getUserAccountName() {
        return userAccountName;
    }

    public String getUserFirstName() {
        return userFirstName;
    }

    public String getUserLastname() {
        return userLastname;
    }

    public ArrayList<Account> getUserAccounts() { return accounts; }
}
