package com.jokopriyono.photography;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    private DataDummy dataDummy;

    RecyclerAdapter(DataDummy dataDummy) {
        this.dataDummy = dataDummy;
    }

    @NonNull
    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapter.ViewHolder holder, int position) {
        holder.txtName.setText(dataDummy.getListDataDummy().get(position));
    }

    @Override
    public int getItemCount() {
        return dataDummy.getListDataDummy().size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtName;

        ViewHolder(@NonNull View v) {
            super(v);
            txtName = v.findViewById(R.id.txt_name);
        }
    }
}
