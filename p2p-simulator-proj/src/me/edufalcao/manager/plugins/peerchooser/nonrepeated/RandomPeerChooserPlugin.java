package me.edufalcao.manager.plugins.peerchooser.nonrepeated;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import me.edufalcao.manager.TimeManager;
import me.edufalcao.manager.model.Peer;

public class RandomPeerChooserPlugin extends NonRepeatedPeerChooser{
	
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
		if(candidatePeers.size()==0)
			return null;
		Peer chosen = candidatePeers.get(random.nextInt(candidatePeers.size()));
		alreadyChosen.add(chosen);		
		return chosen;
	}

}
