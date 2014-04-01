package jp.co.flect.sendgrid;

import static jp.co.flect.sendgrid.SendGridTest.MAIL_FROM;
import static jp.co.flect.sendgrid.SendGridTest.MAIL_TO;
import static jp.co.flect.sendgrid.SendGridTest.PASSWORD;
import static jp.co.flect.sendgrid.SendGridTest.USERNAME;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import jp.co.flect.sendgrid.filter.Dkim;
import jp.co.flect.sendgrid.filter.DomainKeys;
import jp.co.flect.sendgrid.filter.EventNotify;
import jp.co.flect.sendgrid.filter.Footer;
import jp.co.flect.sendgrid.filter.GoogleAnalytics;
import jp.co.flect.sendgrid.model.App;

import org.junit.Test;

public class AppTest {
	
	@Test
	public void getAvailableApps() throws Exception {
		SendGridClient client = new SendGridClient(USERNAME, PASSWORD);
		
		List<App> list = client.getAvailableApps();
		boolean b = false;
		for (App app : list) {
			if ("dkim".equals(app.getName())) {
				b = true;
				assertEquals("DKIM", app.getTitle());
				assertTrue(app.isActivated());
				assertNotNull(app.getDescription());
			}
		}
		assertTrue(b);
	}
	
	@Test
	public void activate() throws Exception {
		final String g = "gravatar";
		
		SendGridClient client = new SendGridClient(USERNAME, PASSWORD);
		assertFalse(isActivated(client, g));
		
		client.activateApp(g);
		assertTrue(isActivated(client, g));
		
		client.deactivateApp(g);
		assertFalse(isActivated(client, g));
	}
	
	private boolean isActivated(SendGridClient client, String name) throws Exception {
		List<App> list = client.getAvailableApps();
		Boolean b = null;
		for (App app : list) {
			if (name.equals(app.getName())) {
				b = app.isActivated();;
			}
		}
		assertNotNull(b);
		return b;
	}
	
	@Test
	public void whitelist() throws Exception {
		SendGridClient client = new SendGridClient(USERNAME, PASSWORD);
		List<String> list = client.getAddressWhilteList();
		assertEquals(2, list.size());
		assertEquals("test@flect.co.jp", list.get(0));
		assertEquals("hoge@flect.co.jp", list.get(1));
		
		client.setAddressWhiteList(Arrays.asList("test@flect.co.jp", "hoge@flect.co.jp", "fuga@flect.co.jp"));
		list = client.getAddressWhilteList();
		assertEquals(3, list.size());
		assertEquals("test@flect.co.jp", list.get(0));
		assertEquals("hoge@flect.co.jp", list.get(1));
		assertEquals("fuga@flect.co.jp", list.get(2));
		
		client.setAddressWhiteList(Arrays.asList("test@flect.co.jp", "hoge@flect.co.jp"));
	}
	
	@Test
	public void bcc() throws Exception {
		SendGridClient client = new SendGridClient(USERNAME, PASSWORD);
		
		String bcc = client.getBcc();
		assertEquals(MAIL_FROM, bcc);
		
		client.setBcc(MAIL_TO);
		bcc = client.getBcc();
		assertEquals(MAIL_TO, bcc);
		
		client.setBcc(MAIL_FROM);
	}
	
	@Test
	public void clicktrack() throws Exception {
		SendGridClient client = new SendGridClient(USERNAME, PASSWORD);
		
		boolean isEnableText = client.getClickTrack();

		client.setClickTrack(true);
		isEnableText = client.getClickTrack();
		assertEquals(true, isEnableText);

		client.setClickTrack(false);
		isEnableText = client.getClickTrack();
		assertEquals(false, isEnableText);
	}
	
	@Test
	public void domainkeys() throws Exception {
		SendGridClient client = new SendGridClient(USERNAME, PASSWORD);
		
		DomainKeys domainKeys = new DomainKeys(
				"google.com", false);
		client.setDomainKeys(domainKeys);
		domainKeys = client.getDomainKeys();
		assertEquals("google.com", domainKeys.getDomain());
		assertEquals(false, domainKeys.isEnableInsertSender());
		
		domainKeys = new DomainKeys(
				"yahoo.com", true);
		client.setDomainKeys(domainKeys);
		domainKeys = client.getDomainKeys();
		assertEquals("yahoo.com", domainKeys.getDomain());
		assertEquals(true, domainKeys.isEnableInsertSender());
	}
	
