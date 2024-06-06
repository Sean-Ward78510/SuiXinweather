package com.example.suixinweather;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import data.SearchPlace;
import okhttp3.Call;
import okhttp3.Response;
import service.HttpUtil;
import tool.SearchAdapter;

public class SearchViewActivity extends AppCompatActivity {

    public String Url_city = "https://geoapi.qweather.com/v2/city/lookup?location=";
    public String Key = "&key=9a93a0d7b2304e47bb71410b4760b03c";
    List<SearchPlace> places = new ArrayList<>();
    RecyclerView searchRecycleView;
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_view);
        Log.d("Star", "onCreate:  SearchView!");

        searchRecycleView = (RecyclerView) findViewById(R.id.search_recycleView);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        searchRecycleView.setLayoutManager(manager);

        searchView = (SearchView) findViewById(R.id.search);
        searchView.setQueryHint("搜索城市天气");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (places.size() != 0){
                    places.clear();
                }
                HttpUtil.sandOkHttpRequest(Url_city + newText + Key, new okhttp3.Callback() {
                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response){
                        try {
                            JSONObject jsonObject = new JSONObject(response.body().string());
                            if (jsonObject.getString("code").equals("200")) {
                                JSONArray jsonArray = jsonObject.getJSONArray("location");
                                Log.d("getArray", "onResponse: " + jsonArray.length());
                                for (int i = 0;i < jsonArray.length();i++){
                                    JSONObject object = jsonArray.getJSONObject(i);
                                    SearchPlace searchPlace = new SearchPlace();
                                    searchPlace.placeName = object.getString("name");
                                    searchPlace.placeId = object.getString("id");
                                    searchPlace.amd2 = object.getString("adm2") + ",";
                                    searchPlace.adm1 = object.getString("adm1");
                                    places.add(searchPlace);
                                }
                                Log.d("getArray", "onResponse: " + places.size());
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Log.d("getSearchPlaces", "run: " + places.isEmpty());
                                        showRecycleView();
                                    }
                                });
                            }else {
                                Log.d("getCitySearch", "onResponse: Error!");
                            }
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {
                    }
                });
                return false;
            }
        });
    }

    public void showRecycleView(){
        SearchAdapter adapter = new SearchAdapter(places,this);
        searchRecycleView.setAdapter(adapter);
    }
}