package com.fairyfalls.kds;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alex on 15.11.2017.
 */

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {
    List<String> Date;
    List<String> Status;
    Context context;


    public CustomAdapter(Context context, List dates, List statuses) {
        this.context = context;
        this.Date = dates;
        this.Status = statuses;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rowlayout, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.dateView.setText(Date.get(position));
        holder.statusView.setText(Status.get(position));
    }

    @Override
    public int getItemCount() {
        return Date.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView dateView;
        TextView statusView;

        public MyViewHolder(View itemView) {
            super(itemView);
            dateView = (TextView)itemView.findViewById(R.id.date);
            statusView = (TextView)itemView.findViewById(R.id.status);
        }
    }

}
