package jp.co.flect.sendgrid.model;

import java.util.List;

public class CommonRequest extends AbstractRequest {
	
	public String get(String name) { return doGetString(name);}
	public void set(String name, String value) { doSetString(name, value);}
	public void set(String name, int value) { doSetInt(name, value);}
	public void set(String name, List<String> value) { doSetList(name, value);}
	public void set(String name, boolean value) { doSetString(name, value ? "1" : "0");}
}
