package com.notes.serve;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.DatatypeConverter;

import com.google.api.client.util.IOUtils;
import com.google.appengine.tools.cloudstorage.GcsFilename;
import com.google.appengine.tools.cloudstorage.GcsInputChannel;
import com.google.appengine.tools.cloudstorage.GcsService;
import com.google.appengine.tools.cloudstorage.GcsServiceFactory;
import com.google.appengine.tools.cloudstorage.RetryParams;

public class GCSServer {

	private final GcsService gcsService1 = GcsServiceFactory.createGcsService(RetryParams.getDefaultInstance());
	
	public void getBlob(String url, HttpServletResponse res) throws IOException, ClassNotFoundException {
				
 
		/*
		 GcsFilename file = new GcsFilename("glass-tree.appspot.com", url);
		
		GcsInputChannel readChannel = gcsService1.openPrefetchingReadChannel(file, 0, 1024 * 1024);
		
		//ServletOutputStream out = res.getOutputStream();  
		//BufferedOutputStream bout = new BufferedOutputStream(out);
		ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		PrintWriter writer = res.getWriter();
		
		try (InputStream is = Channels.newInputStream(readChannel)) {
		
			int nRead;
			byte[] data = new byte[16384];

			while ((nRead = is.read(data, 0, data.length)) != -1) {
				buffer.write(data, 0, nRead);
			}

			buffer.flush();
			//res.setContentType("image/png");
			//Testing

			res.setContentType("image/png");
			String base64IMG = DatatypeConverter.printBase64Binary(buffer.toByteArray());
			//bout.write(buffer.toByteArray());
			//System.out.println("base64IMG: " + base64IMG.substring(0, 1000));
			writer.print(base64IMG);;
			
			buffer.close();
		}	
		writer.flush();
		writer.close();
		*/

	    /*
		PrintWriter writer = res.getWriter();
		
		//try (ObjectInputStream oin = new ObjectInputStream(Channels.newInputStream(readChannel))) {
				
		try (InputStream is = Channels.newInputStream(readChannel)) {
			
			ByteArrayOutputStream buffer = new ByteArrayOutputStream();

			int nRead;
			byte[] data = new byte[16384];

			while ((nRead = is.read(data, 0, data.length)) != -1) {
			  buffer.write(data, 0, nRead);
			}

			buffer.flush();
			res.setContentType("image/png");
			writer.print(buffer.toByteArray());
			//writer.print(oin.readObject());
		}	
		
		writer.flush();
		writer.close();
		*/
		
		/* old code */
		
		GcsFilename file = new GcsFilename("glass-tree.appspot.com", url);
		
		GcsInputChannel readChannel = gcsService1.openPrefetchingReadChannel(file, 0, 1024 * 1024);
		
		ServletOutputStream out = res.getOutputStream();  
		BufferedOutputStream bout = new BufferedOutputStream(out);  
	    
		try (InputStream is = Channels.newInputStream(readChannel)) {
			
			ByteArrayOutputStream buffer = new ByteArrayOutputStream();

			int nRead;
			byte[] data = new byte[16384];

			while ((nRead = is.read(data, 0, data.length)) != -1) {
			  bout.write(data, 0, nRead);
			}

			buffer.flush();
			res.setContentType("image/png");
			bout.write(buffer.toByteArray());	
			buffer.close();
		}	

		bout.close();
		out.close();
		/* old code */
	}
}