package edu.csudh.goTorosBank;

import java.util.ArrayList;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;

/**
 * User - an account object. This class represents a User's finances.
 * */
public class User {
    private int id;
    private String userAccountName;
    private String userFirstName;
    private String userLastName;
    private ArrayList<Account> accounts;

    /**
     *  this has the users inforamtion for everything
     *  ... if user has one account it will run this constructer
     * @param id the id associated with the user
     * @param userAccountName the user name for the account
     * @param userFirstName the first name of the user
     * @param userLastName last name of the user
     * @param ac ArrayList of accounts
     */
    public User(int id, String userAccountName, String userFirstName,
                String userLastName, ArrayList<Account> ac) {
        this.id = id;
        this.userAccountName = userAccountName;
        this.userFirstName = userFirstName;
        this.userLastName = userLastName;
        if(ac == null) {
            this.accounts = new ArrayList<Account>();
        } else {
            this.accounts = ac;
        }
    }

    /**
     * Secondary Constructor, creates a user with no Accounts attached to it.
     * @param id the id associated with the user
     * @param userAccountName the user name of the account
     * @param userFirstName the first name of the user
     * @param userLastName last name of the user
     */
    public User(int id, String userAccountName, String userFirstName,
                String userLastName) {
        this(id, userAccountName, userFirstName, userLastName, null);
    }
    /**
     * Adds an Account to the User
     * @param ac a single account you want to add to the User class
     */
    public void addAccount(Account ac) {
        this.accounts.add(ac);
    }

    /**
     * Transfers the current object to a JSON format
     * @return user object in JSONObject format.
     */
    @SuppressWarnings("unchecked")
    public JSONObject toJSON() {
        JSONObject jsonUser = new JSONObject();
        JSONArray jsonAccounts = new JSONArray();

        jsonUser.put("id", this.id);
        jsonUser.put("userAccountName", this.userAccountName);
        jsonUser.put("userFirstName", this.userFirstName);
        jsonUser.put("userLastName", this.userLastName);
        /*setup the accounts for the user*/
        for (Account acc : accounts) {
            jsonAccounts.add(acc.toJSON()); //add an account to our accounts jsonArray
        }
        jsonUser.put("accounts", jsonAccounts);
        return jsonUser;
    }
    @Override
    public boolean equals(Object obj) {
        if(obj == null) return false;
        if(obj == this) return true;
        if(obj instanceof User) return false;
        return false; //idk when we would get here
    }
    public boolean equals(User user) {
        return (user.getId() == this.id);
    }
    /**
     * Appends a list of existing Accounts to the user
     * @param accounts an ArrayList of accounts you want to add to the User class
     */
    public void addAccounts(ArrayList<Account> accounts) {
        this.accounts.addAll(accounts);
    }
    /**
     * getter for the getID
     * @return id of the user
     */
    public int getId() {
        return id;
    }

    /**
     * getter for the
     * @return user name of the user
     */
    public String getUserAccountName() {
        return userAccountName;
    }

    /**
     * getter for the user's first name
     * @return always returns bob, JK returns the first name
     */
    public String getUserFirstName() {
        return userFirstName;
    }

    /**
     * getter for the user's last name
     * @return returns user's last name
     */
    public String getUserLastName() {
        return userLastName;
    }

    /**
     * getter for the user accounts, if the User has no accounts, it returns null.
     * @return an arrayList of all the accounts tied to this user
     */
    public ArrayList<Account> getUserAccounts() { return accounts; }
}
