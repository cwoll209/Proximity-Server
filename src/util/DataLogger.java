package util;

import java.util.LinkedList;
import java.util.List;

public class DataLogger {

	private static DataLogger instance = null;

	LinkedList<RSSIMessage> lifo;

	public synchronized static DataLogger getInstance() {
		if (instance == null) {
			instance = new DataLogger();
		}
		return instance;
	}

	private DataLogger() {
		lifo = new LinkedList<RSSIMessage>();
	}

	public synchronized RSSIMessage read() {

		return lifo.pollFirst();

	}

	public synchronized void write(RSSIMessage input) {
		lifo.addLast(input);

	}

}
