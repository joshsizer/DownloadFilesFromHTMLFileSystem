package main;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class HTMLParser
{
	public HTMLParser()
	{
		
	}
	
	public static ArrayList<HTMLFile> getFile(HTMLFile sourceFile) throws FileFormatException
	{
		ArrayList<HTMLFile> HTMLFileList = new ArrayList<HTMLFile>();
		String rawHTML = sourceFile.getRawHTML();
		String fileName = null;
		
		int elementStart = -1;
		int elementEnd = -1;
		boolean isParsable = false;
		
		while (rawHTML.indexOf("<li><a href=") != -1)
		{
			isParsable = true;
			HTMLFile currentFile = null;
			//You could do this, but who wants to read it? 
			//rawList.add(rawHTMLList.substring(rawHTMLList.indexOf("<li>"), rawHTMLList.indexOf("</li")));
			elementStart = rawHTML.indexOf("<li><a href=");
			elementEnd = rawHTML.indexOf("\">");
			
			fileName = rawHTML.substring(elementStart + 13, elementEnd);
						
			//as long as the current element is not the link to go to parent page
			if (!fileName.equals("../") && fileName != null)
			{
				try 
				{				
					currentFile = new HTMLFile(sourceFile.getURL().concat(fileName));
					HTMLFileList.add(currentFile);
				} 
				catch (FileNotFoundException e) 
				{
					System.out.println("Error in creating parsed URL: " + 
										sourceFile.getURL().concat(fileName));
				}
			}
			//gets rid of the already parsed element
			rawHTML = rawHTML.substring(elementEnd + 5);
		}
		
		if (!isParsable)
		{
			throw new FileFormatException();
		}
		
		return HTMLFileList;
	}
}