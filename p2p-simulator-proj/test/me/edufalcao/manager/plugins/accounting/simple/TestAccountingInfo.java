package me.edufalcao.manager.plugins.accounting.simple;

import static org.junit.Assert.*;
import me.edufalcao.manager.model.Peer;
import me.edufalcao.manager.plugins.accounting.AccountingInfo;

import org.junit.Before;
import org.junit.Test;

public class TestAccountingInfo {
	
	private final double ACCEPTABLE_ERROR = 0.000001;
	
	AccountingInfo ac1;

	@Before
	public void setUp() throws Exception {
		ac1 = new AccountingInfo(new Peer("p1"),0);		
	}

	@Test
	public void testEqualsObject() {
		AccountingInfo ac2, ac3;
		ac2 = new AccountingInfo(new Peer("p2"),0);
		ac3 = new AccountingInfo(new Peer("p1"),0);
		assertEquals(ac1, ac3);
		assertNotEquals(ac1, ac2);
	}

	@Test
	public void testAddDonation() {
		double donation = 5.0;
		ac1.addDonation(donation);
		assertEquals(donation, ac1.getDonated(), ACCEPTABLE_ERROR);
		ac1.addDonation(3.1);
		assertEquals(8.1, ac1.getDonated(), ACCEPTABLE_ERROR);
		ac1.addConsumption(10);
		assertEquals(8.1, ac1.getDonated(), ACCEPTABLE_ERROR);
	}

	@Test
	public void testAddConsumption() {
		double consumption = 5.0;
		ac1.addConsumption(consumption);
		assertEquals(consumption, ac1.getConsumed(), ACCEPTABLE_ERROR);
		ac1.addConsumption(3.1);
		assertEquals(8.1, ac1.getConsumed(), ACCEPTABLE_ERROR);
		ac1.addDonation(10);
		assertEquals(8.1, ac1.getConsumed(), ACCEPTABLE_ERROR);
	}

	@Test
	public void testFormatDouble() {
		ac1.addDonation(4.123456789);
		assertEquals(4.1234567, ac1.formatDouble(ac1.getDonated()),ACCEPTABLE_ERROR);
	}

}
