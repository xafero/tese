package com.xafero.tese;

public class Customer {

	private long id;
	private String firstName;
	private String lastName;
	private double money;
	private boolean male;
	private char sex;
	private int houses;

	public Customer() {
	}

	public Customer(long id, String firstName, String lastName, double money, boolean male, char sex, int houses) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.money = money;
		this.male = male;
		this.sex = sex;
		this.houses = houses;
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
}
