package com.biee.apaloundry.com.Layanan;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.biee.apaloundry.com.R;
import com.biee.apaloundry.com.adapter.AdapterLayanan;
import com.biee.apaloundry.com.database.SQLiteHelper;
import com.biee.apaloundry.com.model.ModelLayanan;

import java.util.ArrayList;
import java.util.List;



public class LayananActivity extends AppCompatActivity {
    SQLiteHelper db;
    Button btnLayAdd;
    RecyclerView rvLayanan;
    AdapterLayanan adapterLayanan;
    ArrayList<ModelLayanan> list;
    ProgressDialog progressDialog;
    AlphaAnimation btnAnimasi = new AlphaAnimation(1F, 0.5F);

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_layanan);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main),
                (v, insets) -> {
                    Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                    v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
                    return insets;
                });
        setView();
        eventHandling();
        getData();
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            v.startAnimation(btnAnimasi);
            RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) v.getTag();
            int position = viewHolder.getAdapterPosition();
            ModelLayanan ml = list.get(position);
            Toast.makeText(LayananActivity.this, "Nama Layanan: " + ml.getNamaLayanan(),
                    Toast.LENGTH_SHORT).show();
        }
    };

    private void getData() {
        list.clear();
        showMsg();
        progressDialog.dismiss();
        try {
            List<ModelLayanan> layananList = db.getLayanan();
            if (layananList.size() > 0) {
                for (ModelLayanan layanan : layananList) {
                    ModelLayanan ml = new ModelLayanan();
                    ml.setId(layanan.getId());
                    ml.setNamaLayanan(layanan.getNamaLayanan());
                    ml.setHarga(layanan.getHarga());
                    list.add(ml);
                }
                adapterLayanan = new AdapterLayanan(this, list);
                adapterLayanan.notifyDataSetChanged();
                rvLayanan.setAdapter(adapterLayanan);
                adapterLayanan.setOnItemClickListener(onClickListener);
            } else {
                Toast.makeText(this, "Data layanan tidak ditemukan", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void eventHandling() {
        btnLayAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LayananActivity.this,com.biee.apaloundry.com.AddLaundryActivity.class));
            }
        });
    }

    private void setView() {
        db = new SQLiteHelper(this);
        progressDialog = new ProgressDialog(this);
        btnLayAdd = (Button) findViewById(R.id.btnLayAdd);
        rvLayanan = (RecyclerView) findViewById(R.id.rvlayanan);
        list = new ArrayList<>();
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rvLayanan.setHasFixedSize(true);
        rvLayanan.setLayoutManager(llm);
    }

    private void showMsg() {
        progressDialog.setTitle("Informasi");
        progressDialog.setMessage("Loading Data..");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }
}