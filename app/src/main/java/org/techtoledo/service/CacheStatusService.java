package org.techtoledo.service;

/**
 * Created by chris on 2/22/17.
 */

import android.content.Context;
import android.util.Log;

import org.techtoledo.dao.CacheStatusDAO;
import org.techtoledo.domain.CacheStatus;

import java.util.Date;

public class CacheStatusService {

    private static final int minCacheStatusLength = 14400000;  // 4 hours
    private static final int maxCacheStatusLength = 86400000;  // 1 day
    private CacheStatusDAO cacheStatusDAO;
    private static final String TAG = "Cache Status Service";

    public CacheStatusService(){
        this.cacheStatusDAO = new CacheStatusDAO();
    }

    public boolean hasCacheExpired(Context context){

        CacheStatus cacheStatus = getCacheStatus(context);
        Date currentDate = new Date();

        if(cacheStatus == null){
            Log.d(TAG, "Cache has expired");
            return true;
        }
        else if(currentDate.compareTo(cacheStatus.getMaxCacheDate()) > 0){
            Log.d(TAG, "Cache has expired");
            return true;
        }
        else{
            Log.d(TAG, "Cache has not expired");
            return false;
        }

    }

    public void resetCacheStatus(Context context){

        Log.d(TAG, "Reset Cache");

        CacheStatus cacheStatus = new CacheStatus();
        cacheStatus.setCacheDate(new Date());
        cacheStatus.setMinCacheDate(new Date(System.currentTimeMillis() + minCacheStatusLength));
        cacheStatus.setMaxCacheDate(new Date(System.currentTimeMillis() + maxCacheStatusLength));

        //Save cacheStatus
        this.cacheStatusDAO.setCacheStatus(context, cacheStatus);

    }

    public void expireCacheStatus(Context context){

        Log.d(TAG, "Expire Cache");

        CacheStatus cacheStatus = getCacheStatus(context);
        cacheStatus.setMinCacheDate(cacheStatus.getCacheDate());
        cacheStatus.setMaxCacheDate(cacheStatus.getCacheDate());

        //Save cacheStatus
        this.cacheStatusDAO.setCacheStatus(context, cacheStatus);

    }

    public void updateMaxCacheDate(Context context){

        Log.d(TAG, "Update Max Cache Date");
        CacheStatus cacheStatus = getCacheStatus(context);
        cacheStatus.setMaxCacheDate(cacheStatus.getMinCacheDate());

        this.cacheStatusDAO.setCacheStatus(context, cacheStatus);

    }

    public Date getCacheStatusDate(Context context){

        CacheStatus cacheStatus = getCacheStatus(context);

        return cacheStatus.getCacheDate();
    }

    private CacheStatus getCacheStatus(Context context){

        return this.cacheStatusDAO.getCacheStatus(context);
    }
}
