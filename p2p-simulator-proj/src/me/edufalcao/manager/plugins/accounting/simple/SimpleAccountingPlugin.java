package me.edufalcao.manager.plugins.accounting.simple;

import java.util.ArrayList;
import java.util.List;

import me.edufalcao.manager.TimeManager;
import me.edufalcao.manager.model.Peer;
import me.edufalcao.manager.model.Request;
import me.edufalcao.manager.plugins.accounting.AccountingInfo;
import me.edufalcao.manager.plugins.accounting.AccountingPlugin;

public class SimpleAccountingPlugin implements AccountingPlugin{	
	
	private Peer peer;
	private List<AccountingInfo> accountingList;
	private List<Request> finishedRequestsList;
	
	public SimpleAccountingPlugin(Peer peer){
		this.peer = peer;
		accountingList = new ArrayList<AccountingInfo>();
		finishedRequestsList = new ArrayList<Request>();
	}
	
	/**
	 * Updates the accounting for ongoing requests.
	 */
	@Override
	public void updateAll() {
		List<Request> requests = peer.getRequests();
		for(Request request : requests){			
			updateAccounting(request);				
		}
		
		/**
		 * Once the accounting for all requests of each peer was performed, 
		 * we now update the lastUpdatedTime of each accounting info. 
		 */
		for(AccountingInfo accInfo : accountingList)
			accInfo.setLastUpdated(TimeManager.getInstance().getTime());
				
		finishRequests();
	}
	
	/**
	 * Update requests of a specific peer.
	 */
	@Override
	public void update(Peer otherPeer) {
		List<Request> requests = peer.getRequests();
		for(Request request : requests){
			if(otherPeer.equals(request.getConsumer()) || otherPeer.equals(request.getProvider()))		
				updateAccounting(request);
		}	
		
		/**
		 * Once the accounting for all requests of this peer was performed, 
		 * we now update the lastUpdatedTime of this accounting info. 
		 */
		getAccountingInfo(otherPeer).setLastUpdated(TimeManager.getInstance().getTime());
		
		finishRequests();		
	}
	
	@Override
	public void add(Peer otherPeer) {
		AccountingInfo accountingInfo = getAccountingInfo(otherPeer);
		if(accountingInfo==null)
			accountingList.add(new AccountingInfo(otherPeer, TimeManager.getInstance().getTime()));
		else
			throw new IllegalArgumentException("Peer("+otherPeer.getId()+") is already in peer("+peer.getId()+")\'s accounting list!");
	}
	
	protected void updateAccounting(Request request){
		Peer otherPeer = null;
		boolean consuming;
		if(peer.equals(request.getConsumer())){		//then, the otherPeer is the provider
			otherPeer = request.getProvider();
			consuming = false;
		}
		else if(peer.equals(request.getProvider())){//then the otherPeer is the consumer
			otherPeer = request.getConsumer();
			consuming = true;
		}
		else
			throw new IllegalArgumentException("Peer("+peer.getId()+") should be consumer or provider, but it is neither!");
		
		AccountingInfo accountingInfo = getAccountingInfo(otherPeer);		
		if(accountingInfo!=null){
			
			double resourceUsage = 0;
			int accountingEndTime = 0;
			
			int endRequestTime = request.getSubmitTime() + request.getRuntime();
			if(TimeManager.getInstance().getTime() >= endRequestTime){
				accountingEndTime = endRequestTime;
				//peer.removeRequest(request);
				finishedRequestsList.add(request);
			}
			else
				accountingEndTime = TimeManager.getInstance().getTime();
			
			resourceUsage = accountingEndTime - Math.min(accountingEndTime, 
														 Math.max(accountingInfo.getLastUpdated(), request.getSubmitTime()));			
			if(consuming)
				accountingInfo.addConsumption(resourceUsage);
			else
				accountingInfo.addDonation(resourceUsage);
		} 
		else
			throw new IllegalArgumentException("Peer("+otherPeer.getId()+") should be on ("+peer.getId()+")\'s accounting list!");
	}	

	@Override
	public List<AccountingInfo> getAccountingList() {
		return accountingList;
	}

	@Override
	public AccountingInfo getAccountingInfo(Peer peer){
		for(AccountingInfo accountingInfo: accountingList)
			if(peer.equals(accountingInfo.getPeer()))
				return accountingInfo;
		
		return null;			
	}
	
	/**
	 * Removes all the requests finished from the peer's list.
	 */
	private void finishRequests(){
		peer.getRequests().removeAll(finishedRequestsList);
		finishedRequestsList.clear();
	}
	
	public Peer getPeer(){
		return peer;
	}

}
