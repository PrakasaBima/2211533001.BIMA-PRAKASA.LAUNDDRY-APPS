package com.example.apaloundry;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.apaloundry.Layanan.LayananActivity;
import com.example.apaloundry.pelanggan.PelangganActivity;


public class MainActivity extends AppCompatActivity {

    String username;
    CardView cvLaundry, cvLayanan, cvPelanggan, cvPromo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        username = getIntent().getStringExtra("username");
        Toast.makeText(this, " " + username, Toast.LENGTH_SHORT).show();

        cvLaundry=(CardView) findViewById(R.id.cvLaundry);
        cvLayanan=(CardView) findViewById(R.id.cvLayanan);
        cvPelanggan=(CardView) findViewById(R.id.cvPelanggan);
        cvPromo=(CardView) findViewById(R.id.cvPromo);

        cvLaundry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "LAUNDRY", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, LaundryActivity.class);
                startActivity(intent);
            }
        });

        cvLayanan.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(MainActivity.this, LayananActivity.class);
                startActivity(intent);
            }
        });

        cvPelanggan.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(MainActivity.this, PelangganActivity.class);
                startActivity(intent);
            }
        });

        cvPromo.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(MainActivity.this, PromoActivity.class);
                startActivity(intent);
            }
        });

    }
}