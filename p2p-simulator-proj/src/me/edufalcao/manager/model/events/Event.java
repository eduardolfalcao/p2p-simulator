package me.edufalcao.manager.model.events;

public abstract class Event {
	
	protected int initialTime;
	
	public Event(int initialTime) {
		this.initialTime = initialTime;
	}
	
	public abstract void run();
	
	public int getInitialTime(){
		return initialTime;
	}
	
}
