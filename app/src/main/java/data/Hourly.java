package data;

import org.litepal.crud.LitePalSupport;

public class Hourly extends LitePalSupport{
    public String fxTime;

    public int icon;

    public String text;

    public String temp;

    public String windDir;

    public Hourly() {
    }

    public Hourly(String date, int icon, String text, String degree, String winDirection) {
        this.fxTime = date;
        this.icon = icon;
        this.text = text;
        this.temp = degree;
        this.windDir = winDirection;
    }
}
