package com.example.lab_3.service;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.lab_3.service.impl.SQLiteGroupMatesImpl;
import com.example.lab_3.service.repository.GroupMatesRepository;

public class GroupMatesHelper extends SQLiteOpenHelper {
    private static GroupMatesHelper instance;
    public static final String DB_NAME = "lab3_db";
    public static final int DB_VERSION = 2;
    public GroupMatesRepository groupMatesRepository;

    public static GroupMatesHelper getInstance() {
        return instance;
    }

    public static GroupMatesHelper createInstance(Context context) {
        if (instance == null) instance = new GroupMatesHelper(context);
        return instance;
    }

    public GroupMatesHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    public void initialize() {
        this.groupMatesRepository = new SQLiteGroupMatesImpl(this);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        groupMatesRepository.onCreate(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        groupMatesRepository.onUpgrade(db, oldVersion, newVersion);
    }
}
