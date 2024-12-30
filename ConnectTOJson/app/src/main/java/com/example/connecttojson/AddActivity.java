package com.example.connecttojson;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AddActivity extends BaseActivity {
    String param;
    String id, nama, alamat, hp;
    EditText etNama, etAlamat, etHp;
    Button btnSimpan, btnHapus, btnBatal;
    LinearLayout lytBtn;
    RequestQueue requestQueue;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        setView();
//        cek apakah yang dipilih tambah data atau edit data
        if (param.equalsIgnoreCase("add")){
            btnHapus.setVisibility(View.GONE);
            lytBtn.setWeightSum(2);
        }else if(param.equalsIgnoreCase("edit")){
            lytBtn.setWeightSum(3);
            btnHapus.setVisibility(View.VISIBLE);
//            ambil data dari intent
            id = getIntent().getStringExtra("id");
            nama = getIntent().getStringExtra("nama");
            alamat = getIntent().getStringExtra("alamat");
            hp = getIntent().getStringExtra("hp");
//            set data ke dalam widget
            etNama.setText(nama);
            etAlamat.setText(alamat);
            etHp.setText(hp);
        }else{
            Toast.makeText(this, "oopss", Toast.LENGTH_SHORT).show();
        }

//        event listener ketika tombol simpan diklik
        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                cek param value =add atau edit
                if (param.equalsIgnoreCase("add")){
                    addPelanggan();
                }else if (param.equalsIgnoreCase("edit")){
                    editPelanggan();
                }
            }
        });
        btnBatal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnHapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog();
            }
        });
    }
    private void alertDialog(){
        AlertDialog.Builder alert = new AlertDialog.Builder(AddActivity.this);
        alert.setTitle("Informasi");
        alert.setMessage("Yakin data akan dihapus ?");
        alert.setCancelable(true);
        alert.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deletePelanggan();
            }
        });
        alert.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alert.show();

    }
    private void editPelanggan() {
        showMsg();
        Map<String, String> params = new HashMap<String, String>();
        params.put("id", id);
        params.put("nama", etNama.getText().toString());
        params.put("alamat", etAlamat.getText().toString());
        params.put("hp", etHp.getText().toString());

        JSONObject object = new JSONObject(params);
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.PUT, Config.base_url, object, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                try {
                    String status = jsonObject.getString("status");
                    String msg = jsonObject.getString("message");
                    if (status.equalsIgnoreCase("true")) {
                        progressDialog.dismiss();
                        Toast.makeText(AddActivity.this, ""+msg, Toast.LENGTH_SHORT).show();
                        setResult(200);
                        finish();
                    }else{
                        progressDialog.dismiss();
                        Toast.makeText(AddActivity.this, ""+msg, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                progressDialog.dismiss();
            }
        }){
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json");
                return params;
            }
        };
        requestQueue.add(request);
    }
    private void addPelanggan() {
        showMsg();
        Map<String, String> params = new HashMap<String, String>();
        params.put("nama", etNama.getText().toString());
        params.put("alamat", etAlamat.getText().toString());
        params.put("hp", etHp.getText().toString());

        JSONObject object = new JSONObject(params);
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST, Config.base_url, object, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                try {
                    String status = jsonObject.getString("status");
                    String msg = jsonObject.getString("message");
                    if (status.equalsIgnoreCase("true")) {
                        progressDialog.dismiss();
                        toast(msg);
                        setResult(200);
                        finish();
                    }else{
                        progressDialog.dismiss();
                        Toast.makeText(AddActivity.this, ""+msg, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                progressDialog.dismiss();
            }
        }){
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json");
                return params;
            }
        };
        requestQueue.add(request);
    }
    private void deletePelanggan() {
        showMsg();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.DELETE,
                Config.base_url+"?id="+id, null , new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                try {
                    progressDialog.dismiss();
                    String status = jsonObject.getString("status");
                    String msg = jsonObject.getString("message");
                    if (status.equalsIgnoreCase("true")) {
                        progressDialog.dismiss();
                        Toast.makeText(AddActivity.this, ""+msg, Toast.LENGTH_SHORT).show();
                        setResult(200);
                        finish();
                    }else{
                        progressDialog.dismiss();
                        Toast.makeText(AddActivity.this, ""+msg, Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(AddActivity.this, ""+volleyError, Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(request);

    }
    private void setView() {
        param = getIntent().getStringExtra("param");
        etNama = (EditText) findViewById(R.id.etNama);
        etAlamat = (EditText) findViewById(R.id.etAlamat);
        etHp = (EditText) findViewById(R.id.etHp);
        btnSimpan = (Button) findViewById(R.id.btnSimpan);
        btnHapus = (Button) findViewById(R.id.btnHapus);
        btnBatal = (Button) findViewById(R.id.btnBatal);
        lytBtn = (LinearLayout) findViewById(R.id.lytBtn);
        progressDialog = new ProgressDialog(this);
        requestQueue = Volley.newRequestQueue(this);
    }
    private void showMsg() {
        progressDialog.setTitle("Informasi");
        progressDialog.setMessage("Loading Data..");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }


}