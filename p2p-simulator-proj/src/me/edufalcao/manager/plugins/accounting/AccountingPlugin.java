package me.edufalcao.manager.plugins.accounting;

import java.util.List;

import me.edufalcao.manager.model.Peer;

public interface AccountingPlugin {
	
	public void updateAll();
	public void update(Peer peer);
	public void add(Peer peer);
	public List<AccountingInfo> getAccountingList();
	public AccountingInfo getAccountingInfo(Peer peer);

}
