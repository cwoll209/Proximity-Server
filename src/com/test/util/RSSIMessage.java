package com.test.util;

import java.io.Serializable;

import com.test.util.RSSIData;

public class RSSIMessage implements Serializable {

	private static int messagenrcount = 0;
	private static final long serialVersionUID = 1L;
	public RSSIData[] data;

	public boolean end = false;
	private int msgNr;

	public RSSIMessage() {
		data = new RSSIData[0];
		this.end = false;
		msgNr = messagenrcount;
		messagenrcount++;
	}

	public RSSIMessage(boolean end, RSSIData... rssi) {
		data = rssi;
		msgNr = messagenrcount;
		messagenrcount++;
		this.end = end;
	}

	public String toString() {
		RSSIData temp;

		String result = "Beacon-MessageBundle #" + msgNr + "\n";

		if (data != null) {
			for (int i = 0; i < data.length; i++) {
				result += "#" + i + "\n";
				temp = data[i];
				result += "UUID: " + temp.uuid;
				result += " - Minor: " + temp.major;
				result += " - Major: " + temp.minor + "\n";
				result += "RSSI: " + temp.rssi;
			}

		} else {
			result += "Message contains no Data";
		}
		return result;

	}
}