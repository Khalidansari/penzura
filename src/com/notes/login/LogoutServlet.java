package com.notes.login;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LogoutServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
	           throws ServletException, java.io.IOException {
		
		request.getSession().invalidate();
		response.sendRedirect("login.jsp"); //error page
	}
}
