package main;

import java.io.FileNotFoundException;

public class Main {

	public static final String rootURL = "http://team341.com/programming11";
	public static final String DESTINATION = "C:\\Users\\joshs\\Documents\\programming11";
	
	public static void main(String[] args)
	{
		Directory workingDirectory = null;
	
		try 
		{
			workingDirectory = new Directory(new HTMLFile(rootURL), DESTINATION );
			workingDirectory.downloadAllFiles();
			System.out.println("\nDownload complete.");
		} 
		catch (FileNotFoundException e) 
		{
			System.out.println("URL has no folders...");
		} 
	}
}
