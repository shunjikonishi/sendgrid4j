package jp.co.flect.sendgrid.model;

import java.util.Date;

abstract class AbstractGetRequest extends AbstractRequest {
	
	protected AbstractGetRequest(boolean includeDateParameter) {
		if (includeDateParameter) {
			doSetInt("date", 1);
		}
	}
	
	public int getDays() { return doGetInt("days", 0);}
	public void setDays(int n) { doSetInt("days", n);}
	
	public Date getStartDate() { return doGetDate("start_date");}
	public Date getEndDate() { return doGetDate("end_date");}
	public void setStartAndEndDate(Date start, Date end) {
		doSetDate("start_date", start);
		doSetDate("end_date", end);
	}
	
}
