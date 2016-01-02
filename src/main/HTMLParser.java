package main;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class HTMLParser
{
	public HTMLParser()
	{
		
	}
	
	public static ArrayList<HTMLFile> getFile(HTMLFile sourceFile)
	{
		ArrayList<HTMLFile> HTMLFileList = new ArrayList<HTMLFile>();
		String rawHTML = null;
		String rawHTMLList = null;
		String currentListElement = null;
		ArrayList<String> rawList = new ArrayList<String>();
		String relativeFilePath = null;
		
		int listStart;
		int listEnd;
		
		rawHTML = sourceFile.getRawHTML();
		
		listStart = rawHTML.indexOf("<li>");
		listEnd = rawHTML.lastIndexOf("</li>");
		rawHTMLList = rawHTML.substring(listStart, listEnd + 5);
		
		while (rawHTMLList.indexOf("<li>") != -1)
		{
			//You could do this, but who wants to read it? 
			//rawList.add(rawHTMLList.substring(rawHTMLList.indexOf("<li>"), rawHTMLList.indexOf("</li")));
			listStart = rawHTMLList.indexOf("<li>");
			listEnd = rawHTMLList.indexOf("</li>");
			currentListElement = rawHTMLList.substring(listStart + 4, listEnd);
			rawList.add(currentListElement);
			rawHTMLList = rawHTMLList.substring(listEnd + 5);
		}
		
		for (int i = 0; i < rawList.size(); i++)
		{
			HTMLFile currentFile = null;
			currentListElement = rawList.get(i);
			//but I do it here anyway
			relativeFilePath = currentListElement.substring(currentListElement.indexOf("<a href=") + 9, currentListElement.indexOf("\">"));
			
			if (!relativeFilePath.equals("../"))
			{
				try 
				{
					if (!sourceFile.getURL().endsWith("/"))
					{
						currentFile = new HTMLFile(sourceFile.getURL().concat("/" + relativeFilePath));
					}
					else
					{
						currentFile = new HTMLFile(sourceFile.getURL().concat(relativeFilePath));
					}
				} 
				catch (FileNotFoundException e) 
				{
					e.printStackTrace();
				}
				
				HTMLFileList.add(currentFile);
			}
			
		}
		return HTMLFileList;
	}
}