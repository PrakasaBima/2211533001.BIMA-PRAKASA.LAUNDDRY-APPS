package com.example.apaloundry.Layanan;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.apaloundry.R;
import com.example.apaloundry.database.SQLiteHelper;
import com.example.apaloundry.model.ModelLayanan;

import java.util.UUID;


public class LayananAddActivity extends AppCompatActivity {
    EditText layanan, harga;
    Button btnSimpan, btnBatal;

    SQLiteHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_addlayanan);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        layanan = (EditText) findViewById(R.id.edLayAddLayanan);
        harga = (EditText) findViewById(R.id.edLayAddHarga);
        btnSimpan = (Button) findViewById(R.id.btnLayAddSimpan);
        btnBatal = (Button) findViewById(R.id.btnLayAddBatal);

        db = new SQLiteHelper(LayananAddActivity.this);

        btnSimpan.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                ModelLayanan lm = new ModelLayanan();
                String uniqueID = UUID.randomUUID().toString();
                lm.setId("" + uniqueID);
                lm.setNamaLayanan(layanan.getText().toString());
                lm.setHarga(harga.getText().toString());

                Toast.makeText(LayananAddActivity.this, "" + lm.getId() + lm.getNamaLayanan() + lm.getHarga(), Toast.LENGTH_SHORT).show();


                boolean cek = db.insertLayanan(lm);
                if (cek == true) {
                    Toast.makeText(LayananAddActivity.this, "Data berhasil disimpan", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LayananAddActivity.this, LayananActivity.class));
                    finish();
                } else {
                    Toast.makeText(LayananAddActivity.this, "Data gagal disimpan", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnBatal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}

