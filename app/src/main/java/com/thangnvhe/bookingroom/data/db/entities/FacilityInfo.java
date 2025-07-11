package com.thangnvhe.bookingroom.data.db.entities;

import java.io.Serializable;

public class FacilityInfo implements Serializable {
    public int id;
    public String name;

    public FacilityInfo(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return name; // Để hiển thị trong Spinner
    }
}