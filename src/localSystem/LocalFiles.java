package localSystem;

import java.io.*;

public class LocalFiles {
	
	//Attributes
	private String path;
	private String name;
	private File localFile;
	
	/*
	 * @brief : class constructor
	 * @param : name of the file
	 * @returns : none
	 */
	public LocalFiles (String name, String path) {
		this.path = path;
		this.name = name;
		File file = new File(path + name);
		this.localFile = file;
	}
	
	/*
	 * @brief : create a new file
	 * @param : the file to create
	 * @returns : none
	 */
	private void createFile(File file) {
		try {
			if(! localFile.createNewFile()) {
				System.out.println("The file name is already used.");
			}
		}
		catch (IOException ioe) {
			System.out.println(ioe.toString());
			ioe.printStackTrace();
		}
	}
	
	/*
	 * @brief : delete a file
	 * @param : the file
	 * @returns : none
	 */
	private void deleteFile (File file) {
		if (! file.delete()) {
			System.out.println("The file hasn't been deleted.");
		}
	}
	
	/*
	 * @brief : checkout if the file is writable, otherwise the file is set writable
	 * @param : a file
	 * @returns : none
	 */
	private void manageWritePermission (File file) {
		if(! file.canWrite()) {
			file.setWritable(true);
		}
	}
	
	/*
	 * @brief : write a string into a file
	 * @param : the file, the string to write
	 * @returns : none
	 */
	private void write (File file, String toWrite , char separator) {
		manageWritePermission(file);
		try {
			FileWriter bufferOut = new FileWriter(file,true);
			bufferOut.write(toWrite + separator);
			bufferOut.flush();
			bufferOut.close();
		}
		catch (IOException ioe) {
			System.out.println(ioe.toString());
			ioe.printStackTrace();
		}
	}
	
	/*
	 * @brief : checkout if the file is writable, otherwise the file is set writable
	 * @param : a file
	 * @returns : none
	 */
	private void manageReadPermission (File file) {
		if(! file.canRead()) {
			file.setReadable(true);
		}
	}
	
	/*
	 * @brief : write a string into a file
	 * @param : the file, the string to write
	 * @returns : none
	 */
	private String readAllFile (File file) {
		String message = "";
		manageReadPermission(file);
		try {
			FileReader bufferIn = new FileReader(file);
			//reading loop
			int character;
			while ( (character = (bufferIn.read())) != -1) {
				message = message + (char) character;
			}
			
			bufferIn.close();
		}
		catch (FileNotFoundException fnfe) {
			System.out.println(fnfe.toString());
			fnfe.printStackTrace();
		}
		catch (IOException ioe) {
			System.out.println(ioe.toString());
			ioe.printStackTrace();
		}
		return message;
	}
	
	private void deleteInFile (File file, String toDelete, char separator) {
		manageReadPermission(file);
		File tempFile = new File(this.path + "temp" + this.name);
		createFile(tempFile);
		try {
			FileReader bufferIn = new FileReader(file);
			String currentMessage = "";
			//reading loop
			int character;
			while ( (character = (bufferIn.read())) != -1) { //for all the file
				
				if(character != separator) { //if the current char isn't a char
					currentMessage = currentMessage + (char) character;
				}
				else { //we reached the end of the message
					if (! currentMessage.equals(toDelete)) {
						write(tempFile,currentMessage,separator);
					}
					currentMessage = "";
				}
			}
			
			bufferIn.close();
			deleteFile(file);
			tempFile.renameTo(this.localFile);
			
		}
		catch (FileNotFoundException fnfe) {
			System.out.println(fnfe.toString());
			fnfe.printStackTrace();
		}
		catch (IOException ioe) {
			System.out.println(ioe.toString());
			ioe.printStackTrace();
		}
	}
	
	/*
	 * @brief : test function of our functionalities
	 * @param : none
	 * @returns: none
	 */
	public void test (String toWrite, char separator) {
		createFile(this.localFile);
		write(this.localFile,toWrite,separator);
		write(this.localFile,"Denis",separator);
		write(this.localFile,"Jacques",separator);
		deleteInFile(this.localFile,"Denis",separator);
		System.out.println(readAllFile(this.localFile));
		deleteFile(this.localFile);
	}

}
