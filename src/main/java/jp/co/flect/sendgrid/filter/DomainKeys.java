package jp.co.flect.sendgrid.filter;

public class DomainKeys {

	public DomainKeys(String domain, boolean enableInsertSender) {
		this.domain = domain;
		this.enableInsertSender = enableInsertSender;
	}

	private String domain;

	public String getDomain() {
		return domain;
	}

	private boolean enableInsertSender;

	public boolean isEnableInsertSender() {
		return enableInsertSender;
	}
}
