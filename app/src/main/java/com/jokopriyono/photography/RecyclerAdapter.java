package com.jokopriyono.photography;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jokopriyono.photography.api.PhotoItem;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    private List<PhotoItem> photoItemList;
    private OnPhotoClickListener listener;

    RecyclerAdapter(List<PhotoItem> data, OnPhotoClickListener listener) {
        this.photoItemList = data;
        this.listener = listener;
    }

    public interface OnPhotoClickListener{
        void OnClick(PhotoItem photo);
    }

    @NonNull
    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapter.ViewHolder holder, int position) {
        holder.bind(listener);

        holder.txtName.setText(photoItemList.get(position).getAuthor());
        holder.txtType.setText(photoItemList.get(position).getFormat());
        holder.txtUrl.setText(photoItemList.get(position).getPost_url());

        String url = "https://picsum.photos/200?image=" + photoItemList.get(position).getId();
        Picasso.get().load(url).into(holder.imgPhoto);
    }

    @Override
    public int getItemCount() {
        return photoItemList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtName, txtType, txtUrl;
        ImageView imgPhoto;
        CardView cardView;

        ViewHolder(@NonNull View v) {
            super(v);
            txtName = v.findViewById(R.id.txt_name);
            txtType = v.findViewById(R.id.txt_type);
            txtUrl = v.findViewById(R.id.txt_url);
            imgPhoto = v.findViewById(R.id.img_photo);
            cardView = v.findViewById(R.id.cardview);
        }

        void bind(final OnPhotoClickListener listener) {
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.OnClick(photoItemList.get(getAdapterPosition()));
                }
            });
        }
    }
}
