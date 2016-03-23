package me.edufalcao.manager.plugins.capacitycontroller.fairnessdriven;

import me.edufalcao.manager.model.Peer;
import me.edufalcao.manager.plugins.capacitycontroller.CapacityControllerPlugin;

public class TwoFoldFairnessDrivenController implements CapacityControllerPlugin{

	private FairnessDrivenCapacityController pairwiseController;
	private FairnessDrivenCapacityController globalController;
		
	public TwoFoldFairnessDrivenController(FairnessDrivenCapacityController pairwiseController, FairnessDrivenCapacityController globalController) {
		this.pairwiseController = pairwiseController;
		this.globalController = globalController;
	}	
	
	@Override
	public double getMaxCapacityToSupply(Peer peer) {
		double pairwiseLimit = 0, globalLimit = 0;
		pairwiseLimit = pairwiseController.getMaxCapacityToSupply(peer);
		globalLimit = globalController.getMaxCapacityToSupply(peer);
		if(pairwiseController.getCurrentFairness(peer)>=0)
			return pairwiseLimit;
		else
			return globalLimit;			
	}

}
