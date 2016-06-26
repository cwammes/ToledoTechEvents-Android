package org.techtoledo.domain;

import java.net.URL;
import java.util.Date;

/**
 * Created by cwammes on 6/17/16.
 */
public class Event {

    Date startTime;
    Date endTime;
    String uid;
    String description;
    String summary;
    String location;
    String locationShort;
    URL eventURL;

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public URL getEventURL() {
        return eventURL;
    }

    public void setEventURL(URL eventURL) {
        this.eventURL = eventURL;
    }

    public String getLocationShort() {return locationShort;}

    public void setLocationShort(String locationShort) {this.locationShort = locationShort;}
}
