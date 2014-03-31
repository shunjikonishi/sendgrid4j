package jp.co.flect.sendgrid.filter;

public class Dkim {

	public Dkim(String domain, boolean enableUseFrom) {
		this.domain = domain;
		this.enableUseFrom = enableUseFrom;
	}

	private String domain;

	public String getDomain() {
		return domain;
	}

	private boolean enableUseFrom;

	public boolean isEnableUseFrom() {
		return enableUseFrom;
	}
}
