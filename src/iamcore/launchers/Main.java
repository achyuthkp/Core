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

		// Authentication
		System.out.println("Please enter your login");
		String login = scanner.nextLine();
		System.out.println("Please enter your password");
		String password = scanner.nextLine();
		
		AuthenticationService as = new AuthenticationService();
		IdentityDAO dao = new IdentityXmlDAO();
		boolean authenticated = as.authenticate(login, password);
		
		// end of Authentication
		if (authenticated) {

			// Menu
			System.out.println("You've been granted access to use the application");
			String answer = null;
			while (!"e".equals(answer)) {
				System.out.println("Please select an action that you wish to implement : ");
				System.out.println("- Create (c)");
				System.out.println("- Search (s)");
				System.out.println("- Update (u)");
				System.out.println("- Delete (d)");
				System.out.println("- Exit (e)");
				System.out.println("What's your choice (c|s|e|u|d)?");
				answer = scanner.nextLine();
				switch (answer) {
				case "c":
					// Create
					// read the parameters from the console
					Identity id1 = getIdentityFromInput(scanner);
					dao.create(id1);
					break;

				case "s":
					//Search
					Identity id2 = getIdentityFromInput(scanner);
					List<Identity> list =  dao.search(id2);
					for(int i=0;i<list.size();i++)
					{
						 System.out.println("Identity"+i+list.get(i));
						
					}
					
					
					
 					break;
				
				case "u":
					//Update
					
					break;
				
				case "d":
					//Delete
					System.out.println("Enter the ID of the Identity to be deleted:");
				//	String uid = scanner.nextLine();
					//Identity id4;
					
					//dao.delete(id4);
					//call search function
					//add the return value received from search to delete identity
					//dao.delete(id);
					break;
					
				default: 
					break;
				}

			}
			// Finish
		}
		System.out.println("End of the program");

		scanner.close();
	}
private static Identity getIdentityFromInput(Scanner scanner) throws ParseException
{
		
	System.out.println("Create an Identity:");
	System.out.println("Enter Name of Identity:");
	String name = scanner.nextLine();
	System.out.println("Enter Email ID of Identity:");
	String email = scanner.nextLine();
	System.out.println("Enter ID of Identity:");
	String uid = scanner.nextLine();
	System.out.println("Enter Birth Date of Identity:");
	String dateString = scanner.nextLine();
	DateFormat formatter = new SimpleDateFormat("dd/mm/yyyy");
	Date birthdate = formatter.parse(dateString);
	Identity identity = new Identity(name, email, uid, birthdate);
	System.out.println("Thank you, you have input those information:");
	//System.out.println(identity);
	return identity;
}
}
