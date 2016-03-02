package me.edufalcao.manager.plugins.capacityController;

import me.edufalcao.manager.model.Peer;

public interface CapacityControllerPlugin {
	
	public double getMaxCapacityToSupply(Peer peer);

}
