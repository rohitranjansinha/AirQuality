package com.example.airquality;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Profile extends AppCompatActivity {

    private Button logout_btn;
    private FirebaseAuth mAuth;
    private static int splashTime = 1500;
    AutoCompleteTextView country;
    AutoCompleteTextView state;
    AutoCompleteTextView city;
    private ArrayList<String> countries;
    private ArrayList<String> states;
    private ArrayList<String> cities;
    private Button submit_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        country = (AutoCompleteTextView)findViewById(R.id.country);
        submit_btn = (Button)findViewById(R.id.submit_profile);
        state = (AutoCompleteTextView)findViewById(R.id.state);
        city = (AutoCompleteTextView)findViewById(R.id.city);
        //Disable City And State
        state.setEnabled(false);
        city.setEnabled(false);

        countries = new ArrayList<>();
        states = new ArrayList<>();
        cities = new ArrayList<>();

        //Genearting list of countries

        countries.clear();

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
                    Profile.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(),R.layout.support_simple_spinner_dropdown_item,countries);
                            country.setAdapter(adapter);
                        }
                    });
                }
            }
        });


        country.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                states.clear();
                state.setEnabled(true);
                final String desh = country.getText().toString();
                //Generating List of States

                OkHttpClient client_state =  new OkHttpClient();
                String url_state = "https://api.airvisual.com/v2/states?country="+desh+"&key=a87a76af-daef-4131-b944-a37fc741b117";

                Request request_state = new Request.Builder()
                        .url(url_state)
                        .build();

                client_state.newCall(request_state).enqueue(new Callback() {
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
                                    String rajya = ob.getString("state");
                                    states.add(rajya);
                                }
                                Log.d("hellostate"+String.valueOf(countries.size()),"$$%$");
                            }
                            catch (JSONException e){
                                e.printStackTrace();
                            }
                            // Log.d(String.valueOf(arr.length),"##");
                            Profile.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    ArrayAdapter<String> adapter_state = new ArrayAdapter<>(getApplicationContext(),R.layout.support_simple_spinner_dropdown_item,states);
                                    state.setAdapter(adapter_state);
                                }
                            });
                        }
                    }
                });


                state.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        cities.clear();
                        //For Cities
                        city.setEnabled(true);
                        String query_state = state.getText().toString();
                        String query_desh = country.getText().toString();
                        OkHttpClient client_city =  new OkHttpClient();
                        String url_city = "https://api.airvisual.com/v2/cities?state="+query_state+"&country="+query_desh+"&key=a87a76af-daef-4131-b944-a37fc741b117";


                        Request request_city = new Request.Builder()
                                .url(url_city)
                                .build();

                        client_city.newCall(request_city).enqueue(new Callback() {
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
                                            Log.d("AreeBCCity","#$#$");
                                        }else{
                                            Log.d("AiseKaiseBCCity","%$%$#");

                                        }
                                        Log.d("hey"+String.valueOf(data.length()),"#@##@@");
                                        for(int i=0; i<data.length();i++){
                                            JSONObject ob = data.getJSONObject(i);
                                            String town = ob.getString("city");
                                            cities.add(town);
                                        }
                                        Log.d("hello"+String.valueOf(cities.size()),"$$%$");
                                    }
                                    catch (JSONException e){
                                        e.printStackTrace();
                                    }
                                    // Log.d(String.valueOf(arr.length),"##");
                                    Profile.this.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            ArrayAdapter<String> adapter_city = new ArrayAdapter<>(getApplicationContext(),R.layout.support_simple_spinner_dropdown_item,cities);
                                            city.setAdapter(adapter_city);
                                        }
                                    });
                                }
                            }
                        });
                    }
                });

            }
        });


        //Logout Button
        logout_btn = (Button)findViewById(R.id.logout_btn);
        logout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutPress();
            }
        });


        //submit Button
        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    String desh = country.getText().toString();
                    String rajya = state.getText().toString();
                    String seher = city.getText().toString();
                    if(desh.equals("")){
                        country.setError("Select a country");
                        country.requestFocus();
                        return;
                    }
                    if(rajya.equals("")){
                        state.setError("Select a state");
                        state.requestFocus();
                        return;
                    }
                    if(seher.equals("")){
                        city.setError("Select a city");
                        city.requestFocus();
                        return;
                    }
                    Intent final_intent = new Intent(getApplicationContext(),Final.class);
                    final_intent.putExtra("country",desh);
                    final_intent.putExtra("state",rajya);
                    final_intent.putExtra("city",seher);
                    startActivity(final_intent);
            }
        });

    }

    public void logoutPress(){
        SharedPreferences sharedPref = getSharedPreferences("LoginData", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("email","");
        editor.putString("password","");
        editor.commit();
        FirebaseAuth.getInstance().signOut();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                finish();
            }
        },splashTime);


    }


}
