package com.example.android.fortnitetracker.Objects;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

public class ChallengeObjects {

    public int id;

    private String challenge;
    private String total;
    private String stars;

    private boolean isSelected;

    public ChallengeObjects(String challenge, String total, String stars) {
        this.challenge = challenge;
        this.total = total;
        this.stars = stars;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setChallenge(String challenge) {
        this.challenge = challenge;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public void setStars(String stars) {
        this.stars = stars;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public int getId() {
        return id;
    }

    public String getChallenge() {
        return challenge;
    }

    public String getTotal() {
        return total;
    }

    public String getStars() {
        return stars;
    }

    public boolean isSelected() {
        return isSelected;
    }
}
