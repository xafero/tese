package com.xafero.tese;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.math.BigInteger;

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

	private final String txt1 = "Customer.money=123.89 ~ Customer.sex=m ~ Customer.firstName=Harry ~ Customer.houses=42 ~ Customer.pets=13 ~ Customer.lastName=Johnson ~ Customer.crazyness=97.5 ~ Customer.id=1 ~ Customer.sleep=10 ~ Customer.male=true ~ Customer.awake=1 ~ Customer.bits=7";

	@Test
	public void testDeserialize() {
		Customer cus = tese.deserialize(deflatten(txt1), Customer.class);
		assertEquals(1, cus.getId());
		assertEquals("Harry", cus.getFirstName());
		assertEquals("Johnson", cus.getLastName());
		assertEquals(123.89, cus.getMoney(), 0.0);
		assertEquals(true, cus.isMale());
		assertEquals('m', cus.getSex());
		assertEquals(42, cus.getHouses());
		assertEquals(13, cus.getPets());
		assertEquals(97.5f, cus.getCrazyness(), 0.0f);
		assertEquals((byte) 7, cus.getBits());
		assertEquals(BigInteger.TEN, cus.getSleep());
		assertEquals(BigDecimal.ONE, cus.getAwake());
	}

	private String deflatten(String txt) {
		String nl = String.format("%n");
		txt = txt.replace(" ~ ", nl);
		return txt;
	}

	@Test
	public void testSerialize() {
		Customer cus = new Customer(1, "Harry", "Johnson", 123.89, true, 'm', 42, (short) 13, 97.5f, (byte) 7,
				BigInteger.TEN, BigDecimal.ONE);
		String txt = flatten(tese.serialize(cus));
		assertEquals(txt1, txt);
	}

	private String flatten(String txt) {
		String nl = String.format("%n");
		txt = txt.split(nl, 2)[1].trim();
		txt = txt.replace(nl, " ~ ");
		return txt;
	}
}
