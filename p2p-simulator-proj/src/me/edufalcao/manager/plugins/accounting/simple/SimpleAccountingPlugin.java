package me.edufalcao.manager.plugins.accounting.simple;

import java.util.ArrayList;
import java.util.List;

import me.edufalcao.manager.model.Peer;
import me.edufalcao.manager.model.Request;
import me.edufalcao.manager.model.events.request.RequestEvent;
import me.edufalcao.manager.plugins.accounting.AccountingPlugin;

public class SimpleAccountingPlugin implements AccountingPlugin{	
	
	private Peer peer;
	private List<AccountingInfo> accountingList;
	
	public SimpleAccountingPlugin(Peer peer){
		this.peer = peer;
		accountingList = new ArrayList<AccountingInfo>();
	}

	/**
	 * Updates the accounting for ongoing requests.
	 */
	@Override
	public void update() {
		List<Request> requests = peer.getRequests();
		for(Request request : requests){
			if(peer.equals(request.getConsumer()))	//then, the otherPeer is the provider
				updateAccounting(request.getProvider(), false);
			else									//then the otherPeer is the consumer
				updateAccounting(request.getConsumer(), true);						
		}
	}
	
	@Override
	public void update(Peer otherPeer) {
		List<Request> requests = otherPeer.getRequests();
		for(Request request : requests){
			if(otherPeer.equals(request.getConsumer()))	//then, the otherPeer is the consumer
				updateAccounting(request.getConsumer(), true);	
			else										//then, the otherPeer is the provider
				updateAccounting(request.getProvider(), false);				
		}		
	}
	
	private void updateAccounting(Peer otherPeer, boolean consuming){
		AccountingInfo accountingInfo = getAccountingInfo(otherPeer);
		double resourceUsage = getTime()-accountingInfo.getLastUpdated();
		if(consuming)
			accountingInfo.addConsumption(resourceUsage);
		else
			accountingInfo.addDonation(resourceUsage);
		accountingInfo.setLastUpdated(getTime());		
	}
	
	

	@Override
	public List<AccountingInfo> getAccountingList() {
		return accountingList;
	}

	@Override
	public AccountingInfo getAccountingInfo(Peer peer) {
		for(AccountingInfo accountingInfo: accountingList)
			if(peer.equals(accountingInfo.getPeer()))
				return accountingInfo;
		
		return null;			
	}
	
	private int getTime(){
		return 0;	//FIXME
	}

	

}
