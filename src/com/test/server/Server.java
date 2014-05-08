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

	volatile boolean running;
	DataLogger logger;
	ServerSocket socket;
	Socket clientSocket;
	DataInputStream is;
	ObjectInputStream ois;
	PrintStream os;

	public Server(int port) {

		try {
			port = 12345;
			// String ip = IPUtil.sendIP(port);
			// IPUtil.requestIP();

			InetAddress netAdd = InetAddress.getByName("192.42.21.111");
			for (InetAddress i : InetAddress.getAllByName("192.42.21.111")) {
				System.out.println(i);
			}
			System.out.println(netAdd.getHostAddress());
		

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void shutdown() throws IOException {
		System.out.println("Server shutting down");
		running = false;
		// is.close();
		if (ois != null)
			ois.close();
		if (clientSocket != null)
			clientSocket.close();
//		socket.close();

	}

	public void run() throws Exception {
		socket = new ServerSocket(12345, 0, InetAddress.getByName("192.42.21.111"));
		logger = DataLogger.getInstance();
		running = true;
		System.out.println("Waiting for client to connect:");
		clientSocket = socket.accept();
		// is = new DataInputStream(clientSocket.getInputStream());
		ois = new ObjectInputStream(clientSocket.getInputStream());

		System.out.println("Client accepted: " + clientSocket.getInetAddress().getHostAddress());
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
