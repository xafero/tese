package com.xafero.tese;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

import javax.xml.bind.DatatypeConverter;

public class Tese {

	public <T> T deserialize(String text, Class<T> type) {
		try (StringReader reader = new StringReader(text)) {
			return deserialize(reader, type);
		}
	}

	public <T> T deserialize(Reader reader, Class<T> type) {
		try {
			Properties props = new Properties();
			props.load(reader);
			return deserialize(props, type);
		} catch (IOException e) {
			throw new TeseReadException(e);
		}
	}

	public <T> T deserialize(Properties props, Class<T> type) {
		try {
			T obj = type.newInstance();
			Field[] fields = type.getDeclaredFields();
			for (Field field : fields)
				deserialize(obj, field, props);
			return obj;
		} catch (InstantiationException | IllegalAccessException e) {
			throw new TeseReadException(e);
		}
	}

	private void deserialize(Object obj, Field field, Properties props) {
		try {
			Class<?> type = obj.getClass();
			String prefix = type.getSimpleName();
			String key = field.getName();
			String objKey = String.format("%s.%s", prefix, key);
			String val = props.getProperty(objKey);
			if (val == null)
				return;
			field.setAccessible(true);
			field.set(obj, fromStr(val, field));
		} catch (IllegalArgumentException | IllegalAccessException e) {
			throw new TeseReadException(e);
		}
	}

	private Object fromStr(String val, Field field) {
		Class<?> type = field.getType();
		switch (type.getName()) {
		case "boolean":
			return Boolean.parseBoolean(val);
		case "byte":
			return Byte.parseByte(val);
		case "char":
			return val.charAt(0);
		case "float":
			return Float.parseFloat(val);
		case "double":
			return Double.parseDouble(val);
		case "int":
			return Integer.parseInt(val);
		case "short":
			return Short.parseShort(val);
		case "long":
			return Long.parseLong(val);
		case "java.math.BigInteger":
			return new BigInteger(val);
		case "java.math.BigDecimal":
			return new BigDecimal(val);
		case "java.util.Date":
			Calendar cal = DatatypeConverter.parseDateTime(val);
			return cal.getTime();
		case "java.lang.String":
			return val;
		default:
			throw new UnsupportedOperationException(type + " is not supported!");
		}
	}

	public String serialize(Object obj) {
		try (StringWriter writer = new StringWriter()) {
			serialize(obj, writer);
			return writer.toString();
		} catch (IOException e) {
			throw new TeseWriteException(e);
		}
	}

	public void serialize(Object obj, Writer writer) {
		try {
			Properties props = new Properties();
			Class<?> type = obj.getClass();
			Field[] fields = type.getDeclaredFields();
			for (Field field : fields)
				serialize(obj, field, props);
			props.store(writer, null);
		} catch (IOException e) {
			throw new TeseWriteException(e);
		}
	}

	private void serialize(Object obj, Field field, Properties props) {
		try {
			String key = field.getName();
			field.setAccessible(true);
			Object val = field.get(obj);
			Class<?> type = obj.getClass();
			String prefix = type.getSimpleName();
			String objKey = String.format("%s.%s", prefix, key);
			props.setProperty(objKey, toStr(val, field));
		} catch (IllegalArgumentException | IllegalAccessException e) {
			throw new TeseWriteException(field, e);
		}
	}

	private String toStr(Object value, Field field) {
		Class<?> type = field.getType();
		switch (type.getName()) {
		case "java.util.Date":
			Calendar cal = Calendar.getInstance();
			cal.setTime((Date) value);
			return DatatypeConverter.printDateTime(cal);
		default:
			return value + "";
		}
	}
}
