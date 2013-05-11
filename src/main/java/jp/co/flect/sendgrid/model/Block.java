package jp.co.flect.sendgrid.model;

import java.util.Date;
import java.util.Map;

public class Block extends AbstractModel {
	
	public Block(Map<String, Object> map) {
		super(map);
	}
	public String getStatus() { return doGetString("status");}
	public String getReason() { return doGetString("reason");}
	public Date getCreated() { return doGetDate("created", true);}
	public String getEmail() { return doGetString("email");}
	
	public static class Get extends BasicGetRequest {
	}
	
	public static class Delete extends AbstractRequest {
		
		public String getEmail() { return doGetString("email");}
		public void setEmail(String s) { doSetString("email", s);}
	}
	
}
