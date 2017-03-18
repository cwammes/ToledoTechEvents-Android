package org.techtoledo.domain;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.net.URL;
import java.util.Date;

/**
 * Created by cwammes on 6/17/16.
 */
public class Event implements Serializable {

    private int id;

    @SerializedName("start_time")
    private Date startTime;

    @SerializedName("end_time")
    private Date endTime;

    private String uid;
    private String description;

    @SerializedName("title")
    private String summary;
    private Venue venue;

    @SerializedName("rsvp_url")
    private String rsvpUrl;

    @SerializedName("url")
    private String eventURL;

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

    public String getEventURL() {
        return eventURL;
    }

    public void setEventURL(String eventURL) {
        this.eventURL = eventURL;
    }

    public Venue getVenue() {
        return venue;
    }

    public void setVenue(Venue venue) {
        this.venue = venue;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRsvpUrl() {
        return rsvpUrl;
    }

    public void setRsvpUrl(String rsvpUrl) {
        this.rsvpUrl = rsvpUrl;
    }
}
