package me.edufalcao.manager.plugins.accounting.simple;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import me.edufalcao.manager.model.Peer;
import me.edufalcao.manager.model.Request;
import me.edufalcao.manager.plugins.accounting.AccountingInfo;

import org.junit.Before;
import org.junit.Test;

public class TestSimpleAccountingPlugin {
	
	private final double ACCEPTABLE_ERROR = 0.000001;
	
	SimpleAccountingPlugin accountingPlugin;
	SimpleAccountingPlugin mockedAccountingPlugin;
	Peer peer1, peer2, peer3, peer4;

	@Before
	public void setUp() throws Exception {
		peer1 = new Peer("p1");
		accountingPlugin = new SimpleAccountingPlugin(peer1);
		peer2 = new Peer("p2");
		peer3 = new Peer("p3");
		mockedAccountingPlugin = spy(accountingPlugin);
	}

	@Test
	public void testUpdateAll() {		
		
		peer1.getRequests().add(new Request("id1", peer2, peer1, 10, 50));	//peer1 donates to peer2
		peer1.getRequests().add(new Request("id2", peer2, peer1, 30, 100));	//peer1 donates to peer2
		peer1.getRequests().add(new Request("id5", peer1, peer2, 50, 50));	//peer2 donates to peer1
		peer1.getRequests().add(new Request("id3", peer3, peer1, 0, 150));	//peer1 donates to peer3
		peer1.getRequests().add(new Request("id4", peer1, peer3, 100, 50));	//peer3 donates to peer1
				
		doReturn(0).when(mockedAccountingPlugin).getTime();
		mockedAccountingPlugin.add(peer2);
		mockedAccountingPlugin.add(peer3);
		doReturn(10).when(mockedAccountingPlugin).getTime();
		mockedAccountingPlugin.updateAll();		
		AccountingInfo accPeer2 = mockedAccountingPlugin.getAccountingInfo(peer2);
		assertEquals(0, accPeer2.getConsumed(), ACCEPTABLE_ERROR);
		assertEquals(0, accPeer2.getDonated(), ACCEPTABLE_ERROR);
		AccountingInfo accPeer3 = mockedAccountingPlugin.getAccountingInfo(peer3);
		assertEquals(10, accPeer3.getConsumed(), ACCEPTABLE_ERROR);
		assertEquals(0, accPeer3.getDonated(), ACCEPTABLE_ERROR);
		
		doReturn(50).when(mockedAccountingPlugin).getTime();
		mockedAccountingPlugin.updateAll();		
		accPeer2 = mockedAccountingPlugin.getAccountingInfo(peer2);
		assertEquals(60, accPeer2.getConsumed(), ACCEPTABLE_ERROR);
		assertEquals(0, accPeer2.getDonated(), ACCEPTABLE_ERROR);
		accPeer3 = mockedAccountingPlugin.getAccountingInfo(peer3);
		assertEquals(50, accPeer3.getConsumed(), ACCEPTABLE_ERROR);
		assertEquals(0, accPeer3.getDonated(), ACCEPTABLE_ERROR);
		
		doReturn(100).when(mockedAccountingPlugin).getTime();
		mockedAccountingPlugin.updateAll();		
		accPeer2 = mockedAccountingPlugin.getAccountingInfo(peer2);
		assertEquals(120, accPeer2.getConsumed(), ACCEPTABLE_ERROR);
		assertEquals(50, accPeer2.getDonated(), ACCEPTABLE_ERROR);
		accPeer3 = mockedAccountingPlugin.getAccountingInfo(peer3);
		assertEquals(100, accPeer3.getConsumed(), ACCEPTABLE_ERROR);
		assertEquals(0, accPeer3.getDonated(), ACCEPTABLE_ERROR);
		
		doReturn(200).when(mockedAccountingPlugin).getTime();
		mockedAccountingPlugin.updateAll();		
		accPeer2 = mockedAccountingPlugin.getAccountingInfo(peer2);
		assertEquals(150, accPeer2.getConsumed(), ACCEPTABLE_ERROR);
		assertEquals(50, accPeer2.getDonated(), ACCEPTABLE_ERROR);
		accPeer3 = mockedAccountingPlugin.getAccountingInfo(peer3);
		assertEquals(150, accPeer3.getConsumed(), ACCEPTABLE_ERROR);
		assertEquals(50, accPeer3.getDonated(), ACCEPTABLE_ERROR);
		
	}

