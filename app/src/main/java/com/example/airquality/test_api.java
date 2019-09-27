package com.example.airquality;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class test_api extends AppCompatActivity {

    private TextView mResult;
    private String []arr;
    private ArrayList<String> countries;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_api);
        mResult = (TextView)findViewById(R.id.result_test);
        countries = new ArrayList<>();
        OkHttpClient client =  new OkHttpClient();
        String url = "https://api.airvisual.com/v2/countries?key=a87a76af-daef-4131-b944-a37fc741b117";

        Request request = new Request.Builder()
                .url(url)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.d("onFailure","##");
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if(response.isSuccessful()){
                    final String myResponse = response.body().string();


                    try {
                        JSONObject file = new JSONObject(myResponse);
                        JSONArray data = file.getJSONArray("data");
                        if(data==null){
                            Log.d("AreeBC","#$#$");
                        }else{
                            Log.d("AiseKaiseBC","%$%$#");

                        }
                        Log.d("hey"+String.valueOf(data.length()),"#@##@@");
                        for(int i=0; i<data.length();i++){
                            JSONObject ob = data.getJSONObject(i);
                            String nation = ob.getString("country");
                            countries.add(nation);
                        }
                        Log.d("hello"+String.valueOf(countries.size()),"$$%$");
                    }
                    catch (JSONException e){
                        e.printStackTrace();
                    }
                   // Log.d(String.valueOf(arr.length),"##");
                    test_api.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
//                            for(int i=0;i<arr.length;i++){
//                                mResult.append(arr[i]+"\n");
//                            }
                            mResult.setText(myResponse);
                        }
                    });
                }
            }
        });
    }
}
