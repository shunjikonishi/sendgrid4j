package jp.co.flect.sendgrid.json;

import java.util.List;
import java.util.Map;

public interface Json {
	
	public List<Map<String, Object>> parseArray(String str);
	public Map<String, Object> parse(String str);
	public String serialize(Object obj);
}
