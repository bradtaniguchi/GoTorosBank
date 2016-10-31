package edu.csudh.goTorosBank;

import java.sql.*;
import java.text.ParseException;
import java.util.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Interface that interacts with the database
 * @authors Rudy, Daniel, Bradley, Crosby, Jesus
 */
public class DatabaseInterface {

    private String connectionLink;
    /* constructors */
    public DatabaseInterface() {
        this.connectionLink = "jdbc:sqlite::resource:GoTorosBank.db";
    }
    public DatabaseInterface(String connectionLink) {
        this.connectionLink = connectionLink;
    }

    /**
     * checks all users in the database and makes sure that the user name and
     * password is inside the database
     *••••••
     * @param username the users name
     * @param userpassword the users password
     * @return true if users is inside the data base false if the user is not in
     * the data base
     * @throws ClassNotFoundException checks if file is inside
     * @throws SQLException checks for sql exceptions
     */
    public boolean validate(String username, String userpassword)throws SQLException,ClassNotFoundException{
        Connection c = null;

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
            resultSet.close();
            statement.close();
            c.close();
        return false;
    }

    /**
     * enter the account number and the account number you want to send it to 
     * and the amount and everything is done
     * 
     * @accountIDFrom the account number that you are sending it from
     * @accountIDTo the account number you are sending it to 
     * @amount the amount of money being sent
     */
    public void transfer(int accountIDFrom, int accountIDTo,float amount)
            throws SQLException,ClassNotFoundException{
        withdraw(accountIDFrom,amount,"Transfer withdraw to account number: "+accountIDTo);
        deposit(accountIDTo,amount,"Transfer deposit from account number: "+accountIDFrom);
    }

