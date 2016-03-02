package me.edufalcao.manager.plugins.scheduler;

import me.edufalcao.manager.model.events.Event;

public interface SchedulerPlugin {
	
	public void schedule(Event e);
	
	//maybe we can have an "endEvent()"

}
