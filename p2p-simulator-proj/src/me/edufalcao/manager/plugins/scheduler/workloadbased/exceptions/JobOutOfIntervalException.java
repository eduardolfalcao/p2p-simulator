package me.edufalcao.manager.plugins.scheduler.workloadbased.exceptions;

public class JobOutOfIntervalException extends RuntimeException{
	
	public JobOutOfIntervalException(String message){
		super(message);
	}

}
