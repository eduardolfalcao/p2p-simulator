package me.edufalcao.manager.model;

import java.util.ArrayList;
import java.util.List;

public class PeerRepository {
	
	private List<Peer> peers;	
	private static PeerRepository instance;
	
	private PeerRepository(){
		peers = new ArrayList<Peer>();
	}
	
	public static PeerRepository getInstance(){
		if(instance==null)
			instance = new PeerRepository();
		return instance;
	}
	
	public List<Peer> getPeers(){
		return peers;
	}
	
//	public void populate(int numPeers, double capacity){
//		if(peers==null){
//			peers = new ArrayList<Peer>();
//			for(int i = 1; i<=numPeers; i++)
//				peers.add(new Peer("P"+i, capacity));
//		}
//	}
	
	public Peer getPeer(String id){
		for(Peer peer : peers){
			if(peer.getId().equals(id))
				return peer;
		}
		return null;
	}

}
