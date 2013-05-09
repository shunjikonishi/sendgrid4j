package jp.co.flect.sendgrid.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.util.Map;
import java.util.HashMap;
import java.util.Locale;
import java.text.SimpleDateFormat;
import java.text.ParseException;

import jp.co.flect.sendgrid.json.JsonUtils;

public class WebMail extends AbstractRequest {
	
	private static final String RFC2822_DATE_FORMAT = "EEE, dd MMM yyyy HH:mm:ss Z";
	
	private Map<String, String> content;
	private List<String> categories;
	
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
	
	public String getBcc() { return doGetString("bcc");}
	public void setBcc(String s) { doSetString("bcc", s);}
	
	public List getBccList() { return doGetList("bcc");}
	public void setBccList(List<String> list) { doSetList("bcc", list);}
	
	public String getSubject() { return doGetString("subject");}
	public void setSubject(String s) { doSetString("subject", s);}
	
	public String getText() { return doGetString("text");}
	public void setText(String s) { doSetString("text", s);}
	
	public String getHtml() { return doGetString("html");}
	public void setHtml(String s) { doSetString("html", s);}
	
	//public String getXSmtpApi() { return doGetString("x-smtpapi");}
	//public void setXSmtpApi(String s) { doSetString("x-smtpapi", s);}
	
	public String getReplyTo() { return doGetString("replyto");}
	public void setReplyTo(String s) { doSetString("replyto", s);}
	
	public Date getDate() {
		String s = doGetString("date");
		if (s == null) {
			return null;
		}
		try {
			return new SimpleDateFormat(RFC2822_DATE_FORMAT, Locale.US).parse(s);
		} catch (ParseException e) {
			throw new IllegalStateException(s);
		}
	}
	
	public void setDate(Date d) {
		String s = new SimpleDateFormat(RFC2822_DATE_FORMAT, Locale.US).format(d);
		doSetString("date", s);
	}
	
	public Map<String, String> getHeaders() {
		String s = doGetString("headers");
		if (s == null) {
			return null;
		}
		Map<String, Object> map = JsonUtils.parse(s);
		Map<String, String> ret = new HashMap<String, String>();
		for (Map.Entry<String, Object> entry : map.entrySet()) {
			ret.put(entry.getKey(), entry.getValue().toString());
		}
		return ret;
	}
	
	public void setHeaders(Map<String, String> map) {
		String s = JsonUtils.serialize(map);
		doSetString("headers", s);
	}
	
	/**
	 * return Map of filename and cid.
	 */
	public Map<String, String> getContent() {
		return this.content;
	}
	
	/**
	 * Set filename and cid mapping.
	 * If you want to use inline images in the HTML markup,
	 * you must set its filename and cid mapping.
	 */
	public void setContent(Map<String, String> map) {
		this.content = map;
	}
	
	public String getCategory() { 
		return this.categories == null || this.categories.size() == 0 ? null : this.categories.get(0);
	}
	
	public void setCategory(String s) {
		if (s == null) {
			this.categories = null;
		} else {
			List<String> list = new ArrayList<String>();
			list.add(s);
			this.categories = list;
		}
	}
	
	public List<String> getCategories() { return this.categories;}
	public void setCategories(List<String> list) { this.categories = list;}
	
	public String getXSmtpApiAsString() {
		if (this.categories == null || this.categories.size() == 0) {
			return null;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("category", this.categories.toArray(new String[this.categories.size()]));
		
		return JsonUtils.serialize(map);
	}
}
