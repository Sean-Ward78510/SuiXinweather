package data;

import org.litepal.crud.LitePalSupport;

public class Forecast extends LitePalSupport{
    public String fxDate;

    public int iconDay;
    public int iconNight;

    public String textDay;
    public String textNight;

    public String tempMax;
    public String tempMin;

    public Forecast() {
    }

    public Forecast(String fxDate, int iconDay, int iconNight, String textDay, String textNight, String tempMax, String tempMin) {
        this.fxDate = fxDate;
        this.iconDay = iconDay;
        this.iconNight = iconNight;
        this.textDay = textDay;
        this.textNight = textNight;
        this.tempMax = tempMax;
        this.tempMin = tempMin;
    }
}
