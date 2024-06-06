package data;

import org.litepal.annotation.Column;
import org.litepal.crud.LitePalSupport;

import java.util.List;

public class Weather extends LitePalSupport {
    public String LL ;
    public String Location;
    public String upTime;
    public String Temp;
    public String TEXT;
    public String winDir;
    public String airQua;
    public String tempMax;
    public String tempMin;
    @Column(ignore = true)
    public Suggestion suggestion;
    @Column(ignore = true)
    public List<Forecast> forecastList;
    @Column(ignore = true)
    public List<Hourly> hourlyList;

    public String suggestionJson;
    public String forecastJson;
    public String hourlyJson;

    public Weather() {
    }
}
