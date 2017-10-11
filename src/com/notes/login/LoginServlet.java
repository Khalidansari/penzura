package com.notes.login;

import java.security.NoSuchAlgorithmException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginServlet extends HttpServlet {
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) 
	           throws ServletException, java.io.IOException {

		try {
			if (LoginValidator.isValid(request.getParameter("username"), request.getParameter("pw")))
			 {
				  System.out.println("session created!!");
			      HttpSession session = request.getSession(true);
			      session.setAttribute("currentSessionUser", request.getParameter("username"));
			      response.sendRedirect("home"); //logged-in page      		
			 } else {
				  System.out.println("session not created!!");
			      response.sendRedirect("error.jsp"); //error page
			}
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
	           throws ServletException, java.io.IOException {
		
		response.sendRedirect("login.jsp"); //error page 
	}
}
