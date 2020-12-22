package com.example.myapplication1.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication1.Activity.CardActivity;
import com.example.myapplication1.Model.Item_Card;
import com.example.myapplication1.R;

import java.util.ArrayList;

public class Card_Adapter extends RecyclerView.Adapter<Card_Adapter.MyViewHolder> {
    ArrayList<Item_Card> itemcards;
    Context context ;

    public Card_Adapter(ArrayList <Item_Card> itemcards, Context context){
        this.itemcards = itemcards;
        this.context = context;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_itemlistcard,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final Item_Card card = itemcards.get(position);
        holder.ItemnameCard.setText(itemcards.get(position).getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent_card = new Intent(context, CardActivity.class);
                intent_card.putExtra("Card_id", card.getId_Card());
                context.startActivity(intent_card);
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemcards.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView ItemnameCard;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ItemnameCard = itemView.findViewById(R.id.itemNameCard);

        }
    }
}
