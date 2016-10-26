package com.xafero.tese;

public class Address {

	private String street;
	private int number;
	private long postal;
	private City city;

	public Address() {
	}

	public Address(String street, int number, long postal, City city) {
		this.street = street;
		this.number = number;
		this.postal = postal;
		this.city = city;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public long getPostal() {
		return postal;
	}

	public void setPostal(long postal) {
		this.postal = postal;
	}

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}
}
