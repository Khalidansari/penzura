package com.notes.upload;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UploaderServlet extends HttpServlet {

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
    
		String content = "";
		String currentLineContent = "";
		String metaData = "";
		String tagName = "";
		String fileName = "";
		String imageExtension = "";
		String imageName = "";
		String className = "style";		
		String tabIndex = "";
		String tabName = "";
		String userName = "";
		
		userName = (String)req.getSession().getAttribute("currentSessionUser");
		
		BufferedReader reader = req.getReader();
		metaData = reader.readLine();
		
		while((currentLineContent = reader.readLine()) != null) {
			
			content = content + currentLineContent + '\n';
		}
		
		tagName = metaData.split("tagName:")[1].split(" className:")[0];
		
		System.out.println("tagName: " + tagName);
		
		if(metaData.split(" className:").length > 1) {
			className = metaData.split(" className:")[1].split(" imageExtension:")[0];
		}
		
		if (tagName.equalsIgnoreCase("IMG")) {
			
			imageExtension = metaData.split(" imageExtension:")[1].split(" imageName:")[0];
			imageName = metaData.split(" imageName:")[1].split(" tabIndex:")[0];
			tabIndex = metaData.split(" tabIndex:")[1].split(" tabName:")[0];
			tabName = metaData.split(" tabName:")[1].replaceAll(" ", "_");
			
			
			if(imageName.equalsIgnoreCase("null")) {
				
				imageName = "pastedImage";
				fileName = tabName + "/" + imageName + tabIndex;
				
			} else {
				
				imageName = imageName.split("\\.")[0];
				fileName = tabName + "/" + imageName + tabIndex;
			}
		} else if(className.length() > 0) {
			
			fileName = className.replaceAll(" ", "_");
		} 
		
		System.out.println("filename ---------- " + fileName);
		System.out.println("tagName: " + tagName);
		
		if (tagName.equalsIgnoreCase("IMG")) {
			
			System.out.println("Image content: " + content);
			
			new GCSUploader().saveBlob(userName, fileName, content, imageExtension);
			
		} else if (tagName.equalsIgnoreCase("DIV") || tagName.equalsIgnoreCase("STYLE")) {
			
			System.out.println("Div content: " + content);
			
			new DatastoreUploader().saveData(userName, fileName, content);
		}
    }
}
