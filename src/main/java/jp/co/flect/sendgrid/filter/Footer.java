package jp.co.flect.sendgrid.filter;

public class Footer {

	public Footer(String textHtml, String textPlain) {
		this.textHtml = textHtml;
		this.textPlain = textPlain;
	}

	private String textHtml;

	public String getTextHtml() {
		return textHtml;
	}

	private String textPlain;

	public String getTextPlain() {
		return textPlain;
	}
}
