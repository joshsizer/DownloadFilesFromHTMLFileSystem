package main;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Directory 
{	
	HTMLFile mRoot;
	String mDestination;
	public Directory(HTMLFile root, String destination)
	{
		mRoot = root;
		mDestination = destination;
	}
	
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
	
	public void downloadAllFiles(HTMLFile currentFolder)
	{
		if (currentFolder == null)
		{
			currentFolder = mRoot;
		}
		
		HTMLFile[] currentFolderContents = currentFolder.listFiles();
		
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
