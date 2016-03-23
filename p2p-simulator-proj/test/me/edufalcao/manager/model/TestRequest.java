package me.edufalcao.manager.model;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestRequest {

	@Test
	public void testEqualsObject() {
		
		Request r1 = new Request("id1", new Peer("p1"), new Peer("p2"), 0, 0);
		Request r2 = new Request("id1", new Peer("p2"), new Peer("p1"), 0, 0);
		Request r3 = new Request("id3", new Peer("p1"), new Peer("p2"), 0, 0);
		
		assertTrue(r1.equals(r2));
		assertTrue(r2.equals(r1));
		assertFalse(r3.equals(r1));
		assertFalse(r1.equals(r3));
	}

}
