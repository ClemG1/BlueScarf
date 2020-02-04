package localSystem;

import java.io.*;
import java.util.Scanner;

public class LocalFilesManager {
	
	//Attributes
	private File localFile; //use to stock the File that this class creates
	
	/**
	  * @brief : class constructor
	  * @param : name of the file
	  * @returns : none
	 **/
	public LocalFilesManager (String name, String path) {
		try {
			File file = new File(path + name);
			file.createNewFile();
			this.localFile = file;
		}
		catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
		}
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
	public void createFile() {
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
			Scanner bufferIn = new Scanner(this.localFile);
			//reading loop
			while (bufferIn.hasNextLine()) {
				String data = bufferIn.nextLine();
				message = message.concat(data);
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
	  * @brief : delete a line in a file
	  * @param : the file, the string to delete, the separator in the file
	  * @returns : none
	 **/
	public void deleteInFile (String toDelete) {
		
		try {
			manageReadPermission();
			FileReader bufferIn = new FileReader(this.localFile);
			String toModify = readAllFile();
			String newData = "";
			String dataParts[] = toModify.split("-");
			for(int k =  0; k < dataParts.length-1; k++) {
				if(! dataParts[k].contains(toDelete)) {
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
	
	public String[] findFilesInDirectory() {
		try {
			if(this.localFile.isDirectory()) {
				System.out.println("This is the list of files that I found : " + this.localFile.list());
				return this.localFile.list();
			}
			else {
				return null;
			}
		}
		catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
			return null;
		}
	}

}
