package me.edufalcao.manager.plugins.peerchooser.nonrepeated;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import me.edufalcao.manager.TimeManager;
import me.edufalcao.manager.model.Peer;
import me.edufalcao.manager.model.Request;
import me.edufalcao.manager.plugins.accounting.simple.SimpleAccountingPlugin;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestNetworkOfFavorsPeerChooserPlugin {
	
	SimpleAccountingPlugin accountingPlugin;
	NetworkOfFavorsPeerChooserPlugin nofChooserPlugin;
	TimeManager timeManager;
	Peer peer1, peer2, peer3, peer4, peer5, peer6, peer7;
	List<Peer> peers;
	Peer [] peerArray;

	@Before
	public void setUp() throws Exception {
		peer1 = new Peer("p1");
		accountingPlugin = new SimpleAccountingPlugin(peer1);
		peer2 = new Peer("p2");
		peer3 = new Peer("p3");
		peer4 = new Peer("p4");
		peer5 = new Peer("p5");
		peer6 = new Peer("p6");
		peer7 = new Peer("p7");				
		nofChooserPlugin = new NetworkOfFavorsPeerChooserPlugin(accountingPlugin);
		peerArray = new Peer[] {peer2, peer3, peer4, peer5, peer6, peer7};
		peers = Arrays.asList(peerArray);
		
		peer1.getRequests().add(new Request("id1", peer5, peer1, 0, 100));	//peer1 donates to peer5
		peer1.getRequests().add(new Request("id2", peer1, peer5, 100, 200));//peer5 donates to peer1
		peer1.getRequests().add(new Request("id3", peer6, peer1, 0, 150));	//peer1 donates to peer6
		peer1.getRequests().add(new Request("id4", peer1, peer6, 150, 200));//peer6 donates to peer1
		peer1.getRequests().add(new Request("id5", peer2, peer1, 0, 90));	//peer1 donates to peer2
		peer1.getRequests().add(new Request("id6", peer1, peer2, 90, 100));	//peer2 donates to peer1
		//peer4 didnt interact with peer1
		peer1.getRequests().add(new Request("id7", peer7, peer1, 0, 100));	//peer1 donates to peer7
		peer1.getRequests().add(new Request("id8", peer3, peer1, 0, 200));	//peer1 donates to peer3
		
		TimeManager.getInstance().setTime(0);				
		for(Peer p : peerArray)
			accountingPlugin.add(p);
		TimeManager.getInstance().setTime(1000);
		accountingPlugin.updateAll();		
	}

	@Test
	public void testGetPeerWithHigherDebt() {
		Peer peerWithHigherBalance = nofChooserPlugin.getPeerWithHigherBalance();
		assertEquals(peer5, peerWithHigherBalance);		
	}
	
	@Test
	public void testChooseEveryone() {					
		Set<Peer> chosenPeers = new HashSet<Peer>();
		
		//first choose 6 times
		Peer chosen = nofChooserPlugin.choose(peers);
		assertEquals(peer5, chosen);
		chosenPeers.add(chosen);
		
		chosen = nofChooserPlugin.choose(peers);
		assertEquals(peer6, chosen);
		chosenPeers.add(chosen);
		
		chosen = nofChooserPlugin.choose(peers);
		assertEquals(peer2, chosen);
		chosenPeers.add(chosen);
		
		chosenPeers.add(nofChooserPlugin.choose(peers));
		chosenPeers.add(nofChooserPlugin.choose(peers));
		chosenPeers.add(nofChooserPlugin.choose(peers));
		
		assertEquals(6,chosenPeers.size());
		
		Peer nullPeer = nofChooserPlugin.choose(peers);	//here must be null
		assertNull(nullPeer);		
	}
	
	@Test
	public void testChooseInTwoDifferentTimeSteps() {	
		Set<Peer> chosenPeers = new HashSet<Peer>();
		
		//first choose 6 times
		Peer chosen = nofChooserPlugin.choose(peers);
		assertEquals(peer5, chosen);
		chosenPeers.add(chosen);
		
		chosen = nofChooserPlugin.choose(peers);
		assertEquals(peer6, chosen);
		chosenPeers.add(chosen);
		
		chosen = nofChooserPlugin.choose(peers);
		assertEquals(peer2, chosen);
		chosenPeers.add(chosen);
		
		chosenPeers.add(nofChooserPlugin.choose(peers));
		chosenPeers.add(nofChooserPlugin.choose(peers));
		chosenPeers.add(nofChooserPlugin.choose(peers));
		
		assertEquals(6,chosenPeers.size());
		
		Peer nullPeer = nofChooserPlugin.choose(peers);	//here must be null
		assertNull(nullPeer);		
		
		//consider that the time changed, then the peers can be choosen once more
		TimeManager.getInstance().setTime(1500);
		accountingPlugin.updateAll();	
		
		chosen = nofChooserPlugin.choose(peers);
		assertEquals(peer5, chosen);
		chosenPeers.add(chosen);
		
		chosen = nofChooserPlugin.choose(peers);
		assertEquals(peer6, chosen);
		chosenPeers.add(chosen);
		
		chosen = nofChooserPlugin.choose(peers);
		assertEquals(peer2, chosen);
		chosenPeers.add(chosen);
		
		chosenPeers.add(nofChooserPlugin.choose(peers));
		chosenPeers.add(nofChooserPlugin.choose(peers));
		chosenPeers.add(nofChooserPlugin.choose(peers));
		
		assertEquals(6,chosenPeers.size());
		
		nullPeer = nofChooserPlugin.choose(peers);	//here must be null
		assertNull(nullPeer);		
	}

}
