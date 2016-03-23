package me.edufalcao.manager.plugins.peerchooser.nonrepeatable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import me.edufalcao.manager.TimeManager;
import me.edufalcao.manager.model.Peer;

public class RandomPeerChooserPlugin extends TimeBasedPeerChooserPlugin{
	
	private Random random;
	
	public RandomPeerChooserPlugin(int seed) {
		random = new Random(seed);
	}

	@Override
	public Peer choose(List<Peer> peers) {
		updateListAndTime();		
		List<Peer> candidatePeers = new ArrayList<Peer>();
		candidatePeers.addAll(peers);
		candidatePeers.removeAll(alreadyChosen);
		Peer chosen = peers.get(random.nextInt(candidatePeers.size()));
		alreadyChosen.add(chosen);
		
		return chosen;
	}

}
