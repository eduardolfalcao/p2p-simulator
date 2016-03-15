package me.edufalcao.manager.plugins.capacitycontroller.satisfactiondriven;

import static org.junit.Assert.*;
import me.edufalcao.manager.model.Peer;

import org.junit.Test;

public class TestSatisfactionDrivenCapacityController {

	private final double ACCEPTABLE_ERROR = 0.000001;
	
	@Test
	public void testGetMaxCapacityToSupply() {
		Peer peer1 = new Peer(25, "p1", false);
		peer1.setCapacityControllerPlugin(new SatisfactionDrivenCapacityController(peer1));
		Peer peer2 = new Peer(10, "p2", false);
		assertEquals(25, peer1.getCapacityControllerPlugin().getMaxCapacityToSupply(peer2), ACCEPTABLE_ERROR);
	}

}
