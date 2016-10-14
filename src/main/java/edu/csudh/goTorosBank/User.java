package edu.csudh.goTorosBank;

/**
 * User - an account object. This class represents a User's financies.
 * */
public class User {
    private int id;
    private String userAccountName;
    private String userFirstName;
    private String userLastname;

    public User(int id, String useraccountname,String userfirstname,
                String userlastname) {
        this.id = id;
        this.userAccountName = useraccountname;
        this.userFirstName = userfirstname;
        this.userLastname = userlastname;
    }

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
}
