package me.edufalcao.manager.plugins.scheduler.workloadbased;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import me.edufalcao.manager.model.events.Event;
import me.edufalcao.manager.model.events.request.RequestEvent;
import me.edufalcao.manager.plugins.scheduler.workloadbased.exceptions.JobOutOfIntervalException;

public class LogReader {

	public static List<Event> readWorkload(String inputFile, int initialTime,
			int endTime) {

		List<Event> events = new ArrayList<Event>();

		try {
			BufferedReader bufReader = new BufferedReader(new FileReader(
					inputFile));
			// skip first line
			String line = bufReader.readLine();
			// read the rest of lines
			while ((line = bufReader.readLine()) != null)
				events.add(readTask(line, initialTime, endTime));
			bufReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JobOutOfIntervalException e) {
			e.printStackTrace();
			return events;
		}
		return events;
	}

	private static Event readTask(String line, int initialTime, int endTime)
			throws JobOutOfIntervalException {
		// peerId jobId submitTime runTime
		// P4 U33-J1-T0 3 15

		String[] values = line.split(",");
		String peerId = values[0];
		String jobId = values[1];
		String submitTime = values[2];
		String runtime = values[3];

		if (Integer.parseInt(submitTime) >= initialTime
				&& Integer.parseInt(submitTime) <= endTime) {
			return new RequestEvent(peerId, Integer.parseInt(submitTime),
					Integer.parseInt(runtime), jobId);
		} else {
			throw new JobOutOfIntervalException("Job submit time is "
					+ submitTime + ". " + "It should be inside the interval ["
					+ initialTime + "," + endTime + "]");
		}
	}

}
