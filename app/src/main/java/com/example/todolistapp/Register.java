package com.example.todolistapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;

public class Register extends AppCompatActivity {

    EditText Reg_id;
    EditText Reg_pw;
    Button Reg_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Reg_id = findViewById(R.id.Reg_id);
        Reg_pw = findViewById(R.id.Reg_pw);
        Reg_btn = findViewById(R.id.Reg_btn);

        Reg_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Register.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}