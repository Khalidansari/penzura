package com.notes.upload;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.DatatypeConverter;

import sun.misc.BASE64Decoder;

import com.google.api.client.repackaged.org.apache.commons.codec.binary.Base64;
import com.google.api.client.repackaged.org.apache.commons.codec.binary.StringUtils;
import com.google.appengine.labs.repackaged.com.google.common.io.BaseEncoding;
import com.google.appengine.tools.cloudstorage.GcsFileOptions;
import com.google.appengine.tools.cloudstorage.GcsFilename;
import com.google.appengine.tools.cloudstorage.GcsInputChannel;
import com.google.appengine.tools.cloudstorage.GcsOutputChannel;
import com.google.appengine.tools.cloudstorage.GcsService;
import com.google.appengine.tools.cloudstorage.GcsServiceFactory;
import com.google.appengine.tools.cloudstorage.RetryParams;

public class GCSUploader {

	private final GcsService gcsService1 = GcsServiceFactory.createGcsService(RetryParams.getDefaultInstance());
	
	public void saveBlob(String userName, String fileName, String content, String imageExtension) throws IOException {
		
		GcsFilename file = new GcsFilename("glass-tree.appspot.com", userName + "/" + fileName + "." + imageExtension);
 
		System.out.println("filename: " + userName + "/" + fileName + "." + imageExtension);
		GcsService gcsService = GcsServiceFactory.createGcsService(RetryParams.getDefaultInstance());
		GcsOutputChannel outputChannel = gcsService.createOrReplace(file, GcsFileOptions.getDefaultInstance());
		outputChannel.write(ByteBuffer.wrap(DatatypeConverter.parseBase64Binary(content)));
		outputChannel.close();		
	}
	
	public void saveBlob1() throws IOException {
		
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
		
	  GcsOutputChannel outputChannel = gcsService1.createOrReplace(fileName, GcsFileOptions.getDefaultInstance());
	  @SuppressWarnings("resource")
	  ObjectOutputStream oout = new ObjectOutputStream(Channels.newOutputStream(outputChannel));
	  oout.writeObject(content);
	  oout.close();
	}
	
	private void writeToFile(GcsFilename fileName, byte[] content) throws IOException {
		  
	  @SuppressWarnings("resource")
	  GcsOutputChannel outputChannel = gcsService1.createOrReplace(fileName, GcsFileOptions.getDefaultInstance());
	  outputChannel.write(ByteBuffer.wrap(content));
	  outputChannel.close();
	}
	
	private Object readObjectFromFile(GcsFilename fileName) throws IOException, ClassNotFoundException {
		  
	  GcsInputChannel readChannel = gcsService1.openPrefetchingReadChannel(fileName, 0, 1024 * 1024);
	  
	  try (ObjectInputStream oin = new ObjectInputStream(Channels.newInputStream(readChannel))) {
	    return oin.readObject();
	  }
	}
	
	private byte[] readFromFile(GcsFilename fileName) throws IOException {
		  
	  int fileSize = (int) gcsService1.getMetadata(fileName).getLength();
	  ByteBuffer result = ByteBuffer.allocate(fileSize);
	  
	  try (GcsInputChannel readChannel = gcsService1.openReadChannel(fileName, 0)) {
	    readChannel.read(result);
	  }
	  return result.array();
	}
}
