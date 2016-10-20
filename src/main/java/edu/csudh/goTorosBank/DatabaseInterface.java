package edu.csudh.goTorosBank;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

// Functions to make:
//public validateUser(String user, String pass) - Crosby [NEEDS TESTING]
//public getUser(String username) - brad [NEEDS TESTING]
//  public payBill(int billID) -
//  public transfer(int accountIDFrom, int accountIDTo,int amount) - Crosby [NEEDS TESTING]
//  public withdraw(int accountID, float amount, String Description) - Crosby [NEEDS TESTING]
//  public deposit(int accountID, float amount, String Descriptioin) - Crosby [NEEDS TESTING]
//private getAccounts(int userID) - Rudy [NEEDS TESTING]
//private getTransactions(int accountNumber) - Daniel [TODO: Function doesn't exist. ]
//private getBills(int accountNumber) - Jesus [TODO: See Rudy's, the function doesn't return a Bill Object]
//private addTransaction(int accountNumber,String Description, double transactionAmount, String date)-Crosby [NEEDS TESTING]

/**
 *
 * @authors Rudy, Daniel, Bradley, Crosby, Jesus
 */
public class DatabaseInterface {

    private String connectionLink;

    public DatabaseInterface() {
        this.connectionLink = "jdbc:sqlite::resource:GoTorosBank.db";
    }
    public DatabaseInterface(String connectionLink) {
        this.connectionLink = connectionLink;
    }

    /**
     * checks all users in the database and makes sure that the user name and
     * password is inside the database
     *
     * @param username the users name
     * @param userpassword the users password
     * @return true if users is inside the data base false if the user is not in
     * the data base
     * @throws ClassNotFoundException checks if file is inside
     * @throws SQLException checks for sql exceptions
     */
    public boolean validate(String username, String userpassword){
        Connection c = null;
        try {

            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection(connectionLink); //this will get the file in resources
        /*do other stuff here*/

            Statement statement = c.createStatement();
            statement.setQueryTimeout(30); // set timeout to 30 sec.

            ResultSet resultSet = statement.executeQuery("SELECT * FROM USERS");

            while (resultSet.next()) {
                // iterate & read the result set
                String usname = resultSet.getString("USERNAME");
                String uspass = resultSet.getString("PASSWD");

                if (username.contentEquals(usname) && userpassword.contentEquals(uspass)) {
                    c.close();
                    return true;
                }
            }
            c.close();
        }catch(SQLException s){
            System.out.println("sql problem");
            try {
                c.close();
            }catch (SQLException sql2){
                System.out.println("sql problem in closing");
            }
        }catch (ClassNotFoundException cnf){
            System.out.println("class not found problem");
            try{
            c.close();
            }catch (SQLException sql2){
                System.out.println("sql problem in closing");
            }
        }
        return false;
    }

    /**
     * This function finds all accounts tied to a User. It takes a User class, and get it's ID.
     * Then it checks the database for accounts tied to that ID
     * @param parentUser A User object that is used to get the ID.
     * @throws SQLException this will be caught by the servlet class
     * @throws ClassNotFoundException this will be caught by the servlet class
     */
    public ArrayList<Account> getAccounts(User parentUser) throws SQLException, ClassNotFoundException{
        Connection c = null;
        Statement stmt = null;
        ResultSet resultSet = null;
        ArrayList<Account> accounts = new ArrayList<Account>();
        
        Class.forName("org.sqlite.JDBC");
        c = DriverManager.getConnection(connectionLink);
       
        stmt = c.createStatement();
        /*resultSet = stmt.executeQuery("SELECT * ACCOUNTS, USER"
                                    + "WHERE ACCOUNTS.UID = USER.UID;");
                                    */
        resultSet = stmt.executeQuery("SELECT * FROM ACCOUNTS");
        while (resultSet.next()){
            int id = resultSet.getInt("UID");
            if(parentUser.getId() == id) {
                int accountNumber = resultSet.getInt("ACCOUNT_NUMBER");
                String accountType = resultSet.getString("ACCOUNT_TYPE");
                int accountBalance = resultSet.getInt("ACCOUNT_BALANCE");
                Account account = new Account(accountNumber, accountBalance, parentUser, accountType);
                /*TODO: These need to be tested!*/
                //account.addTransactions(getTransactions(account)); //get the transactions for this Account
                //account.addBills(getBills(account)); //get the bills for this account
                accounts.add(account);
            }
        }
        resultSet.close();
        stmt.close();
        c.close();
        return accounts;
    }

