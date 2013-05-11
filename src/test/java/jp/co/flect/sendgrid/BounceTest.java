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

import jp.co.flect.sendgrid.model.Bounce;
import jp.co.flect.sendgrid.model.WebMail;

import static jp.co.flect.sendgrid.SendGridTest.USERNAME;
import static jp.co.flect.sendgrid.SendGridTest.PASSWORD;
import static jp.co.flect.sendgrid.SendGridTest.MAIL_FROM;
import static jp.co.flect.sendgrid.SendGridTest.MAIL_TO;
import static jp.co.flect.sendgrid.SendGridTest.BOUNCE_FROM;
import static jp.co.flect.sendgrid.SendGridTest.BOUNCE_TO;

public class BounceTest {
	
	public static WebMail createBounceMail() {
		WebMail mail = new WebMail();
		mail.setFrom(BOUNCE_FROM);
		mail.setTo(BOUNCE_TO);
		mail.setSubject("Bounce Test");
		mail.setText("test");
		return mail;
	}
	
	@Test
	public void test() throws Exception {
		SendGridClient client = new SendGridClient(USERNAME, PASSWORD);
		
		WebMail mail = createBounceMail();
		client.mail(mail);
		
		int count = 0;
		int tryCount = 30;
		while (tryCount > 0) {
			count = client.countBounces();
			if (count == 0) {
				tryCount--;
				if (tryCount == 0) {
					fail();
				}
				Thread.sleep(3000);
			} else {
				break;
			}
		}
		
		Bounce.Get request = new Bounce.Get();
		boolean bThere = false;
		List<Bounce> list = client.getBounces(request);
		assertEquals(count, list.size());
		for (Bounce b : list) {
			if (b.getEmail().equals(BOUNCE_TO)) {
				bThere = true;
			}
		}
		assertTrue(bThere);
		
		Bounce.Delete request2 = new Bounce.Delete();
		request2.setEmail(mail.getTo());
		client.deleteBounces(request2);
	}
	
}
