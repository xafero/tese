package com.xafero.tese;

import java.lang.reflect.Field;

public class TeseWriteException extends RuntimeException {

	private static final long serialVersionUID = 1997594550486370430L;

	public TeseWriteException(Throwable t) {
		super(t);
	}

	public TeseWriteException(Field field, Exception e) {
		super(field + "", e);
	}

}
