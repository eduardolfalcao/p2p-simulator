package me.edufalcao.manager.plugins.capacitycontroller.fairnessdriven;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;
import me.edufalcao.manager.model.Peer;
import me.edufalcao.manager.model.Request;
import me.edufalcao.manager.plugins.accounting.simple.SimpleAccountingPlugin;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

public class TestPairwiseFairnessDrivenController {
	
	private final double ACCEPTABLE_ERROR = 0.000001;

	SimpleAccountingPlugin accountingPlugin;
	SimpleAccountingPlugin mockedAccountingPlugin;
	PairwiseFairnessDrivenController fdController;
	PairwiseFairnessDrivenController mockedFdController;
	Peer peer1, peer2, peer3;
	
	double deltaC, minimumThreshold, maximumThreshold, maximumCapacityOfPeer;

	@Before
	public void setUp() {
		peer1 = new Peer("p1");
		accountingPlugin = new SimpleAccountingPlugin(peer1);
		peer2 = new Peer("p2");
		peer3 = new Peer("p3");
		mockedAccountingPlugin = spy(accountingPlugin);		
		
		deltaC = 0.1;
		minimumThreshold = 0.8;
		maximumThreshold = 1;
		maximumCapacityOfPeer = 5;
		fdController = new PairwiseFairnessDrivenController(mockedAccountingPlugin, deltaC, minimumThreshold, maximumThreshold, maximumCapacityOfPeer);
		mockedFdController = spy(fdController);		
	}

	@Test
	public void testGetMaxCapacityToSupplyForOneConsumer() {
		peer1.getRequests().add(new Request("id1", peer2, peer1, 10, 40));	//peer1 donates to peer2
		peer1.getRequests().add(new Request("id2", peer1, peer2, 50, 100));	//peer2 donates to peer1		
		
		doReturn(0).when(mockedAccountingPlugin).getTime();
		doReturn(0).when(mockedFdController).getTime();
		mockedAccountingPlugin.add(peer2);
		
		doReturn(10).when(mockedAccountingPlugin).getTime();
		doReturn(10).when(mockedFdController).getTime();
		mockedAccountingPlugin.updateAll();
		assertEquals(maximumCapacityOfPeer,mockedFdController.getMaxCapacityToSupply(peer2), ACCEPTABLE_ERROR);
		
		doReturn(20).when(mockedAccountingPlugin).getTime();
		doReturn(20).when(mockedFdController).getTime();
		mockedAccountingPlugin.updateAll();
		assertEquals(maximumCapacityOfPeer,mockedFdController.getMaxCapacityToSupply(peer2), ACCEPTABLE_ERROR);
		
		doReturn(30).when(mockedAccountingPlugin).getTime();
		doReturn(30).when(mockedFdController).getTime();
		mockedAccountingPlugin.updateAll();
		assertEquals(4.5,mockedFdController.getMaxCapacityToSupply(peer2), ACCEPTABLE_ERROR);
		
		doReturn(40).when(mockedAccountingPlugin).getTime();
		doReturn(40).when(mockedFdController).getTime();
		mockedAccountingPlugin.updateAll();
		assertEquals(4,mockedFdController.getMaxCapacityToSupply(peer2), ACCEPTABLE_ERROR);
		
		doReturn(50).when(mockedAccountingPlugin).getTime();
		doReturn(50).when(mockedFdController).getTime();
		mockedAccountingPlugin.updateAll();
		assertEquals(3.5,mockedFdController.getMaxCapacityToSupply(peer2), ACCEPTABLE_ERROR);
		
		doReturn(60).when(mockedAccountingPlugin).getTime();
		doReturn(60).when(mockedFdController).getTime();
		mockedAccountingPlugin.updateAll();
		assertEquals(3,mockedFdController.getMaxCapacityToSupply(peer2), ACCEPTABLE_ERROR);
		
		doReturn(70).when(mockedAccountingPlugin).getTime();
		doReturn(70).when(mockedFdController).getTime();
		mockedAccountingPlugin.updateAll();
		assertEquals(2.5,mockedFdController.getMaxCapacityToSupply(peer2), ACCEPTABLE_ERROR);
		
		doReturn(80).when(mockedAccountingPlugin).getTime();
		doReturn(80).when(mockedFdController).getTime();
		mockedAccountingPlugin.updateAll();
		assertEquals(2,mockedFdController.getMaxCapacityToSupply(peer2), ACCEPTABLE_ERROR);
		
		doReturn(90).when(mockedAccountingPlugin).getTime();
		doReturn(90).when(mockedFdController).getTime();
		mockedAccountingPlugin.updateAll();
		assertEquals(1.5,mockedFdController.getMaxCapacityToSupply(peer2), ACCEPTABLE_ERROR);
		
		doReturn(100).when(mockedAccountingPlugin).getTime();
		doReturn(100).when(mockedFdController).getTime();
		mockedAccountingPlugin.updateAll();
		assertEquals(2,mockedFdController.getMaxCapacityToSupply(peer2), ACCEPTABLE_ERROR);		
	}
	
