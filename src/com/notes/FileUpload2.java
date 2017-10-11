package com.notes;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class FileUpload2 {

    public static void main(String[] args) throws IOException {
    	
        Socket socket = new Socket("uploads.im", 80);
        
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF8"));
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        sendMessage(out);
        readResponse(in);
        
        out.close();
        in.close();
        socket.close();
    }
    
    private static void sendMessage(BufferedWriter out) throws IOException {

        System.out.println(" * Request");
        
        out.write("GET https://uploads.im/ HTTP/1.1\r\n");
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
        
        out.write("GET http://uploads.im/api?upload=http://www.google.com/images/srpr/nav_logo66.png/ HTTP/1.1\r\n");
        out.write("Host: uploads.im\r\n");
        out.write("Connection: keep-alive\r\n");
        //out.write("Cache-Control: max-age=0\r\n");
        out.write("Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8\r\n");
        out.write("User-Agent: Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/38.0.2125.104 Safari/537.36\r\n");
        out.write("Accept-Encoding: deflate,sdch\r\n");
        out.write("Accept-Language: en-US,en;q=0.8\r\n");
        //out.write("Cookie: doShort=1; PHPSESSID=1k1q8fp04vqjpe44p6odqjmhh2\r\n");
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
