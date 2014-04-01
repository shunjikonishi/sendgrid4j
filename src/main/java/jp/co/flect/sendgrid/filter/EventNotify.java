package jp.co.flect.sendgrid.filter;

public class EventNotify {

	public EventNotify(boolean enableProcessed, boolean enableDropped,
			boolean enableDeferred, boolean enableDelivered,
			boolean enableBounce, boolean enableClick, boolean enableOpen,
			boolean enableUnsubscribe, boolean enableSpamreport, String url) {
		this.enableProcessed = enableProcessed;
		this.enableDropped = enableDropped;
		this.enableDeferred = enableDeferred;
		this.enableDelivered = enableDelivered;
		this.enableBounce = enableBounce;
		this.enableClick = enableClick;
		this.enableOpen = enableOpen;
		this.enableUnsubscribe = enableUnsubscribe;
		this.enableSpamreport = enableSpamreport;
		this.url = url;
	}

	private boolean enableProcessed;

	public boolean isEnableProcessed() {
		return enableProcessed;
	}

	private boolean enableDropped;

	public boolean isEnableDropped() {
		return enableDropped;
	}

	private boolean enableDeferred;

	public boolean isEnableDeferred() {
		return enableDeferred;
	}

	private boolean enableDelivered;

	public boolean isEnableDelivered() {
		return enableDelivered;
	}

	private boolean enableBounce;

	public boolean isEnableBounce() {
		return enableBounce;
	}

	private boolean enableClick;

	public boolean isEnableClick() {
		return enableClick;
	}

	private boolean enableOpen;

	public boolean isEnableOpen() {
		return enableOpen;
	}

	private boolean enableUnsubscribe;

	public boolean isEnableUnsubscribe() {
		return enableUnsubscribe;
	}

	private boolean enableSpamreport;

	public boolean isEnableSpamreport() {
		return enableSpamreport;
	}

	private String url;

	public String getUrl() {
		return url;
	}

	private int version = 3;

	public int getVersion() {
		return version;
	}
}
