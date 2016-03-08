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
	private int lastUpdateTime;
	
	public SimpleAccountingPlugin(Peer peer){
		this.peer = peer;
		accountingList = new ArrayList<AccountingInfo>();
		lastUpdateTime = 0;
	}

	@Override
	public void update() {
		List<Request> requests = peer.getRequests();
		for(Request request : requests){
			Peer otherPeer = request.getConsumer().equals(peer)?request.getProvider():request.getConsumer();
			AccountingInfo accountingInfo = getAccountingInfo(otherPeer);
			
		}
			
		//lastUpdateTime = simulator.getTime();	FIXME	
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

}
