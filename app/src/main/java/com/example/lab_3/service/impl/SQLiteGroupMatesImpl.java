package com.example.lab_3.service.impl;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.lab_3.models.GroupMate;
import com.example.lab_3.service.GroupMatesHelper;
import com.example.lab_3.service.repository.GroupMatesRepository;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class SQLiteGroupMatesImpl implements GroupMatesRepository {
    private final String TABLE_NAME = "groupmates";
    private final String ID = "_id";
    private final String FIRST_NAME = "first_name";
    private final String LAST_NAME = "last_name";
    private final String MIDDLE_NAME = "middle_name";
    private final String TIME_INSERT = "time_insert";
    private GroupMatesHelper mHelper;
    private SQLiteDatabase db;

    public SQLiteGroupMatesImpl(SQLiteOpenHelper helper) {
        this.mHelper = (GroupMatesHelper) helper;
        this.db = mHelper.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;
        db.execSQL(String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT NOT NULL, %s TEXT NOT NULL, %s TEXT, %s INTEGER NOT NULL)",
                TABLE_NAME,
                ID,
                FIRST_NAME,
                LAST_NAME,
                MIDDLE_NAME,
                TIME_INSERT));
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(String.format("DROP TABLE IF EXISTS %s", TABLE_NAME));
        onCreate(db);
    }

    @Override
    public void insertGroupMate(GroupMate groupMate) {
        ContentValues values = new ContentValues();
        values.put(FIRST_NAME, groupMate.firstName);
        values.put(LAST_NAME, groupMate.lastName);
        values.put(MIDDLE_NAME, groupMate.middleName);
        values.put(TIME_INSERT, Calendar.getInstance().getTime().getTime());
        db.insertWithOnConflict(TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_REPLACE);
    }

    @Override
    public void replaceLastGroupMate(GroupMate newGroupMate) {
        ContentValues contentValues = getContentValues(newGroupMate);
        db.updateWithOnConflict(TABLE_NAME, contentValues, ID + "=(SELECT MAX(" + ID + ") FROM " + TABLE_NAME + ")", null, SQLiteDatabase.CONFLICT_REPLACE);
    }

    @Override
    public List<GroupMate> getGroupMates() {
        Cursor cursor = db.query(TABLE_NAME, new String[]{FIRST_NAME, LAST_NAME, MIDDLE_NAME, TIME_INSERT}, null, null, null, null, TIME_INSERT);
        List<GroupMate> data = new ArrayList<>();
        if (cursor == null) return null;
        if (!cursor.moveToFirst()) return null;
        int idx = -1;
        String[] fio;
        long time_insert = -1;
        do {
            fio = new String[]{
                    cursor.getString(0),
                    cursor.getString(1),
                    cursor.getString(2),
            };
            time_insert = cursor.getLong(3);
            data.add(new GroupMate(fio[0], fio[1], fio[2], time_insert));
        } while (cursor.moveToNext());
        data.forEach((item) -> {
            Log.d("ITEM", item.firstName + " " + item.lastName + " " + item.middleName);
        });
        return data;
    }

    /**
     * Формирование значений для вставки в БД
     *
     * @param groupMate - Класс с нужными полями
     * @return ContentValues заполненный нужными полями
     */
    private ContentValues getContentValues(GroupMate groupMate) {
        ContentValues values = new ContentValues();
        values.put(FIRST_NAME, groupMate.firstName);
        values.put(LAST_NAME, groupMate.lastName);
        values.put(MIDDLE_NAME, groupMate.middleName);
        values.put(TIME_INSERT, Calendar.getInstance().getTime().getTime());
        return values;
    }
}
