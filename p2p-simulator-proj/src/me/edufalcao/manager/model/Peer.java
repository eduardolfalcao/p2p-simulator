package me.edufalcao.manager.model;

import me.edufalcao.manager.plugins.capacityController.CapacityControllerPlugin;
import me.edufalcao.manager.plugins.prioritization.PrioritizationPlugin;

public class Peer {
	
	
	private final double TOTAL_CAPACITY;
	private String id;
	private PrioritizationPlugin prioritizationPlugin;
	private CapacityControllerPlugin capacityControllerPlugin;
	private boolean freerider;
	
	public Peer(double totalCapacity, String id,
			PrioritizationPlugin prioritizationPlugin,
			CapacityControllerPlugin capacityControllerPlugin, boolean freerider) {
		super();
		TOTAL_CAPACITY = totalCapacity;
		this.id = id;
		this.prioritizationPlugin = prioritizationPlugin;
		this.capacityControllerPlugin = capacityControllerPlugin;
		this.freerider = freerider;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof Peer))
			return false;
		else{
			Peer peer = (Peer) obj;
			if(id.equals(peer.getId()))
				return true;
			else
				return false;
		}
	}
	
	@Override
	public int hashCode() {
       return this.id.hashCode();
   }
	
	public String getId(){
		return id;
	}
	
	public boolean isFreerider(){
		return freerider;
	}
}
