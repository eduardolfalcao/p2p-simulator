package me.edufalcao.manager.plugins.capacitycontroller.fairnessdriven;

import java.util.List;

import me.edufalcao.manager.model.Peer;
import me.edufalcao.manager.plugins.accounting.AccountingInfo;
import me.edufalcao.manager.plugins.accounting.AccountingPlugin;
import me.edufalcao.manager.plugins.capacitycontroller.CapacityControllerPlugin;

public class FairnessDrivenCapacityController implements CapacityControllerPlugin{

	private AccountingPlugin accountingPlugin;
	
	private double currentPairwiseFairness, lastPairwiseFairness;
	private double currentGlobalFairness, lastGlobalFairness;
	
	private boolean increasing;
	private double deltaC;
	private double minimumThreshold, maximumThreshold;
	private double maximumCapacityToSupply;
	
	private final double MAXIMUM_CAPACITY_OF_PEER;
	
	public FairnessDrivenCapacityController(AccountingPlugin accountingPlugin, double deltaC,
			double minimumThreshold, double maximumThreshold, double maximumCapacityOfPeer) {
		this.accountingPlugin = accountingPlugin;
		currentPairwiseFairness = lastPairwiseFairness = 0;
		currentGlobalFairness = lastGlobalFairness = 0;
		increasing = false;		
		
		if(minimumThreshold < 0 || maximumThreshold < 0 || deltaC > 1 || deltaC < 0)
			throw new IllegalArgumentException("Unexpected argument for the FDController: "+this+"\n"
					+ "Any of this conditions were (but shouldn't be) satisfied: minimumThreshold < 0 || maximumThreshold < 0 || deltaC > 1 || deltaC < 0");
		this.deltaC = deltaC;
		this.minimumThreshold = minimumThreshold;
		this.maximumThreshold = maximumThreshold;
		this.maximumCapacityToSupply = maximumCapacityOfPeer;
		this.MAXIMUM_CAPACITY_OF_PEER = maximumCapacityOfPeer;
	}
	
	@Override
	public String toString() {
		return "Params of Fairness Driven Capacity Controller - minimumThreshold: "+minimumThreshold+", "
				+ "maximumThreshold: "+maximumThreshold+", deltaC: "+deltaC+".";
	}
	
	//FIXME insert time checking to avoid updating twice at the same timestep
	// this would make the controller lose its last value
	
	@Override
	public double getMaxCapacityToSupply(Peer peer) {
		updatePairwiseFairness(peer);		
		if(currentPairwiseFairness>=0)
			return getMaxCapacityFromFairness(lastPairwiseFairness, currentPairwiseFairness);
		else{
			updateGlobalFairness();
			return getMaxCapacityFromFairness(lastGlobalFairness, currentGlobalFairness);
		}
	}
	
	//CHECK if the 'increasing' variable shouldn't be different for global and pairwise controllers
	
	protected double getMaxCapacityFromFairness(double lastFairness, double currentFairness){
		if(currentFairness < minimumThreshold)
			increasing = false;
		else if(currentFairness > maximumThreshold)
			increasing = true;
		else{
			if(currentFairness <= lastFairness)
				increasing = !increasing;
		}
		
		if(increasing)
			maximumCapacityToSupply = Math.min(MAXIMUM_CAPACITY_OF_PEER, maximumCapacityToSupply + (deltaC * MAXIMUM_CAPACITY_OF_PEER));
		else
			maximumCapacityToSupply = Math.max(0, maximumCapacityToSupply - (deltaC * MAXIMUM_CAPACITY_OF_PEER));
		
		return maximumCapacityToSupply;				
	}
	
	protected void updatePairwiseFairness(Peer peer){
		lastPairwiseFairness = currentPairwiseFairness;		
		//this is what the peer (argument) consumed
		double currentDonated = accountingPlugin.getAccountingInfo(peer).getConsumed();
		//this is what the peer (argument) donated
		double currentConsumed = accountingPlugin.getAccountingInfo(peer).getDonated();				
		currentPairwiseFairness = getFairness(currentConsumed, currentDonated);  
	}
	
	protected void updateGlobalFairness(){
		lastGlobalFairness = currentGlobalFairness;		
		double currentConsumed = 0, currentDonated = 0;
		List<AccountingInfo> accountingList = accountingPlugin.getAccountingList();
		for(AccountingInfo acc : accountingList){
			currentDonated += acc.getConsumed();
			currentConsumed += acc.getDonated();			
		}
		currentGlobalFairness = getFairness(currentConsumed, currentDonated);		
	}
	
	protected double getFairness(double consumed, double donated){
		if(donated < 0 || consumed < 0)
			throw new IllegalArgumentException("Donated and consumed must be >=0. It should never be <0.\n"+
					"Donated("+donated+") and Consumed("+consumed+")");
		else if(donated == 0)
			return -1;		
		else
			return consumed/donated;
	}
	
	
	/**
	 * For MOCKING purposes.
	 */
	
	protected double getCurrentPairwiseFairness() {
		return currentPairwiseFairness;
	}
	
	protected double getLastPairwiseFairness() {
		return lastPairwiseFairness;
	} 
	
	protected double getCurrentGlobalFairness() {
		return currentGlobalFairness;
	}
	
	protected double getLastGlobalFairness() {
		return lastGlobalFairness;
	}
	
	

}