	@Test
	public void dkim() throws Exception {
		SendGridClient client = new SendGridClient(USERNAME, PASSWORD);
		
		Dkim dkim = new Dkim(
				"google.com", false);
		client.setDkim(dkim);
		dkim = client.getDkim();
		assertEquals("google.com", dkim.getDomain());
		assertEquals(false, dkim.isEnableUseFrom());
		
		dkim = new Dkim(
				"yahoo.com", true);
		client.setDkim(dkim);
		dkim = client.getDkim();
		assertEquals("yahoo.com", dkim.getDomain());
		assertEquals(true, dkim.isEnableUseFrom());
	}

	@Test
	public void eventnotify() throws Exception {
		SendGridClient client = new SendGridClient(USERNAME, PASSWORD);
		
		EventNotify eventNotify = new EventNotify(
				false, true, false, true, false, true, false, true, false, "http://www.google.com/"
				);
		client.setEventNotify(eventNotify);
		eventNotify = client.getEventNotify();
		assertEquals(false, eventNotify.isEnableProcessed());
		assertEquals(true, eventNotify.isEnableDropped());
		assertEquals(false, eventNotify.isEnableDeferred());
		assertEquals(true, eventNotify.isEnableDelivered());
		assertEquals(false, eventNotify.isEnableBounce());
		assertEquals(true, eventNotify.isEnableClick());
		assertEquals(false, eventNotify.isEnableOpen());
		assertEquals(true, eventNotify.isEnableUnsubscribe());
		assertEquals(false, eventNotify.isEnableSpamreport());
		assertEquals("http://www.google.com/", eventNotify.getUrl());
		assertEquals(3, eventNotify.getVersion());
		
		eventNotify = new EventNotify(
				true, false, true, false, true, false, true, false, true, "https://www.yahoo.com/"
				);
		client.setEventNotify(eventNotify);
		eventNotify = client.getEventNotify();
		assertEquals(true, eventNotify.isEnableProcessed());
		assertEquals(false, eventNotify.isEnableDropped());
		assertEquals(true, eventNotify.isEnableDeferred());
		assertEquals(false, eventNotify.isEnableDelivered());
		assertEquals(true, eventNotify.isEnableBounce());
		assertEquals(false, eventNotify.isEnableClick());
		assertEquals(true, eventNotify.isEnableOpen());
		assertEquals(false, eventNotify.isEnableUnsubscribe());
		assertEquals(true, eventNotify.isEnableSpamreport());
		assertEquals("https://www.yahoo.com/", eventNotify.getUrl());
		assertEquals(3, eventNotify.getVersion());
	}
	
	@Test
	public void footer() throws Exception {
		SendGridClient client = new SendGridClient(USERNAME, PASSWORD);
		
		Footer footer = new Footer(
				"<div>フッタ</div>", "フッタ");
		client.setFooter(footer);
		footer = client.getFooter();
		assertEquals("<div>フッタ</div>", footer.getTextHtml());
		assertEquals("フッタ", footer.getTextPlain());
	}
	
	@Test
	public void ganalytics() throws Exception {
		SendGridClient client = new SendGridClient(USERNAME, PASSWORD);
		
		GoogleAnalytics ganalytics = new GoogleAnalytics(
				"Transactional Email", "email", "Redesigned Transaction", "Health", "PageB");
		client.setGoogleAnalytics(ganalytics);
		ganalytics = client.getGoogleAnalytics();
		assertEquals("Transactional Email", ganalytics.getUtmSource());
		assertEquals("email", ganalytics.getUtmMedium());
		assertEquals("Redesigned Transaction", ganalytics.getUtmCampaign());
		assertEquals("Health", ganalytics.getUtmTerm());
		assertEquals("PageB", ganalytics.getUtmContent());
	}
	
}
