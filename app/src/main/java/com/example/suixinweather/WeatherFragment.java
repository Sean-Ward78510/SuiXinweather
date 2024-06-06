package com.example.suixinweather;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import data.CardWeather;
import data.Forecast;
import data.Hourly;
import data.Suggestion;
import data.Weather;
import okhttp3.Call;
import okhttp3.Response;
import service.HttpUtil;
import tool.ForecastAdapter;
import tool.HourlyAdapter;

public class WeatherFragment extends Fragment {
    List<Hourly> hourlyList ;
    List<Forecast>  forecastList;

    HourlyAdapter hourlyAdapter;
    ForecastAdapter forecastAdapter;

    String LL = "108.91,34.16";
    String Location;
    String Temp;
    String winDir;
    String airQua;
    String upTime;
    String TEXT;
    String tempMax;
    String tempMin;
    String sugJson;
    String foreJson;
    String hourJson;

    String Key = "&key=9a93a0d7b2304e47bb71410b4760b03c";
    String Url_city = "https://geoapi.qweather.com/v2/city/lookup?location=";
    String Url_now = "https://devapi.qweather.com/v7/weather/now?location=";
    String Url_daily = "https://devapi.qweather.com/v7/weather/7d?location=";
    String Url_hourly = "https://devapi.qweather.com/v7/weather/24h?location=";
    String Url_air = "https://devapi.qweather.com/v7/air/now?location=";
    String Url_sug = "https://devapi.qweather.com/v7/indices/1d?type=1,2,3,5,8,9&location=";


    Suggestion suggestion;
    public TextView PlaceName;
    public TextView Time;
    public TextView Degree;
    public TextView MaxD;
    public TextView MinD;
    public TextView Air;
    public TextView WinDirection;
    public TextView Text;

    public TextView Clothes;
    public TextView UV;
    public TextView Sport;
    public TextView Wash;
    public TextView Com;
    public TextView Cold;

    public NestedScrollView nestedScrollView;
    public SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView hourlyView;
    RecyclerView forecastView;
    Weather weather;

    public WeatherFragment() {
    }

    public WeatherFragment(String id){
        LL = id;
    }
    public WeatherFragment(Weather weather){
        Location = weather.Location;
        LL = weather.LL;
        upTime = weather.upTime;
        Temp= weather.Temp;
        TEXT= weather.TEXT;
        winDir= weather.winDir;
        airQua= weather.airQua;
        tempMax= weather.tempMax;
        tempMin= weather.tempMin;
        sugJson = weather.suggestionJson;
        foreJson = weather.forecastJson;
        hourJson = weather.hourlyJson;
        suggestion= weather.suggestion;
        forecastList= weather.forecastList;
        hourlyList= weather.hourlyList;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment,container,false);

        PlaceName = (TextView) view.findViewById(R.id.title_place);
        Time = (TextView) view.findViewById(R.id.title_update_time);
        Degree = (TextView) view.findViewById(R.id.degree);
        MaxD = (TextView) view.findViewById(R.id.degree_max);
        MinD = (TextView) view.findViewById(R.id.degree_min);
        Air = (TextView) view.findViewById(R.id.air_quality);
        WinDirection = (TextView) view.findViewById(R.id.win_direction);
        Text = (TextView) view.findViewById(R.id.weather_info_text);

        Clothes = (TextView)view.findViewById(R.id.clothes_text);
        UV = (TextView) view.findViewById(R.id.ultraviolet_text);
        Sport = (TextView) view.findViewById(R.id.sport_text);
        Wash = (TextView) view.findViewById(R.id.wash_text);
        Com = (TextView) view.findViewById(R.id.com_text);
        Cold = (TextView) view.findViewById(R.id.cold_text);

        nestedScrollView = (NestedScrollView) view.findViewById(R.id.nestedScrollview);
        hourlyView = (RecyclerView) view.findViewById(R.id.recycle_hourly);
        forecastView = (RecyclerView) view.findViewById(R.id.recycle_forecast);
        LinearLayoutManager forecastManager = new LinearLayoutManager(getContext());
        LinearLayoutManager hourlyManager = new LinearLayoutManager(getContext());
        hourlyManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        hourlyView.setLayoutManager(hourlyManager);
        forecastView.setLayoutManager(forecastManager);

