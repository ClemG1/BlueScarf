package localSystem;

import java.io.*;

public class LocalFilesManager {
	
	//Attributes
	private String path; //path to the file
	private String name; //name of the file
	private File localFile; //use to stock the File that this class creates
	
	/**
	  * @brief : class constructor
	  * @param : name of the file
	  * @returns : none
	 **/
	public LocalFilesManager (String name, String path) {
		this.path = path;
		this.name = name;
		File file = new File(path + name);
		this.localFile = file;
	}
	
	/**
	  * @brief : use to find the path of the current directory
	  * @param : none
	  * @returns : a path
	 **/
	public static String getPath() {
		return System.getProperty("user.dir") + "/configFiles/" ;
	}
	
	/**
	  * @brief : create a new file
	  * @param : none
	  * @returns : none
	 **/
	private void createFile() {
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
	
	/**
	  * @brief : delete a file
	  * @param : none
	  * @returns : none
	 **/
	public void deleteFile () {
		if (! this.localFile.delete()) {
			System.out.println("The file hasn't been deleted.");
		}
	}
	
	/**
	  * @brief : checkout if the file is writable, otherwise the file is set writable
	  * @param : a file
	  * @returns : none
	 **/
	private void manageWritePermission () {
		if(! this.localFile.canWrite()) {
			this.localFile.setWritable(true);
		}
	}
	
	/**
	  * @brief : write a string into a file
	  * @param : the string to write
	  * @returns : none
	  * @note : if the file doesn't exist it is created
	 **/
	public void write (String toWrite , char separator) {
		if (!this.localFile.exists()) {
			createFile();
		}
		manageWritePermission();
		try {
			FileWriter bufferOut = new FileWriter(this.localFile,true);
			bufferOut.write(toWrite + separator);
			bufferOut.flush();
			bufferOut.close();
		}
		catch (IOException ioe) {
			System.out.println(ioe.toString());
			ioe.printStackTrace();
		}
	}
	
	public void overwrite (String toWrite , char separator) {
		if (!this.localFile.exists()) {
			createFile();
		}
		manageWritePermission();
		try {
			FileWriter bufferOut = new FileWriter(this.localFile,false);
			bufferOut.write(toWrite + separator);
			bufferOut.flush();
			bufferOut.close();
		}
		catch (IOException ioe) {
			System.out.println(ioe.toString());
			ioe.printStackTrace();
		}
	}
	
	/**
	  * @brief : checkout if the file is writable, otherwise the file is set writable
	  * @param : a file
	  * @returns : none
	 **/
	private void manageReadPermission () {
		if(! this.localFile.canRead()) {
			this.localFile.setReadable(true);
		}
	}
	
	/**
	  * @brief : write a string into a file
	  * @param : the file, the string to write
	  * @returns : none
	 **/
	public String readAllFile () {
		try {
			String message = "";
			manageReadPermission();
			FileReader bufferIn = new FileReader(this.localFile);
			//reading loop
			int character;
			while ( (character = (bufferIn.read())) != -1) {
				message = message + (char) character;
			}

			bufferIn.close();
			return message;
		}
		catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	  * @brief : write a string into a temporary file
	  * @param : the temporary file, the string to write, a separator
	  * @returns : none
	  * @note : if the file doesn't exist it is created
	 **/
	private void writeTempFile (File file, String toWrite , char separator) {
		if (!file.exists()) {
			createFile();
		}
		manageWritePermission();
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
	
	/**
	  * @brief : delete a line in a file
	  * @param : the file, the string to delete, the separator in the file
	  * @returns : none
	 **/
	public void deleteInFile (String toDelete, char separator) {
		
		try {
			manageReadPermission();
			FileReader bufferIn = new FileReader(this.localFile);
			String toModify = readAllFile();
			String newData = "";
			String dataParts[] = toModify.split("-");
			for(int k =  0; k < dataParts.length-1; k++) {
				if(! dataParts[k].equals(toDelete)) {
					newData += dataParts[k] + "-";
				}
			}
			overwrite(newData, '\0');
			bufferIn.close();
		}
		catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
		}
	}

}
