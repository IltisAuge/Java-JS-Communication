package de.iltisauge.java.js.communication.java;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class JavaClient {
	
	public static void main(String[] args) throws IOException {
		final JavaClient client = new JavaClient();
		System.out.println("Waiting for input to send to server...");
		final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				client.send(line);
			}
		} catch (IOException exception) {
			exception.printStackTrace();
		}
	}
	
	public void send(String text) {
		final Socket socket = new Socket();
		try {
			socket.connect(JavaServer.LOCAL_ADDRESS);
			socket.getOutputStream().write(text.length());
			final PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
		 	printWriter.print(text);
		 	printWriter.flush();
		 	socket.close();
		} catch (IOException exception) {
			exception.printStackTrace();
		}
	}
}
