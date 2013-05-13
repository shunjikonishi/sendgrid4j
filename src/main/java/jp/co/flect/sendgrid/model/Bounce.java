package jp.co.flect.sendgrid.model;

import java.util.Date;
import java.util.Map;

public class Bounce extends AbstractModel {
	
	public enum Type {
		HARD,
		SOFT
	}
	
	public Bounce(Map<String, Object> map) {
		super(map);
	}
	
	public String getStatus() { return doGetString("status");}
	public String getReason() { return doGetString("reason");}
	public Date getCreated() { return doGetDate("created", true);}
	public String getEmail() { return doGetString("email");}
	
	public static class Get extends BasicGetRequest {
		
		public Bounce.Type getType() {
			String s = doGetString("type");
			return s == null ? null : Type.valueOf(s.toUpperCase());
		}
		
		public void setType(Bounce.Type type) {
			doSetString("type", type.toString().toLowerCase());
		}
	}
	
	public static class Count extends AbstractRequest {
		
		public Date getStartDate() { return doGetDate("start_date");}
		public Date getEndDate() { return doGetDate("end_date");}
		@Override
		public void setStartAndEndDate(Date start, Date end) {
			super.setStartAndEndDate(start, end);
		}
		
		
		public Bounce.Type getType() {
			String s = doGetString("type");
			return s == null ? null : Type.valueOf(s.toUpperCase());
		}
		
		public void setType(Bounce.Type type) {
			doSetString("type", type.toString().toLowerCase());
		}
		
	}
	
	public static class Delete extends Count {
		
		public String getEmail() { return doGetString("email");}
		public void setEmail(String s) { doSetString("email", s);}
	}
	
}
