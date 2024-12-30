package com.example.apaloundry;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class LoginActivity extends AppCompatActivity {
    EditText edUsername, edPassword;
    Button btnLogin;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

//        kenalkan widget

        edUsername = (EditText) findViewById(R.id.edUsername);
        edPassword = (EditText) findViewById(R.id.edPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);

//        event handler
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (TextUtils.isEmpty(edUsername.getText().toString())) {
                    Toast.makeText(LoginActivity.this, "Username ga bole Kosong ya cintaa", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(edPassword.getText().toString())) {

                    Toast.makeText(LoginActivity.this, "Passwornd nya manaa cogg", Toast.LENGTH_SHORT).show();


                } else {

//                intent ketika diklik akan ke halaman home

                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.putExtra("username", edUsername.getText().toString());
//                krim data username ke aktivity main menggunakan intnt
                    startActivity(intent);
//                setelah tombol diklik kita matikan aplikasi
                    finish();
                }
            }


        });
    }

}

