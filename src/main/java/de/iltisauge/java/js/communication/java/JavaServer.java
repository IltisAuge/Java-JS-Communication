package de.iltisauge.java.js.communication.java;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JavaServer extends WebSocketServer {
	
	public static void main(String[] args) {
		//new InetSocketAddress("49.12.163.114", 31911)
		final JavaServer server = new JavaServer();
		server.start();
	}
	
	public static final InetSocketAddress LOCAL_ADDRESS = new InetSocketAddress("127.0.0.1", 31912);
	private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
	private final List<WebSocket> sockets = new ArrayList<WebSocket>();
	
	public JavaServer() {
		super(LOCAL_ADDRESS);
	}
	
	public JavaServer(InetSocketAddress address) {
		super(address);
	}
	
	public void broadcast(Object object) {
		broadcast(GSON.toJson(object));
	}
	
	public void broadcast(String message) {
		sockets.forEach(x -> {
			x.send(message);
		});
	}

	@Override
	public void onOpen(WebSocket conn, ClientHandshake handshake) {
		System.out.println("Connection from " + conn.getRemoteSocketAddress() + " accepted.");
		sockets.add(conn);
	}

	@Override
	public void onClose(WebSocket conn, int code, String reason, boolean remote) {
		System.out.println("Connection with " + conn.getRemoteSocketAddress() + " closed.");
		sockets.remove(conn);
	}

	@Override
	public void onMessage(WebSocket conn, String message) {
		System.out.println("Message from " + conn.getRemoteSocketAddress() + ": " + message);
		conn.send(message);
	}

	@Override
	public void onError(WebSocket conn, Exception ex) {
		System.out.println("Error from " + (conn == null ? "-" : conn.getRemoteSocketAddress()) + ": " + ex.getMessage());
		ex.printStackTrace();
	}

	@Override
	public void onStart() {
		System.out.println("Binded server socket on " + getAddress().toString());
	}
}