    public ArrayList<Transaction> getTransactions(Account AccountNumber) throws SQLException, ClassNotFoundException
    {
        Connection dbConnection;
        Statement sqlQuery;
        ResultSet result = null;

        int tNumber;
        String tDescription;
        float tAmount;
        String tDate;
        int aNumber;

        ArrayList<Transaction> transactions = new ArrayList<Transaction>();

        Class.forName("org.sqlite.JDBC");
        dbConnection = DriverManager.getConnection(connectionLink);

        sqlQuery = dbConnection.createStatement();
        result = sqlQuery.executeQuery("SELECT * FROM TRANSACTIONS WHERE ACCOUNT_NUMBER = " + AccountNumber + ";");

        while(result.next())
        {
            aNumber = result.getInt("ACCOUNT_NUMBER");
            if(AccountNumber.getAccountNumber() == aNumber){
                
              tNumber = result.getInt("TRANSACTION_NUMBER");
            tDescription = result.getString("TRANSACTION_DESCRIPTION");
            tAmount = result.getFloat("TRANSACTION_AMOUNT");
            tDate = result.getString("TRANSACTION_DATE");

            // TODO: Fix yo constructor, not compatible to my specifications. - Daniel
            // Fixed -Rudy
            Transaction transaction = new Transaction(AccountNumber, tNumber, tAmount, tDescription);

            transactions.add(transaction);  
            }
            
        }

        result.close();
        sqlQuery.close();
        dbConnection.close();

        return transactions;
    }

    /**
     * enter the account number and the account number you want to send it to 
     * and the amount and everything is done
     * 
     * @accountIDFrom the account number that you are sending it from
     * @accountIDTo the account number you are sending it to 
     * @amount the amount of money being sent
     */
    public void transfer(int accountIDFrom, int accountIDTo,double amount)
            throws SQLException,ClassNotFoundException{
        withdraw(accountIDFrom,amount,"Tansfer withdraw to account number: "+accountIDTo);
        deposit(accountIDTo,amount,"Tansfer deposit from account number: "+accountIDFrom);
    }

    /**
     * enter information and the withdrawl will be mad all you have to do is enter account
     * ID amount and description
     *
     * @param accountIDFrom ID of the account that is being withdrew from
     * @param amount amount of money being transfered
     * @param description description of transfer
     * @throws SQLException this will be caught by the servlet class
     * @throws ClassNotFoundException this will be caught by the servlet class
     */
    public void withdraw(int accountIDFrom,double amount, String description)
            throws SQLException,ClassNotFoundException {
        int accountbalance;
        Connection c = null;

        Class.forName("org.sqlite.JDBC");
        c = DriverManager.getConnection(connectionLink); //this will get the file in resources
        /*do other stuff here*/

        Statement statement = c.createStatement();
        statement.setQueryTimeout(30); // set timeout to 30 sec.
        ResultSet resultSet;

        //get custumers account balance
        resultSet = statement.executeQuery(
                "SELECT ACCOUNT_BALANCE " +
                "FROM ACCOUNTS " +
                "WHERE ACCOUNT_NUMBER="+accountIDFrom+";");

        accountbalance = resultSet.getInt("ACCOUNT_BALANCE");

        //subtract ammount from balance
        accountbalance-=amount;

        //used to change the balance in the database
        statement.executeUpdate(
                "UPDATE ACCOUNTS"+
                "SET ACCOUNT_BALANCE="+ accountbalance +
                "WHERE ACCOUNT_NUMBER="+accountIDFrom+";");

        c.close();

        //now add a transfer row for the transaction
        addTransaction(accountIDFrom,description,amount);
    }

    /**
     *  enter the information below and it will automaticly change account balance
     *  update the account and a transaction will be made.
     *
     * @param accountNumber account ID
     * @param amount amount to be deposited
     * @param description description
     * @throws SQLException this will be caught by the servlet class
     * @throws ClassNotFoundException this will be caught by the servlet class
     */
    public void deposit(int accountNumber, double amount, String description)throws SQLException, ClassNotFoundException{
        int accountbalance;
        Connection c = null;

        Class.forName("org.sqlite.JDBC");
        c = DriverManager.getConnection(connectionLink); //this will get the file in resources
        
        Statement statement = c.createStatement();
        statement.setQueryTimeout(30); // set timeout to 30 sec.
        ResultSet resultSet;

        //get custumers account balance
        resultSet = statement.executeQuery(
                "SELECT ACCOUNT_BALANCE " +
                        "FROM ACCOUNTS " +
                        "WHERE ACCOUNT_NUMBER="+accountNumber+";");

        accountbalance = resultSet.getInt("ACCOUNT_BALANCE");

        //add ammount from balance
        accountbalance+=amount;

        //used to change the balance in the database
        statement.executeUpdate(
                "UPDATE ACCOUNTS"+
                        "SET ACCOUNT_BALANCE="+ accountbalance +
                        "WHERE ACCOUNT_NUMBER="+accountNumber+";");

        c.close();

        //now add a transfer row for the transaction
        addTransaction(accountNumber,description,amount);
    }