	@Test
	public void testUpdatePeer() {		
		doReturn(10).when(mockedAccountingPlugin).getTime();
		
		peer1.getRequests().add(new Request("id1", peer2, peer1, 10, 50));	//peer1 donates to peer2		
		mockedAccountingPlugin.add(peer2);		
		doReturn(30).when(mockedAccountingPlugin).getTime();		
		mockedAccountingPlugin.update(peer2);		
		AccountingInfo accPeer2 = mockedAccountingPlugin.getAccountingInfo(peer2);
		assertEquals(20, accPeer2.getConsumed(), ACCEPTABLE_ERROR);
		
		doReturn(50).when(mockedAccountingPlugin).getTime();
		mockedAccountingPlugin.update(peer2);	
		accPeer2 = mockedAccountingPlugin.getAccountingInfo(peer2);
		assertEquals(40, accPeer2.getConsumed(), ACCEPTABLE_ERROR);		
		
		doReturn(60).when(mockedAccountingPlugin).getTime();
		mockedAccountingPlugin.update(peer2);	
		accPeer2 = mockedAccountingPlugin.getAccountingInfo(peer2);
		assertEquals(50, accPeer2.getConsumed(), ACCEPTABLE_ERROR);
		
		doReturn(70).when(mockedAccountingPlugin).getTime();
		mockedAccountingPlugin.update(peer2);	
		accPeer2 = mockedAccountingPlugin.getAccountingInfo(peer2);
		assertEquals(50, accPeer2.getConsumed(), ACCEPTABLE_ERROR);
		
		peer1.getRequests().add(new Request("id2", peer2, peer1, 70, 30));	//peer1 donates to peer2
		peer1.getRequests().add(new Request("id3", peer2, peer1, 70, 30));	//peer1 donates to peer2
		doReturn(100).when(mockedAccountingPlugin).getTime();
		mockedAccountingPlugin.update(peer2);	
		accPeer2 = mockedAccountingPlugin.getAccountingInfo(peer2);
		assertEquals(110, accPeer2.getConsumed(), ACCEPTABLE_ERROR);
		
		doReturn(150).when(mockedAccountingPlugin).getTime();
		mockedAccountingPlugin.update(peer2);	
		accPeer2 = mockedAccountingPlugin.getAccountingInfo(peer2);
		assertEquals(110, accPeer2.getConsumed(), ACCEPTABLE_ERROR);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testUpdateAccountingOfInexistentPeer() throws IllegalArgumentException {
		accountingPlugin.updateAccounting(new Request("req1", peer1, peer2, 0, 50));
	}
	
	@Test
	public void testUpdateAccounting() {
		doReturn(10).when(mockedAccountingPlugin).getTime();
		mockedAccountingPlugin.add(peer2);
		peer1.getRequests().add(new Request("id1", peer2, peer1, 10, 50));	//peer1 donates to peer2
		doReturn(20).when(mockedAccountingPlugin).getTime();
		mockedAccountingPlugin.updateAccounting(peer1.getRequests().get(0));
		AccountingInfo accPeer2 = mockedAccountingPlugin.getAccountingInfo(peer2);
		assertEquals(10, accPeer2.getConsumed(), ACCEPTABLE_ERROR);
		assertEquals(0, accPeer2.getDonated(), ACCEPTABLE_ERROR);
		mockedAccountingPlugin.getAccountingInfo(peer2).setLastUpdated(20);
		doReturn(40).when(mockedAccountingPlugin).getTime();
		mockedAccountingPlugin.updateAccounting(peer1.getRequests().get(0));
		accPeer2 = mockedAccountingPlugin.getAccountingInfo(peer2);
		assertEquals(30, accPeer2.getConsumed(), ACCEPTABLE_ERROR);
		assertEquals(0, accPeer2.getDonated(), ACCEPTABLE_ERROR);
		mockedAccountingPlugin.getAccountingInfo(peer2).setLastUpdated(40);
		doReturn(100).when(mockedAccountingPlugin).getTime();
		mockedAccountingPlugin.updateAccounting(peer1.getRequests().get(0));
		accPeer2 = mockedAccountingPlugin.getAccountingInfo(peer2);
		assertEquals(50, accPeer2.getConsumed(), ACCEPTABLE_ERROR);
		assertEquals(0, accPeer2.getDonated(), ACCEPTABLE_ERROR);
		
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testAddAlreadyExistingPeer() throws IllegalArgumentException{
		assertNotNull(accountingPlugin.getAccountingList());
		accountingPlugin.add(peer2);
		assertEquals(1, accountingPlugin.getAccountingList().size());
		accountingPlugin.add(peer2);
	}

	@Test
	public void testGetAccountingList() {
		assertNotNull(accountingPlugin.getAccountingList());
		accountingPlugin.add(peer2);
		assertEquals(1, accountingPlugin.getAccountingList().size());
	}

	@Test
	public void testGetAccountingInfo() {
		assertNull(accountingPlugin.getAccountingInfo(peer2));
		
		accountingPlugin.add(peer2);
		assertNotNull(accountingPlugin.getAccountingInfo(peer2));
		
		assertNull(accountingPlugin.getAccountingInfo(peer3));
		accountingPlugin.add(peer3);
		assertNotNull(accountingPlugin.getAccountingInfo(peer3));
	}
	
	@Test
	public void testGetTime() {
		fail("Not yet implemented");
	}

}
