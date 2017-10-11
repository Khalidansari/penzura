package com.notes;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;

public class ContentSaverServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {

		String content = "";
		String currentLineContent = "";

		BufferedReader reader = req.getReader();
		String infoBlock = reader.readLine();
		ContentSaverDAO contentSaverDAO = new ContentSaverDAO();
		
		while((currentLineContent = reader.readLine()) != null) {
			
			content = content + currentLineContent + '\n'; 
		}

		contentSaverDAO.saveHTMLContent(infoBlock, content, "kansari");
	}
}
