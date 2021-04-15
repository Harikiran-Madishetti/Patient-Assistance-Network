import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.sql.Connection;
import java.sql.Statement;
import java.util.Scanner;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class sample {

    // Database credentials
    final static String HOSTNAME = "madi0015.database.windows.net";
    final static String DBNAME = "cs-dsa-4513-sql-db";
    final static String USERNAME = "madi0015";
    final static String PASSWORD = "Kiran@1221";

    // Database connection string
    final static String URL = String.format("jdbc:sqlserver://%s:1433;database=%s;user=%s;password=%s;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;",
            HOSTNAME, DBNAME, USERNAME, PASSWORD);

    // Query templates
    final static String QUERY1 = "INSERT INTO TEAMS VALUES (?, ?, ?);";

    final static String QUERY2_1 = "INSERT INTO CLIENT VALUES (?, ?, ?, ?, ?, ?);";
    final static String QUERY2_2 = "INSERT INTO CARES VALUES (?, ?, ?);";
    
    final static String QUERY3_1 = "INSERT INTO VOLUNTEER VALUES (?, ?, ?, ?);";
    final static String QUERY3_2 = "INSERT INTO SERVES VALUES (?, ?, ?);";
    
    final static String QUERY4 = "INSERT INTO WORK VALUES (?, ?, ?, ?);";
    
    final static String QUERY5_1 = "INSERT INTO EMPLOYEE VALUES (?, ?, ?, ?);";
    final static String QUERY5_2 = "INSERT INTO REPORTS VALUES (?, ?, ?, ?);";
    
    final static String QUERY6 = "INSERT INTO EXPENSES VALUES (?, ?, ?, ?);";
    
    final static String QUERY7_1 = "INSERT INTO ORGANIZATION VALUES (?, ?, ?, ?);";
    final static String QUERY7_2 = "INSERT INTO BUSINESS VALUES (?, ?, ?, ?);";
    final static String QUERY7_3 = "INSERT INTO CHURCH VALUES (?, ?);";
    final static String QUERY7_4 = "INSERT INTO SPONSOR VALUES (?, ?);";
    
    final static String QUERY8_1 = "INSERT INTO DONOR VALUES (?, ?);";
    final static String QUERY8_2 = "INSERT INTO DONOR_DONATIONS VALUES (?, ?, ?, ?, ?);";
    final static String QUERY8_3 = "INSERT INTO DONOR_DONATIONS_CHECK VALUES (?, ?, ?, ?, ?, ?);";
    final static String QUERY8_4 = "INSERT INTO DONOR_DONATIONS_CARD VALUES (?, ?, ?, ?, ?, ?, ?, ?);";
    
    final static String QUERY9_1 = "INSERT INTO ORGANIZATION VALUES (?, ?, ?, ?);";
    final static String QUERY9_2 = "INSERT INTO ORG_DONATIONS VALUES (?, ?, ?, ?, ?, ?);";
    final static String QUERY9_3 = "INSERT INTO ORG_DONATIONS_CHECK VALUES (?, ?, ?, ?, ?, ?);";
    final static String QUERY9_4 = "INSERT INTO ORG_DONATIONS_CARD VALUES (?, ?, ?, ?, ?, ?, ?, ?);";
    
    final static String QUERY10 = "SELECT DOC_NAME, DOC_PH FROM CLIENT WHERE SSN = ? ;";
    
    final static String QUERY11 = "SELECT SSN, SUM(AMOUNT) 'TOTAL_EXPENSES' FROM EXPENSES "
    		+ "WHERE DATE BETWEEN ? AND ? GROUP BY SSN ORDER BY TOTAL_EXPENSES DESC ;";
    
    final static String QUERY12 = "SELECT DISTINCT SSN FROM SERVES "
    		+ "WHERE NAME IN (SELECT DISTINCT NAME FROM CARES WHERE SSN = ?);";
    
    final static String QUERY13 = "SELECT * FROM QUERY13 ORDER BY NAME ;";
    
    final static String QUERY14 = "SELECT * FROM QUERY14 ORDER BY TOTAL_AMOUNT ;";
    
    final static String QUERY15 = "SELECT NAME FROM TEAMS WHERE FORMATION_DATE >  ? ;";
    
    final static String QUERY16 = "EXECUTE UpdateEmpSalary ;"; 
    
    final static String QUERY17 = "EXECUTE DeleteClient ;";
    
    final static String QUERY19 = "SELECT NAME, ADDRESS FROM PERSON WHERE MAILING_LIST = 1;";
    // User input prompt//
    final static String PROMPT = 
            		"\nPlease select one of the below options: \n" +
            		"(1) 	Enter a new team into the database. \n" +
            		"(2) 	Enter a new client into the database and associate him or her with one or more teams.  \n" +
            		"(3) 	Enter a new volunteer into the database and associate him or her with one or more teams.  \n" +
            		"(4) 	Enter the number of hours a volunteer worked this month for a particular team.  \n" +
            		"(5) 	Enter a new employee into the database and associate him or her with one or more teams.  \n" +
            		"(6) 	Enter an expense charged by an employee.  \n" +
            		"(7) 	Enter a new organization and associate it to one or more PAN teams.  \n" +
            		"(8) 	Enter a new donor and associate him or her with several donations.  \n" +
            		"(9) 	Enter a new organization and associate it with several donations.  \n" +
            		"(10) 	Retrieve the name and phone number of the doctor of a particular client.  \n" +
            		"(11) 	Retrieve the total amount of expenses charged by each employee for a particular period of time. The list should be sorted by the total amount of expenses.  \n" +
            		"(12) 	Retrieve the list of volunteers that are members of teams that support a particular client. \n" +
            		"(13) 	Retrieve the names and contact information of the clients that are supported by teams sponsored by an organization whose name starts with a letter between B and K. The client list should be sorted by name.  \n" +
            		"(14) 	Retrieve the name and total amount donated by donors that are also employees. The list should be sorted by the total amount of the donations, and indicate if each donor wishes to remain anonymous.  \n" +
            		"(15) 	Retrieve the names of all teams that were founded after a particular date.  \n" +
            		"(16) 	Increase the salary by 10% of all employees to whom more than one team must report. \n" +
            		"(17) 	Delete all clients who do not have health insurance and whose value of importance for transportation is less than 5.  \n" +
            		"(18) 	Import: enter new teams from a data file until the file is empty (the user must be asked to enter the input file name).  \n" +
            		"(19) 	Export: Retrieve names and mailing addresses of all people on the mailing list and output them to a data file instead of screen (the user must be asked to enter the output file name).  \n" +
            		"(20) 	Quit.  \n";

    @SuppressWarnings("null")
	public static void main(String[] args) throws SQLException {

        System.out.println("WELCOME TO THE PATIENT ASSISTANT NETWORK DATABASE SYSTEM!");

        final Scanner sc = new Scanner(System.in); // Scanner is used to collect the user input
        String option = ""; // Initialize user option selection as nothing
        while (!option.equals("20")) { // As user for options until option 20 is selected
            System.out.println(PROMPT); // Print the available options
            option = sc.next(); // Read in the user option selection
            sc.nextLine();      //Required to consume next line present in buffer after reading input using scanner.next()
            
            switch (option) { // Switch between different options
                case "1": // Enter a new team into the database 
                    // Collect the new team data from the user
                    System.out.println("Please enter Team Name:");
                    String name1 = sc.nextLine(); // Read in the user input of team name
                    if(name1.isEmpty()) { name1 = null; }	//If user not entered anything assuming it as null (same is followed for every user input as string)
                    
                    System.out.println("Please enter Team Type:");
                    
                    String type1 = sc.nextLine(); // Read in user input of team type
                    if(type1.isEmpty()) { type1 = null; }

                    System.out.println("Please enter Team Formation Date in MM/DD/YYY ");
                    
                    String date1 = sc.nextLine();	// Read in user input of team formation date
                    if(date1.isEmpty()) { date1 = null; }

                    
                    //System.out.println("Inserting Team into the database...");
                    // Get a database connection and prepare a query statement
                    try (final Connection connection = DriverManager.getConnection(URL)) {
                        try (
                            final PreparedStatement statement = connection.prepareStatement(QUERY1)) {
                            // Populate the query template with the data collected from the user
                            statement.setString(1, name1);
                            statement.setString(2, type1);
                            statement.setString(3, date1);

                            System.out.println("Dispatching the query...");
                            // Actually execute the populated query
                            statement.executeUpdate();
                            System.out.println(String.format("Team %s inserted successfully.", name1));
                        }
                    }

                    break;
                 
                case "2": //Enter a new client into the database and associate him or her with one or more teams
                	// Collect the new client data from the user
                	System.out.println("Please enter Client SSN:");
                    int ssn2 = Integer.parseInt(sc.nextLine());
                    //if(ssn2.isEmpty()) { ssn2 = null; }
                	
                    System.out.println("Please enter Client Doctor Name:");
                    String docname2 = sc.nextLine() ; 
                    if(docname2.isEmpty()) { docname2 = null; }
                   
                    System.out.println("Please enter Client Attorney Name:");
                    String attname2 = sc.nextLine() ; 
                    if(attname2.isEmpty()) { attname2 = null; }
                    
                    System.out.println("Please enter Client Doctor Phone Number in (XXX) XXX-XXXX:");
                    String docph2 = sc.nextLine() ; 
                    if(docph2.isEmpty()) { docph2 = null; }
                    
                    //sc.nextLine();  //Used to consume extra line created by space
                    
                    System.out.println("Please enter Client Attorney Phone Number in (XXX) XXX-XXXX:");
                    String attph2 = sc.nextLine() ;
                    if(attph2.isEmpty()) { attph2 = null; }
                    
                    System.out.println("Please enter Client Association Date in MM/DD/YYY:");
                    String date2 = sc.nextLine(); 
                    if(date2.isEmpty()) { date2 = null; }
                    
                    //System.out.println("Inserting Client into the database...");
                    
                    try (final Connection connection = DriverManager.getConnection(URL)) {
                        try (
                            final PreparedStatement statement = connection.prepareStatement(QUERY2_1)) {
                            // Populate the query template with the data collected from the user
                        	statement.setInt(1, ssn2);
                            statement.setString(2, docname2);
                            statement.setString(3, attname2);
                            statement.setString(4, docph2);
                            statement.setString(5, attph2);
                            statement.setString(6, date2);
                            
                            System.out.println("Dispatching the query...");
                            // Actually execute the populated query
                            statement.executeUpdate();
                            System.out.println(String.format("Client %d inserted successfully. \n", ssn2));
                        }
                    }
                    
                    System.out.printf("Please enter the No. of teams to be associated with Client %d: \n", ssn2 );
                    int num = Integer.parseInt(sc.nextLine());
                    
                    for (int i = 0; i< num; i++) {
                    	System.out.println("Please enter Team Name:");
                    	String t_name2 = sc.nextLine(); 
                    	if(t_name2.isEmpty()) { t_name2 = null; }
                    	
                    	System.out.println("Please enter Team Client Status (1 for Active, 0 for Inactive):");
                    	final int active2 = Integer.parseInt(sc.nextLine());
                    	
                    	
                    	try (final Connection connection = DriverManager.getConnection(URL)) {
                            try (
                                final PreparedStatement statement = connection.prepareStatement(QUERY2_2)) {
                                // Populate the query template with the data collected from the user
                            	statement.setInt(1, ssn2);
                                statement.setString(2, t_name2);
                                statement.setInt(3, active2);
                                
                                System.out.println("Dispatching the query...");
                                // Actually execute the populated query
                                //final int rows_inserted = statement.executeUpdate();
                                statement.executeUpdate();
                                //System.out.println(String.format("Done. %d rows inserted.", rows_inserted));
                                System.out.printf("Successfully Associated Client %d with Team %s. \n", ssn2, t_name2 );
                            }
                        }
                    }
                    break;
                
                case "3": //Enter a new volunteer into the database and associate him or her with one or more teams
                	System.out.println("Please enter Volunteer SSN:");
                    final int ssn3 = Integer.parseInt(sc.nextLine());
                   
                   
                    System.out.println("Please enter Volunteer Joining Date in MM/DD/YYY:");
                    String jd3 = sc.nextLine() ; 
                    if(jd3.isEmpty()) { jd3 = null; }
                    
                    System.out.println("Please enter Volunteer Training Date in MM/DD/YYY:");
                    String td3 = sc.nextLine() ; 
                    if(td3.isEmpty()) { td3 = null; }
                    
                    System.out.println("Please enter Volunteer Training Location:");
                    String tl3 = sc.nextLine() ; 
                    if(tl3.isEmpty()) { tl3 = null; }
                    
                    //System.out.println("Inserting Client into the database...");
                    
                    try (final Connection connection = DriverManager.getConnection(URL)) {
                        try (
                            final PreparedStatement statement = connection.prepareStatement(QUERY3_1)) {
                            // Populate the query template with the data collected from the user
                        	statement.setInt(1, ssn3);
                            statement.setString(2, jd3);
                            statement.setString(3, td3);
                            statement.setString(4, tl3);
                            
                            System.out.println("Dispatching the query...");
                            // Actually execute the populated query
                            statement.executeUpdate();
                            System.out.println(String.format("Volunteer %d inserted successfully. \n", ssn3));
                        }
                    }
                    
                    System.out.printf("Please enter the No. of teams to be associated with Volunteer %d: \n", ssn3 );
                    int num1 = Integer.parseInt(sc.nextLine());
                    
                    for (int i = 0; i< num1; i++) {
                    	System.out.println("Please enter Team Name:");
                    	String t_name2 = sc.nextLine(); 
                    	if(t_name2.isEmpty()) { t_name2 = null; }
                    	
                    	System.out.println("Please enter Team Volunteer Status (1 for Active, 0 for Inactive):");
                    	final int active2 = Integer.parseInt(sc.nextLine());
                    	
                    	
                    	try (final Connection connection = DriverManager.getConnection(URL)) {
                            try (
                                final PreparedStatement statement = connection.prepareStatement(QUERY3_2)) {
                                // Populate the query template with the data collected from the user
	                            	statement.setInt(1, ssn3);
	                                statement.setString(2, t_name2);
	                                statement.setInt(3, active2);
	                                
	                                System.out.println("Dispatching the query...");
	                                // Actually execute the populated query
	                                //final int rows_inserted = statement.executeUpdate();
	                                statement.executeUpdate();
	                                //System.out.println(String.format("Done. %d rows inserted.", rows_inserted));
	                                System.out.printf("Successfully Associated Volunteer %d with Team %s. \n", ssn3, t_name2 );
	                            }
	                        }
	                    }
	                    break;
	                    
                case "4": //Enter the number of hours a volunteer worked this month for a particular team
                	//sc.nextLine();      //Required to consume next line present in buffer after reading input using scanner.next()
                    System.out.println("Please enter Team Name:");
                    String tname4 = sc.nextLine() ; 
                    if(tname4.isEmpty()) { tname4 = null; } 
                    //sc.nextLine();
                    System.out.println("Please enter Volunteer SSN:");
                    final int ssn4 = Integer.parseInt(sc.nextLine());
                   
                    System.out.println("Please enter working Month in MMM (JAN, FEB, etc., ):");
                    String month4 = sc.nextLine() ; 
                    if(month4.isEmpty()) { month4 = null; }
                    
                    System.out.println("Please enter Hours worked by Volunteer:");
                    final float hours4 = Float.parseFloat(sc.nextLine());
                    
                    
                    try (final Connection connection = DriverManager.getConnection(URL)) {
                        try (
                            final PreparedStatement statement = connection.prepareStatement(QUERY4)) {
                            // Populate the query template with the data collected from the user
                            statement.setString(1, tname4);
                            statement.setInt(2, ssn4);
                            statement.setString(3, month4);
                            statement.setFloat(4, hours4);
                            
                            System.out.println("Dispatching the query...");
                            // Actually execute the populated query
                            statement.executeUpdate();
                            System.out.println(String.format("Successfully inserted hours %f worked by volunteer %d for team %s in %s. \n", hours4, ssn4, tname4, month4 ));
                        }
                    }
                    
                    break;
                    	
                case "5": //Enter a new employee into the database and associate him or her with one or more teams
                	System.out.println("Please enter Employee SSN:");
                    final int ssn5 = Integer.parseInt(sc.nextLine());
                   
                    System.out.println("Please enter Employee Salary:");
                    final float salary5 = Float.parseFloat(sc.nextLine());
                    
                    System.out.println("Please enter Employee Marital Status (MARRIED, SINGLE, etc.,):");
                    String mstatus5 = sc.nextLine() ; 
                    if(mstatus5.isEmpty()) { mstatus5 = null; }
                    
                    System.out.println("Please enter Employee Hire Date in MM/DD/YYY:");
                    String hdate5 = sc.nextLine() ; 
                    if(hdate5.isEmpty()) { hdate5 = null; }
                    
                    //System.out.println("Inserting Client into the database...");
                    
                    try (final Connection connection = DriverManager.getConnection(URL)) {
                        try (
                            final PreparedStatement statement = connection.prepareStatement(QUERY5_1)) {
                            // Populate the query template with the data collected from the user
                        	statement.setInt(1, ssn5);
                            statement.setFloat(2, salary5);
                            statement.setString(3, mstatus5);
                            statement.setString(4, hdate5);
                            
                            System.out.println("Dispatching the query...");
                            // Actually execute the populated query
                            statement.executeUpdate();
                            System.out.println(String.format("Employee %d inserted successfully. \n", ssn5));
                        }
                    }
                    
                    System.out.printf("Please enter the No. of teams to be associated with Employee %d: \n", ssn5 );
                    int num2 = Integer.parseInt(sc.nextLine());
                    
                    for (int i = 0; i< num2; i++) {
                    	System.out.println("Please enter Team Name:");
                    	String t_name5 = sc.nextLine(); 
                    	if(t_name5.isEmpty()) { t_name5 = null; }
                    	
                    	
                    	System.out.println("Please enter Team status reporting Date to Employee in MM/DD/YYY:");
                        String rdate = sc.nextLine() ;
                        if(rdate.isEmpty()) { rdate = null; }
                        
                        System.out.println("Please enter Status Description:");
                        String sdesc = sc.nextLine() ; 
                        if(sdesc.isEmpty()) { sdesc = null; }
                    	
                    	try (final Connection connection = DriverManager.getConnection(URL)) {
                            try (
                                final PreparedStatement statement = connection.prepareStatement(QUERY5_2)) {
                                // Populate the query template with the data collected from the user
                            		statement.setString(1, t_name5);
	                            	statement.setInt(2, ssn5);
	                                statement.setString(3, rdate);
	                                statement.setString(4, sdesc);
	                                
	                                System.out.println("Dispatching the query...");
	                                // Actually execute the populated query
	                                //final int rows_inserted = statement.executeUpdate();
	                                statement.executeUpdate();
	                                //System.out.println(String.format("Done. %d rows inserted.", rows_inserted));
	                                System.out.printf("Successfully Associated Employee %d with Team %s. \n", ssn5, t_name5 );
	                            }
	                        }
	                    }
	                    break;
	                    
                case "6": //Enter an expense charged by an employee
                	System.out.println("Please enter Employee SSN:");
                    final int ssn6 = Integer.parseInt(sc.nextLine());
                    
                    System.out.println("Please enter Date of Expense in MM/DD/YYY:");
                    String edate6 = sc.nextLine() ; 
                    if(edate6.isEmpty()) { edate6 = null; }
                   
                    System.out.println("Please enter Expense Amount");
                    final float eamount6 = Float.parseFloat(sc.nextLine());
                    
                    System.out.println("Please enter Expense Description:");
                    String edesc = sc.nextLine() ; 
                    if(edesc.isEmpty()) { edesc = null; }
                    
                    
                    
                    //System.out.println("Inserting Client into the database...");
                    
                    try (final Connection connection = DriverManager.getConnection(URL)) {
                        try (
                            final PreparedStatement statement = connection.prepareStatement(QUERY6)) {
                            // Populate the query template with the data collected from the user
                        	statement.setInt(1, ssn6);
                        	statement.setString(2, edate6);
                            statement.setFloat(3, eamount6);
                            statement.setString(4, edesc);
                            
                            System.out.println("Dispatching the query...");
                            // Actually execute the populated query
                            statement.executeUpdate();
                            System.out.println(String.format("Expenses of an Employee %d inserted successfully. \n", ssn6));
                        }
                    }
                    break;
                    
                case "7": //Enter a new organization and associate it to one or more PAN teams
                	
                	System.out.println("Please enter Organization Name:");
                    String name7 = sc.nextLine() ;
                    if(name7.isEmpty()) { name7 = null; }
                   
                    System.out.println("Please enter Organization Phone Number in (XXX) XXX-XXXX:");
                    String phone7 = sc.nextLine() ;
                    if(phone7.isEmpty()) { phone7 = null; }
                    
                    System.out.println("Please enter Organization Mailing Address:");
                    String addr7 = sc.nextLine() ; 
                    if(addr7.isEmpty()) { addr7 = null; }
                    
                    System.out.println("Please enter Organization Contact Person Name:");
                    String cpname7 = sc.nextLine() ; 
                    if(cpname7.isEmpty()) { cpname7 = null; }
                    
                    //System.out.println("Inserting Client into the database...");
                    
                    try (final Connection connection = DriverManager.getConnection(URL)) {
                        try (
                            final PreparedStatement statement = connection.prepareStatement(QUERY7_1)) {
                            // Populate the query template with the data collected from the user
                        	statement.setString(1, name7);
                            statement.setString(2, phone7);
                            statement.setString(3, addr7);
                            statement.setString(4, cpname7);
                            
                            System.out.println("Dispatching the query...");
                            // Actually execute the populated query
                            statement.executeUpdate();
                            System.out.println(String.format("Organization %s inserted successfully. \n", name7));
                        }
                    }
                    
                    System.out.println("Please enter Organization Type (Business, Church, etc.,):");
                	String otype7 = sc.nextLine(); 
                	if(otype7.isEmpty()) { otype7 = null; }
                	
                	if(otype7.toLowerCase().equals("church")) {
                		
                		System.out.println("Please enter Organization Religious Affiliation:");
                    	String orf7= sc.nextLine();
                    	if(orf7.isEmpty()) { orf7 = null; }
                    	
                    	
                    	try (final Connection connection = DriverManager.getConnection(URL)) {
                            try (
                                final PreparedStatement statement = connection.prepareStatement(QUERY7_3)) {
                                // Populate the query template with the data collected from the user
                            		statement.setString(1, name7);
                            		statement.setString(2, orf7);
	                                
	                                System.out.println("Dispatching the query...");
	                                // Actually execute the populated query
	                                //final int rows_inserted = statement.executeUpdate();
	                                statement.executeUpdate();
	                                //System.out.println(String.format("Done. %d rows inserted.", rows_inserted));
	                                System.out.printf("Successfully Inserted Organization as Church. \n" );
	                            }
	                        }
                		}
                	else if(otype7.toLowerCase().equals("business")) {
                		
                		System.out.println("Please enter Business Type:");
                    	String obt7= sc.nextLine();
                    	if(obt7.isEmpty()) { obt7 = null; }
                    	
                    	System.out.println("Please enter the size of Business:");
                    	String obs7 = sc.nextLine(); 
                    	if(obs7.isEmpty()) { obs7 = null; }
                    	
                    	System.out.println("Please enter Organization Business Website:");
                    	String obw7 = sc.nextLine(); 
                    	if(obw7.isEmpty()) { obw7 = null; }
                    	
                    	try (final Connection connection = DriverManager.getConnection(URL)) {
                            try (
                                final PreparedStatement statement = connection.prepareStatement(QUERY7_2)) {
                                // Populate the query template with the data collected from the user
                            		statement.setString(1, name7);
                            		statement.setString(2, obt7);
	                                statement.setString(3, obs7);
	                                statement.setString(4, obw7);
	                                
	                                System.out.println("Dispatching the query...");
	                                // Actually execute the populated query
	                                //final int rows_inserted = statement.executeUpdate();
	                                statement.executeUpdate();
	                                //System.out.println(String.format("Done. %d rows inserted.", rows_inserted));
	                                System.out.printf("Successfully Inserted Organization as Business. \n" );
	                            }
	                        }
                	}
                    System.out.printf("Please enter the No. of teams to be sponsored  by  %s: \n", name7 );
                    int num3 = Integer.parseInt(sc.nextLine());
                    
                    for (int i = 0; i< num3; i++) {
                    	
                    	System.out.println("Please enter Team Name:");
                    	String t_name7 = sc.nextLine(); 
                    	if(t_name7.isEmpty()) { t_name7 = null; }
                    	
                    	
                    	try (final Connection connection = DriverManager.getConnection(URL)) {
                            try (
                                final PreparedStatement statement = connection.prepareStatement(QUERY7_4)) {
                                // Populate the query template with the data collected from the user
                            		statement.setString(1, t_name7);
	                                statement.setString(2, name7);
	                                
	                                System.out.println("Dispatching the query...");
	                                // Actually execute the populated query
	                                //final int rows_inserted = statement.executeUpdate();
	                                statement.executeUpdate();
	                                //System.out.println(String.format("Done. %d rows inserted.", rows_inserted));
	                                System.out.printf("Successfully Associated Team %s with Organization %s. \n", t_name7, name7 );
	                            }
	                        }
	                    }
	                    break;
                case "8": //Enter a new donor and associate him or her with several donations
                	
                	System.out.println("Please enter Donor SSN:");
                    final int ssn8= Integer.parseInt(sc.nextLine());
                   
                    System.out.println("Please enter 1 if the donar want's to be anonymous else 0:");
                    final int an8 = Integer.parseInt(sc.nextLine()) ;
                    
                    //System.out.println("Inserting Client into the database...");
                    
                    try (final Connection connection = DriverManager.getConnection(URL)) {
                        try (
                            final PreparedStatement statement = connection.prepareStatement(QUERY8_1)) {
                            // Populate the query template with the data collected from the user
                        	statement.setInt(1, ssn8);
                            statement.setInt(2 ,an8);
                            
                            System.out.println("Dispatching the query...");
                            // Actually execute the populated query
                            statement.executeUpdate();
                            System.out.println(String.format("Donor %s inserted successfully. \n", ssn8));
                        }
                    }
                    
                    
                    System.out.printf("Please enter the No. of Donations to be associated with Donar  %s: \n", ssn8 );
                    int num4 = Integer.parseInt(sc.nextLine());
                    
                    for (int i = 0; i< num4; i++) {
                    	
                    	System.out.printf("Please enter Donation %d Details: \n", (i+1));
                    	
                    	System.out.println("Please enter Date of donation in MM/DD/YYY:");
                    	String date8 = sc.nextLine(); 
                    	if(date8.isEmpty()) { date8 = null; }
                    	
                    	System.out.println("Please enter Donation Amount:");
                        final float amt8 = Float.parseFloat(sc.nextLine());
                        
                    	System.out.println("Please enter Campaign Name:");
                    	String cname8 = sc.nextLine(); 
                    	if(cname8.isEmpty()) { cname8 = null; }
                    	
                    	System.out.println("Please enter the Type of Donation:");
                    	String type8 = sc.nextLine(); 
                    	if(type8.isEmpty()) { type8 = null; }
                    	
                    	try (final Connection connection = DriverManager.getConnection(URL)) {
                            try (
                                final PreparedStatement statement = connection.prepareStatement(QUERY8_2)) {
                                // Populate the query template with the data collected from the user
                            		statement.setInt(1, ssn8);
                            		statement.setString(2, date8);
	                                statement.setFloat(3, amt8);
	                                statement.setString(4, cname8);
	                                statement.setString(5, type8);
	                                
	                                System.out.println("Dispatching the query...");
	                                // Actually execute the populated query
	                                //final int rows_inserted = statement.executeUpdate();
	                                statement.executeUpdate();
	                                //System.out.println(String.format("Done. %d rows inserted.", rows_inserted));
	                                System.out.printf("Successfully Associated Donation with Donar %d. \n", ssn8 );
	                            }
	                        }
                    	System.out.println("Please enter mode of Payment (Check, Card, etc.,):");
                    	String payment8 = sc.nextLine(); 
                    	if(payment8.isEmpty()) { payment8 = null; }
                    	
                    	if(payment8.toLowerCase().equals("check")) {
                    		
                    		System.out.println("Please enter Check Number:");
                        	final int check8= Integer.parseInt(sc.nextLine());
                        	
                        	
                        	try (final Connection connection = DriverManager.getConnection(URL)) {
                                try (
                                    final PreparedStatement statement = connection.prepareStatement(QUERY8_3)) {
                                    // Populate the query template with the data collected from the user
                                		statement.setInt(1, ssn8);
                                		statement.setString(2, date8);
    	                                statement.setFloat(3, amt8);
    	                                statement.setString(4, cname8);
    	                                statement.setString(5, type8);
    	                                statement.setInt(6, check8);
    	                                
    	                                System.out.println("Dispatching the query...");
    	                                // Actually execute the populated query
    	                                //final int rows_inserted = statement.executeUpdate();
    	                                statement.executeUpdate();
    	                                //System.out.println(String.format("Done. %d rows inserted.", rows_inserted));
    	                                System.out.printf("Successfully Inserted Payment Details. \n" );
    	                            }
    	                        }
                    		}
                    	else if(payment8.toLowerCase().equals("card")) {
                    		
                    		System.out.println("Please enter Card Number:");
                        	String card8= sc.nextLine();
                        	if(card8.isEmpty()) { card8 = null; }
                        	
                        	System.out.println("Please enter Card Type:");
                        	String cardt8 = sc.nextLine(); 
                        	if(cardt8.isEmpty()) { cardt8 = null; }
                        	
                        	System.out.println("Please enter Card Expiration Date in MM/YYY:");
                        	String cardexp8 = sc.nextLine(); 
                        	if(cardexp8.isEmpty()) { cardexp8 = null; }
                        	
                        	try (final Connection connection = DriverManager.getConnection(URL)) {
                                try (
                                    final PreparedStatement statement = connection.prepareStatement(QUERY8_4)) {
                                    // Populate the query template with the data collected from the user
                                		statement.setInt(1, ssn8);
                                		statement.setString(2, date8);
    	                                statement.setFloat(3, amt8);
    	                                statement.setString(4, cname8);
    	                                statement.setString(5, type8);
    	                                statement.setString(6, card8);
    	                                statement.setString(7, cardt8);
    	                                statement.setString(8, cardexp8);
    	                                
    	                                System.out.println("Dispatching the query...");
    	                                // Actually execute the populated query
    	                                //final int rows_inserted = statement.executeUpdate();
    	                                statement.executeUpdate();
    	                                //System.out.println(String.format("Done. %d rows inserted.", rows_inserted));
    	                                System.out.printf("Successfully Inserted Payment Details. \n" );
    	                            }
    	                        }
                    	}
                    	
                    		
	                    }
                    	System.out.printf("Successfully Inserted Donar %d and it's Donations \n" , ssn8);
	                    break;
	                    
                case "9": //Enter a new organization and associate it with several donations
                	
                	System.out.println("Please enter Organization Name:");
                    String name9 = sc.nextLine() ;
                    if(name9.isEmpty()) { name9 = null; }
                   
                    System.out.println("Please enter Organization Phone Number in (XXX) XXX-XXXX:");
                    String phone9 = sc.nextLine() ;
                    if(phone9.isEmpty()) { phone9 = null; }
                    
                    System.out.println("Please enter Organization Mailing Address:");
                    String addr9 = sc.nextLine() ; 
                    if(addr9.isEmpty()) { addr9 = null; }
                    
                    System.out.println("Please enter Organization Contact Person Name:");
                    String cpname9 = sc.nextLine() ; 
                    if(cpname9.isEmpty()) { cpname9 = null; }
                    
                    //System.out.println("Inserting Client into the database...");
                    
                    try (final Connection connection = DriverManager.getConnection(URL)) {
                        try (
                            final PreparedStatement statement = connection.prepareStatement(QUERY9_1)) {
                            // Populate the query template with the data collected from the user
                        	statement.setString(1, name9);
                            statement.setString(2, phone9);
                            statement.setString(3, addr9);
                            statement.setString(4, cpname9);
                            
                            System.out.println("Dispatching the query...");
                            // Actually execute the populated query
                            statement.executeUpdate();
                            System.out.println(String.format("Organization %s inserted successfully. \n", name9));
                        }
                    }
                    
                    System.out.println("Please enter Organization Type (Business, Church, etc.,):");
                	String otype9 = sc.nextLine(); 
                	if(otype9.isEmpty()) { otype9 = null; }
                	
                	if(otype9.toLowerCase().equals("church")) {
                		
                		System.out.println("Please enter Organization Religious Affiliation:");
                    	String orf9= sc.nextLine();
                    	if(orf9.isEmpty()) { orf9 = null; }
                    	
                    	
                    	try (final Connection connection = DriverManager.getConnection(URL)) {
                            try (
                                final PreparedStatement statement = connection.prepareStatement(QUERY7_3)) {
                                // Populate the query template with the data collected from the user
                            		statement.setString(1, name9);
                            		statement.setString(2, orf9);
	                                
	                                System.out.println("Dispatching the query...");
	                                // Actually execute the populated query
	                                //final int rows_inserted = statement.executeUpdate();
	                                statement.executeUpdate();
	                                //System.out.println(String.format("Done. %d rows inserted.", rows_inserted));
	                                System.out.printf("Successfully Inserted Organization as Church. \n" );
	                            }
	                        }
                		}
                	else if(otype9.toLowerCase().equals("business")) {
                		
                		System.out.println("Please enter Business Type:");
                    	String obt9= sc.nextLine();
                    	if(obt9.isEmpty()) { obt9 = null; }
                    	
                    	System.out.println("Please enter the size of Business:");
                    	String obs9 = sc.nextLine(); 
                    	if(obs9.isEmpty()) { obs9 = null; }
                    	
                    	System.out.println("Please enter Organization Business Website:");
                    	String obw9 = sc.nextLine(); 
                    	if(obw9.isEmpty()) { obw9 = null; }
                    	
                    	try (final Connection connection = DriverManager.getConnection(URL)) {
                            try (
                                final PreparedStatement statement = connection.prepareStatement(QUERY7_2)) {
                                // Populate the query template with the data collected from the user
                            		statement.setString(1, name9);
                            		statement.setString(2, obt9);
	                                statement.setString(3, obs9);
	                                statement.setString(4, obw9);
	                                
	                                System.out.println("Dispatching the query...");
	                                // Actually execute the populated query
	                                //final int rows_inserted = statement.executeUpdate();
	                                statement.executeUpdate();
	                                //System.out.println(String.format("Done. %d rows inserted.", rows_inserted));
	                                System.out.printf("Successfully Inserted Organization as Business. \n" );
	                            }
	                        }
                	}
                    
                    System.out.printf("Please enter the No. of Donations to be associated with Organication  %s: \n", name9 );
                    int num5 = Integer.parseInt(sc.nextLine());
                    
                    for (int i = 0; i< num5; i++) {
                    	
                    	System.out.printf("Please enter Donation %d Details: \n", (i+1));
                    	
                    	System.out.println("Please enter Date of donation in MM/DD/YYY:");
                    	 String date9 = sc.nextLine(); 
                    	if(date9.isEmpty()) { date9 = null; }
                    	
                    	System.out.println("Please enter Donation Amount:");
                        final float amt9 = Float.parseFloat(sc.nextLine());
                        
                    	System.out.println("Please enter Campaign Name:");
                    	 String cname9 = sc.nextLine(); 
                    	if(cname9.isEmpty()) { cname9 = null; }
                    	
                    	System.out.println("Please enter the Type of Donation:");
                    	 String type9 = sc.nextLine(); 
                    	if(type9.isEmpty()) { type9 = null; }
                    	
                    	System.out.println("Please enter 1 if the Organization want's donation to be anonymous else 0:");
                        final int an9 = Integer.parseInt(sc.nextLine()) ;
                    	
                    	try (final Connection connection = DriverManager.getConnection(URL)) {
                            try (
                                final PreparedStatement statement = connection.prepareStatement(QUERY9_2)) {
                                // Populate the query template with the data collected from the user
                            		statement.setString(1, name9);
                            		statement.setString(2, date9);
	                                statement.setFloat(3, amt9);
	                                statement.setString(4, cname9);
	                                statement.setString(5, type9);
	                                statement.setInt(6, an9);
	                                
	                                System.out.println("Dispatching the query...");
	                                // Actually execute the populated query
	                                //final int rows_inserted = statement.executeUpdate();
	                                statement.executeUpdate();
	                                //System.out.println(String.format("Done. %d rows inserted.", rows_inserted));
	                                System.out.printf("Successfully Associated Donation with Organization %s. \n", name9 );
	                            }
	                        }
                    	System.out.println("Please enter mode of Payment (Check, Card, etc.,):");
                    	String payment9 = sc.nextLine(); 
                    	if(payment9.isEmpty()) { payment9 = null; }
                    	
                    	if(payment9.toLowerCase().equals("check")) {
                    		
                    		System.out.println("Please enter Check Number:");
                        	final int check9= Integer.parseInt(sc.nextLine());
                        	
                        	
                        	try (final Connection connection = DriverManager.getConnection(URL)) {
                                try (
                                    final PreparedStatement statement = connection.prepareStatement(QUERY9_3)) {
                                    // Populate the query template with the data collected from the user
	                                	statement.setString(1, name9);
	                            		statement.setString(2, date9);
		                                statement.setFloat(3, amt9);
		                                statement.setString(4, cname9);
		                                statement.setString(5, type9);
    	                                statement.setInt(6, check9);
    	                                
    	                                System.out.println("Dispatching the query...");
    	                                // Actually execute the populated query
    	                                //final int rows_inserted = statement.executeUpdate();
    	                                statement.executeUpdate();
    	                                //System.out.println(String.format("Done. %d rows inserted.", rows_inserted));
    	                                System.out.printf("Successfully Inserted Payment Details. \n" );
    	                            }
    	                        }
                    		}
                    	else if(payment9.toLowerCase().equals("card")) {
                    		
                    		System.out.println("Please enter Card Number:");
                        	 String card9= sc.nextLine();
                        	if(card9.isEmpty()) { card9 = null; }
                        	
                        	System.out.println("Please enter Card Type:");
                        	 String cardt9 = sc.nextLine(); 
                        	if(cardt9.isEmpty()) { cardt9 = null; }
                        	
                        	System.out.println("Please enter Card Expiration Date in MM/YYY:");
                        	 String cardexp9 = sc.nextLine(); 
                        	if(cardexp9.isEmpty()) { cardexp9 = null; }
                        	
                        	try (final Connection connection = DriverManager.getConnection(URL)) {
                                try (
                                    final PreparedStatement statement = connection.prepareStatement(QUERY9_4)) {
                                    // Populate the query template with the data collected from the user
	                                	statement.setString(1, name9);
	                            		statement.setString(2, date9);
		                                statement.setFloat(3, amt9);
		                                statement.setString(4, cname9);
		                                statement.setString(5, type9);
    	                                statement.setString(6, card9);
    	                                statement.setString(7, cardt9);
    	                                statement.setString(8, cardexp9);
    	                                
    	                                System.out.println("Dispatching the query...");
    	                                // Actually execute the populated query
    	                                //final int rows_inserted = statement.executeUpdate();
    	                                statement.executeUpdate();
    	                                //System.out.println(String.format("Done. %d rows inserted.", rows_inserted));
    	                                System.out.printf("Successfully Inserted Payment Details. \n" );
    	                            }
    	                        }
                    	}	
	                    }
                    	System.out.printf("Successfully Inserted Organization %s and it's Donations \n" , name9);
	                    break;
                case "10":  //Retrieve the name and phone number of the doctor of a particular client      
	                	System.out.println("Please enter Client SSN:");
	                    final int ssn10= Integer.parseInt(sc.nextLine());
	                   
	                    
	                    //System.out.println("Inserting Client into the database...");
	                    
	                    try (final Connection connection = DriverManager.getConnection(URL)) {
	                        try (
	                            final PreparedStatement statement = connection.prepareStatement(QUERY10)) {
	                            // Populate the query template with the data collected from the user
	                        	statement.setInt(1, ssn10);
	                            
	                            System.out.println("Dispatching the query...\n");
	                            // Actually execute the populated query
	                            final ResultSet rs = statement.executeQuery();
	                            
	                            System.out.println("Retriving details of Client Doctor:\n");
                                System.out.println("Doctor_Name\tPhone_Number");
	                            while (rs.next()) {
                                    System.out.println(String.format("%s\t\t%s ",
                                        rs.getString(1),
                                        rs.getString(2)));
                                }
	                            //System.out.println(String.format("Donor %s inserted successfully. \n", ssn8));
	                        }
	                    }
	                    break;
	                    
                case "11":  //Retrieve the total amount of expenses charged by each employee for a particular period of time. 
                			//The list should be sorted by the total amount of expenses    
                	
                	System.out.println("Please enter start date in MM/DD/YYY:");
                	 String sdate11 = sc.nextLine(); 
                	if(sdate11.isEmpty()) { sdate11 = null; }
                   
                	System.out.println("Please enter end date in MM/DD/YYY:");
                	 String edate11 = sc.nextLine(); 
                	if(edate11.isEmpty()) { edate11 = null; }
                    
                    //System.out.println("Inserting Client into the database...");
                    
                    try (final Connection connection = DriverManager.getConnection(URL)) {
                        try (
                            final PreparedStatement statement = connection.prepareStatement(QUERY11)) {
                            // Populate the query template with the data collected from the user
                        	statement.setString(1, sdate11);
                        	statement.setString(2, edate11);
                            
                            System.out.println("Dispatching the query...\n");
                            // Actually execute the populated query
                            final ResultSet rs = statement.executeQuery();
                            
                            System.out.printf("Retriving expenses charged by employees between %s and %s: \n", sdate11, edate11 );
                            System.out.println("Employee_SSN\tTotal_Expenses");
                            while (rs.next()) {
                                System.out.println(String.format("%s\t\t%s ",
                                    rs.getString(1),
                                    rs.getString(2)));
                            }
                           
                        }
                    }
                    break;
                	
                case "12":  //Retrieve the list of volunteers that are members of teams that support a particular client
		        	
                	System.out.println("Please enter Client SSN:");
                    final int ssn12= Integer.parseInt(sc.nextLine());
		            
		            //System.out.println("Inserting Client into the database...");
		            
		            try (final Connection connection = DriverManager.getConnection(URL)) {
		                try (
		                    final PreparedStatement statement = connection.prepareStatement(QUERY12)) {
		                    // Populate the query template with the data collected from the user
		                	statement.setInt(1, ssn12);
		                    
		                    System.out.println("Dispatching the query...\n");
		                    // Actually execute the populated query
		                    final ResultSet rs = statement.executeQuery();
		                    
		                    System.out.printf("Retriving list of volunteers that are members of teams that support a client %d: \n", ssn12 );
		                    System.out.println("Volunteer_SSN");
		                    while (rs.next()) {
		                        System.out.println(String.format("%s ",
		                            rs.getString(1)));
		                    }
		                   
		                }
		            }
		            break;
                case "13":  //Retrieve the names and contact information of the clients 
                			//that are supported by teams sponsored by an organization whose name 
                			//starts with a letter between B and K. The client list should be sorted by name
		        	
		            //System.out.println("Inserting Client into the database...");
		            
		            try (final Connection connection = DriverManager.getConnection(URL)) {
		                try (
		                    final PreparedStatement statement = connection.prepareStatement(QUERY13)) {
		                    // Populate the query template with the data collected from the user
		                	
		                    
		                    System.out.println("Dispatching the query...\n");
		                    // Actually execute the populated query
		                    final ResultSet rs = statement.executeQuery();
		                    
		                    System.out.println("Retriving the names and contact information of the clients supported by teams "
		                    		+ "sponsored by an organization whose name starts with a letter between B and K : \n");
		                    System.out.println("Client_Name\tAddress\t\tEmail\tHome_Phone\tCell_Phone\tWork_Phone");
		                    while (rs.next()) {
		                        System.out.println(String.format("%s\t\t%s\t\t%s\t\t%s\t\t%s\t\t%s",
		                            rs.getString(1),
		                            rs.getString(2),
		                            rs.getString(3),
		                            rs.getString(4),
		                            rs.getString(5),
		                            rs.getString(6)));
		                    }
		                   
		                }
		            }
		            break;
		            
                case "14":  //Retrieve the name and total amount donated by donors that are also employees. 
                			//The list should be sorted by the total amount of the donations, and 
                			//indicate if each donor wishes to remain anonymous
		        	
		            //System.out.println("Inserting Client into the database...");
		            
		            try (final Connection connection = DriverManager.getConnection(URL)) {
		                try (
		                    final PreparedStatement statement = connection.prepareStatement(QUERY14)) {
		                    // Populate the query template with the data collected from the user
		                	
		                    
		                    System.out.println("Dispatching the query...\n");
		                    // Actually execute the populated query
		                    final ResultSet rs = statement.executeQuery();
		                    
		                    System.out.println("Retriving the names and amount donated by donars who are also employees \n");
		                    System.out.println("Donor_Name\tTotal_Amount\tIs_Anonymous");
		                    while (rs.next()) {
		                        System.out.println(String.format("%s\t\t%s\t\t%s",
		                            rs.getString(1),
		                            rs.getString(2),
		                            rs.getString(3)));
		                    }
		                   
		                }
		            }
		            break;
		            
                case "15":  //Retrieve the names of all teams that were founded after a particular date
                	
                	System.out.println("Please enter the date in MM/DD/YYY:");
                	String date15 = sc.nextLine(); 
                	if(date15.isEmpty()) { date15 = null; }
        	
		            //System.out.println("Inserting Client into the database...");
		            
		            try (final Connection connection = DriverManager.getConnection(URL)) {
		                try (
		                    final PreparedStatement statement = connection.prepareStatement(QUERY15)) {
		                    // Populate the query template with the data collected from the user
		                	
		                	statement.setString(1, date15);
		                    
		                    System.out.println("Dispatching the query...\n");
		                    // Actually execute the populated query
		                    final ResultSet rs = statement.executeQuery();
		                    
		                    System.out.printf("Retriving the list of teams that are formed after %s \n", date15);
		                    System.out.println("\nTeam_Name");
		                    while (rs.next()) {
		                        System.out.println(String.format("%s",
		                            rs.getString(1)));
		                    }
		                   
		                }
		            }
		            break;
		            
                case "16":  //Increase the salary by 10% of all employees to whom more than one team must report
                	
                	
		            //System.out.println("Inserting Client into the database...");
		            
		            try (final Connection connection = DriverManager.getConnection(URL)) {
		                try (
		                    final PreparedStatement statement = connection.prepareStatement(QUERY16)) {
		                    // Populate the query template with the data collected from the user
		                	
		                
		                    System.out.println("Dispatching the query...\n");
		                    // Actually execute the populated query
		                    statement.execute();
		                   
		                    
		                    System.out.println("Successfully updated the salary of the employees \n" );
//		                    System.out.println("Retriving the list of teams that are formed after %s \n" );
//		                    System.out.println("Team_Name");
//		                    while (rs.next()) {
//		                        System.out.println(String.format("%s",
//		                            rs.getString(1)));
//		                    }
//		                   
		                }
		            }
		            break;
		            
                case "17":  //Delete all clients who do not have health insurance and 
                			//whose value of importance for transportation is less than 5
                	
                	
		            //System.out.println("Inserting Client into the database...");
		            
		            try (final Connection connection = DriverManager.getConnection(URL)) {
		                try (
		                    final PreparedStatement statement = connection.prepareStatement(QUERY17)) {
		                    // Populate the query template with the data collected from the user
		                	
		                
		                    System.out.println("Dispatching the query...\n");
		                    // Actually execute the populated query
		                    statement.execute();
		                   
		                    
		                    System.out.println("Successfully Deleted the Clients who do not have Health Insurance "
		                    		+ "and whose importance value for transortation is less than 5 \n" );
//		                    System.out.println("Retriving the list of teams that are formed after %s \n" );
//		                    System.out.println("Team_Name");
//		                    while (rs.next()) {
//		                        System.out.println(String.format("%s",
//		                            rs.getString(1)));
//		                    }
//		                   
		                }
		            }
		            break;
		            
                case "18": //Import: enter new teams from a data file until the file is empty
                    System.out.println("Please enter file name:");
                    final String fname18 = sc.nextLine(); //Read user input of file name
                    
                    File file=new File("/Users/madishetti/Documents/Master's/DB/Individual Project/"+fname18); //File location
					try {
						@SuppressWarnings("resource")
						Scanner fsc=new Scanner(file);  //Initializing scanner to read file
//						fsc.useDelimiter("\\z");
//	                    System.out.println(fsc.next());
						 while(fsc.hasNextLine()){
					            //System.out.println(fsc.nextLine());
					            String values[] = fsc.nextLine().split("\\t");  //Splitting lines using delimiter to extract attribute values
					            
					            //System.out.printf("%s, %s, %s",values[0], values[1], values[2]);
					            
					            //Inserting the data into database
					            try (final Connection connection = DriverManager.getConnection(URL)) {
			                        try (
			                            final PreparedStatement statement = connection.prepareStatement(QUERY1)) {
			                            // Populate the query template with the data collected from the user
			                            statement.setString(1, values[0]);
			                            statement.setString(2, values[1]);
			                            statement.setString(3, values[2]);

			                            //System.out.println("Dispatching the query...");
			                            // Actually execute the populated query
			                            statement.executeUpdate();
			                            System.out.println("1 row inserted");
			                        }
			                    }
					        }
						 System.out.println("Successfully inserted Teams from given text file");
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
                    break;
                    
                case "19": //Export: Retrieve names and mailing addresses of all people on the mailing list 
                			//and output them to a data file instead of screen
                    System.out.println("Please enter file name:");
                    final String fname19 = sc.nextLine(); //Read user input of file name
                    
					try {
						@SuppressWarnings("resource")
						Writer write=new FileWriter("/Users/madishetti/Documents/Master's/DB/Individual Project/"+fname19); //File location
						
						//Extracting the data from Database
						try (final Connection connection = DriverManager.getConnection(URL)) {
			                try (
			                    final PreparedStatement statement = connection.prepareStatement(QUERY19)) {
			                    // Populate the query template with the data collected from the user
			                	
			                	System.out.println("Retriving the list from DB \n");
			                    // Actually execute the populated query
			                    final ResultSet rs = statement.executeQuery();  //storing the extracted data
			                    
			                    System.out.println("Writing the data to a file \n");
			                    //System.out.println("Donor_Name\tTotal_Amount\tIs_Anonymous");
			                    //Writing the extracted data to a file
			                    while (rs.next()) {
			                        String str = rs.getString(1)+"\t"+rs.getString(2);
			                        
			                        System.out.printf("%s\n", str);
			                        
			                        write.write(str);
			                        write.write("\n");
			                    }
			                   write.close();
			                   System.out.printf("\nSuccessfully exported data to %s\n", fname19);
			                }
			            }
						
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
                    break;    
                case "20": // Do nothing, the while loop will terminate upon the next iteration
                    System.out.println("Exiting! Goodbuy!");
                    break;
                    
                default: // Unrecognized option, re-prompt the user for the correct one
                    System.out.println(String.format(
                        "Unrecognized option: %s\n" + 
                        "Please try again!", 
                        option));
                    break;
            }
        }

        sc.close(); // Close the scanner before exiting the application
    }
}
