package iamcore.services.dao;

import java.io.IOException;
import java.util.List;

import iamcore.datamodel.Identity;


/**
 * TODO document
 * 
 *
 */
public interface IdentityDAO {

	public List<Identity> search(Identity criteria);
	
	public void create(Identity identity);
	
	public void update(Identity identity) throws IOException;
	
	public void delete(Identity identity) throws IOException;
	
	
	
}
