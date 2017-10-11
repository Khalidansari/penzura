package com.notes;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.Text;

public class ContentRetrieverServlet extends HttpServlet {
	
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
		
		if(req.getSession(false) == null) {
			
			System.out.println("session not present");
			
			res.sendRedirect("login.jsp");
			
		} else {
		
			String content = new ContentRetrieverDAO().getContent((String)req.getSession(false).getAttribute("currentSessionUser"));
	
			res.setContentType("text/html");
			PrintWriter writer = res.getWriter();
			String startContent = CommonUtils.getStartContent();
			String endContent = CommonUtils.getEndContent();
			
			writer.print(startContent);
			writer.print(content);
			writer.print(endContent);
	
			writer.flush();
			writer.close();
		}
	}
}
