package fr.tbr.iamcore.services.authentication;

public class AuthenticationService {

	public boolean authenticate(String login, String password) {
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
