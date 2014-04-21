package jp.co.flect.sendgrid.transport;

public abstract class TransportFactory {
	
	private static TransportFactory DEFAULT_FACTORY;

	static {
		TransportFactory ret = null;
		if (ret == null) {
			try {
				Class.forName("com.ning.http.client.AsyncHttpClient");
				ret = new AsyncHttpClientTransportFactory();
			} catch (Exception e) {
			}
		}
		if (ret == null) {
			try {
				Class.forName("org.apache.http.impl.client.DefaultHttpClient");
				ret = new HttpClientTransportFactory();
			} catch (Exception e) {
			}
		}
		DEFAULT_FACTORY = ret;
	}

	public static TransportFactory getDefaultFactory() { return DEFAULT_FACTORY;}
	public static void setDefaultFactory(TransportFactory v) { DEFAULT_FACTORY = v;}

	public static Transport createDefaultTransport() {
		if (DEFAULT_FACTORY == null) {
			throw new IllegalStateException("Http client libraries not found.");
		}
		return DEFAULT_FACTORY.create();
	}

	public abstract Transport create();

	public static class HttpClientTransportFactory extends TransportFactory {
		public Transport create() { return new HttpClientTransport();}
	}

	public static class AsyncHttpClientTransportFactory extends TransportFactory {
		public Transport create() { return new AsyncHttpClientTransport();}
	}
}

