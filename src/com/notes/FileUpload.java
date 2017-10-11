package com.notes;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URLEncoder;

public class FileUpload {
	
	public void uploadFile(){
		
		try {
			// Construct data
			String data = URLEncoder.encode("key1", "UTF-8") + "=" + URLEncoder.encode("value1", "UTF-8");
			data += "&" + URLEncoder.encode("key2", "UTF-8") + "=" + URLEncoder.encode("value2", "UTF-8");
	
			// Create a socket to the host
			String hostname = "hostname.com";
			int port = 80;
			InetAddress addr = InetAddress.getByName(hostname);
			Socket socket = new Socket(addr, port);
	
			// Send header
			String path = "/servlet/SomeServlet";
			BufferedWriter wr = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF8"));
			wr.write("POST "+path+" HTTP/1.0\r\n");
			wr.write("Content-Length: "+data.length()+"\r\n");
			wr.write("Content-Type: application/x-www-form-urlencoded\r\n");
			wr.write("\r\n");
	
			// Send data
			wr.write(data);
			wr.flush();
	
			// Get response
			BufferedReader rd = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String line;
			while ((line = rd.readLine()) != null) {
				// Process line...
			}
			wr.close();
			rd.close();
		} catch (Exception e) {
			
		}				
	}
}
