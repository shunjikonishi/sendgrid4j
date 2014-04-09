package jp.co.flect.sendgrid.model.apps;

import jp.co.flect.sendgrid.model.App;

public class DomainKeys extends FilterSettings {

	public DomainKeys() {
		super();
	}

	public DomainKeys(App app) {
		super(app);
	}

	// TODO This implement does not support Empty domain yet.
	// When the parameter sender==1 && domain==null,
	// You will got error:
	// "You have to fill domain or enable "use from" option."
	// private String domain;
	public void setDomain(String value) {
		app.setSetting("domain", value);
	}

	public String getDomain() {
		return app.getSettingAsString("domain");
	}

	public void setEnableInsertSender(boolean value) {
		String s = value ? "1" : "0";
		app.setSetting("sender", s);
	}

	public boolean isEnableInsertSender() {
		double d = app.getSettingAsDouble("sender");
		return (int) d == 1;
	}
}
