package org.techtoledo.dao;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.techtoledo.domain.CacheStatus;
import org.techtoledo.domain.Event;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by chris on 2/22/17.
 */

public class CacheStatusDAO {

    private static final String cacheFile = "cacheStatus.json";
    private static final String TAG = "CacheStatusDAO";

    public CacheStatus getCacheStatus(Context context){


        try {
            FileInputStream fin = context.openFileInput(cacheFile);
            String fileString = processInputStream(fin);

            if(fileString != null && fileString != "") {

                Gson gson = new Gson();
                CacheStatus cacheStatus = gson.fromJson(fileString, CacheStatus.class);
                return cacheStatus;
            }
            else{
                CacheStatus cacheStatus = createCacheStatus();
                setCacheStatus(context, cacheStatus);
                return cacheStatus;
            }

        } catch (Exception e) {
            e.printStackTrace();

            CacheStatus cacheStatus = createCacheStatus();
            setCacheStatus(context, cacheStatus);
            return cacheStatus;
        }
    }

    public void setCacheStatus(Context context, CacheStatus cacheStatus){

        Gson gson = new Gson();
        String cacheStatusStr = gson.toJson(cacheStatus);
        Log.d(TAG, cacheStatusStr);

        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput(cacheFile, Context.MODE_PRIVATE));
            outputStreamWriter.write(cacheStatusStr);
            outputStreamWriter.close();
            Log.d(TAG, "Successfully wrote to " + cacheFile);
        }
        catch (IOException e) {
            Log.e(TAG, "File write failed: " + e.toString());
        }


    }

    private String processInputStream(InputStream is){

        String returnStr = "";


        String line;
        String prevLine = "";
        try {
            StringBuilder result = new StringBuilder();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            while ((line = rd.readLine()) != null) {

                result.append(line + "\n");
            }
            rd.close();
            returnStr =  result.toString();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return returnStr;
    }

    private CacheStatus createCacheStatus(){

        CacheStatus cacheStatus = new CacheStatus();
        cacheStatus.setMaxCacheDate(new Date(System.currentTimeMillis() - 1000));
        cacheStatus.setMinCacheDate(new Date(System.currentTimeMillis() - 1000));
        cacheStatus.setCacheDate(new Date(System.currentTimeMillis()));

        return cacheStatus;
    }
}
