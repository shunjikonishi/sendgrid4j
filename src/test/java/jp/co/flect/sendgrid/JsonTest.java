package jp.co.flect.sendgrid;

import java.util.Arrays;
import java.util.Map;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import org.junit.Test;
import org.junit.BeforeClass;

import jp.co.flect.sendgrid.json.JsonUtils;

public class JsonTest {
	
	@Test
	public void parse() throws Exception {
		String str = "{\"str1\" : \"str\", "+
			"\"num1\" : 5, " +
			"\"num2\" : 6.0, " +
			"\"num3\" : 3.14, " +
			"\"bool1\" : true, " +
			"\"bool2\" : false, " +
			"\"array1\" : [\"str1\", \"str2\", \"str3\"], " +
			"\"array2\" : [1, 2, 3], " +
			"\"array3\" : [true, false, true], " + 
			"\"array4\" : [\"str1\", 2, true]}";
		
		Map<String, Object> map = JsonUtils.parse(str);
		test(map, "str1", "str");
		test(map, "num1", 5.0);
		test(map, "num2", 6.0);
		test(map, "num3", 3.14);
		test(map, "bool1", true);
		test(map, "bool2", false);
		test(map, "array1", Arrays.asList("str1", "str2", "str3"));
		test(map, "array2", Arrays.asList(1.0, 2.0, 3.0));
		test(map, "array3", Arrays.asList(true, false, true));
		test(map, "array4", Arrays.asList("str1", 2.0, true));
		
		String str2 = JsonUtils.serialize(map);
		System.out.println("Serialize: " + str2);
		assertEquals(map, JsonUtils.parse(str2));
	}
	
	private void test(Map<String, Object> map, String key, Object expected) {
		Object value = map.get(key);
		assertNotNull(value);
		System.out.println(key + ", " + value.getClass().getName() + ", " + value);
		if (value instanceof List) {
			List list = (List)value;
			List exList = (List)expected;
			assertEquals(list.size(), exList.size());
			for (int i=0; i<list.size(); i++) {
				Object o = list.get(i);
				Object o2 = exList.get(i);
				System.out.println("array: " + o.getClass().getName() + ", " + o);
				assertEquals(o, o2);
			}
		} else {
			assertEquals(expected, value);
		}
	}
}
