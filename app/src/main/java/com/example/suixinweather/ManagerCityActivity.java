package com.example.suixinweather;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.litepal.LitePal;

import java.util.Iterator;
import java.util.List;

import data.CardWeather;
import data.SearchPlace;
import data.Weather;
import tool.CardAdapter;

public class ManagerCityActivity extends AppCompatActivity implements View.OnClickListener{

    boolean isDelete = false;
    TextView manageCity;
    TextView cancelManage;
    List<CardWeather> cardWeathers;
    CardAdapter adapter;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_city);

        manageCity = (TextView) findViewById(R.id.managerCity);
        cancelManage = (TextView) findViewById(R.id.cancelManageCity);
        manageCity.setOnClickListener(this);
        cancelManage.setOnClickListener(this);
        cardWeathers = LitePal.findAll(CardWeather.class);
        recyclerView = (RecyclerView) findViewById(R.id.card_recycle);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        adapter = new CardAdapter(cardWeathers);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.managerCity){
                if (manageCity.getText().equals("管理")){
                    manageCity.setText("删除");
                    cancelManage.setVisibility(View.VISIBLE);
                    adapter.isVisibility = true;
                    Log.d("deleteCity", "onClick:  管理!");
                }else {
                    handleSelectedItem();
                    Log.d("deleteCity", "onClick:  删除!");
                }
            adapter.notifyDataSetChanged();
        }
        if (view.getId() == R.id.cancelManageCity){
            manageCity.setText("管理");
            cancelManage.setVisibility(View.GONE);
            adapter.isVisibility = false;
            adapter.notifyDataSetChanged();
            Log.d("deleteCity", "onClick:  取消!");
        }
    }

    public void handleSelectedItem(){
        List<String> selectedItems = adapter.getSelectedItems();
        Log.d("DeleteCity", "selectedItems: " + selectedItems.size());
        for (int i = 0; i < selectedItems.size(); i++) {
            String city = selectedItems.get(i);
            Log.d("DeleteCity", "cardWeather: " + city);
            LitePal.deleteAll(Weather.class,"Location = ?",city);
            LitePal.deleteAll(CardWeather.class,"placeName = ?",city);
            LitePal.deleteAll(SearchPlace.class,"placeName = ?",city);
        }
        for(String city : selectedItems){
            Iterator<CardWeather> iterator = cardWeathers.listIterator();
            while (iterator.hasNext()){
                CardWeather cardW = iterator.next();
                if (cardW.placeName.equals(city)){
                    iterator.remove();
                }
            }
        }
        adapter.setCardWeathers(cardWeathers);
        adapter.mSelectedItem.clear();
        Log.d("DeleteCity", "mSelectedItem.clear(): " + adapter.mSelectedItem.size());
    }
}