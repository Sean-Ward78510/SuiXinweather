package com.example.suixinweather;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.litepal.LitePal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import data.CardWeather;
import data.Forecast;
import data.Hourly;
import data.SearchPlace;
import data.Suggestion;
import data.Weather;
import okhttp3.Call;
import okhttp3.Response;
import service.HttpUtil;
import tool.SearchWeatherAdapter;

public class SearchWeatherActivity extends AppCompatActivity implements View.OnClickListener {

    String sugJson;
    String forecastJson;
    String hourlyJson;
    String Key = "&key=9a93a0d7b2304e47bb71410b4760b03c";
    String Url_now = "https://devapi.qweather.com/v7/weather/now?location=";
    String Url_daily = "https://devapi.qweather.com/v7/weather/7d?location=";
    String Url_hourly = "https://devapi.qweather.com/v7/weather/24h?location=";
    String Url_air = "https://devapi.qweather.com/v7/air/now?location=";
    String Url_sug = "https://devapi.qweather.com/v7/indices/1d?type=1,2,3,5,8,9&location=";

    TextView Place;
    RecyclerView recyclerView;
    Button add;
    Button turn_HeWeather;
    TextView turn_text;

    List<Forecast> forecasts = new ArrayList<>();
    List<Hourly> hourlies;
    List<SearchPlace> places;

