package com.test.ui;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class TextLog {



	JTextArea logText;
	JPanel logPanel;
	JScrollPane scrollPane;



	public TextLog() {
		init();
	}

	private void init() {

		logPanel = new JPanel(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();
		gc.fill = GridBagConstraints.BOTH;
		gc.weightx = 1d;
		gc.weighty = 1;
		logPanel.setBackground(Color.red);
		logText = new JTextArea();
		logText.setEditable(false);
		logText.setVisible(true);
		logPanel.setVisible(true);
		scrollPane = new JScrollPane(logText);
		logPanel.add(scrollPane, gc);

	}

	public void addToPanel(JPanel p, GridBagConstraints gc) {
		p.add(logPanel, gc);
	}

	public synchronized void writeToLog(String s) {
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		java.util.Date date= new java.util.Date();

		logText.append("["+sdf.format(date)+"] - "+s);
	}

	public void writeLineToLog(String s) {
		writeToLog(s + "\n");
	}

	public void writeLogToFile(String fileName) {

	}

	public void clearLog() {
		logText.setText("");
	}
}
