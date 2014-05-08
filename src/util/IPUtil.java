package util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import com.google.gson.Gson;

public class IPUtil {

	private IPUtil() {

	}

	public static String requestIP() throws IOException {
		String result;
		String url = "http://herbertron.pf-control.de/BLEPos/get.php";
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setDoOutput(true);

		String line = "";
		String response = "";
		InputStreamReader isr = new InputStreamReader(con.getInputStream());
		BufferedReader reader = new BufferedReader(isr);
		StringBuilder sb = new StringBuilder();
		while ((line = reader.readLine()) != null) {
			sb.append(line);
		}
		response = sb.toString();
		result = response.substring(3, response.length() - 3);

		System.out.println(result);
		return result;
	}

	public static String sendIP(int port) {
		// JsonObject o = new JsonObject();
		// o.addProperty("address", getIP());
		Gson gson = new Gson();
	

		String json;
		try {
			json = gson.toJson(getIP());
			System.out.println("sending: " + json);

			String url = "http://herbertron.pf-control.de/BLEPos/read.php";
			URL obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			con.setDoOutput(true);
			con.setRequestMethod("POST");
			con.setRequestProperty("json", json);

			OutputStream os = con.getOutputStream();
			DataOutputStream wr = new DataOutputStream(con.getOutputStream());
			// wr.write(new String("json=" + json).getBytes());
			String param = "json=" + URLEncoder.encode(json, "UTF-8");
			wr.write(param.getBytes());
			wr.flush();
			wr.close();

			int responseCode = con.getResponseCode();
			System.out.println(responseCode);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return getIP();
	}

	private static String getIP() {
		URL whatismyip = null;
		try {
			whatismyip = new URL("http://checkip.amazonaws.com");
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		BufferedReader in = null;
		try {
			in = new BufferedReader(new InputStreamReader(
					whatismyip.openStream()));
			String ip = in.readLine();
			return ip;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return "";
	}

}
