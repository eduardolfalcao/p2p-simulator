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
		super.accountingPlugin = accountingPlugin;
		lastUpdated = -1;		
		controller = new HillClimbingAlgorithm(deltaC, minimumThreshold, maximumThreshold, maximumCapacityOfPeer);
	}

	@Override
	public double getMaxCapacityToSupply(Peer peer) {
		if(lastUpdated!=getTime()){
			//time is different, then we must compute the new maxCapacity
			lastUpdated = getTime();
			return controller.getMaxCapacityFromFairness();
		}	
		//time is equal, then the maxCapacity was already computed, here we just return
		return controller.getMaximumCapacityToSupply();			
	}
	
	protected void updateFairness(){
		controller.setLastFairness(controller.getCurrentFairness());
		double currentConsumed = 0, currentDonated = 0;
		List<AccountingInfo> accountingList = accountingPlugin.getAccountingList();
		for(AccountingInfo acc : accountingList){
			currentDonated += acc.getConsumed();
			currentConsumed += acc.getDonated();			
		}
		controller.setCurrentFairness(getFairness(currentConsumed, currentDonated));
	}
	
	@Override
	public double getCurrentFairness(Peer peer) {
		return controller.getCurrentFairness();
	}
	
	@Override
	public double getLastFairness(Peer peer) {
		return controller.getLastFairness();
	}

}
