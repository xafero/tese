package com.xafero.tese;

public class Customer {

	private long id;
	private String firstName;
	private String lastName;
	private double money;
	private boolean male;

	public Customer() {
	}

	public Customer(long id, String firstName, String lastName, double money, boolean male) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.money = money;
		this.male = male;
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
}
