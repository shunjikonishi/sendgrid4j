package jp.co.flect.sendgrid.model;

import java.util.Map;

public class App extends AbstractModel {
	
	public App(Map<String, Object> map) {
		super(map);
	}
	
	public String getName() { return doGetString("name");}
	public String getTitle() { return doGetString("title");}
	public String getDescription() { return doGetString("description");}
	public boolean isActivated() { return doGetBoolean("activated");}
}
