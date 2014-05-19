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

import jp.co.flect.sendgrid.model.App;
import jp.co.flect.sendgrid.model.apps.Dkim;
import jp.co.flect.sendgrid.model.apps.DomainKeys;
import jp.co.flect.sendgrid.model.apps.EventNotify;
import jp.co.flect.sendgrid.model.apps.Footer;
import jp.co.flect.sendgrid.model.apps.GoogleAnalytics;
import jp.co.flect.sendgrid.model.apps.SpamCheck;

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
		
		DomainKeys domainKeys = new DomainKeys();
		domainKeys.setDomain("google.com");
		domainKeys.setEnableInsertSender(false);
		client.setDomainKeys(domainKeys);
		domainKeys = client.getDomainKeys();
		assertEquals("google.com", domainKeys.getDomain());
		assertEquals(false, domainKeys.isEnableInsertSender());
		
		domainKeys = new DomainKeys();
		domainKeys.setDomain("yahoo.com");
		domainKeys.setEnableInsertSender(true);
		client.setDomainKeys(domainKeys);
		domainKeys = client.getDomainKeys();
		assertEquals("yahoo.com", domainKeys.getDomain());
		assertEquals(true, domainKeys.isEnableInsertSender());
	}
	
	@Test(expected=SendGridException.class)
	public void domainkeysEmptyDomain() throws Exception {
		SendGridClient client = new SendGridClient(USERNAME, PASSWORD);
		// TODO This implement does not support Empty domain yet.
		DomainKeys domainKeys = new DomainKeys();
		domainKeys.setEnableInsertSender(true);
		client.setDomainKeys(domainKeys);
	}
	
	@Test
	public void dkim() throws Exception {
		SendGridClient client = new SendGridClient(USERNAME, PASSWORD);
		
		Dkim dkim = new Dkim();
		dkim.setDomain("google.com");
		dkim.setEnableUseFrom(false);
		client.setDkim(dkim);
		dkim = client.getDkim();
		assertEquals("google.com", dkim.getDomain());
		assertEquals(false, dkim.isEnableUseFrom());
		
		dkim = new Dkim();
		dkim.setDomain("yahoo.com");
		dkim.setEnableUseFrom(true);
		client.setDkim(dkim);
		dkim = client.getDkim();
		assertEquals("yahoo.com", dkim.getDomain());
		assertEquals(true, dkim.isEnableUseFrom());
	}

	@Test(expected=SendGridException.class)
	public void dkimEmptyDomain() throws Exception {
		SendGridClient client = new SendGridClient(USERNAME, PASSWORD);
		Dkim dkim = new Dkim();
		client.setDkim(dkim);
	}
	
	@Test
	public void eventnotify() throws Exception {
		SendGridClient client = new SendGridClient(USERNAME, PASSWORD);
		
		EventNotify eventNotify = new EventNotify();
		eventNotify.setEnableProcessed(false);
		eventNotify.setEnableDropped(true);
		eventNotify.setEnableDeferred(false);
		eventNotify.setEnableDelivered(true);
		eventNotify.setEnableBounce(false);
		eventNotify.setEnableClick(true);
		eventNotify.setEnableOpen(false);
		eventNotify.setEnableUnsubscribe(true);
		eventNotify.setEnableSpamreport(false);
		eventNotify.setUrl("http://www.google.com/");

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
		
		eventNotify.setEnableProcessed(true);
		eventNotify.setEnableDropped(false);
		eventNotify.setEnableDeferred(true);
		eventNotify.setEnableDelivered(false);
		eventNotify.setEnableBounce(true);
		eventNotify.setEnableClick(false);
		eventNotify.setEnableOpen(true);
		eventNotify.setEnableUnsubscribe(false);
		eventNotify.setEnableSpamreport(true);
		eventNotify.setUrl("https://www.yahoo.com/");
		eventNotify.setVersion(3);

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
		
		eventNotify.setVersion(3);
		client.setEventNotify(eventNotify);
		eventNotify = client.getEventNotify();
		assertEquals(3, eventNotify.getVersion());
		
	}
	
	@Test(expected=SendGridException.class)
	public void eventnotifyOldVersion() throws Exception {
		SendGridClient client = new SendGridClient(USERNAME, PASSWORD);
		EventNotify eventNotify = new EventNotify();
		eventNotify.setUrl("https://www.yahoo.com/");
		eventNotify.setVersion(1);

		client.setEventNotify(eventNotify);
	}
	
	@Test(expected=SendGridException.class)
	public void eventnotifyEmptyUrl() throws Exception {
		SendGridClient client = new SendGridClient(USERNAME, PASSWORD);
		EventNotify eventNotify = new EventNotify();
		client.setEventNotify(eventNotify);
	}
	
	@Test
	public void footer() throws Exception {
		SendGridClient client = new SendGridClient(USERNAME, PASSWORD);
		
		Footer footer = new Footer();
		footer.setTextHtml("<div>フッタ</div>");
		footer.setTextPlain("フッタ");
		client.setFooter(footer);
		footer = client.getFooter();
		assertEquals("<div>フッタ</div>", footer.getTextHtml());
		assertEquals("フッタ", footer.getTextPlain());
		
		Footer footer2 = new Footer();
		footer2.setTextHtml("");
		footer2.setTextPlain("aa");
		client.setFooter(footer2);
		footer2 = client.getFooter();
		assertEquals("", footer2.getTextHtml());
		assertEquals("aa", footer2.getTextPlain());
		
		Footer footer3 = new Footer();
		footer3.setTextHtml("aa");
		footer3.setTextPlain("");
		client.setFooter(footer3);
		footer3 = client.getFooter();
		assertEquals("aa", footer3.getTextHtml());
		assertEquals("", footer3.getTextPlain());
	}
	
	@Test(expected=SendGridException.class)
	public void footerEmptyAll() throws Exception {
		SendGridClient client = new SendGridClient(USERNAME, PASSWORD);
		Footer footer = new Footer();
		client.setFooter(footer);
	}
	
	@Test
	public void ganalytics() throws Exception {
		SendGridClient client = new SendGridClient(USERNAME, PASSWORD);
		
		GoogleAnalytics ganalytics = new GoogleAnalytics();
		ganalytics.setUtmSource("Transactional Email");
		ganalytics.setUtfMedium("email");
		ganalytics.setUtmCampaign("Redesigned Transaction");
		ganalytics.setUtfTerm("Health");
		ganalytics.setUtmContent("PageB");
		client.setGoogleAnalytics(ganalytics);
		ganalytics = client.getGoogleAnalytics();
		assertEquals("Transactional Email", ganalytics.getUtmSource());
		assertEquals("email", ganalytics.getUtmMedium());
		assertEquals("Redesigned Transaction", ganalytics.getUtmCampaign());
		assertEquals("Health", ganalytics.getUtmTerm());
		assertEquals("PageB", ganalytics.getUtmContent());
	}
	
	@Test(expected=SendGridException.class)
	public void ganalyticsEmptyAll() throws Exception {
		SendGridClient client = new SendGridClient(USERNAME, PASSWORD);
		
		GoogleAnalytics ganalytics = new GoogleAnalytics();
		ganalytics.setUtmSource("");
		ganalytics.setUtfMedium("");
		ganalytics.setUtmCampaign("");
		ganalytics.setUtfTerm("");
		ganalytics.setUtmContent("");
		client.setGoogleAnalytics(ganalytics);
	}

	@Test
	public void spamcheck() throws Exception {
		SendGridClient client = new SendGridClient(USERNAME, PASSWORD);
		
		SpamCheck spamCheck = new SpamCheck();
		spamCheck.setMaxScore("1.2");
		spamCheck.setUrl("");
		client.setSpamCheck(spamCheck);
		spamCheck = client.getSpamCheck();
		assertEquals("1.2", spamCheck.getMaxScore());
		assertEquals("", spamCheck.getUrl());
		
		spamCheck = new SpamCheck();
		spamCheck.setMaxScore("0.0");
		spamCheck.setUrl("http://www.google.com/");
		client.setSpamCheck(spamCheck);
		spamCheck = client.getSpamCheck();
		assertEquals("0.0", spamCheck.getMaxScore());
		assertEquals("http://www.google.com/", spamCheck.getUrl());
	}
	
	@Test(expected=SendGridException.class)
	public void spamcheckEmptyAll() throws Exception {
		SendGridClient client = new SendGridClient(USERNAME, PASSWORD);
		
		SpamCheck spamCheck = new SpamCheck();
		client.setSpamCheck(spamCheck);
	}

}
