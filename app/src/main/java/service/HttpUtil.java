package service;

import android.util.Log;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HttpUtil {

    public static void sandOkHttpRequest(String address,okhttp3.Callback callback){
        OkHttpClient client  = new OkHttpClient();
        Request request = new Request.Builder()
                .url(address)
                .build();
        client.newCall(request).enqueue(callback);
    }
public static void sandOkHttpRequest(String address){
    Thread Okhttp = new Thread(new Runnable() {
        @Override
        public void run() {
            try {
                OkHttpClient client  = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(address)
                        .build();
                Response response = client.newCall(request).execute();
                String respondData = response.body().string();
                Log.d("getNow1", "run: " + respondData);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    });

    Okhttp.start();
    try {
        Okhttp.join();
    } catch (InterruptedException e) {
        throw new RuntimeException(e);
    }
}
}