    /**
     * this will add a transaction to the transaction database
     * @param accountNumber account number the transaction was made
     * @param transactionDescription the description on the transaction
     * @param amount amount that was going to go in the transaction
     * @throws SQLException this will be caught by the servlet class
     * @throws ClassNotFoundException this will be caught by the servlet class
     */
    public void addTransaction(int accountNumber, String transactionDescription, double amount)
            throws SQLException,ClassNotFoundException{
        Connection c = null;

        Class.forName("org.sqlite.JDBC");
        c = DriverManager.getConnection(connectionLink); //this will get the file in resources
        /*do other stuff here*/

        Statement statement = c.createStatement();
        statement.setQueryTimeout(30); // set timeout to 30 sec.

        //get the largest transaction number and add 1 to it
        ResultSet resultSet = statement.executeQuery("" +
                "SELECT MAX(TRANSACTION_NUMBER) as MAX" +
                "FROM TRANSACTIONS" +
                "WHERE ACCOUNT_NUMBER="+accountNumber+");");

        //used to save the number
        int newTransactionNumber = 0;

        while (resultSet.next()) {
            newTransactionNumber = resultSet.getInt("MAX")+1;
        }

        //used to get the real date for transaction time
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        String dateAndTime = dateFormat.format(cal.getTime());

        //will add the new transaction to the database
        statement.executeUpdate("INSERT INTO TRANSACTIONS VALUES ("+
                        newTransactionNumber+","+
                        transactionDescription+","+
                        amount+","+
                        dateAndTime+","+
                        accountNumber+");");

        c.close();

    }

    /**
     * This function will take a username string, which it will look for in the database, and return a
     * User object, with a list of Accounts tied to that user.
     * If the user name isn't found, the function will return a null value.
     *
     * @param username the username of the user, ie "toro", a unique ID
     * @return User object, or null value
     * @throws SQLException this will be caught by the servlet class
     * @throws ClassNotFoundException this will be caught by the servlet class
     */
    public User getUser(String username) throws SQLException, ClassNotFoundException {
        Connection c = null;
        Statement stmt = null;
        Class.forName("org.sqlite.JDBC");
        c = DriverManager.getConnection(connectionLink);

        stmt = c.createStatement();
        stmt.setQueryTimeout(30); // set timeout to 30 sec.

        ResultSet resultSet = stmt.executeQuery("SELECT * FROM USERS");
        while (resultSet.next()) {
            String databaseUserName = resultSet.getString("USERNAME");
            if (username.contentEquals(databaseUserName)) { //if the username is equal to the given, we have our user

                User user = new User(resultSet.getInt("UID"),
                        databaseUserName, resultSet.getString("FIRST_NAME"),
                        resultSet.getString("LAST_NAME"), null);

                user.addAccounts(getAccounts(user)); //call the private getAccounts function

                resultSet.close();
                stmt.close();
                c.close();

                return user;
            }
        }
        /*there was no user!*/
        return null;
    }

    /**
     * This function will take the account number for an account, then it will look inside of the ACCOUNT table
     * for the first instance of the accountNumber.
     *
     * @param account the account number for the account inside of the ACCOUNT table, a unique ID.
     * @throws ClassNotFoundException this will be caught by the servlet class
     * @throws SQLException this will be caught by the servlet class
     * TODO: Updated with changes to Accounts holding a list of Bills due.
     */
    public ArrayList<Bill> getBills(Account account) throws ClassNotFoundException, SQLException {
        ArrayList<Bill> Bills = new ArrayList<Bill>();
        Class.forName("org.sqlite.JDBC");
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        //try { // we don't need this as the exceptions will be caught outside of the function
        connection = DriverManager.getConnection(connectionLink); //this will get the file in resources
        statement = connection.createStatement();
        resultSet = statement.executeQuery("SELECT * FROM BILLS");
        while (resultSet.next()) {

            int BillsID = resultSet.getInt("BID");
            String Bill_Name = resultSet.getString("BILL_NAME");
            String Bill_Description = resultSet.getString("BILL_DESCRIPTION");
            double Bill_Amount = resultSet.getDouble("BILL_AMOUNT");
            String Bill_Due_Date = resultSet.getString("BILL_DUE_DATE");
            String Bill_Status = resultSet.getString("BILL_STATUS");
            int Uid = resultSet.getInt("UID");
            int Account_Number = resultSet.getInt("ACCOUNT_NUMBER");

            Bill bill = new Bill(BillsID, Bill_Name, Bill_Description, Bill_Amount, Bill_Due_Date, Bill_Status, account);
            Bills.add(bill);
        }
        resultSet.close();
        statement.close();
        connection.close();

        return null; //TODO: Update this
    }
}
