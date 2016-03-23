package me.edufalcao.manager;

public class TimeManager {
	
	private static TimeManager instance;
	
	private int time;
	
	private TimeManager(){
		time = 0;
	}
	
	public static TimeManager getInstance(){
		if(instance==null)
			instance = new TimeManager();
		return instance;
	}
	
	public int getTime() {
		return time;
	}
	
	public void setTime(int time) {
		this.time = time;
	}

}
