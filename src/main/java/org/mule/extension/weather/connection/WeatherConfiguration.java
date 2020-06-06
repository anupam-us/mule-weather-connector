package org.mule.extension.weather.connection;

import org.mule.extension.weather.operation.WeatherZipOperations;
import org.mule.runtime.extension.api.annotation.Expression;
import org.mule.runtime.extension.api.annotation.Operations;
import org.mule.runtime.extension.api.annotation.connectivity.ConnectionProviders;
import org.mule.runtime.extension.api.annotation.param.Parameter;
import org.mule.runtime.extension.api.annotation.param.display.DisplayName;
import org.mule.runtime.extension.api.annotation.param.display.Placement;
import org.mule.runtime.extension.api.annotation.param.display.Summary;

/**
 * This class represents an extension configuration, values set in this class are commonly used across multiple
 * operations since they represent something core from the extension.
 */
@Operations(WeatherZipOperations.class)
@ConnectionProviders(WeatherConnectionProvider.class)
public class WeatherConfiguration {

	private static final String GENL = "General";
	public enum Channel
     {
        openWeather, yahoo, forecast 
     };

     @Parameter
     @Placement(tab = GENL)
     @DisplayName("Weather Channel")
     @Summary("Options: openweather, yahoo, forecast ")
     @Expression(org.mule.runtime.api.meta.ExpressionSupport.NOT_SUPPORTED)
     private String wChannel;


     public String getWChannel() {
           return wChannel;
     }
}
