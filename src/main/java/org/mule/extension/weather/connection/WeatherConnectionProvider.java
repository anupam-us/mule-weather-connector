package org.mule.extension.weather.connection;

import javax.inject.Inject;

import org.mule.runtime.api.connection.CachedConnectionProvider;
import org.mule.runtime.api.connection.ConnectionException;
import org.mule.runtime.api.connection.ConnectionProvider;
import org.mule.runtime.api.connection.ConnectionValidationResult;
import org.mule.runtime.api.connection.PoolingConnectionProvider;
import org.mule.runtime.extension.api.annotation.param.Optional;
import org.mule.runtime.extension.api.annotation.param.Parameter;
import org.mule.runtime.extension.api.annotation.param.ParameterGroup;
import org.mule.runtime.extension.api.annotation.param.display.Placement;
import org.mule.runtime.http.api.HttpService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * This class (as it's name implies) provides connection instances and the funcionality to disconnect and validate those
 * connections.
 * <p>
 * All connection related parameters (values required in order to create a connection) must be
 * declared in the connection providers.
 * <p>
 * This particular example is a {@link PoolingConnectionProvider} which declares that connections resolved by this provider
 * will be pooled and reused. There are other implementations like {@link CachedConnectionProvider} which lazily creates and
 * caches connections or simply {@link ConnectionProvider} if you want a new connection each time something requires one.
 */
public class WeatherConnectionProvider implements PoolingConnectionProvider<WeatherConnection> {

	 private final Logger LOGGER = LoggerFactory.getLogger(WeatherConnectionProvider.class);
	 
	 @Parameter
	 @Placement(tab = "Advanced")
	 @Optional(defaultValue = "5000")
	 int connectionTimeout;

	 @ParameterGroup(name = "Connection")
	 WeatherConfiguration genConfig;

	 @Inject
	 private HttpService httpService; 
	 
	 /**
	  * 
	  */
	 @Override
	 public WeatherConnection connect() throws ConnectionException {
	      return new WeatherConnection(httpService, genConfig, connectionTimeout);
	 }
	 /**
	  * 
	  */
	 @Override
	 public void disconnect(WeatherConnection connection) {
	      try {
	            connection.invalidate();
	      } catch (Exception e) {
	            LOGGER.error("Error while disconnecting to Weather Channel " + e.getMessage(), e);
	      }
	 }
	 /**
	  * 
	  */
	 @Override
	 public ConnectionValidationResult validate(WeatherConnection connection) {
	      ConnectionValidationResult result;
	      try {
	           if(connection.isConnected()){
	                  result = ConnectionValidationResult.success();
	            } else {
	                  result = ConnectionValidationResult.failure("Connection Failed", new Exception());
	            }
	     } catch (Exception e) {
	           result = ConnectionValidationResult.failure("Connection failed: " + e.getMessage(), new Exception());
	     }
	   return result;
	 }
}
