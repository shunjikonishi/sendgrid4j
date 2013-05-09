package jp.co.flect.sendgrid.transport;

import java.io.UnsupportedEncodingException;
import javax.mail.internet.MimeUtility;

public class TransportUtils {
	
	/**
	 * encode string with RFC2047
	 */
	public static String encodeText(String str) {
		//ToDo remove JavaMail dependency.
		try {
			return MimeUtility.encodeText(str, "utf-8", "B");
		} catch (UnsupportedEncodingException e) {
			throw new IllegalStateException(e);
		}
	}
}
