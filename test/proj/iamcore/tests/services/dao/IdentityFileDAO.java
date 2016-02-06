/**
 * 
 */
package proj.iamcore.tests.services.dao;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import iamcore.datamodel.Identity;
import proj.iamcore.tests.services.match.Matcher;
import proj.iamcore.tests.services.match.impl.StartsWithIdentityMatchStrategy;

/**
 * @author tbrou
 *
 */
public class IdentityFileDAO {

	// TODO change this path, make it configurable
	private final static String filePath = "/tests/iam/identities.txt";

	// Writer to insert identities into the file
	private PrintWriter writer;

	// Used to get the data from the file
	private Scanner scanner;
	
	//TODO we would like this to be configuration driven
	private Matcher<Identity> matcher = new StartsWithIdentityMatchStrategy();

	public IdentityFileDAO() throws IOException {

		File file = ensureFileExists(filePath);
		// open in write mode, but with append directive activated (the "true"
		// parameter)
		initIO(file);

	}

	private void initIO(File file) throws FileNotFoundException {
		this.writer = new PrintWriter(new FileOutputStream(file, true));
		this.scanner = new Scanner(file);
	}

	public void create(Identity identity) {
		writeAnIdentity(identity, this.writer);
	}
	
	private void writeAnIdentity(Identity identity, PrintWriter aWriter){
		aWriter.println("--- identity:begin ---");
		aWriter.println(identity.getDisplayName());
		aWriter.println(identity.getEmailAddress());
		aWriter.println(identity.getUid());
		aWriter.println("--- identity:end ---");
		aWriter.flush();
	}

	public List<Identity> search(Identity criteria) {
		// Create the result structure
		List<Identity> identities = new ArrayList<Identity>();

		// While we have some remaining lines to read
		while (this.scanner.hasNext()) {
			Identity identity = readAnIdentity();
			
			//check if the identity is matching the criteria
			if (this.matcher.match(criteria,identity)){
				// put that newly created identity in the list
				identities.add(identity);
			}
			
		}
		// reset the scanner so we will be able to read from the beginning of
		// the file in the next call
		this.scanner.reset();
		return identities;

	}


	public List<Identity> readAll() {
		// Create the result structure
		List<Identity> identities = new ArrayList<Identity>();

		// While we have some remaining lines to read
		while (this.scanner.hasNext()) {
			Identity identity = readAnIdentity();
			// put that newly created identity in the list
			identities.add(identity);
		}
		// reset the scanner so we will be able to read from the beginning of
		// the file in the next call
		this.scanner.reset();
		return identities;
	}

	private Identity readAnIdentity() {
		this.scanner.nextLine(); // we do nothing with this line because it is
							// "--- identity:begin ---"
		String displayName = this.scanner.nextLine();
		String emailAddress = this.scanner.nextLine();
		String uid = this.scanner.nextLine();
		this.scanner.nextLine(); // we do nothing with this line because it is
							// "--- identity:end ---"
		// create an identity with the read properties
		Identity identity = new Identity(displayName, emailAddress, uid);
		return identity;
	}

	public void update(Identity identity) throws IOException {
		//be ready to copy into a new file
		File newFile = ensureFileExists(filePath + "-new");
		PrintWriter newWriter = new PrintWriter(newFile);
		
		//1 - recopy everything except the given identity
		while(this.scanner.hasNext()){
			Identity readIndentity = readAnIdentity();
			if (!readIndentity.getUid().equals(identity.getUid())){
				writeAnIdentity(readIndentity, newWriter);
			}
		}
		
		//2 - append the updated version of the identity
		writeAnIdentity(identity, newWriter);
		
		//3 - Close everything and delete the file
		this.scanner.close();
		this.writer.close();
		newWriter.close();
		
		File file = new File(filePath);
		Path oldFilePath = file.toPath();
		Files.delete(oldFilePath);
		
		//4 - move the new file to the old file location
		Files.move(newFile.toPath(), oldFilePath);

		initIO(file);
		
		
	}

	public void delete(Identity identity) throws IOException {
		//be ready to copy into a new file
				File newFile = ensureFileExists(filePath + "-new");
				PrintWriter newWriter = new PrintWriter(newFile);
				
				//1 - recopy everything except the given identity
				while(this.scanner.hasNext()){
					Identity readIndentity = readAnIdentity();
					if (!readIndentity.getUid().equals(identity.getUid())){
						writeAnIdentity(readIndentity, newWriter);
					}
				}
				
				//3 - Close everything and delete the file
				this.scanner.close();
				this.writer.close();
				newWriter.close();
				
				File file = new File(filePath);
				Path oldFilePath = file.toPath();
				Files.delete(oldFilePath);
				
				//4 - move the new file to the old file location
				Files.move(newFile.toPath(), oldFilePath);

				initIO(file);
	}

	/**
	 * This method will check that the file exists or create it if it doesn't
	 * 
	 * @param pathname
	 * @throws IOException
	 */
	public static File ensureFileExists(String pathname) throws IOException {
		File file = new File(pathname);
		if (!file.exists()) {
			// creation code after
			System.out.println("the file does not exists");
			File parent = file.getParentFile();
			if (!parent.exists()) {
				parent.mkdirs();
			}
			file.createNewFile();
			System.out.println("file was successfully created");
		} else {
			System.out.println("the file already exists");
		}
		return file;
	}

}
