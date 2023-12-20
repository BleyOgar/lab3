package com.example.lab_3.ui;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.EditText;

import androidx.annotation.NonNull;

import com.example.lab_3.databinding.DialogInsertGroupmateBinding;
import com.example.lab_3.models.GroupMate;

public class DialogInsertGroupMate extends Dialog {
    interface ResultListener {
        void accept(final GroupMate groupMate);
    }

    private DialogInsertGroupmateBinding binding;
    private ResultListener mListener;

    public DialogInsertGroupMate(@NonNull Context context, ResultListener listener) {
        super(context);
        this.mListener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        binding = DialogInsertGroupmateBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                binding.btnAccept.setEnabled(canAccept());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };

        binding.teFirstName.addTextChangedListener(textWatcher);
        binding.teMiddleName.addTextChangedListener(textWatcher);
        binding.teLastName.addTextChangedListener(textWatcher);

        binding.btnCancel.setOnClickListener((v) -> dismiss());
        binding.btnAccept.setOnClickListener(this::onAccept);
    }

    /**
     * Обработка нажатия на кнопку "Применить"
     *
     * @param v
     */
    private void onAccept(View v) {
        GroupMate groupMate = new GroupMate(String.format("%s %s %s", getText(binding.teLastName), getText(binding.teFirstName), getText(binding.teMiddleName)), null);
        mListener.accept(groupMate);
        dismiss();
    }

    /**
     * Имя - required
     * Фамилия - required
     * Отчество - optional
     */
    private boolean canAccept() {
        return !(getText(binding.teFirstName).isEmpty() || getText(binding.teLastName).isEmpty());
    }

    /**
     * Получение текста из EditText
     */
    private String getText(EditText v) {
        return v.getText().toString();
    }
}