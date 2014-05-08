import java.net.ServerSocket;

import javax.swing.SwingUtilities;

import server.Server;
import ui.ProximityViewer;

public class main_server {
	
	static ProximityViewer window;

	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {

			public void run() {
				window = new ProximityViewer();

			}
		});

//		Server server = new Server(8080);
	}

}
