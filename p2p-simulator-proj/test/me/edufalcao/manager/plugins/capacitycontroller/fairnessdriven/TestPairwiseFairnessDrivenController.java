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

	@Before
	public void setUp() {
		peer1 = new Peer("p1");
		accountingPlugin = new SimpleAccountingPlugin(peer1);
		peer2 = new Peer("p2");
		peer3 = new Peer("p3");
		mockedAccountingPlugin = spy(accountingPlugin);		
		
		double deltaC = 0.05;
		double minimumThreshold = 0.8;
		double maximumThreshold = 1;
		double maximumCapacityOfPeer = 5;
		fdController = new PairwiseFairnessDrivenController(mockedAccountingPlugin, deltaC, minimumThreshold, maximumThreshold, maximumCapacityOfPeer);
		mockedFdController = spy(fdController);
		
	}

	@Test
	public void testGetMaxCapacityToSupply() {
		fail("Not yet implemented");
	}

	@Test
	public void testUpdateFairness() {
		peer1.getRequests().add(new Request("id1", peer2, peer1, 10, 50));	//peer1 donates to peer2
		peer1.getRequests().add(new Request("id2", peer1, peer2, 20, 100));	//peer2 donates to peer1
		
		doReturn(0).when(mockedAccountingPlugin).getTime();
		mockedAccountingPlugin.add(peer2);
		
		doReturn(10).when(mockedAccountingPlugin).getTime();
		mockedAccountingPlugin.updateAll();				
		assertEquals(-1, mockedFdController.getCurrentFairness(), ACCEPTABLE_ERROR);
		mockedFdController.updateFairness(peer2);
		assertEquals(-1, mockedFdController.getCurrentFairness(), ACCEPTABLE_ERROR);
		
		doReturn(20).when(mockedAccountingPlugin).getTime();
		mockedAccountingPlugin.updateAll();		
		assertEquals(-1, mockedFdController.getCurrentFairness(), ACCEPTABLE_ERROR);
		mockedFdController.updateFairness(peer2);
		assertEquals(0, mockedFdController.getCurrentFairness(), ACCEPTABLE_ERROR);
		
		doReturn(30).when(mockedAccountingPlugin).getTime();
		mockedAccountingPlugin.updateAll();		
		assertEquals(0, mockedFdController.getCurrentFairness(), ACCEPTABLE_ERROR);
		mockedFdController.updateFairness(peer2);
		assertEquals(0.5, mockedFdController.getCurrentFairness(), ACCEPTABLE_ERROR);
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
