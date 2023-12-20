package com.example.lab_3.service.repository;

import android.database.sqlite.SQLiteDatabase;

import com.example.lab_3.models.GroupMate;

import java.util.List;

public interface GroupMatesRepository {
    void onCreate(SQLiteDatabase db);

    void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion);

    void insertGroupMate(GroupMate groupMate);

    void replaceLastGroupMate(GroupMate newGroupMate);

    List<GroupMate> getGroupMates();
}
