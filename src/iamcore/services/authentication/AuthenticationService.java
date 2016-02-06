package iamcore.services.authentication;

public class AuthenticationService {

	public boolean authenticate(String login, String password) {
		/**
		 * Checks if the user input is equal to the login username and password 
		 * mentioned within the if condition
		 * If true, the boolean function returns true 
		 * and If false, it returns false 
		 * back to the main function
		 */
		if(login.equals("root") && password.equals("root"))
		{
			return true;
		}
		else
		{
			return false;
		
		}
	}

}
