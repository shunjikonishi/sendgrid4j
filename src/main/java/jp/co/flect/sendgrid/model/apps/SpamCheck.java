package jp.co.flect.sendgrid.model.apps;

public class SpamCheck {

	public SpamCheck(String maxScore, String url) {
		this.maxScore = maxScore;
		this.url = url;
	}

	private String maxScore;

	public String getMaxScore() {
		return maxScore;
	}

	private String url;

	public String getUrl() {
		return url;
	}
}
