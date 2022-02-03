package org.telegrambot.convertio;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;


public class NotificationListener {
    private final int port;
    private ServerSocket serverSocket;
    private boolean isRunning = true;

    public NotificationListener(int port) {
        this.port = port;
    }

    public static void main(String[] args) {
        NotificationListener httpServer = new NotificationListener(8085);
        httpServer.run();
    }

    public void run() {
        try {
            serverSocket = new ServerSocket(port);
            serverSocket.setReuseAddress(true);
            System.out.println("Listener starts on port " + port);

            while (isRunning) {
                Socket socket = serverSocket.accept();
                System.out.println("\n------------");
                String input = readInput(socket.getInputStream());
                System.out.println(input);
                System.out.println("------------\n");

                JSONObject jsonObject = getAnswer(input);
                System.out.println(jsonObject);
                String output = "";
                if (input.startsWith("POST")) {
                    output = input.replace
                            ("POST / HTTP/1.1", "HTTP/1.1 200 OK")
                            + "\nOk";
                } else if (input.startsWith("GET")) {
                    output = input.replace
                            ("GET / HTTP/1.1", "HTTP/1.1 200 OK")
                            + "\nOk";
                }
                sendOutput(socket.getOutputStream(), output);
                socket.close();
            }
        } catch (IOException ex) {
            System.err.println("Server crushed: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private JSONObject getAnswer(String response) {
        String jsonResponse = "";
        JSONObject json;
        try {
            jsonResponse = response.substring(response.lastIndexOf("\n"));
            json = new JSONObject(jsonResponse);
        } catch (JSONException e) {
            jsonResponse = "{\"error\": \"error\"}";
            json = new JSONObject(jsonResponse);
        }
        return json;
    }

    protected String readInput(InputStream in) throws IOException {
        byte[] data = new byte[32 * 1024];
        int readBytes = in.read(data);
        return new String(data, 0, readBytes);
    }

    protected void sendOutput(OutputStream out, String response) throws IOException {
        out.write(response.getBytes());
        out.flush();
        out.close();
    }

    public void shutDown() {
        isRunning = false;
        try {
            serverSocket.close();
        } catch (IOException e) {
            System.err.println("Problem appeared while shutting down Service.");
        }
    }

}