    /**
     * enter information and the withdraw will be mad all you have to do is enter account
     * ID amount and description
     *
     * @param accountIDFrom ID of the account that is being withdrew from
     * @param amount amount of money being transfered
     * @param description description of transfer
     * @throws SQLException this will be caught by the servlet class
     * @throws ClassNotFoundException this will be caught by the servlet class
     */
    public void withdraw(int accountIDFrom,float amount, String description)
            throws SQLException,ClassNotFoundException {
        float accountbalance;
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

        accountbalance = resultSet.getFloat("ACCOUNT_BALANCE");

        //subtract ammount from balance
        accountbalance-=amount;

        //used to change the balance in the database
        statement.executeUpdate(
                "UPDATE ACCOUNTS"+
                "SET ACCOUNT_BALANCE="+ accountbalance +
                "WHERE ACCOUNT_NUMBER="+accountIDFrom+";");

        resultSet.close();
        statement.close();
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
    public void deposit(int accountNumber, float amount, String description)throws SQLException, ClassNotFoundException{
        float accountbalance;
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

        accountbalance = resultSet.getFloat("ACCOUNT_BALANCE");

        //add ammount from balance
        accountbalance+=amount;

        //used to change the balance in the database
        statement.executeUpdate(
                "UPDATE ACCOUNTS "+
                        "SET ACCOUNT_BALANCE="+ accountbalance + " " +
                        "WHERE ACCOUNT_NUMBER="+accountNumber+";");
        resultSet.close();
        statement.close();
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
    public void addTransaction(int accountNumber, String transactionDescription, float amount)
            throws SQLException,ClassNotFoundException{
        Connection c = null;

        Class.forName("org.sqlite.JDBC");
        c = DriverManager.getConnection(connectionLink); //this will get the file in resources
        /*do other stuff here*/

        Statement statement = c.createStatement();
        statement.setQueryTimeout(30); // set timeout to 30 sec.

        //get the largest transaction number and add 1 to it
        ResultSet resultSet = statement.executeQuery(
                "SELECT MAX(TRANSACTION_NUMBER) as MAX " +
                "FROM TRANSACTIONS " +
                "WHERE ACCOUNT_NUMBER="+accountNumber+";");

        //used to save the number
        int newTransactionNumber = 0;

        while (resultSet.next()) {
            newTransactionNumber = resultSet.getInt("MAX")+1;
        }

        //used to get the real date for transaction time
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateAndTime = sdf.format(new Date());

        //will add the new transaction to the database
        statement.executeUpdate("INSERT INTO TRANSACTIONS VALUES ( "+
                        newTransactionNumber+", '"+
                        transactionDescription+"', "+
                        amount+", '"+
                        dateAndTime+"', "+
                        accountNumber+");");

        resultSet.close();
        statement.close();
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
    public User getUser(String username) throws ParseException, SQLException, ClassNotFoundException {
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
                        resultSet.getString("LAST_NAME"));
                ArrayList<Account> accounts = getAccounts(user);
                user.addAccounts(accounts); //call the private getAccounts function

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
    private ArrayList<Bill> getBills(Account account) throws ParseException, ClassNotFoundException, SQLException {
        ArrayList<Bill> bills = new ArrayList<Bill>();
        Class.forName("org.sqlite.JDBC");
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//dd/MM/yyyy

        connection = DriverManager.getConnection(connectionLink); //this will get the file in resources
        statement = connection.createStatement();
        resultSet = statement.executeQuery("SELECT * FROM BILLS");
        while (resultSet.next()) {

            int BillsID = resultSet.getInt("BID");
            String Bill_Name = resultSet.getString("BILL_NAME");
            String Bill_Description = resultSet.getString("BILL_DESCRIPTION");
            float Bill_Amount = resultSet.getFloat("BILL_AMOUNT");
            Date Bill_Due_Date = sdf.parse(resultSet.getString("BILL_DUE_DATE"));
            String Bill_Status = resultSet.getString("BILL_STATUS");
            int Uid = resultSet.getInt("UID");
            int Account_Number = resultSet.getInt("ACCOUNT_NUMBER");

            Bill bill = new Bill(BillsID, Bill_Name, Bill_Description, Bill_Amount, Bill_Due_Date, Bill_Status, account);
            bills.add(bill);
        }
        resultSet.close();
        statement.close();
        connection.close();
        return bills;
    }
    
     /**
     * This function finds all accounts tied to a User. It takes a User class, and get it's ID.
     * Then it checks the database for accounts tied to that ID
     * @param parentUser A User object that is used to get the ID.
     * @throws SQLException this will be caught by the servlet class
     * @throws ClassNotFoundException this will be caught by the servlet class
     */
    private ArrayList<Account> getAccounts(User parentUser) throws ParseException, SQLException, ClassNotFoundException{
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
                float accountBalance = resultSet.getFloat("ACCOUNT_BALANCE");
                Account account = new Account(accountNumber, accountBalance, parentUser, accountType);
                /*TODO: These need to be tested!*/
                account.addTransactions(getTransactions(account)); //get the transactions for this Account
                account.addBills(getBills(account)); //get the bills for this account
                accounts.add(account);
            }
        }
        resultSet.close();
        stmt.close();
        c.close();
        return accounts;
    }

    private ArrayList<Transaction> getTransactions(Account account) throws SQLException, ParseException,ClassNotFoundException
    {
        Connection dbConnection;
        Statement sqlQuery;
        ResultSet result = null;

        int tNumber;
        String tDescription;
        float tAmount;
        Date tDate;
        int aNumber;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//dd/MM/yyyy

        ArrayList<Transaction> transactions = new ArrayList<Transaction>();

        Class.forName("org.sqlite.JDBC");
        dbConnection = DriverManager.getConnection(connectionLink);

        sqlQuery = dbConnection.createStatement();
        result = sqlQuery.executeQuery("SELECT * FROM TRANSACTIONS WHERE ACCOUNT_NUMBER = " +
                account.getAccountNumber() + ";");

        while(result.next())
        {
            aNumber = result.getInt("ACCOUNT_NUMBER");
            if(account.getAccountNumber() == aNumber) {

                tNumber = result.getInt("TRANSACTION_NUMBER");
                tDescription = result.getString("TRANSACTION_DESCRIPTION");
                tAmount = result.getFloat("TRANSACTION_AMOUNT");

                tDate = sdf.parse(result.getString("TRANSACTION_DATE"));

                Transaction transaction = new Transaction(account, tNumber, tAmount, tDescription,
                        tDate);

                transactions.add(transaction);
            }
        }
        result.close();
        sqlQuery.close();
        dbConnection.close();

        return transactions;
    }

}
