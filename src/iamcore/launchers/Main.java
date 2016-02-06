package iamcore.launchers;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import iamcore.datamodel.Identity;
import iamcore.services.authentication.AuthenticationService;
import iamcore.services.dao.IdentityDAO;
import iamcore.services.dao.IdentityXmlDAO;

public class Main {

	public static void main(String[] args) throws ParseException {

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
				System.out.println("choose an action ");
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
					System.out.println("Create an Identity:");
					System.out.println("Enter Name of Identity:");
					String name = scanner.nextLine();
					System.out.println("Enter ID of Identity:");
					String uid = scanner.nextLine();
					System.out.println("Enter Email ID of Identity:");
					String email = scanner.nextLine();
					System.out.println("Enter Birth Date of Identity:");
					String dateString = scanner.nextLine();
					DateFormat formatter = new SimpleDateFormat("dd/mm/yyyy");
					Date date = formatter.parse(dateString);
					// read the parameters from the console
					Identity id = new Identity(name,uid,email,date);
					dao.create(id);
					break;

				case "s":
					//Search
					break;
				
				case "u":
					//Update
					
					break;
				
				case "d":
					//Delete
					System.out.println("Enter the ID of the Identity to be deleted:");
					uid = scanner.nextLine();
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

}
