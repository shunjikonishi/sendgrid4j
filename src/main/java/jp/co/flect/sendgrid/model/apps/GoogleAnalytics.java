package jp.co.flect.sendgrid.model.apps;

import jp.co.flect.sendgrid.model.App;

public class GoogleAnalytics extends FilterSettings {

	public GoogleAnalytics() {
		super();
	}

	public GoogleAnalytics(App app) {
		super(app);
	}

	public void setUtmSource(String value) {
		app.setSetting("utm_source", value);
	}

	public String getUtmSource() {
		return app.getSettingAsString("utm_source");
	}

	public void setUtfMedium(String value) {
		app.setSetting("utm_medium", value);
	}

	public String getUtmMedium() {
		return app.getSettingAsString("utm_medium");
	}

	public void setUtmCampaign(String value) {
		app.setSetting("utm_campaign", value);
	}

	public String getUtmCampaign() {
		return app.getSettingAsString("utm_campaign");
	}

	public void setUtfTerm(String value) {
		app.setSetting("utm_term", value);
	}

	public String getUtmTerm() {
		return app.getSettingAsString("utm_term");
	}

	public void setUtmContent(String value) {
		app.setSetting("utm_content", value);
	}

	public String getUtmContent() {
		return app.getSettingAsString("utm_content");
	}

}
