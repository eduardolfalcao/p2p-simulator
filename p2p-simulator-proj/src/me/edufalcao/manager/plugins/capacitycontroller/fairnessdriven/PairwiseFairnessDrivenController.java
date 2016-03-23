package me.edufalcao.manager.plugins.capacitycontroller.fairnessdriven;

import java.util.HashMap;
import java.util.Map;

import me.edufalcao.manager.model.Peer;
import me.edufalcao.manager.plugins.accounting.AccountingPlugin;
import me.edufalcao.manager.plugins.accounting.simple.SimpleAccountingPlugin;
import me.edufalcao.manager.plugins.capacitycontroller.CapacityControllerPlugin;

public class PairwiseFairnessDrivenController extends FairnessDrivenCapacityController {
	
	//hillClimbing parameters
	private double deltaC, minimumThreshold, maximumThreshold, maximumCapacityOfPeer;
	
	private Map<Peer, HillClimbingAlgorithm> controllers;
	
	public PairwiseFairnessDrivenController(AccountingPlugin accountingPlugin, double deltaC,
			double minimumThreshold, double maximumThreshold, double maximumCapacityOfPeer) {
		super.accountingPlugin = accountingPlugin;
		controllers = new HashMap<Peer, HillClimbingAlgorithm>();	
		
		this.deltaC = deltaC;
		this.minimumThreshold = minimumThreshold;
		this.maximumThreshold = maximumThreshold;
		this.maximumCapacityOfPeer = maximumCapacityOfPeer;
	}

	@Override
	public double getMaxCapacityToSupply(Peer peer) {
		
		if(controllers.containsKey(peer) && controllers.get(peer).getLastUpdated() == getTime()){			
			Peer thisPeer = null;
			if(accountingPlugin instanceof	SimpleAccountingPlugin)
				thisPeer = ((SimpleAccountingPlugin)accountingPlugin).getPeer();
			throw new IllegalStateException("The controller of peer("+thisPeer.getId()+") is running more than once at the same time step for peer("+peer.getId()+").");			
		} 
		else if(!controllers.containsKey(peer)){
			controllers.put(peer, new HillClimbingAlgorithm(deltaC, minimumThreshold, maximumThreshold, maximumCapacityOfPeer));
		}
		
		controllers.get(peer).setLastUpdated(getTime());
		updateFairness(peer);	
		return controllers.get(peer).getMaxCapacityFromFairness();				
	}
	
	protected void updateFairness(Peer peer){
		//update last fairness
		controllers.get(peer).setLastFairness(controllers.get(peer).getCurrentFairness());		
		//this is what the peer (argument) consumed
		double currentDonated = accountingPlugin.getAccountingInfo(peer).getConsumed();
		//this is what the peer (argument) donated
		double currentConsumed = accountingPlugin.getAccountingInfo(peer).getDonated();		
		//update current fairness
		controllers.get(peer).setCurrentFairness(getFairness(currentConsumed, currentDonated));
	}
	
	@Override
	public double getCurrentFairness(Peer peer) {		
		return controllers.get(peer)==null?-1:controllers.get(peer).getCurrentFairness();
	}
	
	@Override
	public double getLastFairness(Peer peer) {
		return controllers.get(peer)==null?-1:controllers.get(peer).getLastFairness();
	}
	
	protected Map<Peer, HillClimbingAlgorithm> getControllers() {
		return controllers;
	}

}
