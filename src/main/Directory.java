package main;

import java.io.File;
import java.util.ArrayList;

/**
 * A representation of an HTML File Server Directory 
 * which contains all files and folders within a folder
 * on the server
 * 
 * @author joshsizer
 *
 */
public class Directory 
{	
	/**
	 * The root folder of which other files and folders are nested
	 */
	private HTMLFile mRoot;
	
	/**
	 * The path for which the directory should be stored on the local computer
	 */
	private String mDestination;
	
	/**
	 * @param root the root HTMLfolder of which other files and folders are nested
	 * @param destination the location on the local file system where all files in
	 * this directory should be stored
	 */
	public Directory(HTMLFile root, String destination)
	{
		mRoot = root;
		mDestination = destination;
	}
	
	/**
	 * Recursively prints the files inside a directory, 
	 * used as proof of concept while developing this program
	 * @param currentFolder the folder to print the contents of
	 */
	public void printContents(File currentFolder)
	{
		File[] currentFolderContents = currentFolder.listFiles();
		
		for (int i = 0; i < currentFolderContents.length; i++)
		{
			if (currentFolderContents[i].isDirectory())
			{
				printContents(currentFolderContents[i]);
			}
			else
			{
				System.out.println(currentFolderContents[i].getName());
			}
		}
	}
	
	/**
	 * Recursively stores the files inside a directory on the local computer 
	 * used as proof of concept while developing this program.
	 * 
	 * @param currentFolder the folder to print the contents of
	 * @return an ArrayList of all the files within a folder
	 */
	public ArrayList<File> getAllFiles(File currentFolder, ArrayList<File> allFiles)
	{
		File[] currentFolderContents = currentFolder.listFiles();
		
		for (int i = 0; i < currentFolderContents.length; i++)
		{
			if (currentFolderContents[i].isDirectory())
			{
				getAllFiles(currentFolderContents[i], allFiles);
			}
			else
			{
				allFiles.add(currentFolderContents[i]);
			}
		}
		return allFiles;
	}
	
	/**
	 * Downloads all the files in an HTMLFile directory.
	 */
	public void downloadAllFiles()
	{
		//this directory should have an HTMLFolder already specified
		HTMLFile currentFolder = mRoot;
		
		//the array that all the files and folders inside the currentFolder is held
		HTMLFile[] currentFolderContents = currentFolder.listFiles();
		
		//iterates through the currentFolderContents, and downloads the file if it is not 
		//a folder
		for (int i = 0; i < currentFolderContents.length; i++)
		{
			if (currentFolderContents[i].isDirectory())
			{
				downloadAllFiles(currentFolderContents[i]);
			}
			else
			{
				currentFolderContents[i].download(mDestination);
			}
		}
	}
	
	private void downloadAllFiles(HTMLFile currentFolder)
	{
		
		//the array that all the files and folders inside the currentFolder is held
		HTMLFile[] currentFolderContents = currentFolder.listFiles();
		
		//iterates through the currentFolderContents, and downloads the file if it is not 
		//a folder
		for (int i = 0; i < currentFolderContents.length; i++)
		{
			if (currentFolderContents[i].isDirectory())
			{
				downloadAllFiles(currentFolderContents[i]);
			}
			else
			{
				currentFolderContents[i].download(mDestination);
			}
		}
	}
}
