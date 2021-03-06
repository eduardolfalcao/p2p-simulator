package me.edufalcao.manager.model;

import java.util.ArrayList;
import java.util.List;

import me.edufalcao.manager.plugins.accounting.AccountingInfo;
import me.edufalcao.manager.plugins.accounting.AccountingPlugin;
import me.edufalcao.manager.plugins.capacitycontroller.CapacityControllerPlugin;
import me.edufalcao.manager.plugins.peerchooser.PeerChooserPlugin;
import me.edufalcao.manager.plugins.prioritization.PrioritizationPlugin;

public class Peer {
	
	
	private final double TOTAL_CAPACITY;
	private String id;
	private AccountingPlugin accountingPlugin;
	private PeerChooserPlugin peerChooserPlugin;
	private PrioritizationPlugin prioritizationPlugin;
	private CapacityControllerPlugin capacityControllerPlugin;
	private boolean freerider;
	
	private List<Request> requests;
	
	public Peer(double totalCapacity, String id, boolean freerider) {
		super();
		TOTAL_CAPACITY = totalCapacity;
		this.id = id;
		this.accountingPlugin = null;
		this.peerChooserPlugin = null;
		this.prioritizationPlugin = null;
		this.capacityControllerPlugin = null;
		this.freerider = freerider;
		requests = new ArrayList<Request>();
	}
	
	
	//used for tests
	public Peer(String id){
		TOTAL_CAPACITY = 0;
		this.id = id;
		requests = new ArrayList<Request>();
	}
	
	@Override
	public String toString() {
		return "Peerd("+id+"): Capacity("+TOTAL_CAPACITY+"), FR="+freerider;
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
	
	public List<Request> getRequests() {
		return requests;
	}
	
	public void removeRequest(Request request){
		requests.remove(request);
	}
	
	public double getTOTAL_CAPACITY() {
		return TOTAL_CAPACITY;
	}
	
	public CapacityControllerPlugin getCapacityControllerPlugin() {
		return capacityControllerPlugin;
	}
	
	public void setCapacityControllerPlugin(
			CapacityControllerPlugin capacityControllerPlugin) {
		this.capacityControllerPlugin = capacityControllerPlugin;
	}
	
	public void setPrioritizationPlugin(
			PrioritizationPlugin prioritizationPlugin) {
		this.prioritizationPlugin = prioritizationPlugin;
	}
}
