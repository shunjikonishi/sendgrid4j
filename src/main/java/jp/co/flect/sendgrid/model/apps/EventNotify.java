package jp.co.flect.sendgrid.model.apps;

import jp.co.flect.sendgrid.model.App;

public class EventNotify extends FilterSettings {

	public EventNotify() {
		super();
	}

	public EventNotify(App app) {
		super(app);
	}

	public void setEnableProcessed(boolean value) {
		String s = value ? "1" : "0";
		app.setSetting("processed", s);
	}

	public boolean isEnableProcessed() {
		String s = app.getSettingAsString("processed");
		return s == null || s.equals("0") ? false : true;
	}

	public void setEnableDropped(boolean value) {
		String s = value ? "1" : "0";
		app.setSetting("dropped", s);
	}

	public boolean isEnableDropped() {
		String s = app.getSettingAsString("dropped");
		return s == null || s.equals("0") ? false : true;
	}

	public void setEnableDeferred(boolean value) {
		String s = value ? "1" : "0";
		app.setSetting("deferred", s);
	}

	public boolean isEnableDeferred() {
		String s = app.getSettingAsString("deferred");
		return s == null || s.equals("0") ? false : true;
	}

	public void setEnableDelivered(boolean value) {
		String s = value ? "1" : "0";
		app.setSetting("delivered", s);
	}

	public boolean isEnableDelivered() {
		String s = app.getSettingAsString("delivered");
		return s == null || s.equals("0") ? false : true;
	}

	public void setEnableBounce(boolean value) {
		String s = value ? "1" : "0";
		app.setSetting("bounce", s);
	}

	public boolean isEnableBounce() {
		String s = app.getSettingAsString("bounce");
		return s == null || s.equals("0") ? false : true;
	}

	public void setEnableClick(boolean value) {
		String s = value ? "1" : "0";
		app.setSetting("click", s);
	}

	public boolean isEnableClick() {
		String s = app.getSettingAsString("click");
		return s == null || s.equals("0") ? false : true;
	}

	public void setEnableOpen(boolean value) {
		String s = value ? "1" : "0";
		app.setSetting("open", s);
	}

	public boolean isEnableOpen() {
		String s = app.getSettingAsString("open");
		return s == null || s.equals("0") ? false : true;
	}

	public void setEnableUnsubscribe(boolean value) {
		String s = value ? "1" : "0";
		app.setSetting("unsubscribe", s);
	}

	public boolean isEnableUnsubscribe() {
		String s = app.getSettingAsString("unsubscribe");
		return s == null || s.equals("0") ? false : true;
	}

	public void setEnableSpamreport(boolean value) {
		String s = value ? "1" : "0";
		app.setSetting("spamreport", s);
	}

	public boolean isEnableSpamreport() {
		String s = app.getSettingAsString("spamreport");
		return s == null || s.equals("0") ? false : true;
	}

	public void setUrl(String value) {
		app.setSetting("url", value);
	}

	public String getUrl() {
		return app.getSettingAsString("url");
	}

	public void setVersion(int value) {
		app.setSetting("version", value);
	}

	public int getVersion() {
		String s = app.getSettingAsString("version");
		return s == null ? 0 : Integer.parseInt(s);
	}
}
