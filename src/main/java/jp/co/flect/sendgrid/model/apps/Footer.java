package jp.co.flect.sendgrid.model.apps;

import jp.co.flect.sendgrid.model.App;

public class Footer extends FilterSettings {

	public Footer() {
		super();
	}

	public Footer(App app) {
		super(app);
	}

	public void setTextHtml(String value) {
		app.setSetting("text/html", value);
	}

	public String getTextHtml() {
		return app.getSettingAsString("text_html");
	}

	public void setTextPlain(String value) {
		app.setSetting("text/plain", value);
	}

	public String getTextPlain() {
		return app.getSettingAsString("text_plain");
	}
}
