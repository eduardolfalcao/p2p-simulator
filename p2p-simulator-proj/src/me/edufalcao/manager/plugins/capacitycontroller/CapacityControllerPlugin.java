package me.edufalcao.manager.plugins.capacitycontroller;

import me.edufalcao.manager.model.Peer;

public interface CapacityControllerPlugin {
	
	public double getMaxCapacityToSupply(Peer peer);

}
