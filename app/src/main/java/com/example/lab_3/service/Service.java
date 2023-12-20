package com.example.lab_3.service;

import android.content.Context;

import com.example.lab_3.service.impl.SQLiteGroupMatesImpl;
import com.example.lab_3.service.repository.GroupMatesRepository;

public class Service {
    private static Service instance;
    public GroupMatesRepository groupMatesRepository;

    public static Service getInstance() {
        return instance;
    }

    public static Service createInstance(Context context) {
        if (instance == null) instance = new Service(context);
        return instance;
    }

    public Service(Context context) {
        GroupMatesHelper helper = new GroupMatesHelper(context);
        this.groupMatesRepository = new SQLiteGroupMatesImpl(helper);
    }
}
