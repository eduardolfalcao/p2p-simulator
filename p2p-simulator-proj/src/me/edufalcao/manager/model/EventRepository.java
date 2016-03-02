package me.edufalcao.manager.model;

import java.util.List;
import java.util.ArrayList;
import me.edufalcao.manager.model.events.Event;

public class EventRepository {
	
	private List<Event> events;
	private static EventRepository instance;
	
	private EventRepository(){
		events = new ArrayList<Event>();
	}
	
	public static EventRepository getInstance(){
		if(instance == null)
			instance = new EventRepository();
		return instance;
	}
	
	public void add(Event ev){
		events.add(ev);
	}
	
	public List<Event> getEvents(){
		return events;
	}

}
