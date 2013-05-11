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

import jp.co.flect.sendgrid.model.Statistic;

import static jp.co.flect.sendgrid.SendGridTest.USERNAME;
import static jp.co.flect.sendgrid.SendGridTest.PASSWORD;
import static jp.co.flect.sendgrid.SendGridTest.MAIL_FROM;
import static jp.co.flect.sendgrid.SendGridTest.MAIL_TO;

public class StatisticTest {
	
	@Test
	public void statistics() throws Exception {
		SendGridClient client = new SendGridClient(USERNAME, PASSWORD);
		Statistic.Get request = new Statistic.Get();
		//request.setCategory("test");
		request.setDays(10);
		List<Statistic> list = client.getStatistics(request);
		assertTrue(list.size() > 0);
		for (Statistic stat : list) {
			System.out.println("Statistics: " + 
				stat.getDate() + ", " +
				"Category = " + stat.getCategory() + ", " +
				"Requests = " + stat.getRequests() + ", " +
				"Bounces = " + stat.getBounces() + ", " +
				"Clicks = " + stat.getClicks() + ", " +
				"Opens = " + stat.getOpens() + ", " +
				"SpamReports = " + stat.getSpamReports() + ", " +
				"UniqueClicks = " + stat.getUniqueClicks() + ", " +
				"UniqueOpens = " + stat.getUniqueOpens() + ", " +
				"Blocked = " + stat.getBlocked() + ", " +
				"Delivered = " + stat.getDelivered() + ", " +
				"Unsubscribes = " + stat.getUnsubscribes() + ", " +
				"InvalidEmails = " + stat.getInvalidEmails() + ", " +
				"RepeatUnsubscribes = " + stat.getRepeatUnsubscribes() + ", " +
				"SpamDrops = " + stat.getSpamDrops() + ", " +
				"RepeatBounces = " + stat.getRepeatBounces() + ", " +
				"RepeatSpamReports = " + stat.getRepeatSpamReports() + ", " +
				"");
		}
	}
	
	@Test
	public void categoryList() throws Exception {
		SendGridClient client = new SendGridClient(USERNAME, PASSWORD);
		List<String> list = client.getCategoryList();
		assertTrue(list.size() > 0);
		
		boolean bTest = false;
		for (String s : list) {
			if (s.equals("test")) {
				bTest = true;
			}
		}
		assertTrue(bTest);
	}
	
}