	@Test
	public void testGetMaxCapacityToSupplyForOneConsumerWithTwoRequests() {
		peer1.getRequests().add(new Request("id1", peer2, peer1, 10, 40));	//peer1 donates to peer2
		peer1.getRequests().add(new Request("id2", peer1, peer2, 50, 100));	//peer2 donates to peer1
		peer1.getRequests().add(new Request("id3", peer2, peer1, 0, 50));	//peer1 donates to peer2
		
		doReturn(0).when(mockedAccountingPlugin).getTime();
		doReturn(0).when(mockedFdController).getTime();
		mockedAccountingPlugin.add(peer2);
		
		doReturn(10).when(mockedAccountingPlugin).getTime();
		doReturn(10).when(mockedFdController).getTime();
		mockedAccountingPlugin.updateAll();
		assertEquals(maximumCapacityOfPeer,mockedFdController.getMaxCapacityToSupply(peer2), ACCEPTABLE_ERROR);
		
		doReturn(20).when(mockedAccountingPlugin).getTime();
		doReturn(20).when(mockedFdController).getTime();
		mockedAccountingPlugin.updateAll();
		assertEquals(4.5,mockedFdController.getMaxCapacityToSupply(peer2), ACCEPTABLE_ERROR);
		
		doReturn(30).when(mockedAccountingPlugin).getTime();
		doReturn(30).when(mockedFdController).getTime();
		mockedAccountingPlugin.updateAll();
		assertEquals(4,mockedFdController.getMaxCapacityToSupply(peer2), ACCEPTABLE_ERROR);
		
		doReturn(40).when(mockedAccountingPlugin).getTime();
		doReturn(40).when(mockedFdController).getTime();
		mockedAccountingPlugin.updateAll();
		assertEquals(3.5,mockedFdController.getMaxCapacityToSupply(peer2), ACCEPTABLE_ERROR);
		
		doReturn(50).when(mockedAccountingPlugin).getTime();
		doReturn(50).when(mockedFdController).getTime();
		mockedAccountingPlugin.updateAll();
		assertEquals(3,mockedFdController.getMaxCapacityToSupply(peer2), ACCEPTABLE_ERROR);
		
		doReturn(60).when(mockedAccountingPlugin).getTime();
		doReturn(60).when(mockedFdController).getTime();
		mockedAccountingPlugin.updateAll();
		assertEquals(2.5,mockedFdController.getMaxCapacityToSupply(peer2), ACCEPTABLE_ERROR);
		
		doReturn(70).when(mockedAccountingPlugin).getTime();
		doReturn(70).when(mockedFdController).getTime();
		mockedAccountingPlugin.updateAll();
		assertEquals(2,mockedFdController.getMaxCapacityToSupply(peer2), ACCEPTABLE_ERROR);
		
		doReturn(80).when(mockedAccountingPlugin).getTime();
		doReturn(80).when(mockedFdController).getTime();
		mockedAccountingPlugin.updateAll();
		assertEquals(1.5,mockedFdController.getMaxCapacityToSupply(peer2), ACCEPTABLE_ERROR);
		
		doReturn(90).when(mockedAccountingPlugin).getTime();
		doReturn(90).when(mockedFdController).getTime();
		mockedAccountingPlugin.updateAll();
		assertEquals(1,mockedFdController.getMaxCapacityToSupply(peer2), ACCEPTABLE_ERROR);
		
		doReturn(100).when(mockedAccountingPlugin).getTime();
		doReturn(100).when(mockedFdController).getTime();
		mockedAccountingPlugin.updateAll();
		assertEquals(0.5,mockedFdController.getMaxCapacityToSupply(peer2), ACCEPTABLE_ERROR);
		
		doReturn(110).when(mockedAccountingPlugin).getTime();
		doReturn(110).when(mockedFdController).getTime();
		mockedAccountingPlugin.updateAll();
		assertEquals(0,mockedFdController.getMaxCapacityToSupply(peer2), ACCEPTABLE_ERROR);		
		
		doReturn(120).when(mockedAccountingPlugin).getTime();
		doReturn(120).when(mockedFdController).getTime();
		mockedAccountingPlugin.updateAll();
		assertEquals(0,mockedFdController.getMaxCapacityToSupply(peer2), ACCEPTABLE_ERROR);		
	}
	
