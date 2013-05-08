package jp.co.flect.sendgrid.model;

import java.util.Date;
import java.util.List;

abstract class BasicGetRequest extends AbstractGetRequest {
	
	protected BasicGetRequest() {
		super(true);
	}
	
	public int getLimit() { return doGetInt("limit", 0);}
	public void setLimit(int n) { doSetInt("limit", n);}
	
	public int getOffset() { return doGetInt("offset", 0);}
	public void setOffset(int n) { doSetInt("offset", n);}
	
	public String getEmali() { return doGetString("email");}
	public List<String> getEmailList() { return doGetList("email");}
	public void setEmail(String s) { doSetString("email", s);}
	public void setEmailList(List<String> list) { doSetList("email", list);}
}
