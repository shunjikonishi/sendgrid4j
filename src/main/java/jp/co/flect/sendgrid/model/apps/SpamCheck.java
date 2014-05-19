package jp.co.flect.sendgrid.model.apps;

import jp.co.flect.sendgrid.model.App;

public class SpamCheck extends FilterSettings {

	public SpamCheck() {
		super();
	}

	public SpamCheck(App app) {
		super(app);
	}

	public void setMaxScore(String value) {
		app.setSetting("max_score", value);
	}

	public String getMaxScore() {
		return app.getSettingAsString("max_score");
	}

	public void setUrl(String value) {
		app.setSetting("url", value);
	}

	public String getUrl() {
		return app.getSettingAsString("url");
	}
}
