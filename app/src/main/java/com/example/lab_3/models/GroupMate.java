package com.example.lab_3.models;

import androidx.annotation.Nullable;

public class GroupMate {
    public final String firstName;
    public final String lastName;
    public final String middleName;
    public Long timeInsert;
    @Nullable
    public Integer _id;

    public GroupMate(String firstName, String lastName, String middleName, @Nullable Long timeInsert) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.timeInsert = timeInsert;
    }

    public void setId(int id) {
        this._id = id;
    }
}
