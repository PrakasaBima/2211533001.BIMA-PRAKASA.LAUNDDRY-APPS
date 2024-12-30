package com.example.apaloundry.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.apaloundry.R;
import com.example.apaloundry.model.ModelLayanan;

import java.util.List;

public class AdapterLayanan extends RecyclerView.Adapter<AdapterLayanan.ViewHolder> {
    private static final String TAG = AdapterLayanan.class.getSimpleName();
    private Context context;
    private List<ModelLayanan> list;
    private View.OnClickListener onItemClicked;

    public void setOnItemClickListener(View.OnClickListener itemClickListener) {
        onItemClicked = itemClickListener;
    }

    public AdapterLayanan(Context context, List<ModelLayanan> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layanan, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ModelLayanan item = list.get(position);
        holder.tvNamaLayanan.setText(item.getNamaLayanan());
        holder.tvHarga.setText(item.getHarga());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void clear() {
        int size = this.list.size();
        this.list.clear();
        notifyItemRangeRemoved(0, size);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNamaLayanan, tvHarga;

        public ViewHolder(View itemView) {
            super(itemView);
            tvNamaLayanan = itemView.findViewById(R.id.tvNamaLayanan);
            tvHarga = itemView.findViewById(R.id.tvHarga);
            itemView.setTag(this);
            itemView.setOnClickListener(onItemClicked);
        }
    }
}