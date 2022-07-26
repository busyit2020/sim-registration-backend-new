package com.example.sim_registration_v8.nia;

import java.nio.charset.StandardCharsets;

import javax.net.ssl.SSLContext;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.sim_registration_v8.config.GeneralPropertiesConfig;
import com.example.sim_registration_v8.services.TokenService;
import com.google.gson.Gson;

@Component
public class APICallUtils {

	@Autowired
	private GeneralPropertiesConfig config;

	@Autowired
	private TokenService tokenService;

	public String login(AuthRequest req) {
		String responseBody = null;
		try {
			final SSLContext sslContext = new SSLContextBuilder()
					.loadTrustMaterial(null, (x509CertChain, authType) -> true).build();
			CloseableHttpClient client = HttpClientBuilder.create().setSSLContext(sslContext)
					.setConnectionManager(new PoolingHttpClientConnectionManager(RegistryBuilder
							.<ConnectionSocketFactory>create()
							.register("https",
									new SSLConnectionSocketFactory(sslContext, NoopHostnameVerifier.INSTANCE))
							.build()))
					.build();
			HttpPost httpPost = new HttpPost(config.getAuthUrl());
			String json = new Gson().toJson(req);
			StringEntity entity = new StringEntity(json);
			httpPost.setEntity(entity);
			httpPost.setHeader("Accept", "application/json");
			httpPost.setHeader("Content-type", "application/json");
//			System.out.println("Logging into NIA....");
			CloseableHttpResponse response = client.execute(httpPost);
			responseBody = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
			client.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return responseBody;
	}

	public String refreshToken(RefreshTokenRequest req) {
		String responseBody = null;
		try {
			final SSLContext sslContext = new SSLContextBuilder()
					.loadTrustMaterial(null, (x509CertChain, authType) -> true).build();
			CloseableHttpClient client = HttpClientBuilder.create().setSSLContext(sslContext)
					.setConnectionManager(new PoolingHttpClientConnectionManager(RegistryBuilder
							.<ConnectionSocketFactory>create()
							.register("https",
									new SSLConnectionSocketFactory(sslContext, NoopHostnameVerifier.INSTANCE))
							.build()))
					.build();
			HttpPost httpPost = new HttpPost(config.getRefreshUrl());
			String json = new Gson().toJson(req);
			StringEntity entity = new StringEntity(json);
			httpPost.setEntity(entity);
			httpPost.setHeader("Accept", "application/json");
			httpPost.setHeader("Content-type", "application/json");
			System.out.println("Refreshing NIA token....");
			CloseableHttpResponse response = client.execute(httpPost);
			responseBody = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
			client.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return responseBody;
	}

	public String sendVerificationRequest(VerificationRequest req) {
		String responseBody = null;
		try {
			final SSLContext sslContext = new SSLContextBuilder()
					.loadTrustMaterial(null, (x509CertChain, authType) -> true).build();
			CloseableHttpClient client = HttpClientBuilder.create().setSSLContext(sslContext)
					.setConnectionManager(new PoolingHttpClientConnectionManager(RegistryBuilder
							.<ConnectionSocketFactory>create()
							.register("https",
									new SSLConnectionSocketFactory(sslContext, NoopHostnameVerifier.INSTANCE))
							.build()))
					.build();
			HttpPost httpPost = new HttpPost(config.getVUrl());
			String json = new Gson().toJson(req);
			StringEntity entity = new StringEntity(json);
			httpPost.setEntity(entity);
			httpPost.setHeader("Accept", "application/json");
			httpPost.setHeader("Content-type", "application/json");
			httpPost.setHeader("Authorization", "Bearer " + tokenService.getToken().getAccessToken());
			CloseableHttpResponse response = client.execute(httpPost);
			responseBody = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
			client.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return responseBody;
	}

}
