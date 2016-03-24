package me.edufalcao.manager.plugins.peerchooser.nonrepeated;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import me.edufalcao.manager.TimeManager;
import me.edufalcao.manager.model.Peer;

import org.junit.Before;
import org.junit.Test;

public class TestRandomPeerChooserPlugin {

	private static final int SEED = 0;
	
	RandomPeerChooserPlugin randomChooserPlugin;
	TimeManager timeManager;
	List<Peer> peers;
	
	@Before
	public void setUp() throws Exception {
		randomChooserPlugin = new RandomPeerChooserPlugin(SEED);
		TimeManager.getInstance().setTime(0);
		Peer [] peerArray = new Peer[] {new Peer("p1"), new Peer("p2"),  new Peer("p3"),
				 new Peer("p4"),  new Peer("p5"),  new Peer("p6")};
		peers = Arrays.asList(peerArray);
		
	}

	@Test
	public void testChooseEveryone() {			
		TimeManager.getInstance().setTime(1);
		
		Set<Peer> chosen = new HashSet<Peer>();
		
		//first choose 6 times
		chosen.add(randomChooserPlugin.choose(peers));
		chosen.add(randomChooserPlugin.choose(peers));
		chosen.add(randomChooserPlugin.choose(peers));
		chosen.add(randomChooserPlugin.choose(peers));
		chosen.add(randomChooserPlugin.choose(peers));
		chosen.add(randomChooserPlugin.choose(peers));
		
		Peer nullPeer = randomChooserPlugin.choose(peers);	//here must be null
		assertNull(nullPeer);	
		assertEquals(6,chosen.size());
	}
	
	@Test
	public void testChooseInTwoDifferentTimeSteps() {			
		TimeManager.getInstance().setTime(1);
		
		Set<Peer> chosen = new HashSet<Peer>();
		
		//first choose 6 times
		chosen.add(randomChooserPlugin.choose(peers));
		chosen.add(randomChooserPlugin.choose(peers));
		chosen.add(randomChooserPlugin.choose(peers));
		chosen.add(randomChooserPlugin.choose(peers));
		chosen.add(randomChooserPlugin.choose(peers));
		chosen.add(randomChooserPlugin.choose(peers));
		
		Peer nullPeer = randomChooserPlugin.choose(peers);	//here must be null
		assertNull(nullPeer);	
		assertEquals(6,chosen.size());
		
		TimeManager.getInstance().setTime(2);
		
		//first choose 6 times
		chosen.add(randomChooserPlugin.choose(peers));
		chosen.add(randomChooserPlugin.choose(peers));
		chosen.add(randomChooserPlugin.choose(peers));
		chosen.add(randomChooserPlugin.choose(peers));
		chosen.add(randomChooserPlugin.choose(peers));
		chosen.add(randomChooserPlugin.choose(peers));
				
		nullPeer = randomChooserPlugin.choose(peers);	//here must be null
		assertNull(nullPeer);	
		assertEquals(6,chosen.size());		
	}

}
