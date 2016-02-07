package iamcore.launchers;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import iamcore.datamodel.Identity;
import iamcore.services.authentication.AuthenticationService;
import iamcore.services.dao.IdentityDAO;
import iamcore.services.dao.IdentityXmlDAO;

public class Main {

	public static void main(String[] args) throws  IOException, ParseException {

		System.out.println("Welcome to the Identity Management System");

		Scanner scanner = new Scanner(System.in);

		/**
		 * Authentication method is invoked after login information is provided. 
		 * login and password are passed as arguments into the authenticate method. 
		 * the method returns a boolean value which is stored into the variable authenticated
		 * If the value is false, the program is not executed any further. 
		 */
		
		System.out.println("Please enter your login");
		String login = scanner.nextLine();
		System.out.println("Please enter your password");
		String password = scanner.nextLine();
		
		AuthenticationService as = new AuthenticationService();
		IdentityDAO dao = new IdentityXmlDAO();
		boolean authenticated = as.authenticate(login, password);
		
		// end of Authentication
		if (authenticated) {
			/**
			 * Menu for User to access the functionalities of the application
			 * Functionalities of the application are : 
			 * Create, Search, Update, Delete
			 * The user can continue to use the menu until he/she no longer needs to use the functions
			 * of the application
			 */
			System.out.println("You've been granted access to use the application");
			String answer = null;
			String con = "y";
			while ("y".equals(con)) 
			{
				System.out.println("Please select an action that you wish to implement : ");
				System.out.println("- Create (c)");
				System.out.println("- Search (s)");
				System.out.println("- Update (u)");
				System.out.println("- Delete (d)");
				System.out.println("- Exit (e)");
				System.out.println("What's your choice (c|s|u|d|e)?");
				answer = scanner.nextLine();
				switch (answer) {
				case "c":
					/**
					 * Create section of the application
					 * getIdentityFromInput method is called to store properties of the class Identity. 
					 * It returns the identity to id1
					 * id1 is sent as an argument within the create function
					 */
					System.out.println("Create an Identity:");
					Identity id1 = getIdentityFromInput(scanner);
					dao.create(id1);
					break;

				case "s":
					/**
					 * Search section of the application 
					 * name and email are taken as input
					 * A new Identity id2 is created
					 * setDisplayName method is called to save the value of name to DisplayName
					 * setEmailAddress method is called to save the value of email to Email
					 * id2 is sent as an argument to the search method
					 * The search method returns a List<Identity> object which is stored inside list 
					 * A for loop is created to read the data within the list value  
					 * 
					 */
					System.out.println("Enter Name of Identity:");
					String name = scanner.nextLine();
					System.out.println("Enter Email ID of Identity:");
					String email = scanner.nextLine();
					Identity id2 = new Identity();
					id2.setDisplayName(name);
					id2.setEmailAddress(email);
					List<Identity> list =  dao.search(id2);
					for( Identity ide : list)
					{
						 System.out.println("Identity details:");
						 System.out.println("Name:");
						 System.out.println(ide.getDisplayName());
						 System.out.println("Email:");
						 System.out.println(ide.getEmailAddress());
						 System.out.println("User ID:");
						 System.out.println(ide.getUid());
						 System.out.println("Birth Date:");
						 System.out.println(ide.getBirthDate());
					}
					break;
				
				case "u":
					/**
					 * Update Section of the Application :
					 * The uid value is taken as input from the user
					 * A new identity id3 is created 
					 * setUid method is used to set the uid value to the property of id3
					 * uid value is given as the argument for setUid method
					 * update method with argument id3 is called to update the identity
					 */
					System.out.println("Enter UID of Identity to be updated:");
					String uid = scanner.nextLine();
					Identity id3 = new Identity();
					id3.setUid(uid);
					dao.update(id3);
					break;
				
				case "d":
					/**
					 * Delete Section of the Application :
					 * The uid is taken as an input
					 * the value is passed as argument into setUid() to store it within Identity id4
					 * The delete method is called with argument id4 to delete the identity
					 */
					System.out.println("Enter the UID of the Identity to be deleted:");
					uid = scanner.nextLine();
					Identity id4 = new Identity();
					id4.setUid(uid);
					dao.delete(id4);
					
					break;
					
				default: 
					break;
				}
				System.out.println("Do you wish to continue ( y/n )?");
				con = scanner.nextLine();
			}
			// Finish
		}
		System.out.println("End of the program");

		scanner.close();
	}
private static Identity getIdentityFromInput(Scanner scanner) throws ParseException
{
	/**
	 * This method is used to read the fields of the Identity 
	 * Parse String dateString into Date birthdate  
	 * The method then returns the identity
	 */
	
	System.out.println("Enter Name of Identity:");
	String name = scanner.nextLine();
	System.out.println("Enter Email ID of Identity:");
	String email = scanner.nextLine();
	System.out.println("Enter UID of Identity:");
	String uid = scanner.nextLine();
	System.out.println("Enter Birth Date of Identity:");
	String dateString = scanner.nextLine();
	DateFormat formatter = new SimpleDateFormat("dd/mm/yyyy");
	Date birthdate = formatter.parse(dateString);
	Identity identity = new Identity(name, email, uid, birthdate);
	System.out.println("Thank you, you have input those information:");
	return identity;
}
}
