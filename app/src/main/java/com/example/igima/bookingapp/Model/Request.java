package com.example.igima.bookingapp.Model;

import java.util.List;

public class Request {
    private String userId;
    private String bandId;
    private Boolean valid;

    public Request() {
    }

    public Request(String userId, String bandId, Boolean valid) {
        this.userId = userId;
        this.bandId = bandId;
        this.valid = valid;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getBandId() {
        return bandId;
    }

    public void setBandId(String bandId) {
        this.bandId = bandId;
    }

    public Boolean getValid() {
        return valid;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }
}
