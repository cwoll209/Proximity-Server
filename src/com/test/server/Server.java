package com.test.server;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import com.test.util.DataLogger;
import com.test.util.IPUtil;
import com.test.util.RSSIMessage;

public class Server {

	boolean running;
	DataLogger logger;
	ServerSocket socket;
	Socket clientSocket;
	DataInputStream is;
	ObjectInputStream ois;
	PrintStream os;
	


	public Server(int port) {

		try {
			String ip = IPUtil.sendIP(port);
			IPUtil.requestIP();
			InetAddress netAdd = InetAddress.getByName("localhost");
			System.out.println(netAdd.getHostAddress());
			socket = new ServerSocket(port);
			logger = DataLogger.getInstance();
			
			run();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	


	public void shutdown() throws IOException {
		System.out.println("Server shutting down");
		running = false;
//		is.close();
		clientSocket.close();
		socket.close();
		os.close();
	}

	public void run() throws Exception {
		running = true;
		System.out.println("Waiting for client to connect:");
		clientSocket = socket.accept();
//		is = new DataInputStream(clientSocket.getInputStream());
		ois = new ObjectInputStream(clientSocket.getInputStream());

		System.out.println("Client accepted: "
				+ clientSocket.getInetAddress().getHostAddress());
		String line = "";

		while (running) {
			RSSIMessage container;
			Object o = ois.readObject();
			
			
			
			if (o instanceof RSSIMessage) {
				container = (RSSIMessage) o;
				if (container.end) {
					running = false;
				
				}
				logger.write(container);
				
				
			}
		

		}
		shutdown();
	}

}
