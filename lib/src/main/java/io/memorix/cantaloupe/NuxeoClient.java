package io.memorix.cantaloupe;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.net.Proxy;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.Base64;
import java.util.Collections;
import java.util.HashMap;
import java.io.IOException;
import java.net.InetSocketAddress;

import edu.illinois.library.cantaloupe.config.Configuration;
import edu.illinois.library.cantaloupe.delegate.Logger;


public final class NuxeoClient {
  
  public static final String NX_FILE_PATH = "/nxfile/default/";
  public static final String NX_API_PATH = "/api/v1/id/";

  protected final Configuration config = Configuration.getInstance();
  public final String url = config.getString(NuxeoKey.NX_URL.key()).replaceAll("/+$", "");


  public static String getUrl()
  {
    final NuxeoClient self = new NuxeoClient();
    return self.url;
  }

  private OkHttpClient getHttpClient() {
    final OkHttpClient.Builder builder = new OkHttpClient.Builder();
    final boolean httpProxyEnabled = config.getBoolean(NuxeoKey.NX_PROXY_ENABLED.key(), false);
    if (httpProxyEnabled) {
      final String httpProxyServer = config.getString(NuxeoKey.NX_PROXY_SERVER.key());
      if (httpProxyServer == null) {
        throw new RuntimeException(String.format("proxy server setting '%s' should not be empty", NuxeoKey.NX_PROXY_SERVER.key()));
      }
      final int httpProxyPort = config.getInt(NuxeoKey.NX_PROXY_PORT.key(), 8080);
      Logger.debug(String.format("******* [MRX:getHTTPClient] Using HTTP Proxy at server %s on port %d", httpProxyServer, httpProxyPort));
      Proxy httpProxy = new Proxy(Proxy.Type.HTTP,new InetSocketAddress(httpProxyServer, httpProxyPort));
      builder.proxy(httpProxy);
    }
    return builder.build();
  }

  public String getFileUrlForIdentifier(String identifier) {
    return String.format("%s%s%s", url, NX_FILE_PATH, identifier);
  }

    public Map<String,Object> getMetadata(String identifier) {
      if (!config.getBoolean(NuxeoKey.NX_LOAD_PROPERTIES.key(), false)) return Collections.emptyMap();

      String url = String.format("%s%s%s?properties=*", this.url, NX_API_PATH, identifier);

      Request.Builder builder = new Request.Builder().method("GET", null).url(url);
      byte[] BasicAuthHeader = String.format("%s:%s", 
        config.getString(NuxeoKey.NX_USERNAME.key()),
        config.getString(NuxeoKey.NX_SECRET.key())
      ).getBytes(StandardCharsets.UTF_8);
      builder.addHeader("Authorization", "Basic " + Base64.getEncoder().encodeToString(BasicAuthHeader));
      
      Response response;

      Map<String, Object> metadata = new HashMap<>();

      try {
        response = getHttpClient().newCall(builder.build()).execute();
      } catch (IOException e) {
        metadata.put("nuxeoPropertiesError",  "Failed to load properties from Nuxeo" + e.getMessage());
        return metadata;
      }

      if (!response.isSuccessful()) {
        metadata.put("nuxeoPropertiesError",  "Failed to load properties from Nuxeo");
        return metadata;
      }

      String body;
      try {
        body = response.body().string();
      } catch (IOException e) {
        metadata.put("nuxeoPropertiesError",  "Failed to load body from Nuxeo response");
        return metadata;
      }
      Map<String, Object> nuxeoProperties = new Gson().fromJson(
        body, new TypeToken<HashMap<String, Object>>() {}.getType()
      );

      metadata.put("nuxeoProperties", nuxeoProperties);

      return metadata;
    }

}
