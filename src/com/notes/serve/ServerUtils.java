package com.notes.serve;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ServerUtils {

	public static String getStartContent() throws IOException {
		
		return fileToString("./Start.html");
	}
	
	public static String getEmptyContent() throws IOException{
		
		return fileToString("./emptyContent.html");
	}
	
	public static String getEndContent() throws IOException{
		
		return fileToString("./End.html");
	}
	
	public static String fileToString(String fileName) throws IOException{
		
		StringBuilder content = new StringBuilder();
		String currentLine  = "";

		BufferedReader buffReader = new BufferedReader(new FileReader(fileName));

		while((currentLine = buffReader.readLine()) != null) {
			
			content.append(currentLine);
			content.append(System.lineSeparator());
		}
		
		buffReader.close();
		
		return content.toString();
	}
}
