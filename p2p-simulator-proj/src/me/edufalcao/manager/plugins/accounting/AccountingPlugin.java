package me.edufalcao.manager.plugins.accounting;

import java.util.List;

import me.edufalcao.manager.model.Peer;
import me.edufalcao.manager.model.events.request.RequestEvent;
import me.edufalcao.manager.plugins.accounting.simple.AccountingInfo;

public interface AccountingPlugin {
	
	public void update();
	public void update(Peer peer);
	public List<AccountingInfo> getAccountingList();
	public AccountingInfo getAccountingInfo(Peer peer);

}
