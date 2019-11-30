package localSystem;

import java.io.*;

public class LocalFilesManager extends Thread {
	
	//Attributes
	private String path; //path to the file
	private String name; //name of the file
	private File localFile; //use to stock the File that this class creates
	private String message; //string that you want to update (write or delete)
	private char separator; //separator used in the file
	
	/*
	 * @brief : class constructor
	 * @param : name of the file
	 * @returns : none
	 */
	public LocalFilesManager (String name, String path, String message, char separator) {
		this.path = path;
		this.name = name;
		File file = new File(path + name);
		this.localFile = file;
		this.message = message;
		this.separator = separator;
		start();
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
	
	/*
	 * @brief : delete a line in a file
	 * @param : the file, the string to delete, the separator in the file
	 * @returns : none
	 */
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
	 * @brief : run function of the thread
	 * @param : none
	 * @returns: none
	 */
	public void run () {
		createFile(this.localFile);
		write(this.localFile, "test1", '-');
		write(this.localFile, this.message, this.separator);
		write(this.localFile, "test3", '-');
		readAllFile(this.localFile);
		deleteInFile(this.localFile, this.message, this.separator);
		readAllFile(this.localFile);
		deleteFile(this.localFile);
	}

}
