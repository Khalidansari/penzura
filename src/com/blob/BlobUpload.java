package com.blob;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.tools.cloudstorage.GcsFileOptions;
import com.google.appengine.tools.cloudstorage.GcsFilename;
import com.google.appengine.tools.cloudstorage.GcsInputChannel;
import com.google.appengine.tools.cloudstorage.GcsOutputChannel;
import com.google.appengine.tools.cloudstorage.GcsService;
import com.google.appengine.tools.cloudstorage.GcsServiceFactory;
import com.google.appengine.tools.cloudstorage.RetryParams;

public class BlobUpload extends HttpServlet {

	private final GcsService gcsService = GcsServiceFactory.createGcsService(RetryParams.getDefaultInstance());
	  
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
    	  
	      GcsFilename filename = new GcsFilename("glass-tree.appspot.com", "foo1");
	      Map<String, String> mapContent = new HashMap<>();
	      mapContent.put("foo", "bar");

	      writeObjectToFile(filename, mapContent);

	      try {
			System.out.println("Wrote " + mapContent + " read: " + readObjectFromFile(filename));
		  } catch (ClassNotFoundException e) {

			e.printStackTrace();
		  }

	      /** Write and read back a byteArray */
	      byte[] byteContent = new byte[] {1, 2, 3, 4, 5, 6};

	      writeToFile(filename, byteContent);

	      System.out.println("Wrote " + Arrays.toString(byteContent) + " read: " + Arrays.toString(readFromFile(filename)));

    }
    
	private void writeObjectToFile(GcsFilename fileName, Object content) throws IOException {
		
	    GcsOutputChannel outputChannel = gcsService.createOrReplace(fileName, GcsFileOptions.getDefaultInstance());
	    @SuppressWarnings("resource")
	    ObjectOutputStream oout = new ObjectOutputStream(Channels.newOutputStream(outputChannel));
	    oout.writeObject(content);
	    oout.close();
	  }
	
	  private void writeToFile(GcsFilename fileName, byte[] content) throws IOException {
		  
	    @SuppressWarnings("resource")
	    GcsOutputChannel outputChannel = gcsService.createOrReplace(fileName, GcsFileOptions.getDefaultInstance());
	    outputChannel.write(ByteBuffer.wrap(content));
	    outputChannel.close();
	  }
	
	  private Object readObjectFromFile(GcsFilename fileName) throws IOException, ClassNotFoundException {
		  
	    GcsInputChannel readChannel = gcsService.openPrefetchingReadChannel(fileName, 0, 1024 * 1024);
	    
	    try (ObjectInputStream oin = new ObjectInputStream(Channels.newInputStream(readChannel))) {
	      return oin.readObject();
	    }
	  }
	
	  private byte[] readFromFile(GcsFilename fileName) throws IOException {
		  
	    int fileSize = (int) gcsService.getMetadata(fileName).getLength();
	    ByteBuffer result = ByteBuffer.allocate(fileSize);
	    
	    try (GcsInputChannel readChannel = gcsService.openReadChannel(fileName, 0)) {
	      readChannel.read(result);
	    }
	    return result.array();
	  }
    
    /*
    private BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
    
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res)
        throws ServletException, IOException {

    	String uploadURL = blobstoreService.createUploadUrl("/upload");
    	System.out.println("uploadURL: " + uploadURL);
    	
    	BufferedReader reader = req.getReader();
    	String line;
    	String rawContent = "";
    	while ((line = reader.readLine()) != null){
    		
    		rawContent = rawContent + line;
    	}
    	
    	BlobUploadRaw.executeRawHTTPReq(uploadURL, rawContent);
    	/*
        Map<String, BlobKey> blobs = blobstoreService.getUploadedBlobs(req);
        BlobKey blobKey = blobs.get("myFile");

        if (blobKey == null) {
            res.sendRedirect("/");
        } else {
            res.sendRedirect("/serve?blob-key=" + blobKey.getKeyString());
        }
        */
}
