package main;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;

public class HTMLFile 
{
	private boolean isDirectory = false;
	private RawHTMLFile rawHTML = null;
	private String mAbsolutePath;
	private String mURL = null;
	private String mFileName = null;
	
	public HTMLFile()
	{
		
	}
	
	public HTMLFile(String URL) throws FileNotFoundException
	{
		mURL = URL;
		
		try
		{
			rawHTML = new RawHTMLFile(mURL);
		}
		catch (MalformedURLException e)
		{
			System.out.println("Malformed URL...");
		}
				
		//throws an exception if the url given is the home page... which means it is not a folder... I think
		if (getAbsolutePath().equals("/"))
		{
			throw new FileNotFoundException();
		}
		else
		{
			mAbsolutePath = getAbsolutePath();
			mFileName = determineActualFileName(mAbsolutePath);
		}
		
		isDirectory = determineIfDirectory();
	}
	
	public String getName()
	{
		return mFileName;
	}
	
	public String getURL()
	{
		return mURL;
	}
	
	public boolean isDirectory()
	{
		return isDirectory;
	}
	
	
	public String getAbsolutePath()
	{
		return rawHTML.getRelativeURL();
	}
	
	public String getRawHTML()
	{
		return rawHTML.getFileAsString();
	}
	
	public String getRawHTMLLn()
	{
		return rawHTML.getFileAsStringLn();
	}
	
	public HTMLFile[] listFiles()
	{
		ArrayList<HTMLFile> fileList = new ArrayList<HTMLFile>();
		
		//parses out all the the files within this HTMLFile
		//this function only gets called if the this object is a directory
		fileList = HTMLParser.getFile(this);
		
		//ArrayLists create 10 elements at a time, and if they are not all filled, the remaining are null
		//so they can't be returned by the .toArray function (ugh)
		int size = 0;
		//finds the number of HTMLFile objects in fileList
		for (int i = 0; i < fileList.size(); i++)
		{
			if (fileList.get(i) != null)
			{
				size++;
			}
		}
		
		//list is an array with the length of the number of HTMLFile objects found
		HTMLFile[] list = new HTMLFile[size];
		
		//fills the array
		for (int i = 0; i < size; i++)
		{
			list[i] = fileList.get(i);
		}
		return list;
	}
	
	public void download(String destination)
	{
		String path = getAbsolutePath();
		BufferedWriter bw = null;
		File file = null;
		
		System.out.println("Downloading: " + getURL() + "...");
		
		if (!path.endsWith("/"))
		{
			path.concat("/");
		}
		if (!destination.endsWith("/"))
		{
			destination.concat("/");
		}
		
		path = path.replace("/", "\\");
		destination = destination.concat(path);
		
		file = new File(destination);
		file.getParentFile().mkdirs();
				
		try {
			if (!file.exists())
			{
				file.createNewFile();
			}
		
			bw = new BufferedWriter(new FileWriter(file));
			bw.write(getRawHTMLLn());
			bw.close();
		} 
		catch (IOException e) {
			System.out.println("Error in downloading " + getURL());
		}		
	}
	
	private String determineActualFileName(String relativePath)
	{
		int lastSlash = -1;
		int secondToLastSlash = -1;
		String currentChar;
		String fileName;
		
		//goes through entire relative path, 
		//which should be everything after the .com or .org
		//finds the location of the last slash and the second to last slash 
		//ie. /hello/world/thisisatest.txt/ would calculate the location of the slashes "/"thisisatest.txt"/"
		for (int i = 0; i < relativePath.length(); i++)
		{
			//sets current character equal to the relative path at index i
			currentChar = relativePath.substring(i, i+1);
			if (currentChar.equals("/"))	//if it finds a "/"; denoting a subfile
			{
				if (lastSlash == -1)		//and has not found a slash yet
				{	
					lastSlash = i;			//sets location of the first slash
				}
				else	//else if the first slash has already been found
				{
					secondToLastSlash = lastSlash;	//current slash is equal to the last slash found in relative path
					lastSlash = i;					//and secondToLastSlash is equal to the previously found slash
				}
			}
		}
		
		//takes care of possible path with no slash on the end 
		//ie /hello/world/thisisatest.txt <--- no slash at end
		if (lastSlash == relativePath.length() - 1)	//if the last slash is at the end of the relative path
		{
			//the filename is set to what's between second to last slash and last slash
			fileName = relativePath.substring(secondToLastSlash + 1, lastSlash); 
		}
		else	//if the last slash is not at the end of the relative path
		{
			//the filename is set to what's between the last found slash and the end of the relative path
			fileName = relativePath.substring(lastSlash + 1);
		}
		
		return fileName;
	}
	
	
	private boolean determineIfDirectory()
	{
		boolean tempIsDirectory;
		
		//basically an HTMLFile is a directory if the name does not contain a file extensions
		if (mFileName.contains("."))
		{
			tempIsDirectory = false;
		}
		else
		{
			tempIsDirectory = true;
		}
		return tempIsDirectory;
	}
		
}
