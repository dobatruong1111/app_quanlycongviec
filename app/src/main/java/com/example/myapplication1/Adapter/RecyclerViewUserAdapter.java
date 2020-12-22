package com.example.myapplication1.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication1.Model.User;

import java.util.List;



public class RecyclerViewUserAdapter extends RecyclerView.Adapter<RecyclerViewUserAdapter.MyUserViewHolder> {

    Context mContext;
    List<User> mDataUser;

    public RecyclerViewUserAdapter(Context mContext, List<User> mDataUser) {
        this.mContext = mContext;
        this.mDataUser = mDataUser;
        setHasStableIds(true);
    }

    public RecyclerViewUserAdapter() {
    }

    @NonNull
    @Override
    public MyUserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull MyUserViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class MyUserViewHolder extends RecyclerView.ViewHolder{

        public MyUserViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
