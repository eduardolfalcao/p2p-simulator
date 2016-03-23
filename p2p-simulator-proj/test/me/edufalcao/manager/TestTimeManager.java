package me.edufalcao.manager;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestTimeManager {

	@Test
	public void test() {
		TimeManager tm = TimeManager.getInstance();
		assertNotNull(tm);
		assertEquals(0, tm.getTime());
		TimeManager tm2 = TimeManager.getInstance();
		assertEquals(0, tm2.getTime());
		assertEquals(tm, tm2);
		tm2.setTime(10);
		assertEquals(10, tm.getTime());		
	}

}
