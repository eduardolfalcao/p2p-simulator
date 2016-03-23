package me.edufalcao.manager.plugins.peerchooser;

import java.util.List;

import me.edufalcao.manager.model.Peer;

public interface PeerChooserPlugin {
	
	public Peer choose(List<Peer> peers);

}
