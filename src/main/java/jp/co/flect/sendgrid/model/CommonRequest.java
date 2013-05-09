package jp.co.flect.sendgrid.model;

public class CommonRequest extends AbstractRequest {
	
	public String get(String name) { return doGetString(name);}
	public void set(String name, String value) { doSetString(name, value);}
	
}
