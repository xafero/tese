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
import java.util.Map;
import java.util.Properties;

import javax.xml.bind.DatatypeConverter;

public class Tese {

	private static final String emptyPrefix = "";

	public <T> T deserialize(String text, Class<T> type) {
		try (StringReader reader = new StringReader(text)) {
			return deserialize(reader, type);
		}
	}

	public <T> T deserialize(Reader reader, Class<T> type) {
		try {
			Properties props = new Properties();
			props.load(reader);
			return deserializeFields(emptyPrefix, props, type);
		} catch (IOException e) {
			throw new TeseReadException(e);
		}
	}

	private <T> T deserializeFields(String prefix, Map<?, ?> props, Class<T> type) {
		try {
			T obj = type.newInstance();
			Field[] fields = type.getDeclaredFields();
			for (Field field : fields)
				deserializeField(prefix, obj, field, props);
			return obj;
		} catch (InstantiationException | IllegalAccessException e) {
			throw new TeseReadException(e);
		}
	}

	@SuppressWarnings("rawtypes")
	private void deserializeField(String prefix, Object obj, Field field, Map props) {
		try {
			String key = field.getName();
			String objKey = String.format("%s.%s", prefix, key);
			Object val = props.get(objKey);
			if (val == null) {
				if (findLongerKey(props, objKey)) {
					field.setAccessible(true);
					field.set(obj, deserializeFields(objKey, props, field.getType()));
				}
				return;
			}
			field.setAccessible(true);
			field.set(obj, fromStr(val.toString(), field));
		} catch (IllegalArgumentException | IllegalAccessException e) {
			throw new TeseReadException(e);
		}
	}

	@SuppressWarnings("rawtypes")
	private boolean findLongerKey(Map props, String shortKey) {
		for (Object key : props.keySet())
			if (key.toString().startsWith(shortKey))
				return true;
		return false;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private Object fromStr(String val, Field field) {
		Class<?> type = field.getType();
		if (type.isEnum())
			return Enum.valueOf((Class<Enum>) type, val);
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
			serializeFields(emptyPrefix, obj, props);
			props.store(writer, null);
		} catch (IOException e) {
			throw new TeseWriteException(e);
		}
	}

	@SuppressWarnings("rawtypes")
	private void serializeFields(String prefix, Object obj, Map props) {
		Class<?> type = obj.getClass();
		Field[] fields = type.getDeclaredFields();
		for (Field field : fields)
			serializeField(prefix, obj, field, props);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void serializeField(String prefix, Object obj, Field field, Map props) {
		try {
			String key = field.getName();
			field.setAccessible(true);
			Object val = field.get(obj);
			String objKey = String.format("%s.%s", prefix, key);
			try {
				props.put(objKey, toStr(val, field));
			} catch (UnsupportedOperationException uoe) {
				serializeFields(objKey, val, props);
			}
		} catch (IllegalArgumentException | IllegalAccessException e) {
			throw new TeseWriteException(field, e);
		}
	}

	private String toStr(Object value, Field field) {
		Class<?> type = field.getType();
		if (type.isEnum())
			return ((Enum<?>) value).name();
		switch (type.getName()) {
		case "java.util.Date":
			Calendar cal = Calendar.getInstance();
			cal.setTime((Date) value);
			return DatatypeConverter.printDateTime(cal);
		case "long":
		case "char":
		case "int":
		case "byte":
		case "short":
		case "float":
		case "double":
		case "boolean":
		case "java.math.BigInteger":
		case "java.math.BigDecimal":
		case "java.lang.String":
			return value.toString();
		default:
			throw new UnsupportedOperationException(type + " is not supported!");
		}
	}
}
