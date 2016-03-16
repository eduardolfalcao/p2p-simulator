package me.edufalcao.manager.plugins.capacitycontroller.fairnessdriven;

import java.util.HashMap;
import java.util.Map;

import me.edufalcao.manager.model.Peer;
import me.edufalcao.manager.plugins.accounting.AccountingPlugin;
import me.edufalcao.manager.plugins.accounting.simple.SimpleAccountingPlugin;
import me.edufalcao.manager.plugins.capacitycontroller.CapacityControllerPlugin;

public class PairwiseFairnessDrivenController extends FairnessDrivenCapacityController {
	
	private Map<Peer, Integer> lastUpdated;
	
	private HillClimbingAlgorithm controller;
	
	public PairwiseFairnessDrivenController(AccountingPlugin accountingPlugin, double deltaC,
			double minimumThreshold, double maximumThreshold, double maximumCapacityOfPeer) {
		super.accountingPlugin = accountingPlugin;
		currentFairness = lastFairness = -1;
		lastUpdated = new HashMap<Peer, Integer>();
		
		controller = new HillClimbingAlgorithm(deltaC, minimumThreshold, maximumThreshold, maximumCapacityOfPeer);
	}

	@Override
	public double getMaxCapacityToSupply(Peer peer) {
		if(lastUpdated.containsKey(peer)){
			if(lastUpdated.get(peer).intValue() == getTime()){
				Peer thisPeer = null;
				if(accountingPlugin instanceof	SimpleAccountingPlugin)
					thisPeer = ((SimpleAccountingPlugin)accountingPlugin).getPeer();
				throw new IllegalStateException("The controller of peer("+thisPeer.getId()+") is running more than once at the same time step for peer("+peer.getId()+").");			
			}			
		}		
		lastUpdated.put(peer, getTime());		
		updateFairness(peer);	
		return controller.getMaxCapacityFromFairness(lastFairness, currentFairness);				
	}
	
	protected void updateFairness(Peer peer){
		lastFairness = currentFairness;		
		//this is what the peer (argument) consumed
		double currentDonated = accountingPlugin.getAccountingInfo(peer).getConsumed();
		//this is what the peer (argument) donated
		double currentConsumed = accountingPlugin.getAccountingInfo(peer).getDonated();				
		currentFairness = getFairness(currentConsumed, currentDonated);  
	}

}
