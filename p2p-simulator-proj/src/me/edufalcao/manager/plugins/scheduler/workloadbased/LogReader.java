package me.edufalcao.manager.plugins.scheduler.workloadbased;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import me.edufalcao.manager.model.events.Event;
import me.edufalcao.manager.model.events.request.RequestEvent;

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
			while ((line = bufReader.readLine()) != null){
				Event event = readTask(line, initialTime, endTime);
				if(event.getInitialTime() < initialTime)
					continue;
				else if(event.getInitialTime() >= initialTime
						&& event.getInitialTime() <= endTime)
				events.add(event);
				else
					break;			
			}
			bufReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		} 
		return events;
	}

	protected static Event readTask(String line, int initialTime, int endTime){
		// peerId jobId submitTime runTime
		// P4 U33-J1-T0 3 15

		String[] values = line.split(",");
		String peerId = values[0];
		String jobId = values[1];
		String submitTime = values[2];
		String runtime = values[3];
		
		return new RequestEvent(peerId, Integer.parseInt(submitTime),
				Integer.parseInt(runtime), jobId);
	}

}