package localSystem;

import java.io.*;
import java.util.Scanner;

public class LocalFilesManager {
	
	//Attributes
	private File localFile; //use to stock the File that this class creates
	
	/**
	  * class constructor
	  * @param : name of the file
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
	  * use to find the path of the current directory
	  * @return : a path
	 **/
	public static String getPath() {
		return System.getProperty("user.dir") + "/configFiles/" ;
	}
	
	/**
	  * create a new file
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
	  * delete a file
	 **/
	public void deleteFile () {
		if (! this.localFile.delete()) {
			System.out.println("The file hasn't been deleted.");
		}
	}
	
	/**
	  * checkout if the file is writable, otherwise the file is set writable
	  * @param : a file
	 **/
	private void manageWritePermission () {
		if(! this.localFile.canWrite()) {
			this.localFile.setWritable(true);
		}
	}
	
	/**
	  * Write a string into a file. If the file doesn't exist it is created
	  * @param : the string to write
	 **/
	public void write (String toWrite , String separator) {
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
	
	public void overwrite (String toWrite , String separator) {
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
	  * checkout if the file is writable, otherwise the file is set writable
	  * @param : a file
	 **/
	private void manageReadPermission () {
		if(! this.localFile.canRead()) {
			this.localFile.setReadable(true);
		}
	}
	
	/**
	  * write a string into a file
	  * @param : the file, the string to write
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
	  * delete a line in a file
	  * @param : the file, the string to delete, the separator in the file
	 **/
	public void deleteInFile (String toDelete) {
		
		try {
			manageReadPermission();
			FileReader bufferIn = new FileReader(this.localFile);
			String toModify = readAllFile();
			String newData = "";
			String dataParts[] = toModify.split("\\|");
			for(int k =  0; k < dataParts.length-1; k++) {
				if(! dataParts[k].contains(toDelete)) {
					newData += dataParts[k] + "|";
				}
			}
			overwrite(newData, "");
			bufferIn.close();
		}
		catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
		}
	}
	
	/**
	 * find all the files in a directory
	 * @return an array with the name of all the files
	 */
	public String[] findFilesInDirectory() {
		try {
			if(this.localFile.isDirectory()) {
				String fileList[] = this.localFile.list();
				return fileList;
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