    Weather weather;
    SearchPlace searchPlace = new SearchPlace();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_weather);
        Log.d("Star", "onCreate:  SearchWeather!");

        Log.d("getIntent", "onCreate: succeed in creating!" );

        Intent intent = getIntent();
        searchPlace.placeName = intent.getStringExtra("name");
        searchPlace.placeId = intent.getStringExtra("id");
        searchPlace.adm1 = intent.getStringExtra("adm1");
        searchPlace.amd2 = intent.getStringExtra("adm2");

        add = (Button) findViewById(R.id.add_weather);
        turn_HeWeather = (Button) findViewById(R.id.turn_HeWeather);
        turn_text = (TextView) findViewById(R.id.turn_text);

        loadSearchWeather();

        places = LitePal.findAll(SearchPlace.class);
        if (places.contains(searchPlace)) {
            add.setVisibility(View.GONE);
            turn_HeWeather.setVisibility(View.VISIBLE);
            turn_text.setVisibility(View.VISIBLE);
        }

        Place = (TextView) findViewById(R.id.Search_weather_place);
        recyclerView = (RecyclerView) findViewById(R.id.search_weather_recycle);
        add.setOnClickListener(this);
        turn_HeWeather.setOnClickListener(this);

        Place.setText(searchPlace.placeName);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.add_weather){
            loadWeather();
            saveWeather();

            add.setVisibility(View.INVISIBLE);
            turn_HeWeather.setVisibility(View.VISIBLE);
            turn_text.setVisibility(View.VISIBLE);
        }
        if (view.getId() == R.id.turn_HeWeather){
            Intent intent = new Intent(this,SuixinWeatherActivity.class);
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }

    Handler handler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case 1:
                    showSearchWeather();
                    Log.d("getIntentMes", "loadSearchWeather:  sand 1 have execute");
                    break;
            }
        }
    };

    public void loadSearchWeather(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                getDaily(searchPlace.placeId);
            }
        }).start();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        Message message = new Message();
        message.what = 1;
        handler.sendMessage(message);
        Log.d("getIntentMes", "loadSearchWeather:  sand 1");
    }

    public void loadWeather(){
        weather = new Weather();
        hourlies = new ArrayList<>();
        Thread load = new Thread(new Runnable(){
            @Override
            public void run() {
                // 在后台执行耗时任务，如加载天气数据或进行网络请求
                getNowWeather(searchPlace.placeId);
                getHourly(searchPlace.placeId);
                getAirQua(searchPlace.placeId);
                getSug(searchPlace.placeId);

            }
        });
        load.start();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void showSearchWeather(){
        SearchWeatherAdapter adapter = new SearchWeatherAdapter(forecasts);
        recyclerView.setAdapter(adapter);
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
                        weather.Temp = nowObject.getString("temp") + "°";
                        weather.TEXT = nowObject.getString("text");
                        weather.winDir = nowObject.getString("windDir");
                        weather.upTime = "更新时间:" + ((String) nowObject.get("obsTime")).substring(5,10) + "-" +
                                ((String) nowObject.get("obsTime")).substring(11,16);
                        Log.d("getNow", "onResponse: " + weather.Temp);
                        Log.d("getNow", "onResponse: " + weather.TEXT);
                        Log.d("getNow", "onResponse: " + weather.winDir);
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
                    hourlyJson = response.body().string();
                    JSONObject jsonObject = new JSONObject(hourlyJson);
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
                            hourlies.add(hourly);
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
                    forecastJson = response.body().string();
                    JSONObject jsonObject = new JSONObject(forecastJson);
                    if (jsonObject.getString("code").equals("200")){
                        JSONArray jsonArray = jsonObject.getJSONArray("daily");
                        for(int i = 0;i < jsonArray.length();i++){
                            JSONObject object = jsonArray.getJSONObject(i);
                            Forecast forecast = new Forecast();
                            forecast.fxDate = object.getString("fxDate").substring(5);
                            forecast.iconDay = Integer.parseInt(object.getString("iconDay"));
                            forecast.iconNight = Integer.parseInt(object.getString("iconNight"));
                            forecast.textDay = object.getString("textDay");
                            forecast.textNight = object.getString("textNight");
                            forecast.tempMax = object.getString("tempMax") + "°";
                            forecast.tempMin = object.getString("tempMin") + "°";
                            Log.d("SearchWeather", "onResponse: tempMax "  + forecast.tempMax);
                            Log.d("SearchWeather", "onResponse: tempMin "  + forecast.tempMin);

                            forecasts.add(forecast);
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
                        weather.airQua = "空气" + jsonAir.getString("category");
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
                    sugJson = response.body().string();
                    JSONObject jsonObject = new JSONObject(sugJson);
                    if (jsonObject.getString("code").equals("200")){
                        JSONArray jsonArray = jsonObject.getJSONArray("daily");
                        Suggestion suggestion = new Suggestion();
                        Log.d("getSug", "onResponse: " + jsonArray.getJSONObject(0).getString("name"));
                        suggestion.sport = jsonArray.getJSONObject(0).getString("category") + "运动";
                        Log.d("getSug", "onResponse: " + jsonArray.getJSONObject(1).getString("name"));
                        suggestion.wash = jsonArray.getJSONObject(1).getString("category") + "洗车";
                        Log.d("getSug", "onResponse: " + jsonArray.getJSONObject(2).getString("name"));
                        suggestion.clothes = jsonArray.getJSONObject(2).getString("category");
                        Log.d("getSug", "onResponse: " + jsonArray.getJSONObject(3).getString("name"));
                        suggestion.ult = "紫外线" +  jsonArray.getJSONObject(3).getString("category");
                        Log.d("getSug", "onResponse: " + jsonArray.getJSONObject(4).getString("name"));
                        suggestion.com = "温度" + jsonArray.getJSONObject(4).getString("category");
                        Log.d("getSug", "onResponse: " + jsonArray.getJSONObject(5).getString("name"));
                        suggestion.cold = jsonArray.getJSONObject(5).getString("category") + "感冒";
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
    public void saveWeather(){
        weather.Location = searchPlace.placeName;
        weather.LL = searchPlace.placeId;
        weather.tempMax = forecasts.get(0).tempMax ;
        weather.tempMin = forecasts.get(0).tempMin ;

        weather.forecastJson = forecastJson;
        weather.hourlyJson = hourlyJson;
        weather.suggestionJson = sugJson;

        CardWeather cardWeather = new CardWeather();
        cardWeather.placeName = weather.Location;
        cardWeather.temp = weather.Temp;
        cardWeather.airQua = weather.airQua;
        cardWeather.tempMax = weather.tempMax;
        cardWeather.tempMin = weather.tempMin;

        searchPlace.save();
        weather.save();
        cardWeather.save();
    }
}