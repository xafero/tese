package com.xafero.tese;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Date;

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

	private final String txt1 = ".id=1 ~ .bits=7 ~ .lastName=Johnson ~ .sex=m ~ .birth=1970-02-19T02\\:17\\:29.348+01\\:00 ~ .money=123.89 ~ .awake=1 ~ .home.city.state=IA ~ .firstName=Harry ~ .home.city.name=Ankeny ~ .home.city.code=1 ~ .home.number=22 ~ .houses=42 ~ .crazyness=97.5 ~ .home.postal=50023 ~ .sleep=10 ~ .male=true ~ .home.street=West Ohio Street ~ .pets=13";

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
		assertEquals(4238249348L, cus.getBirth().getTime());
		assertEquals(22, cus.getHome().getNumber());
		assertEquals(50023, cus.getHome().getPostal());
		assertEquals("West Ohio Street", cus.getHome().getStreet());
		assertEquals(1, cus.getHome().getCity().getCode());
		assertEquals("Ankeny", cus.getHome().getCity().getName());
		assertEquals(State.IA, cus.getHome().getCity().getState());
	}

	private String deflatten(String txt) {
		String nl = String.format("%n");
		txt = txt.replace(" ~ ", nl);
		return txt;
	}

	@Test
	public void testSerialize() {
		Customer cus = new Customer(1, "Harry", "Johnson", 123.89, true, 'm', 42, (short) 13, 97.5f, (byte) 7,
				BigInteger.TEN, BigDecimal.ONE, new Date(4238249348L),
				new Address("West Ohio Street", 22, 50023, new City("Ankeny", State.IA, 1L)));
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
