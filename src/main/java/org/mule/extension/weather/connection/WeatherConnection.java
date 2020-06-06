package org.mule.extension.weather.connection;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeoutException;

import org.mule.extension.weather.internal.WeatherConstants;
import org.mule.runtime.api.connection.ConnectionException;
import org.mule.runtime.api.util.MultiMap;
import org.mule.runtime.http.api.HttpConstants.Method;
import org.mule.runtime.http.api.HttpService;
import org.mule.runtime.http.api.client.HttpClient;
import org.mule.runtime.http.api.client.HttpClientConfiguration;
import org.mule.runtime.http.api.domain.message.request.HttpRequest;
import org.mule.runtime.http.api.domain.message.request.HttpRequestBuilder;
import org.mule.runtime.http.api.domain.message.response.HttpResponse;

/**
 * This class represents an extension connection just as example (there is no real connection with anything here c:).
 */
public class WeatherConnection {

    private WeatherConfiguration genConfig;
    private int connectionTimeout;
    private HttpClient httpClient;
    private HttpRequestBuilder httpRequestBuilder;
    
    /**
     * 
     * @param gConfig
     * @param pConfig
     * @param cTimeout
     */
    public WeatherConnection(HttpService httpService, WeatherConfiguration gConfig, int cTimeout) {
          genConfig = gConfig;
          connectionTimeout = cTimeout;
          
          initHttpClient(httpService);
    }

    /**
     * 
     * @param httpService
     */
    
    public void initHttpClient(HttpService httpService){
          HttpClientConfiguration.Builder builder = new HttpClientConfiguration.Builder();
          builder.setName("AnupamUsWeather");
          httpClient = httpService.getClientFactory().create(builder.build());
          
          httpRequestBuilder = HttpRequest.builder();
          
          httpClient.start();
    }

    /**
     * 
     */
 public void invalidate() {
     httpClient.stop();
 }
 
 public boolean isConnected() throws Exception{
   
   String wChannel = genConfig.getWChannel();
   String strUri = WeatherConstants.getUrl(wChannel);
   MultiMap<String, String> qParams = WeatherConstants.getQueryForZip(wChannel,30328);

          HttpRequest request = httpRequestBuilder
                        .method(Method.GET) 
                        .uri(strUri)
                        .queryParams(qParams)
                        .build();

          HttpResponse httpResponse = httpClient.send(request,connectionTimeout,false,null);
          
          if (httpResponse.getStatusCode() >= 200 && httpResponse.getStatusCode() < 300)
                 return true;
          else
                 throw new ConnectionException("Error connecting to the server: Error Code " + httpResponse.getStatusCode()
                 + "~" + httpResponse);
    }
 
 /**
  * 
  * @param Zip
  * @return
  */
    public InputStream callHttpZIP(int iZip){
          HttpResponse httpResponse = null;
          String strUri = WeatherConstants.getUrl(genConfig.getWChannel());
          System.out.println("URL is: " + strUri);
          MultiMap<String, String> qParams = WeatherConstants.getQueryForZip(genConfig.getWChannel(),iZip);
          
          HttpRequest request = httpRequestBuilder
                        .method(Method.GET) 
                        .uri(strUri)
                        .queryParams(qParams)
                        .build();
          
          System.out.println("Request is: " + request);
          
          try {
                 
                 httpResponse = httpClient.send(request,connectionTimeout,false,null);
                 System.out.println(httpResponse);
                 return httpResponse.getEntity().getContent();
                 
          } catch (IOException e) {
                 // TODO Auto-generated catch block
                 e.printStackTrace();
          } catch (TimeoutException e) {
                 // TODO Auto-generated catch block
                 e.printStackTrace();
          } catch (Exception e) {
                 // TODO Auto-generated catch block
                 e.printStackTrace();
          }
          return null;
    }     
}
