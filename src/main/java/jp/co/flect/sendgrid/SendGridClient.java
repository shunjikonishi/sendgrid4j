package jp.co.flect.sendgrid;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.co.flect.sendgrid.json.JsonUtils;
import jp.co.flect.sendgrid.model.AbstractRequest;
import jp.co.flect.sendgrid.model.App;
import jp.co.flect.sendgrid.model.Block;
import jp.co.flect.sendgrid.model.Bounce;
import jp.co.flect.sendgrid.model.CommonRequest;
import jp.co.flect.sendgrid.model.InvalidEmail;
import jp.co.flect.sendgrid.model.Profile;
import jp.co.flect.sendgrid.model.SpamReport;
import jp.co.flect.sendgrid.model.Statistic;
import jp.co.flect.sendgrid.model.Unsubscribe;
import jp.co.flect.sendgrid.model.WebMail;
import jp.co.flect.sendgrid.model.apps.Dkim;
import jp.co.flect.sendgrid.model.apps.DomainKeys;
import jp.co.flect.sendgrid.model.apps.EventNotify;
import jp.co.flect.sendgrid.model.apps.Footer;
import jp.co.flect.sendgrid.model.apps.GoogleAnalytics;
import jp.co.flect.sendgrid.model.apps.SpamCheck;
import jp.co.flect.sendgrid.transport.HttpClientTransport;
import jp.co.flect.sendgrid.transport.Transport;
import jp.co.flect.sendgrid.transport.TransportFactory;
import jp.co.flect.sendgrid.transport.TransportUtils;

public class SendGridClient {
	
	private String username;
	private String apikey;
	private Transport transport;
	
	private String baseUrl = "https://api.sendgrid.com/api";
	
	public SendGridClient(String username, String apikey) {
		this.username = username;
		this.apikey = apikey;
		this.transport = createDefaultTransport();
	}
	
	protected Transport createDefaultTransport() {
		return TransportFactory.createDefaultTransport();
	}
	
	public String getBaseUrl() { return this.baseUrl;}
	public void setBaseUrl(String url) { this.baseUrl = url;}
	
	public Transport getTransport() { return this.transport;}
	public void setTransport(Transport t) { this.transport = t;}
	
	private String doRequest(String path, AbstractRequest request) throws IOException, SendGridException {
		Map<String, String[]> map = request.getParams();
		map.put("api_user", new String[] { this.username});
		map.put("api_key", new String[] { this.apikey});
		
		return this.transport.send(this.baseUrl + path, map);
	}
	
	private Map<String, Object> checkResponse(String json) throws SendGridException {
		Map<String, Object> map = JsonUtils.parse(json);
		if (map.get("error") != null || map.get("errors") != null) {
			throw new SendGridException(map);
		}
		String msg = (String)map.get("message");
		if (!"success".equals(msg)) {
			throw new SendGridException(msg == null ? json : msg);
		}
		return map;
	}
	
	//Blocks
	public List<Block> getBlocks(Block.Get request) throws IOException, SendGridException {
		String json = doRequest("/blocks.get.json", request);
		List<Map<String, Object>> list = JsonUtils.parseArray(json);
		List<Block> ret = new ArrayList<Block>();
		for (Map<String, Object> map : list) {
			ret.add(new Block(map));
		}
		return ret;
	}
	
	public void deleteBlocks(Block.Delete request) throws IOException, SendGridException {
		String json = doRequest("/blocks.delete.json", request);
		checkResponse(json);
	}
	
	//Bounce
	public List<Bounce> getBounces(Bounce.Get request) throws IOException, SendGridException {
		String json = doRequest("/bounces.get.json", request);
		List<Map<String, Object>> list = JsonUtils.parseArray(json);
		List<Bounce> ret = new ArrayList<Bounce>();
		for (Map<String, Object> map : list) {
			ret.add(new Bounce(map));
		}
		return ret;
	}
	
	public void deleteBounces(Bounce.Delete request) throws IOException, SendGridException {
		String json = doRequest("/bounces.delete.json", request);
		checkResponse(json);
	}
	
	public int countBounces() throws IOException, SendGridException {
		return countBounces(new Bounce.Count());
	}
	
	public int countBounces(Bounce.Count request) throws IOException, SendGridException {
		String json = doRequest("/bounces.count.json", request);
		Map<String, Object> map = JsonUtils.parse(json);
		Double count = (Double)map.get("count");
		if (count == null) {
			throw new SendGridException(json);
		}
		return count.intValue();
	}
	
	//Filter commands
	public List<App> getAvailableApps() throws IOException, SendGridException {
		String json = doRequest("/filter.getavailable.json", new CommonRequest());
		List<Map<String, Object>> list = JsonUtils.parseArray(json);
		List<App> ret = new ArrayList<App>();
		for (Map<String, Object> map : list) {
			ret.add(new App(map));
		}
		return ret;
	}
	
	public void activateApp(String name) throws IOException, SendGridException {
		CommonRequest request = new CommonRequest();
		request.set("name", name);
		String json = doRequest("/filter.activate.json", request);
		checkResponse(json);
	}
	
