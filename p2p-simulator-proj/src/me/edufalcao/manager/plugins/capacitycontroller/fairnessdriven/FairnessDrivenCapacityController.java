package me.edufalcao.manager.plugins.capacitycontroller.fairnessdriven;

import me.edufalcao.manager.model.Peer;
import me.edufalcao.manager.plugins.accounting.AccountingPlugin;
import me.edufalcao.manager.plugins.capacitycontroller.CapacityControllerPlugin;

public abstract class FairnessDrivenCapacityController implements CapacityControllerPlugin{

	protected AccountingPlugin accountingPlugin;	
	
	protected double getFairness(double consumed, double donated){
		if(donated < 0 || consumed < 0)
			throw new IllegalArgumentException("Donated and consumed must be >=0. It should never be <0.\n"+
					"Donated("+donated+") and Consumed("+consumed+")");
		else if(donated == 0)
			return -1;		
		else
			return consumed/donated;
	}	
	
	public abstract double getCurrentFairness(Peer peer);
	public abstract double getLastFairness(Peer peer);
	
	/**
	 * For MOCKING purposes.
	 */
	//this will probably be removed
	protected int getTime(){
		return 0;	//FIXME
	}
	
}
