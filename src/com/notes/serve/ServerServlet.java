package com.notes.serve;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.notes.CommonUtils;
import com.notes.ContentRetrieverDAO;

public class ServerServlet extends HttpServlet {
	
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
		
		if(req.getSession(false) == null) {
			
			System.out.println("session not present");
			
			res.sendRedirect("login.jsp");
			
		} else {
			
			String userName = (String)req.getSession(false).getAttribute("currentSessionUser");
		
			String startContent = ServerUtils.getStartContent();
			String hiddenUserName = "<input type=\"hidden\" Id=\"userName\" value=\"" + userName + "\">";
			String content = new DatastoreServer().getContent((String)req.getSession(false).getAttribute("currentSessionUser"));
			String endContent = ServerUtils.getEndContent();
			
			res.setContentType("text/html");
			PrintWriter writer = res.getWriter();

			writer.print(startContent);
			writer.print(hiddenUserName);
			writer.print(content);
			writer.print(endContent);
	
			writer.flush();
			writer.close();
		}
	}
}
