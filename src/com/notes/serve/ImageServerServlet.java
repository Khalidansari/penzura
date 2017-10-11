package com.notes.serve;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ImageServerServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
		
		System.out.println("Images loaded");
		
		String url = req.getRequestURI();
		String filename = url.substring("/images/".length(), url.length());
		String userName = filename.substring(0, filename.indexOf("/"));
		
		System.out.println("userName: " + userName);
		System.out.println("filename: " + filename);
		
		try {
			
			if(req.getSession(false) == null || !userName.equals((String)req.getSession(false).getAttribute("currentSessionUser"))) {
				
				System.out.println("session not present");
				
				res.sendRedirect("../../../login.jsp");
				
			} else {

				new GCSServer().getBlob(filename, res);			
			}
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
