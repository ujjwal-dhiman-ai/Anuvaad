package com.dhimanujjwal.anuvaad;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.JobIntentService;
import androidx.lifecycle.MutableLiveData;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class ExampleJobIntentService extends JobIntentService
{
    private static final String TAG = "ExampleJobIntentService";

    // This is data type which can be observed by another class
    public static MutableLiveData<String> translatedText = new MutableLiveData<>(); // I made it public, better to use private and getter

    static void enqueueWork(Context context, Intent work)
    {
        enqueueWork(context, ExampleJobIntentService.class, 123, work);
    }

    @Override
    public void onCreate()
    {
        super.onCreate();
        Log.d(TAG, "onCreate");
    }

    @Override
    protected void onHandleWork(@NonNull Intent intent)
    {
        Log.d(TAG, "onHandleWork");
//      Modification
        Bundle extras = intent.getExtras();

        String input = extras.getString("input");
        String langTo = extras.getString("target_language");

        String langFrom = "en";
//        String langTo = "hi";

        // INSERT YOU URL HERE
        try
        {
            String urlStr = "https://script.google.com/macros/s/AKfycbxt1kjnGx5twzn8lGZopvHkivUCL-B8su8PLXsSRlByiAdRKgA/exec" +
                    "?q=" + URLEncoder.encode(input, "UTF-8") +
                    "&target=" + langTo +
                    "&source=" + langFrom;
            URL url = new URL(urlStr);
            StringBuilder response = new StringBuilder();
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestProperty("User-Agent", "Mozilla/5.0");
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null)
            {
                response.append(inputLine);
            }
            in.close();
            String output = response.toString();

            Log.i(TAG, "onHandleWork: " + output);

            translatedText.postValue(output);//here I am updating data
        }
        catch (IOException e)
        {
            Log.i(TAG, "onHandleWork: ERROR");
        }
    }

    @Override
    public void onDestroy()
    {
        Log.d(TAG, "onDestroy");
        super.onDestroy();
    }

    @Override
    public boolean onStopCurrentWork()
    {
        Log.d(TAG, "onStopCurrentWork");
        return super.onStopCurrentWork();
    }
}