        hourlyAdapter = new HourlyAdapter(hourlyList);

        forecastAdapter = new ForecastAdapter(forecastList);

        hourlyView.setAdapter(hourlyAdapter);
        forecastView.setAdapter(forecastAdapter);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {//刷新界面
            @Override
            public void onRefresh() {
                forecastList = new ArrayList<>();
                hourlyList = new ArrayList<>();

                loadWeather();

                reSaveWeather();

                swipeRefreshLayout.setRefreshing(false);
            }
        });

        init();

        return view;
    }
    private void init(){
        if (sugJson == null){
            loadWeather();
            hourlyAdapter.set(hourlyList);
            forecastAdapter.set(forecastList);
            hourlyAdapter.notifyDataSetChanged();
            forecastAdapter.notifyDataSetChanged();
            reSaveWeather();
            reSaveCard();
        }else {
            parseForeCast();
            parseHourLy();
            parseSug();
            hourlyAdapter.set(hourlyList);
            forecastAdapter.setForecastList(forecastList);
            showWeather();
        }
    }

    private Handler handler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(@NonNull Message msg) {
            showWeather();
        }
    };
    public void loadWeather(){
        Thread load = new Thread(new Runnable(){
            @Override
            public void run() {
                // 在后台执行耗时任务，如加载天气数据或进行网络请求
                getCity(LL);
                getNowWeather(LL);
                getHourly(LL);
                getDaily(LL);
                getAirQua(LL);
                getSug(LL);
            }
        });
        load.start();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        handler.sendEmptyMessage(1);
    }
    public void showWeather(){
        PlaceName.setText(Location);
        Time.setText(upTime);
        Degree.setText(Temp);
        MaxD.setText(tempMax);
        MinD.setText(tempMin);
        Air.setText(airQua);
        WinDirection.setText(winDir);
        Text.setText(TEXT);

        if (suggestion != null){
            Clothes.setText(suggestion.clothes);
            UV.setText(suggestion.ult);
            Sport.setText(suggestion.sport);
            Wash.setText(suggestion.wash);
            Com.setText(suggestion.com);
            Cold.setText(suggestion.cold);
        }

        Log.d("weatherFragment", "onRefresh: " +forecastList.isEmpty() + hourlyList.isEmpty());
        hourlyAdapter.set(hourlyList);
        forecastAdapter.set(forecastList);
        hourlyAdapter.notifyDataSetChanged();
        forecastAdapter.notifyDataSetChanged();
    }

    public void getCity(String location){
        HttpUtil.sandOkHttpRequest(Url_city + location + Key, new okhttp3.Callback() {
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                try {
                    String respondData = response.body().string();
                    Log.d("getCity", "onResponse: " + respondData);
                    JSONObject jsonObject = new JSONObject(respondData);
                    if (jsonObject.getString("code").equals("200")) {
                        JSONArray jsonArray = jsonObject.getJSONArray("location");
                        Location = jsonArray.getJSONObject(0).getString("name");
                    }else {
                        Log.d("getCity", "onResponse:  Error!" );
                    }
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
            }
        });
    }
    public void getNowWeather(String location){
        HttpUtil.sandOkHttpRequest(Url_now + location + Key, new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("Error", "getNow " + e);
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    if (jsonObject.getString("code").equals("200")){
                        JSONObject nowObject = jsonObject.getJSONObject("now");
                        Temp = nowObject.getString("temp") + "°";
                        TEXT = nowObject.getString("text");
                        winDir = nowObject.getString("windDir");
                        upTime = "更新时间:" + ((String) nowObject.get("obsTime")).substring(5,10) + "-" +
                                ((String) nowObject.get("obsTime")).substring(11,16);
                    }else {
                        Log.d("getNow", "onResponse:  Error!" );
                    }
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    public void getHourly(String location){
        HttpUtil.sandOkHttpRequest(Url_hourly + location + Key, new okhttp3.Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
            }
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                try {
                    hourlyList = new ArrayList<>();
                    hourJson = response.body().string();
                    JSONObject jsonObject = new JSONObject(hourJson);
                    if (jsonObject.getString("code").equals("200")){
                        JSONArray jsonArray = jsonObject.getJSONArray("hourly");
                        for(int i = 0;i < jsonArray.length();i++){
                            JSONObject object = jsonArray.getJSONObject(i);
                            Hourly hourly = new Hourly();
                            hourly.fxTime = object.getString("fxTime").substring(11,16);
                            hourly.temp = object.getString("temp") + "°";
                            hourly.icon = Integer.parseInt(object.getString("icon"));
                            hourly.windDir = object.getString("windDir");
                            hourly.text = object.getString("text");
                            hourlyList.add(hourly);
                        }
                    }else {
                        Log.d("getHourly", "onResponse:  Error!" );
                    }
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        });

    }

    public void getDaily(String location){
        HttpUtil.sandOkHttpRequest(Url_daily + location + Key, new okhttp3.Callback() {
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                try {
                    forecastList = new ArrayList<>();
                    foreJson = response.body().string();
                    JSONObject jsonObject = new JSONObject(foreJson);
                    if (jsonObject.getString("code").equals("200")){
                        JSONArray jsonArray = jsonObject.getJSONArray("daily");
                        for(int i = 0;i < jsonArray.length();i++){
                            JSONObject object = jsonArray.getJSONObject(i);
                            Forecast forecast = new Forecast();
                            forecast.fxDate = object.getString("fxDate");
                            forecast.iconDay = Integer.parseInt(object.getString("iconDay"));
                            forecast.iconNight = Integer.parseInt(object.getString("iconNight"));
                            forecast.textDay = object.getString("textDay");
                            forecast.textNight = object.getString("textNight");
                            forecast.tempMax = object.getString("tempMax") + "°";
                            forecast.tempMin = object.getString("tempMin") + "°";
                            if (i == 0) {
                                tempMax = forecast.tempMax ;
                                tempMin = forecast.tempMin ;
                            }
                            forecastList.add(forecast);
                        }
                    }else {
                        Log.d("getDaily", "onResponse:  Error!" );
                    }
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
            }
        });
    }

    public void getAirQua(String location){
        HttpUtil.sandOkHttpRequest(Url_air + location + Key, new okhttp3.Callback() {
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                try {
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    if (jsonObject.getString("code").equals("200")) {
                        JSONObject jsonAir = jsonObject.getJSONObject("now");
                        airQua = "空气" + jsonAir.getString("category");
                    }else {
                        Log.d("getAir", "onResponse:  Error!" );
                    }
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
            }
        });
    }

    public void getSug(String location){
        HttpUtil.sandOkHttpRequest(Url_sug + location + Key, new okhttp3.Callback() {
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                try {
                    suggestion = new Suggestion();
                    sugJson = response.body().string();
                    JSONObject jsonObject = new JSONObject(sugJson);
                    if (jsonObject.getString("code").equals("200")){
                        JSONArray jsonArray = jsonObject.getJSONArray("daily");
                        Suggestion sug = new Suggestion();
                        sug.sport = jsonArray.getJSONObject(0).getString("category") + "运动";
                        sug.wash = jsonArray.getJSONObject(1).getString("category") + "洗车";
                        sug.clothes = jsonArray.getJSONObject(2).getString("category");
                        sug.ult = "紫外线" +  jsonArray.getJSONObject(3).getString("category");
                        sug.com = "温度" + jsonArray.getJSONObject(4).getString("category");
                        sug.cold = jsonArray.getJSONObject(5).getString("category") + "感冒";
                        suggestion = sug;
                        Log.d("getSug", "onResponse: " + suggestion.wash + "    " +  suggestion.cold);
                    }else {
                        Log.d("getDaily", "onResponse:  Error!" );
                    }
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
            }
        });
    }
    public void loadBingPic(){
        String Url_bing = "https://www.bing.com/HPImageArchive.aspx?format=js&idx=0&n=1&mkt=en-US";
        String url_bing;
        HttpUtil.sandOkHttpRequest(Url_bing, new okhttp3.Callback() {
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                try {
                    JSONObject json = new JSONObject(response.body().string());
                    String imageUrl = json.getJSONArray("images")
                            .getJSONObject(0)
                            .getString("url");
                    imageUrl = "https://www.bing.com" + imageUrl;
                    Thread.sleep(500);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
            }
        });
    }

    public void parseForeCast(){
        try {
            forecastList = new ArrayList<>();
            JSONObject jsonObject = new JSONObject(foreJson);
            if (jsonObject.getString("code").equals("200")){
                JSONArray jsonArray = jsonObject.getJSONArray("daily");
                for(int i = 0;i < jsonArray.length();i++){
                    JSONObject object = jsonArray.getJSONObject(i);
                    Forecast forecast = new Forecast();
                    forecast.fxDate = object.getString("fxDate");
                    forecast.iconDay = Integer.parseInt(object.getString("iconDay"));
                    forecast.iconNight = Integer.parseInt(object.getString("iconNight"));
                    forecast.textDay = object.getString("textDay");
                    forecast.textNight = object.getString("textNight");
                    forecast.tempMax = object.getString("tempMax") + "°";
                    forecast.tempMin = object.getString("tempMin") + "°";
                    if (i == 0) {
                        tempMax = forecast.tempMax ;
                        tempMin = forecast.tempMin ;
                    }
                    forecastList.add(forecast);
                }
            }else {
                Log.d("getDaily", "onResponse:  Error!" );
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public void parseHourLy(){
        try {
            hourlyList = new ArrayList<>();
            JSONObject jsonObject = new JSONObject(hourJson);
            if (jsonObject.getString("code").equals("200")){
                JSONArray jsonArray = jsonObject.getJSONArray("hourly");
                for(int i = 0;i < jsonArray.length();i++){
                    JSONObject object = jsonArray.getJSONObject(i);
                    Hourly hourly = new Hourly();
                    hourly.fxTime = object.getString("fxTime").substring(11,16);
                    hourly.temp = object.getString("temp") + "°";
                    hourly.icon = Integer.parseInt(object.getString("icon"));
                    hourly.windDir = object.getString("windDir");
                    hourly.text = object.getString("text");
                    hourlyList.add(hourly);
                }
            }else {
                Log.d("getHourly", "onResponse:  Error!" );
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
    public void parseSug(){
        try {
            suggestion = new Suggestion();
            JSONObject jsonObject = new JSONObject(sugJson);
            if (jsonObject.getString("code").equals("200")){
                JSONArray jsonArray = jsonObject.getJSONArray("daily");
                Suggestion sug = new Suggestion();
                sug.sport = jsonArray.getJSONObject(0).getString("category") + "运动";
                sug.wash = jsonArray.getJSONObject(1).getString("category") + "洗车";
                sug.clothes = jsonArray.getJSONObject(2).getString("category");
                sug.ult = "紫外线" +  jsonArray.getJSONObject(3).getString("category");
                sug.com = "温度" + jsonArray.getJSONObject(4).getString("category");
                sug.cold = jsonArray.getJSONObject(5).getString("category") + "感冒";
                suggestion = sug;
                Log.d("getSug", "onResponse: " + suggestion.wash + "    " +  suggestion.cold);
            }else {
                Log.d("getDaily", "onResponse:  Error!" );
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
    public void reSaveWeather(){
        weather = new Weather();
        weather.Location = Location;
        weather.LL = LL;
        weather.upTime = upTime;
        weather.Temp = Temp;
        weather.TEXT = TEXT;
        weather.winDir = winDir;
        weather.airQua = airQua;
        weather.tempMax = tempMax;
        weather.tempMin = tempMin;
        weather.suggestion = suggestion;
        weather.forecastList = forecastList;
        weather.hourlyList = hourlyList;
        weather.forecastJson = foreJson;
        weather.hourlyJson = hourJson;
        weather.suggestionJson = sugJson;

        weather.updateAll("Location = ?",Location);
    }
    public void reSaveCard(){
        CardWeather cardWeather = new CardWeather();
        cardWeather.placeName = weather.Location;
        cardWeather.temp = weather.Temp;
        cardWeather.tempMax = weather.tempMax;
        cardWeather.tempMin = weather.tempMin;
        cardWeather.airQua = weather.airQua;
        cardWeather.updateAll("placeName = ?",weather.Location);
    }

}

