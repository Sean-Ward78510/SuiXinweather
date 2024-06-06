package tool;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.suixinweather.R;

import java.util.List;

import data.Forecast;

public class SearchWeatherAdapter extends RecyclerView.Adapter<SearchWeatherAdapter.ViewHolder> {

    List<Forecast> forecastList;

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView time;
        TextView dayText;
        TextView nightText;
        TextView dayTemp;
        TextView nightTemp;
        ImageView day_icon;
        ImageView night_icon;

        public  ViewHolder(@NonNull View itemView) {
            super(itemView);
            time = (TextView) itemView.findViewById(R.id.search_weather_time);
            dayTemp = (TextView) itemView.findViewById(R.id.search_weather_dayTemp);
            nightTemp = (TextView) itemView.findViewById(R.id.search_weather_nightTemp);
            dayText = (TextView) itemView.findViewById(R.id.search_weather_dayText);
            nightText = (TextView) itemView.findViewById(R.id.search_weather_nightText);
            day_icon = (ImageView) itemView.findViewById(R.id.search_weather_dayIcon);
            night_icon = (ImageView) itemView.findViewById(R.id.search_weather_nightIcon);
        }
    }

    public SearchWeatherAdapter(List<Forecast> forecastList) {
        this.forecastList = forecastList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_weather_item,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Forecast forecast = forecastList.get(position);
        holder.time.setText(forecast.fxDate);
        holder.dayText.setText(forecast.textDay);
        holder.nightText.setText(forecast.textNight);
        holder.dayTemp.setText(forecast.tempMax);
        holder.nightTemp.setText(forecast.tempMin);
        switch (forecast.iconDay){
            case 100:
                holder.day_icon.setImageResource(R.drawable._100);
                break;
            case 101:
                holder.day_icon.setImageResource(R.drawable._101);
                break;
            case 102:
                holder.day_icon.setImageResource(R.drawable._102);
                break;
            case 103:
                holder.day_icon.setImageResource(R.drawable._103);
                break;
            case 104:
                holder.day_icon.setImageResource(R.drawable._104);
                break;
            case 150:
                holder.day_icon.setImageResource(R.drawable._150);
                break;
            case 151:
                holder.day_icon.setImageResource(R.drawable._151);
                break;
            case 152:
                holder.day_icon.setImageResource(R.drawable._152);
                break;
            case 153:
                holder.day_icon.setImageResource(R.drawable._153);
                break;
            case 300:
                holder.day_icon.setImageResource(R.drawable._300);
                break;
            case 301:
                holder.day_icon.setImageResource(R.drawable._301);
                break;
            case 302:
                holder.day_icon.setImageResource(R.drawable._302);
                break;
            case 303:
                holder.day_icon.setImageResource(R.drawable._303);
                break;
            case 304:
                holder.day_icon.setImageResource(R.drawable._304);
                break;
            case 305:
                holder.day_icon.setImageResource(R.drawable._305);
                break;
            case 306:
                holder.day_icon.setImageResource(R.drawable._306);
                break;
            case 307:
                holder.day_icon.setImageResource(R.drawable._307);
                break;
            case 308:
                holder.day_icon.setImageResource(R.drawable._308);
                break;
            case 309:
                holder.day_icon.setImageResource(R.drawable._309);
                break;
            case 310:
                holder.day_icon.setImageResource(R.drawable._310);
                break;
            case 311:
                holder.day_icon.setImageResource(R.drawable._311);
                break;
            case 312:
                holder.day_icon.setImageResource(R.drawable._312);
                break;
            case 313:
                holder.day_icon.setImageResource(R.drawable._313);
                break;
            case 314:
                holder.day_icon.setImageResource(R.drawable._314);
                break;
            case 315:
                holder.day_icon.setImageResource(R.drawable._315);
                break;
            case 316:
                holder.day_icon.setImageResource(R.drawable._316);
                break;
            case 317:
                holder.day_icon.setImageResource(R.drawable._317);
                break;
            case 318:
                holder.day_icon.setImageResource(R.drawable._318);
                break;
            case 350:
                holder.day_icon.setImageResource(R.drawable._350);
                break;
            case 351:
                holder.day_icon.setImageResource(R.drawable._351);
                break;
            case 400:
                holder.day_icon.setImageResource(R.drawable._400);
                break;
            case 401:
                holder.day_icon.setImageResource(R.drawable._401);
                break;
            case 402:
                holder.day_icon.setImageResource(R.drawable._402);
                break;
            case 403:
                holder.day_icon.setImageResource(R.drawable._403);
                break;
            case 404:
                holder.day_icon.setImageResource(R.drawable._404);
                break;
            case 405:
                holder.day_icon.setImageResource(R.drawable._405);
                break;
            case 406:
                holder.day_icon.setImageResource(R.drawable._406);
                break;
            case 407:
                holder.day_icon.setImageResource(R.drawable._407);
                break;
            case 408:
                holder.day_icon.setImageResource(R.drawable._408);
                break;
            case 409:
                holder.day_icon.setImageResource(R.drawable._409);
                break;
            case 410:
                holder.day_icon.setImageResource(R.drawable._410);
                break;
            case 456:
                holder.day_icon.setImageResource(R.drawable._456);
                break;
            case 457:
                holder.day_icon.setImageResource(R.drawable._457);
                break;
            case 499:
                holder.day_icon.setImageResource(R.drawable._499);
                break;
            case 500:
                holder.day_icon.setImageResource(R.drawable._500);
                break;
            case 501:
                holder.day_icon.setImageResource(R.drawable._501);
                break;
            case 502:
                holder.day_icon.setImageResource(R.drawable._502);
                break;
            case 503:
                holder.day_icon.setImageResource(R.drawable._503);
                break;
            case 504:
                holder.day_icon.setImageResource(R.drawable._504);
                break;
            case 507:
                holder.day_icon.setImageResource(R.drawable._507);
                break;
            case 508:
                holder.day_icon.setImageResource(R.drawable._508);
                break;
            case 509:
                holder.day_icon.setImageResource(R.drawable._509);
                break;
            case 510:
                holder.day_icon.setImageResource(R.drawable._510);
                break;
            case 514:
                holder.day_icon.setImageResource(R.drawable._514);
                break;
            case 515:
                holder.day_icon.setImageResource(R.drawable._515);
                break;
            case 999:
                holder.day_icon.setImageResource(R.drawable._999);
                break;
            case 1001:
                holder.day_icon.setImageResource(R.drawable._1001);
                break;
            case 1002:
                holder.day_icon.setImageResource(R.drawable._1002);
                break;
            case 1003:
                holder.day_icon.setImageResource(R.drawable._1003);
                break;
            case 1004:
                holder.day_icon.setImageResource(R.drawable._1004);
                break;
        }
        switch (forecast.iconNight){
            case 100:
                holder.night_icon.setImageResource(R.drawable._100);
                break;
            case 101:
                holder.night_icon.setImageResource(R.drawable._101);
                break;
            case 102:
                holder.night_icon.setImageResource(R.drawable._102);
                break;
            case 103:
                holder.night_icon.setImageResource(R.drawable._103);
                break;
            case 104:
                holder.night_icon.setImageResource(R.drawable._104);
                break;
            case 150:
                holder.night_icon.setImageResource(R.drawable._150);
                break;
            case 151:
                holder.night_icon.setImageResource(R.drawable._151);
                break;
            case 152:
                holder.night_icon.setImageResource(R.drawable._152);
                break;
            case 153:
                holder.night_icon.setImageResource(R.drawable._153);
                break;
            case 300:
                holder.night_icon.setImageResource(R.drawable._300);
                break;
            case 301:
                holder.night_icon.setImageResource(R.drawable._301);
                break;
            case 302:
                holder.night_icon.setImageResource(R.drawable._302);
                break;
            case 303:
                holder.night_icon.setImageResource(R.drawable._303);
                break;
            case 304:
                holder.night_icon.setImageResource(R.drawable._304);
                break;
            case 305:
                holder.night_icon.setImageResource(R.drawable._305);
                break;
            case 306:
                holder.night_icon.setImageResource(R.drawable._306);
                break;
            case 307:
                holder.night_icon.setImageResource(R.drawable._307);
                break;
            case 308:
                holder.night_icon.setImageResource(R.drawable._308);
                break;
            case 309:
                holder.night_icon.setImageResource(R.drawable._309);
                break;
            case 310:
                holder.night_icon.setImageResource(R.drawable._310);
                break;
            case 311:
                holder.night_icon.setImageResource(R.drawable._311);
                break;
            case 312:
                holder.night_icon.setImageResource(R.drawable._312);
                break;
            case 313:
                holder.night_icon.setImageResource(R.drawable._313);
                break;
            case 314:
                holder.night_icon.setImageResource(R.drawable._314);
                break;
            case 315:
                holder.night_icon.setImageResource(R.drawable._315);
                break;
            case 316:
                holder.night_icon.setImageResource(R.drawable._316);
                break;
            case 317:
                holder.night_icon.setImageResource(R.drawable._317);
                break;
            case 318:
                holder.night_icon.setImageResource(R.drawable._318);
                break;
            case 350:
                holder.night_icon.setImageResource(R.drawable._350);
                break;
            case 351:
                holder.night_icon.setImageResource(R.drawable._351);
                break;
            case 400:
                holder.night_icon.setImageResource(R.drawable._400);
                break;
            case 401:
                holder.night_icon.setImageResource(R.drawable._401);
                break;
            case 402:
                holder.night_icon.setImageResource(R.drawable._402);
                break;
            case 403:
                holder.night_icon.setImageResource(R.drawable._403);
                break;
            case 404:
                holder.night_icon.setImageResource(R.drawable._404);
                break;
            case 405:
                holder.night_icon.setImageResource(R.drawable._405);
                break;
            case 406:
                holder.night_icon.setImageResource(R.drawable._406);
                break;
            case 407:
                holder.night_icon.setImageResource(R.drawable._407);
                break;
            case 408:
                holder.night_icon.setImageResource(R.drawable._408);
                break;
            case 409:
                holder.night_icon.setImageResource(R.drawable._409);
                break;
            case 410:
                holder.night_icon.setImageResource(R.drawable._410);
                break;
            case 456:
                holder.night_icon.setImageResource(R.drawable._456);
                break;
            case 457:
                holder.night_icon.setImageResource(R.drawable._457);
                break;
            case 499:
                holder.night_icon.setImageResource(R.drawable._499);
                break;
            case 500:
                holder.night_icon.setImageResource(R.drawable._500);
                break;
            case 501:
                holder.night_icon.setImageResource(R.drawable._501);
                break;
            case 502:
                holder.night_icon.setImageResource(R.drawable._502);
                break;
            case 503:
                holder.night_icon.setImageResource(R.drawable._503);
                break;
            case 504:
                holder.night_icon.setImageResource(R.drawable._504);
                break;
            case 507:
                holder.night_icon.setImageResource(R.drawable._507);
                break;
            case 508:
                holder.night_icon.setImageResource(R.drawable._508);
                break;
            case 509:
                holder.night_icon.setImageResource(R.drawable._509);
                break;
            case 510:
                holder.night_icon.setImageResource(R.drawable._510);
                break;
            case 514:
                holder.night_icon.setImageResource(R.drawable._514);
                break;
            case 515:
                holder.night_icon.setImageResource(R.drawable._515);
                break;
            case 999:
                holder.night_icon.setImageResource(R.drawable._999);
                break;
            case 1001:
                holder.night_icon.setImageResource(R.drawable._1001);
                break;
            case 1002:
                holder.night_icon.setImageResource(R.drawable._1002);
                break;
            case 1003:
                holder.night_icon.setImageResource(R.drawable._1003);
                break;
            case 1004:
                holder.night_icon.setImageResource(R.drawable._1004);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return forecastList.size();
    }

}
