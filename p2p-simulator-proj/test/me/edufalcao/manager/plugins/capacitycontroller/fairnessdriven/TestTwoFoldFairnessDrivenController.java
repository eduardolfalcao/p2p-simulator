package me.edufalcao.manager.plugins.capacitycontroller.fairnessdriven;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;
import me.edufalcao.manager.model.Peer;
import me.edufalcao.manager.model.Request;
import me.edufalcao.manager.plugins.accounting.simple.SimpleAccountingPlugin;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestTwoFoldFairnessDrivenController {

	private final double ACCEPTABLE_ERROR = 0.000001;

	SimpleAccountingPlugin accountingPlugin;
	SimpleAccountingPlugin mockedAccountingPlugin;
	
	TwoFoldFairnessDrivenController twofoldController;
		
	PairwiseFairnessDrivenController pairwiseController;
	PairwiseFairnessDrivenController mockedPairwiseController;
	GlobalFairnessDrivenController globalController;
	GlobalFairnessDrivenController mockedGlobalController;
	
	
	Peer peer1, peer2, peer3;
	
	double deltaC, minimumThreshold, maximumThreshold, maximumCapacityOfPeer;
	
	@Before
	public void setUp() throws Exception {
		peer1 = new Peer("p1");
		accountingPlugin = new SimpleAccountingPlugin(peer1);
		peer2 = new Peer("p2");
		peer3 = new Peer("p3");
		mockedAccountingPlugin = spy(accountingPlugin);		
		
		deltaC = 0.1;
		minimumThreshold = 0.8;
		maximumThreshold = 1;
		maximumCapacityOfPeer = 5;
		pairwiseController = new PairwiseFairnessDrivenController(mockedAccountingPlugin, deltaC, minimumThreshold, maximumThreshold, maximumCapacityOfPeer);
		mockedPairwiseController = spy(pairwiseController);
		globalController = new GlobalFairnessDrivenController(peer1, mockedAccountingPlugin, deltaC, minimumThreshold, maximumThreshold, maximumCapacityOfPeer);
		mockedGlobalController = spy(globalController);
		
		twofoldController = new TwoFoldFairnessDrivenController(mockedPairwiseController, mockedGlobalController);
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
		
		doReturn(time).when(mockedAccountingPlugin).getTime();
		doReturn(time).when(mockedPairwiseController).getTime();
		doReturn(time).when(mockedGlobalController).getTime();
		mockedAccountingPlugin.add(peer2);
		mockedAccountingPlugin.add(peer3);
		
		time = 10;
		doReturn(time).when(mockedAccountingPlugin).getTime();
		doReturn(time).when(mockedPairwiseController).getTime();
		doReturn(time).when(mockedGlobalController).getTime();
		mockedAccountingPlugin.updateAll();
		assertEquals(maximumCapacityOfPeer,twofoldController.getMaxCapacityToSupply(peer2), ACCEPTABLE_ERROR);
		assertEquals(maximumCapacityOfPeer,twofoldController.getMaxCapacityToSupply(peer3), ACCEPTABLE_ERROR);
		
		time = 20;
		doReturn(time).when(mockedAccountingPlugin).getTime();
		doReturn(time).when(mockedPairwiseController).getTime();
		doReturn(time).when(mockedGlobalController).getTime();
		mockedAccountingPlugin.updateAll();
		assertEquals(4.5,twofoldController.getMaxCapacityToSupply(peer2), ACCEPTABLE_ERROR);
		assertEquals(4.5,twofoldController.getMaxCapacityToSupply(peer3), ACCEPTABLE_ERROR);
		
		time = 30;
		doReturn(time).when(mockedAccountingPlugin).getTime();
		doReturn(time).when(mockedPairwiseController).getTime();
		doReturn(time).when(mockedGlobalController).getTime();
		mockedAccountingPlugin.updateAll();
		assertEquals(4,twofoldController.getMaxCapacityToSupply(peer2), ACCEPTABLE_ERROR);
		assertEquals(4,twofoldController.getMaxCapacityToSupply(peer3), ACCEPTABLE_ERROR);
		
		time = 40;
		doReturn(time).when(mockedAccountingPlugin).getTime();
		doReturn(time).when(mockedPairwiseController).getTime();
		doReturn(time).when(mockedGlobalController).getTime();
		mockedAccountingPlugin.updateAll();
		assertEquals(4.5,twofoldController.getMaxCapacityToSupply(peer2), ACCEPTABLE_ERROR);
		assertEquals(3.5,twofoldController.getMaxCapacityToSupply(peer3), ACCEPTABLE_ERROR);
		
		time = 50;
		doReturn(time).when(mockedAccountingPlugin).getTime();
		doReturn(time).when(mockedPairwiseController).getTime();
		doReturn(time).when(mockedGlobalController).getTime();
		mockedAccountingPlugin.updateAll();
		assertEquals(maximumCapacityOfPeer,twofoldController.getMaxCapacityToSupply(peer2), ACCEPTABLE_ERROR);
		assertEquals(3,twofoldController.getMaxCapacityToSupply(peer3), ACCEPTABLE_ERROR);
		
		time = 60;
		doReturn(time).when(mockedAccountingPlugin).getTime();
		doReturn(time).when(mockedPairwiseController).getTime();
		doReturn(time).when(mockedGlobalController).getTime();
		mockedAccountingPlugin.updateAll();
		assertEquals(maximumCapacityOfPeer,twofoldController.getMaxCapacityToSupply(peer2), ACCEPTABLE_ERROR);
		assertEquals(2.5,twofoldController.getMaxCapacityToSupply(peer3), ACCEPTABLE_ERROR);
		
		time = 70;
		doReturn(time).when(mockedAccountingPlugin).getTime();
		doReturn(time).when(mockedPairwiseController).getTime();
		doReturn(time).when(mockedGlobalController).getTime();
		mockedAccountingPlugin.updateAll();
		assertEquals(maximumCapacityOfPeer,twofoldController.getMaxCapacityToSupply(peer2), ACCEPTABLE_ERROR);
		assertEquals(2,twofoldController.getMaxCapacityToSupply(peer3), ACCEPTABLE_ERROR);
		
		time = 80;
		doReturn(time).when(mockedAccountingPlugin).getTime();
		doReturn(time).when(mockedPairwiseController).getTime();
		doReturn(time).when(mockedGlobalController).getTime();
		mockedAccountingPlugin.updateAll();
		assertEquals(maximumCapacityOfPeer,twofoldController.getMaxCapacityToSupply(peer2), ACCEPTABLE_ERROR);
		assertEquals(1.5,twofoldController.getMaxCapacityToSupply(peer3), ACCEPTABLE_ERROR);
		
		time = 90;
		doReturn(time).when(mockedAccountingPlugin).getTime();
		doReturn(time).when(mockedPairwiseController).getTime();
		doReturn(time).when(mockedGlobalController).getTime();
		mockedAccountingPlugin.updateAll();
		assertEquals(maximumCapacityOfPeer,twofoldController.getMaxCapacityToSupply(peer2), ACCEPTABLE_ERROR);
		assertEquals(1,twofoldController.getMaxCapacityToSupply(peer3), ACCEPTABLE_ERROR);
		
		time = 100;
		doReturn(time).when(mockedAccountingPlugin).getTime();
		doReturn(time).when(mockedPairwiseController).getTime();
		doReturn(time).when(mockedGlobalController).getTime();
		mockedAccountingPlugin.updateAll();
		assertEquals(maximumCapacityOfPeer,twofoldController.getMaxCapacityToSupply(peer2), ACCEPTABLE_ERROR);
		assertEquals(0.5,twofoldController.getMaxCapacityToSupply(peer3), ACCEPTABLE_ERROR);
		
		time = 110;
		doReturn(time).when(mockedAccountingPlugin).getTime();
		doReturn(time).when(mockedPairwiseController).getTime();
		doReturn(time).when(mockedGlobalController).getTime();
		mockedAccountingPlugin.updateAll();
		assertEquals(maximumCapacityOfPeer,twofoldController.getMaxCapacityToSupply(peer2), ACCEPTABLE_ERROR);
		assertEquals(0,twofoldController.getMaxCapacityToSupply(peer3), ACCEPTABLE_ERROR);
		
		time = 120;
		doReturn(time).when(mockedAccountingPlugin).getTime();
		doReturn(time).when(mockedPairwiseController).getTime();
		doReturn(time).when(mockedGlobalController).getTime();
		mockedAccountingPlugin.updateAll();
		assertEquals(maximumCapacityOfPeer,twofoldController.getMaxCapacityToSupply(peer2), ACCEPTABLE_ERROR);
		assertEquals(0,twofoldController.getMaxCapacityToSupply(peer3), ACCEPTABLE_ERROR);	
	}

}
