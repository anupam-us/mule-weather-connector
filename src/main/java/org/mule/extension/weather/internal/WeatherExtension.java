package org.mule.extension.weather.internal;

import static org.mule.runtime.api.meta.Category.COMMUNITY;

import org.mule.extension.weather.connection.WeatherConnectionProvider;
import org.mule.extension.weather.operation.WeatherZipOperations;
import org.mule.runtime.extension.api.annotation.Extension;
import org.mule.runtime.extension.api.annotation.Operations;
import org.mule.runtime.extension.api.annotation.connectivity.ConnectionProviders;
import org.mule.runtime.extension.api.annotation.dsl.xml.Xml;


/**
 * This is the main class of an extension, is the entry point from which configurations, connection providers, operations
 * and sources are going to be declared.
 */
@Xml(prefix = "weather")
@ConnectionProviders(WeatherConnectionProvider.class)
@Extension(name = "Weather", vendor = "anupam.us", category = COMMUNITY)
@Operations({WeatherZipOperations.class})
public class WeatherExtension {

}
