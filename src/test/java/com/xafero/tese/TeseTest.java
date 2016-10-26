package com.xafero.tese;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TeseTest {

	private Tese tese;

	@Before
	public void testSetup() {
		tese = (new TeseBuilder()).skipNull(true).create();
	}

	@After
	public void testTeardown() {
		tese = null;
	}

	@Test
	public void testDeserialize() {
		String txt = "Customer.sex=m ~ " + "Customer.lastName=Johnson ~ " + "Customer.money=123.89 ~ "
				+ "Customer.houses=42 ~ " + "Customer.firstName=Harry ~ Customer.id=1 ~ " + "Customer.male=true";
		Customer cus = tese.deserialize(deflatten(txt), Customer.class);
		assertEquals(1, cus.getId());
		assertEquals("Harry", cus.getFirstName());
		assertEquals("Johnson", cus.getLastName());
		assertEquals(123.89, cus.getMoney(), 0.0);
		assertEquals(true, cus.isMale());
		assertEquals('m', cus.getSex());
		assertEquals(42, cus.getHouses());
	}

	private String deflatten(String txt) {
		String nl = String.format("%n");
		txt = txt.replace(" ~ ", nl);
		return txt;
	}

	@Test
	public void testSerialize() {
		Customer cus = new Customer(1, "Harry", "Johnson", 123.89, true, 'm', 42);
		String txt = flatten(tese.serialize(cus));
		assertEquals("Customer.sex=m ~ " + "Customer.lastName=Johnson ~ " + "Customer.money=123.89 ~ "
				+ "Customer.houses=42 ~ " + "Customer.firstName=Harry ~ Customer.id=1 ~ " + "Customer.male=true", txt);
	}

	private String flatten(String txt) {
		String nl = String.format("%n");
		txt = txt.split(nl, 2)[1].trim();
		txt = txt.replace(nl, " ~ ");
		return txt;
	}
}
