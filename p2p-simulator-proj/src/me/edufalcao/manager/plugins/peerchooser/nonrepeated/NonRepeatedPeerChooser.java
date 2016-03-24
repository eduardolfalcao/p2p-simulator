package me.edufalcao.manager.plugins.peerchooser.nonrepeated;

import java.util.ArrayList;
import java.util.List;

import me.edufalcao.manager.TimeManager;
import me.edufalcao.manager.model.Peer;
import me.edufalcao.manager.plugins.peerchooser.PeerChooserPlugin;

public abstract class NonRepeatedPeerChooser implements PeerChooserPlugin{
	
	private int lastUpdated;
	protected List<Peer> alreadyChosen;
	
	public NonRepeatedPeerChooser() {
		alreadyChosen = new ArrayList<Peer>();
		lastUpdated = 0;
	}
	
	protected void updateListAndTime(){
		if(TimeManager.getInstance().getTime()!=lastUpdated){
			alreadyChosen.clear();		
			lastUpdated = TimeManager.getInstance().getTime();
		}
	}

}
