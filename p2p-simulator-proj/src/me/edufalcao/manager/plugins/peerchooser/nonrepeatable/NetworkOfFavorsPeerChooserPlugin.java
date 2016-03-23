package me.edufalcao.manager.plugins.peerchooser.nonrepeatable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import me.edufalcao.manager.model.Peer;
import me.edufalcao.manager.plugins.accounting.AccountingInfo;
import me.edufalcao.manager.plugins.accounting.AccountingPlugin;

public class NetworkOfFavorsPeerChooserPlugin extends TimeBasedPeerChooserPlugin{

	private AccountingPlugin accountingPlugin;
	private List<Peer> alreadyChosen;
	
	public NetworkOfFavorsPeerChooserPlugin(AccountingPlugin accountingPlugin) {
		super();
		this.accountingPlugin = accountingPlugin;
		alreadyChosen = new ArrayList<Peer>();
	}
	
	@Override
	public Peer choose(List<Peer> peers) {
		updateListAndTime();		
		Peer chosen = getPeerWithHigherDebt();
		alreadyChosen.add(chosen);		
		return chosen;
	}
	
	protected Peer getPeerWithHigherDebt(){
		List<AccountingInfo> accountingList = new ArrayList<AccountingInfo>();
		accountingList.addAll(accountingPlugin.getAccountingList());
		
		List<AccountingInfo> alreadyChosenAccountingList = new ArrayList<AccountingInfo>();
		for(Peer chosen : alreadyChosen)
			alreadyChosenAccountingList.add(new AccountingInfo(chosen, 0));
		
		accountingList.removeAll(alreadyChosenAccountingList);
		
		Collections.sort(accountingList);
		return accountingList.get(accountingList.size()-1).getPeer();
	}

}
