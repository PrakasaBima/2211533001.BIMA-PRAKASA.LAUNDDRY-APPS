package com.example.connecttojson;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdapterPelanggan extends
        RecyclerView.Adapter<AdapterPelanggan.ViewHolder> {
    private static final String TAG = AdapterPelanggan.class.getSimpleName();
    private Context context;
    private List<ModelPelanggan> list;
    private View.OnClickListener onItemClicked;

    public void setOnItemClickListener(View.OnClickListener itemClickListener) {
        onItemClicked = itemClickListener;
    }
    public AdapterPelanggan(Context context, List<ModelPelanggan> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pelanggan,
                parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ModelPelanggan item = list.get(position);
        holder.tvNama.setText(item.getNama());
        holder.tvHp.setText(item.getHp());
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
        TextView tvNama, tvHp;

        public ViewHolder(View itemView) {
            super(itemView);
            tvNama = (TextView) itemView.findViewById(R.id.tvItemPelNama);
            tvHp = (TextView) itemView.findViewById(R.id.tvItemPelHp);
            itemView.setTag(this);
            itemView.setOnClickListener(onItemClicked);

        }
    }

}