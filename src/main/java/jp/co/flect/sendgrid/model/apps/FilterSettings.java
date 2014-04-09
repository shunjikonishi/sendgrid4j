package jp.co.flect.sendgrid.model.apps;

import java.util.Map;

import jp.co.flect.sendgrid.model.App;

public class FilterSettings {
	
	protected App app;

	public FilterSettings() {
		this.app = new App("");
	}
	
	public FilterSettings(App app) {
		if (app == null)
			throw new IllegalArgumentException(
					"The parameter app can not be null");
		this.app = app;
	}
	
	public Map<String, Object> getSettings() {
		return app.getSettings();
	}
}
