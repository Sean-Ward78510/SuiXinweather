package com.example.suixinweather;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.bumptech.glide.Glide;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONException;
import org.json.JSONObject;
import org.litepal.LitePal;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import data.BackgroundURL;
import data.Weather;
import okhttp3.Call;
import okhttp3.Response;
import service.HttpUtil;
import tool.MyViewPagerAdapter;

public class SuixinWeatherActivity extends AppCompatActivity{
    String LL;
    boolean isSaveBackground;
    String tempBackgroundUrl;
    Toolbar toolbar;
    ViewPager viewPager;
    TabLayout tableLayout;

    String bingBackUrl;
    Calendar calendar;
    BackgroundURL backgroundURL;
    ArrayList fragments = new ArrayList();
    List<String> pageTitles = new ArrayList<>();
    List<Weather> weathers;
    MyViewPagerAdapter myViewPagerAdapter;

    private ImageView bingPic;
    public LocationClient mLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        setContentView(R.layout.activity_suixin_weather);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        bingPic = (ImageView) findViewById(R.id.bing_picture);
        setSupportActionBar(toolbar);

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        tableLayout = (TabLayout) findViewById(R.id.tabLayout);
        viewPager.setOffscreenPageLimit(5);
        myViewPagerAdapter = new MyViewPagerAdapter(getSupportFragmentManager(), fragments, pageTitles);
        viewPager.setAdapter(myViewPagerAdapter);

