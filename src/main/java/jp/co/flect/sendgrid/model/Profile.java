package jp.co.flect.sendgrid.model;

import java.util.Map;
import java.util.HashMap;

public class Profile extends AbstractModel {
	
	public Profile(Map<String, Object> map) {
		super(map);
	}
	
	public String getUserName() { return doGetString("username");}
	public String getEmail() { return doGetString("email");}
	
	public boolean isActive() { return doGetBoolean("active");}
	public boolean isWebSiteAccess() { return doGetBoolean("website_access");}
	
	public String getFirstName() { return doGetString("first_name");}
	public void setFirstName(String s) { doSetString("first_name", s);}
	
	public String getLastName() { return doGetString("last_name");}
	public void setLastName(String s) { doSetString("last_name", s);}
	
	public String getAddress() { return doGetString("address");}
	public void setAddress(String s) { doSetString("address", s);}
	
	public String getAddress2() { return doGetString("address2");}
	public void setAddress2(String s) { doSetString("address2", s);}
	
	public String getCity() { return doGetString("city");}
	public void setCity(String s) { doSetString("city", s);}
	
	public String getState() { return doGetString("state");}
	public void setState(String s) { doSetString("state", s);}
	
	public String getZip() { return doGetString("zip");}
	public void setZip(String s) { doSetString("zip", s);}
	
	public String getCountry() { return doGetString("country");}
	public void setCountry(String s) { doSetString("country", s);}
	
	public String getPhone() { return doGetString("phone");}
	public void setPhone(String s) { doSetString("phone", s);}
	
	public String getWebSite() { return doGetString("website");}
	public void setWebSite(String s) { doSetString("website", s);}
	
	public Map<String, String> getParameters() {
		Map<String, String> map = new HashMap<String, String>();
		toParametersMap("first_name", map);
		toParametersMap("last_name", map);
		toParametersMap("address", map);
		toParametersMap("address2", map);
		toParametersMap("city", map);
		toParametersMap("state", map);
		toParametersMap("zip", map);
		toParametersMap("country", map);
		toParametersMap("phone", map);
		toParametersMap("website", map);
		return map;
	}
	
	private void toParametersMap(String key, Map<String, String> map) {
		String s = doGetString(key);
		if (s != null) {
			map.put(key, s);
		}
	}
}