	public void deactivateApp(String name) throws IOException, SendGridException {
		CommonRequest request = new CommonRequest();
		request.set("name", name);
		String json = doRequest("/filter.deactivate.json", request);
		checkResponse(json);
	}
	
	public void setupApp(App app) throws IOException, SendGridException {
		CommonRequest request = new CommonRequest();
		request.set("name", app.getName());
		for (Map.Entry<String, Object> entry : app.getSettings().entrySet()) {
			String key = entry.getKey();
			Object value = entry.getValue();
			if (value == null) {
				request.set(key, (String)null);
			} else if (value instanceof List) {
				request.set(key, (List<String>)value);
			} else {
				request.set(key, value.toString());
			}
		}
		String json = doRequest("/filter.setup.json", request);
		checkResponse(json);
	}
	
	public App getAppSettings(String name) throws IOException, SendGridException {
		CommonRequest request = new CommonRequest();
		request.set("name", name);
		String json = doRequest("/filter.getsettings.json", request);
		Map<String, Object> map = JsonUtils.parse(json);
		if (!map.containsKey("settings")) {
			throw new SendGridException(map);
		}
		Map<String, Object> settings = (Map<String, Object>)map.get("settings");
		return new App(name, settings);
	}
	
	//Individual apps
	public List<String> getAddressWhilteList() throws IOException, SendGridException {
		App app = getAppSettings("addresswhitelist");
		return app.getSettingAsList("list");
	}
	
	public void setAddressWhiteList(List<String> list) throws IOException, SendGridException {
		Map<String, Object> settings = new HashMap<String, Object>();
		settings.put("list", list);
		App app = new App("addresswhitelist", settings);
		setupApp(app);
	}
	
	public String getBcc() throws IOException, SendGridException {
		App app = getAppSettings("bcc");
		String ret = app.getSettingAsString("email");
		return ret == null || ret.length() == 0 ? null : ret;
	}
	
	public void setBcc(String bcc) throws IOException, SendGridException {
		Map<String, Object> settings = new HashMap<String, Object>();
		settings.put("email", bcc);
		App app = new App("bcc", settings);
		setupApp(app);
	}
	
	public boolean getClickTrack() throws IOException, SendGridException {
		App app = getAppSettings("clicktrack");
		String ret = app.getSettingAsString("enable_text");
		return ret != null && ret.equals("1") ? true : false;
	}
	
	public void setClickTrack(boolean isEnableText) throws IOException, SendGridException {
		Map<String, Object> settings = new HashMap<String, Object>();
		settings.put("enable_text", isEnableText ? "1" : "null");
		App app = new App("clicktrack", settings);
		setupApp(app);
	}
	
	public DomainKeys getDomainKeys() throws IOException, SendGridException {
		App app = getAppSettings("domainkeys");
		return new DomainKeys(app);
	}
	
	public void setDomainKeys(DomainKeys domainKeys) throws IOException, SendGridException {
		App app = new App("domainkeys", domainKeys.getSettings());
		setupApp(app);
	}
	
	public Dkim getDkim() throws IOException, SendGridException {
		App app = getAppSettings("dkim");
		return new Dkim(app);
	}
	
	public void setDkim(Dkim dkim) throws IOException, SendGridException {
		App app = new App("dkim", dkim.getSettings());
		setupApp(app);
	}
	
	public EventNotify getEventNotify() throws IOException, SendGridException {
		App app = getAppSettings("eventnotify");
		return new EventNotify(app);
	}
	
	public void setEventNotify(EventNotify eventNotify) throws IOException, SendGridException {
		App app = new App("eventnotify", eventNotify.getSettings());
		setupApp(app);
	}
	
	public Footer getFooter() throws IOException, SendGridException {
		App app = getAppSettings("footer");
		return new Footer(app);
	}
	
	public void setFooter(Footer footer) throws IOException, SendGridException {
		App app = new App("footer", footer.getSettings());
		setupApp(app);
	}

	public GoogleAnalytics getGoogleAnalytics() throws IOException, SendGridException {
		App app = getAppSettings("ganalytics");
		return new GoogleAnalytics(app);
	}
	
	public void setGoogleAnalytics(GoogleAnalytics ganalytics) throws IOException, SendGridException {
		App app = new App("ganalytics", ganalytics.getSettings());
		setupApp(app);
	}

	public SpamCheck getSpamCheck() throws IOException, SendGridException {
		App app = getAppSettings("spamcheck");
		return new SpamCheck(app);
	}
	
	public void setSpamCheck(SpamCheck spamCheck) throws IOException, SendGridException {
		App app = new App("spamcheck", spamCheck.getSettings());
		setupApp(app);
	}

	//InvalidEmails
	public List<InvalidEmail> getInvalidEmails(InvalidEmail.Get request) throws IOException, SendGridException {
		String json = doRequest("/invalidemails.get.json", request);
		List<Map<String, Object>> list = JsonUtils.parseArray(json);
		List<InvalidEmail> ret = new ArrayList<InvalidEmail>();
		for (Map<String, Object> map : list) {
			ret.add(new InvalidEmail(map));
		}
		return ret;
	}
	
