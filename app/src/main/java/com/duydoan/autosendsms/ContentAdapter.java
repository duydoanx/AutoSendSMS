package com.duydoan.autosendsms;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class ContentAdapter extends RecyclerView.Adapter<ContentAdapter.ViewHolder> {

    private List<ContentData> dataList;

    public ContentAdapter(List<ContentData> dataList) {
        this.dataList = dataList;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View contentView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout,
                parent,false);
        ViewHolder viewHolder = new ViewHolder(contentView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ContentData contentData = dataList.get(position);
        holder.tvCode.setText(contentData.getCode());
        holder.tvNumber.setText(contentData.getNumber());
        holder.tvContent.setText(contentData.getText());
        holder.btDelete.setOnClickListener(view -> {
            Database database = Room.databaseBuilder(view.getContext().getApplicationContext(),
                    Database.class,"database")
                    .allowMainThreadQueries()
                    .build();
            database.contentDataDao().deleteDataById(contentData.getId());
            dataList.remove(position);
            notifyDataSetChanged();
            Snackbar.make(view,R.string.delete,Snackbar.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView tvCode, tvNumber,tvContent;
        public Button btDelete;

        public ViewHolder(View itemView){
            super(itemView);

            tvCode = (TextView) itemView.findViewById(R.id.tvCode);
            tvNumber = (TextView) itemView.findViewById(R.id.tvNumber);
            tvContent = (TextView) itemView.findViewById(R.id.tvContent);

            btDelete = (Button) itemView.findViewById(R.id.btDelete);
        }
    }
    public void setData(List<ContentData> newData) {
        this.dataList = newData;
        notifyDataSetChanged();
    }
}
