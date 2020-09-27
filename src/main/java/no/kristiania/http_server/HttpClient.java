package no.kristiania.http_server;

import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class HttpClient {
    private final int responseCode;
    private Map<String, String> responseHeaders = new HashMap<>();

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
        String[] responseLineParts = line.split(" ");
        responseCode = Integer.parseInt(responseLineParts[1]);

        String headerLine;
        while (!(headerLine = readLine(socket)).isEmpty()) {
            System.out.println(headerLine);
            int colonPos = headerLine.indexOf(':');
            String name = headerLine.substring(0, colonPos);
            String value = headerLine.substring(colonPos+1).trim();
            responseHeaders.put(name, value);
        }
    }


    private String readLine(Socket socket) throws IOException {
        // Reads one BYTE at a time, until there is nothing more to read
        // (c = socket.getInputStream().read() != -1) means ->
        // Assign the next value of "read()" to c and check if it is not -1
        // (-1 means end of data)
        StringBuilder line = new StringBuilder();
        int c;
        while ((c = socket.getInputStream().read()) != -1) {
            // Stop reading at newline
            if (c == '\n'){
                socket.getInputStream().read();
                break;
            }
            // Treat each byte as a character ("(char)") and add it to the response
            line.append((char)c);
        }
        return line.toString();
    }

    public static void main(String[] args) throws IOException {
        String hostName = "urlecho.appspot.com";
        int port = 80;
        String requestTarget = "/echo?status=200&Content-Type=text%2Fhtml&body=Hello%20Kristiania";
        new HttpClient(hostName, port, requestTarget);

    }

    public int getResponseCode() {
        return responseCode;
    }

    public String getResponseHeader(String headerName) {
        return responseHeaders.get(headerName);
    }
}
