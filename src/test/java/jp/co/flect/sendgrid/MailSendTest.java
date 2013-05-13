package jp.co.flect.sendgrid;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import org.junit.Test;
import org.junit.BeforeClass;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;
import java.util.Arrays;
import java.util.List;
import java.util.Date;
import java.util.Map;
import java.util.HashMap;

import jp.co.flect.sendgrid.model.InvalidEmail;
import jp.co.flect.sendgrid.model.WebMail;

import static jp.co.flect.sendgrid.SendGridTest.USERNAME;
import static jp.co.flect.sendgrid.SendGridTest.PASSWORD;
import static jp.co.flect.sendgrid.SendGridTest.MAIL_FROM;
import static jp.co.flect.sendgrid.SendGridTest.MAIL_TO;

public class MailSendTest {
	
	@Test
	public void sendMail() throws Exception {
		Date now = new Date();
		SendGridClient client = new SendGridClient(USERNAME, PASSWORD);
		WebMail mail = new WebMail();
		mail.setFrom(MAIL_FROM);
		mail.setToList(Arrays.asList(MAIL_TO, MAIL_FROM));
		mail.setFromName("テスト From");
		mail.setSubject("テストメール: " + now);
		mail.setText("てすと\nてすと");
		mail.setHtml("<div>ほげ<br>ふが</div>");
		mail.setCategory("test");
		
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("x-test", "test123");
		mail.setHeaders(headers);
		
		mail.setDate(now);
		assertEquals(now.getTime() / 1000, mail.getDate().getTime() / 1000);//Ignore milliseconds
		
		mail.setReplyTo("noreply@flect.co.jp");
		
		client.mail(mail);
	}
	
	@Test
	public void attachement() throws Exception {
		Date now = new Date();
		SendGridClient client = new SendGridClient(USERNAME, PASSWORD);
		WebMail mail = new WebMail();
		mail.setFrom(MAIL_FROM);
		mail.setTo(MAIL_TO);
		mail.setFromName("テスト From");
		mail.setToName("テスト To");
		mail.setSubject("添付ファイルテスト: " + now);
		mail.setText("添付ファイルテスト\nてすと\n\u2650\u2764\u270f\u2708" + new String(Character.toChars(0x1F419)));
		
		File f1 = new File("testdata/test.zip");
		File f2 = new File("testdata/test.txt");
		
		client.mail(mail, f1, f2);
	}
	
	@Test
	public void cidTest() throws Exception {
		Date now = new Date();
		SendGridClient client = new SendGridClient(USERNAME, PASSWORD);
		WebMail mail = new WebMail();
		mail.setFrom(MAIL_FROM);
		mail.setTo(MAIL_TO);
		mail.setFromName("テスト From");
		mail.setToName("テスト To");
		mail.setSubject("CIDテスト: " + now);
		mail.setHtml("<div>てすと<img src='cid:ii_139db99fdb5c3704'/>てすと</div>");
		
		Map<String, String> content = new HashMap<String, String>();
		content.put("favicon.png", "ii_139db99fdb5c3704");
		mail.setContent(content);
		
		File f1 = new File("testdata/favicon.png");
		
		client.mail(mail, f1);
	}
}
