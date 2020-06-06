package org.mule.extension.weather.internal;

import org.mule.runtime.api.util.MultiMap;

public class WeatherConstants {
    public static final String ZIP = "Get weather by ZIP";

    public static final String chYahoo = "Yahoo";
    public static final String chOpenWthr = "OpenWeather";
    public static final String chApixu = "APIXU";

    private static final String chOpenWthrKey = "bfc3e1a682d19fbebc45954fafd1f3b7";
    private static final String chApixuKey = "576db840a47c478297015039180112";

    private WeatherConstants() {

    }

    /**
     * 
     * @param channel
     * @return
     */
    public static String getUrl(String channel) {
        switch (channel) {
            case chYahoo:
                return ("https://query.yahooapis.com/v1/public/yql");
            case chOpenWthr:
                return ("http://api.openweathermap.org/data/2.5/forecast");
            case chApixu:
                return ("http://api.apixu.com/v1/current.json");
        }
        return null;
    }

    /**
     * 
     * @param wChannel
     * @param i
     * @return
     */
    public static MultiMap < String, String > getQueryForZip(String wChannel, int zip) {
        MultiMap < String, String > q = new MultiMap < String, String > ();

        if (wChannel.equals(chYahoo)) {
            q.put("q", "select * from weather.forecast where woeid in (select woeid from geo.places(1) where text='" + zip + "')");
            q.put("format", "json");
            q.put("env", "store://datatables.org/alltableswithkeys");
        }

        if (wChannel.equals(chOpenWthr)) {
            q.put("zip", zip + ",us");
            q.put("APPID", chOpenWthrKey);
        }

        if (wChannel.equals(chApixu)) {
            q.put("q", Integer.toString(zip));
            q.put("key", chApixuKey);
        }
        return q;
    }
}
