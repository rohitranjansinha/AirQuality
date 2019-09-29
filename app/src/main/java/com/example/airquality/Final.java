package com.example.airquality;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Final extends AppCompatActivity {

    private TextView result;
    private TextView aqi_res;
    private String AQI;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final);

        


        result = (TextView)findViewById(R.id.heading);
        aqi_res = (TextView)findViewById(R.id.aqi_text);

        Intent intent = getIntent();
        String country = intent.getExtras().getString("country");
        String state = intent.getExtras().getString("state");
        String city = intent.getExtras().getString("city");

        OkHttpClient client =  new OkHttpClient();
        String url = "https://api.waqi.info/feed/"+city+"/?token=51a195cf0279fee2db917ef004a508099e3ba680";

        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
                Log.d("AQIFail","&^&^");
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if(response.isSuccessful()){
                    final String myResponse = response.body().string();

                    try {
                        JSONObject file = new JSONObject(myResponse);
                        JSONObject data = file.getJSONObject("data");
                        AQI = data.getString("aqi");
                    }catch (JSONException e){
                        e.printStackTrace();
                    }
                    Final.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            aqi_res.setText(AQI);
                        }
                    });
                }

            }
        });
        result.setText("Country: "+country+"\nState: "+state+"\nCity: "+city);
    }
}