	public void deleteInvalidEmails(InvalidEmail.Delete request) throws IOException, SendGridException {
		String json = doRequest("/invalidemails.delete.json", request);
		checkResponse(json);
	}
	
	//Mail
	public void mail(WebMail mail, File... attachements) throws IOException, SendGridException {
		Map<String, String[]> params = mail.getParams();
		params.put("api_user", new String[] { this.username});
		params.put("api_key", new String[] { this.apikey});
		if (mail.getContent() != null) {
			for (Map.Entry<String, String> entry : mail.getContent().entrySet()) {
				String key = "content[" + TransportUtils.encodeText(entry.getKey()) + "]";
				String value = entry.getValue();
				params.put(key, new String[] { value });
			}
		}
		String xsmtp = mail.getXSmtpApiAsString();
		if (xsmtp != null) {
			params.put("x-smtpapi", new String[] { xsmtp});
		}
		checkResponse(this.transport.send(this.baseUrl + "/mail.send.json", params, attachements));
	}
	
	//Multiple Credentials   - NOT IMPLEMENTED
	//Parse WebHook Settings - NOT IMPLEMENTED
	
	//Profile
	public Profile getProfile() throws IOException, SendGridException {
		CommonRequest request = new CommonRequest();
		String json = doRequest("/profile.get.json", request);
		List<Map<String, Object>> list = JsonUtils.parseArray(json);
		if (list == null || list.size() != 1) {
			throw new IllegalStateException();
		}
		return new Profile(list.get(0));
	}
	
	public void setProfile(Profile profile) throws IOException, SendGridException {
		CommonRequest request = new CommonRequest();
		for (Map.Entry<String, String> entry : profile.getParameters().entrySet()) {
			request.set(entry.getKey(), entry.getValue());
		}
		String json = doRequest("/profile.set.json", request);
		checkResponse(json);
	}
	
	public void setPassword(String newPassword) throws IOException, SendGridException {
		CommonRequest request = new CommonRequest();
		request.set("password", newPassword);
		request.set("confirm_password", newPassword);
		String json = doRequest("/password.set.json", request);
		checkResponse(json);
		this.apikey = newPassword;
	}
	
	public void setUserName(String newUsername) throws IOException, SendGridException {
		CommonRequest request = new CommonRequest();
		request.set("username", newUsername);
		String json = doRequest("/profile.setUsername.json", request);
		checkResponse(json);
		this.username = newUsername;
	}
	
	public void setEmail(String newEmail) throws IOException, SendGridException {
		CommonRequest request = new CommonRequest();
		request.set("email", newEmail);
		String json = doRequest("/profile.setEmail.json", request);
		checkResponse(json);
	}
	
	//SpamReports
	public List<SpamReport> getSpamReports(SpamReport.Get request) throws IOException, SendGridException {
		String json = doRequest("/spamreports.get.json", request);
		List<Map<String, Object>> list = JsonUtils.parseArray(json);
		List<SpamReport> ret = new ArrayList<SpamReport>();
		for (Map<String, Object> map : list) {
			ret.add(new SpamReport(map));
		}
		return ret;
	}
	
	public void deleteSpamReports(SpamReport.Delete request) throws IOException, SendGridException {
		String json = doRequest("/spamreports.delete.json", request);
		checkResponse(json);
	}
	
	//Statistics
	public List<Statistic> getStatistics(Statistic.Get request) throws IOException, SendGridException {
		String json = doRequest("/stats.get.json", request);
		List<Map<String, Object>> list = JsonUtils.parseArray(json);
		List<Statistic> ret = new ArrayList<Statistic>();
		for (Map<String, Object> map : list) {
			ret.add(new Statistic(map));
		}
		return ret;
	}
	
	public List<String> getCategoryList() throws IOException, SendGridException {
		CommonRequest request = new CommonRequest();
		request.set("list", "true");
		String json = doRequest("/stats.get.json", request);
		List<Map<String, Object>> list = JsonUtils.parseArray(json);
		List<String> ret = new ArrayList<String>();
		for (Map<String, Object> map : list) {
			ret.add(map.get("category").toString());
		}
		return ret;
	}
	
	//Unsubscribles
	public List<Unsubscribe> getUnsubscribes(Unsubscribe.Get request) throws IOException, SendGridException {
		String json = doRequest("/unsubscribes.get.json", request);
		List<Map<String, Object>> list = JsonUtils.parseArray(json);
		List<Unsubscribe> ret = new ArrayList<Unsubscribe>();
		for (Map<String, Object> map : list) {
			ret.add(new Unsubscribe(map));
		}
		return ret;
	}
	
	public void deleteUnsubscribes(Unsubscribe.Delete request) throws IOException, SendGridException {
		String json = doRequest("/unsubscribes.delete.json", request);
		checkResponse(json);
	}
	
	public void addUnsubscribes(Unsubscribe.Add request) throws IOException, SendGridException {
		String json = doRequest("/unsubscribes.add.json", request);
		checkResponse(json);
	}
}
