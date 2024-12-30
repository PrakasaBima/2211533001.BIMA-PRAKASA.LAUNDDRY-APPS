package com.example.apaloundry.pelanggan;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.apaloundry.R;
import com.example.apaloundry.database.SQLiteHelper;
import com.example.apaloundry.model.ModelPelanggan;

import java.util.UUID;

public class PelangganEditActivity extends AppCompatActivity {
    private String id, nama, email, hp;
    private EditText edPelEditNama, edPelEditEmail, edPelEditHp;
    private Button btnPelEditSimpan, btnPelEditHapus, btnPelEditBatal;

    SQLiteHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pelanggan_edit);

        id = getIntent().getStringExtra("ID");
        nama = getIntent().getStringExtra("NAMA");
        email = getIntent().getStringExtra("EMAIL");
        hp = getIntent().getStringExtra("HP");

        db = new SQLiteHelper(this);
        edPelEditNama = findViewById(R.id.edPelEditNama);
        edPelEditEmail = findViewById(R.id.edPelEditEmail);
        edPelEditHp = findViewById(R.id.edPelEditHp);
        btnPelEditSimpan = findViewById(R.id.btnPelEditSimpan);
        btnPelEditHapus = findViewById(R.id.btnPelEditHapus);
        btnPelEditBatal = findViewById(R.id.btnPelEditBatal);

        // Set data pelanggan ke EditText
        edPelEditNama.setText(nama);
        edPelEditEmail.setText(email);
        edPelEditHp.setText(hp);



        btnPelEditSimpan.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View v){
                ModelPelanggan mp = new ModelPelanggan();

                if (id != null) {
                    mp.setId(id);
                } else {
                    String uniqueID = UUID.randomUUID().toString();
                    mp.setId(uniqueID);
                }

                mp.setNama(edPelEditNama.getText().toString());
                mp.setEmail(edPelEditEmail.getText().toString());
                mp.setHp(edPelEditHp.getText().toString());

                if (id == null) {
                    boolean cek = db.insertPelanggan(mp);
                    if (cek) {
                        Toast.makeText(PelangganEditActivity.this, "Data berhasil disimpan", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(PelangganEditActivity.this, "Data gagal disimpan", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    boolean cek = db.updatePelanggan(mp);
                    if (cek) {
                        Toast.makeText(PelangganEditActivity.this, "Data berhasil diperbarui", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(PelangganEditActivity.this, "Data gagal diperbarui", Toast.LENGTH_SHORT).show();
                    }
                }

                startActivity(new Intent(PelangganEditActivity.this, PelangganActivity.class));
                finish();
            }
        });

        btnPelEditHapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (id != null) {
                    boolean cek = db.deletePelanggan(id);
                    if (cek) {
                        Toast.makeText(PelangganEditActivity.this, "Data berhasil dihapus", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(PelangganEditActivity.this, PelangganActivity.class));
                        finish();
                    } else {
                        Toast.makeText(PelangganEditActivity.this, "Data gagal dihapus", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(PelangganEditActivity.this, "Tidak ada data yang dipilih untuk dihapus", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnPelEditBatal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
