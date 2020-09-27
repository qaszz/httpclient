package no.kristiania.http_server;

import java.io.IOException;
import java.net.Socket;

public class HttpClient {
    public HttpClient(String hostname, int port, String requestTarget) {
    }

    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("urlecho.appspot.com", 80);

        //Format as specified in the HTTP specification
        // Each line is seperared by \r\b (CRLF)
        // The request ends with an empty line (r\n\r\n)
        String request = "GET /echo?body=hello%20world HTTP/1.1\n" +
                "Host: urlecho.appspot.com\r\n\r\n";
        // Writes data to the server
        socket.getOutputStream().write(request.getBytes());

        // Reads one BYTE at a time, until there is nothing more to read
        // (c = socket.getInputStream().read() != -1) means -
        // Assign the next value of "read()" to c and check if it is not -1
        // (-1 means end of data)
        int c;
        while ((c = socket.getInputStream().read()) != -1) {
            System.out.print((char)c);
        }
    }

    public int getResponseCode() {
        return 200;
    }
}
