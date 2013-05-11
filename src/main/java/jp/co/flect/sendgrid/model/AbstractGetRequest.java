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
		if (start == null && end == null) {
			throw new IllegalArgumentException();
		}
		if (start != null && end != null && start.getTime() > end.getTime()) {
			throw new IllegalArgumentException("Start date must be before the end date");
		}
		if (start != null) {
			doSetDate("start_date", start);
		}
		if (end != null) {
			doSetDate("end_date", end);
		}
	}
	
}