        askPermission();
        LitePal.initialize(this);//初始化数据库
        initBackground();
        requestLocation();
    }
    @Override
    protected void onRestart() {
        super.onRestart();
        weathers = LitePal.findAll(Weather.class);
        if (fragments.size() > 1){
            fragments.subList(1,fragments.size()).clear();
            pageTitles.subList(1,pageTitles.size()).clear();
        }
        if (!weathers.isEmpty()){
            for (Weather weather : weathers){
                fragments.add(new WeatherFragment(weather));
                pageTitles.add(weather.Location);
            }
        }
        initBackground();
        myViewPagerAdapter.setFragments(fragments);
        myViewPagerAdapter.setPageTitles(pageTitles);
        myViewPagerAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.search){
            Intent intent = new Intent(SuixinWeatherActivity.this, SearchViewActivity.class);
            startActivity(intent);
        }
        if (item.getItemId() == R.id.manager){
            Intent intent = new Intent(SuixinWeatherActivity.this, ManagerCityActivity.class);
            startActivity(intent);
        }
        return true;
    }

    public void askPermission(){//申请权限
        try {
            List<String> permissionList = new ArrayList<>();
            if (ContextCompat.checkSelfPermission(SuixinWeatherActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED){
                permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
            }
            if (ContextCompat.checkSelfPermission(SuixinWeatherActivity.this,Manifest.permission.READ_PHONE_STATE )
                    != PackageManager.PERMISSION_GRANTED){
                permissionList.add(Manifest.permission.READ_PHONE_STATE);
            }
            if (ContextCompat.checkSelfPermission(SuixinWeatherActivity.this,Manifest.permission.WRITE_EXTERNAL_STORAGE )
                    != PackageManager.PERMISSION_GRANTED){
                permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            }
            if (!permissionList.isEmpty()){
                String[] permissions = permissionList.toArray(new String[permissionList.size()]);
                ActivityCompat.requestPermissions(SuixinWeatherActivity.this,permissions,1);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0) {
                    for(int result : grantResults){
                        if (result != PackageManager.PERMISSION_GRANTED) {
                            Toast.makeText(this, "必须所有权限都同意才能使用本应用！", Toast.LENGTH_SHORT).show();
                            finish();
                            return;
                        }
                    }
                }else {
                    Toast.makeText(this, "发生未知错误！", Toast.LENGTH_SHORT).show();
                    finish();
                }
        }
    }
    public void requestLocation() {
        LocationClient.setAgreePrivacy(true);
        Log.d("location", "requestLocation: here is requestLocation!");
        try {
            mLocationClient = new LocationClient(getApplicationContext());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Battery_Saving);
        option.setCoorType("bd09ll");
        option.setFirstLocType(LocationClientOption.FirstLocType.ACCURACY_IN_FIRST_LOC);
        mLocationClient.setLocOption(option);

        mLocationClient.registerLocationListener(new MyLocationListener());
        mLocationClient.start();
    }

    public class MyLocationListener implements BDLocationListener {//百度位置监听器

        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            double latitude = bdLocation.getLatitude();//纬度
            double longitude = bdLocation.getLongitude();//经度
            DecimalFormat df = new DecimalFormat("#.##");
            String Lat = df.format(latitude);
            String Lon = df.format(longitude);
            LL  = Lon + "," + Lat;
            Log.d("location0", "onReceiveLocation: " + LL);
            initUI();
        }
    }

    public void initUI(){
        Log.d("location", "onCreate: initUI has executed");
        Log.d("location", "initUI:  " + LL);
        weathers = LitePal.findAll(Weather.class);
        Log.d("DataBase", "onCreate2: " + weathers.size());
        if (!weathers.isEmpty()){
            Log.d("location", "!empty:  " + LL);
            fragments.add(new LocalFragment(LL));

            pageTitles.add("当前");
            for (Weather weather : weathers){
                fragments.add(new WeatherFragment(weather));
                pageTitles.add(weather.Location);
            }
        }else {
            Log.d("location", "empty:  " + LL);
            fragments.add(new WeatherFragment(LL));
            pageTitles.add("当前");
        }
        myViewPagerAdapter.notifyDataSetChanged();
        tableLayout.setupWithViewPager(viewPager);
    }

    public void initBackground(){
        calendar = Calendar.getInstance();
        backgroundURL = LitePal.findFirst(BackgroundURL.class);
        Log.d("background", "initBackground: 1");
        if (backgroundURL == null){
            isSaveBackground = true;
            backgroundURL = new BackgroundURL();
            loadBingPic();
            Log.d("background", "initBackground: backgroundURL == null");
        }else {
            bingBackUrl = backgroundURL.bingUrl;
            if (backgroundURL.day != calendar.get(Calendar.DAY_OF_MONTH)){
                isSaveBackground = false;
                tempBackgroundUrl = backgroundURL.bingUrl;
                loadBingPic();
                Log.d("background", "initBackground: backgroundURL != null");
            }else {
                Log.d("background", "initBackground: backgroundURL != null,database!");
                Log.d("background", "have load background!!" + bingBackUrl);
                Glide.with(this)
                        .load(bingBackUrl)
                        .into(bingPic);
                Log.d("background", "have load background!!");
            }
        }
    }

    public void loadBingPic(){
        String Url_bing = "https://www.bing.com/HPImageArchive.aspx?format=js&idx=0&n=1&mkt=en-US";
        HttpUtil.sandOkHttpRequest(Url_bing, new okhttp3.Callback() {
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                try {
                    JSONObject json = new JSONObject(response.body().string());
                    String imageUrl = json.getJSONArray("images")
                            .getJSONObject(0)
                            .getString("url");
                    bingBackUrl = "https://www.bing.com" + imageUrl;
                    backgroundURL.bingUrl = bingBackUrl;
                    backgroundURL.day = calendar.get(Calendar.DAY_OF_MONTH);
                    if (isSaveBackground){
                        Log.d("background", "save: " + backgroundURL.day + "   " + backgroundURL.bingUrl);
                        backgroundURL.save();
                    }else {
                        backgroundURL.updateAll("bingUrl = ?",tempBackgroundUrl);
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Glide.with(SuixinWeatherActivity.this).load(bingBackUrl).into(bingPic);
                            Log.d("background", "run: " + bingBackUrl);
                        }
                    });
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
            }
        });
    }
//    public class MyAsyncTask extends AsyncTask<Void,Void,Void> {
//        @Override
//        protected Void doInBackground(Void... voids){
//            initUI();
//            return null;
//        }
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            initBackground();
//        }
//    }

}