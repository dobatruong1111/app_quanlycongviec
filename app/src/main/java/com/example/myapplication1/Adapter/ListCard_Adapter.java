package com.example.myapplication1.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication1.Model.ListCard;
import com.example.myapplication1.Model.Item_Card;
import com.example.myapplication1.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListCard_Adapter extends RecyclerView.Adapter<ListCard_Adapter.MyViewHolder> implements Filterable {
    ArrayList<ListCard> listcards ;
    Context context;
    ArrayList<ListCard> listcardsAll;
    //ArrayList<ArrayList<Item_Card>> Cards = new ArrayList<>();

    public ListCard_Adapter(ArrayList<ListCard> listcards, Context context){
        this.listcards = listcards;
        this.context = context;
        this.listcardsAll = new ArrayList<>(listcards);
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_listcard,parent,false);
        return new ListCard_Adapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        holder.ItemName.setText(listcards.get(position).getName());

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false);
        holder.RVitemlistcard.setLayoutManager(layoutManager);
        holder.RVitemlistcard.setFocusable(false);
        holder.RVitemlistcard.setHasFixedSize(true);
        readCard(holder.RVitemlistcard, this.listcards.get(position).getID_listcard());

        holder.ItemThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Dialog dialog = new Dialog(context);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.customer_dialog);

                final EditText text_input = (EditText) dialog.findViewById(R.id.edittext_card);
                Button button_ok = (Button) dialog.findViewById(R.id.button_ok);
                button_ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String Name = text_input.getText().toString();
                        writeCard(Name, position);
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });

        holder.menu_listcard.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(final View v) {
                final PopupMenu popup = new PopupMenu(context, holder.menu_listcard);
                popup.inflate(R.menu.menulistcard);
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.xoa_listcard:
                                String id = listcards.get(position).getID_listcard();
                                Delete_listcard(id);
                                //handle menu1 click
                                break;
                            case R.id.sua_listcard:
                                final Dialog dialog = new Dialog(context);
                                dialog.setContentView(R.layout.liscard_dialog);

                                final EditText text_inputliscard = (EditText) dialog.findViewById(R.id.edittext_listcard);
                                Button button_ok = (Button) dialog.findViewById(R.id.buttonliscard_ok);
                                button_ok.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        String name = text_inputliscard.getText().toString();
                                        String id = listcards.get(position).getID_listcard();
                                        Update_listcard(name, id);
                                        dialog.dismiss();
                                    }
                                });
                                dialog.show();
                                break;
                        }
                        return false;
                    }
                });
                popup.show();
            }
        });
    }
    public void writeCard(String name ,int position){
        ListCard listCard = listcards.get(position);
        String Id_listcard = listCard.getID_listcard();
        DatabaseReference referenceCard = FirebaseDatabase.getInstance().getReference();
        String Id_Card = referenceCard.push().getKey();
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("Name",name);
        hashMap.put("ID_of_listcard", Id_listcard);
        hashMap.put("Id_Card", Id_Card);

        referenceCard.child("Cards").child(Id_Card).setValue(hashMap);

        DatabaseReference referenceDate = FirebaseDatabase.getInstance().getReference();
        Map<String,Object> Date = new HashMap<>();
        Date.put("date", "Ngày hết hạn");
        Date.put("id_card", Id_Card);
        referenceDate.child("Date").child(Id_Card).setValue(Date);
    }

    public void readCard(RecyclerView recyclerView, String id){
        ArrayList<Item_Card> item_cards = new ArrayList<>();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Cards");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                item_cards.clear();
                for (DataSnapshot dataSnapshot1 : snapshot.getChildren()){
                    Item_Card card = dataSnapshot1.getValue(Item_Card.class);
                    if (card.getID_of_listcard().equals(id)){
                        item_cards.add(card);
                    }
                }
                Card_Adapter adapter1 = new Card_Adapter(item_cards,context);
                recyclerView.setAdapter(adapter1);
                adapter1.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void Delete_listcard(String id){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("ListCard");
        reference.child(id).removeValue();
        notifyDataSetChanged();
        DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference("Cards");
        reference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot1 : snapshot.getChildren()){
                    Item_Card card = dataSnapshot1.getValue(Item_Card.class);
                    if (card.getID_of_listcard().equals(id)){
                        DatabaseReference reference2 = FirebaseDatabase.getInstance().getReference("Cards");
                        reference2.child(card.getId_Card()).removeValue();
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    public void Update_listcard(String name, String id){
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("Name", name);
        hashMap.put("ID_listcard",id);
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("ListCard");
        reference.child(id).updateChildren(hashMap);
    }


    @Override
    public int getItemCount() {
        return listcards.size();
    }

    @Override
    public Filter getFilter() {

        return filter;
    }
    Filter filter = new Filter() {
        //chay tren background
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            List <ListCard> filteredList = new ArrayList<>();

            if (constraint.toString().isEmpty()){
                filteredList.addAll(listcardsAll);
            }else{
                for (ListCard movie : listcardsAll){
                    if(movie.getName().toLowerCase().contains(constraint.toString().toLowerCase())){
                        filteredList.add(movie);
                    }
                }

            }
            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredList;

            return filterResults;
        }
        //chay tren giao dien
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            listcards.clear();
            listcards.addAll((Collection<? extends ListCard>) results.values);
            notifyDataSetChanged();
        }
    };

    public class MyViewHolder extends  RecyclerView.ViewHolder{
        TextView ItemName , ItemThem;
        RecyclerView RVitemlistcard;
        TextView menu_listcard;
        public MyViewHolder(@NonNull View itemView) {

            super(itemView);
            ItemName = itemView.findViewById(R.id.ten_cv);
            RVitemlistcard = itemView.findViewById(R.id.rv);
            menu_listcard = itemView.findViewById(R.id.menu_listcard);
            ItemThem = itemView.findViewById(R.id.them_card);
        }
    }

}