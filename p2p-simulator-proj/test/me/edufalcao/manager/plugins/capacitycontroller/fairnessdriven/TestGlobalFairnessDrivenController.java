package me.edufalcao.manager.plugins.capacitycontroller.fairnessdriven;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;
import me.edufalcao.manager.TimeManager;
import me.edufalcao.manager.model.Peer;
import me.edufalcao.manager.model.Request;
import me.edufalcao.manager.plugins.accounting.simple.SimpleAccountingPlugin;

import org.junit.Before;
import org.junit.Test;

public class TestGlobalFairnessDrivenController {
	
	private final double ACCEPTABLE_ERROR = 0.000001;
	
	SimpleAccountingPlugin accountingPlugin;
	GlobalFairnessDrivenController fdController;
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
		fdController = new GlobalFairnessDrivenController(peer1, accountingPlugin, deltaC, minimumThreshold, maximumThreshold, maximumCapacityOfPeer);
	}

	@Test
	public void testGetMaxCapacityToSupplyForOneConsumer() {
		peer1.getRequests().add(new Request("id1", peer2, peer1, 10, 40));	//peer1 donates to peer2
		peer1.getRequests().add(new Request("id2", peer1, peer2, 50, 100));	//peer2 donates to peer1		
		
		TimeManager.getInstance().setTime(0);	
		accountingPlugin.add(peer2);
		
		TimeManager.getInstance().setTime(10);	
		accountingPlugin.updateAll();
		assertEquals(maximumCapacityOfPeer,fdController.getMaxCapacityToSupply(peer2), ACCEPTABLE_ERROR);
		
		TimeManager.getInstance().setTime(20);	
		accountingPlugin.updateAll();
		assertEquals(maximumCapacityOfPeer,fdController.getMaxCapacityToSupply(peer2), ACCEPTABLE_ERROR);
		
		TimeManager.getInstance().setTime(30);	
		accountingPlugin.updateAll();
		assertEquals(4.5,fdController.getMaxCapacityToSupply(peer2), ACCEPTABLE_ERROR);
		
		TimeManager.getInstance().setTime(40);	
		accountingPlugin.updateAll();
		assertEquals(4,fdController.getMaxCapacityToSupply(peer2), ACCEPTABLE_ERROR);
		
		TimeManager.getInstance().setTime(50);	
		accountingPlugin.updateAll();
		assertEquals(3.5,fdController.getMaxCapacityToSupply(peer2), ACCEPTABLE_ERROR);
		
		TimeManager.getInstance().setTime(60);	
		accountingPlugin.updateAll();
		assertEquals(3,fdController.getMaxCapacityToSupply(peer2), ACCEPTABLE_ERROR);
		
		TimeManager.getInstance().setTime(70);	
		accountingPlugin.updateAll();
		assertEquals(2.5,fdController.getMaxCapacityToSupply(peer2), ACCEPTABLE_ERROR);
		
		TimeManager.getInstance().setTime(80);	
		accountingPlugin.updateAll();
		assertEquals(2,fdController.getMaxCapacityToSupply(peer2), ACCEPTABLE_ERROR);
		
		TimeManager.getInstance().setTime(90);	
		accountingPlugin.updateAll();
		assertEquals(1.5,fdController.getMaxCapacityToSupply(peer2), ACCEPTABLE_ERROR);
		
		TimeManager.getInstance().setTime(100);	
		accountingPlugin.updateAll();
		assertEquals(2,fdController.getMaxCapacityToSupply(peer2), ACCEPTABLE_ERROR);		
	}
	
	@Test
	public void testGetMaxCapacityToSupplyForOneConsumerWithTwoRequests() {
		peer1.getRequests().add(new Request("id1", peer2, peer1, 10, 40));	//peer1 donates to peer2
		peer1.getRequests().add(new Request("id2", peer1, peer2, 50, 100));	//peer2 donates to peer1
		peer1.getRequests().add(new Request("id3", peer2, peer1, 0, 50));	//peer1 donates to peer2
		
		TimeManager.getInstance().setTime(0);	
		accountingPlugin.add(peer2);
		
		TimeManager.getInstance().setTime(10);	
		accountingPlugin.updateAll();
		assertEquals(maximumCapacityOfPeer,fdController.getMaxCapacityToSupply(peer2), ACCEPTABLE_ERROR);
		
		TimeManager.getInstance().setTime(20);	
		accountingPlugin.updateAll();
		assertEquals(4.5,fdController.getMaxCapacityToSupply(peer2), ACCEPTABLE_ERROR);
		
		TimeManager.getInstance().setTime(30);	
		accountingPlugin.updateAll();
		assertEquals(4,fdController.getMaxCapacityToSupply(peer2), ACCEPTABLE_ERROR);
		
		TimeManager.getInstance().setTime(40);	
		accountingPlugin.updateAll();
		assertEquals(3.5,fdController.getMaxCapacityToSupply(peer2), ACCEPTABLE_ERROR);
		
		TimeManager.getInstance().setTime(50);	
		accountingPlugin.updateAll();
		assertEquals(3,fdController.getMaxCapacityToSupply(peer2), ACCEPTABLE_ERROR);
		
		TimeManager.getInstance().setTime(60);	
		accountingPlugin.updateAll();
		assertEquals(2.5,fdController.getMaxCapacityToSupply(peer2), ACCEPTABLE_ERROR);
		
		TimeManager.getInstance().setTime(70);	
		accountingPlugin.updateAll();
		assertEquals(2,fdController.getMaxCapacityToSupply(peer2), ACCEPTABLE_ERROR);
		
		TimeManager.getInstance().setTime(80);	
		accountingPlugin.updateAll();
		assertEquals(1.5,fdController.getMaxCapacityToSupply(peer2), ACCEPTABLE_ERROR);
		
		TimeManager.getInstance().setTime(90);	
		accountingPlugin.updateAll();
		assertEquals(1,fdController.getMaxCapacityToSupply(peer2), ACCEPTABLE_ERROR);
		
		TimeManager.getInstance().setTime(100);	
		accountingPlugin.updateAll();
		assertEquals(0.5,fdController.getMaxCapacityToSupply(peer2), ACCEPTABLE_ERROR);
		
		TimeManager.getInstance().setTime(110);	
		accountingPlugin.updateAll();
		assertEquals(0,fdController.getMaxCapacityToSupply(peer2), ACCEPTABLE_ERROR);		
		
		TimeManager.getInstance().setTime(120);	
		accountingPlugin.updateAll();
		assertEquals(0,fdController.getMaxCapacityToSupply(peer2), ACCEPTABLE_ERROR);		
	}
	
	@Test
	public void testGetMaxCapacityToSupplyForTwoConsumers() {
		peer1.getRequests().add(new Request("id1", peer2, peer1, 10, 40));	//peer1 donates to peer2
		peer1.getRequests().add(new Request("id2", peer1, peer2, 50, 100));	//peer2 donates to peer1
		
		peer1.getRequests().add(new Request("id3", peer3, peer1, 0, 30));	//peer1 donates to peer3
		peer1.getRequests().add(new Request("id4", peer1, peer3, 25, 25));	//peer3 donates to peer1
		
		TimeManager.getInstance().setTime(0);	
		accountingPlugin.add(peer2);
		accountingPlugin.add(peer3);	
		
		TimeManager.getInstance().setTime(10);	
		accountingPlugin.updateAll();
		assertEquals(maximumCapacityOfPeer,fdController.getMaxCapacityToSupply(peer2), ACCEPTABLE_ERROR);
		assertEquals(maximumCapacityOfPeer,fdController.getMaxCapacityToSupply(peer3), ACCEPTABLE_ERROR);
		
		TimeManager.getInstance().setTime(20);	
		accountingPlugin.updateAll();
		assertEquals(4.5,fdController.getMaxCapacityToSupply(peer2), ACCEPTABLE_ERROR);
		assertEquals(4.5,fdController.getMaxCapacityToSupply(peer3), ACCEPTABLE_ERROR);
		
		TimeManager.getInstance().setTime(30);	
		accountingPlugin.updateAll();
		assertEquals(4,fdController.getMaxCapacityToSupply(peer2), ACCEPTABLE_ERROR);
		assertEquals(4,fdController.getMaxCapacityToSupply(peer3), ACCEPTABLE_ERROR);
		
		TimeManager.getInstance().setTime(40);	
		accountingPlugin.updateAll();
		assertEquals(3.5,fdController.getMaxCapacityToSupply(peer2), ACCEPTABLE_ERROR);
		assertEquals(3.5,fdController.getMaxCapacityToSupply(peer3), ACCEPTABLE_ERROR);
		
		TimeManager.getInstance().setTime(50);	
		accountingPlugin.updateAll();
		assertEquals(3,fdController.getMaxCapacityToSupply(peer2), ACCEPTABLE_ERROR);
		assertEquals(3,fdController.getMaxCapacityToSupply(peer3), ACCEPTABLE_ERROR);
		
		TimeManager.getInstance().setTime(60);	
		accountingPlugin.updateAll();
		assertEquals(2.5,fdController.getMaxCapacityToSupply(peer2), ACCEPTABLE_ERROR);
		assertEquals(2.5,fdController.getMaxCapacityToSupply(peer3), ACCEPTABLE_ERROR);
		
		TimeManager.getInstance().setTime(70);	
		accountingPlugin.updateAll();
		assertEquals(2,fdController.getMaxCapacityToSupply(peer2), ACCEPTABLE_ERROR);
		assertEquals(2,fdController.getMaxCapacityToSupply(peer3), ACCEPTABLE_ERROR);
		
		TimeManager.getInstance().setTime(80);	
		accountingPlugin.updateAll();		
		assertEquals(1.5,fdController.getMaxCapacityToSupply(peer2), ACCEPTABLE_ERROR);
		assertEquals(1.5,fdController.getMaxCapacityToSupply(peer3), ACCEPTABLE_ERROR);
		
		TimeManager.getInstance().setTime(90);	
		accountingPlugin.updateAll();
		assertEquals(1.0,fdController.getMaxCapacityToSupply(peer2), ACCEPTABLE_ERROR);
		assertEquals(1.0,fdController.getMaxCapacityToSupply(peer3), ACCEPTABLE_ERROR);
		
		TimeManager.getInstance().setTime(100);	
		accountingPlugin.updateAll();
		assertEquals(1.5,fdController.getMaxCapacityToSupply(peer2), ACCEPTABLE_ERROR);		
		assertEquals(1.5,fdController.getMaxCapacityToSupply(peer3), ACCEPTABLE_ERROR);		
		
		TimeManager.getInstance().setTime(110);	
		accountingPlugin.updateAll();
		assertEquals(2,fdController.getMaxCapacityToSupply(peer2), ACCEPTABLE_ERROR);		
		assertEquals(2,fdController.getMaxCapacityToSupply(peer3), ACCEPTABLE_ERROR);		
		
		TimeManager.getInstance().setTime(120);
		accountingPlugin.updateAll();
		assertEquals(2.5,fdController.getMaxCapacityToSupply(peer2), ACCEPTABLE_ERROR);		
		assertEquals(2.5,fdController.getMaxCapacityToSupply(peer3), ACCEPTABLE_ERROR);		
	}

	@Test
	public void testUpdateFairness() {
		peer1.getRequests().add(new Request("id1", peer2, peer1, 10, 50));	//peer1 donates to peer2
		peer1.getRequests().add(new Request("id2", peer1, peer2, 20, 100));	//peer2 donates to peer1
		peer1.getRequests().add(new Request("id3", peer1, peer3, 5, 100));	//peer3 donates to peer1
		
		TimeManager.getInstance().setTime(0);
		accountingPlugin.add(peer2);
		accountingPlugin.add(peer3);
		
		TimeManager.getInstance().setTime(5);
		accountingPlugin.updateAll();				
		assertEquals(-1, fdController.getCurrentFairness(null), ACCEPTABLE_ERROR);
		fdController.updateFairness();
		assertEquals(-1, fdController.getCurrentFairness(null), ACCEPTABLE_ERROR);
		
		TimeManager.getInstance().setTime(10);
		accountingPlugin.updateAll();		
		assertEquals(-1, fdController.getCurrentFairness(null), ACCEPTABLE_ERROR);
		fdController.updateFairness();
		assertEquals(-1, fdController.getCurrentFairness(null), ACCEPTABLE_ERROR);
		
		TimeManager.getInstance().setTime(30);
		accountingPlugin.updateAll();		
		assertEquals(-1, fdController.getCurrentFairness(null), ACCEPTABLE_ERROR);
		fdController.updateFairness();
		assertEquals(35.0/20.0, fdController.getCurrentFairness(null), ACCEPTABLE_ERROR);
		
		TimeManager.getInstance().setTime(120);
		accountingPlugin.updateAll();		
		assertEquals(35.0/20.0, fdController.getCurrentFairness(null), ACCEPTABLE_ERROR);
		fdController.updateFairness();
		assertEquals(4, fdController.getCurrentFairness(null), ACCEPTABLE_ERROR);
	}
	
	@Test
	public void testGetCurrentFairness() {
		assertEquals(-1, fdController.getCurrentFairness(peer2), ACCEPTABLE_ERROR);
		assertEquals(-1, fdController.getCurrentFairness(peer3), ACCEPTABLE_ERROR);
	}
	
	@Test
	public void testGetLastFairness() {
		assertEquals(-1, fdController.getLastFairness(peer2), ACCEPTABLE_ERROR);
		assertEquals(-1, fdController.getLastFairness(peer3), ACCEPTABLE_ERROR);
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
