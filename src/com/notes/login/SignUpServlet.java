package com.notes.login;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.bind.DatatypeConverter;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;

public class SignUpServlet extends HttpServlet {
	
	static DatastoreService ds = DatastoreServiceFactory.getDatastoreService();

	public void doPost(HttpServletRequest request, HttpServletResponse response) 
	           throws ServletException, java.io.IOException {

		try {
			setPasswordDigest(request.getParameter("username"), request.getParameter("pw")); 
			
			HttpSession session = request.getSession(true);
			session.setAttribute("currentSessionUser", request.getParameter("username")); 
			
			//START - Kansari 1st May 2015 - testing
			response.sendRedirect("home"); //logged-in page
			//request.getRequestDispatcher("/home").forward(request, response);
			//END - Kansari 1st May 2015 - testing
				
			System.out.println("Credentials creation successful!");
			
		} catch (NoSuchAlgorithmException e) {
			System.out.println("exception creating credentials");
			
			response.sendRedirect("login.jsp"); //error page
			e.printStackTrace();
		} 
	}
	
	public static void setPasswordDigest(String userName, String password) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		
		Entity userEntity;
		
		MessageDigest digest = MessageDigest.getInstance("SHA-256");
		byte[] hash = digest.digest(password.getBytes("UTF-8"));
		
		Filter userNameFilter =  new Query.FilterPredicate("userName", FilterOperator.EQUAL, userName);
		Query q = new Query("userTable").setFilter(userNameFilter);

		PreparedQuery pq = ds.prepare(q);
		userEntity = pq.asSingleEntity();

		System.out.println("Password to be set: " + password);
		
		if (userEntity != null) {

			System.out.println("Password to be set: Entity not null: " + DatatypeConverter.printBase64Binary(hash));
			userEntity.setProperty("PasswordDigest", DatatypeConverter.printBase64Binary(hash));
			
		} else {
			System.out.println("Password to be set: Entity null: " + DatatypeConverter.printBase64Binary(hash));
			userEntity = new Entity("userTable");
			userEntity.setProperty("userName", userName);
			userEntity.setProperty("PasswordDigest", DatatypeConverter.printBase64Binary(hash));
		}
		
		ds.put(userEntity);
	}
}