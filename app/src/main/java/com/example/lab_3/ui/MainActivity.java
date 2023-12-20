package com.example.lab_3.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lab_3.databinding.ActivityMainBinding;
import com.example.lab_3.models.GroupMate;
import com.example.lab_3.service.Service;


public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);

        binding.btnGroupmates.setOnClickListener(this::showGroupMates);
        binding.btnInsert.setOnClickListener(this::insertGroupMate);
        binding.btnReplace.setOnClickListener(this::replaceGroupMate);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void showGroupMates(View v) {
        Intent intent = new Intent(this, GroupMatesActivity.class);
        startActivity(intent);
    }

    private void insertGroupMate(View v) {
        DialogInsertGroupMate dialog = new DialogInsertGroupMate(this, (groupMate) -> {
            Service.getInstance().groupMatesRepository.insertGroupMate(groupMate);
        });
        dialog.show();
    }

    private void replaceGroupMate(View v) {
        Service.getInstance().groupMatesRepository.replaceLastGroupMate(new GroupMate("Иванов Иван Иванович", null));
    }
}