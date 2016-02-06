package iamcore.services.dao;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import iamcore.datamodel.Identity;


/**
 * The DAO for the Identity
 * Create , Update, Search and Delete methods
 * */
public interface IdentityDAO {

	public List<Identity> search(Identity criteria);
	
	public void create(Identity identity);
	
	public void update(Identity identity) throws IOException, ParseException;
	
	public void delete(Identity identity) throws IOException;
	
	
	
}
