package me.edufalcao.manager.plugins.capacitycontroller.fairnessdriven;

import java.util.List;

import me.edufalcao.manager.model.Peer;
import me.edufalcao.manager.plugins.accounting.AccountingInfo;
import me.edufalcao.manager.plugins.accounting.AccountingPlugin;
import me.edufalcao.manager.plugins.capacitycontroller.CapacityControllerPlugin;

public class GlobalFairnessDrivenController extends FairnessDrivenCapacityController{
	
	private int lastUpdated;	
	private HillClimbingAlgorithm controller;
	
	public GlobalFairnessDrivenController(Peer peer, AccountingPlugin accountingPlugin, double deltaC,
			double minimumThreshold, double maximumThreshold, double maximumCapacityOfPeer) {
		this.accountingPlugin = accountingPlugin;
		currentFairness = lastFairness = -1;
		lastUpdated = -1;		
		controller = new HillClimbingAlgorithm(deltaC, minimumThreshold, maximumThreshold, maximumCapacityOfPeer);
	}

	@Override
	public double getMaxCapacityToSupply(Peer peer) {
		if(lastUpdated!=getTime()){
			//time is different, then we must compute the new maxCapacity
			lastUpdated = getTime();
			return controller.getMaxCapacityFromFairness(lastFairness, currentFairness);
		}	
		//time is equal, then the maxCapacity was already computed, here we just return
		return controller.getMaximumCapacityToSupply();			
	}
	
	protected void updateFairness(){
		lastFairness = currentFairness;		
		double currentConsumed = 0, currentDonated = 0;
		List<AccountingInfo> accountingList = accountingPlugin.getAccountingList();
		for(AccountingInfo acc : accountingList){
			currentDonated += acc.getConsumed();
			currentConsumed += acc.getDonated();			
		}
		currentFairness = getFairness(currentConsumed, currentDonated);		
	}

}
