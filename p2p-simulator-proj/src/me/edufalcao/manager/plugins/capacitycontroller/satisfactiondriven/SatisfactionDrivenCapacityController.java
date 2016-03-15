package me.edufalcao.manager.plugins.capacitycontroller.satisfactiondriven;

import me.edufalcao.manager.model.Peer;
import me.edufalcao.manager.plugins.capacitycontroller.CapacityControllerPlugin;

public class SatisfactionDrivenCapacityController implements CapacityControllerPlugin{

	private Peer peer;
	
	public SatisfactionDrivenCapacityController(Peer peer) {
		this.peer = peer;
	}
	
	@Override
	public double getMaxCapacityToSupply(Peer peer) {
		return this.peer.getTOTAL_CAPACITY();
	}

}
