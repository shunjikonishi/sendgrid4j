package jp.co.flect.sendgrid.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;

public abstract class AbstractModel {
	
	private static final String DATE_FORMAT     = "yyyy-MM-dd";
	private static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
	
	private Map<String, Object> map;
	private SimpleDateFormat dateFormat = null;
	private SimpleDateFormat datetimeFormat = null;
	
	protected AbstractModel(Map<String, Object> map) {
		this.map = map;
	}
	
	private SimpleDateFormat getDatetimeFormat() {
		if (this.datetimeFormat == null) {
			this.datetimeFormat = new SimpleDateFormat(DATETIME_FORMAT);
		}
		return this.datetimeFormat;
	}
	
	private SimpleDateFormat getDateFormat() {
		if (this.dateFormat == null) {
			this.dateFormat = new SimpleDateFormat(DATE_FORMAT);
		}
		return this.dateFormat;
	}
	
	protected int doGetInt(String name, int defaultValue) {
		Object o = this.map.get(name);
		if (o == null) {
			return defaultValue;
		} else if (o instanceof Number) {
			return ((Number)o).intValue();
		} else {
			try {
				return Integer.parseInt(o.toString());
			} catch (NumberFormatException e) {
				throw new IllegalStateException(name + ": " + o);
			}
		}
	}
	
	protected boolean doGetBoolean(String name) {
		Object o = this.map.get(name);
		if (o == null) {
			return false;
		} else if (o instanceof Boolean) {
			return ((Boolean)o).booleanValue();
		} else {
			return "true".equals(o.toString());
		}
	}
	
	protected String doGetString(String name) {
		Object o = this.map.get(name);
		return o == null ? null : o.toString();
	}
	
	protected List<String> doGetList(String name) {
		Object o = this.map.get(name);
		if (o == null) {
			return null;
		}
		List<String> ret = new ArrayList<String>();
		if (o instanceof List) {
			List list = (List)o;
			for (Object value : list) {
				ret.add(value.toString());
			}
		} else {
			ret.add(o.toString());
		}
		return ret;
	}
	
	protected Date doGetDate(String name, boolean includeTime) {
		String s = doGetString(name);
		if (s == null) {
			return null;
		}
		try {
			SimpleDateFormat format = includeTime ? getDatetimeFormat() : getDateFormat();
			return format.parse(s);
		} catch (ParseException e) {
			throw new IllegalStateException(name + ": " + s);
		}
	}
	
}
