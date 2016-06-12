package apm.idm;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class UserTimeout {

	@SuppressWarnings("unchecked")
	public static void main(String[] args) {

		// *****Create Metrics******
		// Array of actual Metrics WITHOUT the metrics KEY in front
		JSONArray metricArray = new JSONArray();
		metricArray.add(createMetric("IntCounter", "SAM:UserTimeouts", getUserTimout()));
		JSONObject metricsToEPAgent = new JSONObject();
		metricsToEPAgent.put("metrics", metricArray);

		// Location & Port of EPAgent
		WebServiceHandler.sendMetric(metricsToEPAgent.toString(), "localhost", 9080);

		userTOupdated(getUserTimout());

	}

	/////////
	public static int getUserTimout() {

		int count = 0;
		try {
			if (!new File("UserTimeout.txt").exists())
				return 1;
			else {
				BufferedReader br = new BufferedReader(new FileReader(new File("UserTimeout.txt")));
				String s = br.readLine();
				count = Integer.parseInt(s);
				br.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

	////////
	public static void userTOupdated(int count) {
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(new File("UserTimeout.txt")));
			System.out.println("UserTimeout Before Increase= " + count);
			bw.write(Integer.toString(++count));
			System.out.println("UserTimeouts After increase= " + count);
			bw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public static JSONObject createMetric(String type, String name, Object value) {

		JSONObject metric = new JSONObject();
		metric.put("type", type);
		metric.put("name", name);
		metric.put("value", value);
		return metric;

	}

}
