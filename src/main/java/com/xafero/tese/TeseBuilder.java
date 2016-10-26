package com.xafero.tese;

public class TeseBuilder {

	private boolean skipNull;

	public TeseBuilder skipNull(boolean skipNull) {
		this.skipNull = skipNull;
		return this;
	}

	public boolean isSkipNull() {
		return skipNull;
	}

	public Tese create() {
		return new Tese();
	}
}
