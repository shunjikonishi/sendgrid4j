package jp.co.flect.sendgrid.json;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.lang.reflect.Type;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;

public class JsonByJackson implements Json {
	
	public List<Map<String, Object>> parseArray(String str) {
		try {
			TypeReference type = new TypeReference<List<Map<String, Object>>>() {};
			List<Map<String, Object>> ret = new ObjectMapper().readValue(str, type);
			convertIntToDouble(ret);
			return ret;
		} catch (IOException e) {
			throw new IllegalArgumentException(e);
		}
	}
	
	public Map<String, Object> parse(String str) {
		try {
			TypeReference type = new TypeReference<Map<String, Object>>() {};
			Map<String, Object> ret = new ObjectMapper().readValue(str, type);
			convertIntToDouble(ret);
			return ret;
		} catch (IOException e) {
			throw new IllegalArgumentException(e);
		}
	}
	
	public String serialize(Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (IOException e) {
			throw new IllegalArgumentException(e);
		}
	}

	private void convertIntToDouble(Map map) {
		for (Object key : map.keySet()) {
			Object value = map.get(key);
			if (value instanceof Integer) {
				map.put(key, ((Integer)value).doubleValue());
			} else if (value instanceof Map) {
				convertIntToDouble((Map)value);
			} else if (value instanceof List) {
				convertIntToDouble((List)value);
			}
		}
	}

	private void convertIntToDouble(List list) {
		for (int i=0; i<list.size(); i++) {
			Object value = list.get(i);
			if (value instanceof Integer) {
				list.set(i, ((Integer)value).doubleValue());
			} else if (value instanceof Map) {
				convertIntToDouble((Map)value);
			} else if (value instanceof List) {
				convertIntToDouble((List)value);
			}
		}
	}
}