	@Test
	public void testGetMaxCapacityToSupplyForTwoConsumers() {
		peer1.getRequests().add(new Request("id1", peer2, peer1, 10, 40));	//peer1 donates to peer2
		peer1.getRequests().add(new Request("id2", peer1, peer2, 50, 100));	//peer2 donates to peer1
		
		peer1.getRequests().add(new Request("id3", peer3, peer1, 50, 100));	//peer1 donates to peer3
		peer1.getRequests().add(new Request("id4", peer1, peer3, 10, 40));	//peer3 donates to peer1
		
		doReturn(0).when(mockedAccountingPlugin).getTime();
		doReturn(0).when(mockedFdController).getTime();
		mockedAccountingPlugin.add(peer2);
		mockedAccountingPlugin.add(peer3);	
		
		doReturn(10).when(mockedAccountingPlugin).getTime();
		doReturn(10).when(mockedFdController).getTime();
		mockedAccountingPlugin.updateAll();
		assertEquals(maximumCapacityOfPeer,mockedFdController.getMaxCapacityToSupply(peer2), ACCEPTABLE_ERROR);
		assertEquals(maximumCapacityOfPeer,mockedFdController.getMaxCapacityToSupply(peer3), ACCEPTABLE_ERROR);
		
		doReturn(20).when(mockedAccountingPlugin).getTime();
		doReturn(20).when(mockedFdController).getTime();
		mockedAccountingPlugin.updateAll();
		assertEquals(maximumCapacityOfPeer,mockedFdController.getMaxCapacityToSupply(peer2), ACCEPTABLE_ERROR);
		assertEquals(maximumCapacityOfPeer,mockedFdController.getMaxCapacityToSupply(peer3), ACCEPTABLE_ERROR);
		
		doReturn(30).when(mockedAccountingPlugin).getTime();
		doReturn(30).when(mockedFdController).getTime();
		mockedAccountingPlugin.updateAll();
		assertEquals(4.5,mockedFdController.getMaxCapacityToSupply(peer2), ACCEPTABLE_ERROR);
		assertEquals(5,mockedFdController.getMaxCapacityToSupply(peer3), ACCEPTABLE_ERROR);
		
		doReturn(40).when(mockedAccountingPlugin).getTime();
		doReturn(40).when(mockedFdController).getTime();
		mockedAccountingPlugin.updateAll();
		assertEquals(4,mockedFdController.getMaxCapacityToSupply(peer2), ACCEPTABLE_ERROR);
		assertEquals(5,mockedFdController.getMaxCapacityToSupply(peer3), ACCEPTABLE_ERROR);
		
		doReturn(50).when(mockedAccountingPlugin).getTime();
		doReturn(50).when(mockedFdController).getTime();
		mockedAccountingPlugin.updateAll();
		assertEquals(3.5,mockedFdController.getMaxCapacityToSupply(peer2), ACCEPTABLE_ERROR);
		assertEquals(5,mockedFdController.getMaxCapacityToSupply(peer3), ACCEPTABLE_ERROR);
		
		doReturn(60).when(mockedAccountingPlugin).getTime();
		doReturn(60).when(mockedFdController).getTime();
		mockedAccountingPlugin.updateAll();
		assertEquals(3,mockedFdController.getMaxCapacityToSupply(peer2), ACCEPTABLE_ERROR);
		assertEquals(5,mockedFdController.getMaxCapacityToSupply(peer3), ACCEPTABLE_ERROR);
		
		doReturn(70).when(mockedAccountingPlugin).getTime();
		doReturn(70).when(mockedFdController).getTime();
		mockedAccountingPlugin.updateAll();
		assertEquals(2.5,mockedFdController.getMaxCapacityToSupply(peer2), ACCEPTABLE_ERROR);
		assertEquals(5,mockedFdController.getMaxCapacityToSupply(peer3), ACCEPTABLE_ERROR);
		
		doReturn(80).when(mockedAccountingPlugin).getTime();
		doReturn(80).when(mockedFdController).getTime();
		mockedAccountingPlugin.updateAll();		
		assertEquals(2,mockedFdController.getMaxCapacityToSupply(peer2), ACCEPTABLE_ERROR);
		assertEquals(5,mockedFdController.getMaxCapacityToSupply(peer3), ACCEPTABLE_ERROR);
		
		doReturn(90).when(mockedAccountingPlugin).getTime();
		doReturn(90).when(mockedFdController).getTime();
		mockedAccountingPlugin.updateAll();
		assertEquals(1.5,mockedFdController.getMaxCapacityToSupply(peer2), ACCEPTABLE_ERROR);
		assertEquals(4.5,mockedFdController.getMaxCapacityToSupply(peer3), ACCEPTABLE_ERROR);
		
		doReturn(100).when(mockedAccountingPlugin).getTime();
		doReturn(100).when(mockedFdController).getTime();
		mockedAccountingPlugin.updateAll();
		assertEquals(2,mockedFdController.getMaxCapacityToSupply(peer2), ACCEPTABLE_ERROR);		
		assertEquals(5,mockedFdController.getMaxCapacityToSupply(peer3), ACCEPTABLE_ERROR);		
		
		doReturn(110).when(mockedAccountingPlugin).getTime();
		doReturn(110).when(mockedFdController).getTime();
		mockedAccountingPlugin.updateAll();
		assertEquals(2.5,mockedFdController.getMaxCapacityToSupply(peer2), ACCEPTABLE_ERROR);		
		assertEquals(4.5,mockedFdController.getMaxCapacityToSupply(peer3), ACCEPTABLE_ERROR);		
		
		doReturn(120).when(mockedAccountingPlugin).getTime();
		doReturn(120).when(mockedFdController).getTime();
		mockedAccountingPlugin.updateAll();
		assertEquals(3,mockedFdController.getMaxCapacityToSupply(peer2), ACCEPTABLE_ERROR);		
		assertEquals(4,mockedFdController.getMaxCapacityToSupply(peer3), ACCEPTABLE_ERROR);		
	}
	


