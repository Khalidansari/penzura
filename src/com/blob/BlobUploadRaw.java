package com.blob;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class BlobUploadRaw {

	public static void executeRawHTTPReq(String url, String rawContent) throws IOException {
		    	
        Socket socket = new Socket(url.split("/_ah/upload")[0], 80);
        
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF8"));
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        sendMessage(out, url, rawContent);
        readResponse(in);
        
        out.close();
        in.close();
        socket.close();
        
	}
	
    private static void sendMessage(BufferedWriter out, String url, String rawContent) throws IOException {

        System.out.println(" * Request");
        
        out.write("POST url HTTP/1.1\r\n");
        out.write("Host: uploads.im\r\n");
        out.write("Connection: keep-alive\r\n");
        out.write("Cache-Control: max-age=0\r\n");
        out.write("Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*//* *//*;q=0.8\r\n");
        out.write("User-Agent: Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/38.0.2125.104 Safari/537.36\r\n");
        out.write("Accept-Encoding: deflate,sdch\r\n");
        out.write("Accept-Language: en-US,en;q=0.8\r\n");
        out.write("Cookie: doShort=1; PHPSESSID=1k1q8fp04vqjpe44p6odqjmhh2\r\n");
        out.write("\r\n");
        out.flush();
    }
    
    private static void readResponse(BufferedReader in) throws IOException {
    	
        System.out.println("\n * Response");
        
        String line;
        
        while ((line = in.readLine()) != null) {
            System.out.println(line);
        }
    }
}
