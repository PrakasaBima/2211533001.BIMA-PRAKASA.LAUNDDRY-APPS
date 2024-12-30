package com.example.apaloundry.Layanan;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.apaloundry.R;
import com.example.apaloundry.database.SQLiteHelper;
import com.example.apaloundry.model.ModelLayanan;

import java.util.UUID;

public class LayananEditActivity extends AppCompatActivity {
    private String id, tipe, harga;
    private EditText EditLayanan, EditHarga;
    private Button EditSimpan, EditHapus, EditBatal;

    SQLiteHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layanan_edit);

        id = getIntent().getStringExtra("ID");
        tipe = getIntent().getStringExtra("TIPE");
        harga = getIntent().getStringExtra("HARGA");

        db = new SQLiteHelper(this);
        EditLayanan = findViewById(R.id.EditLayanan);
        EditHarga = findViewById(R.id.EditHarga);

        EditLayanan.setText(tipe);
        EditHarga.setText(harga);

        EditSimpan = findViewById(R.id.EditSimpan);
        EditHapus = findViewById(R.id.EditHapus);
        EditBatal = findViewById(R.id.EditBatal);

        EditSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v){
                ModelLayanan ml = new ModelLayanan();

                if (id != null) {
                    ml.setId(id);
                } else {
                    String uniqueID = UUID.randomUUID().toString();
                    ml.setId(uniqueID);
                }

                ml.setNamaLayanan(EditLayanan.getText().toString());
                ml.setHarga(EditHarga.getText().toString());

                boolean cek;
                if (id == null) {
                    cek = db.insertLayanan(ml);
                } else {
                    cek = db.updateLayanan(ml);
                }

                if (cek) {
                    Toast.makeText(LayananEditActivity.this, "Data berhasil disimpan", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(LayananEditActivity.this, "Data gagal disimpan", Toast.LENGTH_SHORT).show();
                }

                startActivity(new Intent(LayananEditActivity.this, LayananActivity.class));
                finish();
            }
        });

        EditHapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (id != null) {
                    boolean cek = db.deleteLayanan(id);
                    if (cek) {
                        Toast.makeText(LayananEditActivity.this, "Data berhasil dihapus", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LayananEditActivity.this, LayananActivity.class));
                        finish();
                    } else {
                        Toast.makeText(LayananEditActivity.this, "Data gagal dihapus", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(LayananEditActivity.this, "Tidak ada data yang dipilih untuk dihapus", Toast.LENGTH_SHORT).show();
                }
            }
        });

        EditBatal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}