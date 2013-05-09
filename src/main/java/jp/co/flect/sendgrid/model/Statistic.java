package jp.co.flect.sendgrid.model;

import java.util.Date;
import java.util.Map;

public class Statistic extends AbstractModel{
	
	public Statistic(Map<String, Object> map) {
		super(map);
	}
	
	public Date getDate() { return doGetDate("date", false);}
	public String getCategory() { return doGetString("cateegory");}
	public int getRequests() { return doGetInt("requests", 0);}
	public int getBounces() { return doGetInt("bounces", 0);}
	public int getClicks() { return doGetInt("clicks", 0);}
	public int getOpens() { return doGetInt("opens", 0);}
	public int getSpamReports() { return doGetInt("spamreports", 0);}
	public int getUniqueClicks() { return doGetInt("unique_clicks", 0);}
	public int getUniqueOpens() { return doGetInt("unique_opens", 0);}
	public int getBlocked() { return doGetInt("blocked", 0);}
	
	public int getDelivered() { return doGetInt("delivered", 0);}
	public int getUnsubscribes() { return doGetInt("unsubscribes", 0);}
	public int getInvalidEmails() { return doGetInt("invalid_email", 0);}
	public int getRepeatUnsubscribes() { return doGetInt("repeat_unsubscribes", 0);}
	public int getSpamDrops() { return doGetInt("spam_drop", 0);}
	public int getRepeatBounces() { return doGetInt("repeat_bounces", 0);}
	public int getRepeatSpamReports() { return doGetInt("repeat_spamreports", 0);}
	
	public static class Get extends AbstractGetRequest {
		
		public Get() {
			super(false);
		}
		
		public boolean isAggregate() {
			String s = doGetString("aggregate");
			return s != null && s.equals("1");
		}
		
		public void setAggregate(boolean b) {
			String s = b ? "1" : "0";
			doSetString("aggregate", s);
		}
		
		public String getCategory() { return doGetString("category");}
		public void setCategory(String s) { doSetString("category", s);}
	}
	
}
