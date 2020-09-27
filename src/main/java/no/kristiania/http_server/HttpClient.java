package no.kristiania.http_server;

import java.io.IOException;
import java.net.Socket;

public class HttpClient {
    private final int responseCode;

    public HttpClient(String hostName, int port, String requestTarget) throws IOException {
        Socket socket = new Socket(hostName, port);

        //Format as specified in the HTTP specification
        // Each line is separated by \r\b (CRLF)
        // The request ends with an empty line (r\n\r\n)
        String request = "GET " + requestTarget + " HTTP/1.1\r\n" +
                "Host: " + hostName + "\r\n\r\n";
        // Writes data to the server
        socket.getOutputStream().write(request.getBytes());
        String line = readLine(socket);
        System.out.println(line);
        String[] responseLineParts = line.toString().split(" ");
        responseCode = Integer.parseInt(responseLineParts[1]);
    }

    private String readLine(Socket socket) throws IOException {
        // Reads one BYTE at a time, until there is nothing more to read
        // (c = socket.getInputStream().read() != -1) means -
        // Assign the next value of "read()" to c and check if it is not -1
        // (-1 means end of data)
        StringBuilder line = new StringBuilder();
        int c;
        while ((c = socket.getInputStream().read()) != -1) {
            // Stop reading at newline
            if (c == '\n'){
                break;
            }
            // Treat each byte as a charater ("(char)") and add it to the response
            line.append((char)c);
        }
        return line.toString();
    }

    public static void main(String[] args) throws IOException {
        String hostName = "urlecho.appspot.com";
        int port = 80;
        String requestTarget = "/echo?body=hello%20world!";
        new HttpClient(hostName, port, requestTarget);

    }

    public int getResponseCode() {
        return responseCode;
    }
}
