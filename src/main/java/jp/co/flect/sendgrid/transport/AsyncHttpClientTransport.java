package jp.co.flect.sendgrid.transport;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import jp.co.flect.sendgrid.SendGridException;
import jp.co.flect.sendgrid.json.JsonUtils;

import com.ning.http.client.Response;
import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.AsyncHttpClientConfig;
import com.ning.http.client.ProxyServer;
import com.ning.http.multipart.StringPart;
import com.ning.http.multipart.FilePart;

public class AsyncHttpClientTransport implements Transport {
	
	private AsyncHttpClient client = null;
	private ProxyInfo proxyInfo = null;
	
	private int soTimeout = 0;
	private int connectionTimeout = 0;
	
	private String handleResponse(Response res) throws IOException, SendGridException {
		String body = res.getResponseBody("utf-8");
		
		if (res.getStatusCode() != 200) {
			if (body != null && body.length() > 0 && body.charAt(0) == '{') {
				Map<String, Object> map = null;
				try {
					map = JsonUtils.parse(body);
				} catch (Exception e) {
					e.printStackTrace();
				}
				if (map != null && (map.get("error") != null || map.get("errors") != null)) {
					throw new SendGridException(map);
				}
			}
			throw new SendGridException(res.getStatusText());
		}
		return body;
	}
	
	public String send(String url, Map<String, String[]> params, File... attachement) throws IOException, SendGridException {
		try {
			if (attachement == null || attachement.length == 0) {
				return simpleSend(url, params);
			} else {
				return multipartSend(url, params, attachement);
			}
		} catch (InterruptedException e) {
			throw new IOException(e);
		} catch (ExecutionException e) {
			throw new IOException(e);
		}
	}
	
	private String simpleSend(String url, Map<String, String[]> params) throws IOException, InterruptedException, ExecutionException, SendGridException {
		AsyncHttpClient client = getHttpClient();
		AsyncHttpClient.BoundRequestBuilder builder = client.preparePost(url);

		for (Map.Entry<String, String[]> entry : params.entrySet()) {
			String key = entry.getKey();
			if (entry.getValue().length > 1) {
				key += "[]";
			}
			for (String s : entry.getValue()) {
				builder.addParameter(key, s);
			}
		}
		Response res = builder.execute().get();
		return handleResponse(res);
	}
	
	public String multipartSend(String url, Map<String, String[]> params, File... attachement) throws IOException, InterruptedException, ExecutionException, SendGridException {
		AsyncHttpClient client = getHttpClient();
		AsyncHttpClient.BoundRequestBuilder builder = client.preparePost(url);

		for (Map.Entry<String, String[]> entry : params.entrySet()) {
			String key = entry.getKey();
			for (String s : entry.getValue()) {
				builder.addBodyPart(new StringPart(key, s, "utf-8"));
			}
		}
		for (File f : attachement) {
			String filename = TransportUtils.encodeText(f.getName());
			String key = "files[" + filename + "]";
			builder.addBodyPart(new FilePart(key, filename, f, "application/octet-stream", "utf-8"));
		}
		Response res = builder.execute().get();
		return handleResponse(res);
	}
	
	public ProxyInfo getProxyInfo() { return this.proxyInfo;}
	public void setProxyInfo(ProxyInfo proxy) { this.proxyInfo = proxy;}
	
	private AsyncHttpClient getHttpClient() {
		if (this.client == null) {
			AsyncHttpClientConfig.Builder builder = new AsyncHttpClientConfig.Builder();
			if (this.connectionTimeout > 0) {
				builder.setConnectionTimeoutInMs(this.connectionTimeout);
			}
			if (this.soTimeout > 0) {
				builder.setRequestTimeoutInMs(this.soTimeout);
			}
			if (this.proxyInfo != null) {
				ProxyServer proxy = null;
				if (proxyInfo.getUserName() != null && proxyInfo.getPassword() != null) {
					proxy = new ProxyServer(proxyInfo.getHost(), proxyInfo.getPort(),
						proxyInfo.getUserName(), proxyInfo.getPassword());
				} else {
					proxy = new ProxyServer(proxyInfo.getHost(), proxyInfo.getPort());
				}
				builder.setProxyServer(proxy);
			}
			this.client = new AsyncHttpClient(builder.build());
		}
		return this.client;
	}
	
	public int getSoTimeout() { return this.soTimeout;}
	public void setSoTimeout(int n) { this.soTimeout = n;}
	
	public int getConnectionTimeout() { return this.connectionTimeout;}
	public void setConnectionTimeout(int n) { this.connectionTimeout = n;}
}

