package jp.co.flect.sendgrid.model.apps;

import jp.co.flect.sendgrid.model.App;

public class Dkim extends FilterSettings {

	public Dkim() {
		super();
	}

	public Dkim(App app) {
		super(app);
	}

	public void setDomain(String value) {
		app.setSetting("domain", value);
	}

	public String getDomain() {
		return app.getSettingAsString("domain");
	}

	public void setEnableUseFrom(boolean value) {
		String s = value ? "1" : "0";
		app.setSetting("use_from", s);
	}

	public boolean isEnableUseFrom() {
		double d = app.getSettingAsDouble("use_from");
		return (int)d == 1;
	}

}
