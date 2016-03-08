package me.edufalcao.manager.plugins.scheduler.workloadbased;

import static org.junit.Assert.*;

import java.util.List;

import me.edufalcao.manager.model.events.Event;
import me.edufalcao.manager.model.events.request.RequestEvent;
import me.edufalcao.manager.plugins.scheduler.workloadbased.LogReader;

import org.junit.Test;

public class TestLogReader {
	
	private static final String WORKLOAD_PATH = "test/workload/";
	private static final String FILE_PATH_SMALL = WORKLOAD_PATH+"small-workload.csv";
	private static final String FILE_PATH_LARGE = WORKLOAD_PATH+"large-workload.csv";
	
	@Test
	public void testReadTask(){
		// peerId jobId submitTime runTime
		// P4 U33-J1-T0 3 15
		
		String task1 = "P4,U33-J1-T3,3,13";
		
		int initialTime = 0, endTime = 50;
		Event event = LogReader.readTask(task1, initialTime, endTime);
		
		assertNotNull(event);
		assertTrue(event instanceof RequestEvent);
		
		RequestEvent reqEvent = (RequestEvent) event;
		
		assertEquals(3, reqEvent.getInitialTime());
		assertEquals(13, reqEvent.getRuntime());
		assertEquals("P4", reqEvent.getPeerId());
		assertEquals("U33-J1-T3", reqEvent.getRequestId());
	}
	
	/**
	 * Be careful! The log file must not contain a blank line in the end.
	 */
	
	@Test
	public void testReadWorkloadSmallFile() {		
		List<Event> events = LogReader.readWorkload(FILE_PATH_SMALL, 0, Integer.MAX_VALUE);
		assertEquals(49, events.size());
		
		events = LogReader.readWorkload(FILE_PATH_SMALL, 10, 20);
		assertEquals(7, events.size());
	}
	
	@Test
	public void testReadWorkloadHugeFile() {		
		List<Event> events = LogReader.readWorkload(FILE_PATH_LARGE, 0, Integer.MAX_VALUE);
		assertEquals(945582, events.size());
	}
	
	
	

}