	@Test
	public void testUpdateFairness() {
		peer1.getRequests().add(new Request("id1", peer2, peer1, 10, 50));	//peer1 donates to peer2
		peer1.getRequests().add(new Request("id2", peer1, peer2, 20, 100));	//peer2 donates to peer1
		
		doReturn(0).when(mockedAccountingPlugin).getTime();
		mockedAccountingPlugin.add(peer2);
		
		doReturn(10).when(mockedAccountingPlugin).getTime();
		mockedAccountingPlugin.updateAll();				
		assertEquals(-1, mockedFdController.getCurrentFairness(peer2), ACCEPTABLE_ERROR);
		mockedFdController.getControllers().put(peer2, new HillClimbingAlgorithm(deltaC, minimumThreshold, maximumThreshold, maximumCapacityOfPeer));
		mockedFdController.updateFairness(peer2);
		assertEquals(-1, mockedFdController.getCurrentFairness(peer2), ACCEPTABLE_ERROR);
		
		doReturn(20).when(mockedAccountingPlugin).getTime();
		mockedAccountingPlugin.updateAll();		
		assertEquals(-1, mockedFdController.getCurrentFairness(peer2), ACCEPTABLE_ERROR);
		mockedFdController.updateFairness(peer2);
		assertEquals(0, mockedFdController.getCurrentFairness(peer2), ACCEPTABLE_ERROR);
		
		doReturn(30).when(mockedAccountingPlugin).getTime();
		mockedAccountingPlugin.updateAll();		
		assertEquals(0, mockedFdController.getCurrentFairness(peer2), ACCEPTABLE_ERROR);
		mockedFdController.updateFairness(peer2);
		assertEquals(0.5, mockedFdController.getCurrentFairness(peer2), ACCEPTABLE_ERROR);
	}

	@Test
	public void testGetFairness() {
		assertEquals(1, fdController.getFairness(1, 1), ACCEPTABLE_ERROR);
		assertEquals(0, fdController.getFairness(0, 1), ACCEPTABLE_ERROR);
		assertEquals(-1, fdController.getFairness(1, 0), ACCEPTABLE_ERROR);
		assertEquals(-1, fdController.getFairness(0, 0), ACCEPTABLE_ERROR);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testGetFairnessWithNegativeConsumption() {
		assertEquals(-1, fdController.getFairness(-1, 0), ACCEPTABLE_ERROR);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testGetFairnessWithNegativeDonation() {
		assertEquals(-1, fdController.getFairness(0, -1), ACCEPTABLE_ERROR);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testGetFairnessWithNegativeValues() {
		assertEquals(-1, fdController.getFairness(-1, -1), ACCEPTABLE_ERROR);
	}

}
