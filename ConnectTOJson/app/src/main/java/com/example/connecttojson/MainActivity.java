package com.example.connecttojson;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends BaseActivity {
    //    buat variable volley untuk mengirim request ke json
    RequestQueue requestQueue;
    RecyclerView rvPelanggan;
    AdapterPelanggan adapterPelanggan;
    ArrayList<ModelPelanggan> list;
    ProgressDialog progressDialog;
    AlphaAnimation btnAnimasi = new AlphaAnimation(1F,0.5F);
    Button btnAdd,btnReload;
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
        requestQueue = Volley.newRequestQueue(this);
        setView();
        getData();
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(MainActivity.this, AddActivity.class);
                a.putExtra("param", "add");
                startActivityForResult(a, 200);
            }
        });
        btnReload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData();
            }
        });
    }
    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            v.startAnimation(btnAnimasi);
            RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) v.getTag();
            int position = viewHolder.getAdapterPosition();
            ModelPelanggan mp = list.get(position);

            Intent a = new Intent(MainActivity.this, AddActivity.class);
            a.putExtra("param", "edit");
            a.putExtra("id", mp.getId());
            a.putExtra("nama", mp.getNama());
            a.putExtra("alamat", mp.getAlamat());
            a.putExtra("hp", mp.getHp());
            startActivityForResult(a, 200);

        }
    };
    private void showMsg() {
        progressDialog.setTitle("Informasi");
        progressDialog.setMessage("Loading Data..");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }
    private void setView() {
        progressDialog = new ProgressDialog(this);
        rvPelanggan = (RecyclerView) findViewById(R.id.rvPelanggan);
        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnReload = (Button) findViewById(R.id.btnReload);
        list = new ArrayList<>();
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rvPelanggan.setHasFixedSize(true);
        rvPelanggan.setLayoutManager(llm);
    }
    private void getData() {
        list.clear();
        showMsg();
        progressDialog.dismiss();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                Config.base_url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                try {
                    String status = jsonObject.getString("status");
                    String message = jsonObject.getString("message");
                    if (status.equalsIgnoreCase("true")){
//                        ambil json array dari object data ex : data
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for (int a=0; a<jsonArray.length(); a++){
//                            ambil json object dari data array (ex : id, nama, alamat, hp)
                            JSONObject object = jsonArray.getJSONObject(a);
                            ModelPelanggan mp = new ModelPelanggan();
                            mp.setId(object.getString("id"));
                            mp.setNama(object.getString("nama"));
                            mp.setAlamat(object.getString("alamat"));
                            mp.setHp(object.getString("hp"));
                            list.add(mp);
                        }
                        adapterPelanggan = new AdapterPelanggan(MainActivity.this, list);
                        adapterPelanggan.notifyDataSetChanged();
                        rvPelanggan.setAdapter(adapterPelanggan);
                        adapterPelanggan.setOnItemClickListener(onClickListener);


                    }else{
                        Toast.makeText(MainActivity.this, ""+message, Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.d("masnoer", volleyError.toString());
            }
        });
        requestQueue.add(jsonObjectRequest);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 200){
            getData();
        }
    }
}