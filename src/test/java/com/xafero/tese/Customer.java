package com.xafero.tese;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

public class Customer {

	private long id;
	private String firstName;
	private String lastName;
	private double money;
	private boolean male;
	private char sex;
	private int houses;
	private short pets;
	private float crazyness;
	private byte bits;
	private BigInteger sleep;
	private BigDecimal awake;
	private Date birth;

	public Customer() {
	}

	public Customer(long id, String firstName, String lastName, double money, boolean male, char sex, int houses,
			short pets, float crazyness, byte bits, BigInteger sleep, BigDecimal awake, Date birth) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.money = money;
		this.male = male;
		this.sex = sex;
		this.houses = houses;
		this.pets = pets;
		this.crazyness = crazyness;
		this.bits = bits;
		this.sleep = sleep;
		this.awake = awake;
		this.birth = birth;
	}

	public long getId() {
		return id;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public double getMoney() {
		return money;
	}

	public boolean isMale() {
		return male;
	}

	public char getSex() {
		return sex;
	}

	public int getHouses() {
		return houses;
	}

	public short getPets() {
		return pets;
	}

	public float getCrazyness() {
		return crazyness;
	}

	public byte getBits() {
		return bits;
	}

	public BigInteger getSleep() {
		return sleep;
	}

	public BigDecimal getAwake() {
		return awake;
	}

	public Date getBirth() {
		return birth;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setMoney(double money) {
		this.money = money;
	}

	public void setMale(boolean male) {
		this.male = male;
	}

	public void setSex(char sex) {
		this.sex = sex;
	}

	public void setHouses(int houses) {
		this.houses = houses;
	}

	public void setPets(short pets) {
		this.pets = pets;
	}

	public void setCrazyness(float crazyness) {
		this.crazyness = crazyness;
	}

	public void setBits(byte bits) {
		this.bits = bits;
	}

	public void setSleep(BigInteger sleep) {
		this.sleep = sleep;
	}

	public void setAwake(BigDecimal awake) {
		this.awake = awake;
	}

	public void setBirth(Date birth) {
		this.birth = birth;
	}
}
