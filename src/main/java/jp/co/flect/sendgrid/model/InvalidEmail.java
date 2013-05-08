package jp.co.flect.sendgrid.model;

import java.util.Date;
import java.util.Map;

public class InvalidEmail extends AbstractModel {
	
	public InvalidEmail(Map<String, Object> map) {
		super(map);
	}
	
	public String getReason() { return doGetString("reason");}
	public Date getCreated() { return doGetDate("created", true);}
	public String getEmail() { return doGetString("email");}
	
   	public static class Get extends BasicGetRequest {
	}
	
	public static class Delete {
	}
	
}
