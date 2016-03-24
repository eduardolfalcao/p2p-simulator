package me.edufalcao.manager.plugins.capacitycontroller.fairnessdriven;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;
import me.edufalcao.manager.TimeManager;
import me.edufalcao.manager.model.Peer;
import me.edufalcao.manager.model.Request;
import me.edufalcao.manager.plugins.accounting.simple.SimpleAccountingPlugin;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestTwoFoldFairnessDrivenController {

	private final double ACCEPTABLE_ERROR = 0.000001;

	SimpleAccountingPlugin accountingPlugin;	
	TwoFoldFairnessDrivenController twofoldController;		
	PairwiseFairnessDrivenController pairwiseController;
	GlobalFairnessDrivenController globalController;

	
	
	Peer peer1, peer2, peer3;
	
	double deltaC, minimumThreshold, maximumThreshold, maximumCapacityOfPeer;
	
	@Before
	public void setUp() throws Exception {
		peer1 = new Peer("p1");
		accountingPlugin = new SimpleAccountingPlugin(peer1);
		peer2 = new Peer("p2");
		peer3 = new Peer("p3");	
		
		deltaC = 0.1;
		minimumThreshold = 0.8;
		maximumThreshold = 1;
		maximumCapacityOfPeer = 5;
		pairwiseController = new PairwiseFairnessDrivenController(accountingPlugin, deltaC, minimumThreshold, maximumThreshold, maximumCapacityOfPeer);
		globalController = new GlobalFairnessDrivenController(peer1, accountingPlugin, deltaC, minimumThreshold, maximumThreshold, maximumCapacityOfPeer);
		
		twofoldController = new TwoFoldFairnessDrivenController(pairwiseController, globalController);
	}

	@Test
	public void testGetMaxCapacityToSupply() {
		peer1.getRequests().add(new Request("id1", peer2, peer1, 40, 50));	//peer1 donates to peer2
		peer1.getRequests().add(new Request("id2", peer1, peer2, 20, 50));	//peer2 donates to peer1
		peer1.getRequests().add(new Request("id3", peer1, peer2, 40, 40));	//peer2 donates to peer1
		peer1.getRequests().add(new Request("id4", peer1, peer2, 100, 20));	//peer2 donates to peer1
		
		peer1.getRequests().add(new Request("id5", peer3, peer1, 0, 90));	//peer1 donates to peer3
		peer1.getRequests().add(new Request("id6", peer1, peer3, 10, 30));	//peer3 donates to peer1
		
		int time = 0;
		
		TimeManager.getInstance().setTime(time);
		accountingPlugin.add(peer2);
		accountingPlugin.add(peer3);
		
		time = 10;
		TimeManager.getInstance().setTime(time);
		accountingPlugin.updateAll();
		assertEquals(maximumCapacityOfPeer,twofoldController.getMaxCapacityToSupply(peer2), ACCEPTABLE_ERROR);
		assertEquals(maximumCapacityOfPeer,twofoldController.getMaxCapacityToSupply(peer3), ACCEPTABLE_ERROR);
		
		time = 20;
		TimeManager.getInstance().setTime(time);
		accountingPlugin.updateAll();
		assertEquals(4.5,twofoldController.getMaxCapacityToSupply(peer2), ACCEPTABLE_ERROR);
		assertEquals(4.5,twofoldController.getMaxCapacityToSupply(peer3), ACCEPTABLE_ERROR);
		
		time = 30;
		TimeManager.getInstance().setTime(time);
		accountingPlugin.updateAll();
		assertEquals(4,twofoldController.getMaxCapacityToSupply(peer2), ACCEPTABLE_ERROR);
		assertEquals(4,twofoldController.getMaxCapacityToSupply(peer3), ACCEPTABLE_ERROR);
		
		time = 40;
		TimeManager.getInstance().setTime(time);
		accountingPlugin.updateAll();
		assertEquals(4.5,twofoldController.getMaxCapacityToSupply(peer2), ACCEPTABLE_ERROR);
		assertEquals(3.5,twofoldController.getMaxCapacityToSupply(peer3), ACCEPTABLE_ERROR);
		
		time = 50;
		TimeManager.getInstance().setTime(time);
		accountingPlugin.updateAll();
		assertEquals(maximumCapacityOfPeer,twofoldController.getMaxCapacityToSupply(peer2), ACCEPTABLE_ERROR);
		assertEquals(3,twofoldController.getMaxCapacityToSupply(peer3), ACCEPTABLE_ERROR);
		
		time = 60;
		TimeManager.getInstance().setTime(time);
		accountingPlugin.updateAll();
		assertEquals(maximumCapacityOfPeer,twofoldController.getMaxCapacityToSupply(peer2), ACCEPTABLE_ERROR);
		assertEquals(2.5,twofoldController.getMaxCapacityToSupply(peer3), ACCEPTABLE_ERROR);
		
		time = 70;
		TimeManager.getInstance().setTime(time);
		accountingPlugin.updateAll();
		assertEquals(maximumCapacityOfPeer,twofoldController.getMaxCapacityToSupply(peer2), ACCEPTABLE_ERROR);
		assertEquals(2,twofoldController.getMaxCapacityToSupply(peer3), ACCEPTABLE_ERROR);
		
		time = 80;
		TimeManager.getInstance().setTime(time);
		accountingPlugin.updateAll();
		assertEquals(maximumCapacityOfPeer,twofoldController.getMaxCapacityToSupply(peer2), ACCEPTABLE_ERROR);
		assertEquals(1.5,twofoldController.getMaxCapacityToSupply(peer3), ACCEPTABLE_ERROR);
		
		time = 90;
		TimeManager.getInstance().setTime(time);
		accountingPlugin.updateAll();
		assertEquals(maximumCapacityOfPeer,twofoldController.getMaxCapacityToSupply(peer2), ACCEPTABLE_ERROR);
		assertEquals(1,twofoldController.getMaxCapacityToSupply(peer3), ACCEPTABLE_ERROR);
		
		time = 100;
		TimeManager.getInstance().setTime(time);
		accountingPlugin.updateAll();
		assertEquals(maximumCapacityOfPeer,twofoldController.getMaxCapacityToSupply(peer2), ACCEPTABLE_ERROR);
		assertEquals(0.5,twofoldController.getMaxCapacityToSupply(peer3), ACCEPTABLE_ERROR);
		
		time = 110;
		TimeManager.getInstance().setTime(time);
		accountingPlugin.updateAll();
		assertEquals(maximumCapacityOfPeer,twofoldController.getMaxCapacityToSupply(peer2), ACCEPTABLE_ERROR);
		assertEquals(0,twofoldController.getMaxCapacityToSupply(peer3), ACCEPTABLE_ERROR);
		
		time = 120;
		TimeManager.getInstance().setTime(time);
		accountingPlugin.updateAll();
		assertEquals(maximumCapacityOfPeer,twofoldController.getMaxCapacityToSupply(peer2), ACCEPTABLE_ERROR);
		assertEquals(0,twofoldController.getMaxCapacityToSupply(peer3), ACCEPTABLE_ERROR);	
	}

}
