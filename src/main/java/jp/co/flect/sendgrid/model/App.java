package jp.co.flect.sendgrid.model;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class App extends AbstractModel {
	
	private Map<String, Object> settings = new HashMap<String, Object>();
	
	private static Map<String, Object> createMap(String name) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", name);
		return map;
	}
	
	public App(Map<String, Object> map) {
		super(map);
	}
	
	public App(String name) {
		super(createMap(name));
	}
	
	public App(String name, Map<String, Object> settings) {
		super(createMap(name));
		this.settings.putAll(settings);
	}
	
	public String getName() { return doGetString("name");}
	public String getTitle() { return doGetString("title");}
	public String getDescription() { return doGetString("description");}
	public boolean isActivated() { return doGetBoolean("activated");}
	
	public Map<String, Object> getSettings() { return this.settings;}
	public void setSetting(String name, Object value) { this.settings.put(name, value);}
	
	public String getSettingAsString(String name) { return (String)this.settings.get(name);}
	public int getSettingAsInt(String name) { return (Integer)this.settings.get(name);}
	public boolean getSettingAsBoolean(String name) { return (Boolean)this.settings.get(name);}
	public List<String> getSettingAsList(String name) { return (List<String>)this.settings.get(name);}
	public double getSettingAsDouble(String name) {return (Double)this.settings.get(name);}
}
