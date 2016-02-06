package iamcore.launchers;

import java.util.Scanner;

import iamcore.datamodel.Identity;
import iamcore.services.authentication.AuthenticationService;
import iamcore.services.dao.IdentityDAO;
import iamcore.services.dao.IdentityXmlDAO;

public class Main {

	public static void main(String[] args) {

		System.out.println("Welcome to the IAM System");

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

					// ...
					// read the parameters from the console
					// ...

					// Identity identity = new Identity(displayName,
					// emailAddress, uid):
					// dao.create(identity);
					break;

				case "s":
					// Search
					break;
				
				case "u":
					//Update
					break;
				
				case "d":
					//Delete
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
