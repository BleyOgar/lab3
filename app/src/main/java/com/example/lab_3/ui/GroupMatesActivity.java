package com.example.lab_3.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lab_3.R;
import com.example.lab_3.databinding.ActivityGroupmatesBinding;
import com.example.lab_3.databinding.ItemGroupmateBinding;
import com.example.lab_3.models.GroupMate;
import com.example.lab_3.service.GroupMatesHelper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class GroupMatesActivity extends AppCompatActivity {
    private ActivityGroupmatesBinding binding;
    private GroupMatesAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGroupmatesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        binding.rvGroupMates.setLayoutManager(new LinearLayoutManager(this));
        List<GroupMate> groupMates = GroupMatesHelper.getInstance().groupMatesRepository.getGroupMates();
        mAdapter = new GroupMatesAdapter(this, groupMates);
        binding.rvGroupMates.setAdapter(mAdapter);
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;

    }

    class GroupMatesAdapter extends RecyclerView.Adapter<GroupMatesAdapter.GroupMateViewHolder> {
        private LayoutInflater mInflater;
        private List<GroupMate> mData;

        public GroupMatesAdapter(Context context, List<GroupMate> data) {
            mInflater = LayoutInflater.from(context);
            mData = data;
            notifyDataSetChanged();
        }

        @Override
        public int getItemCount() {
            return mData.size();
        }

        @Override
        public void onBindViewHolder(@NonNull GroupMateViewHolder holder, int position) {
            if (mData == null) {
                return;
            }
            holder.setData(mData.get(position));
        }

        @NonNull
        @Override
        public GroupMateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = mInflater.inflate(R.layout.item_groupmate, parent, false);
            return new GroupMateViewHolder(view);
        }


        class GroupMateViewHolder extends RecyclerView.ViewHolder {
            private View mItemView;
            private ItemGroupmateBinding binding;

            public GroupMateViewHolder(View itemView) {
                super(itemView);
                mItemView = itemView;
            }

            public void setData(GroupMate groupMate) {
                ((TextView) mItemView.findViewById(R.id.tv_fio)).setText(String.format("ФИО: %s %s %s", groupMate.firstName, groupMate.lastName, groupMate.middleName));
                ((TextView) mItemView.findViewById(R.id.tv_timeInsert)).setText(groupMate.timeInsert == null ? "" : "Дата добавления: " + new SimpleDateFormat("yyyy-mm-dd hh:mm:ss").format(
                        new Date(groupMate.timeInsert)));
            }
        }
    }
}