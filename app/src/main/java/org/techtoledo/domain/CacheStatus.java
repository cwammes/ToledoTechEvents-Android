package org.techtoledo.domain;

/**
 * Created by chris on 2/22/17.
 */


import java.io.Serializable;
import java.util.Date;

public class CacheStatus implements Serializable {

    private Date cacheDate;
    private Date minCacheDate;
    private Date maxCacheDate;

    public Date getCacheDate() {
        return cacheDate;
    }

    public void setCacheDate(Date cacheDate) {
        this.cacheDate = cacheDate;
    }

    public Date getMinCacheDate() {
        return minCacheDate;
    }

    public void setMinCacheDate(Date minCacheDate) {
        this.minCacheDate = minCacheDate;
    }

    public Date getMaxCacheDate() {
        return maxCacheDate;
    }

    public void setMaxCacheDate(Date maxCacheDate) {
        this.maxCacheDate = maxCacheDate;
    }
}
