package me.edufalcao.manager.plugins.scheduler.workloadbased;

import java.util.List;

import me.edufalcao.manager.model.events.Event;
import me.edufalcao.manager.plugins.scheduler.SchedulerPlugin;

public class WorkloadScheduler implements SchedulerPlugin{

	private String inputFile;
	private int grainTime;
	private int endTime;
	
	public WorkloadScheduler(String inputFile, int grainTime, int endTime) {
		this.inputFile = inputFile;
		this.grainTime = grainTime;
		this.endTime = endTime;		
	}
	
	@Override
	public void schedule(Event e) {
		// TODO Auto-generated method stub
		
	}
	
	public void init(){
		for(int i = 0; i <= endTime; i+=grainTime){
		}
	}
	
}
