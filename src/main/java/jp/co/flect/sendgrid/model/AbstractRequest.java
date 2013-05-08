package jp.co.flect.sendgrid.model;

import java.util.List;
import java.util.Arrays;
import java.util.Map;
import java.util.HashMap;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;

public abstract class AbstractRequest {
	
	private static final String DATE_FORMAT = "yyyy-MM-dd";
	
	private Map<String, String[]> map = new HashMap<String, String[]>();
	private SimpleDateFormat dateFormat = null;
	
	private SimpleDateFormat getDateFormat() {
		if (this.dateFormat == null) {
			this.dateFormat = new SimpleDateFormat(DATE_FORMAT);
		}
		return this.dateFormat;
	}
	
	protected int doGetInt(String name, int defaultValue) {
		String s = doGetString(name);
		if (s == null) {
			return defaultValue;
		}
		try {
			return Integer.parseInt(s);
		} catch (NumberFormatException e) {
			throw new IllegalStateException(name + ": " + s);
		}
	}
	
	protected void doSetInt(String name, int n) {
		doSetString(name, Integer.toString(n));
	}
	
	protected String doGetString(String name) {
		String[] strs = this.map.get(name);
		return strs != null && strs.length > 0 ? strs[0] : null;
	}
	
	protected void doSetString(String name, String s) {
		String[] strs = new String[1];
		strs[0] = s;
		this.map.put(name, strs);
	}
	
	protected List<String> doGetList(String name) {
		String[] strs = map.get(name);
		return strs != null && strs.length > 0 ? Arrays.asList(strs) : null;
	}
	
	protected void doSetList(String name, List<String> list) {
		String[] strs = new String[list.size()];
		strs = list.toArray(strs);
		this.map.put(name, strs);
	}
	
	protected Date doGetDate(String name) {
		String s = doGetString(name);
		if (s == null) {
			return null;
		}
		try {
			return getDateFormat().parse(s);
		} catch (ParseException e) {
			throw new IllegalStateException(name + ": " + s);
		}
	}
	
	protected void doSetDate(String name, Date d) {
		doSetString(name, getDateFormat().format(d));
	}
	
	public Map<String, String[]> getParams() {
		return new HashMap<String, String[]>(this.map);
	}
}
