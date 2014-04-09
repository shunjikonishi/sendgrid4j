package jp.co.flect.sendgrid.model;

import java.util.Date;
import java.util.Map;

public class Unsubscribe extends AbstractModel {
	
	public Unsubscribe(Map<String, Object> map) {
		super(map);
	}
	
	public Date getCreated() { return doGetDate("created", true);}
	public String getEmail() { return doGetString("email");}
	
	public static class Get extends BasicGetRequest {
		
	}
	
	public static class Delete extends AbstractRequest {
		
		public Date getStartDate() { return doGetDate("start_date");}
		public Date getEndDate() { return doGetDate("end_date");}
		@Override
		public void setStartAndEndDate(Date start, Date end) {
			super.setStartAndEndDate(start, end);
		}
		
		public String getEmail() { return doGetString("email");}
		public void setEmail(String s) { doSetString("email", s);}

		public boolean isDeleteAll() { return doGetInt("delete_all", 0) == 1;}
		public void setDeleteAll(boolean b) { doSetInt("delete_all", b ? 1 : 0);}
	}
	
	public static class Add extends AbstractRequest {
		
		public Add() {}
		public Add(String email) {
			setEmail(email);
		}
		
		public String getEmail() { return doGetString("email");}
		public void setEmail(String s) { doSetString("email", s);}
	}
	
}
