package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

	public class RawHTMLFile {
		
		private URL mURL = null;
		private InputStream mInputStream = null;
		private BufferedReader htmlFileReader = null;
		private String fileContents = null;
		private String line = null;
		
		public RawHTMLFile(String url) throws MalformedURLException
		{
			mURL = new URL(url);
			
			try 
			{
				mInputStream = mURL.openStream();
			} 
			catch (IOException e) 
			{
				System.out.println("Could not open URL stream...");
				e.printStackTrace();
			}
			
			htmlFileReader = new BufferedReader(new InputStreamReader(mInputStream));
		}
	
		public String getFileAsStringLn()
		{
			try {
				while ((line = htmlFileReader.readLine()) != null)
				{
					if (fileContents == null)
					{
						fileContents = line;
					}
					else
					{
						fileContents += "\n" + line;
					}
				}
			} 
			catch (IOException e)
			{
				System.out.println("Unable to read rawHTML");
				e.printStackTrace();
			}		
			
			return fileContents;
		}
		
		public String getFileAsString()
		{
			try {
				while ((line = htmlFileReader.readLine()) != null)
				{
					if (fileContents == null)
					{
						fileContents = line;
					}
					else
					{
						fileContents += line;
					}
				}
			}
			catch (IOException e)
			{
				System.out.println("Unable to read rawHTML");
				e.printStackTrace();
			}
			
			return fileContents;
		}
		
		public URL getURL()
		{
			return mURL;
		}
		
		public String getRelativeURL()
		{
			return mURL.getPath();
		}
		
		
}