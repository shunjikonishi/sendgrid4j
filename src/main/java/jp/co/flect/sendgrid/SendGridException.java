package jp.co.flect.sendgrid;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SendGridException extends Exception {
	
	private static String getMessageFromMap(Map<String, Object> map) {
		Object msg = map.get("error");
		if (msg != null) {
			return msg.toString();
		}
		msg = map.get("errors");
		if (msg instanceof List) {
			return ((List)msg).get(0).toString();
		}
		throw new IllegalArgumentException(map.toString());
	}
	
	private List<String> msgs;
	
	public SendGridException(String msg) {
		super(msg);
		this.msgs = Arrays.asList(msg);
	}
	
	public SendGridException(List<String> msgs) {
		super(msgs.get(0));
		this.msgs = msgs;
	}
	
	public SendGridException(Map<String, Object> map) {
		super(getMessageFromMap(map));
		this.msgs = new ArrayList<String>();
		this.msgs.add(super.getMessage());
		Object errors = map.get("errors");
		if (errors instanceof List) {
			List list = (List)errors;
			for (int i=1; i<list.size(); i++) {//Skip 0
				this.msgs.add(list.get(i).toString());
			}
		}
	}
	
	public List<String> getAllMessages() { return this.msgs;}
}
