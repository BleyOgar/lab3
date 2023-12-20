package com.example.lab_3.models;

import androidx.annotation.Nullable;

public class GroupMate {
    public final String fio;
    public Long timeInsert;
    @Nullable
    public Integer _id;

    public GroupMate(String fio, @Nullable Long timeInsert) {
        this.fio = fio;
        this.timeInsert = timeInsert;
    }

    public void setId(int id) {
        this._id = id;
    }
}
