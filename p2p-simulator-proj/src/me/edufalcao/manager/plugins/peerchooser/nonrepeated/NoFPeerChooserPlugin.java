package me.edufalcao.manager.plugins.peerchooser.nonrepeated;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import me.edufalcao.manager.model.Peer;
import me.edufalcao.manager.plugins.accounting.AccountingInfo;
import me.edufalcao.manager.plugins.accounting.AccountingPlugin;

public class NoFPeerChooserPlugin extends NonRepeatedPeerChooser{

	private AccountingPlugin accountingPlugin;
	
	public NoFPeerChooserPlugin(AccountingPlugin accountingPlugin) {
		super();
		this.accountingPlugin = accountingPlugin;
		alreadyChosen = new ArrayList<Peer>();
	}
	
	@Override
	public Peer choose(List<Peer> peers) {
		updateListAndTime();		
		Peer chosen = getPeerWithHigherBalance();
		if(chosen!=null)
			alreadyChosen.add(chosen);		
		return chosen;
	}
	
	protected Peer getPeerWithHigherBalance(){
		List<AccountingInfo> accountingList = new ArrayList<AccountingInfo>();
		accountingList.addAll(accountingPlugin.getAccountingList());
		
		List<AccountingInfo> alreadyChosenAccountingList = new ArrayList<AccountingInfo>();
		for(Peer chosen : alreadyChosen)
			alreadyChosenAccountingList.add(new AccountingInfo(chosen, 0));
		
		accountingList.removeAll(alreadyChosenAccountingList);
		
		Collections.sort(accountingList);
		return accountingList.size()==0 ? null : accountingList.get(accountingList.size()-1).getPeer();
	}

}
