package com.test.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;
import java.io.IOException;

import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.test.server.Server;
import com.test.util.DataLogger;
import com.test.util.RSSIMessage;

public class ProximityViewer extends JFrame {
	Server server;
	Thread serverThread, dataThread;
	JPanel controlPanel;
	JPanel canvasPanel;
	JPanel rootPanel;
	TextLog textLog;
	JButton btnStart;
	boolean hasBeenStarted;

	public ProximityViewer() {
		pack();
		hasBeenStarted = false;
		rootPanel = new JPanel(new GridBagLayout());

		setTitle("Proximity Viewer");
		setSize(800, 600);
		// setPreferredSize(new Dimension(800, 600));
		getContentPane().add(rootPanel);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		setBackground(Color.black);
		init();
	}

	public void init() {
		initThreads();
		initCanvasPanel();
		initLogPanel();
		initControlPanel();
		server = new Server(8080);

	}

	public void initThreads() {
		dataThread = new Thread(new Runnable() {

			public void run() {
				RSSIMessage container;
				while (true) {
					container = DataLogger.getInstance().read();
					if (container != null) {
						textLog.writeLineToLog(container.toString());
					} else {
						// textLog.writeLineToLog("!LIFO Empty!");
					}
				}
			}
		});
		serverThread = new Thread(new Runnable() {

			public void run() {

				try {
					server.run();

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});

	}

	private void initLogPanel() {
		textLog = new TextLog();
		GridBagConstraints gc = new GridBagConstraints();
		gc.gridx = 0;
		gc.gridy = 1;
		gc.gridwidth = 2;
		gc.fill = GridBagConstraints.BOTH;
		gc.weightx = 1d;
		gc.weighty = 1;
		textLog.addToPanel(rootPanel, gc);

	}

	public void initControlPanel() {
		btnStart = new JButton("Start server");

		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("Start button pressed");

			if(!hasBeenStarted)
				serverThread.start();
				dataThread.start();

			}
		});

		JButton btnShutdown = new JButton("Shutdown server");

		btnShutdown.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				System.out.println("Shutdown button pressed");
				hasBeenStarted = true;
				try {
					server.shutdown();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});

		GridBagConstraints gc = new GridBagConstraints();
		gc.gridx = 1;
		gc.gridy = 0;

		gc.fill = GridBagConstraints.BOTH;

		controlPanel = new JPanel();
		Box b = Box.createVerticalBox();
		b.add(btnStart);
		b.add(Box.createVerticalStrut(20));
		b.add(btnShutdown);
		controlPanel.setBackground(Color.WHITE);
		controlPanel.setBorder(BorderFactory.createLineBorder(Color.GREEN));
		controlPanel.add(b);

		rootPanel.add(controlPanel, gc);

	}

	public void initCanvasPanel() {
		GridBagConstraints gc = new GridBagConstraints();
		gc.gridx = 0;
		gc.gridy = 0;
		gc.fill = GridBagConstraints.BOTH;
		gc.weightx = 3d;
		gc.weighty = 3d;
		canvasPanel = new JPanel();
		canvasPanel.setBackground(Color.black);
		rootPanel.add(canvasPanel, gc);
	}

}
