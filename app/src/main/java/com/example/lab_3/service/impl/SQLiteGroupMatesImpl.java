package com.example.lab_3.service.impl;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.lab_3.models.GroupMate;
import com.example.lab_3.service.CommonService;
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

    public SQLiteGroupMatesImpl(SQLiteOpenHelper helper) {
        this.mHelper = (GroupMatesHelper) helper;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT NOT NULL, %s TEXT NOT NULL, %s TEXT, %s INTEGER NOT NULL)",
                    TABLE_NAME,
                    ID,
                    FIRST_NAME,
                    LAST_NAME,
                    MIDDLE_NAME,
                    TIME_INSERT));
        } catch (SQLException e) {
            e.printStackTrace();
            CommonService.getInstance().showToast(e.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            db.execSQL(String.format("DROP TABLE IF EXISTS %s", TABLE_NAME));
            onCreate(db);
        } catch (SQLException e) {
            e.printStackTrace();
            CommonService.getInstance().showToast(e.getMessage());
        }
    }

    @Override
    public void insertGroupMate(GroupMate groupMate) {
        ContentValues values = getContentValues(groupMate);
        try {
            mHelper.getWritableDatabase().insertWithOnConflict(TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_REPLACE);
        } catch (SQLException e) {
            e.printStackTrace();
            CommonService.getInstance().showToast(e.getMessage());
        }
    }

    @Override
    public void replaceLastGroupMate(GroupMate newGroupMate) {
        ContentValues contentValues = getContentValues(newGroupMate);
        try {
            mHelper.getWritableDatabase().updateWithOnConflict(TABLE_NAME, contentValues, ID + "=(SELECT MAX(" + ID + ") FROM " + TABLE_NAME + ")", null, SQLiteDatabase.CONFLICT_REPLACE);
        } catch (SQLException e) {
            e.printStackTrace();
            CommonService.getInstance().showToast(e.getMessage());
        }
    }

    @Override
    public List<GroupMate> getGroupMates() {
        List<GroupMate> data = new ArrayList<>();
        try {
            Cursor cursor = mHelper.getWritableDatabase().query(TABLE_NAME, new String[]{ID, FIRST_NAME, LAST_NAME, MIDDLE_NAME, TIME_INSERT}, null, null, null, null, TIME_INSERT);
            if (cursor == null) return null;
            if (!cursor.moveToFirst()) return null;
            String[] fio;
            Integer id = -1;
            long time_insert = -1;
            do {
                id = cursor.getInt(0);
                fio = new String[]{
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                };
                time_insert = cursor.getLong(4);
                GroupMate mate = new GroupMate(fio[0], fio[1], fio[2], time_insert);
                mate.setId(id);
                data.add(mate);
            } while (cursor.moveToNext());
        } catch (RuntimeException e) {
            e.printStackTrace();
            CommonService.getInstance().showToast(e.getMessage());
        }
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
