package com.example.finalweatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {
String city="London";
    public class  DownloadTask extends AsyncTask<String,Void,String> {
        @Override
        protected String doInBackground(String... urls) {
            String result="";
            URL url;
            HttpURLConnection urlConnection=null;
            try {
                url=new URL(urls[0]);
                urlConnection=(HttpURLConnection) url.openConnection();
                InputStream in=urlConnection.getInputStream();
                InputStreamReader reader=new InputStreamReader(in);
                int data =reader.read();
                while (data!=-1){
                    char current=(char)data;
                    result+=current;
                    data=reader.read();
                }try {
                    JSONObject jsonObject = new JSONObject(result);
                    result=jsonObject.getString("weather");
                    JSONArray jsonArray=new JSONArray(result);
                    //for (int i=0;i<jsonArray.length();i++){
                        JSONObject jsonPart= jsonArray.getJSONObject(0);
                        result=jsonPart.getString("description");
                       // Log.i("main",jsonPart.getString("description"));
                   // }
                    return result;
                }catch (JSONException e){
                    e.printStackTrace();
                    Log.i("fffffffffffffffffffffff","jsonffffffffffffffff");
                    return "Failed";
                }
            }
            catch (Exception e){
                e.printStackTrace();
                Log.i("fffffffffffffffffffffff",String.valueOf(e));
                return "Failed";
            }
        }
    }


    TextView textView;
    TextView textView2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView=(TextView) findViewById(R.id.textView);
        textView2=(TextView) findViewById(R.id.textView2);
    }
    public void on(View view){
        DownloadTask downloadTask=new DownloadTask();
        String result= null;
        city=String.valueOf(textView.getText());
        try {
            result = downloadTask.execute("https://samples.openweathermap.org/data/2.5/weather?q="+"London,uk"+"&appid=439d4b804bc8187953eb36d2a8c26a02").get();
            Log.i("resultttttttttttttt",result);
            textView2.setText(result);
        } catch (ExecutionException e) {
            e.printStackTrace();
            Log.i("kkkkkkk","kkkkkkkkk");
        } catch (InterruptedException e) {
            e.printStackTrace();
            Log.i("inter","inteeeeeeeeeeeer");
        }

    }
}