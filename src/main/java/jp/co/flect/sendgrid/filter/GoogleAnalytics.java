package jp.co.flect.sendgrid.filter;

public class GoogleAnalytics {

	public GoogleAnalytics(String utmSource, String utmMedium,
			String utmCampaign, String utmTerm, String utmContent) {
		this.utmSource = utmSource;
		this.utmMedium = utmMedium;
		this.utmCampaign = utmCampaign;
		this.utmTerm = utmTerm;
		this.utmContent = utmContent;
	}

	private String utmSource;

	public String getUtmSource() {
		return utmSource;
	}

	private String utmMedium;

	public String getUtmMedium() {
		return utmMedium;
	}

	private String utmCampaign;

	public String getUtmCampaign() {
		return utmCampaign;
	}

	private String utmTerm;

	public String getUtmTerm() {
		return utmTerm;
	}

	private String utmContent;

	public String getUtmContent() {
		return utmContent;
	}

}
