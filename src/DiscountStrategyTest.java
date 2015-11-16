import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import junit.framework.TestCase;


public class DiscountStrategyTest extends TestCase {
	
	private DiscountStrategy dS;
	private Order testOrder = new Order("Customer", (float) 100.0);
		
	@Before
	protected void setUp() throws Exception {
		super.setUp();
	}

	@After
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	@Test
	public void testPercentDiscount() {
		dS = new PercentDiscountStrategy(50);
		assertEquals(testOrder.getPrice()/(float) 2, dS.applyDiscount(testOrder));
		
		dS = new PercentDiscountStrategy(100);
		assertEquals((float) 0, dS.applyDiscount(testOrder));
		
		dS = new PercentDiscountStrategy(0);
		assertEquals(testOrder.getPrice(), dS.applyDiscount(testOrder));
	}
	
	@Test
	public void testRandomDiscount() {
		for (int i = 0; i < 100; i++) {
			dS = new RandomDiscountStrategy();
			assertTrue(testOrder.getPrice() > dS.applyDiscount(testOrder));
			assertTrue(dS.applyDiscount(testOrder) > 0);
		}
	}
	
	@Test
	public void testCreditDiscount() {
		dS = new StoreCreditDiscountStrategy(0);
		assertEquals(testOrder.getPrice(), dS.applyDiscount(testOrder));
		
		dS = new StoreCreditDiscountStrategy(1);
		assertEquals(testOrder.getPrice() - (float) 1, dS.applyDiscount(testOrder));
		
		dS = new StoreCreditDiscountStrategy(100);
		assertEquals(testOrder.getPrice() - (float) 100, dS.applyDiscount(testOrder));
	}
}
