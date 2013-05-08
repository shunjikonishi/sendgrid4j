package jp.co.flect.sendgrid.model;

import java.util.List;

public class WebMail extends AbstractRequest {

	public String getTo() { return doGetString("to");}
	public void setTo(String s) { doSetString("to", s);}
	
	public List getToList() { return doGetList("to");}
	public void setToList(List<String> list) { doSetList("to", list);}
	
	public String getToName() { return doGetString("toname");}
	public void setToName(String s) { doSetString("toname", s);}
	
	public List getToNameList() { return doGetList("toname");}
	public void setToNameList(List<String> list) { doSetList("toname", list);}
	
	public String getFrom() { return doGetString("from");}
	public void setFrom(String s) { doSetString("from", s);}
	
	public String getFromName() { return doGetString("fromname");}
	public void setFromName(String s) { doSetString("fromname", s);}
	
	public String getSubject() { return doGetString("subject");}
	public void setSubject(String s) { doSetString("subject", s);}
	
	public String getText() { return doGetString("text");}
	public void setText(String s) { doSetString("text", s);}
	
	public String getHtml() { return doGetString("html");}
	public void setHtml(String s) { doSetString("html", s);}
/*
x-smtpapi
subject
text
html
bcc
replyto
date
files
content
headers
*/
}
