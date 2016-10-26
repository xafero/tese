package com.xafero.tese;

public class City {

	private String name;
	private State state;
	private long code;

	public City() {
	}

	public City(String name, State state, long code) {
		this.name = name;
		this.state = state;
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public long getCode() {
		return code;
	}

	public void setCode(long code) {
		this.code = code;
	}
}
